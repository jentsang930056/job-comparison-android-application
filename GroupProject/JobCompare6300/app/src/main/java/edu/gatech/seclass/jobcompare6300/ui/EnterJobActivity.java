package edu.gatech.seclass.jobcompare6300.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;

import java.util.List;
import java.util.ArrayList;

import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.data.database.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.models.Job;

public class EnterJobActivity extends AppCompatActivity {
    private AppDatabase db;
    private EditText title;
    private EditText company;
    private EditText city;
    private EditText state;
    private EditText costOfLiving;
    private EditText yearlySalary;
    private EditText yearlyBonus;
    private EditText trainingFund;
    private EditText leaveTime;
    private EditText teleworkDays;
    private Button exitButton;
    private Button cancelButton;
    private Button saveButton;
    private CheckBox isCurrentJob;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_job);

        db = AppDatabase.getInstance(this);

        exitButton = (Button) findViewById(R.id.exitID);
        cancelButton = (Button) findViewById(R.id.cancelButtonID);
        title = (EditText) findViewById(R.id.titleID);
        company = (EditText) findViewById(R.id.companyID);
        city = (EditText) findViewById(R.id.cityID);
        state = (EditText) findViewById(R.id.stateID);
        costOfLiving = (EditText) findViewById(R.id.costOfLivingID);
        yearlySalary = (EditText) findViewById(R.id.yearlySalaryID);
        yearlyBonus = (EditText) findViewById(R.id.yearlyBonusID);
        trainingFund = (EditText) findViewById(R.id.trainingFundID);
        leaveTime = (EditText) findViewById(R.id.leaveTimeID);
        teleworkDays = (EditText) findViewById(R.id.teleworkDaysID);
        isCurrentJob = (CheckBox) findViewById(R.id.checkBox);

        saveButton = findViewById(R.id.saveButtonID);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;
                Drawable errorIcon = ContextCompat.getDrawable(EnterJobActivity.this, R.drawable.ic_error);
                if (errorIcon != null) {
                    errorIcon.setBounds(0, 0, errorIcon.getIntrinsicWidth(), errorIcon.getIntrinsicHeight());
                }

                List<EditText> editTextList = new ArrayList<>();
                editTextList.add(title);
                editTextList.add(company);
                editTextList.add(city);
                editTextList.add(state);
                editTextList.add(costOfLiving);
                editTextList.add(yearlySalary);
                editTextList.add(yearlyBonus);
                editTextList.add(trainingFund);
                editTextList.add(leaveTime);
                editTextList.add(teleworkDays);

                for (EditText currentEditText : editTextList) {
                    String input = currentEditText.getText().toString();

                    if (TextUtils.isEmpty(input)) {
                        currentEditText.setError("Invalid Input", errorIcon);
                        isValid = false;
                    }

                    boolean containsLetterOrNumber = false;
                    for (char c : input.toCharArray()) {
                        if (Character.isLetterOrDigit(c)) {
                            containsLetterOrNumber = true;
                            if (currentEditText == teleworkDays) {
                                int inputValue = Integer.parseInt(input);
                                if (inputValue > 7) {
                                    currentEditText.setError("Invalid Input", errorIcon);
                                    isValid = false;
                                }
                            }
                            break;
                        }
                    }
                    if (!containsLetterOrNumber) {
                        currentEditText.setError("Invalid Input", errorIcon);
                        isValid = false;
                    }
                }

                if (isValid){
                    insert_new_job();
                    goToMain();
                }
            }
        });
    }

    private void insert_new_job() {
        String title_ = title.getText().toString();
        String company_ = company.getText().toString();
        String city_ = city.getText().toString();
        String state_ = state.getText().toString();
        int costOfLiving_ = Integer.parseInt(costOfLiving.getText().toString());
        float yearlySalary_ = Float.parseFloat(yearlySalary.getText().toString());
        float yearlyBonus_ = Float.parseFloat(yearlyBonus.getText().toString());
        float trainingFund_ = Float.parseFloat(trainingFund.getText().toString());
        float leaveTime_ = Float.parseFloat(leaveTime.getText().toString());
        int teleworkDays_ = Integer.parseInt(teleworkDays.getText().toString());
        boolean isCurrentJob_ = isCurrentJob.isChecked();

        Job newJob = new Job(title_, company_, city_, state_, costOfLiving_, yearlySalary_,
                yearlyBonus_, trainingFund_, leaveTime_, teleworkDays_, isCurrentJob_);

        if (isCurrentJob_) {
            // delete current job
            if (db.jobDAO().getCurrentJob() != null) {
                db.jobDAO().deleteJob(db.jobDAO().getCurrentJob());
            }
        }
        // add new job
        db.jobDAO().insertJob(newJob);
    }

    private void goToMain() {
        Intent intent = new Intent(EnterJobActivity.this, MainActivity.class);
        startActivity(intent);
    }
}














