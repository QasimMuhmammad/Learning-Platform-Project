package frontend.view.pages.items.assignment;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import frontend.interfaces.WondrisInfo;
import frontend.view.pages.components.customSwing.WLabel;
import frontend.view.pages.interfaces.GUIConstants;
import frontend.view.pages.items.GeneralItem;
import shared.objects.Assignment;

/**
 * 
 * @author Trevor Le (30028725), Qasim Muhammad (30016415), Jimmy Truong
 *         (30017293)
 * @version 1.0
 * @since April 6, 2018
 */
abstract public class AssignItem extends GeneralItem implements WondrisInfo, GUIConstants
{

	private static final long serialVersionUID = 1L;

	protected Assignment assignment;

	protected WLabel assignmentName;

	protected WLabel dueDate;

	public AssignItem(Assignment assignment)
	{
		super(BoxLayout.X_AXIS, Integer.toString(assignment.getId()));
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
		this.assignment = assignment;
		this.assignmentName = new WLabel(assignment.getTitle());
		setUnderline();
		this.dueDate = new WLabel(assignment.getDueDate());

		this.setBorder(BorderFactory.createEtchedBorder());
		this.add(createTheAssignment());
	}
	
	private void setUnderline()
	{
		Font font = assignmentName.getFont();
		Map attributes = (Map) font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		assignmentName.setFont(font.deriveFont(attributes));
	}

	public WLabel getAssignmentLabel()
	{
		return assignmentName;
	}

	abstract protected JPanel createTheAssignment();

	@Override
	public int getId()
	{
		return assignment.getId();
	}
}
