/*
 * Created on 7.12.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.etnetera.projects.panenka.ui;

import java.util.Map;

import org.zweistein.tfs.TemplateData;
import org.zweistein.tfs.parser.PartStatement;
import org.zweistein.wbeans.Context;
import org.zweistein.wbeans.Memento;
import org.zweistein.wbeans.tfs.WBeanEnvironment;

import com.etnetera.projects.panenka.model.CalendarModel;
import com.etnetera.projects.panenka.model.ReservationCalendarModel;


/**
 * @author Jiri Stepan
 *
 */
public class CalendarView extends AplicationWBeanComposite {
	/* (non-Javadoc)
	 * @see org.zweistein.wbeans.impl.AbstractWBean#prepareEnviroment(org.zweistein.wbeans.Context, org.zweistein.wbeans.tfs.WBeanEnvironment, java.util.Map, org.zweistein.tfs.TemplateData, org.zweistein.tfs.parser.PartStatement)
	 */
	CalendarModel calendarModel;

	public void setCalendarModel(CalendarModel calMod){
		this.calendarModel=calMod;
	}
	protected void prepareEnviroment(
		Context ctx,
		WBeanEnvironment env,
		Map parameters,
		TemplateData tpl,
		PartStatement part)
		throws Exception {
		env.pushValue("actualMonth",new Integer(calendarModel.getActualMonth()+1));
		env.pushValue("actualYear",new Integer(calendarModel.getActualYear()));
		
		env.pushValue("dayList",calendarModel.getDaysForMonthView());
		super.prepareEnviroment(ctx, env, parameters, tpl, part);

		
	}

	protected boolean executeWebMethod(Context ctx, String method)
		throws Exception {
		
		if (method.equals("nextMonth")){
			calendarModel.nextMonth();
			ctx.redirect(ctx.getAddress().getTopClass());
		}
		else if (method.equals("prevMonth")){
			calendarModel.prevMonth();
			ctx.redirect(ctx.getAddress().getTopClass());
		}
		else if (method.equals("showMonth")){
		    calendarModel.setActualMonth(ctx.getArgumentSource().getInt("month"));
		    ctx.redirect(ctx.getAddress().getTopClass());
		}
		
		return super.executeWebMethod(ctx, method);
	}

	public void burden(Context ctx) throws Exception {
		Memento mem=pickMemento(ctx);
		this.calendarModel=new ReservationCalendarModel(ctx.getDefaultConnection());
		Integer year=(Integer)mem.get("calendarYear",null);		
		Integer month=(Integer)mem.get("calendarMonth",null);		
		if (year!=null) this.calendarModel.setActualYear(year.intValue());
		if (month!=null) this.calendarModel.setActualMonth(month.intValue());
		super.burden(ctx);
	}

	public void relieve(Context ctx) {
		Memento mem=pickMemento(ctx);
		mem.set("calendarYear",new Integer(this.calendarModel.getActualYear()));
		mem.set("calendarMonth",new Integer(this.calendarModel.getActualMonth()));
		super.relieve(ctx);
	}


}
