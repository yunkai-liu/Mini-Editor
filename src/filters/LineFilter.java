package filters;

import figures.Figure;
import figures.enums.LineType;

/**
 * Filtre filtrant les figures ayant un certain type de trait
 * @author yunkai-liu
 */
public class LineFilter extends FigureFilter<LineType>
{
	/**
	 * Test du prédicat
	 * @return true si la figure courante possède le même type de trait
	 */
	public boolean test(Figure f)
	{
		if(f.getStroke()!=null)
		{
			if(f.getStroke().getDashArray()!=null)
			{
				if(this.element.equals(LineType.DASHED))
					return true;
				else
					return false;
			}
			else 
			{
				if(this.element.equals(LineType.SOLID))
					return true;
				else
					return false;
			}
		}
		else
		{
			if(this.element.equals(LineType.NONE))
				return true;
			else
				return false;
		}
	}
	
	/**
	 * Constructeur d'un {@link LineFilter}
	 * @param type le type de ligne à filtrer
	 */
	public LineFilter(LineType type)
	{
		super(type);
	}
}
