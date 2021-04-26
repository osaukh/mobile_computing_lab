# WS4_0 Transfer Learning on Mobile Devices

### Activity Monitoring: What should you have by now?

If you decided to go for Option 1, you should have a mobile app providing the following functionality:
* Gathering sensor data (from accelerometer, gyroscope, etc.)
* Extracting features from this data
* Using kNN (or another method) to attribute the feature vector to the right activity. The model parameters are determined offline using your own data set.

Your app should be fairly accurate when tested with a smartphone held and fixed in the same position on your body as when gathering training data. If you change the position (e.g., by rotating the phone or fixing it to your leg instead of your arm) your activitiy recognition may (and most probably will) fail because measured signal will look fairly different (see below). In this workshop you will learn how one can address the problem with __on-device transfer learning__. We will create a generic model and personalize it on the phone.

<img src="img/sensors/project_option_1_acceleration_data.png" width="600">


### Transfer Learning for Model Personalization

Transfer learning is inspired by the way human learners take advantage of their existing knowledge and skills: A human who knows how to read literature is more likely to succeed in reading scientific papers than a human who does not know how to read at all. In the context of supervised learning, transfer learning implies the ability to reuse the knowledge of the dependency structure between features and labels learned in one setting to improve the inference of the dependency structure in another setting.


Suppose that you want to get the best user experience possible by adjusting the model to users’ needs (specific position or way of carrying a smartphone, etc.). Sending user data to the cloud to train a model requires a lot of care to prevent potential privacy breaches. It's not always practical to send data to a central server for training---issues like power, data caps, and privacy can be problematic. However, training directly on device is a strong approach with many benefits: Privacy-sensitive data stays on the device so it saves bandwidth, and it works without an Internet connection.

Training can require a non-trivial number of data samples which is hard to get on-device. Training a deep network from scratch can take days on the cloud so it's not suitable on device. Instead of training a whole new model from scratch, we can retrain an already trained model to adapt to a similar problem through transfer learning.

Transfer learning involves using a pre-trained model for one “data-rich” task and retraining a part of its layers (typically the last ones) to solve another, “data-poor” task. This is usually done by re-training only the last few layers (the head) of a neural network. The remaining layers are frozen and not updated. This is how the knowledge learned by on a data-rich task is transfered. Below you can how the head of a neural network on the left is cut off and replaced by a new head to be trained, while the bottom part of the network remains unchanged.

<img src="https://miro.medium.com/max/1400/1*Bi-_rpBbfhVAz6gi1uIRnw.png" width="800">

<div class="alert alert-block alert-info">
With transfer learning we can train personalized models <b>on-device</b> even with <b>limited training data and computational resources</b> while <b>preserving user privacy</b>.
</div>

### Example: On-device Transfer Learning for Object Detection

<div class="alert alert-block alert-info">
This application example is taken from the TensorFlow Lite tutorial:
<ul><li>
<a href="https://github.com/tensorflow/examples/tree/master/lite/examples/model_personalization">TensorFlow Lite Example On-device Model Personalization</a>
</li></ul>
</div>

The example includes an Android application that learns to classify camera images in real-time. The training is performed on-device by taking sample photos of different target classes.

<img src="https://3.bp.blogspot.com/-UZq9OA4Kz4s/XehrONzQvYI/AAAAAAAABdA/DDjNRBeuSkkrjtV13bb2tZYL5gQrdxX_ACLcBGAsYHQ/s1600/Screenshot_20190820-163122.png" width="200">

The app uses transfer learning on a quantized MobileNetV2 model pre-trained on ImageNet with the last few layers replaced by a trainable softmax classifier. You can train the last layers to recognize any four new classes. Accuracy depends on how “hard” the classes are to capture. For many objects just tens of samples can be enough to achieve good results. Compare this to ImageNet, which has 1.3 million samples!

This example includes a set of easily reusable tools that make it easy to create and use your own personalizable models. The example includes three distinct and isolated parts, each of them responsible for a single step in the transfer learning pipeline.

<img src="https://4.bp.blogspot.com/-vfNgyvvXghI/Xehr7LQTXNI/AAAAAAAABdM/OM7OY69jppExAqulrzu805h8iZJF1YPwgCLcBGAsYHQ/s1600/sDAezIApe_pdRnU8tGB0Vfg.png" width="400">

#### Converter

To generate a transfer learning model for your task, you need to pick two models that will form it:
* __Base model__ that is typically a deep neural network pre-trained on a generic data-rich task.
* __Head model__ that takes the features produced by the base model as inputs and learns from them to solve the target personalized task. This is typically a simpler network with a few fully-connected layers.

The model should be created with TensorFlow.

#### Android library

The transfer learning model produced by the transfer learning converter cannot be used directly with the TensorFlow Lite interpreter. An intermediate layer is required to handle the non-linear lifecycle of the model. This is done by the Android library (iOS is not yet supported). The Android library is hosted as a part of the example, but it lives in a stand-alone Gradle module so it can be easily integrated into any Android application.

#### Application

The Android application shows how to light-retrain a model (transfer learning) and do inference using that model.

<div class="alert alert-block alert-success">
<b>Run this app on your device and understand the source code:</b>
<ul><li>
<a href="https://github.com/tensorflow/examples/tree/master/lite/examples/model_personalization">TensorFlow Lite Example On-device Model Personalization</a>
</li></ul>
</div>


***

# Your Task: Activity Monitoring & Transfer Learning (Option 1)

<img src="img/project_options4.png" width="800">

### Cookbook

#### Step 1: Create a base model using an existing data set

<div class="alert alert-block alert-success">
<b>Follow the steps to train a generic base model</b>
<table class="tfo-notebook-buttons" align="left" style="float:none;">
  <td>
    <a target="_blank" href="https://colab.research.google.com/github/osaukh/mobile_computing_lab/blob/master/workshops/WS04--TransferLearning--BaseModel.ipynb">
    <img src="https://www.tensorflow.org/images/colab_logo_32px.png" />
    Run in Google Colab</a>
  </td>
  <td>
    <a target="_blank" href="https://github.com/osaukh/mobile_computing_lab/blob/master/workshops/WS04--TransferLearning--BaseModel.ipynb">
    <img src="https://www.tensorflow.org/images/GitHub-Mark-32px.png" />
    View source on GitHub</a>
  </td>
</table>
</div>

Feel free to modify the model and improve it.

#### Step 2: Create a simple head model

<div class="alert alert-block alert-success">
<b>Follow the steps to create a simple head model</b>
<table class="tfo-notebook-buttons" align="left" style="float:none;">
  <td>
    <a target="_blank" href="https://colab.research.google.com/github/osaukh/mobile_computing_lab/blob/master/workshops/WS04--TransferLearning--HeadModel.ipynb">
    <img src="https://www.tensorflow.org/images/colab_logo_32px.png" />
    Run in Google Colab</a>
  </td>
  <td>
    <a target="_blank" href="https://github.com/osaukh/mobile_computing_lab/blob/master/workshops/WS04--TransferLearning--HeadModel.ipynb">
    <img src="https://www.tensorflow.org/images/GitHub-Mark-32px.png" />
    View source on GitHub</a>
  </td>
</table>
</div>

Feel free to modify the model and extend it to 4 classes (it currently supports 2).

#### Step 3: Integrate transfer learning into your own app

<div class="alert alert-block alert-success">
<b>Run an example app with your custom-made model with 2 classes</b>
<table class="tfo-notebook-buttons" align="left" style="float:none;">
  <td>
    <a target="_blank" href="https://github.com/osaukh/mobile_computing_lab/blob/master/workshops/WS04--TransferLearning--Application.ipynb">
    <img src="https://www.tensorflow.org/images/GitHub-Mark-32px.png" />
    View on GitHub</a>
  </td>
</table>
</div>

Extend your own activity recognition app (which uses kNN or another method) to provide the following functionality:
* Continue using kNN implementation
* Add inference using a generic model trained in step 1
* Use transfer learning to support data gathering, training and inference for the personalized model
* In the GUI show activity prediction using all three models

#### Step 4: Compare model performance

Compare the performance of the three models on a self-defined set of tests. The test set should be the same for all three models. Log test result into a file and present these in your report!

#### Step 5: Final demo

Show all models in action in the final demo!

## References

*    TensorFlow Blog: [Example on-device model personalization with TensorFlow Lite](https://blog.tensorflow.org/2019/12/example-on-device-model-personalization.html), 2019
*    Medium: [Transfer Learning with keras](https://medium.com/analytics-vidhya/transfer-learning-with-keras-9a1b3253211c), 2019
* 	 Medium: [Human Activity Recognition (HAR) Tutorial with Keras and Core ML (Part 1)](https://towardsdatascience.com/human-activity-recognition-har-tutorial-with-keras-and-core-ml-part-1-8c05e365dfa0)
*    GitHub: [Human Activity Recognition Tutorial with Keras and CoreML (Part 1)](https://github.com/ni79ls/har-keras-coreml/blob/master/Human%20Activity%20Recognition%20with%20Keras%20and%20CoreML.ipynb)