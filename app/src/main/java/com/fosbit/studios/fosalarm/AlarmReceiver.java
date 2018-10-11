package com.fosbit.studios.fosalarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.fosbit.studios.fosalarm.ui.AlarmActivity;

/**
 * Created by Tatson on 05-Apr-17.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        Intent in = new Intent( context, AlarmActivity.class );
        context.startActivity( in );
        setResultCode( Activity.RESULT_OK );
    }
}
