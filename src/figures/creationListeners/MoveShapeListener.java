package figures.creationListeners;

import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import figures.Drawing;

/**
 * Listener permettant d'enchainer les actions souris pour réaliser le movement de
 * la figure
 * @author yunkai-liu 
 */
public class MoveShapeListener extends AbstractCreationListener
{	
	/**
	 * Constructeur d'un listener à deux étapes
	 *
	 * @param model le modèle de dessin à modifier par ce creationListener
	 * @param tipLabel le label dans lequel afficher les conseils utilisateur
	 */
	public MoveShapeListener(Drawing model, JLabel tipLabel)
	{
		super(model, tipLabel, 2);

		tips[0] = new String("Cliquez et maintenez enfoncé pour déplacer la figure");
		tips[1] = new String("Relâchez pour terminer le déplacement");

		updateTip();

		System.out.println("MoveShapeListener created");
	}
	
	/**
	 * Sélection de la figure au point de
	 * l'évènement souris, si le bouton appuyé est le bouton gauche.
	 *
	 * @param e l'évènement souris
	 * @see AbstractCreationListener#startFigure(MouseEvent)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 * 
	 * @note Ici on va faire le déplacement pas une création donc il faut reécrire cette méthode
	 * 
	 */
	@Override
	public void startFigure(MouseEvent e)
	{
		startPoint = e.getPoint();
		
		currentFigure = drawingModel.getFigureAt(e.getPoint());

		nextStep();

		drawingModel.update();
	}
	
	/**
	 * Création d'une nouvelle figure au point de
	 * l'évènement souris, si le bouton appuyé est le bouton gauche.
	 *
	 * @param e l'évènement souris
	 * @see AbstractCreationListener#startFigure(MouseEvent)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			startFigure(e);
		}
	}

	/**
	 * Terminaison de la nouvelle figure si le bouton appuyé
	 * était le bouton gauche
	 * @param e l'évènement souris
	 * @see AbstractCreationListener#endFigure(MouseEvent)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			endFigure(e);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		// Rien
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e)
	{
		// Rien
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e)
	{
		// Rien
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e)
	{
		// Rien
	}

	/**
	 * Déplacement du point de la figure, si
	 * l'on se trouve à l'étape 1 et que
	 * le bouton enfoncé est bien le bouton gauche.
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (currentStep == 1)
		{
			if (currentFigure != null)
			{
				currentFigure.setLocation(e.getPoint());
			}
			drawingModel.update();
		}
	}
}