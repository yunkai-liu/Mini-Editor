package filters;

import figures.Figure;
import figures.enums.FigureType;

/**
 * Filtre de figure appliqué au type de figure
 * @author yunkai-liu
 */
public class ShapeFilter extends FigureFilter<FigureType>
{
	/**
	 * Test du prédicat
	 * @param f la figure à tester
	 * @return vrai si la figure f est du type contenu dans element
	 * @see java.util.function.Predicate#test(java.lang.Object)
	 */
	public boolean test(Figure f)
	{
		if(this.element.equals(f.getType()))
			return true;
		else
			return false;
	}
	
	/**
	 * Constructeur d'un {@link ShapeFilter} à partir d'un type de figure
	 * @param element le type de figure à tester par ce prédicat
	 */
	public ShapeFilter(FigureType type)
	{
		super(type);
	}
}