package frontend.view.pages.items;

import javax.swing.Box;

import frontend.interfaces.ColourPalette;
import frontend.interfaces.WondrisInfo;

/**
 * 
 * @author Trevor Le (30028725), Qasim Muhammad (30016415), Jimmy Truong
 *         (30017293)
 * @version 1.0
 * @since April 6, 2018
 */
public abstract class GeneralItem extends Box
		implements WondrisInfo, ColourPalette
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GeneralItem(int axis, String name)
	{
		super(axis);

		this.setName(name);
	}

	public abstract int getId();

}
