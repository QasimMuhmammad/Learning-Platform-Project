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

import backend.database.tables.AssignmentTable;
import backend.database.tables.CourseTable;
import backend.database.tables.GradeTable;
import backend.database.tables.StudentEnrollmentTable;
import backend.database.tables.SubmissionTable;
import backend.database.tables.UserTable;
import shared.interfaces.UserInfo;
import shared.objects.Course;
import shared.objects.Professor;
import shared.objects.Student;

/**
 * Provides a class that communicates with the database and stores its various
 * tables.
 * 
 * @author Trevor Le (30028725), Qasim Muhammad (30016415), Jimmy Truong
 *         (30017293)
 * @version 1.0
 * @since April 6, 2018
 */
public class Database implements DatabaseProperties, UserInfo
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
	@SuppressWarnings("unused")
	private PreparedStatement preparedStatement;

	/**
	 * The assignment table.
	 */
	private AssignmentTable assignmentTable;

	/**
	 * The course table.
	 */
	private CourseTable courseTable;

	/**
	 * The grade table.
	 */
	private GradeTable gradeTable;
	private StudentEnrollmentTable studentEnrollmentTable;
	private UserTable userTable;
	private SubmissionTable submissionTable;

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
	 * Returns the assignment table
	 * 
	 * @return The assignment table
	 */
	public AssignmentTable getAssignmentTable()
	{
		return assignmentTable;
	}

	/**
	 * Returns the course table
	 * 
	 * @return the course table
	 */
	public CourseTable getCourseTable()
	{
		return courseTable;
	}

	/**
	 * Returns the grade table
	 * 
	 * @return The grade table
	 */
	public GradeTable getGradeTable()
	{
		return gradeTable;
	}

	/**
	 * Returns the student enrollment table
	 * 
	 * @return The sudent enrollment table
	 */
	public StudentEnrollmentTable getStudentEnrollmentTable()
	{
		return studentEnrollmentTable;
	}

	/**
	 * Returns the user table
	 * 
	 * @return The user table
	 */
	public UserTable getUserTable()
	{
		return userTable;
	}

	/**
	 * Returns the submission table
	 * 
	 * @return The submission table
	 */
	public SubmissionTable getSubmissionTable()
	{
		return submissionTable;
	}

	/**
	 * Used to create the database itself
	 */
	@SuppressWarnings("unused")
	private void createDB()
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

	/**
	 * Attaches all the tables to the database
	 */
	private void addAllTables()
	{
		assignmentTable = new AssignmentTable(dbConnection, "AssignmentTable");
		userTable = new UserTable(dbConnection, "UserTable");
		courseTable = new CourseTable(dbConnection, "CourseTable");
		studentEnrollmentTable = new StudentEnrollmentTable(dbConnection,
				"StudentEnrollmentTable");
		submissionTable = new SubmissionTable(dbConnection, "SubmissionTable");
		gradeTable = new GradeTable(dbConnection, "GradeTable");
	}

	/**
	 * Deletes the connection with the database and the table
	 */
	private void removeAllTables()
	{
		assignmentTable.removeTable();
		userTable.removeTable();
		courseTable.removeTable();
		studentEnrollmentTable.removeTable();
		submissionTable.removeTable();
		gradeTable.removeTable();
	}

	/**
	 * Creates the tables in the data , used ot initalize
	 */
	private void createAllTables()
	{
		assignmentTable.createTable();
		userTable.createTable();
		courseTable.createTable();
		studentEnrollmentTable.createTable();
		submissionTable.createTable();
		gradeTable.createTable();
	}

	/**
	 * Used to read users into the User table for initialization
	 * 
	 * @param fileName
	 *            The textfile containing the users
	 */
	private void readUser(String fileName)
	{
		try
		{
			BufferedReader fileReader = new BufferedReader(
					new FileReader(fileName));
			String line = fileReader.readLine();

			while (line != null)
			{
				String toAdd[] = line.split(";");
				int id = Integer.parseInt(toAdd[0]);
				String firstName = toAdd[1];
				String lastName = toAdd[2];
				String email = toAdd[3];
				String type = toAdd[4];
				String password = toAdd[5];

				if (type.equals(IS_PROFESSOR))
				{
					this.getUserTable().add(new Professor(id, firstName,
							lastName, email, password));

				} else if (toAdd[4].equals(IS_STUDENT))
				{
					this.getUserTable().add(new Student(id, firstName, lastName,
							email, password));

				}
				line = fileReader.readLine();
			}
			fileReader.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Used to read users into the Course table for the professor
	 * 
	 * @param fileName
	 *            The textfile containing the users
	 */
	private void readCourses(String fileName)
	{
		try
		{
			BufferedReader fileReader = new BufferedReader(
					new FileReader(fileName));
			String line = fileReader.readLine();

			while (line != null)
			{
				String toAdd[] = line.split(";");
				int courseID = Integer.parseInt(toAdd[0]);
				int profID = Integer.parseInt(toAdd[1]);
				String courseName = toAdd[2];
				boolean isActive = Boolean.parseBoolean(toAdd[3]);

				this.getCourseTable().add(
						new Course(courseID, profID, courseName, isActive));

				line = fileReader.readLine();
			}
			fileReader.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Reset the database to a known state.
	 * 
	 * @param args
	 *            unused
	 */
	public static void main(String[] args)
	{
		Database myDatabase = new Database();
		// myDatabase.createDB();
		myDatabase.removeAllTables();
		myDatabase.createAllTables();
		myDatabase.addAllTables();
		myDatabase.readUser("users.txt");
		myDatabase.readCourses("courses.txt");
	}
}
