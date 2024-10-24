package edu.gatech.seclass.jobcompare6300;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.gatech.seclass.jobcompare6300.data.database.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.models.Job;
import edu.gatech.seclass.jobcompare6300.data.models.Weights;
import edu.gatech.seclass.jobcompare6300.ui.CompareJobActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import android.content.Context;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class CompareJobsTest {
    private AppDatabase db;
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = AppDatabase.getInstance(context);
        db.clearAllTables();  // Clear all data before each test
    }


    @Test
    public void testCorrectCompare() {
        ActivityScenario<CompareJobActivity> scenario = ActivityScenario.launch(CompareJobActivity.class);
        onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("Compare Job Offers")));
        scenario.close();
    }

    @Test
    public void testExitButtonExists() {
        ActivityScenario<CompareJobActivity> scenario = ActivityScenario.launch(CompareJobActivity.class);
        onView(withId(R.id.exitID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testRankTableExists() {
        ActivityScenario<CompareJobActivity> scenario = ActivityScenario.launch(CompareJobActivity.class);
        onView(withId(R.id.rankTableID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testSelectJob1Exists() {
        ActivityScenario<CompareJobActivity> scenario = ActivityScenario.launch(CompareJobActivity.class);
        onView(withId(R.id.selectJob1_ID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testSelectJob2Exists() {
        ActivityScenario<CompareJobActivity> scenario = ActivityScenario.launch(CompareJobActivity.class);
        onView(withId(R.id.selectJob2_ID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testScoreCalculation() {
        Context context;

        context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = AppDatabase.getInstance(context);
        Job job1 = new Job("Software Engineer", "Company 1", "San Francisco", "CA", 100, 80000, 15000, 2000, 15, 2, false);
        Job job2 = new Job("Product Manager", "Company 2", "New York", "NY", 110, 70000, 20000, 3000, 20, 1, false);
        Weights weights = new Weights();
        weights.updateWeights(2, 4, 6, 8, 2);

        db.jobDAO().insertJob(job1);
        db.jobDAO().insertJob(job2);
        db.weightsDAO().insertWeights(weights);

        // Manually calculate expected scores
        float AYS1 = job1.adjustYearlySalary();
        float AYB1 = job1.adjustYearlyBonus();
        float TDF1 = job1.getTrainingDevFund();
        float LT1 = job1.getLeaveTime();
        int RWT1 = job1.getTeleworkPerW();

        float AYS2 = job2.adjustYearlySalary();
        float AYB2 = job2.adjustYearlyBonus();
        float TDF2 = job2.getTrainingDevFund();
        float LT2 = job2.getLeaveTime();
        int RWT2 = job2.getTeleworkPerW();

        int yearlySalaryWeight = weights.getYearlySalaryWeight();
        int yearlyBonusWeight = weights.getYearlyBonusWeight();
        int trainingFundWeight = weights.getTrainingFundWeight();
        int leaveTimeWeight = weights.getLeaveTimeWeight();
        int teleworkPerWWeight = weights.getTeleworkPerWWeight();
        int totalWeights = yearlySalaryWeight + yearlyBonusWeight + trainingFundWeight + leaveTimeWeight + teleworkPerWWeight;

        float part1Job1 = ((float) yearlySalaryWeight / totalWeights) * AYS1;
        float part2Job1 = ((float) yearlyBonusWeight / totalWeights) * AYB1;
        float part3Job1 = ((float) trainingFundWeight / totalWeights) * TDF1;
        float part4Job1 = ((float) leaveTimeWeight / totalWeights) * (LT1 * AYS1 / 260.0f);
        float part5Job1 = ((float) teleworkPerWWeight / totalWeights) * ((260.0f - 52.0f * RWT1) * (AYS1 / 260.0f) / 8.0f);

        float expecteds1 = part1Job1 + part2Job1 + part3Job1 + part4Job1 - part5Job1;
        expecteds1 = Math.round(expecteds1) * 1f;

        float part1Job2 = ((float) yearlySalaryWeight / totalWeights) * AYS2;
        float part2Job2 = ((float) yearlyBonusWeight / totalWeights) * AYB2;
        float part3Job2 = ((float) trainingFundWeight / totalWeights) * TDF2;
        float part4Job2 = ((float) leaveTimeWeight / totalWeights) * (LT2 * AYS2 / 260.0f);
        float part5Job2 = ((float) teleworkPerWWeight / totalWeights) * ((260.0f - 52.0f * RWT2) * (AYS2 / 260.0f) / 8.0f);

        float expecteds2 = part1Job2 + part2Job2 + part3Job2 + part4Job2 - part5Job2;
        expecteds2 = Math.round(expecteds2) * 1f;

        // Verify
        ActivityScenario<CompareJobActivity> scenario = ActivityScenario.launch(CompareJobActivity.class);
        onView(withText(String.valueOf(expecteds1))).check(matches(isDisplayed()));
        onView(withText(String.valueOf(expecteds2))).check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void testRankTableSorted() {
        Context context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDatabase db = AppDatabase.getInstance(context);
        Job job1 = new Job("Software Engineer", "Company 1", "San Francisco", "CA", 100, 100000, 15000, 2000, 15, 2, false);
        Job job2 = new Job("Product Manager", "Company 2", "New York", "NY", 110, 120000, 20000, 3000, 20, 1, false);
        Job job3 = new Job("Data Scientist", "Company 3", "Seattle", "WA", 90, 95000, 12000, 1500, 10, 3, false);
        Weights weights = new Weights();
        weights.updateWeights(2, 4, 6, 8, 2);

        db.jobDAO().insertJob(job1);
        db.jobDAO().insertJob(job2);
        db.jobDAO().insertJob(job3);
        db.weightsDAO().insertWeights(weights);

        ActivityScenario<CompareJobActivity> scenario = ActivityScenario.launch(CompareJobActivity.class);

        // Verify if the scores are correct in the rank table
        List<Job> jobs = db.jobDAO().getAllJobsSortedByScore();
        assert jobs.get(0).getTitle().equals("Product Manager");
        assert jobs.get(1).getTitle().equals("Software Engineer");
        assert jobs.get(2).getTitle().equals("Data Scientist");

        scenario.close();
    }

    @Test
    public void testJobInPopup() {
        Context context;
        AppDatabase db;
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = AppDatabase.getInstance(context);

        Job job1 = new Job("Software Engineer", "Tech Company", "Atlanta", "GA", 100, 120000, 15000, 2000, 15, 2, false);
        Job job2 = new Job("UX Designer", "Company 2", "San Francisco", "CA", 100, 80000, 10000, 5000, 20, 3, false);
        Weights weights = new Weights();
        weights.updateWeights(2, 4, 6, 8, 2);

        db.jobDAO().insertJob(job1);
        db.jobDAO().insertJob(job2);
        db.weightsDAO().insertWeights(weights);

        ActivityScenario<CompareJobActivity> scenario = ActivityScenario.launch(CompareJobActivity.class);

        // Click Select Job
        onView(withId(R.id.selectJob1_ID)).perform(click());

        // Check if the job is listed in the selection
        onView(withText("Software Engineer@Tech Company")).check(matches(isDisplayed()));
        onView(withText("UX Designer@Company 2")).check(matches(isDisplayed()));
        onView(withText("Software Engineer@Tech Company")).perform(click());

        onView(withId(R.id.selectJob2_ID)).perform(click());
        // Check if the job is listed in the selection
        onView(withText("UX Designer@Company 2")).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testJobCompareTable() {
        Context context;
        AppDatabase db;
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = AppDatabase.getInstance(context);
        Job job1 = new Job("DevOps Engineer", "Company 7", "New York", "NY", 100, 105000, 12000, 5000, 18, 2, false);
        Job job2 = new Job("QA", "Company 6", "LA", "CA", 95, 90000, 10000, 4000, 15, 1, false);
        Weights weights = new Weights();
        weights.updateWeights(2, 4, 6, 8, 2);

        db.jobDAO().insertJob(job1);
        db.jobDAO().insertJob(job2);
        db.weightsDAO().insertWeights(weights);

        ActivityScenario<CompareJobActivity> scenario = ActivityScenario.launch(CompareJobActivity.class);

        onView(withId(R.id.selectJob1_ID)).perform(click());
        onView(withText("DevOps Engineer@Company 7")).check(matches(isDisplayed()));
        onView(withText("DevOps Engineer@Company 7")).perform(click());

        onView(withId(R.id.selectJob2_ID)).perform(click());
        onView(withText("QA@Company 6")).check(matches(isDisplayed()));
        onView(withText("QA@Company 6")).perform(click());

        // Click Compare
        onView(withId(R.id.compareID)).perform(click());

        // Check if the table displayed correctly
        onView(withId(R.id.compareTableID)).check(matches(isDisplayed()));
        onView(allOf(withParent(withId(R.id.compareTableID)), withChild(withText("DevOps Engineer"))))
                .check(matches(isDisplayed()));
        onView(allOf(withParent(withId(R.id.compareTableID)), withChild(withText("QA"))))
                .check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void testBackButton() {
        ActivityScenario<CompareJobActivity> scenario = ActivityScenario.launch(CompareJobActivity.class);

        onView(withId(R.id.exitID)).perform(click());
        // Back to Main Page
        onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("JobCompare")));
        scenario.close();
   }
}

