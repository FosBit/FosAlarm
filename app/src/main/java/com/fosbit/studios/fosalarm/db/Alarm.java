package com.fosbit.studios.fosalarm.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Aaron on 10/31/2017.
 * Last Modified by Aaron on 06/21/2018
 * Alarm Entity
 */

@Entity
public class Alarm {
    @PrimaryKey
    @NonNull
    @ColumnInfo( name = "alarm_id" )
    private String alarmID;
    @ColumnInfo( name = "time" )
    private long timeOfDay;
    @ColumnInfo( name = "isSet" )
    private boolean status;
    @ColumnInfo( name = "memory_id" )
    private String memoryID;

    public Alarm( String alarmID, long timeOfDay, boolean status, String memoryID ) {

        setAlarmID( alarmID );
        setTimeOfDay( timeOfDay );
        setStatus( status );
        setMemoryID( memoryID );
    }

    private void setAlarmID( String alarmID ) {
        this.alarmID = alarmID;
    }

    public void setTimeOfDay( long timeOfDay ) {
        this.timeOfDay = timeOfDay;
    }

    public void setStatus( boolean status ) {
        this.status = status;
    }

    public void setMemoryID( String memoryID ) {
        this.memoryID = memoryID;
    }

    public String getAlarmID() {
        return this.alarmID;
    }

    public long getTimeOfDay() {
        return this.timeOfDay;
    }

    public boolean getStatus() {
        return this.status;
    }

    public String getMemoryID() {
        return this.memoryID;
    }
}
