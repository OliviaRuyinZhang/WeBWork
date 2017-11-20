![The Infinite Loopers](https://i.imgur.com/3qSxZjk.png)
### Team L02_11
#### Abhay Vaidya, Felix Chen, Julian Barker, Ruyin (Olivia) Zhang, Tito Ku

## Deliverable 1: Team Setup
##### September 25, 2017
An introduction of our team and our team agreement can be found [here](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Project_Deliverable_1.pdf).

## Deliverable 2: Project Requirements
##### October 16, 2017
The first version of our personas and user stories (version 0) can be found [here](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Personas_and_User_Stories_v0.pdf).

## Deliverable 3: Project Planning
##### October 23, 2017
- This week, we updated some of our user stories to reflect a different direction in technology. Version 1 of our personas and user stories (version 0) can be found [here](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Personas_and_User_Stories_v1.pdf).
- We also went through our first sprint, details about that can be found [here](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Sprint_1_Plan.pdf)
  - For this sprint, we developed some initial user interface mockups to decide on a general layout:
### Login Screen
![Login Screen](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Mockups/login.png)
### Assignment Creation
![Assignment Creation](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Mockups/new_assignment.png)
### Professor Dashboard
![Professor Dashboard](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Mockups/professor_dashboard.png)

## Deliverable 4: Project Execution
##### October 30, 2017
- This week, we implemented the user login/register interface, InstructorAssignmentListing panel and the assignment creator interface.
### Login Screen
![Login Screen](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Deliverable_4/Application_Screenshots/Login_Screen.png)
### Instructor Dashboard
![Professor Dashboard](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Deliverable_4/Application_Screenshots/Instructor_Dashboard.png)
### Assignment Creation
![Assignment Creation](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Deliverable_4/Application_Screenshots/New_Assignment.png)

## Deliverable 5: Product Validation
### Sprint 4: November 6, 2017
For this sprint, we implemented: 
- an assignment completion user interface for students, who can now complete assignments.
- an assignment editing user interface, so that instructors have the option to edit their existing assignments.
#### Sprint 5: November 13, 2017
For this print, we implemented:
- a remark user interface on both instructors and students assignment listing classes. 
- handler to handle a submitted remark request by sending them directly to every registered instructor's emails. Upon completion of a remark, an email is sent to the student to notify them that changes have been made to their grade.
- a closed assignment section to lists all the closed assignments for students.
- user interface for students to view ther marks, time spent, average marks between every attempt and number of tries upon completion of an assignment.
- unit tests to verify our product quality.
- validation of our product by matching the client's requirements.
### User Instructions
**_Set up_**
- Due to usage of an external library, JavaMail, if the program is being run an IDE, the user must reconfigure their java build path to access the library. The API is found in the "lib" folder in the project and you can add them to your external library.
- Go to `ApplicationDriver.java` under the `controllers` package
- Run `ApplicationDriver.java` and you will see a login/register interface

**INSTRUCTOR'S MANUAL**
**_Register_**
- For first time users, you must register your account by typing in your valid email address and password.
- You will have to check the checkbox "instructor" before clicking "register"
- You will see a screen to ask you input your first name, last name and passcode to finish your registration. 
- As an instructor, you will need to provide a valid passcode for the course in order to register as an instructor successfully. The passcode for now is `abc`.
- You wll see a popup window says "Registration Successful" after you have entered all the information properly when you register.

**_Login_**
- After you have registered successfully, you will be able to log in to the instructor dashboard using the same email address and password that you registered with (it is not necessary to check the instructor box)

**_Creating a Problem Set_**
- Open the assignment creation panel by clicking the "+ Add Assignment" button at the top right corner
- Select a due date of the assignment by clicking the drop down box on the left top
- Enter the assignment number you are going to create
- Manually enter each problem corresponding multiple choice options
- Preset the solution for each question by clicking the corresponding radio button for the correct multiple choice option
- After you have entered all the required information for the question, you can click "Add this Problem" to add the problem into the problem set
- If you have not enter all the required information that stated above before clicking "Add this Problem", you will get a popup notification to ask you fulfill all the required text.
- Repeatly do the steps above until you have added all the problems to the problem set for the current assignment 
- Click the "Create" button after you have entered all your problems to finish creating the assignment
- As an instructor, you can release and unrelease assignments by clicking the labeled buttons corresponding to the appropriate assignments

**_Assignment Editing_**
- Cick "Edit" button on the dashboard beside the assignment that you would like to edit
- Use the dropdown menu to slect the question you want to edit
- After you select a question from the dropdown menu, you can edit the problem, multiple choices, and its solution on the panel directly. Click "Save Edited Problem" after you have done editing.  
- If you wish to delete a question, select the question you wish to delete from the dropdown menu first, and then click "Remove problem"
- If you wish to add a new question to this assignment, you need to click "Clear" to clear all the default text in the textfiled first. Enter the new assignment question, multipule choice options and its solution to the corresponding textfileds and then click "Add new problem" to add this problem to the assignment database.
- After you have done all your changes on the assignment, you need to click "Save" to save all your changes. After you click "Save", the application would take you back to the dashboard.
- Now your edited assignment would be under the Unreleased Assignment session. Confirm your changes in your head once more time, then you can click "Release" to release the assignment you just edited if you want to release it to the students

**_Grade Analysis & Grade Retrieval_**
- The class average of each closed assignment would be display on the dashboard automatically after the assignment due date has passed. You can check out the class avergae under the _Closed Assignment_ session.
- If you would like to export a copy of a excel spreadsheet which includes all the students performances about this assignment, click "Export Mark" for the assignment performance you would like to review. There's a filechooser window open after clicking "Export Mark", you can save the copy of the documnet to anywhere on your computer by choosing a directory. The new performance result file will replace the old file if the file has already exist in the directory you chose. 
- If you would like to get a summary of the performance of an individual student, you can search the result by clicking "Search Student". After clicking "Search Student", it will open a search panel for you, you need to enter the student number into the textfield and then click "search". All the marks that this student has achived in the past assignments will be displayed on the display panel. 

**_Rmark_**
- all the remark requests would be sent to your email directly through this application system once the student click the remark button on their student panel. Check your emails to view all the remark requests.
- If you want to approve a remark request, you can go to your dashboard and click the "Remark" button. Enter the student number and the assignment number which you are handling, and the student's final mark to the textbox. Click "Adjust Mark" to save the changes to the database and the application system would send an email to notify the student with the change of his/her mark automatically. Click "cancel" if you would like to go back to the dashboard. 

**_STUDENT'S MANUAL_**
**Register**
- Enter your valid student email address and password, click "Register"
- You will see a screen to ask you input your first name, last name and student number to complete your registration process.
- You wll see a popup window says "Registration Successful" after you have entered all the information properly when you register.

**_Login_**
- After you have registered successfully, you will be able to log in to the student dashboard using the same email address and password that you registered with

**_Solving Problem Sets_**
- Click "open" on the assignment you want to start
- Answer each question by selecting the best multiple choice option
- Click "save and close" if you have not finished the assignment
- Click "submit and grade" to grade your assignment and get your mark
- If you click "save and close", the application will auto fill your previous saved answers for each question and set it as default option when you open the assignment again next time.

**_Auto-marking & student feedback_**
- You can view each assignment submission summary after the deadline of the assignment passed. Go to "Closed Assignment" session and click the "Result" button on the assignment result you want to reivew. You will be able to see your final mark, avergae mark, number of tries, and time spent as feedback on a new display panel. Click "done" after reviewing it and the application would take you back to the dashboard.

**_Auto-marking & student feedback_**
- Click the "Remark" button beside the assignment you want to remark. 
- Elaborate your remark reasoning on the message box and then click "Submit" to reuqest a remark
- The remark will be sent to instructors directly by email


###### Note: GitHub may have problems displaying some of our documents. For this reason, it is recommended you download the document instead of using GitHub's preview to view it.
