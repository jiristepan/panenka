/*
 * Created on 7.12.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.etnetera.projects.panenka.model;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Jiri Stepan
 *
 */
public class DayModel {
	private int day;
	private int month;
	private int year;
	private int dayOfWeek;

	private Object dayData;

	private boolean start_week;
	private boolean end_week;
	
	public DayModel(int d, int m, int y){
		this(d,m,y,0);
    }

	public DayModel(int d, int m, int y, int dof){
		this.setDay(d);
		this.setMonth(m);
		this.setYear(y);
		this.setDayOfWeek(dof);
		if (this.dayOfWeek==0) start_week=true; else start_week=false;
		if (this.dayOfWeek==6) end_week=true; else end_week=false;
	}
	
	/**
	 * @return
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @param day
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * @return
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param month
	 */
	public void setMonth(int month) {
		this.month = month+1; //korekce z kalendar, kde January==0
	}

	/**
	 * @return
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year; 
	}

	public String getStdFormat(){
		return this.day+"."+this.month+"."+(this.year);
	}
	/**
	 * @return
	 */
	public boolean isEnd_week() {
		return end_week;
	}

	/**
	 * @param end_week
	 */
	public void setEnd_week(boolean end_week) {
		this.end_week = end_week;
	}

	/**
	 * @return
	 */
	public boolean isStart_week() {
		return start_week;
	}

	/**
	 * @param start_week
	 */
	public void setStart_week(boolean start_week) {
		this.start_week = start_week;
	}

	/**
	 * @return
	 */
	public int getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * @param dayOfWeek
	 */
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = (7+dayOfWeek-Calendar.MONDAY)%7;
	}

	/**
	 * @return
	 */
	public Object getDayData() {
		return dayData;
	}

	/**
	 * @param dayData
	 */
	public void setDayData(Object dayData) {
		this.dayData = dayData;
	}
	
	public Date getDate(){
		return new Date(year-1900,month-1,day);
	}

}
