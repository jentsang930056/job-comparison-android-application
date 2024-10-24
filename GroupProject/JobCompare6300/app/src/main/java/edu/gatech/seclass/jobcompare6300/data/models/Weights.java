package edu.gatech.seclass.jobcompare6300.data.models;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.Entity;
@Entity(tableName = "weights")
public class Weights {
    @PrimaryKey
    @ColumnInfo(name="weight_id")
    private int weightId;
    @ColumnInfo(name="yearly_salary_weight")
    private int yearlySalaryWeight = 1;
    @ColumnInfo(name="yearly_bonus_weight")
    private int yearlyBonusWeight = 1;
    @ColumnInfo(name="training_fund_weight")
    private int trainingFundWeight = 1;
    @ColumnInfo(name="leave_time_weight")
    private int leaveTimeWeight = 1;
    @ColumnInfo(name="telework_per_w_weight")
    private int teleworkPerWWeight = 1;

    public Weights() {
        this.weightId = 1;
    }

    public void updateWeights(int yearlySalaryWeight, int yearlyBonusWeight, int trainingFundWeight, int leaveTimeWeight, int teleworkPerWWeight) {
        this.yearlySalaryWeight = yearlySalaryWeight;
        this.yearlyBonusWeight = yearlyBonusWeight;
        this.trainingFundWeight = trainingFundWeight;
        this.leaveTimeWeight = leaveTimeWeight;
        this.teleworkPerWWeight = teleworkPerWWeight;
    }

    public int getWeightId() {
        return weightId;
    }

    public int getYearlySalaryWeight() {
        return yearlySalaryWeight;
    }

    public int getYearlyBonusWeight() {
        return yearlyBonusWeight;
    }

    public int getTrainingFundWeight() {
        return trainingFundWeight;
    }

    public int getLeaveTimeWeight() {
        return leaveTimeWeight;
    }

    public int getTeleworkPerWWeight() {
        return teleworkPerWWeight;
    }

    public void setWeightId(int weightId) {
        this.weightId = weightId;
    }

    public void setYearlySalaryWeight(int yearlySalaryWeight) {
        this.yearlySalaryWeight = yearlySalaryWeight;
    }

    public void setYearlyBonusWeight(int yearlyBonusWeight) {
        this.yearlyBonusWeight = yearlyBonusWeight;
    }

    public void setTrainingFundWeight(int trainingFundWeight) {
        this.trainingFundWeight = trainingFundWeight;
    }

    public void setLeaveTimeWeight(int leaveTimeWeight) {
        this.leaveTimeWeight = leaveTimeWeight;
    }

    public void setTeleworkPerWWeight(int teleworkPerWWeight) {
        this.teleworkPerWWeight = teleworkPerWWeight;
    }
}