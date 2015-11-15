/*
 * Created on 9.12.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.etnetera.projects.panenka.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.zweistein.wbeans.ArgumentSource;
import org.zweistein.wbeans.Context;

/**
 * @author Jiri Stepan
 *
 */
public class Reservation {

	/* stavy rezervaci pri ruznych kolizich */
	public static final int OK=0;
	public static final int FULL=1; //termin nebo jaho cast je plna - prihlaska vzata jako nahradnik
	public static final int USER_DUPLICITY=2; //jeden nebo vice uzivatelu jsou jiz v tomto terminu prihlaseni - nelze provest
    public static final int BAD_DATE = 3; //spatne zadana data
	
	private int id;
	private java.util.Date dateFrom;
	private java.util.Date dateTo;
	private java.util.Date dateReservation;
	private int reservedById;
	private Person reservedBy;
	private String note;
	private boolean deleted;
	private ArrayList persons;
	private ArrayList memberIdList;
	private ArrayList nonMemberIdList;
	private ArrayList members;
	private ArrayList nonMembers;


	public static Reservation createNew(Connection conn) throws SQLException{
		PreparedStatement pst=conn.prepareStatement("INSERT INTO reservation (id,date_reservation) values(NULL, NOW())");	
		pst.execute();
		pst.close();
		pst=conn.prepareStatement("SELECT * FROM reservation WHERE id IS NULL");
		ResultSet rs=pst.executeQuery();
		if (rs.next()) return new Reservation(conn,rs);
		pst.close();
		return null;
	}

	public static Collection getAllReservations(Connection conn) throws SQLException{
	    long now=new java.util.Date().getTime();
	    return getReservations(conn,new Date(now-24*3600*1000),null);
	}
	
	/** list of reservations bertwen two dates */
	public static Collection getReservations(Connection conn,java.util.Date dateFrom, java.util.Date dateTo) throws SQLException{
	    String query="SELECT r.id from reservation r WHERE r.deleted=0 AND r.date_from >= ? ";
	    if (dateTo!=null) query+=" AND r.date_to <= ? ";
	    query+="ORDER BY date_from ASC";
	    
		PreparedStatement pst=conn.prepareStatement(query);
		pst.setDate(1,new Date(dateFrom.getTime()));
		if (dateTo!=null )pst.setDate(2,new Date(dateTo.getTime()));
		ResultSet rs=pst.executeQuery();
	    ArrayList out=new ArrayList(10);
		while (rs.next()){
			Reservation res=new Reservation(conn,rs.getInt("id"));
			out.add(res);
		} 
		return out;
	}
	
	/** returns list of reservations created by some person */
	public static Collection getReservationsReservedBy(Connection conn, int person,boolean future_only) throws SQLException{
	    ArrayList out=new ArrayList();
	    PreparedStatement ps=conn.prepareStatement("SELECT * FROM reservation WHERE deleted=0 AND reserved_by=? AND date_from > ?");
        ps.setInt(1,person);
        if (future_only) ps.setDate(2,new Date(new java.util.Date().getTime()));
        else ps.setDate(2,new Date(0,1,1));
        ResultSet rs=ps.executeQuery();
        while (rs.next()){
            Reservation res=new Reservation();
            res.readData(conn,rs);
            out.add(res);
        }
        return out;
	}
	
	/** returns list of reservations containing some person or created by this person*/
	public static Collection getReservationsContainingPerson(Connection conn, int personId, boolean future_only) throws SQLException{
	    ArrayList out=new ArrayList();
	    PreparedStatement ps=conn.prepareStatement("SELECT DISTINCT r.* FROM reservation r left join aux_reservation_person rp ON (rp.reservation_id=r.id) WHERE r.deleted=0 AND rp.person_id=? AND date_from > ? ORDER BY date_from");
        ps.setInt(1,personId);
        if (future_only) ps.setDate(2,new Date(new java.util.Date().getTime()));
        else ps.setDate(2,new Date(0,1,1));
        ResultSet rs=ps.executeQuery();
        while (rs.next()){
            Reservation res=new Reservation();
            res.readData(conn,rs);
            out.add(res);
        }
        return out;
	}

	public Reservation(){
		persons = new ArrayList(10);
		memberIdList=new ArrayList(10);
		nonMemberIdList=new ArrayList(10);
		members=new ArrayList(10);
		nonMembers=new ArrayList(10);
	}
	
	/** nacte rezercaci dle id - vrati i tu, ktera ma priznak deleted nastaven na 1 */
	public Reservation(Connection conn,int id) throws SQLException{
		this();
		PreparedStatement pst=conn.prepareStatement("SELECT * FROM reservation WHERE id=?");	
		pst.setInt(1,id);
		ResultSet rs=pst.executeQuery();
		if (rs.next()){
			this.readData(conn, rs);
		}
	}
	
	public Reservation(Connection conn,ResultSet rs) throws SQLException{
		this();
		this.readData(conn,rs);
	}
	
	public void readData(Connection conn, ResultSet rs) throws SQLException{
		this.setId(rs.getInt("id"));
		this.setDateFrom(rs.getDate("date_from"));
		this.setDateTo(rs.getDate("date_to"));
		this.setDateReservation(rs.getDate("date_reservation"));
		this.setReservedById(rs.getInt("reserved_by"));
		this.setNote(rs.getString("note"));
		this.setDeleted(rs.getBoolean("deleted"));

		PreparedStatement pst=conn.prepareStatement("SELECT p.* from person p LEFT JOIN aux_reservation_person rp ON p.id=rp.person_id WHERE rp.reservation_id=? ORDER BY surname");
		pst.setInt(1,this.getId());
		rs=pst.executeQuery();
		while (rs.next()){
			Person p=new Person(conn,rs,false);
			this.persons.add(p);
			if (p.isMember()){
				this.memberIdList.add(new Integer(p.getId()));
				this.members.add(p);
			}
			else {
				this.nonMemberIdList.add(new Integer(p.getId()));
				this.nonMembers.add(p);
			}
			
		}
		
		//read information about person who made the reservation
		reservedBy=new Person(conn,reservedById,false);
	}


	
	public void readFromArgumentSource(Context ctx, Person loggedPerson) throws SQLException{
		ArgumentSource as=ctx.getArgumentSource();
		this.setDateFrom(as.getDate("date_from"));
		this.setDateTo(as.getDate("date_to"));
		this.setNote(as.getString("note"));
		
		//sestavime pole lidi, kteri jedou
		int memNum=as.sizeOf("member");
		memberIdList=new ArrayList(memNum);
		for (int i=0;i<memNum; i++){
			memberIdList.add(as.getInteger("member",i));
		}

		//sestavime pole neclenu, kteri jedou
		int nonMemNum=as.sizeOf("nonmember");
		nonMemberIdList=new ArrayList(nonMemNum);
		for (int i=0;i<nonMemNum; i++){
			nonMemberIdList.add(as.getInteger("nonmember",i));
		}
		
		
	}

	/**
	 * @param connection
	 */
	public void updateDB(Connection connection) throws SQLException{
		PreparedStatement pst=connection.prepareStatement("UPDATE reservation SET date_from=?, date_to=?, reserved_by=?, note=? WHERE id=?");
		pst.setDate(1,new Date(getDateFrom().getTime()));
		pst.setDate(2,new Date(getDateTo().getTime()));
		pst.setInt(3,getReservedById());
		pst.setString(4,getNote());
		pst.setInt(5,getId());
		pst.execute();
		pst.close();
		
		pst=connection.prepareStatement("DELETE FROM aux_reservation_person WHERE reservation_id=?");
		pst.setInt(1,getId());
		pst.execute();
		pst.close();
		
		for (int i=0; i<memberIdList.size();i++){
			Integer iid=(Integer)memberIdList.get(i);
			if (iid!=null){			
				pst=connection.prepareStatement("INSERT INTO aux_reservation_person values(?,?)");
				pst.setInt(1,iid.intValue());
				pst.setInt(2,getId());
				pst.execute();
			}
		}
		for (int i=0; i<nonMemberIdList.size();i++){
			Integer iid=(Integer)nonMemberIdList.get(i);
			if (iid!=null){			
				pst=connection.prepareStatement("INSERT INTO aux_reservation_person values(?,?)");
				pst.setInt(1,iid.intValue());
				pst.setInt(2,getId());
				pst.execute();
			}
		}
	}

	/**
	 * Provede nasledujici testy:
	 * - overi zda pro nektery den neni prilis plno
	 * - overi zda zapsani lide jiz nejsou na nektery den zapsani
	 */
	public int testReservation(Connection connection) {
	    if ((dateFrom==null)||(dateTo==null)) return Reservation.BAD_DATE;
	    if (!dateTo.after(dateFrom)) return Reservation.BAD_DATE;
	    
		ReservationCalendarModel cal=new ReservationCalendarModel(connection);
		Collection dayCol=cal.getDaysBetweenDates(this.dateFrom,this.dateTo);
		DayModel day;
		DayReservationData dayData;
		int reservationNumber=this.getMemberIdList().size()+this.getNonMemberIdList().size();

		for (Iterator i=dayCol.iterator();i.hasNext();){
			day=(DayModel)i.next();
			dayData=(DayReservationData)day.getDayData();
			//test number of people
			if (reservationNumber+dayData.getTotalPersonNum() > dayData.getCapacity()){
				return Reservation.FULL;
			}
			
			//test duplicity
			Collection list=dayData.getReservationList();
			Iterator it=this.memberIdList.iterator();
			while (it.hasNext()){
			    Integer tmpId=(Integer)it.next();
			    Iterator it1=list.iterator();
			    while (it1.hasNext()){
			        ReservationItem ri=(ReservationItem)it1.next();
			        if ((ri.getPerson().getId()==tmpId.intValue())&&(ri.getReservationId()!=this.id))
			            return Reservation.USER_DUPLICITY;
			    }
			}
			it=this.nonMemberIdList.iterator();
			while (it.hasNext()){
			    Integer tmpId=(Integer)it.next();
			    Iterator it1=list.iterator();
			    while (it1.hasNext()){
			        ReservationItem ri=(ReservationItem)it1.next();
			        if ((ri.getPerson().getId()==tmpId.intValue())&&(ri.getReservationId()!=this.id))
			            return Reservation.USER_DUPLICITY;
			    }
			}
		}		
		return Reservation.OK;
	}

    /**
     * @param conn
     * @param integer
     */
    public static void deleteReservation(Connection conn, Integer id) throws SQLException {
        PreparedStatement ps=conn.prepareStatement("UPDATE reservation SET deleted=1 WHERE id=?");
        ps.setInt(1,id.intValue());
        ps.execute();
        ps.close();
    }
	
	
	public ArrayList getMembers() {
		return members;
	}

	public ArrayList getNonMembers() {
		return nonMembers;
	}

    public Person getReservedBy() {
        return reservedBy;
    }

    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

	public java.util.Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(java.util.Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public java.util.Date getDateReservation() {
		return dateReservation;
	}

	public void setDateReservation(java.util.Date dateReservation) {
		this.dateReservation = dateReservation;
	}

	public java.util.Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(java.util.Date dateTo) {
		this.dateTo = dateTo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Collection getPersons() {
		return persons;
	}


	public int getReservedById() {
		return reservedById;
	}

	public void setReservedById(int reserved_by) {
		this.reservedById = reserved_by;
	}
	
	public int getNumDays(){
		return (int)((dateTo.getTime()-dateFrom.getTime())/1000/3600/24);
	}

	public Collection getMemberIdList() {
		return memberIdList;
	}

	public Collection getNonMemberIdList() {
		return nonMemberIdList;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

    
}
