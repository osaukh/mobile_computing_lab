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

<img src="img/sensors/project_option_1_person_walking.png" width="800">

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

<img src="img/sensors/project_option_1_acceleration_data.png" width="800">

Source: Paper [Activity Recognition from User-Annotated Acceleration Data](http://web.media.mit.edu/~intille/papers-files/BaoIntille04.pdf), Pervasive 2004


#### Step 2: Feature extraction (art and science)

* How would you describe a signal statistically?
* Example:
	* Choose a window size (20 samples, 500 ms)
	* Select features (mean, max min, variance, Fourier transforms, autocorrelation, ...)

<img src="img/sensors/project_option_1_acceleration_data2.png" width="800">

#### Step 3: Classification method (science)

* __Exploration:__ Get some features and plot them. There going to be an overlap depending on the window's size.

<img src="img/sensors/project_option_1_classification.png" width="800">

* __k-Nearest Neighbors algorithm (kNN)__ is a top-10 machine learning tool! The output is a class membership. An object is classified by a majority vote of its neighbors, with the object being assigned to the class most common among its k nearest neighbors (k is a positive integer, typically small). If k = 1, then the object is simply assigned to the class of that single nearest neighbor.

<img src="https://camo.githubusercontent.com/1ae598a658f0cf308d0b5afe2149a2d716cde3f82036fb9e04948cfc6db26904/68747470733a2f2f75706c6f61642e77696b696d656469612e6f72672f77696b6970656469612f636f6d6d6f6e732f7468756d622f652f65372f4b6e6e436c617373696669636174696f6e2e7376672f32323070782d4b6e6e436c617373696669636174696f6e2e7376672e706e67" width="200">

The test sample (green circle) should be classified either to the first class of blue squares or to the second class of red triangles. If k = 3 (solid line circle) it is assigned to the second class because there are 2 triangles and only 1 square inside the inner circle. If k = 5 (dashed line circle) it is assigned to the first class (3 squares vs. 2 triangles inside the outer circle).

Source: [Wikipedia](http://en.wikipedia.org/wiki/K-nearest_neighbor_algorithm)

* __Decisions to be made:__
	
_How large is K?_
* Odd number
* ![](https://latex.codecogs.com/svg.latex?\sqrt{N}) 

_What is the best distance measure?_
		
__Euclidean:__ is the "ordinary" (i.e. straight-line) distance between two points in Euclidean space.

![](https://latex.codecogs.com/svg.latex?&space;D(w_i,v_i)=\sqrt{\sum(w_i-v_i)^2})

__Manhattan:__ is a city block distance, taxicab metric is defined as the sum of the lengths of the projections of the line segment between the points onto the coordinate axes.

![](https://latex.codecogs.com/svg.latex?&space;D(w_i,v_i)=\sum|w_i-v_i|)

<img src="https://github.com/osaukh/mobile_computing_lab/raw/6ed57c866a4ec772d4a2a322da4d057c6254f437//img/sensors/project_option_1_manhatten.png" width="200">

__Chebyshev (Chessboard):__ is a distance measure where all 8 adjacent cells from the given point can be reached by one unit i.e diagonal move is valid. It is also known as chessboard distance, since in the game of chess the minimum number of moves needed by a king to go from one square on a chessboard to another equals the Chebyshev distance between the centers of the squares.

![](https://latex.codecogs.com/svg.latex?&space;D(w_i,v_i)=\max|w_i-v_i|)

<img src="https://github.com/osaukh/mobile_computing_lab/raw/6ed57c866a4ec772d4a2a322da4d057c6254f437//img/sensors/project_option_1_chebyshev.png" width="200">

Source: [Euclidean vs Chebyshev vs Manhattan Distance?](http://www.isumitjha.com/2017/12/chebyshev-vs-euclidean-vs-manhattan.html)

# Your Task: Activity Monitoring (Option 1)

<img src="img/project_options2.png" width="800">

### Cookbook

#### 1. Read accelerometer data and display data on the screen

* Implement the interface `SensorEventListener`:
```java
public class MainActivity extends AppCompatActivity implements SensorEventListener {
```

* Declare variables for `SensorManager` and `accelerometer`:
```java
private SensorManager sm;
private Sensor accelerometer;
```

* Instantiate them in `OnCreate` method:
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
        ...
    sm = (SensorManager)getSystemService(SENSOR_SERVICE);
    accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        ...

    sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
}
```

* Capture sensor values in `onSensorChanged` method:
```java
@Override
public void onSensorChanged(SensorEvent event) {
    double x, y, z;
    x = event.values[0];
    y = event.values[1];
    z = event.values[2];

    //Store data in memory, file, or in other data structure
    addDataToProcess (x, y, z); 
}

```

* Call your function to recognize activity at suitable times:
```java
public ActivityType recognizeActivity () {
    // ActivityType is an enum {NONE, SIT, WALK, RUN, ...};

    // Fill in with your algorithm
}
```

#### Step 2: Write accelerometer data to a file

* File I/O - Reading:
```java
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//Reading from a file
DataInputStream fInpStream = null;

try {
    fInpStream = new DataInputStream (new FileInputStream("<path>"));
    while(fInpStream.available() > 0) {
        double d = fInpStream.readDouble ();
        ...
    }
}
catch (FileNotFoundException e) {
    e.printStackTrace();
    ...
}
```

* File I/O - Writing:
```java
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//Writing to a file
DataOutputStream fOutStream = null;

try {
    fOutStream = new DataOutputStream (new FileOutputStream("<path>"));
    double d = 0.02;
    fOutStream.writeDouble(d);
    fOutStream.flush();
} catch (FileNotFoundException e) {
    e.printStackTrace();
    ...
} catch (IOException e) {
    e.printStackTrace();
    ...
}
```
Also see: [App data and files](https://developer.android.com/guide/topics/data)

You need to make sure that the application has permission to read and write data to the users SD card, so open up the `AndroidManifest.xml` and add the following permissions:
```xml
<uses-permission android:name="android.permission.WRITE_INTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.READ_INTERNAL_STORAGE"/>
```
or
```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```

#### Step 3: Copy file from step 3) to your laptop

#### Step 4: Do kNN analysis offline first

You can use any programming language you want! I highly recommend to use Python. You can use a third-party kNN implementation in this step.
* Divide data in two parts for training and testing
* Select a window size
* Select feature vectors from training set
* Perform classification with testing set

#### Step 5: Program kNN in your smartphone and display classification on the screen

<img src="https://camo.githubusercontent.com/f17f3b410d883db9e2d2e900294cd255765b3871ab0fdb6c5ab76cf0887effaf/68747470733a2f2f6171696273616565642e6769746875622e696f2f696d672f6861725f6170705f73637265656e73686f742e706e67" width="200">

(This is just an example)

__Important:__ Please do NOT use Google Play Services Activity Recognition API but you can use it to compare your own app performance.

## References

* Research papers on activity monitoring
	* [LIMU-BERT: Unleashing the Potential of Unlabeled Data for IMU Sensing Applications](https://tanrui.github.io/pub/LIMU_BERT.pdf), 2021
	* [SelfHAR: Improving Human Activity Recognition through Self-training with Unlabeled Data](https://arxiv.org/abs/2102.06073), 2021
