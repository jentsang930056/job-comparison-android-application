package edu.gatech.seclass.jobcompare6300.UnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.seclass.jobcompare6300.data.models.Job;

public class JobTest {

    private Job job;

    @Before
    public void setUp() {
        job = new Job("Software Engineer", "Test Company", "Atlanta", "GA",
                100, 100000, 10000, 5000, 20, 3, false);
    }

    @Test
    public void testTitle() {
        job.setTitle("Senior Engineer");
        assertEquals("Senior Engineer", job.getTitle());
    }

    @Test
    public void testCompany() {
        job.setCompany("New Company");
        assertEquals("New Company", job.getCompany());
    }

    @Test
    public void testCity() {
        job.setCity("New York");
        assertEquals("New York", job.getCity());
    }

    @Test
    public void testState() {
        job.setState("NY");
        assertEquals("NY", job.getState());
    }

    @Test
    public void testCostOfLiving() {
        job.setCostOfLiving(120);
        assertEquals(120, job.getCostOfLiving());
    }

    @Test
    public void testYearlySalary() {
        job.setYearlySalary(110000);
        assertEquals(110000, job.getYearlySalary(), 0.0001);
    }

    @Test
    public void testYearlyBonus() {
        job.setYearlyBonus(12000);
        assertEquals(12000, job.getYearlyBonus(), 0.0001);
    }

    @Test
    public void testGTrainingFund() {
        job.setTrainingDevFund(6000);
        assertEquals(6000, job.getTrainingDevFund(), 0.0001);
    }

    @Test
    public void testLeaveTime() {
        job.setLeaveTime(25);
        assertEquals(25, job.getLeaveTime(), 0.0001);
    }

    @Test
    public void testTeleworkPerW() {
        job.setTeleworkPerW(4);
        assertEquals(4, job.getTeleworkPerW());
    }

    @Test
    public void testIsCurrentJob() {
        job.setCurrentJob(true);
        assertTrue(job.isCurrentJob());
    }

    @Test
    public void testScore() {
        job.setScore(150.0f);
        assertEquals(150.0f, job.getScore(),0.0001);
    }

    @Test
    public void testAdjustYearlySalary() {
        float adjustedSalary = job.adjustYearlySalary();
        assertEquals(100000, adjustedSalary, 0.0001);
    }

    @Test
    public void testAdjustYearlyBonus() {
        float adjustedBonus = job.adjustYearlyBonus();
        assertEquals(10000, adjustedBonus,0.0001);
    }

}
