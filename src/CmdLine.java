import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Scanner;

/**
 * @filename CmdLine.java
 *
 * @author Gabriel Revells and Erica Pincumbe
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
		Calendar cal = loadCal();
		Scanner in = new Scanner(System.in);

		if (cal == null) {
			cal = new Calendar();
		}
		System.out.println();
		System.out.println();
		System.out.println("Welcome to the MTU calendar app!");
		System.out.println("To use this calendar, simply type your command at the prompt");

		inputLoop: while (true) {
			System.out.println();
			System.out.println("To see a list of available commands, enter \"cmd\"");
			System.out.println("Please type a command:\n");
			String input = "";
			
			input = in.nextLine();
			
			if (input.equalsIgnoreCase("cmd")) {
				commands();
			} else if (input.equalsIgnoreCase("View week")) {
				DateTime d = null;
				boolean rep = false;
				do {
					if (rep) {
						System.out.println("The date you entered was not in the range of this semester.");
						rep = false;
					}
					System.out.println("Please enter the date of the week you want (m-d-y) or \"cancel\":");
					String test = in.nextLine();
					if (test.equalsIgnoreCase("cancel")) {
						continue inputLoop;
					}
					try {
						d = new DateTime(test);
					} catch (Exception e) {
						System.out.println("The date was not formatted correctly.");
						continue;
					}
					rep = true;
				} while (!printWeek(cal, d));
			} else if (input.equalsIgnoreCase("Add event")) {
				addEvent(cal);
				saveCal(cal);
			} else if (input.equalsIgnoreCase("Delete event")) {
				deleteEvent(cal);
				saveCal(cal);
			} else if (input.equalsIgnoreCase("terminate")) {
				break;
			} else if (input.equalsIgnoreCase("view day")) {
				DateTime d = null;
				boolean rep = false;
				do {
					if (rep) {
						System.out.println("The date you entered was not in the range of this semester.");
						rep = false;
					}
					System.out.println("Please enter the day you would like to view (m-d-y) or \"cancel\":");
					String test = in.nextLine();
					if (test.equalsIgnoreCase("cancel")) {
						continue inputLoop;
					}
					try {
						d = new DateTime(test);
					} catch (Exception e) {
						System.out.println("The date was not formatted correctly.");
						continue;
					}
					rep = true;
				} while (!dayView(cal, d));
			} else if (input.equalsIgnoreCase("next event")) {
				Event next = cal.getNextEvent();

				if (next == null) {
					System.out.println("You do not have any more events.");
				} else {
					System.out.println("Up next you have " + next.getName() + " starting at " + next.getDateTime());
				}
			}

			else {
				System.out.println("That is not a valid command. Please try again.");
			}
		}
		in.close();
	}

	/**
	 * Description
	 * 
	 * @author Gabriel Revells
	 * 
	 * @date Sep 20, 2014
	 *
	 * @param cal
	 */
	private static void deleteEvent(Calendar cal) {
		Scanner in = new Scanner(System.in);
		DateTime d = null;

		while (true) {
			System.out.println("Please enter the date and time of the event to be deleted (m-d-y h:m am):");
			try {
				d = new DateTime(in.nextLine());
			} catch (Exception ex) {
				System.out.println("The date was not formatted correctly.");
				continue;
			}

			break;
		}

		Event[] array = cal.findAtTime(d);

		if (array.length == 0) {
			System.out.println("You do not have an event at that time.");
		} else if (array.length == 1) {
			if (array[0].getRecur()) {
				System.out.println("Do you want to delete all occurences of this event? Yes or no:");
				if ((in.nextLine()).equalsIgnoreCase("yes")) {
					cal.deleteAll(array[0]);
				} else {
					cal.deleteSingleEvent(array[0]);
				}
			} else {
				cal.deleteSingleEvent(array[0]);
			}
		} else {
			System.out.println("You have multiple events at that time, please type the name of the one you wish to delete.");
			for (Event e : array) {
				System.out.println(e.getName());
			}
			System.out.println();
			String del = in.nextLine();
			for (Event e : array) {
				if (e.getName().equalsIgnoreCase(del)) {
					if (e.getRecur()) {
						System.out.println("Do you want to delete all occurences of this event? Yes or no:");
						if ((in.nextLine()).equalsIgnoreCase("yes")) {
							cal.deleteAll(e);
							System.out.println("The event has been deleted.");
							return;
						} else {
							cal.deleteSingleEvent(e);
							System.out.println("The event has been deleted.");
							return;
						}
					} else {
						cal.deleteSingleEvent(e);
						System.out.println("The event has been deleted.");
						return;
					}
				}
			}
			System.out.println("That event could not be found.");
		}

		//in.close();
	}

	/**
	 * Description
	 * 
	 * @author Gabriel Revells
	 * 
	 * @date Sep 19, 2014
	 *
	 * @param cal
	 * @param d
	 */
	private static boolean dayView(Calendar cal, DateTime d) {
		Event[] events = cal.getDay(d);

		if (events == null) {
			return false;
		}

		//30 spaces across
		String title = "Events for " + d.getMonth() + "-" + d.getDay();
		String spacer = "---------------------------------------------";
		int x = 45 - title.length();
		title = space(x/2 + x%2) + title + space(x/2);
		System.out.println(title);

		if (events.length == 0) {
			System.out.println(spacer);
			title = "You have no events for this day!";
			x = 45 - title.length();
			title = space(x/2 + x%2) + title + space(x/2);
			System.out.println(title);
			return true;
		}

		for (Event e : events) {
			System.out.println(spacer);
			title = e.getName();
			x = 45 - title.length();
			if (x < 0) {
				title = title.substring(0, 42) + "...";
			} else {
				title = space(x/2 + x%2) + title + space(x/2);
			}
			System.out.println(title);

			title = "from " + e.getDateTime().getHour() + ":";
			if (e.getDateTime().getMinute() < 10) {
				title += "0";
			}
			title += e.getDateTime().getMinute();
			if (e.getDateTime().isAm()) {
				title += " am";
			} else {
				title += " pm";
			}
			title += " to " + e.getEndTime().getHour() + ":";
			if (e.getEndTime().getMinute() < 10) {
				title += 0;
			}
			title += e.getEndTime().getMinute();
			if (e.getEndTime().isAm()) {
				title += " am";
			} else {
				title += " pm";
			}

			//title = "From " + e.getDateTime().getHour() + ":" + e.getDateTime().getMinute() + " to " + e.getEndTime().getHour() + ":" + e.getEndTime().getMinute();
			x = 45 - title.length();
			title = space(x/2 + x%2) + title + space(x/2);
			System.out.println(title);
		}

		return true;
	}

	/**
	 * Description
	 * 
	 * @author Gabriel Revells
	 * @param cal 
	 * 
	 * @date Sep 19, 2014
	 *
	 */
	private static void addEvent(Calendar cal) {
		Scanner in = new Scanner(System.in);

		System.out.println("Please enter the name of the event:");
		String name = in.nextLine();

		System.out.println("Please enter the type of the event\n(class, meeting, work, other):");
		String type = in.nextLine();

		if (type.equalsIgnoreCase("Class")) {
			Event e = new Event(name, type);
			e.setRecur(true);

			System.out.println("Enter the days of the week the class occurs on (m, t, w, r, f):");
			String days = in.nextLine();
			days.replaceAll(" ", "");

			for (char c : days.toCharArray()) {
				e.setRecurrance(c);
			}

			while (true) {
				System.out.println("Please enter the start time of the class (h:m am):");
				try {
					e.setDateTime(new DateTime("8-31-14 " + in.nextLine()));
				} catch (Exception ex) {
					System.out.println("The date was not formatted correctly.");
					continue;
				}

				System.out.println("Please enter the end time of the class (h:m am):");
				try {
					e.setEndTime(new DateTime("8-31-14 " + in.nextLine()));
				} catch (Exception ex) {
					System.out.println("The date was not formatted correctly.");
					continue;
				}
				break;
			}

			cal.createEvent(e);
		} else {
			Event e = new Event(name, type);

			while (true) {
				System.out.println("Please enter the start date and time of the event (m-d-y h:m):");
				try {
					e.setDateTime(new DateTime(in.nextLine()));
				} catch (Exception ex) {
					System.out.println("The date was not formatted correctly.");
					continue;
				}

				System.out.println("Please enter the end date and time of the event (m-d-y h:m):");
				try {
					e.setEndTime(new DateTime(in.nextLine()));
				} catch (Exception ex) {
					System.out.println("The date was not formatted correctly.");
					continue;
				}
				break;
			}

			System.out.println("Would you like to repeat this event every week? (yes or no):");
			if (in.nextLine().equalsIgnoreCase("yes")) {
				e.setRecur(true);
				boolean[] array = new boolean[7];
				array[e.getDateTime().dayOffest()] = true;
				e.setRecurrance(array);
			}

			cal.createEvent(e);
		}
		//in.close();
	}

	/**
	 * Description
	 * 
	 * @author Gabriel Revells
	 * 
	 * @date Sep 18, 2014
	 *
	 */
	private static void commands() {
		System.out.println("\"Add event\" - Create a new event");
		System.out.println("\"Delete event\" - Delete an event");
		System.out.println("\"View week\" - View a week");
		System.out.println("\"View day\" - View a day");
		System.out.println("\"Terminate\" - End the program");
	}

	public static Calendar loadCal() {
		Calendar cal = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("Calendar.ser"));
			cal = (Calendar) in.readObject();
			in.close();
		} catch(IOException i) {
			//i.printStackTrace();
			return null;
		} catch(ClassNotFoundException c) {
			//c.printStackTrace();
			return null;
		}
		return cal;
	}

	public static void saveCal(Calendar cal) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Calendar.ser"));
			out.writeObject(cal);
			out.close();
		} catch(Exception i) {
			i.printStackTrace();
		}
	}

	public static boolean printWeek(Calendar cal, DateTime sunday) {
		Event[][] events = cal.getWeek(sunday);
		if (events == null) {
			return false;
		}
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
		return true;
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
