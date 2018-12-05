package com.fosbit.studios.fosalarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.JobIntentService;

import com.fosbit.studios.fosalarm.ui.AlarmActivity;

public class AlarmJobIntentService extends JobIntentService {
    /**
     * Unique job ID for this service.
     */
    static final int ALARM_JOB_ID = 1000;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    static void enqueueWork(Context context, Intent work ) {
        enqueueWork( context, AlarmJobIntentService.class, ALARM_JOB_ID, work );
    }

    @Override
    protected void onHandleWork(Intent intent) {
        // We have received work to do.  The system or framework is already
        // holding a wake lock for us at this point, so we can just go.
        Intent in = new Intent( getApplicationContext(), AlarmActivity.class );
        Bundle bundle = new Bundle();
        bundle.putAll( intent.getExtras() );
        in.putExtras( bundle );
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity( in );
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
