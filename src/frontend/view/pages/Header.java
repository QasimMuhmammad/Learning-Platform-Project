package frontend.view.pages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import frontend.interfaces.Colours;
import frontend.interfaces.WondrisInfo;

public class Header extends JPanel implements GUIConstants, Colours, WondrisInfo
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private NavigationBar navigationBar;

	public Header()
	{
		this.setLayout(new GridLayout(1, 3));
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, 100));
		this.setBackground(ACCENT_COLOR);
		navigationBar = new NavigationBar();
		this.add(createTitle(NAME), 0);
		this.add(createTitle(""), 1);
		this.add(navigationBar, 2);
	}
	
	public JLabel createTitle(String title) {
		JLabel theTitle = new JLabel(title);
		theTitle.setFont(TITLE_FONT);
		theTitle.setForeground(Color.WHITE);
		return theTitle;
	}
	
	public void setTitle(String title) {
		this.remove(1);
		this.add(createTitle(title), 1);
	}
	
	public void setBackButtonListener(ActionListener listener)
	{
		navigationBar.setBackButtonListener(listener);
	}
	
	public void setHomeButtonListener(ActionListener listener)
	{
		navigationBar.setHomeButtonListener(listener);
	}
}
