package frontend.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import frontend.Colours;
import frontend.ServerInfo;
import frontend.WondrisInfo;
import sharedobjects.LoginInfo;



public class LoginGUI extends JFrame implements ServerInfo, WondrisInfo, Colours
{

	private static final long serialVersionUID = 1L;
	private JTextField userName;
	private JTextField password;
	private JButton enterCredentials;

	public LoginGUI(String s)
	{
		super(s);
		setSize(1600, 1000);
		createFields();

		JPanel loginPanel = new JPanel();
		GridLayout loginPanelLayout = new GridLayout(1, 1);
		loginPanelLayout.setVgap(0);
		loginPanelLayout.setHgap(0);
		loginPanel.setLayout(loginPanelLayout);
		loginPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(200, 300, 200, 300),
				new MatteBorder(10, 10, 10, 10, Color.DARK_GRAY)));
		loginPanel.add(createLoginPanel(NAME));
		loginPanel.setBackground(TERTIARY_COLOR);
		add(loginPanel);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setupLogin();
	}

	private void setupLogin()
	{
		enterCredentials.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					Socket mySocket = new Socket("localhost", 8991);
					LoginInfo userInfo = new LoginInfo(Integer.parseInt(userName.getText()), password.getText());
					

					
				}catch(NumberFormatException e2) {
					System.out.println("Username must only be numbers");
				}
				
				catch (UnknownHostException e1)
				{
					e1.printStackTrace();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
				
				
			}

		});
	}


	private void createFields()
	{
		userName = new JTextField(20);
		password = new JTextField(20);
		enterCredentials = new JButton("Sign In");
		enterCredentials.setFont(TEXTFONT);
	}

	private JPanel createLoginPanel(String s)
	{
		JPanel thePanel = new JPanel();
		thePanel.setLayout(new GridLayout(1, 2));
		thePanel.setBackground(ACCENT_COLOR);
		// thePanel.setPreferredSize(new Dimension(757, 500));
		thePanel.add(createTitle(s));
		thePanel.add(createTextPanel());
		return thePanel;
	}

	private JPanel createTextPanel()
	{
		GridLayout panelLayout = new GridLayout(3, 1);
		panelLayout.setVgap(0);
		JPanel thePanel = new JPanel(panelLayout);
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(3, 1));
		textPanel.add(createTextField("Username", userName));
		textPanel.add(createTextField("Password", password));
		textPanel.add(createButtonPanel());
		thePanel.add(createEmptyPanel(ACCENT_COLOR));
		thePanel.add(textPanel);
		thePanel.add(createEmptyPanel(ACCENT_COLOR));
		return thePanel;
	}

	private JPanel createTitle(String s)
	{
		JPanel titlePanel = new JPanel(new GridLayout(1, 1));
		titlePanel.setBackground(BACKGROUND_COLOUR);
		JLabel title = new JLabel(s);
		title.setFont(TITLE_FONT);
		title.setForeground(Color.WHITE);
		titlePanel.add(title);
		return titlePanel;
	}

	private JPanel createTextField(String s, JTextField field)
	{
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setBackground(ACCENT_COLOR);
		JLabel text = new JLabel(s);
		text.setFont(TEXTFONT);
		text.setForeground(Color.WHITE);
		textFieldPanel.add(text);
		textFieldPanel.add(field);
		return textFieldPanel;
	}

	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(ACCENT_COLOR);
		buttonPanel.setPreferredSize(new Dimension(50, 50));
		buttonPanel.add(enterCredentials);
		return buttonPanel;
	}

	private JPanel createEmptyPanel(Color c)
	{
		FlowLayout panelLayout = new FlowLayout();
		panelLayout.setVgap(0);
		JPanel panel = new JPanel(panelLayout);
		panel.setBackground(c);
		return panel;
	}

	public static void main(String[] args)
	{
		new LoginGUI(TITLE);
	}
}