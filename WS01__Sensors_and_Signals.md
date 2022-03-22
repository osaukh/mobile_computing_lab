# Workshop 1: Sensors and Signals


### 1. Install Android Studio on Your Laptop

Follow instructions for your operating system:
* [Install Android Studio](https://developer.android.com/studio/install.html)

### 2. Android Hello World App

Goal: Make sure you have a working system, create your first Android app and run it in an emulator and on your phone.

<img src="https://camo.githubusercontent.com/1c0ee94e6172d92afe6e1fc566bcc9ec236a8fbe2266776c5d9f2e150e9be20a/687474703a2f2f6d6174656f6a2e636f6d2f7075626c69632f696d616765732f6d6f766965732d6170702f73637265656e73686f745f73746570312e706e67" width="200">

__Follow instructions:__ 
* [Build Your First Android App](https://developer.android.com/training/basics/firstapp)

In case you have troubles activating your phone or running an app on your phone, please check the following:

* Unauthorised access: [A fix available on StackOverflow](https://stackoverflow.com/questions/30258272/adb-rsa-authorization-key-dialog-will-not-open)
* Settings -> Developer Options -> Networking -> USB Configuration = MTP (or make sure File Transfer is enabled)

### 3. Sensors and Signals

Goal: Learn about the Android sensor framework, which is used to find the available sensors on a device and retrieve data from those sensors.

<table><tr>
<td><img src="https://camo.githubusercontent.com/081ffdabcfe0e78ede3b746ff05ef9eba91eef9db2195c10a396d9df6176b3cc/68747470733a2f2f646576656c6f7065722e616e64726f69642e636f6d2f636f64656c6162732f616476616e6365642d616e64726f69642d747261696e696e672d73656e736f722d646174612f696d672f393836356337353238353139643061662e706e67" width="200"></td>
<td><img src="https://camo.githubusercontent.com/d13fa1decb8e683d5b1e8b9eb3ba43f832c872872941925fed6361a1bfaaf56b/68747470733a2f2f646576656c6f7065722e616e64726f69642e636f6d2f636f64656c6162732f616476616e6365642d616e64726f69642d747261696e696e672d73656e736f722d646174612f696d672f363233653439656431333034366564332e706e67" width="200"></td>
</tr>
</table>

__Follow instructions:__
* Read: [Sensors overview](https://developer.android.com/guide/topics/sensors/sensors_overview)
* Follow the steps: [3.1: Getting sensor data (do both Task 1 and Task 2)](https://developer.android.com/codelabs/advanced-android-training-sensor-data#0)


### 4. Activity Monitoring

<img src="img/sensors/project_option_1_person_walking.png">

#### Step 1: Raw signal

* Key sensors for our app (... and many more other applications):
	* __Accelerometers__ convert acceleration into an electrical signal
	* __Gyroscopes__ measure angular rate (how quickly an object turns)
	* __Compass__ sensor detect the magnetic field of the Earth
	* Radio transceivers detect surrounding access points and their __signal strength (RSS)__
* Energy consumption matters
	* For example, on HP iPAQ hw6965: GPS 620 mW, Microphone 225 mW, Accelerometer 2 mW
	* __Always solve the problem using as little energy as possible__
* Most smartphones have 3-axis accelerometers. Accelerometers can provide a lot of information.
	* Activity monitoring 

<img src="img/sensors/project_option_1_acceleration_data.png" width="600">

Source: Paper [Activity Recognition from User-Annotated Acceleration Data](http://web.media.mit.edu/~intille/papers-files/BaoIntille04.pdf), Pervasive 2004


#### Step 2: Feature extraction (art and science)

* How would you describe a signal statistically?
* Example:
	* Choose a window size (20 samples, 500 ms)
	* Select features (mean, max min, variance, Fourier transforms, autocorrelation, ...)

<img src="img/sensors/project_option_1_acceleration_data2.png">

#### Step 3: Classification method (science)

* __Exploration:__ Get some features and plot them. There going to be an overlap depending on the window's size.

<img src="img/sensors/project_option_1_classification.png" width="600">

* __k-Nearest Neighbors algorithm (kNN)__

kNN is a top-10 machine learning tool! The output is a class membership. An object is classified by a majority vote of its neighbors, with the object being assigned to the class most common among its k nearest neighbors (k is a positive integer, typically small). If k = 1, then the object is simply assigned to the class of that single nearest neighbor.

<img src="https://camo.githubusercontent.com/1ae598a658f0cf308d0b5afe2149a2d716cde3f82036fb9e04948cfc6db26904/68747470733a2f2f75706c6f61642e77696b696d656469612e6f72672f77696b6970656469612f636f6d6d6f6e732f7468756d622f652f65372f4b6e6e436c617373696669636174696f6e2e7376672f32323070782d4b6e6e436c617373696669636174696f6e2e7376672e706e67" width="200">

The test sample (green circle) should be classified either to the first class of blue squares or to the second class of red triangles. If k = 3 (solid line circle) it is assigned to the second class because there are 2 triangles and only 1 square inside the inner circle. If k = 5 (dashed line circle) it is assigned to the first class (3 squares vs. 2 triangles inside the outer circle).

Source: [Wikipedia](http://en.wikipedia.org/wiki/K-nearest_neighbor_algorithm)

* __Decisions to be made:__
	* How large is K?
		* Odd number
		* $\sqrt{N}$

## References

