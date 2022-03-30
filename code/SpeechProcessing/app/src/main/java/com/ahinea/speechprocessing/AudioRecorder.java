package com.ahinea.speechprocessing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.core.app.ActivityCompat;

public class AudioRecorder {

    /**
     * Audio recording options:
     */
    private static final int RECORDING_RATE = 44100;
    private static final int CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    private static final int FORMAT = AudioFormat.ENCODING_PCM_FLOAT;

    // the minimum buffer size needed for audio recording
    private int MIN_BUFFER_SIZE_IN_BYTES;

    private AudioRecord audioRecord;

    private final List<AudioRecorderDataReceiveListener> dataReceiveListeners;
    private final Handler recordingHandler;
    private final Handler callbacksHandler;

    public AudioRecorder() {
        MIN_BUFFER_SIZE_IN_BYTES = AudioRecord.getMinBufferSize(RECORDING_RATE, CHANNEL, FORMAT);
        if (MIN_BUFFER_SIZE_IN_BYTES == AudioRecord.ERROR || MIN_BUFFER_SIZE_IN_BYTES == AudioRecord.ERROR_BAD_VALUE) {
            // one tenth of RECORDING_RATE  * CHANNEL * sizeof(float);
            MIN_BUFFER_SIZE_IN_BYTES = RECORDING_RATE / 10 * 1 * Float.BYTES;
        }

        HandlerThread recordingThread = new HandlerThread("RecordingThread");
        HandlerThread callbacksThread = new HandlerThread("CallbacksExecThread");
        recordingThread.start();
        callbacksThread.start();

        recordingHandler = new Handler(recordingThread.getLooper());
        callbacksHandler = new Handler(callbacksThread.getLooper());
        dataReceiveListeners = new ArrayList<>();
    }

    public void registerDataReceiveListener(final AudioRecorderDataReceiveListener listener) {
        dataReceiveListeners.add(listener);
    }

    public boolean isRecording() {
        return audioRecord != null && audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING;
    }

    private boolean isStopped() {
        return audioRecord != null && audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_STOPPED;
    }

    @SuppressLint("MissingPermission")
    public void startRecording() {
        audioRecord = new AudioRecord(
                MediaRecorder.AudioSource.MIC,
                RECORDING_RATE,
                CHANNEL,
                FORMAT,
                MIN_BUFFER_SIZE_IN_BYTES);

        if (audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.d(String.valueOf(this), "Audio Record can't be initialized.");
        } else {
            Log.d(String.valueOf(this), String.format(Locale.getDefault(),
                    "Audio Client successfully created with minimum buffer of %d bytes.",
                    MIN_BUFFER_SIZE_IN_BYTES));

            startRecording(audioRecord, recordingHandler);

            Log.d(String.valueOf(this), String.format(Locale.getDefault(),
                    "%d: Recording started.", System.currentTimeMillis()));
        }
    }

    private void startRecording(AudioRecord ar, Handler handler) {
        ar.startRecording();
        handler.post(createRecurringReadAudioTask());
    }

    private void stopRecording(AudioRecord ar, Handler handler) {
        ar.stop();
        handler.removeCallbacksAndMessages(null);
    }

    private Runnable createRecurringReadAudioTask() {
        return new Runnable() {
            @Override
            public void run() {
                // schedule next read
                recordingHandler.post(createRecurringReadAudioTask());

                float[] audioBuffer = new float[MIN_BUFFER_SIZE_IN_BYTES / Float.BYTES];
                // float[] audioBuffer = new float[MIN_BUFFER_SIZE_IN_BYTES];
                int readSamples = audioRecord.read(audioBuffer, 0, audioBuffer.length, AudioRecord.READ_BLOCKING);

                if (readSamples > 0) {
                    // asynchronous samples processing listeners, each executed in a separate "CallbacksExecThread" thread
                    asyncProcessReceivedData(audioBuffer, readSamples);
                } else if (readSamples == 0) {
                    // skip
                } else {
                    // TODO ERROR
                    Log.d(String.valueOf(this), String.format(Locale.getDefault(), "Reading Error: %d.", readSamples));
                }
            }
        };
    }

    private void asyncProcessReceivedData(final float[] buffer, final int samples) {
        dataReceiveListeners.forEach(listener -> {
            callbacksHandler.post(() -> {
                listener.onDataReceive(buffer, samples);
            });
        });
    }

    public void clearRecording() {
        if (audioRecord != null) {
            stopRecording(audioRecord, recordingHandler);
            audioRecord.release();
            audioRecord = null;

            Log.d(String.valueOf(this), String.format(Locale.getDefault(),
                    "%d: Recording cleared.", System.currentTimeMillis()));
        }
    }

    public void pauseRecording() {
        if (isRecording()) {
            stopRecording(audioRecord, recordingHandler);

            Log.d(String.valueOf(this), String.format(Locale.getDefault(),
                    "%d: Recording paused.", System.currentTimeMillis()));
        } else {
            Log.d(String.valueOf(this), "Attempt to pause recording while not recording.");
        }
    }

    public void continueRecording() {
        if (isStopped()) {
            startRecording(audioRecord, recordingHandler);
        } else {
            Log.d(String.valueOf(this), "Attempt to continue recording before initializing recording context or while already recording.");
        }
    }

}
