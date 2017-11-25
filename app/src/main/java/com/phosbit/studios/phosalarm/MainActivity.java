package com.phosbit.studios.phosalarm;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements MyAlarmFragment.OnFragmentInteractionListener
{

    private DrawerLayout m_drawer;
    private NavigationView m_nvDrawer;
    private static String LOG_TAG = "CardViewActivity";

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = ( Toolbar ) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        FloatingActionButton fab = ( FloatingActionButton ) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                TimePickerDialog timePicker =
                        new TimePickerDialog( MainActivity.this,
                                new TimePickerDialog.OnTimeSetListener()
                                {
                                    @Override
                                    public void onTimeSet( TimePicker view,
                                                           int hourOfDay,
                                                           int minute )
                                    {

                                    }
                                },
                                Calendar.getInstance().get( Calendar.HOUR_OF_DAY ),
                                Calendar.getInstance().get( Calendar.MINUTE ),
                                false ); // 'false' for 12-hour times
                timePicker.show();
            }
        } );

        // Find our drawer layout
        m_drawer = ( DrawerLayout ) findViewById( R.id.drawer_layout );
        // Find our drawer view
        m_nvDrawer = ( NavigationView ) findViewById( R.id.nav_view );
        // Setup drawer view
        setupDrawerContent( m_nvDrawer );

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, m_drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        // Tie DrawerLayout events to the ActionBarDrawerToggle
        // This lets hamburger icon animate
        m_drawer.addDrawerListener( toggle );
        toggle.syncState();

        // SET DEFAULT FRAGMENT
        // Check that the activity is using the layout version with
        // the 'flContent' FrameLayout
        if ( findViewById( R.id.flContent ) != null )
        {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if ( savedInstanceState != null )
            {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            MyAlarmFragment firstFragment = new MyAlarmFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments( getIntent().getExtras() );

            // Add the fragment to the 'flContent' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add( R.id.flContent, firstFragment ).commit();
        }
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
        Class fragmentClass;
        switch ( item.getItemId() )
        {
            case R.id.nav_alarm:
                fragmentClass = MyAlarmFragment.class;
                break;


            case R.id.nav_memory_bank:
                fragmentClass = MyAlarmFragment.class;
                break;

            default:
                fragmentClass = MyAlarmFragment.class;
        }

        try
        {
            // This is the fragment we will replace the current view with
            fragment = ( Fragment ) fragmentClass.newInstance();

        }catch ( Exception e )
        {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        // Add the fragment to the 'flContent' FrameLayout
        fragmentManager.beginTransaction().replace( R.id.flContent, fragment ).commit();

        //so this works now, but idk how to get the full view on it...

        //NOT SURE HOW TO GET THIS PART TO WORK... WE HAVE THE FRAGMENT IN NOW
        //BUT NOT ABLE TO GET THE LAYOUT INSIDE OF IT?
        //THE FRAGMENT WORKS NOW, BUT IDK HOW TO GET THE NEXT PART WITHIN IT
        //        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        //        mRecyclerView.setHasFixedSize(true);
        //        mLayoutManager = new LinearLayoutManager(this);
        //        mRecyclerView.setLayoutManager(mLayoutManager);
        //        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        //        mRecyclerView.setAdapter(mAdapter);
        //THIS PART NO IDEA HOW TO SET it though...


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
