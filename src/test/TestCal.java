/*
 * Created on 7.12.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package test;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Jiri Stepan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TestCal {
	public static void main(String[] args){

	  // create a GregorianCalendar with the Pacific Daylight time zone
	  // and the current date and time
	  Calendar calendar = new GregorianCalendar();
	  Date trialTime = new Date();
	  calendar.setFirstDayOfWeek(Calendar.MONDAY);
	  calendar.setTime(trialTime);
	  TestCal.printStuff(calendar);
	  System.out.println("MONDAY="+Calendar.MONDAY);

	  //TestCal.printStuff(calendar);	  
      calendar.set(Calendar.DAY_OF_MONTH,1);
	  TestCal.printStuff(calendar);

	  calendar.add(Calendar.MONTH,1);
	  TestCal.printStuff(calendar);
	  calendar.add(Calendar.DAY_OF_MONTH, -1);
	  TestCal.printStuff(calendar);
	}

	private  static void printStuff(Calendar calendar){
      // print out a bunch of interesting things
	  
	  System.out.println("\nYEAR: " + calendar.get(Calendar.YEAR));
	  System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
	  System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
	  System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
	  System.out.println("DATE: " + calendar.get(Calendar.DATE));
	  System.out.println("DAY_OF_WEEK: "+calendar.get(Calendar.DAY_OF_WEEK));
	 
      int daystomonday = (7+calendar.get(Calendar.DAY_OF_WEEK)-Calendar.MONDAY)%7;
	  System.out.println("POCET DNU PRED PONDELIM: "+daystomonday);
  }
}
