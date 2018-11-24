package com.fosbit.studios.fosalarm;

import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;

import com.fosbit.studios.fosalarm.db.Alarm;
import com.fosbit.studios.fosalarm.db.Memory;
import com.fosbit.studios.fosalarm.db.FosViewModel;
import com.fosbit.studios.fosalarm.ui.EditAlarmActivity;
import com.fosbit.studios.fosalarm.ui.EditMemoryActivity;
import com.fosbit.studios.fosalarm.ui.MemoryBankFragment;
import com.fosbit.studios.fosalarm.ui.MyAlarmFragment;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MyAlarmFragment.OnFragmentInteractionListener,
                   MemoryBankFragment.OnFragmentInteractionListener
{
    private DrawerLayout m_drawer;
    private NavigationView m_nvDrawer;
    private static String LOG_TAG = "FosAlarm";
    private FosViewModel mFosViewModel;
    private MyAlarmFragment alarmFragment;
    private MemoryBankFragment memoryBankFragment;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        /*
         * Use ViewModelProviders to associate your ViewModel with your UI controller.
         * When the app first starts, the ViewModelProviders will create the ViewModel.
         * When the activity is destroyed, for example through a configuration change,
         * the ViewModel persists. When the activity is re-created, the ViewModelProviders
         * return the existing ViewModel.
         */
        mFosViewModel = ViewModelProviders.of( this ).get( FosViewModel.class );

        //Instantiate Fragments
        alarmFragment = MyAlarmFragment.newInstance();
        memoryBankFragment = MemoryBankFragment.newInstance();

        // Add an observer on the LiveData returned by getAlarms and getMemories.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mFosViewModel.getAlarms().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(@Nullable final List<Alarm> alarms) {
                alarmFragment.updateData( alarms );
            }
        });

        mFosViewModel.getMemories().observe(this, new Observer<List<Memory>>() {
            @Override
            public void onChanged(@Nullable final List<Memory> memories) {
                memoryBankFragment.updateData( memories );
            }
        });

        Toolbar toolbar = ( Toolbar ) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        FloatingActionButton fab = ( FloatingActionButton ) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                if ( getTitle().toString().equals( "Memory Bank" ) ) {
                    // Start edit memory activity
                    Intent intent = new Intent( v.getContext(), EditMemoryActivity.class );
                    Bundle bundle = new Bundle();
                    bundle.putBoolean( "ISNEW", true );
                    intent.putExtras( bundle );
                    v.getContext().startActivity( intent );
                } else {
                    TimePickerDialog timePicker =
                            new TimePickerDialog(MainActivity.this,
                                    new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet( TimePicker view,
                                                               int hourOfDay,
                                                               int minute ) {
                                            // Start edit alarm activity
                                            Intent intent = new Intent( view.getContext(), EditAlarmActivity.class );
                                            Bundle bundle = new Bundle();
                                            bundle.putBoolean( "ISNEW", true );
                                            bundle.putLong( "HOUROFDAY", Long.valueOf(hourOfDay) );
                                            bundle.putLong( "MINUTE", Long.valueOf(minute) );
                                            intent.putExtras( bundle );
                                            view.getContext().startActivity( intent );
                                        }
                                    },
                                    Calendar.getInstance().get( Calendar.HOUR_OF_DAY ),
                                    Calendar.getInstance().get( Calendar.MINUTE ),
                                    false); // 'false' for 12-hour times
                    timePicker.show();
                }
            }
        } );

        // Check that the activity is using the layout version with
        // the 'flContent' FrameLayout; defined in content_main.xml
        if ( findViewById( R.id.flContent ) != null )
        {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if ( savedInstanceState != null ) {
                return;
            }

        }

        // Find our drawer layout
        m_drawer = ( DrawerLayout ) findViewById( R.id.drawer_layout );
        // Find our drawer view
        m_nvDrawer = ( NavigationView ) findViewById( R.id.nav_view );
        // Setup drawer view
        setupDrawerContent( m_nvDrawer );
        // Default item is nav_alarm
        selectDrawerItem( m_nvDrawer.getMenu().getItem(0) );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this,
                                                                  m_drawer,
                                                                  toolbar,
                                                                  R.string.navigation_drawer_open,
                                                                  R.string.navigation_drawer_close );
        // Tie DrawerLayout events to the ActionBarDrawerToggle
        // This lets hamburger icon animate
        m_drawer.addDrawerListener( toggle );
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        // The action bar home/up action should open or close the drawer.
        switch ( item.getItemId() )
        {
            case android.R.id.home:
                m_drawer.openDrawer( GravityCompat.START );
                return true;
        }

        return super.onOptionsItemSelected( item );
    }

    private void setupDrawerContent( NavigationView navigationView )
    {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener()
                {
                    @Override
                    public boolean onNavigationItemSelected( @NonNull MenuItem menuItem )
                    {
                        selectDrawerItem( menuItem );
                        return true;
                    }
                } );
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = ( DrawerLayout ) findViewById( R.id.drawer_layout );
        if ( drawer.isDrawerOpen( GravityCompat.START ) )
        {
            drawer.closeDrawer( GravityCompat.START );
        }else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    public boolean selectDrawerItem( MenuItem item )
    {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        String fragmentTag;
        switch ( item.getItemId() )
        {
            case R.id.nav_alarm:
                fragment = alarmFragment;
                fragmentTag = "alarm_fragment";
                break;


            case R.id.nav_memory_bank:
                fragment = memoryBankFragment;
                fragmentTag = "memory_fragment";
                break;

            default:
                fragment = alarmFragment;
                fragmentTag = "alarm_fragment";
        }

        // Add the fragment to the 'flContent' FrameLayout
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace( R.id.flContent, fragment, fragmentTag ).commit();

        // Update ActionBar
        item.setChecked( true );
        setTitle( item.getTitle() );


        DrawerLayout drawer = ( DrawerLayout ) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;


    }

    @Override
    public void onFragmentInteraction( Uri uri )
    {
        // Leaving blank for now. URI stands for "Uniform Resource Identifier",
        // a compact sequence of characters that identifies an abstract or physical resource.
        // It is useful if we have an Android content provider, but since we don't, I think
        // we can ignore this for now. We still have to override the method it in order to communicate
        // with our fragments.
    }
}
