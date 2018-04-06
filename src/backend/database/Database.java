package backend.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import backend.database.*;
import backend.database.tables.AssignmentTable;
import backend.database.tables.CourseTable;
import backend.database.tables.GradeTable;
import backend.database.tables.StudentEnrollmentTable;
import backend.database.tables.SubmissionTable;
import backend.database.tables.UserTable;
import backend.interfaces.DatabaseProperties;
import sharedobjects.Course;
import sharedobjects.Professor;
import sharedobjects.Student;
import sharedobjects.User;

/**
 * 
 * @author Trevor Le (30028725), Qasim Muhammad (30016415), Jimmy Truong
 *         (30017293)
 * @version 1.0
 * @since April 6, 2018
 */
public class Database implements DatabaseProperties
{

	/**
	 * Used to connect to the database
	 */
	private Connection dbConnection;

	/**
	 * Used to create DB
	 */
	private Statement statement;

	/**
	 * Used for many different aspects for the table
	 */
	private PreparedStatement preparedStatement;

	AssignmentTable assignmentTable;
	CourseTable courseTable;
	GradeTable gradeTable;
	StudentEnrollmentTable studentEnrollmentTable;
	UserTable userTable;
	SubmissionTable submissionTable;

	/**
	 * Constructor for the DB to initialize the connection
	 */
	public Database()
	{
		try
		{
			// If this throws an error, make sure you have added the mySQL
			// connector JAR to the project
			Class.forName("com.mysql.jdbc.Driver");

			// If this fails make sure your connectionInfo and login/password
			// are correct
			
			Properties connectionProperties = createDatabaseProperties();

			dbConnection = DriverManager.getConnection(CONNECTION_URL,
					connectionProperties);
			System.out.println("Connected to: " + CONNECTION_URL + "\n");
			addAllTables();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Creates a properties object to be used by a DriverManager to log into a
	 * database.
	 * 
	 * @return the login properties
	 */


	
	private Properties createDatabaseProperties()
	{
		Properties databaseProperties = new Properties();
		databaseProperties.put("user", USERNAME);
		databaseProperties.put("password", PASSWORD);

		return databaseProperties;
	}

	public AssignmentTable getAssignmentTable()
	{
		return assignmentTable;
	}

	public CourseTable getCourseTable()
	{
		return courseTable;
	}

	public GradeTable getGradeTable()
	{
		return gradeTable;
	}

	public StudentEnrollmentTable getStudentEnrollmentTable()
	{
		return studentEnrollmentTable;
	}

	public UserTable getUserTable()
	{
		return userTable;
	}

	public SubmissionTable getSubmissionTable()
	{
		return submissionTable;
	}

	/**
	 * Used to create the database itself
	 */
	public void createDB()
	{
		try
		{
			statement = dbConnection.createStatement();
			statement.executeUpdate("CREATE DATABASE " + DATABASE_NAME);
			System.out.println("Created Database " + DATABASE_NAME);
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void addAllTables()
	{
		assignmentTable = new AssignmentTable(dbConnection, "AssignmentTable");
		userTable = new UserTable(dbConnection, "UserTable");
		courseTable = new CourseTable(dbConnection, "CourseTable");
		studentEnrollmentTable = new StudentEnrollmentTable(dbConnection,
				"StudentEnrollmentTable");
		submissionTable = new SubmissionTable(dbConnection, "SubmissionTable");
		gradeTable = new GradeTable(dbConnection, "GradeTable");

	}

	public void removeAllTables()
	{
		assignmentTable.removeTable();
		userTable.removeTable();
		courseTable.removeTable();
		studentEnrollmentTable.removeTable();
		submissionTable.removeTable();
		gradeTable.removeTable();

	}

	public void createAllTables()
	{
		assignmentTable.createTable();
		userTable.createTable();
		courseTable.createTable();
		studentEnrollmentTable.createTable();
		submissionTable.createTable();
		gradeTable.createTable();

	}

	public static void main(String[] args)
	{
		Database myDatabase = new Database();
		  //myDatabase.createDB();
		 myDatabase.removeAllTables();
		 myDatabase.createAllTables();
		 myDatabase.addAllTables();
		 myDatabase.readUser("users.txt");
		 myDatabase.readCourses("courses.txt");

	}

	public void readUser(String fileName) {
		 try
		{
			BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
			String line = fileReader.readLine();
			
			while (line!=null)
			{
				String toAdd[] = line.split(" ");
				
				if(toAdd[4].equals("P"))
				{
					this.getUserTable().add(new Professor(Integer.parseInt(toAdd[0]), toAdd[1], toAdd[2], toAdd[3], toAdd[4], toAdd[5]));
				}
				
				if(toAdd[4].equals("S"))
				{
					this.getUserTable().add(new Student(Integer.parseInt(toAdd[0]), toAdd[1], toAdd[2], toAdd[3], toAdd[4], toAdd[5]));
				}
				line = fileReader.readLine();

			}
			
			
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		
		}catch (IOException e) {
			
		}
		
	}
	
	public void readCourses(String fileName){
		 try
			{
				BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
				String line = fileReader.readLine();
				
				while (line != null)
				{
					String toAdd[] = line.split(" ");
					
					this.getCourseTable().add(new Course(Integer.parseInt(toAdd[0]), Integer.parseInt(toAdd[1]),
													toAdd[2], Boolean.parseBoolean(toAdd[3])));
					line = fileReader.readLine();
				}
				
				
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			
			}catch (IOException e) {
				
			}
		
	}
	
}
