/*
 * Created on 19.5.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.etnetera.projects.panenka.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.zweistein.wbeans.Context;

/**
 * @author Jiri Stepan
 *   
 */
public class EventLogger {
	public static void logEvent(Context ctx,LoggedEvent event, Person person) throws SQLException{
		Connection conn=ctx.getDefaultConnection();
		PreparedStatement pst=conn.prepareStatement("INSERT INTO event_log SET event_time=NOW(), logged_user_id=?, logged_user_name=?, event_type=?, event_params=?, event_ip=?, event_remote_host=?, event_user_cookie=?");
		pst.setInt(1,person.getId());
		pst.setString(2,person.getLongName());
		pst.setString(3,event.getEventType());
		pst.setString(4,event.getEventParams());
		pst.setString(5,ctx.getRequest().getRemoteAddr());
		pst.setString(6,ctx.getRequest().getRemoteHost());
		pst.setString(7,"---");
		pst.execute();
		pst.close();
	}
}
