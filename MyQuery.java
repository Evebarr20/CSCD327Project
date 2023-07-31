/*****************************
Query the University Database
*****************************/
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.String;

public class MyQuery {

    private Connection conn = null;
	 private Statement statement = null;
	 private ResultSet resultSet = null;
    
    public MyQuery(Connection c)throws SQLException
    {
        conn = c;
        // Statements allow to issue SQL queries to the database
        statement = conn.createStatement();
    }
    
    public void findFall2009Students() throws SQLException
    {
        String query  = "select distinct name from student natural join takes where semester = \'Fall\' and year = 2009;";

        resultSet = statement.executeQuery(query);
    }
    
    public void printFall2009Students() throws IOException, SQLException
    {
	      System.out.println("******** Query 0 ********");
         System.out.println("name");
         while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number which starts at 1
			String name = resultSet.getString(1);
         System.out.println(name);
   		}        
    }

    public void findGPAInfo() throws SQLException
    {
        String query = "SELECT student.ID, name, ROUND(sum((CASE " +
                "WHEN grade = 'F' THEN 0.0 " +
                "WHEN grade = 'D-' THEN 0.7 " +
                "WHEN grade = 'D' THEN 1.0 " +
                "WHEN grade = 'D+' THEN 1.3 " +
                "WHEN grade = 'C-' THEN 1.7 " +
                "WHEN grade = 'C' THEN 2.0 " +
                "WHEN grade = 'C+' THEN 2.3 " +
                "WHEN grade = 'B-' THEN 2.7 " +
                "WHEN grade = 'B' THEN 3.0 " +
                "WHEN grade = 'B+' THEN 3.3 " +
                "WHEN grade = 'A-' THEN 3.7 " +
                "WHEN grade = 'A' THEN 4.0 " +
                "END) * credits) / sum(credits), 2) AS GPA " +
                "FROM student JOIN takes USING(ID) JOIN course USING(course_id) " +
                "WHERE grade is not null " +
                "GROUP BY student.ID;";

        resultSet = statement.executeQuery(query);
    }
    
    public void printGPAInfo() throws IOException, SQLException
    {
		   System.out.println("******** Query 1 ********");
        System.out.printf("| %5s | %-10s | %-4s |%n", "ID", "Name", "GPA");
        System.out.printf("-------------------------------%n");
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number which starts at 1
            int ID = resultSet.getInt(1);
            String Name = resultSet.getString(2);
            double GPA = resultSet.getDouble(3);
            System.out.printf("| %05d | %-10s | %,.2f |%n", ID, Name, GPA);
        }
        System.out.printf("-------------------------------%n");
        resultSet.close();
    }

    public void findMorningCourses() throws SQLException
    {
        String query ="SELECT course_id, sec_id, title, semester, year, name, count(DISTINCT takes.id) enrollment " +
                "from course NATURAL JOIN section NATURAL JOIN time_slot NATURAL JOIN teaches NATURAL JOIN instructor " +
                "JOIN takes USING (course_id, sec_id, semester, year) " +
                "WHERE start_hr <= 12 " +
                "GROUP BY course_id, sec_id, semester, year, name " +
                "HAVING count(DISTINCT takes.id) > 0;";
                this.resultSet = this.statement.executeQuery(query);

    }

    public void printMorningCourses() throws IOException, SQLException
    {
	   	System.out.println("******** Query 2 ********");
        System.out.printf("| %-9s | %1s | %-26s | %-8s | %4s | %-10s | %1s |%n", "course_id", "sec_id", "title", "semester", "year",
                "name", "enrollment");
        System.out.printf("-----------------------------------------------------------------------------------------------%n");
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number which starts at 1
            String course_id = resultSet.getString(1);
            int sec_id = resultSet.getInt(2);
            String title = resultSet.getString(3);
            String semester = resultSet.getString(4);
            int year = resultSet.getInt(5);
            String name = resultSet.getString(6);
            int enrollment = resultSet.getInt(7);
            System.out.printf("| %-9s | %01d      | %-26s | %-8s | %04d | %-10s | %01d          |%n", course_id, sec_id, title, semester, year, name, enrollment);
        }

    }

    public void findBusyClassroom() throws SQLException
    {
        String query  = "select building, room_number, count(course_id) as frequency " +
                "from section " +
                "group by room_number, building " +
                "having count(course_id) >= all (select count(course_id) " +
                "from section " +
                "group by room_number);";

        resultSet = statement.executeQuery(query);
 
    }

    public void printBusyClassroom() throws IOException, SQLException
    {
        System.out.println("******** Query 3 ********");
        System.out.printf("| %-10s | %-11s | %4s |%n", "Building", "Room_Number", "Frequency");
        System.out.printf("----------------------------------------%n");
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number which starts at 1
            String building = resultSet.getString(1);
            int room_number = resultSet.getInt(2);
            int frequency = resultSet.getInt(3);
            System.out.printf("| %-10s | %-11s | %01d         |%n", building, room_number, frequency);
        }
        System.out.printf("----------------------------------------%n");
        resultSet.close();
    }

    public void findPrereq() throws SQLException
    {
        String query = "SELECT c.title as course, " +
                "CASE " +
                "  WHEN c.course_id IN (SELECT course_id FROM prereq) " +
                "  THEN (SELECT c1.title " +
                "        FROM course AS c1, prereq AS p " +
                "        WHERE c.course_id = p.course_id " +
                "        AND c1.course_id = p.prereq_id) " +
                "  ELSE 'N/A' " +
                "END AS prereq " +
                "FROM course AS c;";

        resultSet = statement.executeQuery(query);

    }

    public void printPrereq() throws IOException, SQLException
    {
        System.out.println("******** Query 4 ********");
        System.out.printf("| %-26s | %-26s |%n", "Course", "Prereq");
        System.out.printf("-----------------------------------------------------------%n");
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number which starts at 1
            String course = resultSet.getString(1);
            String prereq = resultSet.getString(2);
            System.out.printf("| %-26s | %-26s |%n", course, prereq);
        }
        System.out.printf("-----------------------------------------------------------%n");
        resultSet.close();
    }
    public void updateTable() throws SQLException
    {
        statement.executeUpdate("drop table if exists studentCopy");

        statement.executeUpdate("CREATE TABLE studentCopy LIKE student");

        statement.executeUpdate("INSERT INTO studentCopy SELECT * FROM student");

       statement.executeUpdate("UPDATE studentCopy SET tot_cred = "
             + "(SELECT COALESCE(SUM(credits), 0) FROM takes NATURAL JOIN course "
             + "WHERE studentCopy.id = takes.id AND takes.grade != 'F' AND takes.grade IS NOT NULL)");



       String query1 = "SELECT studentCopy.*, COUNT(DISTINCT takes.course_id) AS num_of_courses "
             + "FROM studentCopy LEFT JOIN takes ON studentCopy.id = takes.id "
             + "GROUP BY studentCopy.id";
       resultSet = statement.executeQuery(query1);

    }

    public void printUpdatedTable() throws IOException, SQLException
    {
		   System.out.println("******** Query 5 ********");
        System.out.printf("| %5s | %-8s | %-10s | %-8s | %17s |%n", "id", "name", "dept_name", "tot_cred", "number_of_courses");
        System.out.printf("----------------------------------------------------------------%n");
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number which starts at 1
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String dept_name = resultSet.getString(3);
            int tot_cred = resultSet.getInt(4);
            int number_of_courses = resultSet.getInt(5);
            System.out.printf("| %05d | %-8s | %-10s | %-8s | %17s |%n", id, name, dept_name, tot_cred, number_of_courses);
        }
        System.out.printf("----------------------------------------------------------------%n");
        resultSet.close();
    }
	
	 public void findDeptInfo() throws SQLException
	 {
		  System.out.println("******** Query 6 ********");
          Scanner scan = new Scanner(System.in);
          System.out.println("Please enter the department name: ");
          String input = scan.nextLine();

          CallableStatement cStmt = conn.prepareCall("{call deptInfo(?, ?, ?, ?) }");

          cStmt.setString(1, input);
          cStmt.registerOutParameter(2, Types.INTEGER);
          cStmt.registerOutParameter(3, Types.FLOAT);
          cStmt.registerOutParameter(4, Types.FLOAT);

          cStmt.execute();

          int num_of_instructor = cStmt.getInt(2);
          float total_salary = cStmt.getFloat(3);
          float budget = cStmt.getFloat(4);

         System.out.println(input + " Department has " + num_of_instructor + " instructors.");
         System.out.println(input + " Department has a total salary of $" + total_salary);
         System.out.println(input + " Department has a budget of $" + budget);

         cStmt.close();

     }
    
    
    public void findFirstLastSemester() throws SQLException
    {
        statement.executeUpdate("CREATE TEMPORARY TABLE tempFirst AS "
                + "SELECT ID, name, CONCAT(CASE MIN(CASE semester "
                + "                             WHEN 'Fall' THEN 3 "
                + "                             WHEN 'Spring' THEN 1 "
                + "                             WHEN 'Summer' THEN 2 "
                + "                          END) "
                + "                        WHEN 3 THEN 'Fall' "
                + "                        WHEN 1 THEN 'Spring' "
                + "                        WHEN 2 THEN 'Summer' "
                + "                     END, ' ', year) AS first_semester "
                + "FROM takes NATURAL JOIN student AS T "
                + "WHERE year <= (SELECT MIN(year) "
                + "               FROM takes NATURAL JOIN student AS S "
                + "               WHERE S.ID = T.ID) "
                + "GROUP BY ID, year");

        statement.executeUpdate("CREATE TEMPORARY TABLE tempLast AS "
                + "SELECT ID, name, CONCAT(CASE MAX(CASE semester "
                + "                             WHEN 'Fall' THEN 3 "
                + "                             WHEN 'Spring' THEN 1 "
                + "                             WHEN 'Summer' THEN 2 "
                + "                          END) "
                + "                        WHEN 3 THEN 'Fall' "
                + "                        WHEN 1 THEN 'Spring' "
                + "                        WHEN 2 THEN 'Summer' "
                + "                     END, ' ', year) AS last_semester "
                + "FROM takes NATURAL JOIN student AS T "
                + "WHERE year >= (SELECT MAX(year) "
                + "               FROM takes NATURAL JOIN student AS S "
                + "               WHERE S.ID = T.ID) "
                + "GROUP BY ID, year");

        String query = ("SELECT ID, name, first_semester, last_semester "
                + "FROM tempFirst NATURAL JOIN tempLast");
        resultSet = statement.executeQuery(query);
    }

    public void printFirstLastSemester() throws IOException, SQLException
    {
        System.out.println("******** Query 7 ********");

        System.out.printf("| %5s | %-8s | %-15s | %-15s |%n", "id", "name", "First_Semester", "Last_semester");
        System.out.printf("--------------------------------------------------------%n");
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number which starts at 1
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String First_Semester = resultSet.getString(3);
            String Last_Semester = resultSet.getString(4);
            System.out.printf("| %05d | %-8s | %-15s | %-15s |%n", id, name, First_Semester, Last_Semester);
        }
        System.out.printf("--------------------------------------------------------%n");
        resultSet.close();
    }
}
