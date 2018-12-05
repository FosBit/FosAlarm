package com.fosbit.studios.fosalarm;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.JobIntentService;

public class AlarmJobIntentService extends JobIntentService {
    /**
     * Unique job ID for this service.
     */
    static final int ALARM_JOB_ID = 1000;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    static void enqueueWork(Context context, Intent intent ) {
        enqueueWork( context, AlarmJobIntentService.class, ALARM_JOB_ID, intent );
    }

    @Override
    protected void onHandleWork(Intent intent) {
        // We have received work to do.  The system or framework is already
        // holding a wake lock for us at this point, so we can just go.
        startActivity( intent );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
