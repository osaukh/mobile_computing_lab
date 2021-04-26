# WS4_0 Transfer Learning on Mobile Devices

## Activity Monitoring: What should you have by now?

If you decided to go for Option 1, you should have a mobile app providing the following functionality:
* Gathering sensor data (from accelerometer, gyroscope, etc.)
* Extracting features from this data
* Using kNN (or another method) to attribute the feature vector to the right activity. The model parameters are determined offline using your own data set.

Your app should be fairly accurate when tested with a smartphone held and fixed in the same position on your body as when gathering training data. If you change the position (e.g., by rotating the phone or fixing it to your leg instead of your arm) your activitiy recognition may (and most probably will) fail because measured signal will look fairly different (see below). In this workshop you will learn how one can address the problem with __on-device transfer learning__. We will create a generic model and personalize it on the phone.

<img src="img/sensors/project_option_1_acceleration_data.png" width="600">
