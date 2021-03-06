package shared.objects;

import java.io.File;
import java.io.Serializable;
import java.util.Random;

/**
 * Provides a class to represent a submission object.
 * 
 * @author Trevor Le (30028725), Qasim Muhammad (30016415), Jimmy Truong
 *         (30017293)
 * @version 1.0
 * @since April 13, 2018
 */
public class Submission implements Serializable
{
	/**
	 * The version of the class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The id for the submission
	 */
	private int id;

	/**
	 * The id for the assignment which the submission this is attributed with
	 */
	private int assign_id;

	/**
	 * The id for the student who submits the file
	 */
	private int student_id;

	/**
	 * Pathway from where the file comes from
	 */
	private String path;

	/**
	 * The grade for the submission
	 */
	private int grade;

	/**
	 * Comment made by the submitted. Not used.
	 */
	private String comment;

	/**
	 * Title of the file in the submission.
	 */
	private String title;

	/**
	 * The time stamp for the file submission.
	 */
	private String timestamp;

	public Submission(int assignID, int stuID, String filePath, int subGrade,
			String subComment, String subTitle, String time)
	{
		Random random = new Random();
		id = random.nextInt(6000) + 4000;
		assign_id = assignID;
		student_id = stuID;
		path = filePath;
		grade = subGrade;
		comment = subComment;
		if (subTitle != null)
		{
			for (int i = 0; i < subTitle.length(); i++)

			{
				if (subTitle.charAt(i) == '.')
				{
					subTitle = subTitle.substring(i, subTitle.length());
					break;
				}
			}
		}
		title = subTitle;
		timestamp = time;
	}

	public Submission(int subId, int assignID, int stuID, String filePath,
			int subGrade, String subComment, String subTitle, String time)
	{

		id = subId;
		assign_id = assignID;
		student_id = stuID;
		path = filePath;
		grade = subGrade;
		comment = subComment;
		title = subTitle;
		timestamp = time;
	}

	public int getId()
	{
		return id;
	}

	public int getAssign_id()
	{
		return assign_id;
	}

	public int getStudent_id()
	{
		return student_id;
	}

	public String getPath()
	{
		return path;
	}

	public int getGrade()
	{
		return grade;
	}

	public String getComment()
	{
		return comment;
	}

	public String getTitle()
	{
		return title;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public void setPath(String string)
	{
		path = string;

	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public void setDate(String date)
	{
		timestamp = date;
	}

	public void setTitle(String title)
	{
		if (title != null)
		{
			for (int i = 0; i < title.length(); i++)

			{
				if (title.charAt(i) == File.separatorChar)
				{
					title = title.substring(i, title.length());
					break;
				}
			}
		}
		this.title = title;
	}
}
