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
import edu.gatech.seclass.jobcompare6300.ui.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    AppDatabase db;
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = AppDatabase.getInstance(context);
        db.clearAllTables();  // Clear all data before each test
    }

    @Test
    public void testEnterJobEixsts() {

        //Launch the MainActivity
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        // Verify main menu options are displayed
        onView(withId(R.id.enterButtonID)).check(matches(isDisplayed()));

        // Close the scenario
        scenario.close();
    }

    @Test
    public void testUpdateJobEixsts() {
        // Launch the MainActivity
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        // Verify main menu options are displayed
        onView(withId(R.id.updateJobButtonID)).check(matches(isDisplayed()));

        // Close the scenario
        scenario.close();
    }

    @Test
    public void testCompSettingsExists() {
        // Launch the MainActivity
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        // Verify main menu options are displayed
        onView(withId(R.id.settingButtonID)).check(matches(isDisplayed()));

        // Close the scenario
        scenario.close();
    }

    @Test
    public void testCompareJobExists() {
        // Launch the MainActivity
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        // Verify main menu options are displayed
        onView(withId(R.id.compareButtonID)).check(matches(isDisplayed()));

        // Close the scenario
        scenario.close();
    }

    @Test
    public void testNavToEnterJob() {
        // Launch the MainActivity
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        // Click the Enter Current Job Details button and verify navigation
        onView(withId(R.id.enterButtonID)).perform(click());
        onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("Enter Job Details")));

        // Close the scenario
        scenario.close();
    }

    @Test
    public void testNavToUpdateJob() {
        Context context;
        long job1;
        long job2;

        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // Insert a mock job into the database
        Job mockJob = new Job("Software Engineer", "Tech Company", "Atlanta", "GA", 100, 120000, 15000, 2000, 15, 2, false);

        job1 = db.jobDAO().insertJob(mockJob);
        job2 = db.jobDAO().insertJob(mockJob);
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.updateJobButtonID)).perform(click());
        onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("Update Job Details")));

        Job fetchedJob1 = db.jobDAO().getJobByID(job1);
        Job fetchedJob2 = db.jobDAO().getJobByID(job2);

        db.jobDAO().deleteJob(fetchedJob1);
        db.jobDAO().deleteJob(fetchedJob2);
        scenario.close();
        scenario = ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.updateJobButtonID)).perform(click());
        onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("JobCompare")));

        scenario.close();
    }

    @Test
    public void testNavToCompareJob() {
        Context context;
        long job_1;
        long job_2;

        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // Insert a mock job into the database
        Job job1 = new Job("Software Engineer1", "Tech Company", "Atlanta", "GA", 100, 120000, 15000, 2000, 15, 2, false);
        Job job2 = new Job("Software Engineer2", "Tech Company", "Atlanta", "GA", 100, 120000, 15000, 2000, 15, 2, false);

        job_1 = db.jobDAO().insertJob(job1);
        job_2 = db.jobDAO().insertJob(job2);

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.compareButtonID)).perform(click());
        onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("Compare Job Offers")));

        Job fetchedJob1 = db.jobDAO().getJobByID(job_1);
        Job fetchedJob2 = db.jobDAO().getJobByID(job_2);

        db.jobDAO().deleteJob(fetchedJob1);
        db.jobDAO().deleteJob(fetchedJob2);

        scenario = ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.compareButtonID)).perform(click());
        onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("JobCompare")));

        scenario.close();
    }
}
