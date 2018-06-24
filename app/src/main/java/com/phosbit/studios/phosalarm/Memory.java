package com.phosbit.studios.phosalarm;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

/**
 * Created by Aaron on 10/31/2017.
 * Last Modified by Aaron on 06/20/2018.
 * Memory Entity
 */

@Entity
public class Memory
{
    @PrimaryKey
    private String mMemoryID;
    @ColumnInfo( name = "title")
    private String mTitle;
    @ColumnInfo( name = "message")
    private String mMessage;

    Memory( String title, String message ) {
        this.mMemoryID = UUID.randomUUID().toString();
        setTitle( title );
        setMessage( message );
    }

    public void setTitle( String title ) {
        this.mTitle = title;
    }

    public void setMessage( String message ) {
        this.mMessage = message;
    }

    public String getID() {
        return this.mMemoryID;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getMessage() {
        return this.mMessage;
    }
}

