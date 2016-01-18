import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* 
 * This class creates the frame object for selling tickets.  
 * When tickets are sold out, a button becomes available to open a 
 * new EventFrame to create a new event and sell more tickets.
 */

/**
 * @author Kristin Clark
 *
 */
public class TicketOrderFrame extends JFrame{
	
	final private String FRAME_TITLE = "TICKET SALES";
	final private int FRAME_WIDTH = 500;
	final private int FRAME_HEIGHT = 500;
	final private int BLANK_HEIGHT = 20;
	final private int STATUS_CANVAS_HEIGHT = 125;
	final private int TICKET_FIELD_WIDTH = 6;
	
	//Set initial frame dimensions and status bar dimensions.
	private int frameHeight = FRAME_HEIGHT;
	private int frameWidth = FRAME_WIDTH;
	
	private int statusWidth = frameWidth - (2* BLANK_HEIGHT);
	private JLabel eventName = new JLabel("");
	private JLabel blankSpace = new JLabel("  ");
	private JLabel userPrompt= new JLabel();
	private JTextField ticketField = new JTextField(TICKET_FIELD_WIDTH);
	private JButton purchaseButton = new JButton("Purchase Tickets");
	private JButton newEventButton = new JButton("New Event");
	private JLabel infoLabel = new JLabel();
	private TicketOrderEvent event;
	private JLabel ticketIconLabel;
	private TicketOrderStatus statusBar = new TicketOrderStatus(this);
	
	private int totalMax;
	private int totalSold;


	//CONSTRUCTOR for ticket order frame for a particular event.
	public TicketOrderFrame(TicketOrderEvent event){
		
		this.event = event;
				
		//Set Frame title
		this.setTitle(FRAME_TITLE);
		
		//Create first panels
		JPanel p1 = createP1();
		JPanel p2 = createP2();
		JPanel p3 = createP3();
			
		//Add a listener to Purchase Button
		PurchaseListener p = new PurchaseListener();
		purchaseButton.addActionListener(p);
		
		//Add a listener to newEvent Button
		newEventButtonListener ev = new newEventButtonListener();
		newEventButton.addActionListener(ev);
		
		//add a resize listener
	    this.addComponentListener(new ResizeListener());
		
		//Place JPanels in border layout sections.
		this.add(BorderLayout.NORTH, p1);
		this.add(BorderLayout.CENTER,p2);
		this.add(BorderLayout.SOUTH, p3);

		this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		this.setMinimumSize(getSize());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newEventButton.setVisible(false);
		this.setVisible(true);
		
	}//end constructor TicketOrderFrame
		
	
	//Getters and Setters
	
	public int getFrameWidth() {
		return frameWidth;
	}

	public int getFrameHeight() {
		return frameHeight;
	}
	
	public int getSTATUS_CANVAS_HEIGHT(){
		return STATUS_CANVAS_HEIGHT;
	}
	
	public int getBLANK_HEIGHT(){
		return BLANK_HEIGHT;
	}
	
	public int getStatusWidth(){
		return statusWidth;
	}
	
	//Create First Panel
	private JPanel createP1(){
		
		//Create custom Font sizes
		Font fontLarge = new Font("Dialog", Font.BOLD, 18);
		Font fontSmall = new Font("Dialog", Font.PLAIN, 14);
				
		//Create a panel in box layout  
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
		p1.setBorder(BorderFactory.createEmptyBorder(BLANK_HEIGHT, 0, 0, 0));    //Set blank top & bottom borders
		
		//Add the event name, an image of tickets, and a prompt to the user.
		eventName.setText(event.getEventName());
		eventName.setFont(fontLarge);
		eventName.setAlignmentX(CENTER_ALIGNMENT);
		if (event.getMaxPerOrder()== 1){
			userPrompt.setText("Enter how many tickets you would like to purchase - only 1 per order!");
		}else{
		userPrompt.setText("Enter how many tickets you would like to purchase (1 - "+event.getMaxPerOrder()+")");
		}
		userPrompt.setAlignmentX(CENTER_ALIGNMENT);
		userPrompt.setFont(fontSmall);
		blankSpace.setFont(fontLarge);
		
		//Get picture to add to JLabel or use blank if not found.
		String imagePath = "images/ticketPic.png";
		String imageDescription = "Picture of tickets.";
		 java.net.URL imgURL = getClass().getResource(imagePath);
		    if (imgURL != null) {
		        ImageIcon ticketIcon = new ImageIcon(imgURL, imageDescription);
		        ticketIconLabel = new JLabel(ticketIcon);
		    } else {
		        ticketIconLabel = new JLabel(" ");
		    }
		ticketIconLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		//Add name of event, picture, and prompt to JPanel p1, with blank labels for extra spacing.
		p1.add(eventName);
		p1.add(blankSpace);
		p1.add(ticketIconLabel);
		p1.add(userPrompt);
		
		return p1;
	
	}//end createP1
	
	
	//Create a panel with a field to enter number of tickets, and a button to purchase them.
	private JPanel createP2(){
		//Create custom Font sizes
		Font fontSmall = new Font("Dialog", Font.PLAIN, 14);

		JPanel p2 = new JPanel();
		p2.setBorder(BorderFactory.createEmptyBorder(BLANK_HEIGHT, 0, 0, 0));    //add blank space before
		ticketField.setFont(fontSmall);
		purchaseButton.setFont(fontSmall);
		newEventButton.setFont(fontSmall);
		
		//Add ticket field and buttons to JPanel p2
		p2.add(ticketField);
		p2.add(purchaseButton);
		p2.add(newEventButton);
		return p2;
	
	}//end createP2
	
	
	//Create a panel in box layout to give feedback and error messages, and a bar graph showing percentage of tickets sold.  
		
	private JPanel createP3(){
		
		//Create custom font
		Font fontSmallItalic = new Font("Dialog", Font.ITALIC, 14);
		
		//set layouts and add information label
		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.PAGE_AXIS));
		p3.setBorder(BorderFactory.createEmptyBorder(0, 0, BLANK_HEIGHT, 0));   //set blank bottom border
		infoLabel.setText(event.getMaxTickets()+" tickets available to be sold.");
		infoLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		infoLabel.setFont(fontSmallItalic);
		p3.add(infoLabel);
		
		//Add status bar.
		statusBar.setPreferredSize(new Dimension(statusWidth, STATUS_CANVAS_HEIGHT));  //set size of drawing canvas
		p3.add(statusBar);
		
		return p3;
	}//end createP3
	

	//When event is sold out, this is called to show the button for creating a new event.
	private void showNewEventButton(){
		infoLabel.setText("This event is SOLD OUT!");	
		userPrompt.setText(" ");
		ticketField.setVisible(false);
		purchaseButton.setVisible(false);
		newEventButton.setVisible(true);
	}//end createNewEvent

	//Listener for button to buy tickets
	class PurchaseListener implements ActionListener{

		private int maxPerOrder = event.getMaxPerOrder();
		private int available = event.getTicketsAvailable();
		private int requested;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			totalMax = event.getMaxTickets();

			//Get user input and check to see if it is a number.
			String tempString = ticketField.getText();
			
			//Get number of tickets requested and process
			try {
				//Parse input string into an integer.  Use catch block for numeric exception error.
				requested = Integer.parseInt(tempString);
				
				//If the requested number cannot be purchased, print an error message.
				
				//Print error message if the number of tickets requested is less than zero.
				if(requested <= 0){
					infoLabel.setForeground(Color.RED);
					infoLabel.setText("Please enter a positive number.");

				//Print error message if the number of tickets requested is greater than the number allowed per person.
				}else if (requested > maxPerOrder){
					infoLabel.setText(maxPerOrder +" is the max available. "+available+" tickets remaining.");
				
				}else if (requested > available){
					infoLabel.setText(available+" is the max available. "+available+" tickets remaining.");
	
				//Else sell the tickets.
				}else{
					available-= requested;
					event.setTicketsAvailable(available);
					infoLabel.setForeground(Color.BLACK);
					infoLabel.setText(requested+" tickets purchased. "+available+" tickets remaining.");
					//Update the status bar.
					totalSold = totalMax - available;
					statusBar.setTicketsSoldStatus(totalSold, totalMax);
				}//end try
				
			//If requested tickets is not a number, print error message.
			}catch (NumberFormatException nfe) { 
				infoLabel.setForeground(Color.RED);
				infoLabel.setText("Please enter a number only.");
				
			//Clear the field for requested tickets.
			}finally{
				ticketField.setText("");
				ticketField.requestFocusInWindow();
				if (available == 0){
					showNewEventButton();
				}//end if
				
			}//end 	finally
		
		}//end Action Performed
			
	}//end class Purchase Listener
	
	
	//Button listener for the button that creates a new event - only available when current event is sold out.
	class newEventButtonListener implements ActionListener{
	
		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			EventFrame frame = new EventFrame();
			
		}//end actionPerformed
		
	}//end class newEventButtonListener


	//Listener to resize status bar if window size is changed.
   class ResizeListener implements ComponentListener {

	@Override
	public void componentHidden(ComponentEvent e) {}
	
	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentResized(ComponentEvent e) {
		frameWidth = (int) e.getComponent().getBounds().getWidth();
		statusWidth = frameWidth - (2 * BLANK_HEIGHT);
		statusBar.setTicketsSoldStatus(totalSold, totalMax);

		repaint();
	}//end componentResized

	@Override
	public void componentShown(ComponentEvent e) {}


    }//end class Resize Listener

	
}//end class TicketOrderFrame
