package com.phosbit.studios.phosalarm;

/**
 * Created by Aaron on 10/31/2017.
 */

public class Alarm
{
    private int hourOfDay;
    private int minute;
    private boolean isSet;
    private Memory memory;

    Alarm( int hour, int minute, Memory memory, boolean isSet )
    {
        this.hourOfDay = hour;
        this.minute = minute;
        this.memory = memory;
        this.isSet = isSet;
    }

    public void setAlarm( boolean isSet )
    {
        this.isSet = isSet;
    }

    public void setTime( int hour, int minute )
    {
        this.hourOfDay = hour;
        this.minute = minute;
    }

    public void setMemory( Memory memory )
    {
        this.memory = memory;
    }

    public boolean getStatus()
    {
        return this.isSet;
    }

    public int getHourOfDay()
    {
        return this.hourOfDay;
    }

    public int getMinute()
    {
        return this.minute;
    }

    public com.phosbit.studios.phosalarm.Memory getMemory()
    {
        return this.memory;
    }
}
