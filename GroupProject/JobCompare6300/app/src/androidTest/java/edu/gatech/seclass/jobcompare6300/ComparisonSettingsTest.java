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
import edu.gatech.seclass.jobcompare6300.data.models.Weights;
import edu.gatech.seclass.jobcompare6300.ui.SettingActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

@RunWith(AndroidJUnit4.class)
public class ComparisonSettingsTest {
    AppDatabase db;
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = AppDatabase.getInstance(context);
        db.clearAllTables();  // Clear all data before each test
    }

    @Test
    public void testCorrectCompSetting() {
        ActivityScenario<SettingActivity> scenario = ActivityScenario.launch(SettingActivity.class);
        onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("Adjusting The Comparison Settings")));
        scenario.close();
    }

    @Test
    public void testExitButtonExists() {
        ActivityScenario<SettingActivity> scenario = ActivityScenario.launch(SettingActivity.class);
        onView(withId(R.id.exitID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testYearlySalaryWExists() {
        ActivityScenario<SettingActivity> scenario = ActivityScenario.launch(SettingActivity.class);
        onView(withId(R.id.yearlySalaryWID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testYearlyBonusWExists() {
        ActivityScenario<SettingActivity> scenario = ActivityScenario.launch(SettingActivity.class);
        onView(withId(R.id.yearlyBonusWID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testTrainingFundWExists() {
        ActivityScenario<SettingActivity> scenario = ActivityScenario.launch(SettingActivity.class);
        onView(withId(R.id.trainingFundWID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testLeaveTimeWExists() {
        ActivityScenario<SettingActivity> scenario = ActivityScenario.launch(SettingActivity.class);
        onView(withId(R.id.leaveTimeWID)).check(matches(isDisplayed()));
        scenario.close();
    }

    @Test
    public void testTeleworkDaysWExists() {
        ActivityScenario<SettingActivity> scenario = ActivityScenario.launch(SettingActivity.class);
        onView(withId(R.id.teleworkDaysWID)).check(matches(isDisplayed()));
        scenario.close();
    }


    @Test
    public void testWeightsAreSaved() {
        Context context;
        AppDatabase db;
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = AppDatabase.getInstance(context);
        //(reopen the page, the weights data should be there)
        ActivityScenario<SettingActivity> scenario = ActivityScenario.launch(SettingActivity.class);

        // Input values
        onView(withId(R.id.yearlySalaryWID)).perform(replaceText("9"));
        onView(withId(R.id.yearlyBonusWID)).perform(replaceText("5"));
        onView(withId(R.id.trainingFundWID)).perform(replaceText("3"));
        onView(withId(R.id.leaveTimeWID)).perform(replaceText("2"));
        onView(withId(R.id.teleworkDaysWID)).perform(replaceText("1"));

        // Click Save button
        onView(withId(R.id.saveButtonID)).perform(click());

        // reopen
        scenario.close();
        scenario = ActivityScenario.launch(SettingActivity.class);

        Weights weights = db.weightsDAO().getWeights();
        assert weights != null;
        assert weights.getYearlySalaryWeight() == 9;
        assert weights.getYearlyBonusWeight() == 5;
        assert weights.getTrainingFundWeight() == 3;
        assert weights.getLeaveTimeWeight() == 2;
        assert weights.getTeleworkPerWWeight() == 1;

        scenario.close();
    }

    @Test
    public void testUpdateWeights() {
        Context context;
        AppDatabase db;
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = AppDatabase.getInstance(context);
        Weights weights = new Weights();
        weights.setYearlySalaryWeight(7);
        weights.setYearlyBonusWeight(6);
        weights.setTrainingFundWeight(5);
        weights.setLeaveTimeWeight(4);
        weights.setTeleworkPerWWeight(3);
        db.weightsDAO().insertWeights(weights);

        ActivityScenario<SettingActivity> scenario = ActivityScenario.launch(SettingActivity.class);

        // Input values
        onView(withId(R.id.yearlySalaryWID)).perform(replaceText("9"));
        onView(withId(R.id.yearlyBonusWID)).perform(replaceText("5"));
        onView(withId(R.id.trainingFundWID)).perform(replaceText("3"));
        onView(withId(R.id.leaveTimeWID)).perform(replaceText("2"));
        onView(withId(R.id.teleworkDaysWID)).perform(replaceText("1"));

        // Click Save button
        onView(withId(R.id.saveButtonID)).perform(click());

        Weights weights_new = db.weightsDAO().getWeights();
        assert weights_new != null;
        assert weights_new.getYearlySalaryWeight() == 9;
        assert weights_new.getYearlyBonusWeight() == 5;
        assert weights_new.getTrainingFundWeight() == 3;
        assert weights_new.getLeaveTimeWeight() == 2;
        assert weights_new.getTeleworkPerWWeight() == 1;

        scenario.close();
    }

    @Test
    public void testInput_1() {
        ActivityScenario<SettingActivity> scenario = ActivityScenario.launch(SettingActivity.class);

        // Input values into the fields
        onView(withId(R.id.yearlySalaryWID)).perform(replaceText("8"));
        onView(withId(R.id.yearlyBonusWID)).perform(replaceText("6"));
        onView(withId(R.id.trainingFundWID)).perform(replaceText("4"));
        onView(withId(R.id.leaveTimeWID)).perform(replaceText("3"));
        onView(withId(R.id.teleworkDaysWID)).perform(replaceText("2"));

        // Check that the values are displayed correctly
        onView(withId(R.id.yearlySalaryWID)).check(matches(withText("8")));
        onView(withId(R.id.yearlyBonusWID)).check(matches(withText("6")));
        onView(withId(R.id.trainingFundWID)).check(matches(withText("4")));
        onView(withId(R.id.leaveTimeWID)).check(matches(withText("3")));
        onView(withId(R.id.teleworkDaysWID)).check(matches(withText("2")));

        scenario.close();
    }

    @Test
    public void testInput_2() {
        ActivityScenario<SettingActivity> scenario = ActivityScenario.launch(SettingActivity.class);

        // Input invalid values into the fields
        onView(withId(R.id.yearlySalaryWID)).perform(replaceText("8a"));
        onView(withId(R.id.yearlyBonusWID)).perform(replaceText("6$"));
        onView(withId(R.id.trainingFundWID)).perform(replaceText("4b"));
        onView(withId(R.id.leaveTimeWID)).perform(replaceText("#"));
        onView(withId(R.id.teleworkDaysWID)).perform(replaceText("10"));

        // Attempt to save and ensure the save does not proceed due to error, and still on the settings page
        onView(withId(R.id.saveButtonID)).perform(click());
        onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("Adjusting The Comparison Settings")));

        scenario.close();
    }

    @Test
    public void testBackButton() {
        ActivityScenario<SettingActivity> scenario = ActivityScenario.launch(SettingActivity.class);

        onView(withId(R.id.exitID)).perform(click());
        // Back to Main Page
        onView(ViewMatchers.withId(R.id.textView1)).check(matches(withText("JobCompare")));
        scenario.close();
    }

}

