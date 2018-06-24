package com.phosbit.studios.phosalarm;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

/**
 * Created by Aaron on 10/31/2017.
 * Last Modified by Aaron on 06/21/2018
 * Alarm Entity
 */

@Entity
public class Alarm
{
    @PrimaryKey
    private String mAlarmID;
    @ColumnInfo( name = "time" )
    private int mTimeOfDay;
    @ColumnInfo( name = "isSet" )
    private boolean mIsSet;
    @ColumnInfo( name = "memoryid" )
    private String mMemoryID;

    Alarm( int timeOfDay, boolean isSet ) {
        this.mAlarmID = UUID.randomUUID().toString();
        setTime( timeOfDay );
        setAlarm( isSet );
    }

    public void setAlarm( boolean isSet ) {
        this.mIsSet = isSet;
    }

    public void setTime( int timeOfDay ) {
        this.mTimeOfDay = timeOfDay;
    }

    public void setMemory( String memoryID )
    {
        this.mMemoryID = memoryID;
    }

    public String getID() {
        return this.mAlarmID;
    }

    public boolean getStatus()
    {
        return this.mIsSet;
    }

    public int getTime()
    {
        return this.mTimeOfDay;
    }

    public String getMemoryID()
    {
        return this.mMemoryID;
    }
}
