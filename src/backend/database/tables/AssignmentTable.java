package backend.database.tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import shared.objects.Assignment;

/**
 * 
 * @author Trevor Le (30028725), Qasim Muhammad (30016415), Jimmy Truong
 *         (30017293)
 * @version 1.01
 * @since April 6, 2018
 */
public class AssignmentTable extends Table<Assignment> implements Activable
{
	String tableName = "AssignmentTable";

	public AssignmentTable(Connection connectionToDB, String tableName)
	{
		super(connectionToDB, tableName);
	}

	@Override
	public void add(Assignment toAdd)
	{
		String sql = "INSERT INTO " + tableName + " VALUES" + "(?,?,?,?,?,?)";

		try
		{

			preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setInt(1, toAdd.getId());
			preparedStatement.setInt(2, toAdd.getCourse_id());
			preparedStatement.setString(3, toAdd.getTitle());
			preparedStatement.setString(4, toAdd.getPath());
			preparedStatement.setBoolean(5, toAdd.getActive());
			preparedStatement.setString(6, toAdd.getDueDate());
			preparedStatement.executeUpdate();

			System.out.println("Added Assignment " + toAdd.getTitle()
					+ " creadted for course num " + toAdd.getCourse_id());
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void createTable()
	{
		String sql = "CREATE TABLE " + tableName + "(" + "ID INT(8) NOT NULL, "
				+ "COURSEID INT(8) NOT NULL, " + "TITLE VARCHAR(50) NOT NULL, "
				+ "PATH VARCHAR(100) NOT NULL, " + "ACTIVE BIT(1) NOT NULL, "
				+ "DUEDATE VARCHAR(16) NOT NULL," + "PRIMARY KEY ( id ) )";

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

	public Vector<Assignment> searchByCourseID(int courseID)
	{
		Vector<Assignment> myAssignments = new Vector<Assignment>();

		String sql = "SELECT * FROM " + tableName + " WHERE COURSEID= ?";
		ResultSet assigns;
		try
		{
			preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setInt(1, courseID);
			assigns = preparedStatement.executeQuery();
			if (assigns.next())
			{
				myAssignments.add(new Assignment(assigns.getInt("ID"),
						assigns.getInt("COURSEID"), assigns.getString("TITLE"),
						assigns.getString("PATH"), assigns.getBoolean("ACTIVE"),
						assigns.getString("DUEDATE")));

			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return myAssignments;

	}

	public Vector<Assignment> getAllAssignments(int id)
	{
		String sql = "SELECT * FROM " + tableName + " WHERE COURSEID= ? ";
		Vector<Assignment> userList = new Vector<Assignment>();
		ResultSet assign;

		try
		{

			preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			assign = preparedStatement.executeQuery();
			while (assign.next())
			{
				userList.add(new Assignment(assign.getInt("ID"),
						assign.getInt("COURSEID"), assign.getString("TITLE"),
						assign.getString("PATH"), assign.getBoolean("ACTIVE"),
						assign.getString("DUEDATE")));

			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return userList;
	}

	@Override
	public void setActive(int assignID, boolean isActive)
	{
		String sql = "UPDATE " + tableName + " SET ACTIVE="
				+ ((Boolean) isActive).toString().toUpperCase() + " WHERE ID=?";
		try
		{
			preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setInt(1, assignID);
			preparedStatement.executeUpdate();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
