package backend.userSession;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;
import java.util.regex.Pattern;

import shared.interfaces.ProfessorCommands;
import shared.objects.Assignment;
import shared.objects.Course;
import shared.objects.EmailInfo;
import shared.objects.Grade;
import shared.objects.Professor;
import shared.objects.SendMessage;
import shared.objects.Student;
import shared.objects.StudentEnrollment;
import shared.objects.Submission;
import shared.objects.User;

/**
 * A class that serves professor users and implements professor commands.
 * 
 * @author Trevor Le (30028725), Qasim Muhammad (30016415), Jimmy Truong
 *         (30017293)
 * @version 1.01
 * @since April 6, 2018
 */
public class ProfessorSession extends ClientSession implements ProfessorCommands
{
	/**
	 * The professor associated with this session
	 */
	private Professor professor;

	/**
	 * Constructor for the ProfessorSession
	 * 
	 * @param socket
	 *            The socket used to connect the server with the client
	 */
	public ProfessorSession(Socket socket)
	{
		super(socket);
	}

	@Override
	protected boolean interpretMessage(SendMessage<?> command)
	{
		String interpreter[] = command.getCommand().split(";");
		String commandType = interpreter[0] + ";";

		if (commandType.equals(CMD_INSERT))
		{
			handleInsert(interpreter[1], command.getContents());

		} else if (commandType.equals(CMD_REMOVE))
		{
			System.err.println("!---------------------------------------!");
			System.err.println(
					"The command: " + command + " has not been implemented");
			System.err.println("\t\t - " + commandType);
			System.err.println("!---------------------------------------!");

		} else if (commandType.equals(CMD_RECEIVE))
		{
			handleRecieve(interpreter[1], command.getContents());

		} else if (commandType.equals(CMD_MODIFY))
		{
			handleModify(interpreter[1], command.getContents());

		} else if (commandType.equals(CMD_EMAIL))
		{

			super.handleEmail((EmailInfo) command.getContents());

		} else if (commandType.equals(CMD_LOGOUT))
		{
			return false;

		} else
		{
			System.err.println("!---------------------------------------!");
			System.err.println("An unknown command was received. It was: ");
			System.err.println("\t\t - " + commandType);
			System.err.println("!---------------------------------------!");
			try
			{
				throw new Exception();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return true;
	}

	/**
	 * Handles client commands having to do with them modifying data
	 * 
	 * @param modify
	 *            The specific command from an interface
	 * @param getmessageObject
	 *            The object that is used to help modify
	 */
	private void handleModify(String modify, Object getmessageObject)
	{
		if (modify.equals(MODIFY_COURSE_ACTIVE))
		{
			database.getCourseTable()
					.setActive(((Course) getmessageObject).getId(), true);

		} else if (modify.equals(MODIFY_COURSE_INACTIVE))
		{
			database.getCourseTable()
					.setActive(((Course) getmessageObject).getId(), false);

		} else if (modify.equals(MODIFY_ASSIGNMENT_ACTIVE))
		{
			database.getAssignmentTable()
					.setActive(((Assignment) getmessageObject).getId(), true);

		} else if (modify.equals(MODIFY_ASSIGNMENT_INACTIVE))
		{
			database.getAssignmentTable()
					.setActive(((Assignment) getmessageObject).getId(), false);

		} else
		{
			System.err.println("!---------------------------------------!");
			System.err.println("An unknown modify was received. It was: ");
			System.err.println("\t\t - " + modify);
			System.err.println("!---------------------------------------!");
		}
	}

	/**
	 * Handles client commands having to do with them receiving data
	 * 
	 * @param type
	 *            The specific command from an interface
	 * @param getmessageObject
	 *            The object that is used for some commands
	 */
	private void handleRecieve(String type, Object getMessage)
	{
		if (type.equals(RECEIVE_COURSES))
		{
			Vector<Course> myCourses = database.getCourseTable()
					.searchByProfId(professor.getId());
			sendObject(myCourses);

		} else if (type.equals(RECEIVE_STUDENT_BY_ID))
		{
			Student myEnrolledStudents = (Student) database.getUserTable()
					.getUserByID(((int) getMessage));
			sendObject(myEnrolledStudents);

		} else if (type.equals(RECEIVE_STUDENT_BY_LASTNAME))
		{
			Vector<Student> myEnrolledStudents = database.getUserTable()
					.searchLastName(((String) getMessage));
			sendObject(myEnrolledStudents);

		} else if (type.equals(RECEIVE_ALL_STUDENTS))
		{
			Vector<Student> allStudents = database.getUserTable().allStudents();
			sendObject(allStudents);

		} else if (type.equals(RECEIVE_ALL_ENROLLED_STUDENTS))
		{
			Vector<Integer> enrolled = database.getStudentEnrollmentTable()
					.getAllEnrolledStudents(((Course) getMessage).getId());
			Vector<Student> enrolledStudent = new Vector<Student>();
			for (int i = 0; i < enrolled.size(); i++)
			{
				enrolledStudent.add((Student) database.getUserTable()
						.getUserByID(enrolled.get(i)));
			}
			sendObject(enrolledStudent);

		} else if (type.equals(RECEIVE_ALL_ASSIGNMENTS))
		{
			Vector<Assignment> allAssignments = database.getAssignmentTable()
					.getAllAssignments(((Course) getMessage).getId());
			sendObject(allAssignments);

		} else if (type.equals(RECEIVE_STUDENT_IS_ENROLLED))
		{

			int studentID = ((StudentEnrollment) getMessage).getStudent_id();
			int courseID = ((StudentEnrollment) getMessage).getCourse_id();

			Boolean isEnrolled = database.getStudentEnrollmentTable()
					.isStudentEnrolled(studentID, courseID);
			sendObject(isEnrolled);

		} else if (type.equals(RECEIVE_STUDENT_SUBMISSION))
		{
			super.sendBackFile(((Submission) getMessage).getPath());

		} else if (type.equals(RECEIVE_ASSIGNMENT))
		{
			super.sendBackFile(((Assignment) getMessage).getPath());

		}

		else if (type.equals(RECEIVE_ALL_SUBMISSIONS))
		{
			Vector<Integer> allAssignIDs = database.getAssignmentTable()
					.searchByCourse(((Course) getMessage).getId());

			Vector<Submission> allSubmission = new Vector<Submission>();

			for (int i = 0; i < allAssignIDs.size(); i++)
			{
				allSubmission.addAll(database.getSubmissionTable()
						.searchByAssign(allAssignIDs.get(i)));
			}

			sendObject(allSubmission);

		}

		else
		{

			System.err.println("!---------------------------------------!");
			System.err.println("An unknown type was received. It was: ");
			System.err.println("\t\t - " + type);
			System.err.println("!---------------------------------------!");
		}
	}

	/**
	 * Handles client commands having to do with keeping database updated
	 * 
	 * @param interpreter
	 *            The specific command from an interface
	 * @param getmessageObject
	 *            The object that is being inserted
	 */
	private void handleInsert(String type, Object getmessageObject)
	{
		if (type.equals(INSERT_COURSE))
		{
			database.getCourseTable().add((Course) getmessageObject);

		} else if (type.equals(INSERT_ENROLLMENT))
		{
			database.getStudentEnrollmentTable()
					.add((StudentEnrollment) getmessageObject);

		} else if (type.equals(INSERT_UNENROLLMENT))
		{
			database.getStudentEnrollmentTable()
					.remove((StudentEnrollment) getmessageObject);

		} else if (type.equals(INSERT_ASSIGNMENT))
		{

			Assignment profAssign = (Assignment) getmessageObject;

			String fileName = profAssign.getPath();
			System.out.println("Before: " + fileName);

			String toSplit[] = profAssign.getPath()
					.split(Pattern.quote(File.separator));
			
			
			
			profAssign.setPath(DATABASE_STORAGE + profAssign.getTitle()
					+ File.separator + (toSplit[toSplit.length - 1]));

			System.out.println(profAssign.getPath());
			
			database.getAssignmentTable().add(profAssign);
			byte[] file;
			try
			{
				file = (byte[]) objectIn.readObject();
				System.out.println("FILE TITLE: " + profAssign.getTitle());
				fileHelper.checkDir(DATABASE_STORAGE + profAssign.getTitle());
				fileHelper.storeFile(file, profAssign);
			} catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}

		} else if (type.equals(INSERT_GRADE))
		{
			Grade myGrade = (Grade) getmessageObject;
			Submission toChange = database.getSubmissionTable().
					searchByCourseAndStudentIDAndAssignID(myGrade.getStudent_id(), myGrade.getAssignID());
			if(myGrade.getGrade() > toChange.getGrade())
			{
				database.getSubmissionTable().updateGrade(myGrade.getGrade(), toChange.getId());
			}
			
			database.getGradeTable().add(myGrade);
		} else
		{
			System.err.println("!---------------------------------------!");
			System.err.println("An unknown type was received. It was: ");
			System.err.println("\t\t - " + type);
			System.err.println("!---------------------------------------!");

		}
	}

	@Override
	public void setUser(User user)
	{
		this.professor = (Professor) user;
	}
}
