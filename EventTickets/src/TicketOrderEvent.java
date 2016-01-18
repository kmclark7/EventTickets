/**
 * This class represents the actual ticket objects.
 */

/**
 * @author Kristin Clark
 *
 */
public class TicketOrderEvent {
	private String eventName;
	private int maxTickets;
	private int maxPerOrder;
	private int ticketsAvailable;
	
	//Constructor
	TicketOrderEvent(String eventName, int maxTickets, int maxPerOrder){
		this.eventName = eventName;
		this.maxTickets = maxTickets;
		this.maxPerOrder = maxPerOrder;
		this.ticketsAvailable = maxTickets;
	}//end constructor
	
	//Constructor
	TicketOrderEvent(int maxTickets, int maxPerOrder){
		this.eventName = "Event";
		this.maxTickets = maxTickets;
		this.maxPerOrder = maxPerOrder;
		this.ticketsAvailable = maxTickets;
	}//end constructor

	public void setEventName(String eventName) {
			this.eventName = eventName;
		}

	public String getEventName() {
		return eventName;
	}

	public int getMaxTickets() {
		return maxTickets;
	}

	public void setMaxTickets(int maxTickets) {
		this.maxTickets = maxTickets;
	}

	public int getMaxPerOrder() {
		return maxPerOrder;
	}

	public void setMaxPerOrder(int maxPerOrder) {
		this.maxPerOrder = maxPerOrder;
	}

	public int getTicketsAvailable() {
		return ticketsAvailable;
	}

	public void setTicketsAvailable(int ticketsAvailable) {
		this.ticketsAvailable = ticketsAvailable;
	}
	
}
