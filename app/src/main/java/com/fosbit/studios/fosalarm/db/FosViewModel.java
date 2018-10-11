package com.fosbit.studios.fosalarm.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class FosViewModel extends AndroidViewModel {
    private FosRepositiory mRepository;
    private LiveData<List<Alarm>> mAlarms;
    private LiveData<List<Memory>> mMemories;

    public FosViewModel(Application application ) {
        super( application );
        mRepository = new FosRepositiory( application );
        mAlarms = mRepository.getAlarms();
        mMemories = mRepository.getMemories();
    }

    public LiveData<List<Alarm>> getAlarms() { return mAlarms; }

    public LiveData<List<Memory>> getMemories() { return mMemories; }

    public void insertAlarms( Alarm... alarms ) { mRepository.insertAlarms( alarms ); }

    public void insertMemories( Memory... memories ) { mRepository.insertMemories( memories ); }

    public void updateAlarms( Alarm... alarms) { mRepository.updateAlarms( alarms ); }

    public void updateMemories( Memory... memories ) { mRepository.updateMemories( memories ); }

    public void deleteAlarms( Alarm... alarms ) { mRepository.deleteAlarms( alarms ); }

    public void deleteMemories( Memory... memories ) { mRepository.deleteMemories( memories ); }
}
