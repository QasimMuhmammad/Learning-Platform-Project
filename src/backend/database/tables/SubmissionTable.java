package backend.database.tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import shared.objects.Submission;

/**
 *
 * @author Trevor Le (30028725), Qasim Muhammad (30016415), Jimmy Truong
 *         (30017293)
 * @version 1.0
 * @since April 6, 2018
 */
public class SubmissionTable extends Table<Submission>
{

	/**
	 * Constructor for the submission table
	 *
	 * @param connectionToDB
	 *            Connection with SQL server
	 * @param tableName
	 */
	public SubmissionTable(Connection connectionToDB, String tableName)
	{
		super(connectionToDB, tableName);
	}

	@Override
	public void add(Submission toAdd)
	{
		String sql = "INSERT INTO " + tableName + " VALUES"
				+ "(?,?,?,?,?,?,?,?)";
		try
		{

			preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setInt(1, toAdd.getId());
			preparedStatement.setInt(2, toAdd.getAssign_id());
			preparedStatement.setInt(3, toAdd.getStudent_id());
			preparedStatement.setString(4, toAdd.getPath());
			preparedStatement.setString(5, toAdd.getTitle());
			preparedStatement.setInt(6, toAdd.getGrade());
			preparedStatement.setString(7, toAdd.getComment());
			preparedStatement.setString(8, toAdd.getTimestamp());
			preparedStatement.executeUpdate();

			System.out.println("Added Grade ");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void createTable()
	{
		String sql = "CREATE TABLE " + tableName + "(" + "ID INT(8) NOT NULL, "
				+ "ASSIGNID INT(8) NOT NULL, " + "STUDENTID INT(8) NOT NULL, "
				+ "PATH VARCHAR(255) NOT NULL, "
				+ "TITLE VARCHAR(50) NOT NULL, "
				+ "SUBMISSION_GRADE INT(3) NOT NULL,"
				+ "COMMENT VARCHAR(140) NOT NULL, "
				+ "TIMESTAMP VARCHAR(16) NOT NULL, " + "PRIMARY KEY ( id ) )";

		try
		{
			preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.executeUpdate();
			System.out.println("Created Table " + tableName);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Used by professor to look at submission by a specific student
	 *
	 * @param studentID
	 * @return
	 */
	public Vector<Submission> searchByStudentID(int studentID)
	{
		Vector<Submission> studentSubmissons = new Vector<Submission>();
		String sql = "SELECT * FROM " + tableName + " WHERE STUDENTID= ? ";
		ResultSet submission;
		try
		{
			preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setInt(1, studentID);
			submission = preparedStatement.executeQuery();
			if (submission.next())
			{

				studentSubmissons.add(new Submission(submission.getInt("ID"),
						submission.getInt("ASSIGNID"),
						submission.getInt("STUDENTID"),
						submission.getString("PATH"),
						submission.getInt("SUBMISSION_GRADE"),
						submission.getString("COMMENT"),
						submission.getString("TITLE"),
						submission.getString("TIMESTAMP")));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return studentSubmissons;

	}


	/**
	 * Searches the table by course ID''s
	 *
	 * @param courseID
	 *            The course ID to search for
	 * @return The vector containing all the submissions for a course ID
	 */
	public Vector<Submission> searchByAssign(int assignID)
	{
		Vector<Submission> assignSubmissons = new Vector<Submission>();
		String sql = "SELECT * FROM " + tableName + " WHERE ASSIGNID= ? ";
		ResultSet submission;
		try
		{
			preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setInt(1, assignID);
			submission = preparedStatement.executeQuery();
			while (submission.next())
			{

				assignSubmissons.add(new Submission(submission.getInt("ID"),
						submission.getInt("ASSIGNID"),
						submission.getInt("STUDENTID"),
						submission.getString("PATH"),
						submission.getInt("SUBMISSION_GRADE"),
						submission.getString("COMMENT"),
						submission.getString("TITLE"),
						submission.getString("TIMESTAMP")));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return assignSubmissons;
	}

	public Submission searchByCourseAndStudentIDAndAssignID(int studentID,  int assignID)
	{
		String sql = "SELECT * FROM " + tableName + " WHERE STUDENTID= ? AND ASSIGNID= ?";
		ResultSet submission;
		try
		{
			preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setInt(1, studentID);
			preparedStatement.setInt(2, assignID);
			
			submission = preparedStatement.executeQuery();
			while (submission.next())
			{

				return (new Submission(submission.getInt("ID"),
						submission.getInt("ASSIGNID"),
						submission.getInt("STUDENTID"),
						submission.getString("PATH"),
						submission.getInt("SUBMISSION_GRADE"),
						submission.getString("COMMENT"),
						submission.getString("TITLE"),
						submission.getString("TIMESTAMP")));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public void updateGrade(int grade, int id)
	{
		String sql = "UPDATE " + tableName + " SET SUBMISSION_GRADE="
				+ grade + " WHERE ID=?";
		try
		{
			preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}

}
