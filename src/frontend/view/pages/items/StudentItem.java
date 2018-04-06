package frontend.view.pages.items;

import javax.swing.BoxLayout;

import sharedobjects.Student;

public class StudentItem extends GeneralItem
{
	private static final long serialVersionUID = 1L;
	private Student student;

	public StudentItem(Student student)
	{
		super(BoxLayout.X_AXIS, Integer.toString(student.getId()));
		this.student = student;
	}

	@Override
	public int getId()
	{
		return student.getId();
	}

}
