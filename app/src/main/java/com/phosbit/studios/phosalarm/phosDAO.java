package com.phosbit.studios.phosalarm;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface phosDAO {
    //TODO: Add queries that are needed

    @Insert
    public void insertAlarm( Alarm alarm );

    @Insert
    public void insertAlarms( Alarm[] alarms );

    @Insert
    public void insertMemory( Memory memory );

    @Insert
    public void insertAlarms( Memory[] memories );

    @Update
    public void updateAlarm( Alarm alarm );

    @Update
    public void updateMemory( Memory memory );

    @Delete
    public void deleteAlarm( Alarm alarm );

    @Delete
    public void deleteMemory( Memory memory );
}