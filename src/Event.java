/**
 * @filename Event.java
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
public class Event implements Comparable<Event> {
	
	private DateTime dateTime;
	private DateTime endTime;
	private String name;
	private String type;
	
	public Event(String n, String t) {
		name = n;
		type = t;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Event o) {
		return dateTime.compareTo(o.getDateTime());
	}

	/**
	 * @return the dateTime
	 */
	public DateTime getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @return the endTime
	 */
	public DateTime getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}

}
