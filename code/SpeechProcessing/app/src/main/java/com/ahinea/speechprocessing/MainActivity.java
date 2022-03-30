package com.ahinea.speechprocessing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton playButton;
    private FloatingActionButton pauseButton;
    private FloatingActionButton stopButton;

    private SoundVisualizer visualizer;
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init buttons
        playButton = findViewById(R.id.play);
        pauseButton = findViewById(R.id.pause);
        stopButton = findViewById(R.id.stop);

        // init visualizer
        visualizer = findViewById(R.id.visualizer);

        // get data management model
        model = new ViewModelProvider(this).get(MainViewModel.class);

        // restore UI
        if (model.isRecordingInProgress()) {
            if (model.isRecording()) {
                playButton.setVisibility(View.INVISIBLE);
                pauseButton.setVisibility(View.VISIBLE);
            }
            stopButton.setVisibility(View.VISIBLE);
        }

        // connect visualizer to the data provider
        model.registerDataReceiveListener((buffer, samples) -> visualizer.onDataReceive(buffer, samples));
    }

    public void onPlayClick(View view) {
        initRecording();
        continueRecording();
    }

    public void onPauseClick(View view) {
        pauseRecording();
    }

    public void onStopClick(View view) {
        if (model.isRecordingInProgress()) {
            pauseRecording();
        }
    }

    public void onDiscardClick(View view) {
        pauseRecording();
        clearRecording();
    }

    private void initRecording() {
        if (!model.isRecordingInProgress()) {
            stopButton.setVisibility(View.VISIBLE);
        }
    }

    private void continueRecording() {
        if (!model.isRecording()) {
            playButton.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.VISIBLE);
            model.startRecording();
        }
    }

    private void pauseRecording() {
        if (model.isRecording()) {
            model.pauseRecording();
            playButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.INVISIBLE);
        }
    }

    private void clearRecording() {
        model.clearRecording();
        stopButton.setVisibility(View.INVISIBLE);
    }
}