package com.etnetera.projects.panenka.model;

import java.sql.Date;

/**
 * Trida sdruzujici informaci o datu rezervace a cloveku, ktery ji provedl.
 * @author Jiri Stepan
 */
public class ReservationItem {
    public static final int RESERVATION_OK=1;
    public static final int RESERVATION_FAILED_FULL=2;
    public static final int RESERVATION_AFTER_DEADLINE=3;
    private Date reservationDate;
    private Person person;
    private int reservationId;
    private int status;
    
    
    /**
     * @param reservation_id
     * @param reservation_date
     * @param person2
     */
    public ReservationItem(int reservation_id, Date reservation_date, Person person2) {
        this.person=person2;
        this.reservationId=reservation_id;
        this.reservationDate=reservation_date;
    }
    
    public Date getReservationDate() {
        return reservationDate;
    }
    public void setReservationDate(Date date) {
        this.reservationDate = date;
    }
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }
    public int getReservationId() {
        return reservationId;
    }
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
