package com.phosbit.studios.phosalarm.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class PhosRepositiory {
    private PhosDAO mPhosDao;
    private LiveData<List<Alarm>> mAlarms;
    private LiveData<List<Memory>> mMemories;

    // Constructor
    PhosRepositiory( Application application ) {
        PhosRoomDatabase db = PhosRoomDatabase.getDatabase( application );
        mPhosDao = db.phosDAO();
        mAlarms = mPhosDao.getAlarms();
        mMemories = mPhosDao.getMemories();
    }

    LiveData<List<Alarm>> getAlarms() {
        return mAlarms;
    }

    LiveData<List<Memory>> getMemories() {
        return mMemories;
    }

    public void insertAlarms( final Alarm... alarms ) {
        new insertAlarmsAsync( mPhosDao ).execute( alarms );
    }

    public void insertMemories( final Memory... memories ) {
        new insertMemoriesAsync( mPhosDao ).execute( memories );
    }

    public void updateAlarms( final Alarm... alarms ) {
        new updateAlarmsAsync( mPhosDao ).execute( alarms );
    }

    public void updateMemories( final Memory... memories ) {
        new updateMemoriesAsync( mPhosDao ).execute( memories );
    }

    public void deleteAlarms( final Alarm... alarms ) {
        new deleteAlarmsAsync( mPhosDao ).execute( alarms );
    }

    public void deleteMemories( final Memory... memories) {
        new deleteMemoriesAsync( mPhosDao ).execute( memories );
    }

    public static class insertAlarmsAsync extends AsyncTask<Alarm, Void, Void> {
        private PhosDAO mAsyncTaskDao;

        insertAlarmsAsync( PhosDAO dao ) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground( final Alarm... alarms ) {
            mAsyncTaskDao.insertAlarms( alarms );
            return null;
        }
    }

    public static class insertMemoriesAsync extends AsyncTask<Memory, Void, Void> {
        private PhosDAO mAsyncTaskDao;

        insertMemoriesAsync( PhosDAO dao ) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground( final Memory... memories ) {
            mAsyncTaskDao.insertMemories( memories );
            return null;
        }
    }

    public static class updateAlarmsAsync extends AsyncTask<Alarm, Void, Void> {
        private PhosDAO mAsyncTaskDao;

        updateAlarmsAsync( PhosDAO dao ) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground( final Alarm... alarms ) {
            mAsyncTaskDao.updateAlarms( alarms );
            return null;
        }
    }

    public static class updateMemoriesAsync extends AsyncTask<Memory, Void, Void> {
        private PhosDAO mAsyncTaskDao;

        updateMemoriesAsync( PhosDAO dao ) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground( final Memory... memories ) {
            mAsyncTaskDao.updateMemories( memories );
            return null;
        }
    }

    public static class deleteAlarmsAsync extends AsyncTask<Alarm, Void, Void> {
        private PhosDAO mAsyncTaskDao;

        deleteAlarmsAsync( PhosDAO dao ) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground( final Alarm... alarms ) {
            mAsyncTaskDao.deleteAlarms( alarms );
            return null;
        }
    }

    public static class deleteMemoriesAsync extends AsyncTask<Memory, Void, Void> {
        private PhosDAO mAsyncTaskDao;

        deleteMemoriesAsync( PhosDAO dao ) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground( final Memory... memories ) {
            mAsyncTaskDao.deleteMemories( memories );
            return null;
        }
    }
}
