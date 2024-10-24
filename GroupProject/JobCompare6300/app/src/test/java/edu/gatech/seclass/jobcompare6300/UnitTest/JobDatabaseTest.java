package edu.gatech.seclass.jobcompare6300.UnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.data.database.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.database.DAO.JobDAO;
import edu.gatech.seclass.jobcompare6300.data.models.Job;

@RunWith(AndroidJUnit4.class)
public class JobDatabaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private AppDatabase database;
    private JobDAO jobDAO;

    @Before
    public void setUp() {
        database = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(), AppDatabase.class).allowMainThreadQueries().build();
        jobDAO = database.jobDAO();
    }

    @After
    public void tearDown() {
        database.close();
    }

    @Test
    public void testInsertJob() {
        Job job1 = new Job("Software Engineer", "Company 1", "Atlanta", "GA", 90, 100000, 10000, 5000, 20, 3, false);
        Job job2 = new Job("UX Designer", "Company 2", "San Francisco", "CA", 100, 80000, 10000, 5000, 20, 3, false);

        // Add the jobs to the database
        jobDAO.insertJob(job1);
        jobDAO.insertJob(job2);
        List<Job> jobs = jobDAO.getAll();
        assertNotNull(jobs);
        assertEquals("Software Engineer", jobs.get(0).getTitle());
        assertEquals("UX Designer", jobs.get(1).getTitle());
        assertEquals(2, jobs.size());
    }

    @Test
    public void testUpdateJob() {
        Job job = new Job("UX Designer", "Company 2", "San Francisco", "CA", 100, 80000, 10000, 5000, 20, 3, false);
        job.setJobId(jobDAO.insertJob(job));

        // Update the job details
        job.setTitle("Senior UX Designer");
        jobDAO.updateJob(job);

        List<Job> jobs = jobDAO.getAll();
        assertNotNull(jobs);
        assertEquals("Senior UX Designer", jobs.get(0).getTitle());
        assertEquals(1, jobs.size());
    }

    @Test
    public void testDeleteJob() {
        Job job = new Job("UX Designer", "Company 2", "San Francisco", "CA", 100, 80000, 10000, 5000, 20, 3, false);
        job.setJobId(jobDAO.insertJob(job));

        // Delete the job from the database
        jobDAO.deleteJob(job);

        List<Job> jobs = jobDAO.getAll();
        assertNotNull(jobs);
        assertEquals(0, jobs.size());
    }

    @Test
    public void testGetJobByID() {
        Job job = new Job("Product Manager", "Company 3", "New York", "NY", 120, 110000, 20000, 7000, 25, 4, false);
        long jobId = jobDAO.insertJob(job);

        Job retrievedJob = jobDAO.getJobByID(jobId);
        assertNotNull(retrievedJob);
        assertEquals("Product Manager", retrievedJob.getTitle());
    }

    @Test
    public void testGetCurrentJob() {
        Job job1 = new Job("Data Analyst", "Company 4", "Seattle", "WA", 110, 115000, 15000, 6000, 22, 2, false);
        Job job2 = new Job("Data Engineer", "Company 5", "Seattle", "WA", 105, 125000, 18000, 8000, 20, 3, true);
        jobDAO.insertJob(job1);
        jobDAO.insertJob(job2);

        Job currentJob = jobDAO.getCurrentJob();
        assertNotNull(currentJob);
        assertEquals("Data Engineer", currentJob.getTitle());
    }

    @Test
    public void testGetAllJobsSorted() {
        Job job1 = new Job("QA", "Company 6", "LA", "CA", 95, 90000, 10000, 4000, 15, 1, false);
        Job job2 = new Job("DevOps Engineer", "Company 7", "New York", "NY", 100, 105000, 12000, 5000, 18, 2, false);
        job1.setScore(80);
        job2.setScore(90);
        jobDAO.insertJob(job1);
        jobDAO.insertJob(job2);

        List<Job> jobs = jobDAO.getAllJobsSortedByScore();
        assertNotNull(jobs);
        assertEquals("DevOps Engineer", jobs.get(0).getTitle());
        assertEquals("QA", jobs.get(1).getTitle());
    }
}