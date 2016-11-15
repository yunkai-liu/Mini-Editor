/**
 * 
 */
package filters;

import java.awt.Paint;

import figures.Figure;

/**
 * Filtre filtrant les figures possédant une certaine couleur de trait
 * @author yunkai-liu
 */
public class EdgeColorFilter extends FigureFilter<Paint>
{
	/**
	 * Test du prédicat
	 * @return true si la figure courante possède la même couleur de trait
	 */
	public boolean test(Figure f)
	{
		if(this.element.equals(f.getEdgePaint()))
			return true;
		else
			return false;
	}
	
	/**
	 * Constructeur d'un {@link EdgeColorFilter}
	 * @param paint la couleur à filtrer
	 */
	public EdgeColorFilter(Paint paint)
	{
		super(paint);
	}
}
