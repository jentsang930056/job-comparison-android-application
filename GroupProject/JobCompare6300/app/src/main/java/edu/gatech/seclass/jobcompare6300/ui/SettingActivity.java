package edu.gatech.seclass.jobcompare6300.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.data.database.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.models.Job;
import edu.gatech.seclass.jobcompare6300.data.models.Weights;

public class SettingActivity extends AppCompatActivity {
    private AppDatabase db;
    private Button exitButton;
    private EditText yearlySalaryField;
    private EditText yearlyBonusField;
    private EditText trainingFundField;
    private EditText leaveTimeField;
    private EditText teleworkDayField;
    private Button saveButton;
    private Button cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        db = AppDatabase.getInstance(this);

        exitButton = (Button) findViewById(R.id.exitID);
        cancelButton = (Button) findViewById(R.id.cancelButtonID);
        saveButton = (Button) findViewById(R.id.saveButtonID);
        yearlySalaryField = (EditText) findViewById(R.id.yearlySalaryWID);
        yearlyBonusField = (EditText) findViewById(R.id.yearlyBonusWID);
        trainingFundField = (EditText) findViewById(R.id.trainingFundWID);
        leaveTimeField = (EditText) findViewById(R.id.leaveTimeWID);
        teleworkDayField = (EditText) findViewById(R.id.teleworkDaysWID);

        populateFieldsWithWeights(db.weightsDAO().getWeights());

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
                if (checkInputs()) {
                    updateNewWeights();
                    goToMain();
                }
            }
        });
    }

    private boolean checkInputs() {
        List<EditText> editTextList = Arrays.asList(
                yearlySalaryField, yearlyBonusField, trainingFundField, leaveTimeField, teleworkDayField
        );

        Drawable errorIcon = ContextCompat.getDrawable(SettingActivity.this, R.drawable.ic_error);
        if (errorIcon != null) {
            errorIcon.setBounds(0, 0, errorIcon.getIntrinsicWidth(), errorIcon.getIntrinsicHeight());
        }

        boolean isValid = true;
        for (EditText editText : editTextList){
            String input = editText.getText().toString();
            if (TextUtils.isEmpty(input) || !isInteger(input)) {
                editText.setError("Invalid Input", errorIcon);
                isValid = false;
            }
        }

        return isValid;
    }

    private static boolean isInteger(String text) {
        try {
            int weight = Integer.parseInt(text);
            return weight >= 0 && weight <= 9;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void updateNewWeights() {
        Weights weights = new Weights();

        int yearlySalaryW = Integer.parseInt(yearlySalaryField.getText().toString());
        int yearlyBonusFieldW = Integer.parseInt(yearlyBonusField.getText().toString());
        int tandDW = Integer.parseInt(trainingFundField.getText().toString());
        int leaveTimeW = Integer.parseInt(leaveTimeField.getText().toString());
        int teleworkDayW = Integer.parseInt(teleworkDayField.getText().toString());

        weights.updateWeights(yearlySalaryW, yearlyBonusFieldW, tandDW, leaveTimeW, teleworkDayW);
        db.weightsDAO().insertWeights(weights);

        // Update all the scores
        // db.jobDAO().getAll().forEach(job -> calculateScore(job.getJobId()));
    }

    private void goToMain() {
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void populateFieldsWithWeights(Weights weights) {
        if (weights != null) {
            yearlySalaryField.setHint(String.valueOf(weights.getYearlySalaryWeight()));
            yearlyBonusField.setHint(String.valueOf(weights.getYearlyBonusWeight()));
            teleworkDayField.setHint(String.valueOf(weights.getTeleworkPerWWeight()));
            leaveTimeField.setHint(String.valueOf(weights.getLeaveTimeWeight()));
            trainingFundField.setHint(String.valueOf(weights.getTrainingFundWeight()));
        }
    }

    private void calculateScore(long jobId) {

        Job job = db.jobDAO().getJobByID(jobId);

        float AYS = job.adjustYearlySalary();
        float AYB = job.adjustYearlyBonus();
        float TDF = job.getTrainingDevFund();
        float LT = job.getLeaveTime();
        int RWT = job.getTeleworkPerW();

        // Get weights and calculate each part of the formula
        Weights weights = db.weightsDAO().getWeights();

        int yearlySalaryWeight = weights.getYearlySalaryWeight();
        int yearlyBonusWeight = weights.getYearlyBonusWeight();
        int trainingFundWeight = weights.getTrainingFundWeight();
        int leaveTimeWeight = weights.getLeaveTimeWeight();
        int teleworkPerWWeight = weights.getTeleworkPerWWeight();
        int totalWeights = yearlySalaryWeight + yearlyBonusWeight + trainingFundWeight + leaveTimeWeight + teleworkPerWWeight;

        float part1 = ((float) yearlySalaryWeight / totalWeights) * AYS;
        float part2 = ((float) yearlyBonusWeight / totalWeights) * AYB;
        float part3 = ((float) trainingFundWeight / totalWeights) * TDF;
        float part4 = ((float) leaveTimeWeight / totalWeights) * (LT * AYS / 260.0f);
        float part5 = ((float) teleworkPerWWeight / totalWeights) * ((260.0f - 52.0f * RWT) * (AYS / 260.0f) / 8.0f);

        // Add up to compute the final score
        float score = part1 + part2 + part3 + part4 - part5;
        score = Math.round(score) * 1f;

        // update the job with the new score
        job.setScore(score);

        db.jobDAO().updateJob(job);
    }

}