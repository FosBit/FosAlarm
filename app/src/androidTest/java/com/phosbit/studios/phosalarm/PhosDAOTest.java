package com.phosbit.studios.phosalarm;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.phosbit.studios.phosalarm.db.Alarm;
import com.phosbit.studios.phosalarm.db.PhosRoomDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PhosDAOTest {
    private PhosRoomDatabase mPhosDb;

    @Before
    public void createDb() {
        /* When you are writing your tests and you want to load a resource of your real app,
         * use getTargetContext(). If you want to use a resource of your test app
         * (e.g. a test input for one of your tests) then call getContext().
         */
        Context context = InstrumentationRegistry.getContext();
        mPhosDb = Room.inMemoryDatabaseBuilder(context, PhosRoomDatabase.class).build();
    }

    @After
    public void closeDb() {
        mPhosDb.close();
    }

    @Test
    public void insertAlarms() {
        Alarm alarm_one = new Alarm( UUID.randomUUID().toString(), 1234, true, "1" );
        Alarm alarm_two = new Alarm( UUID.randomUUID().toString(), 5678, false, "2" );
        mPhosDb.phosDAO().insertAlarms( alarm_one, alarm_two );
        //List<Alarm> alarms = mPhosDb.phosDAO().getAlarms();
        //assert( !alarms.isEmpty() );
    }
}
