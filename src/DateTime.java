import java.io.Serializable;
import java.util.Scanner;

/**
 * @filename DateTime.java
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
 * @author Gabriel Revells and Erica Pincumbe
 *
 * @date Sep 14, 2014
 *
 */
public class DateTime implements Comparable<DateTime>, Serializable  {
	
	private static final long serialVersionUID = -9080059968178683770L;

	//autogenerated by eclipse
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (am ? 1231 : 1237);
		result = prime * result + day;
		result = prime * result + hour;
		result = prime * result + minute;
		result = prime * result + month;
		result = prime * result + year;
		return result;
	}
	//autogenerated by eclipse
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DateTime other = (DateTime) obj;
		if (am != other.am)
			return false;
		if (day != other.day)
			return false;
		if (hour != other.hour)
			return false;
		if (minute != other.minute)
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	private int year;
	private short month;
	private short day;
	private short hour;
	private short minute;
	private boolean am;
	
	private static int[] DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	public DateTime() {
		
	}
	
	/**
	 * 
	 * Constructor for the DateTime data type.
	 * This constructor takes in a single string
	 * in the format "(M)M-(D)D-YY(YY) (H)H:(M)M -m"
	 * and will parse it to get the correct
	 * date and time.
	 * 
	 * @author Gabriel Revells
	 * 
	 * @date Sep 14, 2014
	 *
	 * @param s
	 */
	public DateTime(String s) throws NumberFormatException {
		int startIndex = 0;
		int endIndex = s.indexOf('-');
		
		month = Short.parseShort(s.substring(startIndex, endIndex));
		startIndex = endIndex + 1;
		endIndex = s.indexOf('-', startIndex);
		day = Short.parseShort(s.substring(startIndex, endIndex));
		startIndex = endIndex + 1;
		endIndex = s.indexOf(' ', startIndex);
		
		if (endIndex < startIndex) {
			endIndex = s.length();
		}
		
		{
			String tmp = s.substring(startIndex, endIndex);
			if (tmp.length() == 4) {
				year = Integer.parseInt(tmp);
			} else if (tmp.length() == 2) {
				year = Integer.parseInt(tmp) + 2000;
			} else {
				throw new NumberFormatException("Invalid input to DateTime. The year is not 2 or 4 digits long.");
			}
		}
		
		if (endIndex == s.length()) {
			hour = 12;
			minute = 0;
			am = true;
			return;
		}
		
		startIndex = s.indexOf(' ') + 1;
		endIndex = s.indexOf(':', startIndex);
		hour = Short.parseShort(s.substring(startIndex, endIndex));
		startIndex = endIndex + 1;
		endIndex = s.indexOf(' ', startIndex);
		minute = Short.parseShort(s.substring(startIndex, endIndex));
		if (s.charAt(endIndex + 1) == 'a' || s.charAt(endIndex + 1) == 'A') {
			am = true;
		} else if (s.charAt(endIndex + 1) == 'p' || s.charAt(endIndex + 1) == 'P') {
			am = false;
		} else {
			throw new NumberFormatException("Invalid input to DateTime. There was no am or pm");
		}
	}
	
	public String toString() {
		String ret =  month + "-" + day + "-" + year + " " + hour;
		
		if (minute < 10) {
			ret += ":0" + minute;
		} else {
			ret += ":" + minute;
		}
		
		if (am) {
			return ret + " am";
		}
		return ret + " pm";
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(DateTime o) {
		if (getYear() < o.getYear()) {
			return -1;
		} else if (getYear() > o.getYear()) {
			return 1;
		}
		
		if (getMonth() < o.getMonth()) {
			return -1;
		} else if (getMonth() > o.getMonth()) {
			return 1;
		}
		
		if (getDay() < o.getDay()) {
			return -1;
		} else if (getDay() > o.getDay()) {
			return 1;
		}
		
		if (isAm() && !o.isAm()) {
			return -1;
		} else if (!isAm() && o.isAm()) {
			return 1;
		}
		
		if (getHour() % 12 < o.getHour() % 12) {
			return -1;
		} else if (getHour() % 12 > o.getHour() % 12) {
			return 1;
		}
		
		if (getMinute() < o.getMinute()) {
			return -1;
		} else if (getMinute() > o.getMinute()) {
			return 1;
		}
		return 0;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public short getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(short month) {
		this.month = month;
	}

	/**
	 * @return the day
	 */
	public short getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(short day) {
		this.day = day;
	}

	/**
	 * @return the hour
	 */
	public short getHour() {
		return hour;
	}

	/**
	 * @param hour the hour to set
	 */
	public void setHour(short hour) {
		this.hour = hour;
	}

	/**
	 * @return the minute
	 */
	public short getMinute() {
		return minute;
	}

	/**
	 * @param minute the minute to set
	 */
	public void setMinute(short minute) {
		this.minute = minute;
	}

	/**
	 * @return the am
	 */
	public boolean isAm() {
		return am;
	}

	/**
	 * @param am the am to set
	 */
	public void setAm(boolean am) {
		this.am = am;
	}
	
	public void incrementDay() {
		day++;
		if (day > DAYS_IN_MONTH[month - 1]) {
			day = 1;
			month++;
		}
		if (month > 12) {
			month = 1;
			year++;
		}
	}
	
	public void incrementWeek() {
		incrementDay();
		incrementDay();
		incrementDay();
		incrementDay();
		incrementDay();
		incrementDay();
		incrementDay();
	}
	
	public int dayOffest() {
		int cnt = 0;
		DateTime d = new DateTime(Calendar.FIRST_SUNDAY.toString());
		
		if (d.compareTo(this) > 0) {
			return -1;
		}
		
		while (d.getDay() != day || d.getMonth() != month || d.getYear() != year) {
			cnt += 1;
			d.incrementDay();
			
			if (d.compareTo(this) > 0) {
				return -1;
			}
		}
		
		return cnt % 7;
	}
	
	public boolean timeEquals(DateTime date){
		return this.hour == date.hour && this.minute == date. minute && this.am == date.am;
	}

}
