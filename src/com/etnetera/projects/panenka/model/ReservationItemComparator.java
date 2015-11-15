package com.etnetera.projects.panenka.model;

import java.sql.Date;
import java.util.Comparator;
/* Created on 13.11.2004 by Jiri Stepan*/

/**
 * Porovnani dvou rezervacnich itemu.
 * @author Jiri Stepan
 */
public class ReservationItemComparator implements Comparator {
    Date memberPriorityDate;
    Date compareForDate;
    
    public ReservationItemComparator(Date compareForDate, Date memberPriorityDate){
        this.compareForDate=compareForDate;
        this.memberPriorityDate=memberPriorityDate;
        
    }
    public int compare(Object arg0, Object arg1) {
        ReservationItem r1=(ReservationItem)arg0;
        ReservationItem r2=(ReservationItem)arg1;
        
        int result=0; //r1 < r2;

        //r2 je po datu rezervace a r1 nikoliv
        if ((r1.getReservationDate().before(memberPriorityDate)||r1.getReservationDate().equals(memberPriorityDate))
                &&(r2.getReservationDate().after(memberPriorityDate))){
            return -1;
        }

        //r1 je po datu rezervace a r2 nikoliv
        if ((r2.getReservationDate().before(memberPriorityDate)||r2.getReservationDate().equals(memberPriorityDate))
                &&(r1.getReservationDate().after(memberPriorityDate))){
            return +1;
        }

        //oba vcas -> clen ma prednost
        int p1=r1.getPerson().getPriority();
        int p2=r2.getPerson().getPriority();
        if (p1>p2) return -1;
        if (p1<p2) return 1;
 
        //oba se prihlasili pred nebo po terminu a maji stejnou clenskou pozici
        //rozhoduje datum prihlaseni
        result=r1.getReservationDate().compareTo(r2.getReservationDate());
        
        //pokud je shodne rozhoduje id - nesmi se totiz nikdy vratit nula kvuli TreeSet
        if (result==0) result=r1.getReservationId()<r2.getReservationId()?-1:+1;
        
        return result;
    }

}
