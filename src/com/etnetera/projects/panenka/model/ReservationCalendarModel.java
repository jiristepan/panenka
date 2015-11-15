/*
 * Created on 7.12.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.etnetera.projects.panenka.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
;

/**
 * @author Jiri Stepan
 * Specializovana trida, ktera naplnuje DayModel.dayData informacemi z databaze
 */
public class ReservationCalendarModel extends CalendarModel {
	
	private Connection connection;

	public ReservationCalendarModel(Connection conn){
		super();
		this.connection=conn;
	}	
	
	
	/* (non-Javadoc)
	 * @see com.etnetera.projects.panenka.model.CalendarModel#ConstructDay(java.util.Calendar)
	 */
	protected DayModel ConstructDay(Calendar tmpcal){
		DayModel day=super.ConstructDay(tmpcal);
		
		/* get info from database */
		try{
			day.setDayData(new DayReservationData(connection,day));
		}
		catch (SQLException e){
			day.setDayData(e.getMessage());
		}
		return day;
	}

}
