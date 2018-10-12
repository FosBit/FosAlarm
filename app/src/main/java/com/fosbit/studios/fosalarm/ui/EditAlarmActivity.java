package com.fosbit.studios.fosalarm.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fosbit.studios.fosalarm.AlarmReceiver;
import com.fosbit.studios.fosalarm.R;
import com.fosbit.studios.fosalarm.db.Alarm;
import com.fosbit.studios.fosalarm.db.FosViewModel;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class EditAlarmActivity extends AppCompatActivity {
    private FosViewModel mFosViewModel;
    CardView alarmTimeCV;
    TextView alarmTime;
    CardView selectedMemoryCV;
    CheckBox alarmRepeat;
    Button alarmCancel;
    Button alarmSet;
    boolean isNew;
    boolean status;
    String alarmID;
    int hour;
    int minute;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit_alarm );

        mFosViewModel = ViewModelProviders.of( this ).get( FosViewModel.class );

        // Get the hour and minute from extras when started from intent
        Bundle bundle = getIntent().getExtras();
        isNew = bundle.getBoolean( "ISNEW" );
        if ( isNew ) {
            alarmID = UUID.randomUUID().toString();
            status = true;
        } else {
            alarmID = bundle.getString( "ALARMID" );
            status = bundle.getBoolean( "STATUS" );
        }
        hour = bundle.getInt( "HOUROFDAY" );
        minute = bundle.getInt( "MINUTE" );
        alarmTime = ( TextView ) findViewById( R.id.alarm_edit_time );
        if ( ( 12 - hour ) > 0 ) {
            if ( hour == 0 ) {
                alarmTime.setText( new DecimalFormat( "00" ).format( hour + 12 )
                        + ":" + new DecimalFormat( "00" ).format( minute ) + " AM" );
            } else {
                alarmTime.setText( new DecimalFormat( "00" ).format( hour )
                        + ":" + new DecimalFormat( "00" ).format( minute ) + " AM" );
            }
        } else {
            alarmTime.setText( new DecimalFormat( "00" ).format( hour - 12 )
                    + ":" + new DecimalFormat( "00" ).format( minute ) + " PM" );
        }

        alarmTimeCV = (CardView) findViewById( R.id.alarm_time_cardview );
        selectedMemoryCV = ( CardView ) findViewById( R.id.memory_select_cardview );
        alarmRepeat = (CheckBox) findViewById( R.id.alarm_repeat );
        alarmCancel = ( Button ) findViewById( R.id.cancel_alarm_button );
        alarmSet = ( Button ) findViewById( R.id.set_alarm_button );

        alarmTimeCV.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                TimePickerDialog timePicker =
                        new TimePickerDialog(EditAlarmActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet( TimePicker view,
                                                           int hourOfDay,
                                                           int minute ) {
                                        EditAlarmActivity.this.hour = hourOfDay;
                                        EditAlarmActivity.this.minute = minute;
                                        if ( ( 12 - hour ) > 0 ) {
                                            if ( hour == 0 ) {
                                                EditAlarmActivity.this.alarmTime.setText(
                                                        new DecimalFormat( "00" ).format( hour + 12 )
                                                        + ":" + new DecimalFormat( "00" ).format( minute ) + " AM" );
                                            } else {
                                                EditAlarmActivity.this.alarmTime.setText(
                                                        new DecimalFormat( "00").format( hour )
                                                        + ":" + new DecimalFormat( "00" ).format( minute ) + " AM");
                                            }
                                        } else {
                                            EditAlarmActivity.this.alarmTime.setText(
                                                    new DecimalFormat( "00" ).format( hour - 12 )
                                                    + ":" + new DecimalFormat( "00" ).format( minute )
                                                    + " PM"  );
                                        }
                                    }
                                },
                                Calendar.getInstance().get( Calendar.HOUR_OF_DAY ),
                                Calendar.getInstance().get( Calendar.MINUTE ),
                                false); // 'false' for 12-hour times
                timePicker.show();
            }
        });

        selectedMemoryCV.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

            }
        });

        alarmCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                finish();
            }
        });

        alarmSet.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                // Build requestCode from unique alarm ID. This is so we can access the specific
                // pendingIntent that was sent in case we need to cancel it and reset the alarm
                int requestCode = 0;
                for ( int i = 0; i < alarmID.length(); i++ ) {
                    requestCode += Character.getNumericValue( alarmID.charAt( i ) );
                }
                Intent myIntent = new Intent( getApplicationContext(), AlarmReceiver.class );
                PendingIntent pendingIntent = PendingIntent.getBroadcast( getBaseContext(),
                        requestCode,
                        myIntent,
                        0 );
                AlarmManager alarmManager = ( AlarmManager ) getSystemService( ALARM_SERVICE );
                if ( isNew ) {
                    // Set new alarm with pendingIntent
                    alarmManager.set( AlarmManager.RTC_WAKEUP,
                            TimeUnit.HOURS.toMillis( hour )
                                    + TimeUnit.MINUTES.toMillis( minute ),
                            pendingIntent );
                    Alarm alarm = new Alarm( alarmID,
                            TimeUnit.HOURS.toMillis( hour )
                                    + TimeUnit.MINUTES.toMillis( minute ),
                            status, "1");
                    mFosViewModel.insertAlarms( alarm );
                } else {
                    // Cancel pendingIntent that matches previously set pending intent
                    alarmManager.cancel( pendingIntent );
                    pendingIntent.cancel();
                    // Re-set updated alarm with pendingIntent
                    alarmManager.set( AlarmManager.RTC_WAKEUP,
                            TimeUnit.HOURS.toMillis( hour )
                                    + TimeUnit.MINUTES.toMillis( minute ),
                            pendingIntent );
                    Alarm alarm = new Alarm( alarmID,
                            TimeUnit.HOURS.toMillis( hour )
                                    + TimeUnit.MINUTES.toMillis( minute ),
                            status, "1");
                    mFosViewModel.updateAlarms( alarm );
                }

                finish();
            }
        });
    }
}
