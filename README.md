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
### Sprint 4
##### November 6, 2017
For this sprint, we implemented: 
- An assignment completion user interface for students, who can now complete assignments.
- An assignment editing user interface, so that instructors have the option to edit their existing assignments.

### Assignment Editing
![Assignment Editing](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Deliverable_5/Sprint_4/Application_Screenshots/Assignment_Editing.png)

### Assignment Completion
![Assignment Editing](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Deliverable_5/Sprint_4/Application_Screenshots/Assignment_Completion.png)


### Sprint 5
##### November 13, 2017
For this sprint, we implemented:
- A remark user interface on both instructors' and students' dashboards. 
- A Handler to handle a submitted remark request by sending them directly to every registered instructor's emails. Upon completion of a remark, an email is sent to the student to notify them that changes have been made to their grade.
- A closed assignment section to lists all the closed assignments (assignments past the deadline).
- A user interface for students to view ther marks, time spent, average marks, and number of tries upon completion of an assignment.
- Unit tests to verify our product quality.
- Acceptance tests to validate our product by reviewing the client's requirements and what we have delivered.

### Student's Assignment Details
![Assignment Details](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Deliverable_5/Sprint_5/Application_Screenshots/Assignment_Details.png)

### Exporting Grades
![Export Grades](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Deliverable_5/Sprint_5/Application_Screenshots/Instructor_Export.png)

### Student Remark
![Remark Buttons](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Deliverable_5/Sprint_5/Application_Screenshots/Student_Remark.png)

![Remark Explanation](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Deliverable_5/Sprint_5/Application_Screenshots/Remark_Explanation.png)

### Instructor Remark
![Instructor Remark](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Deliverable_5/Sprint_5/Application_Screenshots/Instructor_Remark.png)

![Instructor Remark Email](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Deliverable_5/Sprint_5/Application_Screenshots/Instructor_Remark_Email.png)

### Instructor Student Search
![Student Search](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Deliverable_5/Sprint_5/Application_Screenshots/Student_Search.png)

### Code Review
We individually conducted code review and got together to discuss our findings. [Here](https://drive.google.com/file/d/1z9PObnjwZjBa-xFHlrXZTirhLWe--yDO/view?usp=sharing) is a video of our debriefing.

## Deliverable 6: Final Product
### Sprint 6
##### November 20, 2017
For this sprint, we implmented:
- A feedback user interface on student's dashboard
- A user interface for instructors to view all the students' feedback.
- An application logo to the user's login page
- A submission summary window for students to view their answers correctness for each submission
- A personal information interface for students to enter their first name, last name and student number when they register
- A personal information interface for instructors to enter their first name, last name and invite code when they register
- A personal information interface for the admin instructor to enter their first name, last name and allow admin instructor to set the invite code for all other instructors and TAs
For this sprint, we refactored the code in:
- RegisterStudentInfo
- RegisterInstructorInfo
- StudentListingGUI
- InsturctorListingGUI

### WeBWorK Welcome Page

### Personal Information Interface

### Submission Summary

### Send Feedback

### View Feedback

### User Instructions
##### Setup
- Due to usage of an external library, JavaMail, if the program is being run through an IDE, the user must reconfigure their java build path to access the library. The API is found as 5 jar files in the "lib" folder in the project and you must add them to your build path.
- Go to `ApplicationDriver.java` under the `controllers` package
- Run `ApplicationDriver.java` and you will see a login/register interface

#### **INSTRUCTOR'S MANUAL**
##### Register
- For first-time users, you must register your account by typing in your valid email address and password
- You will have to check the "Instructor" checkbox before clicking "Register"
- You will be presented with a screen to ask you input your first name, last name and passcode to finish your registration 
- As an instructor, you will need to provide a valid passcode for the course in order to register as an instructor successfully. If you are the first instructor, you are considered the admin and will be required to set the passcode for all other instructors.
- You wll see a popup window notifying you of a successful registration after you have entered all the information properly and submit.

##### Login
- After you have registered successfully, you will be able to log in to the instructor dashboard using the same email address and password that you registered with (it is not necessary to check the instructor box)

##### Creating a Problem Set
- Open the assignment creation panel by clicking the "+ Add Assignment" button at the top right corner
- Select a due date of the assignment by clicking the drop down box on the left top
- Enter the assignment number you are going to create
- Manually enter each problem corresponding multiple choice options
- Preset the solution for each question by clicking the corresponding radio button for the correct multiple choice option
- After you have entered all the required information for the question, you can click "Add this Problem" to add the problem into the problem set
- If you have not entered all the required information before clicking "Add this Problem", you will get a popup notification to ask you fulfill all the required text
- Repeatly do the steps above until you have added all the problems to the problem set for the current assignment 
- Click the "Create" button after you have entered all your problems to finish creating the assignment
- As an instructor, you can release and unrelease assignments by clicking the labeled buttons corresponding to the appropriate assignments

##### Assignment Editing
- Cick "Edit" button on the dashboard beside the assignment that you would like to edit
- Use the dropdown menu to slect the question you want to edit
- After you select a question from the dropdown menu, you can edit the problem, multiple choices, and its solution on the panel directly. Click "Save Edited Problem" after you have done editing.  
- If you wish to delete a question, select the question you wish to delete from the dropdown menu first, and then click "Remove problem"
- If you wish to add a new question to this assignment, you need to click "Clear" to clear all the default text in the boxes first. Enter the new assignment question, multiple choice options and its solution to the corresponding text fields and then click "Add new problem" to add this problem to the assignment database.
- After you have done all your changes on the assignment, you need to click "Save" to save all your changes. After you click "Save", the application would take you back to the dashboard.
- Now your edited assignment would be under the Unreleased Assignment session. Confirm your changes in your head once more time, then you can click "Release" to release the assignment you just edited to the students

##### Grade Analysis & Retrieval
- The class average of each closed assignment would be display on the dashboard automatically after the assignment due date has passed. You can check out the class average under the _Closed_ assignments session.
- If you would like to export a copy of an excel spreadsheet which includes all the students performances for a given assignment, click "Export Mark" for the assignment data you would like to review. A file-chooser window will open after clicking "Export Mark", you can save the copy of the document to anywhere on your computer by choosing a directory. The new assignment details result file will replace the old file if the file has already exist in the directory you chose. 
- If you would like to get a summary of the performance of an individual student, you can search for their results by clicking "Search Student". After clicking "Search Student", it will open a search panel where you need to enter the student number into the text field and then click "Search". All the marks that this student has achived in the past assignments will be displayed on the display panel. 

##### Remarks
- All the remark requests would be sent to your email directly through this application system once the student clicks the remark button on their student panel. Check your emails to view all the remark requests.
- If you want to approve a remark request, you can go to your dashboard and click the "Remark" button. Enter the student number and the assignment number which you are handling, and the student's final mark to the text box. Click "Adjust Mark" to save the changes to the database and the application system will send an email to notify the student with the change of his/her mark automatically. Click "Cancel" if you would like to go back to the dashboard. 

##### Feedback
- You can view student feedback by clicking the "view feedback" button at the top corner of the dashboard. 

#### **STUDENT'S MANUAL**
##### Register
- Enter your valid student email address and password, click "Register"
- You will see a screen to ask you to input your first name, last name and student number to complete your registration process
- You wll see a popup window says "Registration Successful" after you have entered all the information properly when you register

##### Login
- After you have registered successfully, you will be able to log in to the student dashboard using the same email address and password that you registered with

##### Solving Problem Sets
- Click "Open" on the assignment you want to start
- Answer each question by selecting a multiple choice option
- Click "Save and Close" if you have not finished the assignment
- Click "Submit and Grade" to grade your assignment and get your mark
- If you click "Save and Close", the application will auto-fill your previous saved answers for each question and set it as default option when you open the assignment again next time.

##### Auto-marking & Submission Summary
- The application will auto grade your assignment after you click "Submit and Grade". There will be a submission summary to notify you which questions you have got right and which questions you have got wrong for the current submission. Your final mark of this assignment will get adjusted if your current try is better than the highest mark you have got in the history. 
- You can view each assignment submission summary after the deadline of the assignment passed. Find your "Closed" assignments and click the "Result" button on the assignment result you want to reivew. You will be able to see your final mark, avergae mark, number of tries, and time spent as feedback. Click "Done" after reviewing it and the application would take you back to the dashboard.

##### Remarks
- Click the "Remark" button beside the assignment you wish to get remarked
- Explain why you want a remark in the message box and then click "Submit" to request a remark
- The remark will be sent to instructors directly by email and you will be notified if your grade has been changed

##### Feedback
- You can communicate with your professors or TAs by sending them feedback messages. The "send feedback" button is at the right corner of the dashboard. Click "send feedback" button to write a feedback message.

###### Note: GitHub may have problems displaying some of our documents. For this reason, it is recommended you download the document instead of using GitHub's preview to view it.
