package edu.gatech.seclass.jobcompare6300.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.data.database.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.models.Job;
import edu.gatech.seclass.jobcompare6300.data.models.Weights;

import android.widget.Button;
import android.content.Intent;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;

    private Button enterButton;
    private Button updateButton;
    private Button settingButton;
    private Button compareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = AppDatabase.getInstance(this);
        List<Job> jobs = db.jobDAO().getAll();
        initializeWeights();

        enterButton = (Button) findViewById(R.id.enterButtonID);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnterJobActivity.class);
                startActivity(intent);
            }
        });

        updateButton = (Button) findViewById(R.id.updateJobButtonID);
        updateButton.setEnabled(jobs.size() > 0);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateJobActivity.class);
                startActivity(intent);
            }
        });

        settingButton = (Button) findViewById(R.id.settingButtonID);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        compareButton = (Button) findViewById(R.id.compareButtonID);
        compareButton.setEnabled(jobs.size() > 1);
        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CompareJobActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initializeWeights() {
        // if not initialized, add default weights
        if (db.weightsDAO().getWeights() == null) {
            Weights weights = new Weights();
            db.weightsDAO().insertWeights(weights);
        }
    }
}
