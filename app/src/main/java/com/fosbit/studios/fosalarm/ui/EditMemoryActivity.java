package com.fosbit.studios.fosalarm.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.fosbit.studios.fosalarm.R;
import com.fosbit.studios.fosalarm.db.FosViewModel;
import com.fosbit.studios.fosalarm.db.Memory;

import java.util.UUID;

public class EditMemoryActivity extends AppCompatActivity {
    private FosViewModel mFosViewModel;
    EditText memoryTitle;
    EditText memoryDescription;
    Button memoryCancel;
    Button memorySave;
    boolean isNew;
    String memoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memory);

        memoryTitle = ( EditText ) findViewById( R.id.memory_title_text);
        memoryDescription = ( EditText ) findViewById( R.id.memory_description_text );
        memoryCancel = ( Button ) findViewById( R.id.cancel_memory_button );
        memorySave = ( Button ) findViewById( R.id.save_memory_button );

        mFosViewModel = ViewModelProviders.of( this ).get( FosViewModel.class );

        Bundle bundle = getIntent().getExtras();
        isNew = bundle.getBoolean( "ISNEW" );
        if ( isNew ) {
            memoryID = UUID.randomUUID().toString();
        } else {
            memoryTitle.setText( bundle.getString("TITLE" ) );
            memoryDescription.setText( bundle.getString("DESCRIPTION" ) );
            memoryID = bundle.getString( "MEMORYID" );
        }

        memoryCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                finish();
            }
        });

        memorySave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Memory memory = new Memory( memoryID,
                        memoryTitle.getText().toString(),
                        memoryDescription.getText().toString() );
                if ( isNew ) {
                    mFosViewModel.insertMemories( memory );
                } else {
                    mFosViewModel.updateMemories( memory );
                }

                finish();
            }
        });

    }
}
