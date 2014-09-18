import java.io.Serializable;
import java.util.TreeSet;

/**
 * @filename Calendar.java
 *
 * @author Gabriel Revells
 *
 * @date Sep 14, 2014
 *
 * Package - 
 *
 * Project - Calendar
 * 
 * Description
 */

/**
 * Description
 * 
 * ClassName - 
 * 
 * @author Gabriel Revells
 *
 * @date Sep 14, 2014
 *
 */
public class Calendar implements Serializable {
	
	
	private static final long serialVersionUID = -147953699231233621L;

	TreeSet<Event> events = new TreeSet<Event>();
	
	public static final DateTime FIRST_SUNDAY = new DateTime("8-31-2014 12:00 am");
	
	public boolean deleteEvent(Event e) {
		return false;
	}
	
	public void createEvent(Event e) {
		events.add(e);
	}
	
	public Event getNextEvent() {
		return null;
	}
	
	public Event[] getDay(DateTime day) {
		return null;
	}
	
	public Event[][] getWeek(DateTime startDay) {
		//Check if date is a sunday
		return null;
	}

}
