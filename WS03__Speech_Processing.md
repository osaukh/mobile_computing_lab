# Workshop 3: Speech Processing


### 1. Speech Processing Fundamentals

<mark>Run the tutorial in Google Colab:</mark>

<table>
  <td>
    <a target="_blank" href="https://colab.research.google.com/github/osaukh/mobile_computing_lab/blob/master/colab/WS03_SpeechProcessing_Fundamentals.ipynb">
    <img src="https://www.tensorflow.org/images/colab_logo_32px.png" />
    Run in Google Colab</a>
  </td>
  <td>
    <a target="_blank" href="https://github.com/osaukh/mobile_computing_lab/blob/master/colab/WS03_SpeechProcessing_Fundamentals.ipynb">
    <img src="https://www.tensorflow.org/images/GitHub-Mark-32px.png" />
    View source on GitHub</a>
  </td>
</table>


### 2. Audio Processing and Deep Learning

In recent years, great results have been achieved in generating and processing images with neural networks. This can partly be attributed to the great performance of deep CNNs to capture and transform high-level information in images. However, we are still in the early days of AI application in audio processing. Deep learning methods allow us to approach signal processing problems from a new perspective which is still largely ignored in the audio industry. The understanding of sound is a very complex task and problems that we, as humans, intuitively find quite easy often turn out to be very difficult to solve for a machine.

__Source separation example.__ 
In a scenario where two people are speaking over each other, in your mind, you can imagine either person speaking in isolation without much effort. But how do we describe a formula for separating these two voices? Is there a unified way to describe how human voices sound? If yes, how are parameters of this description affected by sex, age, energy, personality? How does physical proximity to the listener and room acoustics impact this understanding? What about non-human noise that can occur during the recording? On which parameters can we discriminate one voice over another?

As you can see, devising a formula for the full extent of this problem would require attention to a lot of parameters. Here, AI can provide a more pragmatic approach. One of the interesting early publications on audio processing with deep learning was the publication of [Google Deepmind’s “WaveNet”](https://www.deepmind.com/blog/wavenet-a-generative-model-for-raw-audio) - a deep learning model for generating audio recordings which was released in 2016. Using an adapted network architecture, a dilated convolutional neural network, Deepmind researchers succeeded in generating very convincing text-to-speech and some interesting music-like recordings trained from classical piano recordings.

<img src="https://assets-global.website-files.com/621e749a546b7592125f38ed/62227b1d1dd26da452c9e160_unnamed-2.gif" width="700"/>

WaveNet was among the first successful attempts to generate audio on a raw sample level. The one big problem here is that CD quality audio is usually stored with 44.1 kHz samples per second and thus, generating *seconds* of sound with WaveNet takes *hours*. This excludes the method from having use in real-time applications. It’s just a lot of data to make sense of.

Convolutional neural networks are designed with inspiration from the human visual system, loosely based on how information flows into the visual cortex. However, there are several problems related to this approach. Essentially, we are taking audio, translating it to images and performing visual processing on that image before translating it back to audio. So, we are doing machine vision to do machine hearing. But, these two senses don’t function in the same way. Looking at the spectrogram below, how much meaning can you (with your smart human brain) actually gather about the content of the audio? If you could listen to it, you would quickly get an intuitive understanding of what is happening.

<img src="https://miro.medium.com/max/1356/1*sbXTep0McbTJUxQUGCo21Q.png" width="600"/>

__Legend__: A five-second spectrogram. Can you tell what it is? (It’s a blues harp.)


#### Why is audio processing with deep learning challenging?

There are several challanges related to applying deep learning to audio processing:

__Sounds are “transparent”.__
One challenge posed in the comparison between visual images and spectrograms is the fact that visual objects and sound events do not accumulate in the same manner. To use a visual analogy, one could say that sounds are always “transparent” whereas most visual objects are opaque. When encountering a pixel of a certain color in an image, it can most often be assumed to belong to a single object. Discrete sound events do not separate into layers on a spectrogram: Instead, they all sum together into a distinct whole. *That means that a particular observed frequency in a spectrogram cannot be assumed to belong to a single sound as the magnitude of that frequency could have been produced by any number of accumulated sounds or even by the complex interactions between sound waves such as phase cancellation. This makes it difficult to separate simultaneous sounds in spectrogram representations.*

<img src="https://miro.medium.com/max/4000/1*M94npWRtDfdyqTj4M5YbAw.png" width="600"/>

__Legend__: Three examples of difficult scenarios of spectrogram analysis. (Left): Two similar tones cause uneven phase cancellations across frequencies. (Middle): Two simultaneous voices with similar pitch are difficult to tell apart. (Right): Noisy and complex auditory scenes make it particularly difficult to distinguish sound events.

__The axes of spectrograms do not carry the same meaning.__
CNNs for images use two-dimensional filters that share weights across the x and y dimensions. This builds on the assumption that features of an image carry the same meaning regardless of their location. For this to be true, you should also assume that the x and y axes of the data have the same implications to the meaning of the content. For example, a face is still a face regardless of whether it is moved horizontally or vertically in an image.

In spectrograms, the two dimensions represent fundamentally different units, one being strength of frequency and the other being time. Moving a sound event horizontally offsets its position in time and it can be argued that a sound event means the same thing regardless of when it happens. However, moving a sound vertically might influence its meaning: Moving the frequencies of a male voice upwards could change its meaning from man to child or goblin, for example. Therefore, the spatial invariance that 2D CNNs provide might not perform as well for this form of data.

__The spectral properties of sounds are non-local.__
In images, similar neighboring pixels can often be assumed to belong to the same visual object but in sound, frequencies are most often non-locally distributed on the spectrogram. Periodic sounds are typically comprised of a fundamental frequency and a number of harmonics which are spaced apart by relationships dictated by the source of the sound. It is the mixture of these harmonics that determines the timbre of the sound.

In the instance of a female vocal, the fundamental frequency at a moment in time might be 200Hz while the first harmonic is 400Hz, the next 600Hz and so on. These frequencies are not locally grouped but they move together according to a common relationship. This further complicates the task of finding local features in spectrograms using 2D convolutions as they are often unevenly spaced apart even though they move according to the same factors.

__Sound is inherently serial.__
When assessing a visual environment, we can “scan” our surroundings multiple times to locate each visual object in a scene. Since most objects are non-moving, light will reflect from them in a predictable manner and one can make a mental map of their placement in a physical scene. From a perceptual point of view then, the visual objects are assumed to continue to exist at their observed location, even when you look elsewhere.

This is not true for sounds. Sound takes the physical form of pressure waves and, from the point of view of a listener, such waves exist only in their current state at one moment in time. Once the moment has passed, the wave has passed by, traveling away from the observer. This is why it makes sense to refer to these phenomena as sound events rather than objects. From a physical perspective, this means that listeners experience sound only a moment at a time. Where images can be regarded to contain larger amounts of static parallel information, sound, then, is highly serial.

These reasons suggest that audio as a medium for conveying meaning is fundamentally serial and more temporally dependent than video which presents another reason why visual spectrogram representations of sounds fed into image processing networks without temporal awareness might not work optimally.

### 3. Audio Processing Basic App

You are provided the code of a sample app which reads the microphone input and shows the audio wave on the screen. The app should give you a good starting point to implement and test different speech processing algorithms. Below is a screenshot of the SpeechProcessing app in action:

<img src="https://github.com/osaukh/mobile_computing_lab/blob/master/img/speechprocessing/speech_processing_screenshot.jpg" width="240">

<mark>Run the app on your smartphone and undestand the code:</mark>
* [SpeechProcessing Android App](code/SpeechProcessing) (kudos for some parts of the code to Jakub Lukac).
* When running the app on your smartphone for the first time, grant it permissions to access the microphone: `Settings -> Apps -> SpeechProcessing -> Permissions`.


#### Notes on the Code

Some important settings we use in __AudioRecorder__ class: 
* Sampling rate of the audio signal is 44.1 kHz; 
* Only one channel for processing (mono, no stereo); 
* Encoding is 32 bit IEEE single precision float values (`ENCODING_PCM_FLOAT`). Note that this encoding requires at least API level 22 (see [Platform codenames, versions, API levels](https://source.android.com/setup/start/build-numbers).

Android audio format options are detailed in the manual [AudioFormat](https://developer.android.com/reference/android/media/AudioFormat).

```Java
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class AudioRecorder {
    ...
    private static final int RECORDING_RATE = 44100;
    private static final int CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    private static final int FORMAT = AudioFormat.ENCODING_PCM_FLOAT;
    ...
}

```

We create an instance of the `AudioRecord` class to start recording audio data:

```Java
public class AudioRecorder {
    ...
    private AudioRecord audioRecord;
    
    public void startRecording() {
        audioRecord = new AudioRecord(
                MediaRecorder.AudioSource.MIC,
                RECORDING_RATE,
                CHANNEL,
                FORMAT,
                MIN_BUFFER_SIZE_IN_BYTES);
        ...
            
        audioRecord.startRecording();
        ...
   }
   ...
```

In addition, we need to execute tasks within two threds: (1) periodically read audio data from the buffer, and (2) process the data by the registered callbacks implemented by the listeners.

```Java
public class AudioRecorder {
    ...
    private final List<AudioRecorderDataReceiveListener> dataReceiveListeners;    
    private final Handler recordingHandler;
    private final Handler callbacksHandler;
    ...
        
    public AudioRecorder() {
        ...
        HandlerThread recordingThread = new HandlerThread("RecordingThread");
        HandlerThread callbacksThread = new HandlerThread("CallbacksExecThread");
        recordingThread.start();
        callbacksThread.start();

        recordingHandler = new Handler(recordingThread.getLooper());
        callbacksHandler = new Handler(callbacksThread.getLooper());
        dataReceiveListeners = new ArrayList<>();
        ...
    }
    
    public void startRecording() {
        ...
        handler.post(createRecurringReadAudioTask());
    }
    
    private Runnable createRecurringReadAudioTask() {
        return new Runnable() {
            @Override
            public void run() {
                // schedule next read
                recordingHandler.post(createRecurringReadAudioTask());
                ...
                int readSamples = audioRecord.read(
                    audioBuffer, 0, audioBuffer.length, AudioRecord.READ_BLOCKING);
                ...
                if (readSamples > 0) {
                    // asynchronous samples processing listeners, 
                    // each executed in a separate "CallbacksExecThread" thread
                    asyncProcessReceivedData(audioBuffer, readSamples);
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
    ...
}
```

Finally, the `SoundVisualizer` class is a listener, which visualizes the data on the screen.


```Java
public class SoundVisualizer extends View implements AudioRecorderDataReceiveListener {
    ...
    public void onDataReceive(final float[] data, final int length) {
        // process data
    }
    ...
}
```

The app can be used as a starting point to implement a useful audio processing function.

<mark>__Exercise:__</mark> Extend the SpeechProcessing app to periodically compute a spectogram from a fragment of the signal and display the results on the screen.


### 4. (Optional) Example: Audio Command Recognition

This tutorial is based on the [Simple audio recognition: Recognizing keywords](https://www.tensorflow.org/tutorials/audio/simple_audio) tutorial by TensorFlow but makes it also work on a mobile phone. It shows how to build and run a simple speech recognition TF model. Once you’ve completed this tutorial, you’ll have an application that tries to classify a one second audio clip as either silence, an unknown word, “yes”, “no”, “up”, “down”, “left”, “right”, “on”, “off”, “stop”, or “go”.

<img src="https://androidkt.com/wp-content/uploads/2017/09/speech-recognition-model.gif" width="250">


* [Pre-trained Model](https://github.com/EN10/SimpleSpeech/tree/master/test)
* [Model Code and Tutorial](https://www.tensorflow.org/tutorials/audio/simple_audio)
* [App Source Code](https://github.com/Thumar/audio-recognition)


<mark>Follow the TensorFlow tutorial:</mark>

Use Google Colab: [Simple audio recognition: Recognizing keywords](https://colab.research.google.com/github/tensorflow/docs/blob/master/site/en/tutorials/audio/simple_audio.ipynb)

<table>
  <td>
    <a target="_blank" href="https://colab.research.google.com/github/tensorflow/docs/blob/master/site/en/tutorials/audio/simple_audio.ipynb">
    <img src="https://www.tensorflow.org/images/colab_logo_32px.png" />
    Run in Google Colab</a>
  </td>
  <td>
    <a target="_blank" href="https://github.com/tensorflow/docs/blob/master/site/en/tutorials/audio/simple_audio.ipynb">
    <img src="https://www.tensorflow.org/images/GitHub-Mark-32px.png" />
    View source on GitHub</a>
  </td>
</table>

You can train your model on your laptop, or on a server, and then use that pre-trained model on our mobile device. Alternatively, you can use an already [pre-trained model](http://download.tensorflow.org/models/speech_commands_v0.01.zip).


<mark>Run the Android app:</mark>

Android [app source code](https://github.com/Thumar/audio-recognition). You need to copy model files to the assets folder and specify correct paths in `MainActivity.java`.

To request microphone, you should be requesting `RECORD_AUDIO` permission in your manifest file as below:

```XML
<uses-permission android:name="android.permission.RECORD_AUDIO"/>
```

Since Android 6.0 Marshmallow, the application will not be granted any permission at installation time. Instead, the application has to ask the user for a permission one-by-one at runtime.

```Java
private void requestMicrophonePermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{android.Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
    }
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
      if (requestCode == REQUEST_RECORD_AUDIO&& grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startRecording();
            startRecognition();
      }
 }
```

The `AudioRecord` class manages the audio resources for Java applications to record audio from the audio input hardware of the platform. This is achieved by “pulling” (reading) the data from the `AudioRecord` object. The application is responsible for polling the AudioRecord object in time using `read(short[], int, int)`.

```Java
private void record() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);
 
        // Estimate the buffer size we'll need for this device.
        int bufferSize =
                AudioRecord.getMinBufferSize(
                        SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
            bufferSize = SAMPLE_RATE * 2;
        }
        short[] audioBuffer = new short[bufferSize / 2];
 
        AudioRecord record =
                new AudioRecord(
                        MediaRecorder.AudioSource.DEFAULT,
                        SAMPLE_RATE,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT,
                        bufferSize);
 
        if (record.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.e(LOG_TAG, "Audio Record can't initialize!");
            return;
        }
 
        record.startRecording();
 
        Log.v(LOG_TAG, "Start recording");
 
        // Loop, gathering audio data and copying it to a round-robin buffer.
        while (shouldContinue) {
            int numberRead = record.read(audioBuffer, 0, audioBuffer.length);
            int maxLength = recordingBuffer.length;
            int newRecordingOffset = recordingOffset + numberRead;
            int secondCopyLength = Math.max(0, newRecordingOffset - maxLength);
            int firstCopyLength = numberRead - secondCopyLength;
            // We store off all the data for the recognition thread to access. The ML
            // thread will copy out of this buffer into its own, while holding the
            // lock, so this should be thread safe.
            recordingBufferLock.lock();
            try {
                System.arraycopy(audioBuffer, 0, recordingBuffer, recordingOffset, firstCopyLength);
                System.arraycopy(audioBuffer, firstCopyLength, recordingBuffer, 0, secondCopyLength);
                recordingOffset = newRecordingOffset % maxLength;
            } finally {
                recordingBufferLock.unlock();
            }
        }
 
        record.stop();
        record.release();
    }
```

A `TensorFlowInferenceInterface` class that provides a smaller API surface suitable for inference and summarizing the performance of model execution.

```Java
private void recognize() {
    Log.v(LOG_TAG, "Start recognition");
 
    short[] inputBuffer = new short[RECORDING_LENGTH];
    float[] floatInputBuffer = new float[RECORDING_LENGTH];
    float[] outputScores = new float[labels.size()];
    String[] outputScoresNames = new String[]{OUTPUT_SCORES_NAME};
    int[] sampleRateList = new int[]{SAMPLE_RATE};
 
    // Loop, grabbing recorded data and running the recognition model on it.
    while (shouldContinueRecognition) {
            // The recording thread places data in this round-robin buffer, so lock to
            // make sure there's no writing happening and then copy it to our own
            // local version.
       recordingBufferLock.lock();
       try {
           int maxLength = recordingBuffer.length;
           int firstCopyLength = maxLength - recordingOffset;
           int secondCopyLength = recordingOffset;
           System.arraycopy(recordingBuffer, recordingOffset, inputBuffer, 0, firstCopyLength);
           System.arraycopy(recordingBuffer, 0, inputBuffer, firstCopyLength, secondCopyLength);
        } finally {
           recordingBufferLock.unlock();
         }
 
            // We need to feed in float values between -1.0f and 1.0f, so divide the
            // signed 16-bit inputs.
         for (int i = 0; i < RECORDING_LENGTH; ++i) {
             floatInputBuffer[i] = inputBuffer[i] / 32767.0f;
         }
 
            // Run the model.
       inferenceInterface.feed(SAMPLE_RATE_NAME, sampleRateList);
       inferenceInterface.feed(INPUT_DATA_NAME, floatInputBuffer, RECORDING_LENGTH, 1);
       inferenceInterface.run(outputScoresNames);
       inferenceInterface.fetch(OUTPUT_SCORES_NAME, outputScores);
 
       // Use the smoother to figure out if we've had a real recognition event.
       long currentTime = System.currentTimeMillis();
       final RecognizeCommands.RecognitionResult result = recognizeCommands.processLatestResults(outputScores, currentTime);
 
       runOnUiThread(
             new Runnable() {
                 @Override
                 public void run() {
                   // If we do have a new command, highlight the right list entry.
                      if (!result.foundCommand.startsWith("_") && result.isNewCommand) {
                            int labelIndex = -1;
                            for (int i = 0; i < labels.size(); ++i) {
                             if (labels.get(i).equals(result.foundCommand)) {
                                    labelIndex = i;
                              }
                         }
                         label.setText(result.foundCommand);
                    }
                }
              });
        try {
            // We don't need to run too frequently, so snooze for a bit.
            Thread.sleep(MINIMUM_TIME_BETWEEN_SAMPLES_MS);
        } catch (InterruptedException e) {
         // Ignore
        }
    }
 
    Log.v(LOG_TAG, "End recognition");
}
```

`RecognizeCommands` class is fed the output of running the `TensorFlow` model over time, it averages the signals and returns information about a label when it has enough evidence to think that a recognized word has been found. The implementation is fairly small, just keeping track of the last few predictions and averaging them.

```Java
public RecognitionResult processLatestResults(float[] currentResults, long currentTimeMS) {
        if (currentResults.length != labelsCount) {
            throw new RuntimeException(
                    "The results for recognition should contain "
                            + labelsCount
                            + " elements, but there are "
                            + currentResults.length);
        }
 
        if ((!previousResults.isEmpty()) && (currentTimeMS < previousResults.getFirst().first)) {
            throw new RuntimeException(
                    "You must feed results in increasing time order, but received a timestamp of "
                            + currentTimeMS
                            + " that was earlier than the previous one of "
                            + previousResults.getFirst().first);
        }
 
        final int howManyResults = previousResults.size();
        // Ignore any results that are coming in too frequently.
        if (howManyResults > 1) {
            final long timeSinceMostRecent = currentTimeMS - previousResults.getLast().first;
            if (timeSinceMostRecent < minimumTimeBetweenSamplesMs) {
                return new RecognitionResult(previousTopLabel, previousTopLabelScore, false);
            }
        }
 
        // Add the latest results to the head of the queue.
        previousResults.addLast(new Pair<Long, float[]>(currentTimeMS, currentResults));
        Log.d(TAG, currentResults + " " + currentTimeMS);
 
        // Prune any earlier results that are too old for the averaging window.
        final long timeLimit = currentTimeMS - averageWindowDurationMs;
        while (previousResults.getFirst().first < timeLimit) {
            previousResults.removeFirst();
        }
        // If there are too few results, assume the result will be unreliable and
        // bail.
        final long earliestTime = previousResults.getFirst().first;
        final long samplesDuration = currentTimeMS - earliestTime;
        if ((howManyResults < minimumCount)
                || (samplesDuration < (averageWindowDurationMs / MINIMUM_TIME_FRACTION))) {
            Log.v("RecognizeResult", "Too few results");
            return new RecognitionResult(previousTopLabel, 0.0f, false);
        }
 
        // Calculate the average score across all the results in the window.
        float[] averageScores = new float[labelsCount];
        for (Pair<Long, float[]> previousResult : previousResults) {
            final float[] scoresTensor = previousResult.second;
            int i = 0;
            while (i < scoresTensor.length) {
                averageScores[i] += scoresTensor[i] / howManyResults;
                ++i;
            }
        }
 
        // Sort the averaged results in descending score order.
        ScoreForSorting[] sortedAverageScores = new ScoreForSorting[labelsCount];
        for (int i = 0; i < labelsCount; ++i) {
            sortedAverageScores[i] = new ScoreForSorting(averageScores[i], i);
        }
        Arrays.sort(sortedAverageScores);
 
        // See if the latest top score is enough to trigger a detection.
        final int currentTopIndex = sortedAverageScores[0].index;
        final String currentTopLabel = labels.get(currentTopIndex);
        final float currentTopScore = sortedAverageScores[0].score;
        // If we've recently had another label trigger, assume one that occurs too
        // soon afterwards is a bad result.
        long timeSinceLastTop;
        if (previousTopLabel.equals(SILENCE_LABEL) || (previousTopLabelTime == Long.MIN_VALUE)) {
            timeSinceLastTop = Long.MAX_VALUE;
        } else {
            timeSinceLastTop = currentTimeMS - previousTopLabelTime;
        }
        boolean isNewCommand;
        if ((currentTopScore > detectionThreshold) && (timeSinceLastTop > suppressionMs)) {
            previousTopLabel = currentTopLabel;
            previousTopLabelTime = currentTimeMS;
            previousTopLabelScore = currentTopScore;
            isNewCommand = true;
        } else {
            isNewCommand = false;
        }
        return new RecognitionResult(currentTopLabel, currentTopScore, isNewCommand);
    }
```

The demo app updates its UI of results automatically based on the labels text file you copy into assets alongside your frozen graph, which means you can easily try out different models without needing to make any code changes. You will need to update `LABEL_FILENAME and MODEL_FILENAME` to point to the files you’ve added if you change the paths though.

You can easily replace it with a model you’ve trained yourself. If you do this, you’ll need to make sure that the constants in the main `MainActivity Java` source file like `SAMPLE_RATE` and `SAMPLE_DURATION` match any changes you’ve made to the defaults while training. You’ll also see that there’s a Java version of the `RecognizeCommands` module that’s very similar to the C++ version in this tutorial. If you’ve tweaked parameters for that, you can also update them in `MainActivity` to get the same results as in your server testing.



### References, Credits and Further Readings

* Medium: [Effects of spectrogram pre-processing for audio classification](https://medium.com/using-cnn-to-classify-audio/effects-of-spectrogram-pre-processing-for-audio-classification-a551f3da5a46)
* Medium: [Understanding Audio data, Fourier Transform, FFT and Spectrogram features for a Speech Recognition System](https://towardsdatascience.com/understanding-audio-data-fourier-transform-fft-spectrogram-and-speech-recognition-a4072d228520)
* __A series of posts by Daniel Rothmann__:
    * Medium: [The promise of AI in audio processing](https://towardsdatascience.com/the-promise-of-ai-in-audio-processing-a7e4996eb2ca)
    * Medium: [Human-Like Machine Hearing With AI (1/3)](https://towardsdatascience.com/human-like-machine-hearing-with-ai-1-3-a5713af6e2f8)
    * Medium: [Human-Like Machine Hearing With AI (2/3)](https://towardsdatascience.com/human-like-machine-hearing-with-ai-2-3-f9fab903b20a)
    * Medium: [Human-Like Machine Hearing With AI (3/3)](https://towardsdatascience.com/human-like-machine-hearing-with-ai-3-3-fd6238426416)
    * Medium: [What’s wrong with CNNs and spectrograms for audio processing?](https://towardsdatascience.com/whats-wrong-with-spectrograms-and-cnns-for-audio-processing-311377d7ccd)
* Medium: [Using LibROSA to extract audio features](https://medium.com/tencent-thailand/music-information-retrieval-part-1-using-librosa-to-extract-audio-features-6e8569537185)
* Medium: [Create an Audio Recorder for Android](https://medium.com/@ssaurel/create-an-audio-recorder-for-android-94dc7874f3d)
* Medium: [Getting to Know the Mel Spectrogram](https://towardsdatascience.com/getting-to-know-the-mel-spectrogram-31bca3e2d9d0)
* Medium: [The dummy’s guide to MFCC](https://medium.com/prathena/the-dummys-guide-to-mfcc-aceab2450fd)
* Medium: [Sound Classification with TensorFlow](https://medium.com/iotforall/sound-classification-with-tensorflow-8209bdb03dfb)
* [Audio classification using Image classification techniques](https://www.codementor.io/vishnu_ks/audio-classification-using-image-classification-techniques-hx63anbx1)
* [Simple Audio Recognition](https://www.tensorflow.org/tutorials/audio_recognition)
* [Speech Recognition Using TensorFlow](http://androidkt.com/speech-recognition-using-tensorflow/)