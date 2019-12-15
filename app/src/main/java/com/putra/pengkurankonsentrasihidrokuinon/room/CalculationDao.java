package com.putra.pengkurankonsentrasihidrokuinon.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.putra.pengkurankonsentrasihidrokuinon.model.ScanModel;

import java.util.List;

@Dao
public interface CalculationDao {
    @Query("Select * from scanData")
    List<ScanModel> getScanData();

    @Insert
    void insertScanData(ScanModel scanModel);

    @Query("SELECT * FROM scanData WHERE id = :id")
    ScanModel getScanDataById(long id);
}
