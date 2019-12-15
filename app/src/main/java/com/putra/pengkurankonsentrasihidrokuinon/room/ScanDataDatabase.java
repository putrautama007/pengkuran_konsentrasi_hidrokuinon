package com.putra.pengkurankonsentrasihidrokuinon.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.putra.pengkurankonsentrasihidrokuinon.model.ScanModel;

@Database(entities = {ScanModel.class}, exportSchema = false, version = 1)
public abstract class ScanDataDatabase extends RoomDatabase {
    private static final String DB_NAME = "concentration_db";
    private static final Object LOCK = new Object();
    private static ScanDataDatabase instance;

    public static synchronized ScanDataDatabase getInstance(Context context){
        if (instance == null){
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(), ScanDataDatabase.class,
                        DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return instance;
    }

    public abstract CalculationDao calculationDao();
}
