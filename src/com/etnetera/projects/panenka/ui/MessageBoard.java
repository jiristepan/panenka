/*
 * Created on 17.5.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.etnetera.projects.panenka.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.zweistein.tfs.TemplateData;
import org.zweistein.tfs.parser.PartStatement;
import org.zweistein.wbeans.ArgumentSource;
import org.zweistein.wbeans.Context;
import org.zweistein.wbeans.Memento;
import org.zweistein.wbeans.tfs.WBeanEnvironment;

import com.etnetera.projects.panenka.model.Message;

/**
 * Typy zprav:
 *  - 
 * @author Jiri Stepan
 *
 */
public class MessageBoard extends AplicationWBeanComposite {
	public static final int DISPLAY_MESSAGES=0;
	public static final int DISPLAY_EDIT=1;
	private int displayMode=0;
	private int messageId;


	public void burden(Context ctx) throws Exception {
		Memento mem=pickMemento(ctx);
		Integer displayModeI=(Integer)mem.get("MessageBoardDisplayMode",new Integer(DISPLAY_MESSAGES));
		this.displayMode=displayModeI.intValue();
		super.burden(ctx);
	}

	public void relieve(Context ctx) {
		Memento mem=pickMemento(ctx);
		mem.set("MessageBoardDisplayMode",new Integer(this.displayMode));
		super.relieve(ctx);
	}
	
	protected void prepareEnviroment(Context ctx, WBeanEnvironment env,
			Map arg2, TemplateData arg3, PartStatement arg4) throws Exception {

		
		Connection con=ctx.getDefaultConnection();
		PreparedStatement ps;
		ResultSet rs;
		if (this.displayMode==DISPLAY_MESSAGES){
		    //TODO: presunout do modelu
			String query="SELECT b.*, p.name, p.surname, bc.title as catTitle from board b, person p, board_category bc WHERE p.id=b.author AND bc.id=b.category AND category in (";
			
			String catList="";
			if (loggedPerson!=null) {
				query+="2,"; //messages for all logged users\
				if (loggedPerson.isManager()) query+="3,5,"; //messages for managers
				if (loggedPerson.isAdmin()) query+="4,5,"; //messages for admins
			}
			query+="1) ORDER BY b.date desc"; //messages for all
			ps=con.prepareStatement( query);
			rs=ps.executeQuery();
			env.pushValue("messages",rs);
		}
		else if (this.displayMode==DISPLAY_EDIT){
		    Message msg=new Message(con,this.messageId);
		    env.pushValue("msg",msg);
			ps=con.prepareStatement("SELECT * from board_category");
			rs=ps.executeQuery();
			env.pushValue("categoryList",rs);
		}
		env.pushValue("displayList",new Boolean(displayMode==DISPLAY_MESSAGES));
		env.pushValue("displayEdit",new Boolean(displayMode==DISPLAY_EDIT));
			
		super.prepareEnviroment(ctx, env, arg2, arg3, arg4);
	}
	
	protected boolean executeWebMethod(Context ctx, String method)
	throws Exception {
		if (method.equals("displayEdit")){
			this.displayMode=DISPLAY_EDIT;
			this.messageId=ctx.getArgumentSource().getInt("id");
		}
		else if (method.equals("displayMessages")){
			this.displayMode=DISPLAY_MESSAGES;
		}
		else if (method.equals("saveMessage")){
			this.displayMode=DISPLAY_MESSAGES;
			this.saveMessage(ctx);
		}
		else if (method.equals("deleteMessage")){
			this.displayMode=DISPLAY_MESSAGES;
			this.deleteMessage(ctx);
		}

		
		return super.executeWebMethod(ctx, method);
	}

	/**
	 * @param ctx
	 */
	private void saveMessage(Context ctx) throws SQLException{
		if (!(loggedPerson.isAdmin()||loggedPerson.isManager())) return;
		ArgumentSource as=ctx.getArgumentSource();
		int id=as.getInt("id");
		String title=as.getString("title");
		String message=as.getString("message");
		int category=as.getInt("category");
		
		Connection con=ctx.getDefaultConnection();
		PreparedStatement ps=con.prepareStatement("REPLACE board (id,date,title,message,author,category,published) values(?,NOW(),?,?,?,?,1)");
		if (id>0) ps.setInt(1,id);
		else ps.setNull(1,0);
		ps.setString(2,title);
		ps.setString(3,id>0?message:"...text...");
		ps.setInt(4,loggedPerson.getId());
		ps.setInt(5,id>0?category:5); //set category to unpublished info
		ps.execute();
	}
	
	private void deleteMessage(Context ctx) throws SQLException{
		if (!(loggedPerson.isAdmin()||loggedPerson.isManager())) return;
		ArgumentSource as=ctx.getArgumentSource();
		int id=as.getInt("id");
		Connection con=ctx.getDefaultConnection();
		PreparedStatement ps=con.prepareStatement("DELETE FROM board WHERE id=?");
		ps.setInt(1,id);
		ps.execute();
	}
	public int getDisplayMode() {
		return displayMode;
	}
	public void setDisplayMode(int displayMode) {
		this.displayMode = displayMode;
	}
}
