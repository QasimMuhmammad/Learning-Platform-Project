package frontend.view.pages;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JPanel;

import sharedobjects.Course;

public class CoursePage<T extends Box, U> extends Page<T, U>
{

	private static final long serialVersionUID = 1L;

	protected Course course;

	public CoursePage(Course course)
	{
		super();
		
		this.course = course;
		this.setName(COURSE_PAGE + course.getId());
		this.header.setTitle(course.getName());		

		CourseNavigationBar bar = new CourseNavigationBar();
		body = new JPanel();
		body.setLayout(new BorderLayout());
		body.add(bar, BorderLayout.EAST);
		add(body);
	}

	@Override
	public void displayPage()
	{
		// TODO Auto-generated method stub

	}
}
