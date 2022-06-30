import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
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
	private static boolean paused = false;
	private static ArrayList<Langton> antList;
	private static Dimension screen; 
	private static JFrame mainFrame;
	private static JPanel drawPanel, antPanel;
	
	public static void main(String[] args)
	{
		mainFrame = new JFrame();
		antPanel = new JPanel(new BorderLayout());
		drawPanel = new JPanel();
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		drawPanel.setSize(screen.width, screen.height);		
		drawPanel.setBackground(Color.white);
		antList = new ArrayList<>();
		
		JTextField antNumText = new JTextField("1");	
		JTextField antStepsText = new JTextField("11000");
		JLabel antNumLabel = new JLabel("Amount of Ants: ");
		JLabel antStepsLabel = new JLabel("Amount of Ant Steps");
		JButton runButton = new JButton(new ImageIcon(Main.class.getResource("resources/run.png")));
		JButton stopButton = new JButton(new ImageIcon(Main.class.getResource("resources/stop.png")));
		JButton clearButton = new JButton(new ImageIcon(Main.class.getResource("resources/clear.png")));
		JButton colorButton = new JButton(new ImageIcon(Main.class.getResource("resources/color.png")));
		JCheckBox isSleep = new JCheckBox("Enable Sleep");
		isSleep.setToolTipText("Move slowly through simulation");
		JToolBar toolBar = new JToolBar();
		
		mainFrame.setTitle("Langton's Ant by David Blackstone");
		mainFrame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent)
			{
				//window needs resizing support!!!!!
			}
		});
		
		runButton.setToolTipText("Run the Simulation");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				antNum = Integer.valueOf(antNumText.getText());
				antSteps = Long.valueOf(antStepsText.getText());
				if(isSleep.isSelected()) sleep = true;
				else sleep = false;

				new Thread(new Runnable() {
					@Override
					public void run()
					{
						try 
						{
							if(!paused)
							{
								runSimulation((Graphics2D) drawPanel.getGraphics());
							}
							
							else
							{
								for(Langton lang : antList)
								{
									lang.start();
								}
							}
						} 
						
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
		
		stopButton.setToolTipText("Stop the Simulation");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				for(Langton lang : antList)
				{
					lang.stop();
				}
			}
		});
		
		clearButton.setToolTipText("Clear the screen");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				antNum = 0;
				antSteps = 0;
				paused = false;
				drawPanel.invalidate();
				drawPanel.validate();
				drawPanel.repaint();	
			}
		});
		
		colorButton.setToolTipText("set Background Color");
		colorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				drawPanel.setBackground(JColorChooser.showDialog(mainFrame, "Select Background Color", Color.white));
			}
		});
		
		toolBar.add(antStepsLabel);
		toolBar.add(antStepsText);
		toolBar.add(antNumLabel);
		toolBar.add(antNumText);
		toolBar.add(colorButton);
		toolBar.add(isSleep);
		toolBar.add(runButton);
		toolBar.add(stopButton);
		toolBar.add(clearButton);
		antPanel.add(toolBar, BorderLayout.PAGE_START);
		antPanel.add(drawPanel, BorderLayout.CENTER);
		
		mainFrame.add(antPanel);
		mainFrame.setVisible(true);
	}
	
	private static void runSimulation(Graphics2D g2d) throws InterruptedException
	{
		Random rand = new Random();
		
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
			
			lang.start();
			antList.add(lang);
		}
	}
}
