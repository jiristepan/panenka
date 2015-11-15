package com.etnetera.projects.panenka.ui;

import java.util.Map;

import org.zweistein.tfs.TemplateData;
import org.zweistein.tfs.parser.PartStatement;
import org.zweistein.wbeans.ArgumentSource;
import org.zweistein.wbeans.Context;
import org.zweistein.wbeans.Memento;
import org.zweistein.wbeans.tfs.WBeanEnvironment;

import com.etnetera.projects.panenka.model.EventLogger;
import com.etnetera.projects.panenka.model.LoggedEvent;
import com.etnetera.projects.panenka.model.Reservation;

/**
 * @author Jiri Stepan
 */

public class ReservationForm extends DayView {
	Integer reservationId=null;
	
	protected void prepareEnviroment(
		Context ctx,
		WBeanEnvironment env,
		Map parameters,
		TemplateData tpl,
		PartStatement part)
		throws Exception {
		
		env.pushValue("loggedPerson",loggedPerson);
		
		if (reservationId!=null){
			env.pushValue("reservation",new Reservation(ctx.getDefaultConnection(),reservationId.intValue()));
			env.pushValue("newReservation",new Boolean(false));
		}
		else {
			env.pushValue("newReservation",new Boolean(true	));
		}

		super.prepareEnviroment(ctx, env, parameters, tpl, part);
	}
	

	public void burden(Context ctx) throws Exception {
		Memento mem=pickMemento(ctx);
		this.reservationId=(Integer)mem.get("ReservationId",null);
		super.burden(ctx);
	}

	public void relieve(Context ctx) {
		Memento mem=pickMemento(ctx);
		mem.set("ReservationId",this.reservationId);
		super.relieve(ctx);
	}

	public void resetData(){
		this.reservationId=null;
	}

	public Integer getReservationId() {
		return reservationId;
	}

	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}
	
	
    protected boolean executeWebMethod(Context ctx, String method)
            throws Exception {
        
		ArgumentSource as=ctx.getArgumentSource();
        if (method.equals("doReservation")) {
			
			String action=as.getString("action");
			if (action==null) action="new";
			Reservation reservation=null;;
			if (action.equals("new")){
				reservation=Reservation.createNew(ctx.getDefaultConnection());
				EventLogger.logEvent(ctx,new LoggedEvent(LoggedEvent.EVENT_TYPE_ADD_RESERVATION,ctx.getRequest()),loggedPerson);
			}
			else if (action.equals("update")){
				reservation=new Reservation();
				reservation.setId(this.getReservationId().intValue());
				EventLogger.logEvent(ctx,new LoggedEvent(LoggedEvent.EVENT_TYPE_CHANGE_RESERVATION,ctx.getRequest()),loggedPerson);
			}
			reservation.setReservedById(loggedPerson.getId());
			reservation.readFromArgumentSource(ctx,loggedPerson);
			int result=reservation.testReservation(ctx.getDefaultConnection()); // provede test, zda je vse v poradku
			if (result==Reservation.OK){
			    printToMessage("Rezervace uložena");
				reservation.updateDB(ctx.getDefaultConnection());
			}else if(result==Reservation.FULL) {
			    printToMessage("Reservace uložena.");
			    printToAlert("Poèet zarezervovaných osob pøekraèuje kapacitu chaty. Nìkteré osoby jsou zapsány jako náhradníci.");
			    reservation.updateDB(ctx.getDefaultConnection());
			}else if(result==Reservation.USER_DUPLICITY){
			    printToAlert("Chyba - nìkterý z uživatelù je již zapsán. Rezervace nebyla uložena.");
			}else if (result==Reservation.BAD_DATE){
			    printToAlert("Chyba - špatnì zadané datum. Zadejte datum odjezdu i pøíjezdu ve formátu den.mìsíc.rok (napø. 1.3.2005). Datum odjezdu musí také být po datumu pøíjezdu.");
			}
		}

        return super.executeWebMethod(ctx, method);
    }
}
