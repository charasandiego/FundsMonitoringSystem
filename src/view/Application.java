package view;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application 
{
	private static MainFrame mainFrame; 
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				new Application();
			}
		});
	}
	
	public Application()
	{
		initialize();
	}
	
	private void initialize(){
		try 
		{
			LoginDialog window = new LoginDialog();
			window.setVisible(true);
			if (window.getCheck() == 0)
			{
				mainFrame = new MainFrame(window.getUserName(), window.getUserType());
				mainFrame.setVisible(true);
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static MainFrame getMainFrame() 
	{
		return mainFrame;
	}
}
