package edu.gatech.seclass.jobcompare6300.UnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.gatech.seclass.jobcompare6300.data.database.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.database.DAO.WeightsDAO;
import edu.gatech.seclass.jobcompare6300.data.models.Weights;

@RunWith(AndroidJUnit4.class)
public class WeightsDatabaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase database;
    private WeightsDAO weightsDAO;

    @Before
    public void setUp() {
        database = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(), AppDatabase.class).allowMainThreadQueries().build();
        weightsDAO = database.weightsDAO();
    }

    @After
    public void tearDown() {
        database.close();
    }

    @Test
    public void testInsertWeights() throws InterruptedException {
        Weights weights = new Weights();
        weights.updateWeights(2, 4, 6, 8, 2);

        // Add the jobs to the database
        weightsDAO.insertWeights(weights);
        Weights retrievedWeights = weightsDAO.getWeights();
        assertNotNull(retrievedWeights);
        assertEquals(2, retrievedWeights.getYearlySalaryWeight());
        assertEquals(4, retrievedWeights.getYearlyBonusWeight());
        assertEquals(6, retrievedWeights.getTrainingFundWeight());
        assertEquals(8, retrievedWeights.getLeaveTimeWeight());
        assertEquals(2, retrievedWeights.getTeleworkPerWWeight());
    }

    @Test
    public void testUpdateWeights() throws InterruptedException {
        Weights weights = new Weights();
        weights.updateWeights(2, 4, 6, 8, 2);
        weightsDAO.insertWeights(weights);

        // Update the weights
        weights.updateWeights(1, 2, 3, 4, 5);
        weightsDAO.insertWeights(weights);

        Weights retrievedWeights = weightsDAO.getWeights();
        assertNotNull(retrievedWeights);
        assertEquals(1, retrievedWeights.getYearlySalaryWeight());
        assertEquals(2, retrievedWeights.getYearlyBonusWeight());
        assertEquals(3, retrievedWeights.getTrainingFundWeight());
        assertEquals(4, retrievedWeights.getLeaveTimeWeight());
        assertEquals(5, retrievedWeights.getTeleworkPerWWeight());
    }
}

