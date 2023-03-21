# Course Organization

## Basic Information
 
*   TUG Online: [Mobile Computing Lab (MCL), SS'23](https://online.tugraz.at/tug_online/wbLv.wbShowLVDetail?pStpSpNr=256443)
*   Instructor: [Olga Saukh](http://www.olgasaukh.com) ([TUG business card](https://online.tugraz.at/tug_online/visitenkarte.show_vcard?pPersonenGruppe=3&pPersonenId=BC91EB43629EBA38"))
*   TA: Michael Pölzl (michael.poelzl at student.tugraz.at)
*   [MCL course materials on GitHub](https://github.com/osaukh/mobile_computing_lab)

MCL starts on __Wed, 22.03.2023 at 3pm__ over Webex. Access details can be found under "Announcements" in the TeachCenter.

Join the __MCL Slack channel__ to connect to other students enrolled in the class. Access details can be found under "Announcements" in the TeachCenter.


## Content

The course provides an introduction to the current research trends in the area of mobile computing. The course will be based on a programming project, where students will form groups of 1-2 students to develop a smartphone app for Android (iOS is also fine). There is no disadvantage of implementing a project alone! 

To program a smartphone app, a developer needs to be familiar with:
1. Sensors and signals a smartphone can gather,
2. Data science and machine learning tools necessary to process sensor data, and
3. How to run sensor data processing algorithms and models on a smartphone.

This course will provide a solid background for the above points. During the workshops we will get familiar with the state-of-the-art in this emerging field. We will look into a few technologies and papers to understand how algorithms, signal processing, and machine learning tools are used to develop some exciting applications. The students will then use these basic tools to develop their own apps.
    
<img src="img/intro/course_content.png" width="800">


## Workshops

Workshops will help you to get started with the topics:
*   [Workshop 1: Sensors and signals](https://github.com/osaukh/mobile_computing_lab/blob/master/WS01__Sensors_and_Signals.md)
*   [Workshop 2: TensorFlow and TF Lite](https://github.com/osaukh/mobile_computing_lab/blob/master/WS02__TensorFlow_and_TFLite.md)
*   [Workshop 3: Speech processing](https://github.com/osaukh/mobile_computing_lab/blob/master/WS03__Speech_Processing.md)
*   [Workshop 4: On-device transfer learning](https://github.com/osaukh/mobile_computing_lab/blob/master/WS04__OnDevice_TransferLearning.md)


Workshops / discussions / progress reviews / demos: __3-5pm on every scheduled occasion__ over WebEx or in the Lab. Code troubleshooting: use __Slack for asking questions__. Attend individual coaching sessions and ask TA for help or send us an email.


## Schedule

<img src="img/intro/schedule.png" width="900">

<img src="img/intro/schedule2.png" width="800">


## Prerequisites

The course is for new and experienced developers who already have Java programming experience and now want to learn how to build powerful Android apps.


## Teams

*   You will develop Android projects in groups of 1-2 students.
*   Make sure each group owns at least one smartphone running Android (or iOS and a MacBook). __If you have no Android phone contact Olga__.


## Project Options

The course is based on a programming project. Two options are available:

#### Option 1: Activity monitoring and tranfer learning
The project is for beginners and consists of two parts: First, you will read sensor data provided by your phone (e.g., acceleration values) and use different algorithms (e.g., kNN) to detect the type of activity you are currently performing. In the second part of the project, you will have to change the location of your phone (e.g., hold it in your hand instead of in your pocket) and use transfer learning to adjust the model quickly on your device. 

_Your list of activities must be: (1) walking, (2) jogging, (3) jumping, (4) squatting, (5) sitting, (6) standing._
    
<img src="img/sensors/project_option_1_acceleration_data.png" width="800">

Expected path:
* 	Week 1-3: Activity monitoring (e.g., based on kNN)
* 	Week 4-7: Transfer learning
* 	Week 8: GUI
* 	Week 9: Polish your code, measure performance, write final report
* 	Week 10: Demonstration and report

#### Option 2: Free project choice (the topic needs Olga's approval)

The project must involve sensors and non-trivial data processing on a mobile phone. Before you suggest a free choice project, please read related literature on the topic including scientific publications.

<img src="img/intro/project_option_free.png" width="800">
    
Expected path:
* 	Week 1: Define your project (read furiously) and agree with Olga
* 	Week 2: Pitch (problem statement, proposed solution, data)
* 	Week 2-7: Your own project
* 	Week 8: GUI
* 	Week 9: Polish, evaluate, write report
* 	Week 10: Demonstrations/Presentations

Check examples of <a href="http://www.olgasaukh.com/mcl.html">successfully completed MCL projects</a>.


## Tools

### GitHub, GitLab, Google Colab
* 	Version control your code on <a href="https://github.com">GitHub</a> or <a href="https://about.gitlab.com">GitLab</a>. 
* 	Git tutorials: <a href="https://guides.github.com">GitHub Guides</a>, <a href="https://education.github.com/git-cheat-sheet-education.pdf">Git Cheat Sheet</a>, Oreilly's Book <a href="https://www.foo.be/cours/dess-20122013/b/OReilly%20Version%20Control%20with%20GIT.pdf">"Version Control with Git"</a>, game: <a href="https://learngitbranching.js.org">Learn Git Branching</a>.
* 	[Google Colab](https://colab.research.google.com) to run iPython notebooks and collaborate on the code. Will be used later in this course.

### Assistance
* 	Olga is available in WebEx 3-5pm every scheduled appointment.
* 	Peter (peter.prodinger at student.tugraz.at) will help you to fix coding issues in the lab (Technische Informatik Nord, IE01074). Approach him on Slack or email him if you need help.
* 	It's easier to help if you use GitHub / GitLab / Google Colab and we can access your code!

### Hints
* 	Android programming is not difficult, but do not underestimate the required time and effort!
* 	Do not wait until the last minute to start working on the project!

__Previous years' statistics__: Activity recognition app takes a team of 2 people (with some Android programming experience) 4 days to complete (full time). Transfer learning takes the same team 7 days to complete (full time).


## Progress / Demos / Reports 
* 	Intermediate progress meetings are there to make sure you are well on track. See instructions:
    * 	<a href="Progress_Reviews.md">Demo (Option 1) and Progress Review (Option 2)</a> 
* 	At the end of the course you will be asked to 
    * 	Submit your report as PDF
    *  	Share your code with Olga (grant access to your GitHub / GitLab repository)
	*   Submit a video showing the app in action
    * 	Present and demonstrate your app live (during the final in-person meeting)
	* 	See instructions: <a href="Final_Demo_and_Report.md">Final Demo and Report</a>


## Grading
*   Your final grade is based on the following grading scheme:
	- 10 points -- Performance in the intermediate demo / progress review
    - 10 points -- Novelty, your ideas, attention to detail, code clarity
    - 60 points -- Working demo and performance results
    - 20 points -- Quality of the report (structure, readability, completeness) and final video

* Grading scheme:
    - &#8805; 90 --> 1 (excellent)
    - &#8805; 78 --> 2 (good)
    - &#8805; 65 --> 3 (satisfactory)
    - &#8805; 50 --> 4 (sufficient)
    - < 50 --> 5 (insufficient)

There will be presents for the best projects!
<img src="img/intro/android_collectibles.jpg" width="800">
