package com.putra.pengkurankonsentrasihidrokuinon.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.putra.pengkurankonsentrasihidrokuinon.model.ScanModel;

import java.util.List;


// class yang digunakan untuk melakukan fungsi fungsi pada roomdb
@Dao
public interface CalculationDao {

    // fungsi yang digunakan untuk mendapatkan semua data yang sudah disimpan pada roomdb
    @Query("Select * from scanData")
    List<ScanModel> getScanData();

    // fungsi yang digunakan untuk menyimpan data pada roomdb
    @Insert
    void insertScanData(ScanModel scanModel);

    // fungsi yang digunakan untuk mendapatkan semua data yang sudah disimpan pada roomdb berdasarkan idnya
    @Query("SELECT * FROM scanData WHERE id = :id")
    ScanModel getScanDataById(long id);
}
