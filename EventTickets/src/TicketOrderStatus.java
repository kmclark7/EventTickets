import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * This object creates the status bar which is displayed showing the percent of tickets remaining. 
 */

/**
 * @author Kristin Clark
 *
 */
public class TicketOrderStatus extends JComponent{
	TicketOrderFrame frame;
	int blank, xLength, yLength, xStart, yStart, soldXValue;
	
	public TicketOrderStatus(TicketOrderFrame frame) {
		super();
		this.frame = frame;
		blank = frame.getBLANK_HEIGHT();	
		xStart = blank;
		yStart = blank - 1;
		yLength = frame.getSTATUS_CANVAS_HEIGHT() - blank;

		}//end TicketOrderStatus constructor

	public void paintComponent(Graphics g){
		
		//Calculate x dimenstion based on width of frame.
		xLength = frame.getStatusWidth() - blank;
		
		//Draw rectangle graph outline
		g.setColor(Color.BLACK);
		g.drawRect(xStart, yStart, xLength, yLength);
		
		//Draw rectangle representing percent of tickets sold
		g.fillRect(xStart, yStart, soldXValue, yLength);
		
	}//end paintComponent	
	
	public void setTicketsSoldStatus(int totalSold, int maxAvailable){
		
		double pctSold = (double)totalSold / (double) maxAvailable;
		soldXValue = (int) Math.round(pctSold * xLength);
		//System.out.println(pctSold+"  "+totalSold+"  "+maxAvailable+"  "+soldXValue+"  "+xLength);
		
		repaint();
		
	}//end setTicketsSoldStatus
	


}
