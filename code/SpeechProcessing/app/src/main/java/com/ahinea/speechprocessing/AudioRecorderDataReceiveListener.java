package com.ahinea.speechprocessing;

/**
 * Defines the listener for AudioRecorder, onDataReceive is called after
 * data was successfully read from the recording AudioRecord.
 */
public interface AudioRecorderDataReceiveListener {

    void onDataReceive(final float[] buffer, final int samples);

}
