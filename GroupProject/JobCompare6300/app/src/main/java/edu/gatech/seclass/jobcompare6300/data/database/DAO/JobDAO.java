package edu.gatech.seclass.jobcompare6300.data.database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.gatech.seclass.jobcompare6300.data.models.Job;

@Dao
public interface JobDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertJob(Job job);
    @Update()
    public void updateJob(Job job);
    @Delete
    public void deleteJob(Job job);
    @Query("Select * from Job")
    public List<Job> getAll();
    @Query("Select * from Job where job_id = :id")
    public Job getJobByID(long id);
    @Query("SELECT * FROM Job WHERE is_current_job = 1 LIMIT 1")
    public Job getCurrentJob();
    @Query("SELECT * FROM Job ORDER BY score DESC")
    public List<Job> getAllJobsSortedByScore();
}
