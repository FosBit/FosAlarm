package com.fosbit.studios.fosalarm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.fosbit.studios.fosalarm.ui.AlarmActivity;

/**
 * Created by Tatson on 05-Apr-17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        Intent in = new Intent( context, AlarmActivity.class );
        Bundle bundle = new Bundle();
        bundle.putAll( intent.getExtras() );
        in.putExtras( bundle );
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AlarmJobIntentService.enqueueWork( context, in );
        setResultCode( Activity.RESULT_OK );
    }
}
