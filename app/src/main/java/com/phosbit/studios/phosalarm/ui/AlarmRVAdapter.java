package com.phosbit.studios.phosalarm.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.phosbit.studios.phosalarm.R;
import com.phosbit.studios.phosalarm.db.Alarm;

import java.util.List;

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
            alarmName = ( TextView ) itemView.findViewById( R.id.alarm_time );
            alarmEdit = ( Button ) itemView.findViewById( R.id.alarm_edit_button );
            alarmDelete = ( Button ) itemView.findViewById( R.id.alarm_delete_button );
            alarmSwitch = ( Switch ) itemView.findViewById( R.id.alarm_switch );
        }
    }

    List<Alarm> alarms;

    // Provide a suitable constructor
    AlarmRVAdapter(List<Alarm> alarms )
    {
        this.alarms = alarms;
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
    public void onBindViewHolder( AlarmsViewHolder holder, final int i )
    {
        holder.alarmName.setText( alarms.get( i ).getAlarmID() );

        // Set a click listener for memory edit
        holder.alarmEdit.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                // Start edit activity
                Intent intent = new Intent( v.getContext(), EditAlarmActivity.class );
                v.getContext().startActivity( intent );
            }
        });

        // Same for memory delete
        holder.alarmDelete.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                // Get the clicked item label
                String alarmTitle = alarms.get( i ).getAlarmID();
                // Remove the item on delete button click
                alarms.remove( i );
                // Notify any registered observers that the item previously located at position
                // has been removed from the data set. The items previously located at and
                // after position may now be found at oldPosition - 1.
                notifyItemRemoved( i );
                // Notify any registered observers that the itemCount items starting at
                // position positionStart have changed.
                notifyItemRangeChanged( i, alarms.size() );

                // Show the removed item title
                Snackbar.make( v, "Removed: " + alarmTitle, Snackbar.LENGTH_LONG ).show();
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
