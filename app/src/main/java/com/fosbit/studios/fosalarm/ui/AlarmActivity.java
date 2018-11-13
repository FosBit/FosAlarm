package com.fosbit.studios.fosalarm.ui;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fosbit.studios.fosalarm.R;

import org.w3c.dom.Text;

public class AlarmActivity extends AppCompatActivity {
    TextView memoryTitle;
    EditText memoryMessage;
    Button dismissAlarm;
    Ringtone ringtone;
    String memoryValidation;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        memoryTitle = ( TextView ) findViewById( R.id.memory_title_textview);
        memoryMessage = ( EditText ) findViewById( R.id.memory_message );
        dismissAlarm = ( Button ) findViewById( R.id.dismiss_alarm_button );
        dismissAlarm.setClickable( false );

        Bundle bundle = getIntent().getExtras();
        memoryTitle.setText( bundle.getString( "TITLE" ) );
        memoryValidation = bundle.getString( "MESSAGE" );

        memoryMessage.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override public void afterTextChanged(Editable s) {
                String enteredText = s.toString();
                //do validation here
                if ( enteredText.equals( memoryValidation ) ) {
                    dismissAlarm.setClickable( true );
                }
            }
        });

        // Gets default alarm to ring when alarm is sounded
        Uri alarmUri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_ALARM );
        if ( alarmUri == null ) {
            alarmUri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
        }
        ringtone = RingtoneManager.getRingtone( getBaseContext(), alarmUri );
        ringtone.play();
    }

    public void stopRingtone( View v ) {
        ringtone.stop();

        finish();
    }

}
