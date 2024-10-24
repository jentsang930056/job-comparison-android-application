package edu.gatech.seclass.jobcompare6300;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import edu.gatech.seclass.jobcompare6300.data.database.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.models.Job;
import edu.gatech.seclass.jobcompare6300.ui.UpdateJobActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

@RunWith(AndroidJUnit4.class)
public class UpdateJobTest {

    @Test
    public void testCorrectUpdateJob() {
        ActivityScenario<UpdateJobActivity> scenario = ActivityScenario.launch(UpdateJobActivity.class);
        onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("Update Job Details")));
        scenario.close();
    }

    @Test
    public void testSelectJobButtonExists() {
        ActivityScenario<UpdateJobActivity> scenario = ActivityScenario.launch(UpdateJobActivity.class);
        onView(withId(R.id.selectJobButtonID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testTitleExists() {
        ActivityScenario<UpdateJobActivity> scenario = ActivityScenario.launch(UpdateJobActivity.class);
        onView(withId(R.id.titleID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testCompanyExists() {
        ActivityScenario<UpdateJobActivity> scenario = ActivityScenario.launch(UpdateJobActivity.class);
        onView(withId(R.id.companyID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testCityExists() {
        ActivityScenario<UpdateJobActivity> scenario = ActivityScenario.launch(UpdateJobActivity.class);
        onView(withId(R.id.cityID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testStateExists() {
        ActivityScenario<UpdateJobActivity> scenario = ActivityScenario.launch(UpdateJobActivity.class);
        onView(withId(R.id.stateID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testCostOfLivingExists() {
        ActivityScenario<UpdateJobActivity> scenario = ActivityScenario.launch(UpdateJobActivity.class);
        onView(withId(R.id.costOfLivingID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testYearlySalaryWExists() {
        ActivityScenario<UpdateJobActivity> scenario = ActivityScenario.launch(UpdateJobActivity.class);
        onView(withId(R.id.yearlySalaryID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testExitButtonExists() {
        ActivityScenario<UpdateJobActivity> scenario = ActivityScenario.launch(UpdateJobActivity.class);
        onView(withId(R.id.exitID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testJobInPopup() {
        // Insert a job into the database in db first
        Context context;
        AppDatabase db;
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = AppDatabase.getInstance(context);
        Job job = new Job("Software Engineer", "Tech Company", "Atlanta", "GA", 100, 120000, 15000, 2000, 15, 2, false);

        db.jobDAO().insertJob(job);

        ActivityScenario<UpdateJobActivity> scenario = ActivityScenario.launch(UpdateJobActivity.class);

        // Click Select Job button
        onView(withId(R.id.selectJobButtonID)).perform(click());

        // Check if the job is listed in the selection popup
        onView(withText("Software Engineer@Tech Company")).check(matches(isDisplayed()));
        onView(withText("Software Engineer@Tech Company")).perform(click());

        onView(withId(R.id.titleID)).check(matches(withText("Software Engineer")));

        scenario.close();
    }

    @Test
    public void testJobUpdatedInDatabase() {
        Context context;
        AppDatabase db;
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = AppDatabase.getInstance(context);
        Job job2 = new Job("UX Designer", "Company 2", "San Francisco", "CA", 100, 80000, 10000, 5000, 20, 3, false);
        long jobId = db.jobDAO().insertJob(job2);

        ActivityScenario<UpdateJobActivity> scenario = ActivityScenario.launch(UpdateJobActivity.class);

        // Select job
        onView(withId(R.id.selectJobButtonID)).perform(click());
        onView(withText("UX Designer@Company 2")).check(matches(isDisplayed()));
        onView(withText("UX Designer@Company 2")).perform(click());
        //onView(withText("Software Engineer@Tech Company")).inRoot(isDialog()).perform(click());
        //onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(click());

        // Update the job's attributes
        onView(withId(R.id.titleID)).perform(replaceText("Senior SDE"));
        onView(withId(R.id.companyID)).perform(replaceText("Tech Company_2"));
        onView(withId(R.id.cityID)).perform(replaceText("San Francisco"));
        onView(withId(R.id.stateID)).perform(replaceText("CA"));
        onView(withId(R.id.costOfLivingID)).perform(replaceText("150"));
        onView(withId(R.id.yearlySalaryID)).perform(replaceText("150000"));
        onView(withId(R.id.yearlyBonusID)).perform(replaceText("20000"));
        onView(withId(R.id.trainingFundID)).perform(replaceText("3000"));
        onView(withId(R.id.leaveTimeID)).perform(replaceText("20"));
        onView(withId(R.id.teleworkDaysID)).perform(replaceText("3"));

        // Click Update
        onView(withId(R.id.updateJobButtonID)).perform(scrollTo(), click());

        // Check that the job is updated in the database
        Job updatedJob = db.jobDAO().getJobByID(jobId);
        assert updatedJob != null;
        assert updatedJob.getTitle().equals("Senior SDE");
        assert updatedJob.getCompany().equals("Tech Company_2");
        assert updatedJob.getCity().equals("San Francisco");
        assert updatedJob.getState().equals("CA");
        assert updatedJob.getCostOfLiving() == 150;
        assert updatedJob.getYearlySalary() == 150000;
        assert updatedJob.getYearlyBonus() == 20000;
        assert updatedJob.getTrainingDevFund() == 3000;
        assert updatedJob.getLeaveTime() == 20;
        assert updatedJob.getTeleworkPerW() == 3;

        scenario.close();
    }

    @Test
    public void testJobDeleted() {
        Context context;
        AppDatabase db;
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = AppDatabase.getInstance(context);
        Job job = new Job("Software Engineer", "Tech Company", "Atlanta", "GA", 100, 120000, 15000, 2000, 15, 2, false);
        long jobId = db.jobDAO().insertJob(job);

        ActivityScenario<UpdateJobActivity> scenario = ActivityScenario.launch(UpdateJobActivity.class);
        // Select job
        onView(withId(R.id.selectJobButtonID)).perform(click());
        onView(withText("Software Engineer@Tech Company")).perform(click());

        // Delete button
        onView(withId(R.id.deleteButtonID)).perform(scrollTo(), click());

        // Check that the job is deleted from the database
        Job deletedJob = db.jobDAO().getJobByID(jobId);
        assert deletedJob == null;

        scenario.close();
    }

    @Test
    public void testBackButton() {
        ActivityScenario<UpdateJobActivity> scenario = ActivityScenario.launch(UpdateJobActivity.class);

        onView(withId(R.id.exitID)).perform(click());
        // Back to Main Page
        onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("JobCompare")));
        scenario.close();
    }
}

