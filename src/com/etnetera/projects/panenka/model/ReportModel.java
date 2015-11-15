package com.etnetera.projects.panenka.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

/* Created on 14.11.2004 by Jiri Stepan*/

/**
 * 
 * @author Jiri Stepan
 */
public class ReportModel {
    
    public class ReportItem{
        Date date;
        Collection reservations;
        
        public Date getDate() {
            return date;
        }
        public void setDate(Date date) {
            this.date = date;
        }
        public Collection getReservations() {
            return reservations;
        }
        public void setReservations(Collection resrevations) {
            this.reservations = resrevations;
        }
        public String getShortDate(){
            return ReportModel.getShortDate(date);
        }

        
    }
    private Date from_date;
    private Date to_date;
    private ArrayList allDays;
    
    public ReportModel(Connection conn, Date from, Date to){
        this.from_date=from;
        this.to_date=to;
        this.allDays=new ArrayList();
        this.constructReport(conn);
    }
    
    /**
     * @param conn
     */
    private void constructReport(Connection conn) {
        GregorianCalendar cal=new GregorianCalendar();
        cal.setTime(from_date);
        //pro vsechny dny vytvorime collections
        for (;!cal.getTime().after(to_date);cal.add(Calendar.DAY_OF_MONTH,1)){
            DayModel day=new DayModel(cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));
            DayReservationData dayData;
            try {
                dayData = new DayReservationData(conn,day);
            } catch (SQLException e) {
                e.printStackTrace(System.err);
                return;
            }
            day.setDayData(dayData);
            ReportItem ri=new ReportItem();
            ri.setDate(new Date(cal.getTimeInMillis()));
            ri.setReservations(dayData.getReservationList());
            allDays.add(ri);
        }
    }

    public Collection getAllDays(){
        return allDays;
    }
    
    /** vrati strukturu dle jmen */
    public Collection getAllPersons(){
        //sestavime seznam osob
        ArrayList pers=new ArrayList();
        Iterator it=allDays.iterator();
        while (it.hasNext()){
            Iterator it_res=((ReportItem)it.next()).getReservations().iterator();
            while (it_res.hasNext()){
                ReservationItem ri=(ReservationItem)it_res.next();
                Person p=ri.getPerson();
                if (!pers.contains(p)) pers.add(p);
            }
        }
        
        //nyni pro kazdy jednotlivy termin zjistime zda na nej osoba jede a v jakem je stavu
        ArrayList out=new ArrayList(pers.size());
        it=pers.iterator();
        int person_index=0, day_index=0;
        while (it.hasNext()){
            Person p=(Person)it.next();
            Iterator days=allDays.iterator();
            int[] statuses=new int[allDays.size()];
            day_index=0;
            while (days.hasNext()){
                ReportItem rpi=(ReportItem)days.next();
                Iterator res_it=rpi.getReservations().iterator();
                statuses[day_index]=-1; 
                while (res_it.hasNext()){
                    ReservationItem ri=(ReservationItem)res_it.next();
                    Person p1=ri.getPerson();
                    if (p.id==p1.id){
                        statuses[day_index]=ri.getStatus();
                        break;
                    }
                }
                day_index++;
            }
            HashMap item=new HashMap();
            item.put("person",p);
            item.put("days",statuses);
            out.add(item);
            person_index++;
        }
        
        return out;
    }
    
    
    public static String getShortDate(Date date){
        return date.getDate()+"."+(date.getMonth()+1)+"."+new Integer(date.getYear()+1900).toString().substring(2,4);
    }
}
