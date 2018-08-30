package com.phosbit.studios.phosalarm.db;

import android.app.Application;
import android.os.AsyncTask;

public class PhosRepositiory {
    private PhosDAO mPhosDao;

    // Constructor
    PhosRepositiory( Application application ) {
        PhosRoomDatabase db = PhosRoomDatabase.getDatabase( application );
        mPhosDao = db.phosDAO();
    }

    public void insertAlarms( final Alarm... alarms ) {
        new insertAlarmsAsync( mPhosDao ).execute( alarms );
    }

    public void insertMemories( final Memory... memories ) {
        new insertMemoriesAsync( mPhosDao ).execute( memories );
    }

    public void updateAlarm( final Alarm... alarms ) {
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
