package com.fosbit.studios.fosalarm.ui;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.fosbit.studios.fosalarm.R;

public class AlarmActivity extends AppCompatActivity {

    Ringtone ringtone;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // Gets default alarm to ring when alarm is sounded
        Uri alarmUri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_ALARM );
        if ( alarmUri == null ) {
            alarmUri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
        }
        ringtone = RingtoneManager.getRingtone( getBaseContext(), alarmUri );
        ringtone.play();
    }

    public void stopRingtone( View v ) {
        ringtone.stop();
    }

}
