package com.phosbit.studios.phosalarm.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

// Annotation that declares the entities that belong in the database
// and set the version number. Listing the entities will create tables
// in the database.
@Database( entities = { Alarm.class, Memory.class }, version = 1 )
public abstract class PhosRoomDatabase extends RoomDatabase {
    // "getter" method for DAO
    public abstract PhosDAO phosDAO();

    // Make PhosRoomDatabase a singleton to prevent having multiple instances of the
    // database opened at the same time.
    private static PhosRoomDatabase INSTANCE;

    public static PhosRoomDatabase getDatabase( final Context context ) {
        if ( INSTANCE == null ) {
            // synchronized; only one thread can execute at a time, other threads are blocked
            synchronized ( PhosRoomDatabase.class ) {
                if ( INSTANCE == null ) {
                    // This code uses Room's database builder to create a RoomDatabase object
                    // in the application context from the PhosRoomDatabase class named
                    // "phos_database"
                    INSTANCE = Room.databaseBuilder( context.getApplicationContext(),
                               PhosRoomDatabase.class, "phos_database" ).build();
                }
            }
        }
        return INSTANCE;
    }

}
