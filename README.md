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
- This week, we implemented the user login/register interface and the InstructorAssignmentListing panel.
### Login Screen
![Login Screen](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Deliverable_4/Login_Screen.png)
### Professor Dashboard
![Professor Dashboard](https://github.com/CSCC01F17/L02_11/blob/master/Documents/Deliverable_4/Instructor_Dashboard.png)

### User Instructions
- Go to ApplicationDriver.java under the controllers package
- Run ApplicationDriver.java and you will see a login/register interface
- For first time users, you must register your account by typing in your email address and passwords and then click the register button
    - For this delieverable, you should register as an instructor by checking the instructor checkbox in order to see the futher features about this application
- After you have registered successfully, you will be able to login to the instructor dashboard using the same email address and passwords that you just registered (do not forget to login as a instructor by clicking the instructor checkbox)
- As an instructor, you can create an assignment by clicking the "+ Add Assignment" button at the top right corner. 
  - As an instructor, you can manually enter each problem and multiple choice options
  - As an instructor, you can preset the solution for the question by clicking the corresponding radio button for the correct multiple choice option
  - After you have entered all the required information for the question, you can click "Add this Problem" to add the problem into the problem set
  - Repeatly do the steps above until you have added all the problems to the problem set for the current assignment. 
  - You can click the "create" button after you have done creating an assignment. 
  - Close the assignment creation interface by clicking the x button at the left most corner, you will see your new added assignment on the dashboard under the unreleased assignments session
- As an instructor, you can release and unrelease assignments by clicking the labeled buttons corresponding to the appropriate assignments
###### Note: GitHub may have problems displaying some of our documents. For this reason, it is recommended you download the document instead of using GitHub's preview to view it.
