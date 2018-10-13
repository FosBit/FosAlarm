package com.fosbit.studios.fosalarm.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fosbit.studios.fosalarm.R;
import com.fosbit.studios.fosalarm.db.Memory;
import com.fosbit.studios.fosalarm.db.FosViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MemoryBankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MemoryBankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemoryBankFragment extends Fragment
{
    private List<Memory> memories;
    private RecyclerView rv;
    private View mbView;
    private boolean isInitialized;
    private FosViewModel mFosViewModel;

    private OnFragmentInteractionListener mListener;

    public MemoryBankFragment()
    {
        isInitialized = false;
        memories = new ArrayList<>();
    }

    public static MemoryBankFragment newInstance()
    {
        MemoryBankFragment fragment = new MemoryBankFragment();
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        if (mFosViewModel == null) {
            mFosViewModel = ViewModelProviders.of( getActivity() ).get( FosViewModel.class );
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState )
    {
        if ( !isInitialized ) {
            // Inflate the layout for this fragment
            mbView = inflater.inflate(R.layout.fragment_memory_bank, container, false);

            rv = mbView.findViewById(R.id.memory_rv);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            rv.setLayoutManager(llm);
            rv.setHasFixedSize(true);
            initializeAdapter();
            isInitialized = true;
        } else {
            MemoryBankRVAdapter adapter = ( MemoryBankRVAdapter  ) rv.getAdapter();
            adapter.updateMemories( this.memories );
        }
        return mbView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed( Uri uri )
    {
        if ( mListener != null )
        {
            mListener.onFragmentInteraction( uri );
        }
    }

    @Override
    public void onAttach( Context context )
    {
        super.onAttach( context );
        if ( context instanceof OnFragmentInteractionListener )
        {
            mListener = ( OnFragmentInteractionListener ) context;
        }else
        {
            throw new RuntimeException( context.toString()
                    + " must implement OnFragmentInteractionListener" );
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * See the Android Training lesson
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction( Uri uri );
    }

    public void updateData( final List<Memory> memories )
    {
        this.memories = memories;
        // Make sure view is created before updating view
        if ( rv != null ) {
            MemoryBankRVAdapter adapter = ( MemoryBankRVAdapter ) rv.getAdapter();
            adapter.updateMemories( this.memories );
        }
    }

    private void initializeAdapter()
    {
        MemoryBankRVAdapter adapter = new MemoryBankRVAdapter( this.memories, mFosViewModel );
        rv.setAdapter( adapter );
    }
}
