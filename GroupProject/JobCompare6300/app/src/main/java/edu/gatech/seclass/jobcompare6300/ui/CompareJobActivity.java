package edu.gatech.seclass.jobcompare6300.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.data.database.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.models.Job;
import edu.gatech.seclass.jobcompare6300.data.models.Weights;

public class CompareJobActivity extends AppCompatActivity {
    private AppDatabase db;
    private Button exitButton;
    private TableLayout rankTable;
    private Button selectJob1;
    private Button selectJob2;
    private Button compareButton;
    private TableLayout compareTable;
    private List<Job> sortedJobs;

    private long selectedJob1ID;
    private long selectedJob2ID;
    private TextView errorMessage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_job);

        db = AppDatabase.getInstance(this);

        exitButton = (Button) findViewById(R.id.exitID);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
                rankTable.removeAllViews();
            }
        });

        rankTable = (TableLayout) findViewById(R.id.rankTableID);
        fillRankTable();

        selectJob1 = (Button) findViewById(R.id.selectJob1_ID);
        selectJob1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showJobSelectionPopup(0);
            }
        });

        selectJob2 = (Button) findViewById(R.id.selectJob2_ID);
        selectJob2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showJobSelectionPopup(1);
            }
        });

        compareTable = (TableLayout) findViewById(R.id.compareTableID);
        compareTable.setVisibility(View.GONE);

        errorMessage = (TextView) findViewById(R.id.errorMessageID);
        compareButton = (Button) findViewById(R.id.compareID);
        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorMessage.setVisibility(View.GONE);
                if (selectedJob1ID == selectedJob2ID ) {
                    errorMessage.setVisibility(View.VISIBLE);
                    return;
                }

                int childCount = compareTable.getChildCount();
                if (childCount > 1) {
                    compareTable.removeViews(1, childCount - 1);
                }
                fillCompareTable();
                compareTable.setVisibility(View.VISIBLE);
            }
        });

    }

    private void addRowToRankTable(String title, String company, int jobId, double score) {
        TableRow newRow = new TableRow(this);

        TextView idTextView = new TextView(this);
        idTextView.setText(String.valueOf(jobId));
        idTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
        idTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        idTextView.setPadding(10, 10, 10, 10);
        newRow.addView(idTextView);

        TextView titleView = new TextView(this);
        titleView.setText(title);
        titleView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4f));
        titleView.setGravity(Gravity.CENTER_HORIZONTAL);
        titleView.setPadding(10, 10, 10, 10);
        newRow.addView(titleView);

        TextView companyTextView = new TextView(this);
        companyTextView.setText(company);
        companyTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 6f));
        companyTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        companyTextView.setPadding(10, 10, 10, 10);
        newRow.addView(companyTextView);

        TextView scoreTextView = new TextView(this);
        scoreTextView.setText(String.valueOf(score));
        scoreTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
        scoreTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        scoreTextView.setPadding(10, 10, 10, 10);
        newRow.addView(scoreTextView);

        rankTable.addView(newRow);
    }

    private void fillRankTable() {
        List<Job> jobs = db.jobDAO().getAll();
        if (jobs == null || jobs.size() < 1) {
            return;
        }

        for (Job job : jobs) {
            calculateScore(job.getJobId());
        }

        int counter = 1;
        sortedJobs = db.jobDAO().getAllJobsSortedByScore();
        for (Job job : sortedJobs) {
            if (job.isCurrentJob()){
                addRowToRankTable(job.getTitle() + " (Current)", job.getCompany(), counter, job.getScore());
            }
            else {
                addRowToRankTable(job.getTitle(), job.getCompany(), counter, job.getScore());
            }
            counter += 1;
        }
    }

    private void fillCompareTable() {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        Job job1 = db.jobDAO().getJobByID(selectedJob1ID);
        Job job2 = db.jobDAO().getJobByID(selectedJob2ID);
        addDividerToCompareTable(compareTable);
        addRowToCompareTable(compareTable, "Title", job1.getTitle(), job2.getTitle());
        addDividerToCompareTable(compareTable);
        addRowToCompareTable(compareTable, "Company", job1.getCompany(), job2.getCompany());
        addDividerToCompareTable(compareTable);
        addRowToCompareTable(compareTable, "Location", job1.getCity() + ", " + job1.getState(), job2.getCity() + ", " + job2.getState());
        addDividerToCompareTable(compareTable);
        addRowToCompareTable(compareTable, "Salary (Adjusted)", currencyFormatter.format(job1.adjustYearlySalary()), currencyFormatter.format(job2.adjustYearlySalary()));
        addDividerToCompareTable(compareTable);
        addRowToCompareTable(compareTable, "Bonus (Adjusted)", currencyFormatter.format(job1.adjustYearlyBonus()), currencyFormatter.format(job2.adjustYearlyBonus()));
        addDividerToCompareTable(compareTable);
        addRowToCompareTable(compareTable, "Training Fund", currencyFormatter.format(job1.getTrainingDevFund()), currencyFormatter.format(job2.getTrainingDevFund()));
        addDividerToCompareTable(compareTable);
        addRowToCompareTable(compareTable, "Leave Time", String.valueOf(job1.getLeaveTime()), String.valueOf(job2.getLeaveTime()));
        addDividerToCompareTable(compareTable);
        addRowToCompareTable(compareTable, "Telework Days", String.valueOf(job1.getTeleworkPerW()), String.valueOf(job2.getTeleworkPerW()));
        addDividerToCompareTable(compareTable);
    }

    private void addRowToCompareTable(TableLayout table, String attribute, String job1Value, String job2Value) {
        TableRow row = new TableRow(this);

        TextView attributeView = new TextView(this);
        attributeView.setText(attribute);
        attributeView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        attributeView.setGravity(Gravity.CENTER_HORIZONTAL);
        attributeView.setPadding(10, 10, 10, 10);
        row.addView(attributeView);

        TextView job1View = new TextView(this);
        job1View.setText(job1Value);
        job1View.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        job1View.setGravity(Gravity.CENTER_HORIZONTAL);
        job1View.setPadding(10, 10, 10, 10);
        row.addView(job1View);

        TextView job2View = new TextView(this);
        job2View.setText(job2Value);
        job2View.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        job2View.setGravity(Gravity.CENTER_HORIZONTAL);
        job2View.setPadding(10, 10, 10, 10);
        row.addView(job2View);

        table.addView(row);
    }

    private void addDividerToCompareTable(TableLayout table) {
        View divider = new View(this);
        divider.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
        divider.setBackgroundColor(Color.LTGRAY);
        table.addView(divider);
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

    private void goToMain() {
        Intent intent = new Intent(CompareJobActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void showJobSelectionPopup(int button_num) {
        String[] display_strings = new String[sortedJobs.size()];
        for (int i = 0; i < sortedJobs.size(); i++) {
            Job job = sortedJobs.get(i);
            if (job.isCurrentJob()) {
                display_strings[i] = job.getTitle() + "@" + job.getCompany() + " (Current)";
            }
            else {
                display_strings[i] = job.getTitle() + "@" + job.getCompany();
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Job " + String.valueOf(button_num + 1));

        builder.setItems(display_strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (button_num == 0){selectedJob1ID = sortedJobs.get(which).getJobId();}
                else if (button_num == 1){selectedJob2ID = sortedJobs.get(which).getJobId();}
                Toast.makeText(CompareJobActivity.this, "Selected: " + sortedJobs.get(which).getTitle() + "@" + sortedJobs.get(which).getCompany() , Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}