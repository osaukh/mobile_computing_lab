package com.ahinea.speechprocessing;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

// view model maintaining the data
public class MainViewModel extends AndroidViewModel {

    private AudioRecorder recorder;

    private boolean recordingInProgress;

    public MainViewModel(@NonNull Application application) {
        super(application);
        recorder = new AudioRecorder();
    }

    public boolean isRecordingInProgress() {
        return recordingInProgress;
    }

    public void registerDataReceiveListener(final AudioRecorderDataReceiveListener listener) {
        recorder.registerDataReceiveListener(listener);
    }

    public boolean isRecording() {
        return recorder.isRecording();
    }

    public void startRecording() {
        if (!recordingInProgress) {
            recordingInProgress = true;
            recorder.startRecording();
        } else {
            recorder.continueRecording();
        }
    }

    public void pauseRecording() {
        recorder.pauseRecording();
    }

    public void clearRecording() {
        recordingInProgress = false;
        recorder.clearRecording();
    }
}
