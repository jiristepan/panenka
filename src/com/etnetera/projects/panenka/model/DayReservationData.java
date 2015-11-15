package com.etnetera.projects.panenka.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TreeSet;


/**
 * Informace o danem dni. Shrnuje informace o
 * <ul>
 * <li>Rezervacich pro tento den - seznam clenu a neclenu</li>
 * <li>Omezenich pro prihlasovani -  kapacitu a deadline pro prihlaseni</li>
 * <ul>
 * 
 * DULEZITE: omezeni pro prihlasovani se nesmi casove prekryvat!!! 
 * 
 * @author Jiri Stepan
 */
public class DayReservationData {
	private Collection memberOrders;
	private Collection nonMemberOrders;
	private int capacity;
	private Date memberPriorityDate;
	private String note;
	private Date date;
	private static final int DEFAULT_CAPACITY = 50;
	
	public  static final int FULLLEVEL_EMPTY = 0;
	public  static final int FULLLEVEL_LOW = 1;
	public static final int FULLLEVEL_MEDIUM = 2;
	public static final int FULLLEVEL_HARD = 3;
	public static final int FULLLEVEL_FULL = 4;
	
	public DayReservationData(Connection conn, DayModel day) throws SQLException{
		this.memberOrders=new ArrayList(10);
		this.nonMemberOrders=new ArrayList(10);
		
		this.capacity=-1; //nutne proto abych pozdeji nastavil spravne defaultni hodnotu
		this.readDatabase(conn,day);
		this.date=new Date(day.getDate().getTime());
	}
	
	protected void readDatabase(Connection conn, DayModel day) throws SQLException{
		Date date=new Date(day.getYear()-1900, day.getMonth()-1,day.getDay()); //this can be substituted using this.date
		/* read day constraints */
		PreparedStatement pst=conn.prepareStatement("SELECT * FROM day_constraints WHERE from_date<=? AND to_date>=?");
		pst.setDate(1,date);
		pst.setDate(2,date);
		//reading only first line of the resultSet
		ResultSet rs=pst.executeQuery();
		if (rs.next()){
			this.setCapacity(rs.getInt("capacity"));
			this.setMemberPriorityDate(rs.getDate("member_priority_deadline"));
			this.setNote(rs.getString("note"));
		}
		//default values
		if (capacity==-1) this.setCapacity(DEFAULT_CAPACITY);
		if ((memberPriorityDate==null)||(memberPriorityDate.getTime() < (new java.util.Date(0,1,1).getTime()))) 
				this.setMemberPriorityDate(findMemberPriorityDate(date));
		
		/* read reservations */
		pst=conn.prepareStatement("SELECT p.*, r.id  as reservation_id, r.date_reservation from person p, reservation r, aux_reservation_person rp WHERE r.deleted=0 AND rp.reservation_id=r.id AND rp.person_id=p.id AND r.date_from <= ? AND r.date_to > ?");
		
		pst.setDate(1,date);
		pst.setDate(2,date);
		rs=pst.executeQuery();
		while(rs.next()){
			Person person=new Person(conn,rs);
			Date reservation_date=rs.getDate("date_reservation");
			int reservation_id=rs.getInt("reservation_id");
			ReservationItem resItem=new ReservationItem(reservation_id,reservation_date,person);
			if (person.isMember())
				this.memberOrders.add(resItem);
			else
				this.nonMemberOrders.add(resItem);
		}
	}
	
	/**
	 * Pro dany den nalezene datum do kdy ma clen prioritu. Je to streda minuleho tydne.
	 */
	private Date findMemberPriorityDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int day_of_week=calendar.get(Calendar.DAY_OF_WEEK);
		int delta=3; //pocet dnu o ktere skocim zpet
		if (day_of_week==Calendar.SATURDAY) delta=3;
		else if (day_of_week==Calendar.FRIDAY) delta=2;
		else if (day_of_week==Calendar.SUNDAY) delta=4;
		else if (day_of_week==Calendar.MONDAY) delta=5;
		else if (day_of_week==Calendar.TUESDAY) delta=6;
		else if (day_of_week==Calendar.WEDNESDAY) delta=7;
		else if (day_of_week==Calendar.THURSDAY) delta=8;
		
		
		calendar.add(Calendar.DAY_OF_MONTH,-delta);
		Date result=new Date(calendar.getTimeInMillis());
		
		return result;
	}

	public Collection getMemberOrders(){
		return memberOrders;
	}
	
	//seznam id clenu prihlasenych na tento den
	public Collection getMembersIds(){
		ArrayList out=new ArrayList(memberOrders.size());
		for (Iterator i=memberOrders.iterator();i.hasNext();){
			ReservationItem order=(ReservationItem)i.next();
			Person p=order.getPerson();
			out.add(new Integer(p.getId()));
		}
		return out;
	}

	public Collection getNonMemberOrders(){
		return nonMemberOrders;
	}

	//seznam id neclenu prihlasenych na tento den
	public Collection getNonMembersIds(){
		ArrayList out=new ArrayList(nonMemberOrders.size());
		for (Iterator i=nonMemberOrders.iterator();i.hasNext();){
			ReservationItem order=(ReservationItem)i.next();
			Person p=(Person)order.getPerson();
			out.add(new Integer(p.getId()));
		}
		return out;
	}

	public int getNumMembers(){
		return memberOrders.size();
	}
	
	public int getNumNonMembers(){
		return nonMemberOrders.size();
	}
	
	public int getTotalPersonNum(){
		return getNumMembers()+getNumNonMembers();
	}
	public int getCapacity(){
		return capacity;	
	}
	
	public int getFullLevel(){
		int sum=(getNumMembers()+getNumNonMembers());
		float capacity=getCapacity();
		if (capacity <1) return FULLLEVEL_FULL;
		float ratio=sum/capacity;
		if (sum==0) return FULLLEVEL_EMPTY; //empty
		if (ratio <= 0.4) return FULLLEVEL_LOW;
		if (ratio <= 0.8) return FULLLEVEL_MEDIUM;
		if (capacity > sum) return FULLLEVEL_HARD;
		return FULLLEVEL_FULL; 		
	}

	/* return true if is possible to subscribe to the day */
	public boolean isBlocked(){
		java.util.Date today=new java.util.Date();
		if (this.date.before(today)) return true;
		//if (this.getFullLevel()==FULLLEVEL_FULL) return true;
		return false;
	}
	
	public boolean isMemberPriority(){
		java.util.Date today=new java.util.Date();
		return this.memberPriorityDate.after(today);
	}
	
	
	/** metoda vrati jednu serazenou collection obsahujici cleny i necleny podle
	 * data jejich zapisu a jejich priority. 
	 */
	public Collection getReservationList(){
	    ArrayList out=new ArrayList();
	    ReservationItemComparator comparator=new ReservationItemComparator(this.date,this.memberPriorityDate);
		TreeSet ts=new TreeSet(comparator);
		ts.addAll(memberOrders);
		ts.addAll(nonMemberOrders);
		
		Iterator i=ts.iterator();
		int ord=1;
		while (i.hasNext()){
		    ReservationItem ri=(ReservationItem)i.next();
		    //set status
		    if (ord>this.capacity) //je plno!
		       ri.setStatus(ReservationItem.RESERVATION_FAILED_FULL);
		    else {
		        if (ri.getReservationDate().after(this.memberPriorityDate)){
		            ri.setStatus(ReservationItem.RESERVATION_AFTER_DEADLINE);
		        }
		        else ri.setStatus(ReservationItem.RESERVATION_OK);
		    }
			out.add(ri);
		    ord++;
		}


	    return out;
	}
	
	/********** get/seters *******************/
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getMemberPriorityDate() {
		return memberPriorityDate;
	}
	public void setMemberPriorityDate(Date memberPriorityDate) {
		this.memberPriorityDate = memberPriorityDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}
