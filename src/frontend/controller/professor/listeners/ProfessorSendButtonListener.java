package frontend.controller.professor.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import frontend.controller.Client;
import frontend.view.pages.ComposeEmailPage;
import shared.interfaces.ProfessorCommands;
import shared.interfaces.StudentCommands;
import shared.objects.Course;
import shared.objects.EmailInfo;
import shared.objects.SendMessage;

/**
 * 
 * @author Trevor Le (30028725), Qasim Muhammad (30016415), Jimmy Truong
 *         (30017293)
 * @version 1.0
 * @since April 6, 2018
 * Sends an email
 */
public class ProfessorSendButtonListener implements ActionListener, ProfessorCommands
{
	/**
	 * The course this is associated
	 */
	private Course course;

	/**
	 * The client which is used for communication
	 */
	private Client client;

	/**
	 * The composed email page which holds this button
	 */
	private ComposeEmailPage emailPage;

	/** The constructor for this 
	 * @param client Used for communication
	 * @param course The course this is used for
	 * @param email The emailpage which holds the email information
	 */
	public ProfessorSendButtonListener(Client client, Course course,
			ComposeEmailPage email)
	{
		this.client = client;
		this.course = course;
		emailPage = email;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		EmailInfo mailInfo = emailPage.getEmailInfo();
		JTextField gEmail = new JTextField(50);
		JTextField password = new JTextField(50);
		Object toDisplay[] = {"GMail Email: ", gEmail, "GMail Password: ", password};
		int response =JOptionPane.showConfirmDialog(null, toDisplay, "Insert real email information", JOptionPane.OK_CANCEL_OPTION);
		
		if( response == JOptionPane.OK_OPTION)
		{
			if(gEmail.getText().length() == 0 || password.getText().length() == 0)
			{
				JOptionPane.showMessageDialog(null,"Either Password or Email was incoorect");
			}
			
			else {
			mailInfo.setFromEmail(gEmail.getText());
			mailInfo.setFromEmailPassword(password.getText());
			try
			{
				Boolean h = (Boolean) client.sendMessage(new <EmailInfo>SendMessage(mailInfo, CMD_EMAIL));
				if(h == true)
				{
					JOptionPane.showMessageDialog(null,"Email Sent");
				}
				else 
				{
					JOptionPane.showMessageDialog(null,"Email not sent, error in login info or email addresses");
				}
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			
			}
		}
	
		
	}
}