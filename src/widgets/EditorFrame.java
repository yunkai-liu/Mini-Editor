package widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import figures.Drawing;
import figures.creationListeners.AbstractCreationListener;
import figures.creationListeners.MoveShapeListener;
import figures.enums.FigureType;
import figures.enums.LineType;
import figures.enums.PaintToType;
import filters.LineFilter;
import filters.ShapeFilter;
import utils.IconFactory;
import utils.IconItem;
import utils.PaintFactory;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JToolBar;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollPane;

/**
 * Classe de la fenêtre principale de l'éditeur de figures
 * @author yunkai-liu
 */
public class EditorFrame extends JFrame
{
	/**
	 * Le nom de l'éditeur
	 */
	protected static final String EditorName = "Figure Editor v3.0";
	
	/**
	 * Le modèle de dessin sous-jacent;
	 */
	protected Drawing drawingModel;

	protected DrawingPanel drawingPanel;

	protected AbstractCreationListener creationListener;

	/**
	 * Le moveListener pour réaliser la déplacement de la figure 
	 */
	protected MoveShapeListener moveListener;
	
	protected JLabel infoLabel;

	private final static int defaultFigureTypeIndex = 0;

	/**
	 * Les noms des couleurs de remplissage à utiliser pour remplir
	 * la [labeled]combobox des couleurs de remplissage
	 */
	protected final static String[] fillColorNames = {
		"Black",
		"White",
		"Red",
		"Orange",
		"Yellow",
		"Green",
		"Cyan",
		"Blue",
		"Magenta",
		"Others",
		"None"
	};

	protected final static Paint[] fillPaints = {
		Color.black,
		Color.white,
		Color.red,
		Color.orange,
		Color.yellow,
		Color.green,
		Color.cyan,
		Color.blue,
		Color.magenta,
		null, // Color selected by a JColorChooser
		null // No Color
	};

	private final static int defaultFillColorIndex = 0; // black

	/**
	 * L'index de la couleur de remplissage à choisir avec un
	 * {@link JColorChooser} fournit par la {@link PaintFactory}
	 */
	private final static int specialFillColorIndex = 9;

	/**
	 * Les noms des couleurs de trait à utiliser pour remplir
	 * la [labeled]combobox des couleurs de trait
	 */
	protected final static String[] edgeColorNames = {
		"Magenta",
		"Red",
		"Orange",
		"Yellow",
		"Green",
		"Cyan",
		"Blue",
		"Black",
		"Others"
	};

	protected final static Paint[] edgePaints = {
		Color.magenta,
		Color.red,
		Color.orange,
		Color.yellow,
		Color.green,
		Color.cyan,
		Color.blue,
		Color.black,
		null // Color selected by a JColorChooser
	};

	private final static int defaultEdgeColorIndex = 6; // blue;

	/**
	 * L'index de la couleur de remplissage à choisir avec un
	 * {@link JColorChooser} fournit par la {@link PaintFactory}
	 */
	private final static int specialEdgeColorIndex = 8;

	private final static int defaultEdgeTypeIndex = 1; // solid

	/**
	 * La largeur de trait par défaut
	 */
	private final static int defaultEdgeWidth = 4;

	/**
	 * Largeur de trait minimum
	 */
	private final static int minEdgeWidth = 1;

	/**
	 * Largeur de trait maximum
	 */
	private final static int maxEdgeWidth = 30;

	/**
	 * l'incrément entre deux largeurs de trait
	 */
	private final static int stepEdgeWidth = 1;
	
	/**
	 * Action quand changer le mode dans la classe drawing
	 */
	private final Action chModeAction = new ChangeModeAction();
	
	private final Action quitAction = new QuitAction();

	private final Action undoAction = new UndoAction();

	private final Action clearAction = new ClearAction();

	private final Action aboutAction = new AboutAction();
	
	/**
	 * Action quand activer le filtrage dans la classe drawing
	 */
	private final Action filteringAction=new FilteringAction();
		
	private final Action circleFilterAction = new ShapeFilterAction(FigureType.CIRCLE);

	private final Action ellipseFilterAction = new ShapeFilterAction(FigureType.ELLIPSE);

	private final Action rectangleFilterAction = new ShapeFilterAction(FigureType.RECTANGLE);

	private final Action rRectangleFilterAction = new ShapeFilterAction(FigureType.ROUNDED_RECTANGLE);

	private final Action polyFilterAction = new ShapeFilterAction(FigureType.POLYGON);
	
	private final Action rpolyFilterAction = new ShapeFilterAction(FigureType.POLYGON_REGULIER);

	private final Action etoileFilterAction = new ShapeFilterAction(FigureType.ETOILE);

	private final Action noneLineFilterAction = new LineFilterAction(LineType.NONE);

	private final Action solidLineFilterAction = new LineFilterAction(LineType.SOLID);

	private final Action dashedLineFilterAction = new LineFilterAction(LineType.DASHED);

	/**
	 * Constructeur de la fenêtre de l'éditeur.
	 * Construit les widgets et assigne les actions et autres listeners
	 * aux widgets
	 * @throws HeadlessException
	 */
	public EditorFrame() throws HeadlessException
	{
		boolean isMacOS = System.getProperty("os.name").startsWith("Mac OS");
		
		setPreferredSize(new Dimension(850, 650));
		drawingModel = new Drawing();
		creationListener = null;
		
		setTitle(EditorName);
		
		if (!isMacOS)
		{
			setIconImage(Toolkit.getDefaultToolkit().getImage(
					EditorFrame.class.getResource("/images/Logo.png")));
		}
		
		// --------------------------------------------------------------------
		// Toolbar en haut
		// --------------------------------------------------------------------
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		JButton btnUndo = new JButton("Undo",IconFactory.getIcon("Undo_small"));
		btnUndo.addActionListener(undoAction);
		toolBar.add(btnUndo);
		JButton btnClear = new JButton("Clear",IconFactory.getIcon("Delete_small"));
		btnClear.addActionListener(clearAction);
		toolBar.add(btnClear);
		JButton btnAbout = new JButton("About",IconFactory.getIcon("About_small"));
		btnAbout.addActionListener(aboutAction);
		toolBar.add(btnAbout);
		JToggleButton btnMove = new JToggleButton("moveMode");
		btnMove.addActionListener(chModeAction);
		toolBar.add(btnMove);
		toolBar.add(Box.createHorizontalGlue());
		JButton btnQuit = new JButton("Quit",IconFactory.getIcon("Quit_small"));
		btnQuit.addActionListener(quitAction);
		toolBar.add(btnQuit);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		infoLabel = new JLabel("tipLabel");
		panel.add(infoLabel,BorderLayout.WEST);
		JLabel coordLabel = new JLabel("coordLabel");
		panel.add(coordLabel,BorderLayout.EAST);
		
		moveListener = new MoveShapeListener(drawingModel, infoLabel);
		
		// --------------------------------------------------------------------
		// Zone de dessin
		// --------------------------------------------------------------------
		InfoPanel infoPanel=new InfoPanel();
		drawingPanel=new DrawingPanel(drawingModel,coordLabel,infoPanel);
		JScrollPane scrollPane = new JScrollPane(drawingPanel);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
				
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BorderLayout(0, 0));
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
		panel_2.setPreferredSize(new Dimension(250,210));
		panel_2.setLayout(new GridLayout(5, 1, 0, 0));
		String shape[]={"Circle","Ellipse","Rectangle","Rounded Rectangle","Polygon","Regular Polygon","Star"};
		ItemListener Shape_listener=new ShapeItemListener(figures.enums.FigureType.CIRCLE);
		JLabeledComboBox j_shape=new JLabeledComboBox("Shape",shape,defaultFigureTypeIndex,Shape_listener);
		panel_2.add(j_shape);	
		//JPanel_Fill_Color
		PaintToType paint_to_type_fill=PaintToType.FILL;
		ItemListener Color_listener=new ColorItemListener(fillPaints,defaultFillColorIndex,specialFillColorIndex,paint_to_type_fill);
		JLabeledComboBox jfill_color=new JLabeledComboBox("Fill Color",fillColorNames,defaultFillColorIndex,Color_listener);
		panel_2.add(jfill_color);
		//JPanel_Edge_Color
		PaintToType paint_to_type_edge=PaintToType.EDGE;
		ItemListener Color_listener_edge=new ColorItemListener(edgePaints,defaultEdgeColorIndex,specialEdgeColorIndex,paint_to_type_edge);
		JLabeledComboBox jedge_color=new JLabeledComboBox("Edge Color",edgeColorNames,defaultEdgeColorIndex,Color_listener_edge);
		panel_2.add(jedge_color);
		//JPanel_Line_Type
		String line_type[]={"None","Solid","Dashed"};
		ItemListener Line_type_listener=new EdgeTypeListener(LineType.SOLID);
		JLabeledComboBox jline_color=new JLabeledComboBox("Line Type",line_type,defaultEdgeTypeIndex,Line_type_listener);
		panel_2.add(jline_color);
		//JPanel_Line_Width
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		panel_3.setLayout(new GridLayout(1, 2, 0, 0));
		JLabel jla=new JLabel("Line Width");
		panel_3.add(jla);
		JSpinner spinner = new JSpinner();
		panel_3.add(spinner);
		spinner.setModel(new SpinnerNumberModel(defaultEdgeWidth, minEdgeWidth, maxEdgeWidth, stepEdgeWidth));
		EdgeWidthListener Edge_width_listener=new EdgeWidthListener(defaultEdgeWidth);
		spinner.addChangeListener(Edge_width_listener);
		//InfoPanel
		panel_1.add(infoPanel, BorderLayout.SOUTH);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		//JMenuBar_Drawing
		JMenu mnDrawing = new JMenu("Drawing");
		menuBar.add(mnDrawing);
		JMenuItem mntmUndo = new JMenuItem("Undo",IconFactory.getIcon("Undo_small"));
		mnDrawing.add(mntmUndo);
		mntmUndo.addActionListener(undoAction);
		JMenuItem mntmClear = new JMenuItem("Clear",IconFactory.getIcon("Delete_small"));
		mnDrawing.add(mntmClear);
		mntmClear.addActionListener(clearAction);
		JMenuItem mntmQuit = new JMenuItem("Quit",IconFactory.getIcon("Quit_small"));
		mnDrawing.add(mntmQuit);
		mntmQuit.addActionListener(quitAction);
		//JMenuBar_Filtering
		JMenu mnFilter = new JMenu("Filter");
		menuBar.add(mnFilter);
		JCheckBoxMenuItem chckbxmntmFiltering = new JCheckBoxMenuItem("Filtering");
		mnFilter.add(chckbxmntmFiltering);
		chckbxmntmFiltering.addActionListener(filteringAction);
		//Sous menu Figures
		JMenu mnFigures = new JMenu("Figures");
		mnFilter.add(mnFigures);
		JCheckBoxMenuItem mntmCircle = new JCheckBoxMenuItem("CIRCLE");
		mntmCircle.setIcon(IconFactory.getIcon("Circle_small"));
		mnFigures.add(mntmCircle);
		mntmCircle.addActionListener(circleFilterAction);
		JCheckBoxMenuItem mntmEllipse = new JCheckBoxMenuItem("ELLIPSE");
		mntmEllipse.setIcon(IconFactory.getIcon("Ellipse_small"));
		mnFigures.add(mntmEllipse);
		mntmEllipse.addActionListener(ellipseFilterAction);
		JCheckBoxMenuItem mntmRectangle = new JCheckBoxMenuItem("RECTANGLE");
		mntmRectangle.setIcon(IconFactory.getIcon("Rectangle_small"));
		mnFigures.add(mntmRectangle);
		mntmRectangle.addActionListener(rectangleFilterAction);
		JCheckBoxMenuItem mntmRoundedrectangle = new JCheckBoxMenuItem("ROUNDED_RECTANGLE");
		mntmRoundedrectangle.setIcon(IconFactory.getIcon("Rounded Rectangle_small"));
		mnFigures.add(mntmRoundedrectangle);
		mntmRoundedrectangle.addActionListener(rRectangleFilterAction);
		JCheckBoxMenuItem mntmPolygon = new JCheckBoxMenuItem("POLYGON");
		mntmPolygon.setIcon(IconFactory.getIcon("Polygon_small"));
		mnFigures.add(mntmPolygon);
		mntmPolygon.addActionListener(polyFilterAction);
		JCheckBoxMenuItem chckbxmntmSs = new JCheckBoxMenuItem("POLYGON_REGULIER");
		mnFigures.add(chckbxmntmSs);
		chckbxmntmSs.setIcon(IconFactory.getIcon("Regular Polygon_small"));
		chckbxmntmSs.addActionListener(rpolyFilterAction);
		JCheckBoxMenuItem chckbxmntmAa = new JCheckBoxMenuItem("ETOILE");
		mnFigures.add(chckbxmntmAa);
		chckbxmntmAa.setIcon(IconFactory.getIcon("Star_small"));
		chckbxmntmAa.addActionListener(etoileFilterAction);
		//Filter Color
		JMenu mnColors = new JMenu("Colors");
		mnFilter.add(mnColors);
		//Fill Color
		JCheckBoxMenuItem chckbxmntmFillColor = new JCheckBoxMenuItem("Fill Color");
		chckbxmntmFillColor.setIcon(IconFactory.getIcon("FillColor_small"));
		mnColors.add(chckbxmntmFillColor);
		//Edge Color
		JCheckBoxMenuItem chckbxmntmEdgeColor = new JCheckBoxMenuItem("Edge Color");
		chckbxmntmEdgeColor.setIcon(IconFactory.getIcon("EdgeColor_small"));
		mnColors.add(chckbxmntmEdgeColor);
		//Sous menu Strokes
		JMenu mnStrokes = new JMenu("Strokes");
		mnFilter.add(mnStrokes);
		JCheckBoxMenuItem line_solide=new JCheckBoxMenuItem("Solid");
		line_solide.setIcon(IconFactory.getIcon("Solid_small"));
		mnStrokes.add(line_solide);
		line_solide.addActionListener(solidLineFilterAction);
		JCheckBoxMenuItem line_dashed=new JCheckBoxMenuItem("Dashed");
		line_dashed.setIcon(IconFactory.getIcon("Dashed_small"));
		mnStrokes.add(line_dashed);
		line_dashed.addActionListener(dashedLineFilterAction);
		JCheckBoxMenuItem line_none=new JCheckBoxMenuItem("None");
		line_none.setIcon(IconFactory.getIcon("None_small"));
		mnStrokes.add(line_none);
		line_none.addActionListener(noneLineFilterAction);
		//JMenuBar_Help
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		JMenuItem mntmAbout = new JMenuItem("About",IconFactory.getIcon("About_small"));
		mnHelp.add(mntmAbout);
		mntmAbout.addActionListener(aboutAction);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Action pour changer le mode
	 * @author yunkai_liu
	 */
	private class ChangeModeAction extends AbstractAction
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			drawingModel.changeMode();
			if(drawingModel.moveMode())
			{
				drawingPanel.removeCreationListener(creationListener);
				drawingPanel.addCreationListener(moveListener);
			} 
			else 
			{
				drawingPanel.removeCreationListener(moveListener);
				drawingPanel.addCreationListener(creationListener);
			}
		}
	}
	
	/**
	 * Action pour quitter l'application
	 * @author yunkai-liu
	 */
	private class QuitAction extends AbstractAction // implements QuitHandler
	{
		/**
		 * Constructeur de l'action pour quitter l'application.
		 * Met en place le raccourci clavier, l'ic么ne et la description
		 * de l'action
		 */
		public QuitAction()
		{
			putValue(NAME, "Quit");
			/*
			 * Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()
			 * 	= InputEvent.CTRL_MASK on win/linux
			 *  = InputEvent.META_MASK on mac os
			 */
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			putValue(LARGE_ICON_KEY, IconFactory.getIcon("Quit"));
			putValue(SMALL_ICON, IconFactory.getIcon("Quit_small"));
			putValue(SHORT_DESCRIPTION, "Quits the application");
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			doQuit();
		}

		/**
		 * Action réalisée pour quitter dans un {@link Action}
		 */
		public void doQuit()
		{
			System.exit(0);
		}
	}

	private class UndoAction extends AbstractAction
	{
		public UndoAction()
		{
			putValue(NAME, "Undo");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			putValue(LARGE_ICON_KEY, IconFactory.getIcon("Undo"));
			putValue(SMALL_ICON, IconFactory.getIcon("Undo_small"));
			putValue(SHORT_DESCRIPTION, "Undo last drawing");
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			drawingModel.removeLastFigure();
			drawingModel.update();
		}
	}

	/**
	 * Action réalisée pour effacer toutes les figures du dessin
	 */
	private class ClearAction extends AbstractAction
	{
		public ClearAction()
		{
			putValue(NAME, "Clear");
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			putValue(LARGE_ICON_KEY,IconFactory.getIcon("Delete"));
			putValue(SMALL_ICON, IconFactory.getIcon("Delete_small"));
			putValue(SHORT_DESCRIPTION, "Erase all drawings");
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			drawingModel.clear();
			drawingModel.update();
		}
	}

	/**
	 * Action réalisée pour afficher la boite de dialogue "A propos ..."
	 */
	private class AboutAction extends AbstractAction // implements AboutHandler
	{
		public AboutAction() 
		{
			putValue(LARGE_ICON_KEY, IconFactory.getIcon("About"));
			putValue(SMALL_ICON, IconFactory.getIcon("About_small"));
			putValue(NAME, "About");
			putValue(SHORT_DESCRIPTION, "App information");
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			doAbout(e);
		}

		public void doAbout(EventObject e)
		{
			JOptionPane.showMessageDialog(null,"A propos...","About",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private class FilteringAction extends AbstractAction // implements AboutHandler
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JCheckBoxMenuItem item=(JCheckBoxMenuItem)arg0.getSource();
			if(item.isSelected())
			{
				drawingModel.setFiltering(true);
				drawingModel.update();
			}
			else
			{
				drawingModel.setFiltering(false);
				drawingModel.update();
			}
		}
	}
	
	private class ShapeFilterAction extends AbstractAction // implements AboutHandler
	{		
		/**
		 * Le type de figure
		 */
		private FigureType type;
		
		/**
		 * Constructeur de l'action pour mettre en place ou enlever un filtre
		 * pour filtrer les types de figures
		 */
		public ShapeFilterAction(FigureType type) 
		{
			this.type = type;
			String name = type.toString();
			putValue(LARGE_ICON_KEY, IconFactory.getIcon(name));
			putValue(SMALL_ICON, IconFactory.getIcon(name + "_small"));
			putValue(NAME, name);
			putValue(SHORT_DESCRIPTION, "Set/unset " + name  + " filter");
		}

		@Override
		public void actionPerformed(ActionEvent event)
		{
			JCheckBoxMenuItem items=(JCheckBoxMenuItem)event.getSource();
			ShapeFilter filter=new ShapeFilter(type);
			if(items.isSelected())
			{
				drawingModel.addShapeFilter(filter);
				if(drawingModel.getFiltering())
					drawingModel.update();
			}
			else
			{
				drawingModel.removeShapeFilter(filter);
				if(drawingModel.getFiltering())
					drawingModel.update();
			}
		}
	}
	
	/**
	 * Action réqlisée pour ajouter ou retirer un filtre de type trait de figure
	 */
	private class LineFilterAction extends AbstractAction // implements AboutHandler
	{		
		/**
		 * Le type de trait de la figure
		 */
		private LineType type;
		
		/**
		 * Constructeur de l'action pour mettre en place ou enlever un filtre
		 * pour filtrer les types de figures
		 */
		public LineFilterAction(LineType type) 
		{
			this.type = type;
			String name = type.toString();
			putValue(LARGE_ICON_KEY, IconFactory.getIcon(name));
			putValue(SMALL_ICON, IconFactory.getIcon(name + "_small"));
			putValue(NAME, name);
			putValue(SHORT_DESCRIPTION, "Set/unset " + name  + " filter");
		}

		@Override
		public void actionPerformed(ActionEvent event)
		{
			JCheckBoxMenuItem items=(JCheckBoxMenuItem)event.getSource();
			LineFilter filter=new LineFilter(type);
			if(items.isSelected())
			{
				drawingModel.addLineFilter(filter);
				if(drawingModel.getFiltering())
					drawingModel.update();
			}
			else
			{
				drawingModel.removeLineFilter(filter);
				if(drawingModel.getFiltering())
					drawingModel.update();
			}
		}
	}

	private class ShapeItemListener implements ItemListener
	{
		public ShapeItemListener(FigureType type)
		{
			drawingModel.setType(type);

			if((type==FigureType.CIRCLE)||(type==FigureType.RECTANGLE)||(type==FigureType.ELLIPSE))
			{
				creationListener=FigureType.CIRCLE.getCreationListener(drawingModel,infoLabel);
				drawingPanel.addCreationListener(creationListener);
			}
		}

		@Override
		public void itemStateChanged(ItemEvent e)
		{
			JComboBox<?> items = (JComboBox<?>) e.getSource();
			int index=items.getSelectedIndex();

			if(index!=defaultFigureTypeIndex)
			{
				String type=((IconItem) items.getItemAt(index)).getCaption();
				if(type.equals("Ellipse"))
				{
					drawingModel.setType(FigureType.ELLIPSE);
					drawingPanel.removeCreationListener(creationListener);
					creationListener=FigureType.ELLIPSE.getCreationListener(drawingModel,infoLabel);
					drawingPanel.addCreationListener(creationListener);
				}
				else if(type.equals("Rectangle"))
				{
					drawingModel.setType(FigureType.RECTANGLE);
					drawingPanel.removeCreationListener(creationListener);
					creationListener=FigureType.RECTANGLE.getCreationListener(drawingModel, infoLabel);
					drawingPanel.addCreationListener(creationListener);
				}
				else if(type.equals("Rounded Rectangle"))
				{
					drawingModel.setType(FigureType.ROUNDED_RECTANGLE);
					drawingPanel.removeCreationListener(creationListener);
					creationListener=FigureType.ROUNDED_RECTANGLE.getCreationListener(drawingModel,infoLabel);
					drawingPanel.addCreationListener(creationListener);
				}
				else if(type.equals("Polygon"))
				{
					drawingModel.setType(FigureType.POLYGON);
					drawingPanel.removeCreationListener(creationListener);
					creationListener=FigureType.POLYGON.getCreationListener(drawingModel,infoLabel);
					drawingPanel.addCreationListener(creationListener);
				}
				else if(type.equals("Regular Polygon"))
				{
					drawingModel.setType(FigureType.POLYGON_REGULIER);
					drawingPanel.removeCreationListener(creationListener);
					creationListener=FigureType.POLYGON_REGULIER.getCreationListener(drawingModel,infoLabel);
					if(e.getStateChange()==ItemEvent.SELECTED)
					{
						String s=JOptionPane.showInputDialog("Choisissez n côté: (5 par défaut)");
						if(s!=null)
						{
							if(s.length()!=0)
							{
								int num=Integer.parseInt(s);
								if(num>=3)
									creationListener.setNum(num);
							}	
						}
					}
					drawingPanel.addCreationListener(creationListener);
				}
				else if(type.equals("Star"))
				{
					drawingModel.setType(FigureType.ETOILE);
					drawingPanel.removeCreationListener(creationListener);
					creationListener=FigureType.ETOILE.getCreationListener(drawingModel,infoLabel);
					if(e.getStateChange()==ItemEvent.SELECTED)
					{
						String s=JOptionPane.showInputDialog("Choisissez n côté: (5 par défaut)");
						if(s!=null)
						{
							if(s.length()!=0)
							{
								int num=Integer.parseInt(s);
								if(num>=3)
									creationListener.setNum(num);
							}
						}
					}
					drawingPanel.addCreationListener(creationListener);
				}
			}
			else
			{
				drawingModel.setType(FigureType.CIRCLE);
				drawingPanel.removeCreationListener(creationListener);
				creationListener=FigureType.CIRCLE.getCreationListener(drawingModel,infoLabel);
				drawingPanel.addCreationListener(creationListener);
			}
		}
	}

	private class ColorItemListener implements ItemListener
	{
		private PaintToType applyTo;

		private Color lastColor;

		/**
		 * Le tableau des couleurs possibles
		 */
		private Paint[] colors;

		private final int customColorIndex;

		private int lastSelectedIndex;

		/**
		 * la couleur choisie
		 */
		private Paint paint;

		public ColorItemListener(Paint[] colors,
		                         int selectedIndex,
		                         int customColorIndex,
		                         PaintToType applyTo)
		{
			this.colors = colors;
			lastSelectedIndex = selectedIndex;
			this.customColorIndex = customColorIndex;
			this.applyTo = applyTo;
			lastColor = (Color) colors[selectedIndex];
			paint = colors[selectedIndex];

			applyTo.applyPaintTo(paint, drawingModel);
		}

		@Override
		public void itemStateChanged(ItemEvent e)
		{
			JComboBox<?> combo = (JComboBox<?>) e.getSource();
			int index = combo.getSelectedIndex();
			
			if(index<colors.length)
			{
				if(index==customColorIndex)
				{
					if(e.getStateChange()==ItemEvent.SELECTED)
					{
						paint=PaintFactory.getPaint(combo, "choose the color", lastColor);
						if(paint!=null)
						{
							applyTo.applyPaintTo(paint, drawingModel);
						}
						else
							applyTo.applyPaintTo(colors[lastSelectedIndex], drawingModel);
					}
				}
				else
				{
					paint=colors[index];
					applyTo.applyPaintTo(paint, drawingModel);
				}
			}
		}
	}

	private class EdgeTypeListener implements ItemListener
	{
		/**
		 * Le type de trait à mettre en place
		 */
		private LineType edgeType;

		public EdgeTypeListener(LineType type)
		{
			edgeType = type;
			drawingModel.setEdgeType(edgeType);
		}

		@Override
		public void itemStateChanged(ItemEvent e)
		{
			JComboBox<?> items = (JComboBox<?>) e.getSource();
			int index = items.getSelectedIndex();

			String type=((IconItem) items.getItemAt(index)).getCaption();
			if(type.equals("Dashed"))
			{
				edgeType=LineType.DASHED;
				drawingModel.setEdgeType(edgeType);
			}
			else if(type.equals("Solid"))
			{
				edgeType=LineType.SOLID;
				drawingModel.setEdgeType(edgeType);
			}
			else
			{
				edgeType=LineType.NONE;
				drawingModel.setEdgeType(edgeType);
			}
		}
	}

	private class EdgeWidthListener implements ChangeListener
	{
		public EdgeWidthListener(int initialValue)
		{
			drawingModel.setEdgeWidth(initialValue);
		}

		@Override
		public void stateChanged(ChangeEvent e)
		{
			JSpinner items = (JSpinner) e.getSource();
			SpinnerNumberModel model=(SpinnerNumberModel)items.getModel();
			Number number=model.getNumber();
			drawingModel.setEdgeWidth(number.floatValue());
		}
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() 
		{
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}