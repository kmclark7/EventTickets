import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class creates an EventFrame object which allows the user to create a new event.
 */

/**
 * @author Kristin Clark
 *
 */
public class EventFrame extends JFrame{
	
	private final int FRAME_WIDTH = 600;
	private final int FRAME_HEIGHT = 340;
	private final int BLANK_SPACE = 20;
	private final String FRAME_TITLE = "Create Ticketing Event";
	private String eventImagePath = "images/clappingHands.png";
	private String eventImageDescription = "Picture of clapping hands.";
	private JLabel eventNameLabel = new JLabel("Enter a unique name for your event:");
	private JLabel eventMaxPerOrderLabel = new JLabel("Enter maximum number of tickets per person:");
	private JLabel eventMaxTicketsLabel = new JLabel("Enter total number of tickets to be sold:");
	private JTextField eventNameField = new JTextField();
	private JTextField eventMaxPerOrderField = new JTextField();
	private JTextField eventMaxTicketsField = new JTextField();
	private JButton sellButton = new JButton("SELL TICKETS!");
	private JLabel userInfo = new JLabel("Please enter all fields above to create your ticket-selling event.");
	private JLabel eventIconLabel;
	
	//Constructor for for ticketing event
	public EventFrame(){
				
		//Set Frame title and size
		this.setTitle(FRAME_TITLE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(false);
		
		//Create custom Font sizes
		Font font = new Font("Dialog", Font.PLAIN, 14);
		Font fontBold = new Font("Dialog", Font.BOLD, 14);
		Font fontItalic = new Font("Dialog", Font.ITALIC, 14);	
				
		//Apply font sizes and alignment
		eventNameLabel.setFont(fontBold);
		eventMaxPerOrderLabel.setFont(fontBold);
		eventMaxTicketsLabel.setFont(fontBold);
		eventNameField.setFont(font);
		eventMaxPerOrderField.setFont(font);
		eventMaxTicketsField.setFont(font);
		userInfo.setFont(fontItalic);
		userInfo.setAlignmentX(CENTER_ALIGNMENT);
		sellButton.setFont(fontBold);
		sellButton.setAlignmentX(CENTER_ALIGNMENT);
		
		//Create a panel with GroupLayout for labels and text entry fields.
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		//Get picture to add to JLabel or use blank if not found.
		 java.net.URL imgURL = getClass().getResource(eventImagePath);
		    if (imgURL != null) {
		        ImageIcon eventIcon = new ImageIcon(imgURL, eventImageDescription);
		        eventIconLabel = new JLabel(eventIcon);
		    } else {
		        eventIconLabel = new JLabel(" ");
		    }
		eventIconLabel.setAlignmentX(CENTER_ALIGNMENT);
	
		//Set Horizontal Group
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		hGroup.addGroup(layout.createParallelGroup(Alignment.TRAILING)
				.addComponent(eventNameLabel).addComponent(eventMaxPerOrderLabel).addComponent(eventMaxTicketsLabel));
		hGroup.addGroup(layout.createParallelGroup(Alignment.TRAILING)
				.addComponent(eventNameField).addComponent(eventMaxPerOrderField).addComponent(eventMaxTicketsField));
		layout.setHorizontalGroup(hGroup);
		
		//Set Vertical Group
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		vGroup.addGroup(layout.createParallelGroup(Alignment.LEADING)
				 .addComponent(eventNameLabel).addComponent(eventNameField));
		vGroup.addGroup(layout.createParallelGroup(Alignment.LEADING)
				 .addComponent(eventMaxPerOrderLabel).addComponent(eventMaxPerOrderField));
		vGroup.addGroup(layout.createParallelGroup(Alignment.LEADING)
				 .addComponent(eventMaxTicketsLabel).addComponent(eventMaxTicketsField));
		layout.setVerticalGroup(vGroup);		
		
		//Create a box layout panel with user information, picture and button.
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.add(userInfo);
		//buttonPanel.add(blankSpace);
		buttonPanel.add(Box.createRigidArea(new Dimension(0,BLANK_SPACE)));
		buttonPanel.add(eventIconLabel);
		//buttonPanel.add(blankSpace);
		buttonPanel.add(Box.createRigidArea(new Dimension(0,BLANK_SPACE)));
		buttonPanel.add(sellButton);
		
		//Place Components in border layout sections.
		this.add(BorderLayout.NORTH, panel);
		this.add(BorderLayout.CENTER, buttonPanel);
		
		//Add button Listener to sell button.
		sellButtonListener sell = new sellButtonListener();
		sellButton.addActionListener(sell);

		this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		this.setPreferredSize(getSize());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
						
	}//end EventFrame Constructor
	
	public class sellButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ev) {
			
			int maxPer = 0;
			int maxTix = 0;
			
			//Set the event name equal to whatever was typed in.
			String name = eventNameField.getText();
			
			userInfo.setForeground(Color.RED);
			
			//Get ticket numbers and check for errors.
			try{
				String tempMaxPer = eventMaxPerOrderField.getText();;
				maxPer = Integer.parseInt(tempMaxPer);	
				
				String tempMaxTix = eventMaxTicketsField.getText();	
				maxTix = Integer.parseInt(tempMaxTix);
				
				//If ticket numbers are not logically reasonable, print error message
				if ((maxPer < 1 && maxTix < 1) || maxPer >= maxTix){	
					eventMaxPerOrderField.setText("");
					eventMaxTicketsField.setText("");
					userInfo.setText("The max per person must be less than total number of tickets to sell.");
					
				//Create the ticket order event and close this window.
				}else{
					TicketOrderEvent event = new TicketOrderEvent(name, maxTix, maxPer);
					new TicketOrderFrame(event);
					setVisible(false);
					dispose();
				}
				
			//If error, the print error message, clear input, and wait for more.	
			}catch (NumberFormatException nf){
				eventMaxPerOrderField.setText("");
				eventMaxTicketsField.setText("");
				userInfo.setText("Ticket fields MUST be numbers. Max per person must be less than total tickets to sell.");
			}//end try-catch
			
		}//end class actionPerformed
		
	}//end class sellButtonListener
	
}//end class EventFrame

