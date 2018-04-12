package frontend.controller.listeners;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JLabel;

import frontend.controller.Client;
import frontend.interfaces.ColourPalette;
import shared.interfaces.UserCommands;
import shared.objects.Assignment;
import shared.objects.SendMessage;

public class AssignmentLabelMouseListener
		implements MouseListener, UserCommands, ColourPalette
{

	/**
	 * The submission associated with the listener
	 */
	private Assignment assignment;

	/**
	 * The client used to communicate with the server
	 */
	private Client client;

	/**
	 * The listener for submissions, used by the professor
	 * 
	 * @param submission
	 *            The submission
	 * @param client2
	 *            The client
	 */
	public AssignmentLabelMouseListener(Assignment assignment, Client client2)
	{
		this.assignment = assignment;
		client = client2;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		try
		{
			System.out.println("Make it here");
			byte[] file = (byte[]) client
					.sendMessage(new SendMessage<Assignment>(assignment,
							CMD_RECEIVE + RECEIVE_ASSIGNMENT));
			String home = System.getProperty("user.home");

			String[] fileName = assignment.getPath().split("/");
			File newFile = new File(
					home + "/Downloads/" + fileName[fileName.length - 1]);

			if (!newFile.exists())
				newFile.createNewFile();
			FileOutputStream writer = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(writer);
			bos.write(file);
			bos.close();

		} catch (IOException e1)
		{
			e1.getStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		JLabel label = (JLabel) e.getSource();
		label.setForeground(ACCENT_COLOR);
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		JLabel label = (JLabel) e.getSource();
		label.setForeground(Color.BLACK);
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

}
