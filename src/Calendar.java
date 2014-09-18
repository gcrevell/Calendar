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
		events.add(e);
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
		check.incrementDay();
		
		for (int i = 0; i < 7; i++) {
			ArrayList<Event> day = new ArrayList<Event>();
			
			for (Event e : events) {
				if (e.getDateTime().compareTo(last) > 0 && e.getDateTime().compareTo(check) < 0) {
					day.add(e);
				}
			}
			
			ret[i] = day.toArray(new Event[0]);
			check.incrementDay();
			last.incrementDay();
		}
		
		return ret;
	}

}
