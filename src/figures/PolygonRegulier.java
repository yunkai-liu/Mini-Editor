package figures;

import java.awt.BasicStroke;
import java.awt.Paint;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import figures.enums.FigureType;

/**
 * Classe de Figure pour les étoiles
 * @author yunkai-liu
 */
public class PolygonRegulier extends Figure
{
	/**
	 * Le compteur d'instance des polygons.
	 * Utilisé pour donner un numéro d'instance après l'avoir incrémenté
	 */
	private static int counter = 0;

	/**
	 * Le nombre des branches si on ne le met pas 5 par défaut
	 */
	private int n;
	
	/**
	 * Le centre du polygon
	 */
	private Point2D centre;
	
	/**
	 * le premier point du polygon
	 */
	private Point2D p;

	/**
	 * Constructeur valué d'un polygon
	 * 
	 * @param stroke le type du trait de la bordure
	 * @param edge la couleur de la bordure
	 * @param fill la couleur de remplissage
	 * @param center le centre du polygon
	 * @param p le premier point du polygon
	 */
	public PolygonRegulier(BasicStroke stroke, Paint edge, Paint fill, Point2D centre,
			Point p)
	{
		super(stroke, edge, fill);
		instanceNumber = ++counter;
		n = 5;
		this.centre = centre;
		this.p = p;
		
		int i;
		int x[]=new int[n];
		int y[]=new int[n];
		
		double rayon = centre.distance(p.getX(), p.getY());
		double angle = Math.atan2(p.getY(), p.getX());
		
		for(i=0; i<n; i++) 
		{
			x[i] = (int)(Math.cos(2*Math.PI*i/n + angle)*rayon + centre.getX());
			y[i] = (int)(Math.sin(2*Math.PI*i/n + angle)*rayon + centre.getY());
		}
		
		java.awt.Polygon poly = new java.awt.Polygon(x,y,n);
		
		shape = poly;
	}

	/**
	 * Déplacement du dernier point de la ligne (utilisé lors du dessin d'un
	 * étoile pour faire varier le centre et les points du polygon tant que l'on déplace un
	 * point)
	 * 
	 * @param p la nouvelle position du dernier point
	 * @see figures.Figure#setLastPoint(Point2D)
	 */
	@Override
	public void setLastPoint(Point2D p)
	{
		if (shape != null)
		{
			int i;
			int x[]=new int[n]; 
			int y[]=new int[n];
			
			double rayon = centre.distance(p);
			double angle =  Math.atan2(p.getY() - this.p.getY(), p.getX() - this.p.getX());;
			
			for(i=0; i<n; i++) 
			{
				x[i] = (int)(Math.cos(2*Math.PI*i/n + angle)*rayon + centre.getX());
				y[i] = (int)(Math.sin(2*Math.PI*i/n + angle)*rayon + centre.getY());
			}
			
			java.awt.Polygon poly = new java.awt.Polygon(x,y,n);
			
			shape = poly;
		}
	}

	/**
	 * Obtention du barycentre de la figure.
	 * @return le point correspondant au barycentre de la figure
	 */
	@Override
	public Point2D getCenter()
	{
		return new Point2D.Double(centre.getX(), centre.getY());
	}
	
 	/**
 	 * Accesseur du type de figure selon {@link FigureType}
 	 * @return le type de figure
 	 */
 	@Override
	public FigureType getType()
 	{
 		return FigureType.POLYGON_REGULIER;
 	}
 	
 	/**
	 * set les points du polygon
	 * @param n le nombre des branches
	 */
 	public void setN(int n)
	{
		this.n=n;
	}
 	
 	/**
 	 * Repaint la figure quand Move
 	 * @param p le point courant pour repaint la figure
 	 */
 	public void setLocation(Point2D p)
 	{
 		java.awt.Polygon figure = (java.awt.Polygon) shape;
 		Rectangle2D bounds = figure.getBounds2D();
 		double w = bounds.getWidth();
 		double h = bounds.getHeight();
 		figure.translate((int) (p.getX()-bounds.getX()-w/2), (int) (p.getY()-bounds.getY()-h/2));
 	}
}
