package com.etnetera.projects.panenka.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Popisuje zajezd nebo jiné omezeni chaty behem ktereho je zmenena kapacita.
 * 
 * @author jiri
 */

public class DayConstraint {
	private static final int DEFAULT_CAPACITY=50;
	private static final int DEFAULT_NONMEMBER_CAPACITY=40;
	
	private int id;
	private Date fromDate;
	private Date toDate;
	private int capacity;
	private int nonmemberCapacity;
	private Date memberPriorityDeadline;
	private String note;
	private int managerId;
	
	private Person manager;

    /* static method */
	public static void createNew(Connection conn){
		try {
			PreparedStatement ps=conn.prepareStatement("INSERT INTO day_constraints SET from_date='2000-01-01' , note='..new..'");
			ps.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void delete(Connection conn, int id) {
		try {
            PreparedStatement ps=conn.prepareStatement("DELETE from day_constraints WHERE id=?");
			ps.setInt(1,id);
			ps.execute();
             
        } catch (SQLException e) {
			e.printStackTrace();
        }				
	}

	
	public static Collection getList(Connection conn){
		ArrayList out=new ArrayList();
		try {
            PreparedStatement ps=conn.prepareStatement("SELECT * from day_constraints ORDER by from_date ASC");
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
				DayConstraint dc=new DayConstraint(rs);
				out.add(dc);
			}
            
        } catch (SQLException e) {
			e.printStackTrace();
            return null;
        }
        return out;
		
	}

	
	public DayConstraint(Connection conn, int id){
		readData(conn, id);
	}
	
	public DayConstraint(ResultSet rs){
		try {
			readData(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    private DayConstraint readData(Connection conn, int id){
		try {
            PreparedStatement ps=conn.prepareStatement("SELECT * from day_constraints WHERE id=?");
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
			if (rs.next())
				readData(rs);
			else
				return null;
					
        } catch (SQLException e) {
			e.printStackTrace();
            return null;
        }
        return this;
    }
	
	private DayConstraint readData(ResultSet rs) throws SQLException{
        this.setId(rs.getInt("id"));
		this.setFromDate(rs.getDate("from_date"));
		this.setToDate(rs.getDate("to_date"));
		this.setCapacity(rs.getInt("capacity"));
		this.setNonmemberCapacity(rs.getInt("nonmember_capacity"));
		this.setMemberPriorityDeadline(rs.getDate("member_priority_deadline"));
		this.setNote(rs.getString("note"));
		this.setManagerId(rs.getInt("manager_id"));
		
		return this;
		
	}

	public void updateDB(Connection conn) {
		try{
			String query="UPDATE day_constraints SET " +
					"from_date = ? , to_date = ? , " +
					"manager_id = ? , note=? , " +
					"capacity = ? , nonmember_capacity = ? ," +
					"member_priority_deadline = ? " +
					"WHERE id = ?";
			PreparedStatement ps=conn.prepareStatement(query);
			ps.setDate(1,getFromDate());
			ps.setDate(2,getToDate());
			ps.setInt(3,getManagerId());
			ps.setString(4,getNote());
			ps.setInt(5,getCapacity());
			ps.setInt(6,getNonmemberCapacity());
			ps.setDate(7,getMemberPriorityDeadline());
			
			ps.setInt(8,getId());
			ps.execute();
			ps.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
	}

	
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Person getManager() {
		return manager;
	}
	public void setManager(Person manager) {
		this.manager = manager;
	}
	public Date getMemberPriorityDeadline() {
		return memberPriorityDeadline;
	}
	public void setMemberPriorityDeadline(Date memberPriorityDeadline) {
		this.memberPriorityDeadline = memberPriorityDeadline;
	}
	
	public void setMemberPriorityDeadline(java.util.Date memberPriorityDeadline) {
		if (memberPriorityDeadline!=null)
			this.setMemberPriorityDeadline(new Date(memberPriorityDeadline.getTime()));
		else
			this.setMemberPriorityDeadline(null);
	}

	public int getNonmemberCapacity() {
		return nonmemberCapacity;
	}
	public void setNonmemberCapacity(int nonmemberCapacity) {
		this.nonmemberCapacity = nonmemberCapacity;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	
	public void setFromDate(java.util.Date date){
		if (date!=null)
			this.setFromDate(new Date(date.getTime()));
		else
			this.setFromDate(null);
	}
	
	public void setToDate(java.util.Date date){
		if (date!=null)
		   this.setToDate(new Date(date.getTime()));
		else 
			this.setToDate(null);
	}
	
	public void print(){
		System.out.println(this.toString());
	}

	public String toString() {
		return ("("+id+")"+fromDate+"-"+toDate+"/"+capacity+"/"+nonmemberCapacity+"["+managerId+"]");
	}


}
