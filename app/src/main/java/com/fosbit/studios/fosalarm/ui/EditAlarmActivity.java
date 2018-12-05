package com.fosbit.studios.fosalarm.ui;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.fosbit.studios.fosalarm.db.Memory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class EditAlarmActivity extends AppCompatActivity {
    private FosViewModel mFosViewModel;
    CardView alarmTimeCV;
    TextView alarmTime;
    CardView selectedMemoryCV;
    TextView selectedMemory;
    CheckBox alarmRepeat;
    Button alarmCancel;
    Button alarmSet;
    boolean isNew;
    boolean status;
    String alarmID;
    String memoryID;
    String memoryMessage;
    long hour;
    long minute;
    List<Memory> memoriesList;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit_alarm );

        alarmTimeCV = (CardView) findViewById( R.id.alarm_time_cardview );
        selectedMemoryCV = ( CardView ) findViewById( R.id.memory_select_cardview );
        selectedMemory = ( TextView ) findViewById( R.id.selected_memory );
        alarmCancel = ( Button ) findViewById( R.id.cancel_alarm_button );
        alarmSet = ( Button ) findViewById( R.id.set_alarm_button );

        Bundle bundle = getIntent().getExtras();
        // Get the hour and minute from extras when started from intent
        isNew = bundle.getBoolean( "ISNEW" );
        if ( isNew ) {
            alarmID = UUID.randomUUID().toString();
            selectedMemory.setText( "Select Memory");
            status = true;
        } else {
            alarmID = bundle.getString( "ALARMID" );
            memoryID = bundle.getString( "MEMORYID" );
            status = bundle.getBoolean( "STATUS" );
        }
        hour = bundle.getLong( "HOUROFDAY" );
        minute = bundle.getLong( "MINUTE" );
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

        mFosViewModel = ViewModelProviders.of( this ).get( FosViewModel.class );
        mFosViewModel.getMemories().observe(this, new Observer<List<Memory>>() {
            @Override
            public void onChanged(@Nullable final List<Memory> memories) {
                if ( memoriesList == null ) {
                    memoriesList = new ArrayList<>();
                } else {
                    memoriesList.clear();
                }
                memoriesList = memories;
                if ( !isNew ) {
                    for ( int i = 0; i < memoriesList.size(); i++ ) {
                        if ( memoriesList.get(i).getMemoryID().equals( memoryID ) )
                            selectedMemory.setText( memoriesList.get(i).getTitle() );
                    }
                }
            }
        });

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
                //Create an alert dialog
                List<String> memoriesTitles = new ArrayList<>();
                for ( int i = 0; i < memoriesList.size(); i++ ) {
                    memoriesTitles.add( memoriesList.get( i ).getTitle() );
                }
                final CharSequence[] memories= memoriesTitles.toArray(new CharSequence[ memoriesTitles.size() ] );
                final AlertDialog levelDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder( v.getContext() );
                builder.setTitle("Select a memory:");
                builder.setSingleChoiceItems( memories, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        selectedMemory.setText( memoriesList.get( item ).getTitle() );
                        memoryID = memoriesList.get( item ).getMemoryID();
                        memoryMessage = memoriesList.get( item ).getMessage();
                        dialog.dismiss();
                    }
                });
                levelDialog = builder.create();
                levelDialog.show();
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
                Alarm alarm = new Alarm( alarmID,
                        TimeUnit.HOURS.toMillis( hour )
                                + TimeUnit.MINUTES.toMillis( minute ),
                        status, memoryID);

                Intent myIntent = new Intent( getApplicationContext(), AlarmReceiver.class );


                // Just to make sure app handles edge case where no memory is selected
                if ( memoryMessage != null ) {
                    myIntent.putExtra( "TITLE", selectedMemory.getText().toString() );
                    myIntent.putExtra("MESSAGE", memoryMessage);

                } else {
                    myIntent.putExtra( "TITLE", "" );
                    myIntent.putExtra("MESSAGE", "");
                }

                myIntent.putExtra( "ALARMID", alarmID );
                myIntent.putExtra( "TIME", TimeUnit.HOURS.toMillis( hour )
                        + TimeUnit.MINUTES.toMillis( minute ) );
                myIntent.putExtra( "MEMORYID", memoryID );
                PendingIntent pendingIntent = PendingIntent.getBroadcast( getApplicationContext(),
                        requestCode,
                        myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT );
                AlarmManager alarmManager = ( AlarmManager ) getSystemService( ALARM_SERVICE );
                if ( isNew ) {
                    Calendar today = Calendar.getInstance();
                    today.set(Calendar.MILLISECOND, 0);
                    today.set(Calendar.SECOND, 0);
                    today.set(Calendar.MINUTE, 0);
                    today.set(Calendar.HOUR_OF_DAY, 0);
                    long triggerTimeinMillis = today.getTimeInMillis()
                            + TimeUnit.HOURS.toMillis( hour )
                            + TimeUnit.MINUTES.toMillis( minute );
                    // If trigger time is in the past, set to the next day
                    if ( triggerTimeinMillis <  Calendar.getInstance().getTimeInMillis() ) {
                        triggerTimeinMillis +=  TimeUnit.DAYS.toMillis(1);
                    }
                    // Set new alarm with pendingIntent
                    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
                        AlarmManager.AlarmClockInfo alarmClockInfo =
                                new AlarmManager.AlarmClockInfo( triggerTimeinMillis, pendingIntent );
                        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
                    } else if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
                        alarmManager.setExact( android.app.AlarmManager.RTC_WAKEUP,
                                triggerTimeinMillis,
                                pendingIntent );
                    } else {
                        alarmManager.set( AlarmManager.RTC_WAKEUP,
                                triggerTimeinMillis,
                                pendingIntent );
                    }
                    mFosViewModel.insertAlarms( alarm );
                } else {
                    // Cancel pendingIntent that matches previously set pending intent
                    alarmManager.cancel( pendingIntent );
                    // Re-set updated alarm with pendingIntent
                    Calendar today = Calendar.getInstance();
                    today.set(Calendar.MILLISECOND, 0);
                    today.set(Calendar.SECOND, 0);
                    today.set(Calendar.MINUTE, 0);
                    today.set(Calendar.HOUR_OF_DAY, 0);
                    long triggerTimeinMillis = today.getTimeInMillis()
                            + TimeUnit.HOURS.toMillis( hour )
                            + TimeUnit.MINUTES.toMillis( minute );
                    // If trigger time is in the past, set to the next day
                    if ( triggerTimeinMillis <  Calendar.getInstance().getTimeInMillis() ) {
                        triggerTimeinMillis +=  TimeUnit.DAYS.toMillis(1);
                    }
                    // Set new alarm with pendingIntent
                    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
                        AlarmManager.AlarmClockInfo alarmClockInfo =
                                new AlarmManager.AlarmClockInfo( triggerTimeinMillis, pendingIntent );
                        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
                    } else if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
                        alarmManager.setExact( android.app.AlarmManager.RTC_WAKEUP,
                                triggerTimeinMillis,
                                pendingIntent );
                    } else {
                        alarmManager.set( AlarmManager.RTC_WAKEUP,
                                triggerTimeinMillis,
                                pendingIntent );
                    }
                    mFosViewModel.updateAlarms( alarm );
                }

                finish();
            }
        });
    }
}
