import java.io.Serializable;
import java.util.ArrayList;
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
	public static final DateTime LAST_SATURDAY = new DateTime("12-20-2014 11:59 pm");
	
	public boolean deleteEvent(Event e) {
		return false;
	}
	
	public void createEvent(Event e) {
		if (e.getRecur()) {
			createRecur(e);
		} else {
			events.add(e);
		}
	}
	
	public Event getNextEvent() {
		return null;
	}
	
	public Event[] getDay(DateTime day) {
		return null;
	}
	
	public Event[][] getWeek(DateTime startDay) {
		if (startDay.compareTo(FIRST_SUNDAY) < 0 || startDay.compareTo(LAST_SATURDAY) > 0) {
			return null;
		}
		
		//Check if date is a sunday
		DateTime check = FIRST_SUNDAY;
		DateTime last = FIRST_SUNDAY; 
		
		while (check.compareTo(LAST_SATURDAY) < 0) {
			if (check.compareTo(startDay) > 0) {
				break;
			}
			
			last = check;
			check.incrementWeek();
		}
		
		Event[][] ret = new Event[7][0];
		check = new DateTime(last.toString());
		
		for (int i = 0; i < 7; i++) {
			
		}
		
		return null;
	}
	
	public void createRecur(Event e) {
		
	}

}
