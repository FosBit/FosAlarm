package com.phosbit.studios.phosalarm.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PhosDAO {
    //TODO: Add queries that are needed

    @Query("SELECT * FROM Memory")
    public LiveData<List<Memory>> getMemories();

    @Query("SELECT * FROM Alarm")
    public LiveData<List<Alarm>> getAlarms();

    @Insert
    public void insertAlarms( Alarm... alarms );

    @Insert
    public void insertMemories( Memory... memories );

    @Update
    public void updateAlarms( Alarm... alarms );

    @Update
    public void updateMemories( Memory... memories );

    @Delete
    public void deleteAlarms(  Alarm... alarms  );

    @Delete
    public void deleteMemories( Memory... memories );
}