package frontend.view.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import frontend.interfaces.ColourPalette;
import frontend.interfaces.WondrisInfo;
import frontend.view.pages.components.BoxList;
import frontend.view.pages.components.customSwing.WButton;
import frontend.view.pages.items.CourseItem;

public class CoursePageTable extends JPanel
		implements WondrisInfo, ColourPalette, GUIConstants
{
	private static final long serialVersionUID = 1L;

	private static final String TABLE_TITLE = "My Courses";

	private static final String TABLE_CONTENTS = "Table Contents";

	private JPanel tablePanel;

	private JPanel titlePanel;

	private WButton newCourseButton;

	public CoursePageTable()
	{
		setLayout(new BorderLayout());
		add(createTitle(), BorderLayout.NORTH);
		add(createTable(), BorderLayout.CENTER);
	}

	private JPanel createTable()
	{
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		JPanel tableHeader = new JPanel();
		tableHeader.setLayout(new GridLayout(1, 3));
		tableHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
		tableHeader.setPreferredSize(new Dimension(WINDOW_WIDTH, 50));
		tableHeader.add(
				createLabel("Course Name", SUB_TITLE_FONT, JLabel.CENTER), 0);
		tableHeader.add(createLabel("Active", SUB_TITLE_FONT, JLabel.RIGHT), 1);
		tableHeader.add(
				createLabel("Course Home", SUB_TITLE_FONT, JLabel.CENTER), 2);
		tablePanel.add(tableHeader, BorderLayout.NORTH);
		return tablePanel;
	}

	private JPanel createTitle()
	{
		titlePanel = new JPanel(new GridLayout(1, 2));
		JPanel theTitle = new JPanel();
		JLabel title = new JLabel(TABLE_TITLE);
		title.setFont(TITLE_FONT);
		theTitle.setBackground(BACKGROUND_COLOUR);
		theTitle.add(title);
		titlePanel.add(theTitle, 0);
		titlePanel.add(createButton("New Course"), 1);
		return titlePanel;
	}

	private JPanel createButton(String label)
	{
		JPanel theButton = new JPanel();
		theButton.setBackground(BACKGROUND_COLOUR);
		newCourseButton = new WButton(label);
		theButton.add(newCourseButton);
		return theButton;

	}

	private JPanel createLabel(String title, Font font, int alignment)
	{
		JPanel theLabel = new JPanel(new BorderLayout());
		JLabel label = new JLabel(title);
		label.setPreferredSize(new Dimension(200, 30));
		label.setHorizontalAlignment(alignment);
		label.setFont(font);
		theLabel.add(label, BorderLayout.NORTH);
		return theLabel;
	}

	public void setBoxList(BoxList<CourseItem> itemDisplay)
	{
		JScrollPane scrollPane = new JScrollPane(itemDisplay);
		scrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		tablePanel.add(scrollPane, BorderLayout.CENTER);
	}

	public void setNewCourseListener(ActionListener listener)
	{
		newCourseButton.addActionListener(listener);
	}

}
