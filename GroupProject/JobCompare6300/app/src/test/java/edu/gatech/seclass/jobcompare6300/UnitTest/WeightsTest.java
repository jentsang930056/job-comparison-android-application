package edu.gatech.seclass.jobcompare6300.UnitTest;


import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import edu.gatech.seclass.jobcompare6300.data.models.Weights;

public class WeightsTest {

    private Weights weights;

    @Before
    public void setUp() {
        weights = new Weights();
    }

    @Test
    public void testDefaultW() {
        Weights weights = new Weights();
        assertEquals(1, weights.getYearlySalaryWeight());
        assertEquals(1, weights.getYearlyBonusWeight());
        assertEquals(1, weights.getTrainingFundWeight());
        assertEquals(1, weights.getLeaveTimeWeight());
        assertEquals(1, weights.getTeleworkPerWWeight());
    }

    @Test
    public void testUpdateW() {
        weights.updateWeights(2, 4, 6, 8, 2);

        assertEquals(2, weights.getYearlySalaryWeight() );
        assertEquals(4, weights.getYearlyBonusWeight());
        assertEquals(6, weights.getTrainingFundWeight());
        assertEquals(8, weights.getLeaveTimeWeight() );
        assertEquals(2, weights.getTeleworkPerWWeight());
    }

    @Test
    public void testSetAndGetYearlySalaryWeight() {
        Weights weights = new Weights();
        weights.setYearlySalaryWeight(5);
        assertEquals(5, weights.getYearlySalaryWeight());
    }

    @Test
    public void testYearlyBonusW() {
        Weights weights = new Weights();
        weights.setYearlyBonusWeight(7);
        assertEquals(7, weights.getYearlyBonusWeight());
    }

    @Test
    public void testTrainingFundW() {
        Weights weights = new Weights();
        weights.setTrainingFundWeight(3);
        assertEquals(3, weights.getTrainingFundWeight());
    }

    @Test
    public void testLeaveTimeW() {
        Weights weights = new Weights();
        weights.setLeaveTimeWeight(9);
        assertEquals(9, weights.getLeaveTimeWeight());
    }

    @Test
    public void testTeleworkPerWorkdayWW() {
        Weights weights = new Weights();
        weights.setTeleworkPerWWeight(4);
        assertEquals(4, weights.getTeleworkPerWWeight());
    }
}
