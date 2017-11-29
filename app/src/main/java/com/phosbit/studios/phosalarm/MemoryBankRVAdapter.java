package com.phosbit.studios.phosalarm;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aaron on 11/27/2017.
 */

public class MemoryBankRVAdapter extends RecyclerView.Adapter< MemoryBankRVAdapter.MemoriesViewHolder >
{

    public static class MemoriesViewHolder extends RecyclerView.ViewHolder
    {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        CardView cv;
        TextView memoryName;
        TextView memoryDesc;

        MemoriesViewHolder( View itemView )
        {
            super( itemView );
            cv = ( CardView ) itemView.findViewById( R.id.memory_cardview );
            memoryName = ( TextView ) itemView.findViewById( R.id.memory_title );
            memoryDesc = ( TextView ) itemView.findViewById( R.id.memory_description );
        }
    }

    List< Memory > memories;

    // Provide a suitable constructor
    MemoryBankRVAdapter( List< Memory > memories )
    {
        this.memories = memories;
    }

    @Override
    public void onAttachedToRecyclerView( RecyclerView recyclerView )
    {
        super.onAttachedToRecyclerView( recyclerView );
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MemoriesViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        View v = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.memory_item, viewGroup, false );
        MemoriesViewHolder pvh = new MemoriesViewHolder( v );
        return pvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder( MemoriesViewHolder personViewHolder, int i )
    {
        personViewHolder.memoryName.setText( memories.get( i ).getTitle() );
        personViewHolder.memoryDesc.setText( memories.get( i ).getContent() );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return memories.size();
    }
}
