# SQL Queries and Java Integration - universityDB
In this project, you will focus on writing SQL queries and embedding them into Java code using JDBC to implement a standalone program that answers several queries about the universityDB. Before starting, please drop the entire database and use the universityDB script file to re-create it.

## Java Files
You are provided with two Java files: TestMyQuery.java and MyQuery.java.

## TestMyQuery.java
This file contains the main function for running the program. You should only modify three variables: mydatabase, username, and password, replacing them with your own database information

String serverName = "10.219.0.50:3306";

String mydatabase = "DBUserName_universityDB";

String url = "jdbc:mysql://" + serverName + "/" + mydatabase; // a JDBC url

String username = "DBUsername";

String password = "DBPassword";

## MyQuery.java
This is the file where you need to implement the query functions. Feel free to make any necessary modifications to the file.

# Queries (35 points)
There are 7 queries (Queries 1-7) in this assignment. Each query carries 5 points. The queries vary in difficulty, so if you get stuck on a harder one, try an easier one first, and then come back to the tough one.

## Query 0: List the names of students who took some classes in Fall 2009.

## Query 1: Calculate GPA for each student
Convert letter grades into numeric grades and calculate GPA for each student using the given formula. Round the decimals to the hundredths place.

## Query 2: Find all the course sections offered in the morning. Ignore sections with zero enrollments.
Include course information (course_id, title, section_id, semester, year, instructor name) along with the enrollment number for each course.

## Query 3: Find the most frequently used classroom

## Query 4: Find the prereq for each course
List the name of each course along with the name of its prerequisite course. Include courses without any prerequisites and set the prereq field to N/A.

## Query 5: Update Tot_Cred
Update the 'tot_cred' field in the 'studentCopy' table with the real total credits the students received.

## Query 6: Write a stored procedure to get department information
Define a stored procedure in universityDB named deptInfo. This procedure takes department name as input and returns the number of instructors in the department, the total salary amount, and the budget of the department.

## Query 7: Find the first and the last semesters for each student
For each student, find the first and the last semester in which they have taken a course. Combine the semester and the year information in one field in your result.

# Compiling and Running Your Code

## Eclipse Users
1. Download the following files from Canvas: TestMyQuery.java, MyQuery.java, and mysql-connector-java-8.0.19.jar.
2. Open TestMyQuery.java using Eclipse and edit variables password, username, and password in TestMyQuery.java.
3. Go to Project → Properties → Library → Add External JAR, and add mysql-connector-java-8.0.19.jar file.
4. Now you should be able to compile and run TestMyQuery without any error messages.

## jGRASP Users
1. Download the following files from Canvas: TestMyQuery.java, MyQuery.java, and mysql-connector-java-8.0.19.jar.
2. Open TestMyQuery.java using jGRASP and edit variables password, username, and password in TestMyQuery.java.
3. Go to Settings → PATH/CLASSPATH → Workspace, select CLASSPATHS tab, and add a new class path pointing to mysql-connector-java-8.0.19.jar file.
4. Now you should be able to compile and run TestMyQuery without any error messages.

## IntelliJ Users
1. Download the following files from Canvas: TestMyQuery.java, MyQuery.java, and mysql-connector-java-8.0.19.jar.
2. Open TestMyQuery.java using IntelliJ and edit variables password, username, and password in TestMyQuery.java.
3. Go to File from the toolbar → Select Project Structure → Select Modules at the left panel → Select Dependencies tab → Select + icon → Select 1 JARs or directories option, and add mysql-connector-java-8.0.19.jar file.
4. Now you should be able to compile and run TestMyQuery without any error messages



