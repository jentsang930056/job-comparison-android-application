package edu.gatech.seclass.jobcompare6300.data.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import edu.gatech.seclass.jobcompare6300.data.models.Weights;
@Dao
public interface WeightsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertWeights(Weights weights);
    @Query("SELECT * FROM weights WHERE weight_id = 1 LIMIT 1")
    public Weights getWeights();
}
