package com.fosbit.studios.fosalarm.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.fosbit.studios.fosalarm.AlarmReceiver;
import com.fosbit.studios.fosalarm.R;
import com.fosbit.studios.fosalarm.db.Alarm;
import com.fosbit.studios.fosalarm.db.FosViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Aaron on 11/27/2017.
 */

public class AlarmRVAdapter extends RecyclerView.Adapter< AlarmRVAdapter.AlarmsViewHolder >
{

    public static class AlarmsViewHolder extends RecyclerView.ViewHolder
    {
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        CardView cv;
        TextView alarmName;;
        Button alarmEdit;
        Button alarmDelete;
        Switch alarmSwitch;


        AlarmsViewHolder( View itemView )
        {
            super( itemView );
            // Find views to put in the references
            cv = ( CardView ) itemView.findViewById( R.id.memory_cardview );
            alarmName = ( TextView ) itemView.findViewById( R.id.alarm_time);
            alarmEdit = ( Button ) itemView.findViewById( R.id.alarm_edit_button );
            alarmDelete = ( Button ) itemView.findViewById( R.id.alarm_delete_button );
            alarmSwitch = ( Switch ) itemView.findViewById( R.id.alarm_switch );
        }
    }

    List<Alarm> alarms;
    FosViewModel viewModel;

    // Provide a suitable constructor
    AlarmRVAdapter( List<Alarm> alarms, FosViewModel viewModel )
    {
        this.alarms = new ArrayList<>();
        updateAlarms( alarms );
        this.viewModel = viewModel;
    }

    public void updateAlarms( List<Alarm> alarms ) {
        if ( alarms != null && alarms.size() >= 0 ) {
            this.alarms.clear();
            this.alarms.addAll( alarms );
            notifyDataSetChanged();
        }
    }

    @Override
    public void onAttachedToRecyclerView( RecyclerView recyclerView )
    {
        super.onAttachedToRecyclerView( recyclerView );
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlarmsViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        View v = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.alarm_item, viewGroup, false );
        AlarmsViewHolder pvh = new AlarmsViewHolder( v );
        return pvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder( AlarmsViewHolder holder, final int i ) {
        long hour = TimeUnit.MILLISECONDS.toHours( alarms.get( i ).getTimeOfDay() );
        long minute = TimeUnit.MILLISECONDS.toMinutes( alarms.get( i ).getTimeOfDay() - TimeUnit.HOURS.toMillis( hour ) );

        if ( ( 12 - hour ) > 0 ) {
            if ( hour == 0 ) {
                holder.alarmName.setText( new DecimalFormat( "00" ).format( hour + 12 )
                        + ":" + new DecimalFormat("00" ).format( minute ) + " AM" );
            } else {
                holder.alarmName.setText( new DecimalFormat( "00" ).format( hour )
                        + ":" + new DecimalFormat(  "00" ).format( minute ) + " AM" );
            }
        } else {
            holder.alarmName.setText( new DecimalFormat( "00" ).format( hour - 12 )
                    + ":" + new DecimalFormat( "00" ).format( minute ) + " PM");
        }
        holder.alarmSwitch.setChecked( alarms.get( i ).getStatus() );

        // Set a click listener for alarm edit
        holder.alarmEdit.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                long hour = TimeUnit.MILLISECONDS.toHours( alarms.get( i ).getTimeOfDay() );
                long minute = TimeUnit.MILLISECONDS.toMinutes( alarms.get( i ).getTimeOfDay() - TimeUnit.HOURS.toMillis( hour ) );
                // Start edit activity
                Intent intent = new Intent( v.getContext(), EditAlarmActivity.class );
                Bundle bundle = new Bundle();
                bundle.putBoolean( "ISNEW", false );
                bundle.putBoolean( "STATUS", alarms.get( i ).getStatus() );
                bundle.putString( "ALARMID", alarms.get( i ).getAlarmID() );
                bundle.putString( "MEMORYID", alarms.get( i ).getMemoryID() );
                bundle.putLong( "HOUROFDAY", hour );
                bundle.putLong( "MINUTE", minute );
                intent.putExtras( bundle );
                v.getContext().startActivity( intent );
            }
        });

        // Same for alarm delete
        holder.alarmDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v )
            {
                // Remove alarm from database
                viewModel.deleteAlarms( alarms.get( i ) );

                // Build requestCode from unique alarm ID (should probably find a better way to identify)
                int requestCode = 0;
                for ( int j = 0; j < alarms.get( i ).getAlarmID().length(); j++ ) {
                    requestCode += Character.getNumericValue( alarms.get( i ).getAlarmID().charAt( j ) );
                }
                Intent myIntent = new Intent( viewModel.getApplication().getApplicationContext(), AlarmReceiver.class );
                PendingIntent pendingIntent = PendingIntent.getBroadcast( viewModel.getApplication().getBaseContext(),
                        requestCode,
                        myIntent,
                        0 );
                AlarmManager alarmManager =
                        ( AlarmManager ) viewModel.getApplication().getSystemService( viewModel.getApplication().ALARM_SERVICE );
                // Cancel pendingIntent that matches previously set pending intent
                alarmManager.cancel(pendingIntent);
                pendingIntent.cancel();

                // Show removed notification
                Snackbar.make( v, "Alarm Removed.", Snackbar.LENGTH_LONG ).show();
            }
        });

        // And alarm switch
        holder.alarmSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged( CompoundButton buttonView, boolean isChecked ) {
                alarms.get( i ).setStatus( isChecked );
                // Build requestCode from unique alarm ID (should probably find a better way to identify)
                int requestCode = 0;
                for ( int j = 0; j < alarms.get( i ).getAlarmID().length(); j++ ) {
                    requestCode += Character.getNumericValue( alarms.get( i ).getAlarmID().charAt( j ) );
                }
                Intent myIntent = new Intent( viewModel.getApplication().getApplicationContext(), AlarmReceiver.class );
                PendingIntent pendingIntent = PendingIntent.getBroadcast( viewModel.getApplication().getBaseContext(),
                        requestCode,
                        myIntent,
                        0 );
                AlarmManager alarmManager =
                        ( AlarmManager ) viewModel.getApplication().getSystemService( viewModel.getApplication().ALARM_SERVICE );
                if ( !isChecked ) {
                    // Cancel pendingIntent that matches previously set pending intent
                    alarmManager.cancel(pendingIntent);
                } else {
                    Calendar today = Calendar.getInstance();
                    today.set(Calendar.MILLISECOND, 0);
                    today.set(Calendar.SECOND, 0);
                    today.set(Calendar.MINUTE, 0);
                    today.set(Calendar.HOUR_OF_DAY, 0);
                    // Set alarm with pendingIntent
                    alarmManager.set(AlarmManager.RTC_WAKEUP,
                            today.getTimeInMillis() + alarms.get( i ).getTimeOfDay(),
                            pendingIntent);
                }
                viewModel.updateAlarms( alarms.get( i ) );
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return alarms.size();
    }
}
