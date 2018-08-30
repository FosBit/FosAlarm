package com.phosbit.studios.phosalarm.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Aaron on 10/31/2017.
 * Last Modified by Aaron on 06/20/2018.
 * Memory Entity
 */

@Entity
public class Memory
{
    @PrimaryKey
    @NonNull
    @ColumnInfo( name = "memory_id")
    private String memoryID;
    @ColumnInfo( name = "title")
    private String title;
    @ColumnInfo( name = "message")
    private String message;

    public Memory( String memoryID, String title, String message ) {
        setMemoryID( memoryID );
        setTitle( title );
        setMessage( message );
    }

    private void setMemoryID( String memoryID ) {
        this.memoryID = memoryID;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getMemoryID() {
        return this.memoryID;
    }

    public String getTitle() {
        return this.title;
    }

    public String getMessage() {
        return this.message;
    }
}

