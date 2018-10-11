package com.phosbit.studios.phosalarm.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.phosbit.studios.phosalarm.R;
import com.phosbit.studios.phosalarm.db.Memory;
import com.phosbit.studios.phosalarm.db.PhosViewModel;

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
        Button memoryEdit;
        Button memoryDelete;


        MemoriesViewHolder( View itemView )
        {
            super( itemView );
            // Find views to put in the references
            cv = ( CardView ) itemView.findViewById( R.id.memory_cardview );
            memoryName = ( TextView ) itemView.findViewById( R.id.memory_title );
            memoryDesc = ( TextView ) itemView.findViewById( R.id.memory_description );
            memoryEdit = ( Button ) itemView.findViewById( R.id.memory_edit_button );
            memoryDelete = ( Button ) itemView.findViewById( R.id.memory_delete_button );
        }
    }

    List<Memory> memories;
    PhosViewModel viewModel;

    // Provide a suitable constructor
    MemoryBankRVAdapter( List<Memory> memories, PhosViewModel viewModel )
    {
        this.memories = memories;
        this.viewModel = viewModel;
    }

    public void updateMemories( List<Memory> memories ) {
        if ( memories != null && memories.size() > 0 ) {
            this.memories.clear();
            this.memories.addAll( memories );
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
    public MemoriesViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        View v = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.memory_item, viewGroup, false );
        MemoriesViewHolder pvh = new MemoriesViewHolder( v );
        return pvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder( MemoriesViewHolder holder, final int i )
    {
        holder.memoryName.setText( memories.get( i ).getTitle() );
        holder.memoryDesc.setText( memories.get( i ).getMessage() );

        // Set a click listener for memory edit
        holder.memoryEdit.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                // Start edit activity
                Intent intent = new Intent( v.getContext(), EditMemoryActivity.class );
                v.getContext().startActivity( intent );
            }
        });

        // Same for memory delete
        holder.memoryDelete.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                // Get the clicked item label
                String memoryTitle = memories.get( i ).getTitle();
                // Remove the item on delete button click
                memories.remove( i );
                // Notify any registered observers that the item previously located at position
                // has been removed from the data set. The items previously located at and
                // after position may now be found at oldPosition - 1.
                notifyItemRemoved( i);
                // Notify any registered observers that the itemCount items starting at
                // position positionStart have changed.
                notifyItemRangeChanged( i, memories.size());

                // Show the removed item title
                Snackbar.make( v, "Removed: " + memoryTitle, Snackbar.LENGTH_LONG ).show();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return memories.size();
    }
}
