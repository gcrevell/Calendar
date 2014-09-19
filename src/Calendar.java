import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
			createRecurDiff(e);
		} else {
			events.add(e);
		}
	}
	
	public Event getNextEvent() {
		Date d = new Date();
		@SuppressWarnings("deprecation")
		DateTime dt = new DateTime((d.getMonth() + 1) + "-" + d.getDate() + "-" + (d.getYear() + 1900));
		
		for (Event e : events) {
			if (e.getDateTime().compareTo(dt) > 0) {
				return e;
			}
		}
		return null;
	}
	
	public Event[] getDay(DateTime day) {
		if (day.compareTo(FIRST_SUNDAY) < 0 || day.compareTo(LAST_SATURDAY) > 0) {
			return null;
		}
		
		DateTime end = new DateTime(day.toString());
		end.incrementDay();
		
		ArrayList<Event> a = new ArrayList<Event>();
		
		for (Event e : events) {
			if (e.getDateTime().compareTo(day) >= 0 && e.getDateTime().compareTo(end) < 0) {
				a.add(e);
			}
		}
		
		return a.toArray(new Event[0]);
	}
	
	public Event[][] getWeek(DateTime startDay) {
		if (startDay.compareTo(FIRST_SUNDAY) < 0 || startDay.compareTo(LAST_SATURDAY) > 0) {
			return null;
		}
		
		//Check if date is a sunday
		DateTime check = new DateTime(FIRST_SUNDAY.toString());
		DateTime last = new DateTime(FIRST_SUNDAY.toString()); 
		
		while (check.compareTo(LAST_SATURDAY) < 0) {
			if (check.compareTo(startDay) > 0) {
				break;
			}
			
			last = new DateTime(check.toString());
			check.incrementWeek();
		}
		
		startDay.setYear(last.getYear());
		startDay.setMonth(last.getMonth());
		startDay.setDay(last.getDay());
		
		Event[][] ret = new Event[7][0];
		check = new DateTime(last.toString());
		check.incrementDay();
		
		for (int i = 0; i < 7; i++) {
			ArrayList<Event> day = new ArrayList<Event>();
			
			for (Event e : events) {
				if (e.getDateTime().compareTo(last) >= 0 && e.getDateTime().compareTo(check) < 0) {
					day.add(e);
				}
			}
			
			ret[i] = day.toArray(new Event[0]);
			
			last.incrementDay();
			check.incrementDay();
		}
		
		return ret;
	}
	
	public void createRecurSame(Event e){
		events.add(e);
		DateTime date = e.getDateTime();
		DateTime end = e.getEndTime();
		while(date.compareTo(LAST_SATURDAY) == -1){
			Event x = new Event(e.getName(), e.getType());
			DateTime change = changeByWeek(date);
			DateTime changeEnd = changeByWeek(end);
			x.setDateTime(change);
			x.setEndTime(changeEnd);
			x.setRecur(e.getRecur());
			x.setRecurrance(e.getRecurrance());
			events.add(x);
			date = change;
			end = changeEnd;
		}
	}
	public void createRecurDiff(Event e) {
		int day = e.getDateTime().dayOffest();
		for(int i = 0; i<7; i++){
			if(e.getRecurrance()[i]){
				if(i==day){
					createRecurSame(e);
				}
				else if(i<day){
					int diff = 7-(i-day);
					Event x = new Event(e.getName(), e.getType());
					DateTime change = changeByDay(e.getDateTime(), diff);
					DateTime changeEnd = changeByDay(e.getEndTime(), diff);
					x.setDateTime(change);
					x.setEndTime(changeEnd);
					x.setRecur(e.getRecur());
					x.setRecurrance(e.getRecurrance());
					createRecurSame(x);
				}
				else{
					int diff = i-day;
					Event x = new Event(e.getName(), e.getType());
					DateTime change = changeByDay(e.getDateTime(), diff);
					DateTime changeEnd = changeByDay(e.getEndTime(), diff);
					x.setDateTime(change);
					x.setEndTime(changeEnd);
					x.setRecur(e.getRecur());
					x.setRecurrance(e.getRecurrance());
					createRecurSame(x);
				}
			}
			
		}
	}
	
	public DateTime changeByWeek(DateTime date){
		DateTime change = new DateTime();
		change.setAm(date.isAm());
		change.setDay(date.getDay());
		change.setHour(date.getHour());
		change.setMinute(date.getMinute());
		change.setMonth(date.getMonth());
		change.setYear(date.getYear());
		change.incrementWeek();
		return change;
	}
	public DateTime changeByDay(DateTime date, int inc){
		DateTime change = new DateTime();
		change.setAm(date.isAm());
		change.setDay(date.getDay());
		change.setHour(date.getHour());
		change.setMinute(date.getMinute());
		change.setMonth(date.getMonth());
		change.setYear(date.getYear());
		for(int j = 0; j<inc; j++){
			change.incrementDay();
		}
		return change;
	}

}
