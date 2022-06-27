import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class Main extends JFrame
{
	public Main() {}

	private static int antNum = 0;
	private static long antSteps = 0;
	private static boolean sleep = false;
	private static Dimension screen; 
	
	public static void main(String[] args)
	{
		JFrame mainFrame = new JFrame();
		JPanel antPanel = new JPanel(new BorderLayout());
		JPanel drawPanel = new JPanel();
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setSize(screen.width, screen.height);
		drawPanel.setSize(screen.width, screen.height);
		drawPanel.setBackground(Color.black);
		
		JTextField antNumText = new JTextField("1");	
		JTextField antStepsText = new JTextField("11000");
		JLabel antNumLabel = new JLabel("Amount of Ants: ");
		JLabel antStepsLabel = new JLabel("Amount of Ant Steps");
		JButton runButton = new JButton(new ImageIcon(Main.class.getResource("resources/run.png")));
		JButton stopButton = new JButton(new ImageIcon(Main.class.getResource("resources/stop.png")));
		JCheckBox isSleep = new JCheckBox("Enable Sleep");
		JToolBar toolBar = new JToolBar();
		
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				antNum = Integer.valueOf(antNumText.getText());
				antSteps = Long.valueOf(antStepsText.getText());
				if(isSleep.isSelected()) sleep = true;
				else sleep = false;
				try 
				{	
					runSimulation((Graphics2D) drawPanel.getGraphics());
				} 
				
				catch (InterruptedException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				antNum = 0;
				antSteps = 0;
				antPanel.invalidate();
				antPanel.validate();
				antPanel.repaint();
			}
		});
		
		toolBar.add(antStepsLabel);
		toolBar.add(antStepsText);
		toolBar.add(antNumLabel);
		toolBar.add(antNumText);
		toolBar.add(isSleep);
		toolBar.add(runButton);
		toolBar.add(stopButton);
		antPanel.add(toolBar, BorderLayout.PAGE_START);
		antPanel.add(drawPanel, BorderLayout.CENTER);
		
		mainFrame.add(antPanel);
		mainFrame.setVisible(true);
	}
	
	private static void runSimulation(Graphics2D g2d) throws InterruptedException
	{
		Random rand = new Random();
		ArrayList<Thread> antList = new ArrayList<>();
		
		for(int i = 0; i < antNum; i++)
		{
			Langton lang = new Langton
			(
				rand.nextInt(screen.width),
				rand.nextInt(screen.height),
				g2d,
				new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)),
				antSteps,
				screen.width,
				screen.height,
				sleep
			);	
			
			Thread thread = new Thread(lang);
			thread.start();
			antList.add(thread);
		}
		
		for(int i = 0; i < antList.size(); i++)
		{
			Thread tmp = antList.get(i);
			tmp.join();
		}
	}
}
