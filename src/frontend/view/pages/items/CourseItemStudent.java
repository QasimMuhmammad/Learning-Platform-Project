package frontend.view.pages.items;

import java.awt.GridLayout;

import javax.swing.JPanel;

import shared.objects.Course;

public class CourseItemStudent extends CourseItem
{
	private static final long serialVersionUID = 1L;

	public CourseItemStudent(Course course)
	{
		super(course);
		JPanel thePanel = new JPanel(new GridLayout(1, 3));
		thePanel.add(createLabel("Course Name"));
		thePanel.add(new JPanel());
		thePanel.add(createViewButton("View"));
		this.add(thePanel);
	}

}
