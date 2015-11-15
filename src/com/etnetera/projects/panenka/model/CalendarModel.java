/*
 * Created on 6.12.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.etnetera.projects.panenka.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Model slouzi pro zobrazeni kalendare v TFS.
 * @author Jiri Stepan
 *
 */
public class CalendarModel {

	private Calendar calendar;
	
	public CalendarModel(){
		Date trialDate=new Date();
		calendar = new GregorianCalendar();
		calendar.setTime(trialDate);
	}
	
	public Collection getDaysForMonthView(){
		ArrayList out=new ArrayList(35);

		//hack
		//calendar.add(Calendar.MONTH,-1);

		calendar.set(Calendar.DAY_OF_MONTH,1);
		int daystomonday = (7+calendar.get(Calendar.DAY_OF_WEEK)-Calendar.MONDAY)%7;
		
		Calendar tmpcal=(GregorianCalendar)calendar.clone();
		tmpcal.add(Calendar.DAY_OF_MONTH,-daystomonday);	  
		int prevMonth;
		int month=calendar.get(Calendar.MONTH);
		if (month!=0) prevMonth=month-1;
		else prevMonth=11;
		
		while (((tmpcal.get(Calendar.MONTH)==this.getActualMonth())||(tmpcal.get(Calendar.MONTH)==prevMonth))){
			DayModel day=ConstructDay(tmpcal);	 
			out.add(day);
			tmpcal.add(Calendar.DAY_OF_MONTH,1);
		}
		while (tmpcal.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY){
			DayModel day=ConstructDay(tmpcal);
			out.add(day);
			tmpcal.add(Calendar.DAY_OF_MONTH,1);
		}
		tmpcal=null;
		return out;
	}
	
	public Collection getDaysBetweenDates(Date from, Date to){
		calendar.setTime(from);
		ArrayList out=new ArrayList(10);
		DayModel day;
		
		while (calendar.getTime().getTime()<=to.getTime()){
			day=ConstructDay(calendar);
			out.add(day);
			calendar.add(Calendar.DAY_OF_MONTH,1);
		}
		
		return out;
	}
	
	/**
	 * @return
	 */
	public int getActualMonth() {
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * @param actualMonth
	 */
	public void setActualMonth(int actualMonth) {
		calendar.set(Calendar.MONTH,actualMonth);
	}
	

	/**
	 * @return
	 */
	public int getActualYear() {
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * @param actualYear
	 */
	public void setActualYear(int actualYear) {
		calendar.set(Calendar.YEAR,actualYear);
	}
	
	public void prevMonth(){
		this.calendar.add(Calendar.MONTH,-1);
	}

	public void nextMonth(){
		this.calendar.add(Calendar.MONTH,1);
	}

	protected DayModel ConstructDay(Calendar tmpcal){
		return  new DayModel(tmpcal.get(Calendar.DAY_OF_MONTH),tmpcal.get(Calendar.MONTH),tmpcal.get(Calendar.YEAR),tmpcal.get(Calendar.DAY_OF_WEEK));
	}
}

