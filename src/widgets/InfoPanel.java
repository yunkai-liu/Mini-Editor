package widgets;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Paint;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import figures.Figure;
import figures.enums.FigureType;
import figures.enums.LineType;
import utils.IconFactory;
import utils.PaintFactory;

/**
 * InfoPanel
 * @author yunkai-liu 
 *
 */
public class InfoPanel extends JPanel
{
	/**
	 * Une chaine vide pour remplir les champs lorsque la souris n'est au dessus
	 * d'aucune figure
	 */
	private static final String emptyString = new String();

	/**
	 * Une icône vide pour remplir les chanmps avec icône lorsque la souris
	 * n'est au dessus d'aucune figure
	 */
	private static final ImageIcon emptyIcon = IconFactory.getIcon("None");

	/**
	 * Le formatteur à utiliser pour formater les coordonnés
	 */
	private final static DecimalFormat coordFormat = new DecimalFormat("000");

	/**
	 * Le label contenant le nom de la figure
	 */
	private JLabel lblFigureName;
	
	/**
	 * Le label contenant l'icône correspondant à la figure
	 */
	private JLabel lblTypeicon;
	
	/**
	 * La map contenant les différentes icônes des types de figures
	 */
	private Map<FigureType, ImageIcon> figureIcons;
	
	/**
	 * Le label contenant l'icône de la couleur de remplissage
	 */
	private JLabel lblFillcolor;
	
	/**
	 * Le label contenant l'icône de la couleur du contour
	 */
	private JLabel lblEdgecolor;
	
	/**
	 * Map contenant les icônes relatives aux différentes couleurs (de contour
	 * ou de remplissage)
	 */
	private Map<Paint, ImageIcon> paintIcons;
	
	/**
	 * Le label contenant le type de contour
	 */
	private JLabel lblStroketype;
	
	/**
	 * Map contenant les icônes relatives au différents types de traits de 
	 * contour
	 */
	private Map<LineType, ImageIcon> lineTypeIcons;
	
	/**
	 * Le label contenant l'abcisse du point en haut à gauche de la figure
	 */
	private JLabel lblTlx;
	
	/**
	 * Le label contenant l'ordonnée du point en haut à gauche de la figure
	 */
	private JLabel lblTly;

	/**
	 * Le label contenant l'abcisse du point en bas à droite de la figure
	 */
	private JLabel lblBrx;

	/**
	 * Le label contenant l'ordonnée du point en bas à droite de la figure
	 */
	private JLabel lblBry;

	/**
	 * Le label contenant la largeur de la figure
	 */
	private JLabel lblDx;

	/**
	 * Le label contenant la hauteur de la figure
	 */
	private JLabel lblDy;

	/**
	 * Le label contenant l'abcisse du barycentre de la figure
	 */
	private JLabel lblCx;

	/**
	 * Le label contenant l'ordonnée du barycentre de la figure
	 */
	private JLabel lblCy;
	
	/**
	 * Create the panel.
	 */
	public InfoPanel()
	{
		// --------------------------------------------------------------------
		// Initialisation des maps
		// --------------------------------------------------------------------
		figureIcons = new HashMap<FigureType, ImageIcon>();
	
		figureIcons.put(FigureType.CIRCLE, IconFactory.getIcon("Circle"));
		figureIcons.put(FigureType.ELLIPSE, IconFactory.getIcon("Ellipse"));
		figureIcons.put(FigureType.RECTANGLE, IconFactory.getIcon("Rectangle"));
		figureIcons.put(FigureType.ROUNDED_RECTANGLE, IconFactory.getIcon("Rounded Rectangle"));
		figureIcons.put(FigureType.POLYGON, IconFactory.getIcon("Polygon"));
		figureIcons.put(FigureType.POLYGON_REGULIER, IconFactory.getIcon("Regular Polygon"));
		figureIcons.put(FigureType.ETOILE, IconFactory.getIcon("Star"));
		figureIcons.put(FigureType.NONE, null);

		paintIcons = new HashMap<Paint, ImageIcon>();
		String[] colorStrings = {
			"Black",
			"Blue",
			"Cyan",
			"Green",
			"Magenta",
			"None",
			"Orange",
			"Others",
			"Red",
			"White",
			"Yellow"
		};
		
		int i;
		for(i=0;i<colorStrings.length;i++)
		{
			Paint paint = PaintFactory.getPaint(colorStrings[i]);
			if(paint!=null)
				paintIcons.put(paint, IconFactory.getIcon(colorStrings[i]));
		}
		
		lineTypeIcons = new HashMap<LineType, ImageIcon>();
		
		lineTypeIcons.put(LineType.SOLID, IconFactory.getIcon("Solid"));
		lineTypeIcons.put(LineType.DASHED, IconFactory.getIcon("Dashed"));
		lineTypeIcons.put(LineType.NONE, IconFactory.getIcon("None"));
		
		// --------------------------------------------------------------------
		// Création de l'UI
		// --------------------------------------------------------------------
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagConstraints gbc=new GridBagConstraints();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {80, 110, 60};
		gridBagLayout.rowHeights = new int[] {42, 44, 44, 44, 32, 32, 32, 32, 32};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		setLayout(gridBagLayout);

		lblFigureName=new JLabel("Ellipse 1");
		gbc.gridx=1;
		gbc.gridy=0;
		this.add(lblFigureName,gbc);
		JLabel j_type=new JLabel("type");
		gbc.gridx=0;
		gbc.gridy=1;
		this.add(j_type,gbc);
		lblTypeicon=new JLabel();
		lblTypeicon.setIcon(IconFactory.getIcon("Ellipse"));
		gbc.gridx=2;
		gbc.gridy=1;
		this.add(lblTypeicon,gbc);
		JLabel j_fill=new JLabel("fill");
		gbc.gridx=0;
		gbc.gridy=2;
		this.add(j_fill,gbc);
		lblFillcolor=new JLabel();
		lblFillcolor.setIcon(IconFactory.getIcon("Black"));
		gbc.gridx=2;
		gbc.gridy=2;
		this.add(lblFillcolor,gbc);
		JLabel j_stroke=new JLabel("stroke");
		gbc.gridx=0;
		gbc.gridy=3;
		this.add(j_stroke,gbc);
		lblEdgecolor=new JLabel();
		lblEdgecolor.setIcon(IconFactory.getIcon("Orange"));
		gbc.gridx=1;
		gbc.gridy=3;
		this.add(lblEdgecolor,gbc);
		lblStroketype=new JLabel();
		lblStroketype.setIcon(IconFactory.getIcon("Dashed"));
		gbc.gridx=2;
		gbc.gridy=3;
		this.add(lblStroketype,gbc);
		JLabel j_x=new JLabel("x");
		gbc.gridx=1;
		gbc.gridy=4;
		this.add(j_x,gbc);
		JLabel j_y=new JLabel("y");
		gbc.gridx=2;
		gbc.gridy=4;
		this.add(j_y,gbc);
		JLabel j_top_left=new JLabel("top left");
		gbc.gridx=0;
		gbc.gridy=5;
		this.add(j_top_left,gbc);
		lblTlx=new JLabel("399");
		gbc.gridx=1;
		gbc.gridy=5;
		this.add(lblTlx,gbc);
		lblTly=new JLabel("054");
		gbc.gridx=2;
		gbc.gridy=5;
		this.add(lblTly,gbc);
		JLabel j_bottom_right=new JLabel("bottom right");
		gbc.gridx=0;
		gbc.gridy=6;
		this.add(j_bottom_right,gbc);
		lblBrx=new JLabel("594");
		gbc.gridx=1;
		gbc.gridy=6;
		this.add(lblBrx,gbc);
		lblBry=new JLabel("384");
		gbc.gridx=2;
		gbc.gridy=6;
		this.add(lblBry,gbc);
		JLabel j_dimensions=new JLabel("dimensions");
		gbc.gridx=0;
		gbc.gridy=7;
		this.add(j_dimensions,gbc);
		lblDx=new JLabel("195");
		gbc.gridx=1;
		gbc.gridy=7;
		this.add(lblDx,gbc);
		lblDy=new JLabel("320");
		gbc.gridx=2;
		gbc.gridy=7;
		this.add(lblDy,gbc);
		JLabel j_center=new JLabel("center");
		gbc.gridx=0;
		gbc.gridy=8;
		this.add(j_center,gbc);
		lblCx=new JLabel("496");
		gbc.gridx=1;
		gbc.gridy=8;
		this.add(lblCx,gbc);
		lblCy=new JLabel("224");
		gbc.gridx=2;
		gbc.gridy=8;
		this.add(lblCy,gbc);
	}

	public void updateLabels(Figure figure)
	{
		lblFigureName.setText(figure.getName());
		lblTypeicon.setIcon(figureIcons.get(figure.getType()));
		if(figure.getFillPaint()==null)
			lblFillcolor.setIcon(emptyIcon);
		else if(!paintIcons.containsKey(figure.getFillPaint()))
			lblFillcolor.setIcon(IconFactory.getIcon("Others"));
		else
			lblFillcolor.setIcon(paintIcons.get(figure.getFillPaint()));
		if(!paintIcons.containsKey(figure.getEdgePaint()))
			lblEdgecolor.setIcon(IconFactory.getIcon("Others"));
		else
			lblEdgecolor.setIcon(paintIcons.get(figure.getEdgePaint()));
		if(figure.getStroke()!=null)
		{
			if(figure.getStroke().getDashArray()!=null)
			{
				lblStroketype.setIcon(lineTypeIcons.get(LineType.DASHED));
			}
			else
			{
				lblStroketype.setIcon(lineTypeIcons.get(LineType.SOLID));
			}
		}
		else
			lblStroketype.setIcon(emptyIcon);
		
		lblTlx.setText(coordFormat.format(figure.getBounds2D().getX()));
		lblTly.setText(coordFormat.format(figure.getBounds2D().getY()));
		lblBrx.setText(coordFormat.format(figure.getBounds2D().getX()+figure.getBounds2D().getWidth()));
		lblBry.setText(coordFormat.format(figure.getBounds2D().getY()+figure.getBounds2D().getHeight()));
		lblDx.setText(coordFormat.format(figure.getBounds2D().getWidth()));
		lblDy.setText(coordFormat.format(figure.getBounds2D().getHeight()));
		lblCx.setText(coordFormat.format(figure.getCenter().getX()));
		lblCy.setText(coordFormat.format(figure.getCenter().getY()));
	}

	/**
	 * Effacement de tous les labels	 
	 */
	public void resetLabels()
	{
		lblFigureName.setText(emptyString);
		lblTypeicon.setIcon(emptyIcon);
		lblFillcolor.setIcon(emptyIcon);
		lblEdgecolor.setIcon(emptyIcon);
		lblStroketype.setIcon(emptyIcon);

		lblTlx.setText(emptyString);
		lblTly.setText(emptyString);
		lblBrx.setText(emptyString);
		lblBry.setText(emptyString);
		lblDx.setText(emptyString);
		lblDy.setText(emptyString);
		lblCx.setText(emptyString);
		lblCy.setText(emptyString);
	}
}
