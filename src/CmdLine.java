/**
 * @filename CmdLine.java
 *
 * @author Gabriel Revells
 *
 * @date Sep 15, 2014
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
 * @date Sep 15, 2014
 *
 */
public class CmdLine {

	/**
	 * Description
	 * 
	 * @author Gabriel Revells
	 * 
	 * @date Sep 15, 2014
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar cal = new Calendar();
		printWeek(cal, Calendar.FIRST_SUNDAY);
	}

	public static void printWeek(Calendar cal, DateTime sunday) {
		Event[][] events = cal.getWeek(sunday);
		events = new Event[7][0];
		events[0] = new Event[1];
		events[0][0] = new Event("testing", "class");
		events[0][0].setDateTime(new DateTime("8-31-14 7:00 am"));
		events[0][0].setEndTime(new DateTime("8-31-14 8:00 am"));
		//day spaces are 18 wide
		//tabs are 8 spaces
		System.out.println("\t|      Sunday      |      Monday      |     Tuesday      |    Wednesday     |     Thursday     |      Friday      |     Saturday     |");
		String d[] = new String[7];
		//space(7)m/dspace(8)
		//space(7)mm/dspae(7)
		for (int i = 0; i < 7; i++) {
			d[i] = sunday.getMonth() + "/" + sunday.getDay();
			int x = 18 - d[i].length();
			d[i] = space(x/2 + x%2) + d[i] + space(x/2);
			sunday.incrementDay();
		}
		System.out.print("\t|");
		for (int i = 0; i < 7; i++) {
			System.out.print(d[i] + "|");
		}
		System.out.println();

		printSepRow();

		//time:
		// h:mm - 5 char
		//hh:mm - 5 char
		String[] t = new String[48];
		for (int i = 0; i < 48; i++) {
			if (i % 2 == 1) {
				t[i] = "\t";
				continue;
			}
			if (i%24 == 0) {
				t[i] = "12:00";
			} else {
				t[i] = ((i/2)%12) + ":00";
			}
			int x = 8 - t[i].length();
			t[i] = space(x/2) + t[i] + space(x/2 + x%2);
		}

		for (int i = 0; i < 24; i++) {
			//i is the hour of the day
			System.out.print(t[i*2] + "|");
			
			for (int j = 0; j < 7; j++) {
				//j is the day of the week
				if (events[j].length == 0) {
					System.out.print(space(18) + "|");
					continue;
				}
				
				String s = "";
				
				for (int k = 0; k < events[j].length; k++) {
					//k is the events of the day
					int h = events[j][k].getDateTime().getHour();
					if (!events[j][k].getDateTime().isAm()) {
						h += 12;
					}
					
					if (h == i) {
						if (s.length() != 0) {
							s = "  Double booked   ";
							break;
						}
						s = events[j][k].getName();
						if (s.length() > 18) {
							s = s.substring(0, 15) + "...";
						}
						s = s + space(18 - s.length());
					}
				}
				if (s.length() == 0) {
					s = space(18);
				}
				System.out.print(s + "|");
			}

			System.out.println();

			System.out.print(t[i*2 + 1] + "|");
			
			for (int j = 0; j < 7; j++) {
				//j is the day of the week
				if (events[j].length == 0) {
					System.out.print(space(18) + "|");
					continue;
				}
				
				String s = "";
				
				for (int k = 0; k < events[j].length; k++) {
					//k is the events of the day
					int h = events[j][k].getDateTime().getHour();
					if (!events[j][k].getDateTime().isAm()) {
						h += 12;
					}
					
					if (h == i) {
						if (s.length() != 0) {
							s = space(18);
							break;
						}
						s = events[j][k].getDateTime().getHour() + ":";
						
						if (events[j][k].getDateTime().getMinute() < 10) {
							s += "0" + events[j][k].getDateTime().getMinute();
						} else {
							s += events[j][k].getDateTime().getMinute();
						}
						
						s += " to " + events[j][k].getEndTime().getHour() + ":";
						
						if (events[j][k].getEndTime().getMinute() < 10) {
							s += "0" + events[j][k].getEndTime().getMinute();
						} else {
							s += events[j][k].getEndTime().getMinute();
						}
						
						if (s.length() > 18) {
							s = s.substring(0, 15) + "...";
						}
						s = s + space(18 - s.length());
					}
				}
				if (s.length() == 0) {
					s = space(18);
				}
				System.out.print(s + "|");
			}

			System.out.println();
		}
	}

	public static String space(int x) {
		String ret = "";
		for (int i = 0; i < x; i++) {
			ret += " ";
		}
		return ret;
	}

	public static void printSepRow() {
		for (int i = 0; i < 8 + (18*7) + 8; i++) {
			System.out.print("-");
		}
		System.out.println();
	}

}
