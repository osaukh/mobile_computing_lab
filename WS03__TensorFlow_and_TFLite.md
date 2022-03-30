# Workshop 3: TensorFlow and TF Lite

### 1. TensorFlow

TensorFlow is a programming paradigm that Google developed internally for Machine Learning. TensorFlow follows a Dataflow-like paradigm. TensorFlow programs get converted into a directed graph. This graph represents a dataflow computation with data flowing from nodes via the graph edges. Each node has multiple inputs and outputs and it represents a certain operation. Most of the data that flows in the graph is represented as Tensor. Tensors are typed arrays of arbitrary dimensions — a construct quite useful when your are dealing with Matrices or system of equations.

__Follow the quickstart tutorial__ which uses Keras (a high-level neural network library that runs on top of TensorFlow):

<table>
  <td>
    <a target="_blank" href="https://colab.research.google.com/github/tensorflow/docs/blob/master/site/en/tutorials/quickstart/beginner.ipynb">
    <img src="https://www.tensorflow.org/images/colab_logo_32px.png" />
    Run in Google Colab</a>
  </td>
  <td>
    <a target="_blank" href="https://github.com/tensorflow/docs/blob/master/site/en/tutorials/quickstart/beginner.ipynb">
    <img src="https://www.tensorflow.org/images/GitHub-Mark-32px.png" />
    View source on GitHub</a>
  </td>
</table>

### 2. TensorFlow Lite

TensorFlow Lite is a set of tools to help developers run TensorFlow models on mobile, embedded, and IoT devices. It enables on-device machine learning inference with low latency and a small binary size.

TensorFlow Lite consists of two main components:
* __TensorFlow Lite interpreter__, which runs specially optimized models on many different hardware types, including mobile phones, embedded Linux devices, and microcontrollers.
* __TensorFlow Lite converter__, which converts TensorFlow models into an efficient form for use by the interpreter, and can introduce optimizations to improve binary size and performance.

<img src="https://camo.githubusercontent.com/e2dc220875ce6c47a653c4a3c8bc7ccfe4c578958301095adbafbfa6e34f3618/68747470733a2f2f7777772e74656e736f72666c6f772e6f72672f696d616765732f74666c6974652d6172636869746563747572652e6a7067" width=400>

__Read about TF Lite and Android NN API:__
* [TensorFlow Lite converter](https://www.tensorflow.org/lite/convert)
* [Android NN API](https://developer.android.com/ndk/guides/neuralnetworks)

TF Lite is designed to make it easy to perform machine learning on devices, "at the edge" of the network, instead of sending data back and forth from a server. For developers, performing machine learning on-device can help improve:

* Latency: there's no round-trip to a server
* Privacy: no data needs to leave the device
* Connectivity: an Internet connection isn't required
* Power consumption: network connections are power hungry

TF Lite works with a huge range of devices, from tiny microcontrollers to powerful mobile phones.

### 3. Example: Handwritten Digits

This is a handwritten character image (MNIST) classifier that can run on any Android device. The app stores a model  set of images (0-9) that we can cycle through and classify in order. It uses a pre-trained model to perform inference on the device. This idea can be applied to any images, both by using the camera and by pulling from the Web. We're using preloaded images so we can run the app in a simulator (no need for the device since it doesn't require a camera).

<img src="https://github.com/llSourcell/A_Guide_to_Running_Tensorflow_Models_on_Android/raw/master/images/demo.png" width="600">

* [Video Demo](https://www.youtube.com/watch?v=gahi0Hjgokw)
* [App Source Code](https://github.com/llSourcell/A_Guide_to_Running_Tensorflow_Models_on_Android/tree/master/mnistandroid)


__Run the tutorial to train a model:__

<table>
  <td>
    <a target="_blank" href="https://colab.research.google.com/github/osaukh/mobile_computing_lab/blob/master/workshops/WS03--TensorFlow--HandwrittenDigits.ipynb">
    <img src="https://www.tensorflow.org/images/colab_logo_32px.png" />
    Run in Google Colab</a>
  </td>
  <td>
    <a target="_blank" href="https://github.com/osaukh/mobile_computing_lab/blob/master/workshops/WS03--TensorFlow--HandwrittenDigits.ipynb">
    <img src="https://www.tensorflow.org/images/GitHub-Mark-32px.png" />
    View source on GitHub</a>
  </td>
</table>


__Android app:__

* <a href="https://developer.android.com/ndk/downloads/">Download and install Android NDK</a> (you should get an automatic invitation to install NDK from Android Studio once you try to build an app which requires its support).

* Clone and run the [HandWrittenDigits Android app](https://github.com/osaukh/mobile_computing_lab/tree/master/code/HandWrittenDigits). It requires that you use TensorFlow verion > 1.9.1. Examine the code and run it on your hardware. You can also experiment with the model above and use your own model (copy your `.tflite` file to `HandWrittenDigits/app/src/main/assets/`).


***
## References, Credits and Further Readings

* [TensorFlow Lite guide](https://www.tensorflow.org/lite/guide)
* <a href="https://www.oreilly.com/learning/not-another-mnist-tutorial-with-tensorflow">Not another MNIST tutorial with TensorFlow</a>
* <a href="https://heartbeat.fritz.ai/intro-to-machine-learning-on-android-how-to-convert-a-custom-model-to-tensorflow-lite-e07d2d9d50e3">Intro to Machine Learning on Android — How to convert a custom model to TensorFlow Lite</a>
* <a href="https://heartbeat.fritz.ai/introduction-to-machine-learning-on-android-part-2-building-an-app-to-recognize-handwritten-d58ebc01950">Intro to Machine Learning on Android (Part 2): Building an app to recognize handwritten digits with TensorFlow Lite</a>
* Medium [How TensorFlow Lite Optimizes Neural Networks for Mobile Machine Learning](https://heartbeat.fritz.ai/how-tensorflow-lite-optimizes-neural-networks-for-mobile-machine-learning-e6ffa7f8ee12), 2019
* YouTube video by Siraj Raval: <a href="https://www.youtube.com/watch?v=kFWKdLOxykE&feature=youtu.be">A Guide to Running Tensorflow Models on Android</a>
* <a href="https://github.com/llSourcell/A_Guide_to_Running_Tensorflow_Models_on_Android">Original app source code for older versions of TensorFlow (<= 1.5.0)</a> adjusted by Siraj Raval
* Original <a href="https://github.com/miyosuda/TensorFlowAndroidMNIST">app source code</a>