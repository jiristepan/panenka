package com.etnetera.projects.panenka.ui;

import java.util.HashMap;
import java.util.Map;

import org.zweistein.tfs.TemplateData;
import org.zweistein.tfs.parser.PartStatement;
import org.zweistein.wbeans.ArgumentSource;
import org.zweistein.wbeans.Context;
import org.zweistein.wbeans.Memento;
import org.zweistein.wbeans.tfs.WBeanEnvironment;
/**
 * Sdruzuje rezervacni kalendar, pohled na detail dne a rezervacni formular. 
 * Na zaklade udalosti v kalendari otevira pohled na den nebo rezervacni formular.
 * @author Jiri Stepan
 * @see com.etnetera.projects.panenka.ui.CalendarView
 * @see com.etnetera.projects.panenka.ui.ReservationForm
 * @see com.etnetera.projects.panenka.ui.DayView
 */
public class ReservationCalendarComposite extends AplicationWBeanComposite {
	public static final int DISPLAY_DETAIL=0;
	public static final int DISPLAY_RESERVATION=1;
	
	private CalendarView calendarView;
	private DayView dayView;
	private ReservationForm reservationForm;
	
	private int message;
	private HashMap messageParams;

	private int viewMode=0; // 0 - prohlizeni, 1-rezervace
	/* (non-Javadoc)
	 * @see org.zweistein.wbeans.WBean#initialize()
	 */
	public void initialize() {
		this.calendarView=new CalendarView();
		this.calendarView.setDefaultTemplate("calendar.tfs");
		this.calendarView.setName("calendarView");
		this.addChild(calendarView);

		this.dayView=new DayView();
		this.dayView.setDefaultTemplate("dayView.tfs");
		this.dayView.setName("dayView");
		this.addChild(dayView);

		this.reservationForm=new ReservationForm();
		this.reservationForm.setDefaultTemplate("reservationForm.tfs");
		this.reservationForm.setName("reservationForm");
		this.addChild(reservationForm);

		super.initialize();
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
		env.pushValue("showDayDetail", new Boolean(viewMode==DISPLAY_DETAIL));
		env.pushValue("showReservation", new Boolean(viewMode==DISPLAY_RESERVATION));
			
		super.prepareEnviroment(ctx, env, parameters, tpl, part);
	}
	
	

	/* (non-Javadoc)
	 * @see org.zweistein.wbeans.impl.AbstractWBean#executeWebMethod(org.zweistein.wbeans.Context, java.lang.String)
	 */
	protected boolean executeWebMethod(Context ctx, String method)
		throws Exception {
		ArgumentSource as=ctx.getArgumentSource();
		
		if (method.equals("setDay")){
			this.dayView.setDay(ctx);
			this.reservationForm.setDay(ctx);
			this.viewMode=DISPLAY_DETAIL;
		} 
		else if (method.equals("showReservation")) {
			this.reservationForm.resetData();
			if (as.hasArgument("reservation_id",0)){
				this.reservationForm.setReservationId(as.getInteger("reservation_id"));
				this.viewMode=DISPLAY_RESERVATION;

			}
			else if(as.hasArgument("day",0)){
				this.reservationForm.setDay(ctx);
				this.viewMode=DISPLAY_RESERVATION;
			}
		}
		else if (method.equals("cancelReservation")) {
		    this.viewMode=DISPLAY_DETAIL;
		}
		

		ctx.redirect(ctx.getAddress().getTopClass());
		return super.executeWebMethod(ctx, method);
	}
	

	/* (non-Javadoc)
	 * @see org.zweistein.wbeans.WBean#burden(org.zweistein.wbeans.Context)
	 */
	public void burden(Context ctx) throws Exception {
		Memento mem=pickMemento(ctx);
		this.viewMode=((Integer)mem.get("calendarCompositeViewMode",new Integer(0))).intValue();
		super.burden(ctx);
	}

	/* (non-Javadoc)
	 * @see org.zweistein.wbeans.WBean#relieve(org.zweistein.wbeans.Context)
	 */
	public void relieve(Context ctx) {
		Memento mem=pickMemento(ctx);
		mem.set("calendarCompositeViewMode",new Integer(this.viewMode));
		super.relieve(ctx);
	}
	public int getMessage() {
		return message;
	}

	public HashMap getMessageParams() {
		return messageParams;
	}

	public void logoutEvent() {
		this.viewMode=DISPLAY_DETAIL;		
	}

}
