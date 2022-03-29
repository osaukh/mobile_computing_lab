# Workshop 2: Speech Processing


### 1. Speech Processing Fundamentals

Run the tutorial in Google Colab:

<table>
  <td>
    <a target="_blank" href="https://colab.research.google.com/github/osaukh/mobile_computing_lab/blob/master/workshops/WS02--SpeechProcessing--Fundamentals.ipynb">
    <img src="https://www.tensorflow.org/images/colab_logo_32px.png" />
    Run in Google Colab</a>
  </td>
  <td>
    <a target="_blank" href="https://github.com/osaukh/mobile_computing_lab/blob/master/workshops/WS02--SpeechProcessing--Fundamentals.ipynb">
    <img src="https://www.tensorflow.org/images/GitHub-Mark-32px.png" />
    View source on GitHub</a>
  </td>
</table>


### 2. Audio Processing and Deep Learning

In recent years, great results have been achieved in generating and processing images with neural networks. This can partly be attributed to the great performance of deep CNNs to capture and transform high-level information in images. However, we are still in the early days of AI application in audio processing. Deep learning methods allow us to approach signal processing problems from a new perspective which is still largely ignored in the audio industry. The understanding of sound is a very complex task and problems that we, as humans, intuitively find quite easy often turn out to be very difficult to solve for a machine.

__Source separation example.__ 
In a scenario where two people are speaking over each other, in your mind, you can imagine either person speaking in isolation without much effort. But how do we describe a formula for separating these two voices? Is there a unified way to describe how human voices sound? If yes, how are parameters of this description affected by sex, age, energy, personality? How does physical proximity to the listener and room acoustics impact this understanding? What about non-human noise that can occur during the recording? On which parameters can we discriminate one voice over another?

As you can see, devising a formula for the full extent of this problem would require attention to a lot of parameters. Here, AI can provide a more pragmatic approach. See a research paper below, which uses a convolutional recurrent neural network architecture.

[Low Latency Sound Source Separation Using Convolutional Recurrent Neural Networks](http://www.cs.tut.fi/~tuomasv/papers/PID4978439.pdf), 2017.

One of the interesting early publications on audio processing with deep learning was the publication of [Google Deepmind’s “WaveNet”](https://deepmind.com/blog/wavenet-generative-model-raw-audio/) - a deep learning model for generating audio recordings which was released in 2016. Using an adapted network architecture, a dilated convolutional neural network, Deepmind researchers succeeded in generating very convincing text-to-speech and some interesting music-like recordings trained from classical piano recordings.

<img src="https://miro.medium.com/max/2572/1*0TbaaX8l86ghbGEhuSjPzw.jpeg" width="700"/>

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

Run the app on your smartphone and undestand the code: 
* [Source code](code/SpeechProcessing) (kudos for some parts of the code to Jakub Lukac).
* When running the app on your smartphone for the first time, grant it permissions to access the microphone: `Settings -> Apps -> SpeechProcessing -> Permissions`.

