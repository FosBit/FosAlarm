package com.fosbit.studios.fosalarm.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

// Annotation that declares the entities that belong in the database
// and set the version number. Listing the entities will create tables
// in the database.
@Database( entities = { Alarm.class, Memory.class }, version = 1 )
public abstract class FosRoomDatabase extends RoomDatabase {
    // "getter" method for DAO
    public abstract FosDAO phosDAO();

    // Make FosRoomDatabase a singleton to prevent having multiple instances of the
    // database opened at the same time.
    private static FosRoomDatabase INSTANCE;

    public static FosRoomDatabase getDatabase(final Context context ) {
        if ( INSTANCE == null ) {
            // synchronized; only one thread can execute at a time, other threads are blocked
            synchronized ( FosRoomDatabase.class ) {
                if ( INSTANCE == null ) {
                    // This code uses Room's database builder to create a RoomDatabase object
                    // in the application context from the FosRoomDatabase class named
                    // "phos_database"
                    INSTANCE = Room.databaseBuilder( context.getApplicationContext(),
                               FosRoomDatabase.class, "phos_database" ).build();
                }
            }
        }
        return INSTANCE;
    }

}
