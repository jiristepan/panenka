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
			    printToMessage("Rezervace ulo�ena");
				reservation.updateDB(ctx.getDefaultConnection());
			}else if(result==Reservation.FULL) {
			    printToMessage("Reservace ulo�ena.");
			    printToAlert("Po�et zarezervovan�ch osob p�ekra�uje kapacitu chaty. N�kter� osoby jsou zaps�ny jako n�hradn�ci.");
			    reservation.updateDB(ctx.getDefaultConnection());
			}else if(result==Reservation.USER_DUPLICITY){
			    printToAlert("Chyba - n�kter� z u�ivatel� je ji� zaps�n. Rezervace nebyla ulo�ena.");
			}else if (result==Reservation.BAD_DATE){
			    printToAlert("Chyba - �patn� zadan� datum. Zadejte datum odjezdu i p��jezdu ve form�tu den.m�s�c.rok (nap�. 1.3.2005). Datum odjezdu mus� tak� b�t po datumu p��jezdu.");
			}
		}

        return super.executeWebMethod(ctx, method);
    }
}
