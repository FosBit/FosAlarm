package com.fosbit.studios.fosalarm.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class FosRepositiory {
    private FosDAO mFosDao;
    private LiveData<List<Alarm>> mAlarms;
    private LiveData<List<Memory>> mMemories;

    // Constructor
    FosRepositiory(Application application ) {
        FosRoomDatabase db = FosRoomDatabase.getDatabase( application );
        mFosDao = db.phosDAO();
        mAlarms = mFosDao.getAlarms();
        mMemories = mFosDao.getMemories();
    }

    LiveData<List<Alarm>> getAlarms() {
        return mAlarms;
    }

    LiveData<List<Memory>> getMemories() {
        return mMemories;
    }

    public void insertAlarms( final Alarm... alarms ) {
        new insertAlarmsAsync(mFosDao).execute( alarms );
    }

    public void insertMemories( final Memory... memories ) {
        new insertMemoriesAsync(mFosDao).execute( memories );
    }

    public void updateAlarms( final Alarm... alarms ) {
        new updateAlarmsAsync(mFosDao).execute( alarms );
    }

    public void updateMemories( final Memory... memories ) {
        new updateMemoriesAsync(mFosDao).execute( memories );
    }

    public void deleteAlarms( final Alarm... alarms ) {
        new deleteAlarmsAsync(mFosDao).execute( alarms );
    }

    public void deleteMemories( final Memory... memories) {
        new deleteMemoriesAsync(mFosDao).execute( memories );
    }

    public static class insertAlarmsAsync extends AsyncTask<Alarm, Void, Void> {
        private FosDAO mAsyncTaskDao;

        insertAlarmsAsync( FosDAO dao ) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground( final Alarm... alarms ) {
            mAsyncTaskDao.insertAlarms( alarms );
            return null;
        }
    }

    public static class insertMemoriesAsync extends AsyncTask<Memory, Void, Void> {
        private FosDAO mAsyncTaskDao;

        insertMemoriesAsync( FosDAO dao ) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground( final Memory... memories ) {
            mAsyncTaskDao.insertMemories( memories );
            return null;
        }
    }

    public static class updateAlarmsAsync extends AsyncTask<Alarm, Void, Void> {
        private FosDAO mAsyncTaskDao;

        updateAlarmsAsync( FosDAO dao ) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground( final Alarm... alarms ) {
            mAsyncTaskDao.updateAlarms( alarms );
            return null;
        }
    }

    public static class updateMemoriesAsync extends AsyncTask<Memory, Void, Void> {
        private FosDAO mAsyncTaskDao;

        updateMemoriesAsync( FosDAO dao ) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground( final Memory... memories ) {
            mAsyncTaskDao.updateMemories( memories );
            return null;
        }
    }

    public static class deleteAlarmsAsync extends AsyncTask<Alarm, Void, Void> {
        private FosDAO mAsyncTaskDao;

        deleteAlarmsAsync( FosDAO dao ) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground( final Alarm... alarms ) {
            mAsyncTaskDao.deleteAlarms( alarms );
            return null;
        }
    }

    public static class deleteMemoriesAsync extends AsyncTask<Memory, Void, Void> {
        private FosDAO mAsyncTaskDao;

        deleteMemoriesAsync( FosDAO dao ) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground( final Memory... memories ) {
            mAsyncTaskDao.deleteMemories( memories );
            return null;
        }
    }
}
