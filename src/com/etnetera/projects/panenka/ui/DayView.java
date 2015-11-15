/*
 * Created on 7.12.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.etnetera.projects.panenka.ui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.zweistein.tfs.TemplateData;
import org.zweistein.tfs.parser.PartStatement;
import org.zweistein.wbeans.ArgumentSource;
import org.zweistein.wbeans.Context;
import org.zweistein.wbeans.Memento;
import org.zweistein.wbeans.tfs.WBeanEnvironment;

import com.etnetera.projects.panenka.model.DayModel;
import com.etnetera.projects.panenka.model.DayReservationData;

/**
 * @author Jiri Stepan
 *
 */
public class DayView extends AplicationWBeanComposite {

	private DayModel dayModel;
	private Context ctx;
	
	public void setDay(Context ctx) throws Exception{
		ArgumentSource as=ctx.getArgumentSource();
		int day=as.getInt("day");
		int month=as.getInt("month");
		int year=as.getInt("year");
		this.setDay(ctx.getDefaultConnection(),day,month,year);
	}
	
	public void setDay(Connection conn,int d, int m, int y) throws SQLException{
		dayModel=new DayModel(d,m-1,y);
		dayModel.setDayData(new DayReservationData(conn,dayModel));	
	}	
	
	/* (non-Javadoc)
	 * @see org.zweistein.wbeans.WBean#burden(org.zweistein.wbeans.Context)
	 */
	public void burden(Context ctx) throws Exception {
		Memento mem=pickMemento(ctx);
		Integer d=(Integer)mem.get("dayModelDay",null);
		Integer m=(Integer)mem.get("dayModelMonth",null);
		Integer y=(Integer)mem.get("dayModelYear",null);
		if ((d!=null)&&(m!=null)&&(y!=null)){
			this.setDay(ctx.getDefaultConnection(),d.intValue(),m.intValue(),y.intValue());
		} else this.dayModel=null;
			
		super.burden(ctx);
	}

	/* (non-Javadoc)
	 * @see org.zweistein.wbeans.impl.AbstractWBean#prepareEnviroment(org.zweistein.wbeans.Context, org.zweistein.wbeans.tfs.WBeanEnvironment, java.util.Map, org.zweistein.tfs.TemplateData, org.zweistein.tfs.parser.PartStatement)
	 */
	protected void prepareEnviroment(
		Context ctx,
		WBeanEnvironment env,
		Map parameters,
		TemplateData tpl,
		PartStatement part)
		throws Exception {
		env.pushValue("day",this.dayModel);
		env.pushValue("loggedPerson",loggedPerson);
		super.prepareEnviroment(ctx, env, parameters, tpl, part);
	}

	/* (non-Javadoc)
	 * @see org.zweistein.wbeans.WBean#relieve(org.zweistein.wbeans.Context)
	 */
	public void relieve(Context ctx) {
		Memento mem=pickMemento(ctx);
		if (dayModel!=null){
			mem.set("dayModelDay",new Integer(dayModel.getDay()));
			mem.set("dayModelMonth",new Integer(dayModel.getMonth()));
			mem.set("dayModelYear",new Integer(dayModel.getYear()));
		}		
		super.relieve(ctx);
	}

}
