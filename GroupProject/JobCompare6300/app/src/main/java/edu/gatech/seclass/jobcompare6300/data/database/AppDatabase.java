package edu.gatech.seclass.jobcompare6300.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import edu.gatech.seclass.jobcompare6300.data.database.DAO.JobDAO;
import edu.gatech.seclass.jobcompare6300.data.database.DAO.WeightsDAO;
import edu.gatech.seclass.jobcompare6300.data.models.Job;
import edu.gatech.seclass.jobcompare6300.data.models.Weights;

@Database(entities = {Job.class, Weights.class}, version=3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract JobDAO jobDAO();
    public abstract WeightsDAO weightsDAO();
    private static AppDatabase databaseInstance;

    public static synchronized AppDatabase getInstance(Context context) {
        databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "6300database").fallbackToDestructiveMigration()
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        return databaseInstance;
    }
}
