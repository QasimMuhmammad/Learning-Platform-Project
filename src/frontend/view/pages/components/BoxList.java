package frontend.view.pages.components;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * 
 * @author Trevor Le (30028725), Qasim Muhammad (30016415), Jimmy Truong
 *         (30017293)
 * @version 1.0
 * @since April 6, 2018
 */
public class BoxList<T extends Box> extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BoxList()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	@SuppressWarnings("unchecked")
	public T findElement(String name)
	{
		for (Component component : this.getComponents())
		{
			if (component.getName() == name)
			{
				return (T) component;
			}
		}
		return null;
	}

	// public T elementAt(int i)
	// {
	// return this.elementAt(i);
	// }

	public void addItem(T boxItem)
	{
		this.add(boxItem);
	}

	public void setItems(ArrayList<T> boxItems)
	{
		for (T item : boxItems)
		{
			this.add(item);
		}
	}
}
