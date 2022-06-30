import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Langton extends JPanel implements Runnable
{
	private static final int BOX_DIM = 1;
	 
	private volatile int antX, antY;
	private volatile long antSteps;
	private volatile int screenWidth, screenHeight;
	private volatile Graphics2D g2d;
	private volatile Color col;
	private volatile boolean sleep;
	private static volatile int[][] grid;
	
	private final AtomicBoolean running = new AtomicBoolean(false);
	private static Thread worker;
	
	public Langton(int antX, int antY, Graphics2D g2d, Color col, long antSteps, int screenWidth, int screenHeight, boolean sleep) 
	{
		this.antX = antX;
		this.antY = antY;
		this.g2d = g2d;
		this.col = col;
		this.antSteps = antSteps;
		this.sleep = sleep;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		grid = new int[screenWidth][screenHeight];
	}
	
	public void start()
	{
		worker = new Thread(this);
		worker.start();	
	}
	
	public void stop()
	{
		running.set(false);
	}

	public synchronized static void drawPixel(int x, int y, Graphics2D g2d, Color col, boolean white) 
	{
		g2d.setColor(col);
		g2d.fillRect(x, y, BOX_DIM, BOX_DIM); 
		if(white) 
			grid[x][y] = 1;
		else 
			grid[x][y] = 0;
	}
	
	public synchronized static boolean checkPixelWhite(int x, int y)
	{
		return (grid[x][y] == 0);
	}
	
	public void run()
	{
		running.set(true);

		Direction antDirection = Direction.N;
		for(int i = 0; i < antSteps && running.get(); i++)
		{
			if(antX > screenWidth -1) 
				antX = 0;
			else if(antX < 0) 
				antX = screenWidth - 1;
			if(antY > screenHeight -1) 
				antY = 0;
			else if(antY < 0) 
				antY = screenHeight - 1;
			if(checkPixelWhite(antX, antY))
			{	
				drawPixel(antX, antY, g2d, col, true);
				switch(antDirection)
				{
				case N:
					antDirection = Direction.E;
					antY += BOX_DIM;
					break;

				case S:
					antDirection = Direction.W;
					antY -= BOX_DIM;
					break;

				case E:
					antDirection = Direction.S;
					antX += BOX_DIM;
					break;

				case W:
					antDirection = Direction.N;
					antX -= BOX_DIM;
					break;
				}
			}
			
			else
			{
				drawPixel(antX, antY, g2d, col, false);
				switch(antDirection)
				{
				case N:
					antDirection = Direction.W;
					antY -= BOX_DIM;
					break;
				case S:
					antDirection = Direction.E;
					antY += BOX_DIM;
					break;

				case E:
					antDirection = Direction.N;
					antX -= BOX_DIM;
					break;

				case W:
					antDirection = Direction.S;
					antX += BOX_DIM;
					break;
				}
			}
			
			
			if(sleep)
			{
				try 
				{			
					Thread.sleep(1);
				} 
				
				catch (InterruptedException e) {}
			}
		}
		
	}
	
	public enum Direction
	{
		N, S, E, W;
	}
}
