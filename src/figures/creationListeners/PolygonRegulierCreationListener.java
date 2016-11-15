package figures.creationListeners;

import java.awt.event.MouseEvent;
import javax.swing.JLabel;

import figures.Drawing;

/**
 * Listener permettant d'enchainer les actions souris pour créer des formes
 * polygons comme des polygons réguliers ou des étoiles 
 * @author yunkai-liu
 * 
 */
public class PolygonRegulierCreationListener extends AbstractCreationListener
{
	/**
	 * Le nombre des brahcnes par défaut
	 */
	private int num=5;
	
	/**
	 * Constructeur d'un listener à deux étapes: pressed->drag->release pour
	 * toutes les figures à caractère polygon (polygons réguliers ou des étoiles)
	 *
	 * @param model le modèle de dessin à modifier par ce creationListener
	 * @param tipLabel le label dans lequel afficher les conseils utilisateur
	 */
	public PolygonRegulierCreationListener(Drawing model, JLabel tipLabel)
	{
		super(model, tipLabel, 2);
		
		tips[0] = new String("Cliquez et maintenez enfoncé pour initier la figure");
		tips[1] = new String("Relâchez pour terminer la figure");

		updateTip();

		System.out.println("PolygonRegulierShapeCreationListener created");
	}
	
	/**
	 * set les points du polygon
	 * @param n le nombre des branches
	 * @note Cette méthode DOIT être réimplémentée dans la classes (PolygonRegulierCreationListener) fille pour
	 * transmettre n aux étoile et regular polygon
	 */
	public void setNum(int n)
	{
		this.num=n;
	}

	/**
	 * Création d'une nouvelle figure de taille 0 au point de
	 * l'évènement souris, si le bouton appuyé est le bouton gauche.
	 *
	 * @param e l'évènement souris
	 * @see AbstractCreationListener#startFigure(MouseEvent)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		if ((e.getButton() == MouseEvent.BUTTON1) && (currentStep == 0))
		{
			startFigure(e);
			//set le nombre des branches de la figure
			currentFigure.setN(num);
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
		if ((e.getButton() == MouseEvent.BUTTON1) && (currentStep == 1))
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
		//Rien
	}

	/**
	 * Déplacement du point en bas à droite de la figure, si
	 * l'on se trouve à l'étape 1 (après initalisation de la figure) et que
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
				currentFigure.setLastPoint(e.getPoint());
			}
			drawingModel.update();
		}
	}
}