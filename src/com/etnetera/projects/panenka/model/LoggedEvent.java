/*
 * Created on 19.5.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.etnetera.projects.panenka.model;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
/**
 * @author Jiri Stepan
 */
public class LoggedEvent {
	public static final String EVENT_TYPE_ADD_RESERVATION="ADD_RESERVATION";
	public static final String EVENT_TYPE_CHANGE_RESERVATION="CHANGE_RESERVATION";
	public static final String EVENT_TYPE_DELETE_RESERVATION="DELETE_RESERVATION";
	public static final String EVENT_TYPE_CHANGE_PERSONAL_INFO="CHANGE_PERSON";
	public static final String EVENT_TYPE_LOGIN="LOGIN";
	public static final String EVENT_TYPE_LOGOUT="LOGOUT";
	public static final String EVENT_TYPE_UNAUTHORIZED="UNAUTHORIZED";
	
	private int id;
	private Date eventDate;
	private int personId;
	private String personName;
	private String eventType;
	private String eventParams;
	
	public LoggedEvent(String type, String params){
		this.eventType=type;
		this.eventParams=params;
	}
	
	/** tento konstruktor predpoklada ze se jedna o dvojice string string z requestu**/
	public LoggedEvent(String type, HttpServletRequest req){
		this(type,"req");
		
		Enumeration keys=req.getParameterNames();
		StringBuffer sb=new StringBuffer();
		for(;keys.hasMoreElements();){
			String key=(String)keys.nextElement();
			String[] vals=(String[])req.getParameterValues(key);
			sb.append("(");
			sb.append(key);
			sb.append(":");
			for (int i=0;i<vals.length;i++) sb.append(vals[i]+",");
			sb.append(")");
		}
		this.setEventParams(sb.toString());
	}
	
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public String getEventParams() {
		return eventParams;
	}
	public void setEventParams(String eventParams) {
		this.eventParams = eventParams;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
}
