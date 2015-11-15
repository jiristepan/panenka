package com.etnetera.projects.panenka.ui;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.zweistein.tfs.TemplateData;
import org.zweistein.tfs.parser.PartStatement;
import org.zweistein.wbeans.ArgumentSource;
import org.zweistein.wbeans.Context;
import org.zweistein.wbeans.tfs.WBeanEnvironment;

import com.etnetera.projects.panenka.model.EventLogger;
import com.etnetera.projects.panenka.model.LoggedEvent;
import com.etnetera.projects.panenka.model.Reservation;


/**
 *
 * @author Jiri Stepan
 */
public class ReservationsView extends AplicationWBeanComposite {
    ReservationForm reservationForm;
    
	public ReservationsView(){
		this.setDefaultTemplate("reservationsView.tfs");
	}
	
	public void initialize() {
		this.reservationForm=new ReservationForm();
		this.reservationForm.setDefaultTemplate("reservationForm.tfs");
		this.reservationForm.setName("reservationForm");
		this.addChild(reservationForm);
	}	
	
    protected void prepareEnviroment(Context ctx, WBeanEnvironment env,
            Map arg2, TemplateData arg3, PartStatement arg4) throws Exception {
        
        super.prepareEnviroment(ctx, env, arg2, arg3, arg4);
        if (loggedPerson.isAdmin()||loggedPerson.isManager()){
            Collection allRes=Reservation.getAllReservations(ctx.getDefaultConnection());
            env.pushValue("reservations",allRes);
        }else{
            Collection resList1=Reservation.getReservationsContainingPerson(ctx.getDefaultConnection(),loggedPerson.getId(),true);;
            env.pushValue("reservations",resList1);    
        }
        Collection resList2=Reservation.getReservationsReservedBy(ctx.getDefaultConnection(),loggedPerson.getId(),true);
        env.pushValue("reservationsByMe",resList2);
        env.pushValue("today",new Date());
        
    }
    
    protected boolean executeWebMethod(Context ctx, String method)
            throws Exception {
        ArgumentSource as=ctx.getArgumentSource();
        if (method.equals("reservationEdit")){
            reservationForm.setReservationId(as.getInteger("reservationId"));
        }
        else if (method.equals("reservationDelete")){
            try{
                Reservation.deleteReservation(ctx.getDefaultConnection(),as.getInteger("reservationId"));
                printToMessage("Rezervace smazana");
				EventLogger.logEvent(ctx,new LoggedEvent(LoggedEvent.EVENT_TYPE_DELETE_RESERVATION,ctx.getRequest()),loggedPerson);
				redirectToShowMessage(ctx);
            }
            catch (SQLException e){
                printToAlert("Chyba: rezervaci se nepodarilo smazat");
            }
        }
        else if (method.equals("cancelReservation")){
            this.reservationForm.setReservationId(null);
        }
        return super.executeWebMethod(ctx, method);
    }
}
