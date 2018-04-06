package backend.userSession;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import sharedobjects.*;


public class ProfessorSession extends ClientSession
{

	Professor user;
	public ProfessorSession(Socket socket)
	{
		super(socket);
		
	}

	public void setProfessor(Professor thisUser)
	{
		user = thisUser;
	}
	
	
	
	@Override
	public void run()
	{
		boolean isRunning = true;

		while (isRunning)
		{
			try
			{
				SendMessage newMessage = (SendMessage)inputStream.readObject();
				interpretMessage(newMessage);
				

			} catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}


		
	}

	@Override
	public void write()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	void interpretMessage(SendMessage command)
	{
		String interpreter[] = command.getCommand().split(" ");
		
		if(interpreter[0].equals("INSERT"))
		{
			handleInsert(interpreter,command.getmessageObject());
		}
		
		else if(interpreter[0].equals("REMOVE"))
		{
			
		}
		
		else if(interpreter[0].equals("RECEIVE"))
		{
			handleRecieve(interpreter, command.getmessageObject());
		}
		
		else if(interpreter[0].equals("MODIFY"))
		{
			handleModify(interpreter, command.getmessageObject());
		}
		
		
	}

	private void handleModify(String[] interpreter, Object getmessageObject)
	{
		if(interpreter[1].equals("COURSEACTIVE"))
		{
			myDatabase.getCourseTable().setActive( ((Course)getmessageObject).getId());
		}
		
		if(interpreter[1].equals("COURSEINACTIVE"))
		{
			myDatabase.getCourseTable().setInactive(((Course)getmessageObject).getId() );

		}
	}

	private void handleRecieve(String[] interpreter, Object getMessage)
	{
		try 
		{
		
			if(interpreter[1].equals("COURSES"))
			{
				Vector<Course> myCourses = myDatabase.getCourseTable().searchByProfId(user.getId());
				
				outputStream.writeObject(myCourses);
				outputStream.flush();
			}
			
			if(interpreter[1].equals("STUDENTBYID"))
			{
				
				Student myEnrolledStudents = (Student) myDatabase.getUserTable().getUserByID(((int)getMessage));
				outputStream.writeObject(myEnrolledStudents);
				outputStream.flush();
				
			}
			
			if(interpreter[1].equals("STUDENTBYLAST")) 
			{
				Vector<Student> myEnrolledStudents = myDatabase.getUserTable().searchLastName(((String)getMessage));
				outputStream.writeObject(myEnrolledStudents);
				outputStream.flush();
			}
			
			if(interpreter[1].equals("ALLSTUDENTS"))
			{
				Vector<Student> allStudents = myDatabase.getUserTable().allStudents();
				outputStream.writeObject(allStudents);
				outputStream.flush();
			}
			
			if(interpreter[1].equals("ALLENROLLED"))
			{
				Vector<Integer> enrolled = myDatabase.getStudentEnrollmentTable().getAllEnrolledStudent(((Course)getMessage).getId());
				Vector<Student> enrolledStudent = new Vector<Student>();
				for (int i = 0; i < enrolled.size(); i++)
				{
					enrolledStudent.add((Student) myDatabase.getUserTable().getUserByID(enrolled.get(i)));
				}		
				outputStream.writeObject(enrolledStudent);
				outputStream.flush();
			}
			
		}catch (IOException e) {
			System.out.println("Error");
		}
		
	}

	private void handleInsert(String[] interpreter, Object getmessageObject)
	{
		if(interpreter[1].equals("COURSE"))
		{
			myDatabase.getCourseTable().add( (Course) getmessageObject);
			System.out.println("Adding course " + ((Course)getmessageObject).getName() + " for Prof: " + user.getFirstName());
		}
		
		if(interpreter[1].equals("ENROLL"))
		{
			
			myDatabase.getStudentEnrollmentTable().add((StudentEnrollment)getmessageObject);
		}
		
		if(interpreter[1].equals("UNENROLL"))
		{
			myDatabase.getStudentEnrollmentTable().remove((StudentEnrollment)getmessageObject);
		}
		
		if(interpreter[1].equals("ASSIGNMENT"))
		{
			myDatabase.getAssignmentTable().add(((Assignment)getmessageObject));
			byte[] file;
			try
			{
				file = (byte[]) inputStream.readObject();
				myFileHelper.storeFile(file, ((Assignment)getmessageObject));
			} catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	
	
	
}
