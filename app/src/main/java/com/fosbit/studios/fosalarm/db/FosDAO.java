package com.fosbit.studios.fosalarm.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FosDAO {
    //TODO: Add queries that are needed

    @Query( "SELECT * FROM Memory" )
    LiveData<List<Memory>> getMemories();

    @Query( "SELECT * FROM Alarm" )
    LiveData<List<Alarm>> getAlarms();

    @Insert
    void insertAlarms( Alarm... alarms );

    @Insert
    void insertMemories( Memory... memories );

    @Update
    void updateAlarms( Alarm... alarms );

    @Update
    void updateMemories( Memory... memories );

    @Delete
    void deleteAlarms( Alarm... alarms );

    @Delete
    void deleteMemories( Memory... memories );
}