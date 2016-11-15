import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import widgets.EditorFrame;

/**
 * @author yunkai-liu
 */
public class Editor
{
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}

		String osName = System.getProperty("os.name");
		if (osName.startsWith("Mac OS"))
		{
			macOSSettings();
		}
		
		final EditorFrame frame = new EditorFrame();

		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					frame.pack();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	private static void macOSSettings()
	{
		System.setProperty("apple.laf.useScreenMenuBar", "true");

		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Figure Editor");
	}
}