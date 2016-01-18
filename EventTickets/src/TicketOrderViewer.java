import javax.swing.JFrame;

/**
 * Begins the ticket ordering process by opening an EventFrame to create an event.
 * Once an event is created, another window will open to buy the tickets for it.
 */

/**
 * @author Kristin Clark
 *
 */
public class TicketOrderViewer extends JFrame{
		/**
		 * @param args
		 */
		public static void main(String[] args) {
			
			new EventFrame();

		}//end main
}
