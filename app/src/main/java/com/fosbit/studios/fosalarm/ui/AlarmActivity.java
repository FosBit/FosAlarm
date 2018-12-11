package com.fosbit.studios.fosalarm.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fosbit.studios.fosalarm.R;
import com.fosbit.studios.fosalarm.db.Alarm;
import com.fosbit.studios.fosalarm.db.FosViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlarmActivity extends AppCompatActivity {
    TextView memoryTitle;
    LinearLayout messageLayout;
    Button dismissAlarm;
    Ringtone ringtone;
    String fullMemoryMessage;
    String[] memoryMessage;
    HashMap<Integer, Pair<String, Boolean>> wordsFillMap;

    private FosViewModel mFosViewModel;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED );
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD );
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON ) ;
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_alarm );

        mFosViewModel = ViewModelProviders.of(this ).get( FosViewModel.class );
        wordsFillMap = new HashMap<Integer, Pair<String, Boolean>>();

        memoryTitle = findViewById(R.id.memory_title_textview);
        messageLayout = findViewById(R.id.memory_message_layout);
        dismissAlarm = findViewById(R.id.dismiss_alarm_button);
        dismissAlarm.setClickable( false );

        Bundle bundle = getIntent().getExtras();
        memoryTitle.setText( bundle.getString("TITLE" ) );
        fullMemoryMessage = bundle.getString("MESSAGE" );
        memoryMessage = fullMemoryMessage.split(" " );
        mFosViewModel.updateAlarms( new Alarm(
                bundle.getString("ALARMID" ),
                bundle.getLong("TIME" ),
                false, bundle.getString("MEMORYID" ) ) );
        if ( memoryMessage.length > 0 ) {
            List<LinearLayout> messageLines = new ArrayList<>();
            // Split string by spaces
            // If omitting words means there's more than 5 words left, then we can
            // replace words with a blank space for user to enter in the correct word
            if ( ( memoryMessage.length - 5 >= 5 ) ) {
                while( wordsFillMap.size() < 5 ) {
                    // (* max + 1 ) usually + 1 but the length - 1 cancels out
                    int randnum = (int) ( Math.random() * memoryMessage.length );
                    if ( !wordsFillMap.containsKey( randnum ) ) {
                        wordsFillMap.put( randnum, new Pair<>( memoryMessage[randnum], false ) );
                    }
                }

                int i = 0;
                while( i < memoryMessage.length ) {
                    int layoutWidth = messageLayout.getLayoutParams().width;
                    LinearLayout line = new LinearLayout( this );
                    line.setOrientation( LinearLayout.HORIZONTAL );
                    while ( ( layoutWidth > 0 ) && ( i < memoryMessage.length ) ) {
                        if ( !wordsFillMap.containsKey( i ) ) {
                            TextView tv = new TextView( this );
                            int width = (int)( memoryMessage[i].length() *  tv.getTextSize() );
                            layoutWidth = layoutWidth - width;
                            if ( layoutWidth <= 0 ) {
                                break;
                            }
                            tv.setLayoutParams(new LinearLayout.LayoutParams(
                                    width,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            tv.setText( memoryMessage[i] );
                            line.addView( tv );
                            i++;
                        } else {
                            final int mapKey = i;
                            EditText et = new EditText( this ); // Pass it an Activity or Context
                            int width = (int)( memoryMessage[i].length() *  et.getTextSize() );
                            layoutWidth = layoutWidth - width;
                            if ( layoutWidth <= 0 ) {
                                break;
                            }
                            et.setLayoutParams( new LinearLayout.LayoutParams(
                                    width,
                                    LinearLayout.LayoutParams.WRAP_CONTENT) );
                            et.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    String enteredText = editable.toString();
                                    String actualText  = wordsFillMap.get( mapKey ).first;
                                    //do validation here
                                    if ( enteredText.equals( actualText ) ) {
                                        if ( !wordsFillMap.get( mapKey ).second ) {
                                            wordsFillMap.remove(mapKey);
                                            wordsFillMap.put(mapKey, new Pair<>(actualText, true));
                                        }
                                    } else {
                                        if ( wordsFillMap.get( mapKey ).second ) {
                                            wordsFillMap.remove(mapKey);
                                            wordsFillMap.put(mapKey, new Pair<>(actualText, false));
                                        }
                                    }

                                    // If there is even one input that is not valid,
                                    // then do not let user dismiss the alarm.
                                    for ( Pair< String, Boolean> pair : wordsFillMap.values() )  {
                                        if ( !pair.second ) {
                                            dismissAlarm.setClickable( false );
                                            break;
                                        } else {
                                            dismissAlarm.setClickable( true );
                                        }
                                    }
                                }
                            });
                            line.addView( et );
                            i++;
                        }
                    }
                    messageLines.add( line );
                }

                for ( LinearLayout ll : messageLines ) {
                    messageLayout.addView( ll );
                }
            } else {
                EditText et = new EditText( this ); // Pass it an Activity or Context
                et.setLayoutParams( new LinearLayout.LayoutParams(
                        messageLayout.getLayoutParams().width,
                        LinearLayout.LayoutParams.WRAP_CONTENT) );
                et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String enteredText = editable.toString();
                        //do validation here
                        if ( enteredText.equals( fullMemoryMessage ) ) {
                            dismissAlarm.setClickable( true );
                        } else {
                            dismissAlarm.setClickable( true );
                        }
                    }
                });

                messageLayout.addView( et );
            }
        } else {
            dismissAlarm.setClickable( true );
        }

        // Gets default alarm to ring when alarm is sounded
        Uri alarmUri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_ALARM );
        if ( alarmUri == null ) {
            alarmUri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
            if ( alarmUri == null) {
                // alert backup is null, using 2nd backup
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }

        ringtone = RingtoneManager.getRingtone( getBaseContext(), alarmUri );
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            ringtone.setAudioAttributes(
                    new AudioAttributes.Builder().setUsage( AudioAttributes.USAGE_ALARM ).build() );
        } else {
            ringtone.setStreamType(AudioManager.STREAM_ALARM);
        }
        ringtone.play();
    }

    public void stopRingtone( View v ) {
        ringtone.stop();
        finish();
    }

}
