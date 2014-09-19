/**
 * @filename Testing.java
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
public class Testing {

	/**
	 * Description
	 * 
	 * @author Gabriel Revells
	 * 
	 * @date Sep 14, 2014
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DateTime date = new DateTime("8-31-2014 12:00 am");
		DateTime date2 = new DateTime("9-1-2014 5:00 pm");
		DateTime date3 = new DateTime("9-1-2014 12:00 am");
		System.out.println(date.compareTo(date2));
		System.out.println(date2.compareTo(date3));
	}

}
