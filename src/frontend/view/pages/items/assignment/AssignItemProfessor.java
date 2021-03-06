package frontend.view.pages.items.assignment;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


import frontend.view.pages.components.customSwing.WButton;
import frontend.view.pages.components.customSwing.WButtonActivatable;
import shared.objects.Assignment;

public final class AssignItemProfessor extends AssignItem
{

	private static final long serialVersionUID = 1L;

	private WButtonActivatable activeButton;

	public AssignItemProfessor(Assignment assignment)
	{
		super(assignment);
	}

	private JPanel createActivateButton()
	{
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		activeButton = new WButtonActivatable(assignment.getActive());

		activeButton.setPreferredSize(new Dimension(200, 40));

		buttonPanel.add(activeButton, new GridBagConstraints());
		return buttonPanel;
	}

	public WButton getActiveButton()
	{
		return activeButton;
	}

	@Override
	protected JPanel createTheAssignment()
	{
		JPanel theAssignment = new JPanel(new GridLayout(1, 3));
		theAssignment.setBorder(BorderFactory.createEtchedBorder());
		theAssignment.setPreferredSize(new Dimension(500, 50));
		theAssignment.add(assignmentName, 0);
		theAssignment.add(createActivateButton(), 1);
		theAssignment.add(dueDate, 2);
		return theAssignment;
	}
	
}
