package edu.gatech.seclass.jobcompare6300;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.gatech.seclass.jobcompare6300.data.database.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.models.Job;
import edu.gatech.seclass.jobcompare6300.ui.EnterJobActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class EnterJobTest {

    AppDatabase db;
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = AppDatabase.getInstance(context);
        db.clearAllTables();  // Clear all data before each test
    }

    @Test
    public void testCorrectEnter() {
        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);
        onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("Enter Job Details")));
        scenario.close();
    }

    @Test
    public void testTitleExists() {
        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);
        onView(withId(R.id.titleID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testCompanyExists() {
        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);
        onView(withId(R.id.companyID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testCityExists() {
        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);
        onView(withId(R.id.cityID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testStateExists() {
        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);
        onView(withId(R.id.stateID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testCostOfLivingExists() {
        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);
        onView(withId(R.id.costOfLivingID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testYearlySalaryExists() {
        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);
        onView(withId(R.id.yearlySalaryID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testYearlyBonusExists() {
        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);
        onView(withId(R.id.yearlyBonusID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testTrainingFundExists() {
        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);
        onView(withId(R.id.trainingFundID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testLeaveTimeExists() {
        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);
        onView(withId(R.id.leaveTimeID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testTeleworkDaysExists() {
        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);
        onView(withId(R.id.teleworkDaysID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testSaveButtonExists() {
        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);
        onView(withId(R.id.saveButtonID)).perform(scrollTo()).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testCancelButtonExists() {
        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);
        onView(withId(R.id.cancelButtonID)).perform(scrollTo()).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testJobIsSaved() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDatabase db = AppDatabase.getInstance(context);

        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);

        // Input values
        onView(withId(R.id.titleID)).perform(replaceText("Software Engineer"));
        onView(withId(R.id.companyID)).perform(replaceText("Tech Company"));
        onView(withId(R.id.cityID)).perform(replaceText("Atlanta"));
        onView(withId(R.id.stateID)).perform(replaceText("GA"));
        onView(withId(R.id.costOfLivingID)).perform(replaceText("100"));
        onView(withId(R.id.yearlySalaryID)).perform(replaceText("120000"));
        onView(withId(R.id.yearlyBonusID)).perform(replaceText("15000"));
        onView(withId(R.id.trainingFundID)).perform(replaceText("2000"));
        onView(withId(R.id.leaveTimeID)).perform(replaceText("15"));
        onView(withId(R.id.teleworkDaysID)).perform(replaceText("2"));
        onView(withId(R.id.checkBox)).perform(click());

        // Click Save button
        onView(withId(R.id.saveButtonID)).perform(scrollTo(), click());

        // Check that the job is saved in the database
        Job savedJob = db.jobDAO().getCurrentJob();
        assert savedJob != null;
        assert savedJob.getTitle().equals("Software Engineer");
        assert savedJob.getCompany().equals("Tech Company");
        assert savedJob.getCity().equals("Atlanta");
        assert savedJob.getState().equals("GA");
        assert savedJob.getCostOfLiving() == 100;
        assert savedJob.getYearlySalary() == 120000;
        assert savedJob.getYearlyBonus() == 15000;
        assert savedJob.getTrainingDevFund() == 2000;
        assert savedJob.getLeaveTime() == 15;
        assert savedJob.getTeleworkPerW() == 2;

        scenario.close();
    }

    @Test
    public void testInvalidInput() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDatabase db = AppDatabase.getInstance(context);

        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);

        // Input invalid values
        onView(withId(R.id.titleID)).perform(replaceText("Teacher"));
        onView(withId(R.id.companyID)).perform(replaceText("12345"));
        onView(withId(R.id.cityID)).perform(replaceText("!!!"));
        onView(withId(R.id.stateID)).perform(replaceText("123"));
        onView(withId(R.id.costOfLivingID)).perform(replaceText("abc"));
        onView(withId(R.id.yearlySalaryID)).perform(replaceText("abc"));
        onView(withId(R.id.yearlyBonusID)).perform(replaceText("###"));
        onView(withId(R.id.trainingFundID)).perform(replaceText("##"));
        onView(withId(R.id.leaveTimeID)).perform(replaceText("##"));
        onView(withId(R.id.teleworkDaysID)).perform(replaceText("6"));

        onView(withId(R.id.saveButtonID)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.saveButtonID)).perform(click());

        List<Job> savedJob = db.jobDAO().getAll();
        boolean jobFound = false;
        for (Job job : savedJob) {
            if (job.getTitle().equals("Teacher")) {
                jobFound = true;
                break;
            }
        }
        assert !jobFound;

        scenario.close();
    }

    @Test
    public void testBackButton() {
        ActivityScenario<EnterJobActivity> scenario = ActivityScenario.launch(EnterJobActivity.class);

        onView(withId(R.id.exitID)).perform(click());
        // Back to Main Page
        onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("JobCompare")));
        scenario.close();
    }

}
