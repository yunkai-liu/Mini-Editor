/**
 * 
 */
package filters;

import java.awt.Paint;

import figures.Figure;

/**
 * Filtre filtrant les figures possédant une certaine couleur de remplissage
 * @author yunkai-liu
 */
public class FillColorFilter extends FigureFilter<Paint>
{
	/**
	 * Test du prédicat
	 * @return true si la figure courante possède la même couleur de remplissage
	 */
	public boolean test(Figure f)
	{
		if(f.getFillPaint()!=null)
		{
			if(this.element==null)
				return false;
			else
			{
				if(this.element.equals(f.getFillPaint()))
					return true;
				else
					return false;
			}
		}
		else
		{
			if(this.element==null)
				return true;
			else
				return false;
		}
	}
	
	/**
	 * Constructeur d'un {@link FillColorFilter}
	 * @param paint la couleur à filtrer
	 */
	public FillColorFilter(Paint paint)
	{
		super(paint);
	}
}