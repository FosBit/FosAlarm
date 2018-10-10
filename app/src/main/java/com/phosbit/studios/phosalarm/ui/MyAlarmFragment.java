package com.phosbit.studios.phosalarm.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phosbit.studios.phosalarm.R;
import com.phosbit.studios.phosalarm.db.Alarm;
import com.phosbit.studios.phosalarm.db.PhosViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyAlarmFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyAlarmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAlarmFragment extends Fragment
{
    private List<Alarm> alarms;
    private RecyclerView rv;
    private boolean isInitialized;
    private View alarmsView;
    private PhosViewModel mPhosViewModel;

    private OnFragmentInteractionListener mListener;

    public MyAlarmFragment( PhosViewModel viewModel )
    {
        isInitialized = false;
        mPhosViewModel = viewModel;
    }

    public static MyAlarmFragment newInstance( PhosViewModel viewModel )
    {
        MyAlarmFragment fragment = new MyAlarmFragment( viewModel );
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState )
    {
        if ( !isInitialized ) {
            // Inflate the layout for this fragment
            alarmsView =  inflater.inflate( R.layout.fragment_my_alarm, container, false );

            rv = alarmsView.findViewById( R.id.alarm_rv );

            LinearLayoutManager llm = new LinearLayoutManager( getActivity() );
            rv.setLayoutManager( llm );
            rv.setHasFixedSize( true );
            alarms = new ArrayList<>();
            initializeAdapter();
            isInitialized = true;
        }
        return alarmsView;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction( Uri uri );
    }


    public void updateData( final List<Alarm> alarms )
    {
        this.alarms = alarms;
        AlarmRVAdapter adapter = ( AlarmRVAdapter  ) rv.getAdapter();
        adapter.updateAlarms( this.alarms );
    }

    private void initializeAdapter()
    {
        AlarmRVAdapter adapter = new AlarmRVAdapter( this.alarms, mPhosViewModel );
        rv.setAdapter( adapter );
    }
}
