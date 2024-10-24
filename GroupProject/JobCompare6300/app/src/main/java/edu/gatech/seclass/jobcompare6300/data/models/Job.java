package edu.gatech.seclass.jobcompare6300.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Job implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="job_id")
    private long jobId;
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name="company")
    private String company;
    @ColumnInfo(name="city")
    private String city;
    @ColumnInfo(name="state")
    private String state;
    @ColumnInfo(name="cost_of_living")
    private int costOfLiving;
    @ColumnInfo(name="yearly_salary")
    private float yearlySalary;
    @ColumnInfo(name="yearly_bonus")
    private float yearlyBonus;
    @ColumnInfo(name="training_dev_fund")
    private float trainingDevFund;
    @ColumnInfo(name="leave_time")
    private float leaveTime;
    @ColumnInfo(name="telework_per_w")
    private int teleworkPerW;
    @ColumnInfo(name="is_current_job")
    private boolean isCurrentJob;
    @ColumnInfo(name="score")
    private float score;

    public Job(String title, String company, String city, String state, int costOfLiving, float yearlySalary, float yearlyBonus, float trainingDevFund, float leaveTime, int teleworkPerW, boolean isCurrentJob) {
        this.title = title;
        this.company = company;
        this.city = city;
        this.state = state;
        this.costOfLiving = costOfLiving;
        this.yearlySalary = yearlySalary;
        this.yearlyBonus = yearlyBonus;
        this.trainingDevFund = trainingDevFund;
        this.leaveTime = leaveTime;
        this.teleworkPerW = teleworkPerW;
        this.isCurrentJob = isCurrentJob;
        this.score = 0;
    }

    public Job() {

    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCostOfLiving() {
        return costOfLiving;
    }

    public void setCostOfLiving(int costOfLiving) {
        this.costOfLiving = costOfLiving;
    }

    public float getYearlySalary() {
        return yearlySalary;
    }

    public void setYearlySalary(float yearlySalary) {
        this.yearlySalary = yearlySalary;
    }

    public float getYearlyBonus() {
        return yearlyBonus;
    }

    public void setYearlyBonus(float yearlyBonus) {
        this.yearlyBonus = yearlyBonus;
    }

    public float getTrainingDevFund() {
        return trainingDevFund;
    }

    public void setTrainingDevFund(float trainingDevFund) {
        this.trainingDevFund = trainingDevFund;
    }

    public float getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(float leaveTime) {
        this.leaveTime = leaveTime;
    }

    public int getTeleworkPerW() {
        return teleworkPerW;
    }

    public void setTeleworkPerW(int teleworkPerW) {
        this.teleworkPerW = teleworkPerW;
    }

    public boolean isCurrentJob() {
        return isCurrentJob;
    }

    public void setCurrentJob(boolean currentJob) {
        isCurrentJob = currentJob;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float adjustYearlySalary() {
        return this.yearlySalary * 100 / (float) this.costOfLiving;
    }

    public float adjustYearlyBonus() {
        return this.yearlyBonus * 100 / (float) this.costOfLiving;
    }
}
