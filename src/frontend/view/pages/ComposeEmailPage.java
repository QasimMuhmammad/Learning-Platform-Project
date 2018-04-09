package frontend.view.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import frontend.view.pages.items.StudentItem;
import shared.objects.Course;
import shared.objects.Student;

public class ComposeEmailPage extends CoursePage<StudentItem, Student>
{
	private static final long serialVersionUID = 1L;
	private JTextField toField, subjectField;
	private JTextArea emailArea;
	private JButton sendToAllButton, sendButton, addToEmailButton;
	private JList<Student> studentList;

	public JTextField getToField()
	{
		return toField;
	}

	public JTextField getSubjectField()
	{
		return subjectField;
	}

	public JTextArea getEmailArea()
	{
		return emailArea;
	}

	public JButton getSendToAllButton()
	{
		return sendToAllButton;
	}

	public JButton getSendButton()
	{
		return sendButton;
	}

	public JButton getAddToEmailButton()
	{
		return addToEmailButton;
	}

	public JList<Student> getStudentList()
	{
		return studentList;
	}
	
	public void setSendToAllButtonListener(ActionListener listener)
	{
		sendToAllButton.addActionListener(listener);
	}
	public void setSendButtonListener(ActionListener listener)
	{
		sendButton.addActionListener(listener);
	}
	public void setAddToEmailButtonListener(ActionListener listener)
	{
		addToEmailButton.addActionListener(listener);
	}

	public ComposeEmailPage(Course course)
	{
		super(course);
		this.setName(COMPOSE_EMAIL_PAGE + course.getId());
		this.setPageTitle("Compose Email");
		bodyCenter.add(createComposeEmailPanel(), BorderLayout.CENTER);
	}

	private JPanel createComposeEmailPanel()
	{
		JPanel composeEmailPanel = new JPanel(new BorderLayout());
		composeEmailPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		composeEmailPanel.add(createEmailComponents(), BorderLayout.CENTER);
		return composeEmailPanel;
	}
	
	private JPanel createEmailComponents()
	{
		JPanel emailComponentPanel = new JPanel(new BorderLayout());
		emailComponentPanel.add(createEmailFields(), BorderLayout.NORTH);
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(createEmailTextArea(), BorderLayout.CENTER);
		centerPanel.add(createStudentList(), BorderLayout.EAST);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		emailComponentPanel.add(centerPanel, BorderLayout.CENTER);
		return emailComponentPanel;
	}
	
	private JPanel createEmailFields()
	{
		JPanel emailFieldPanel = new JPanel(new BorderLayout());
		JPanel textFields = new JPanel(new GridLayout(2, 1));
		toField = new JTextField(20);
		subjectField = new JTextField(20);
		toField.setFont(TEXT_FONT);
		subjectField.setFont(TEXT_FONT);
		textFields.add(createTextField("To: ", toField));
		textFields.add(createTextField("Subject: ", subjectField));
		emailFieldPanel.add(textFields, BorderLayout.WEST);
		return emailFieldPanel;
	}
	
	private JPanel createTextField(String s, JTextField field)
	{
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
		textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.X_AXIS));
		textFieldPanel.add(createLabel(s, SUB_TITLE_FONT));
		textFieldPanel.add(field);
		return textFieldPanel;
	}
	
	private JPanel createEmailTextArea()
	{
		JPanel emailTextPanel = new JPanel(new BorderLayout());
		emailArea = new JTextArea();
		emailArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		emailArea.setFont(TEXT_FONT);
		emailTextPanel.add(emailArea, BorderLayout.CENTER);
		emailTextPanel.add(createButtonPanel(), BorderLayout.SOUTH);
		return emailTextPanel;
	}
	
	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		sendToAllButton = new JButton();
		sendButton = new JButton();
		buttonPanel.add(createButton(sendToAllButton, "Send to Enrolled Students", TEXT_FONT), BorderLayout.WEST);
		buttonPanel.add(createButton(sendButton, "Send", TEXT_FONT), BorderLayout.EAST);
		return buttonPanel;
	}
	
	
	private JPanel createStudentList()
	{
		JPanel studentListPanel = new JPanel(new BorderLayout());
		studentListPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		studentList = new JList<Student>();
		JScrollPane scrollPane = new JScrollPane(studentList);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		studentListPanel.add(scrollPane, BorderLayout.CENTER);
		JPanel theButton = new JPanel(new BorderLayout());
		theButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		addToEmailButton = new JButton();
		theButton.add(createButton(addToEmailButton, "Add to Email", TEXT_FONT), BorderLayout.EAST);
		studentListPanel.add(theButton, BorderLayout.SOUTH);
		return studentListPanel;
	}
}