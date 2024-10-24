package edu.gatech.seclass.jobcompare6300.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.data.database.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.models.Job;

public class UpdateJobActivity extends AppCompatActivity {

    private AppDatabase db;
    private Button exitButton;
    private Button selectJobButton;
    private Button updateJobButton;
    private Button deleteJobButton;
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
    private long selectedJobId;
    private List<Job> jobs;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_job);

        db = AppDatabase.getInstance(this);
        jobs = db.jobDAO().getAll();

        exitButton = findViewById(R.id.exitID);
        updateJobButton = findViewById(R.id.updateJobButtonID);
        selectJobButton = (Button) findViewById(R.id.selectJobButtonID);
        deleteJobButton = (Button) findViewById(R.id.deleteButtonID);
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

        selectJobButton.setOnClickListener(v -> showJobSelectionPopup());
        exitButton.setOnClickListener(v -> goToMain());
        updateJobButton.setOnClickListener(view -> {
            boolean isValid = true;
            List<String> inputs = new ArrayList<>();
            inputs.add(title.getText().toString());
            inputs.add(company.getText().toString());
            inputs.add(city.getText().toString());
            inputs.add(state.getText().toString());
            inputs.add(costOfLiving.getText().toString());
            inputs.add(yearlySalary.getText().toString());
            inputs.add(yearlyBonus.getText().toString());
            inputs.add(trainingFund.getText().toString());
            inputs.add(leaveTime.getText().toString());
            inputs.add(teleworkDays.getText().toString());

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

            for (int i = 0; i < inputs.size(); i++) {
                String input = inputs.get(i);
                EditText currentEditText = editTextList.get(i);
                if (TextUtils.isEmpty(input)) {
                    Drawable errorIcon = ContextCompat.getDrawable(UpdateJobActivity.this, R.drawable.ic_error);
                    if (errorIcon != null) {
                        errorIcon.setBounds(0, 0, errorIcon.getIntrinsicWidth(), errorIcon.getIntrinsicHeight());
                    }
                    currentEditText.setError("Invalid Input", errorIcon);
                    isValid = false;
                }

                boolean containsLetterOrNumber = false;
                for (char c : input.toCharArray()) {
                    if (Character.isLetterOrDigit(c)) {
                        containsLetterOrNumber = true;
                        break;
                    }
                }
                if (!containsLetterOrNumber) {
                    Drawable errorIcon = ContextCompat.getDrawable(UpdateJobActivity.this, R.drawable.ic_error);
                    if (errorIcon != null) {
                        errorIcon.setBounds(0, 0, errorIcon.getIntrinsicWidth(), errorIcon.getIntrinsicHeight());
                    }
                    currentEditText.setError("Invalid Input", errorIcon);
                    isValid = false;
                }
            }

            if (isValid){
                //update job info to DB
                Job job = new Job();
                job.setJobId(selectedJobId);
                job.setTitle(title.getText().toString());
                job.setCompany(company.getText().toString());
                job.setCity(city.getText().toString());
                job.setState(state.getText().toString());
                job.setCostOfLiving(Integer.parseInt(costOfLiving.getText().toString()));
                job.setYearlySalary(Float.parseFloat(yearlySalary.getText().toString()));
                job.setYearlyBonus(Float.parseFloat(yearlyBonus.getText().toString()));
                job.setTrainingDevFund(Float.parseFloat(trainingFund.getText().toString()));
                job.setLeaveTime(Float.parseFloat(leaveTime.getText().toString()));
                job.setTeleworkPerW(Integer.parseInt(teleworkDays.getText().toString()));
                boolean currentJob = db.jobDAO().getJobByID(selectedJobId).isCurrentJob();
                job.setCurrentJob(currentJob);
                db.jobDAO().updateJob(job);
            }
            goToMain();
        });

        deleteJobButton.setOnClickListener(view -> {
            Job job = db.jobDAO().getJobByID(selectedJobId);
            db.jobDAO().deleteJob(job);
            goToMain();
        });

    }

    private void showJobSelectionPopup() {

        if (jobs != null && !jobs.isEmpty()) {
            String[] display_strings = new String[jobs.size()];
            for (int i = 0; i < jobs.size(); i++) {
                Job job = jobs.get(i);
                if (job.isCurrentJob()) {
                    display_strings[i] = job.getTitle() + "@" + job.getCompany() + " (Current)";
                }
                else {
                    display_strings[i] = job.getTitle() + "@" + job.getCompany();
                }
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateJobActivity.this);
            builder.setTitle("Select Job")
                    .setItems(display_strings, (dialog, which) -> {
                        Job selectedJob = jobs.get(which);
                        title.setText(selectedJob.getTitle());
                        company.setText(selectedJob.getCompany());
                        city.setText(selectedJob.getCity());
                        state.setText(selectedJob.getState());
                        costOfLiving.setText(String.valueOf(selectedJob.getCostOfLiving()));
                        yearlySalary.setText(String.valueOf(selectedJob.getYearlySalary()));
                        yearlyBonus.setText(String.valueOf(selectedJob.getYearlyBonus()));
                        trainingFund.setText(String.valueOf(selectedJob.getTrainingDevFund()));
                        leaveTime.setText(String.valueOf(selectedJob.getLeaveTime()));
                        teleworkDays.setText(String.valueOf(selectedJob.getTeleworkPerW()));
                        selectedJobId = selectedJob.getJobId();
                        Toast.makeText(UpdateJobActivity.this, "Selected: " + selectedJob.getTitle(), Toast.LENGTH_SHORT).show();
                    })
                    .create()
                    .show();
        } else {
            Toast.makeText(UpdateJobActivity.this, "No jobs available", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToMain() {
        Intent intent = new Intent(UpdateJobActivity.this, MainActivity.class);
        startActivity(intent);
    }
}