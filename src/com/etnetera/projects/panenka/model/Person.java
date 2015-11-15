/*
 * Created on 7.12.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.etnetera.projects.panenka.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.zweistein.wbeans.ArgumentSource;

/**
 * @author Jiri Stepan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Person {
	protected int id;
	protected String login;
	protected String password;
	protected String name;
	protected String surname;
	protected String apendix; //obvykle 'ml.'
	protected String email;
	protected String phone1;
	protected String phone2;
	protected String address;
	protected boolean member;
	protected boolean admin;
	protected boolean manager;
	protected boolean child;
	protected int granted_by;
	protected boolean isValid;
	private ArrayList canSubscribeList;
	private ArrayList grantNonMemberList;
	private ArrayList canBeSubscribedList;
	private Person granterPerson;

	public static Person createNew(Connection conn) throws SQLException{
		PreparedStatement pst=conn.prepareStatement("INSERT INTO person (id) values(NULL)");	
		pst.execute();
		pst.close();
		pst=conn.prepareStatement("SELECT * FROM person WHERE id IS NULL");
		ResultSet rs=pst.executeQuery();
		if (rs.next()) return new Person(conn,rs,true);
		pst.close();
		return null;
	}
	

	public Person(Connection conn, int id,boolean readLinks) throws SQLException{
		PreparedStatement pst=conn.prepareStatement("SELECT * FROM person WHERE id=?");
		pst.setInt(1,id);
		ResultSet rs=pst.executeQuery();
		if (rs.next()){
			this.isValid = true;
			readData(conn,rs,readLinks);
		}
		else this.isValid=false;			
	}
	
	public Person(int id){
		this.setId(id);
	}
	
	public Person(Connection conn, int id) throws SQLException{
		this(conn,id,true);
	}

	public Person(Connection conn,String login, String password) throws SQLException{
		PreparedStatement pst=conn.prepareStatement("SELECT * FROM person WHERE login=? AND password=?");
		pst.setString(1,login);
		pst.setString(2,password);
		ResultSet rs=pst.executeQuery();
		if (rs.next()){
			this.isValid = true;
			readData(conn,rs,false);
		}
		else this.isValid=false;
	}

	public Person(Connection conn, ResultSet rs) throws SQLException{
		this(conn,rs,true);
	}	
	
	public Person(Connection conn,ResultSet rs, boolean readLinks) throws SQLException{
		this.isValid=true;
		this.readData(conn,rs,readLinks);
	}
	protected void readData(Connection conn,ResultSet rs, boolean readLinks) throws SQLException{
		this.setId(rs.getInt("id"));
		this.setLogin(rs.getString("login"));
		this.setPassword(rs.getString("password"));
		this.setName(rs.getString("name"));
		this.setSurname(rs.getString("surname"));
		this.setApendix(rs.getString("apendix"));
		this.setEmail(rs.getString("email"));
		this.setPhone1(rs.getString("phone1"));
		this.setPhone2(rs.getString("phone2"));
		this.setAddress(rs.getString("address"));
		this.setMember(rs.getBoolean("member"));
		this.setAdmin(rs.getBoolean("admin"));
		this.setManager(rs.getBoolean("manager"));
		this.setChild(rs.getBoolean("child"));
		
		if (!isMember()){
			this.setGranted_by(rs.getInt("granted_by"));
		} 
		else {
			this.setGranted_by(-1);
		}
					
		if (readLinks){
			if (this.isMember()){
					
				/* nacteni tech, ktere muze zapisovat */
			    String query;
			    if (this.isManager()) 
			        query="SELECT * FROM person WHERE member=1 OR child=1 ORDER BY surname";
			    else
			        query="SELECT person.* FROM person LEFT JOIN can_subscribe ON person.id=can_subscribe.subject WHERE subscriber=? ORDER BY surname";
			        
				PreparedStatement pst=conn.prepareStatement(query);
				if (!this.isManager())pst.setInt(1,this.getId());
				ResultSet rs1=pst.executeQuery();
				this.canSubscribeList=new ArrayList(10);
				String idString="(";
				while (rs1.next()){
					Person p=new Person(conn,rs1,false);
					if (p.isValid) {
						this.canSubscribeList.add(p);
						idString+=p.id+",";
					}
				}
				
				pst.close();
				rs1.close();
				idString+=this.id+")";
				
				/* doplnime rodinu 
				try{
					PreparedStatement pst2=conn.prepareStatement("SELECT * FROM person WHERE surname like ? AND id<>?");		
					pst2.setString(1,this.getSurname().substring(0,4)+"%");
					pst2.setInt(2,this.getId());
					ResultSet rs2=pst2.executeQuery();
					while (rs2.next()){
						Person p=new Person(conn,rs2,false);
						if (p.isValid&&(!this.canSubscribeList.contains(p))) {
							this.canSubscribeList.add(p);
							idString+=p.id+",";
						}
					}
					
					pst2.close();
					rs2.close();
				}
				catch(IndexOutOfBoundsException e){}
				finally{
				    
				} */
				
				/* nacteni techneclenu, ktere privedl */
				pst=conn.prepareStatement("SELECT person.* FROM person WHERE member=0 AND granted_by in "+idString);
				rs1=pst.executeQuery();
				this.grantNonMemberList=new ArrayList(10);
				while (rs1.next()){
					Person p=new Person(conn,rs1,false);
					if (p.isValid) this.grantNonMemberList.add(p);
				}
				rs1.close();
				pst.close();
				
				/* nacteni tech, kteri mohou zapsat jeho - nepocitame rodinu*/
				pst=conn.prepareStatement("SELECT person.* FROM person LEFT JOIN can_subscribe ON person.id=can_subscribe.subscriber WHERE subject=?;");
				pst.setInt(1,this.getId());
				rs1=pst.executeQuery();
				this.canBeSubscribedList=new ArrayList(10);
				while (rs1.next()){
					Person p=new Person(conn,rs1,false);
					if (p.isValid) {
						this.canBeSubscribedList.add(p);
					}
				}
				pst.close();
				rs1.close();
			}
			else{
				/* nacteni osoby ktere privedla neclena do oddilu */
				this.granterPerson=new Person(conn,this.granted_by,false);
			}			
			
		}
	}
	
	
	public void addNonMember(Person nonMember){
		this.grantNonMemberList.add(nonMember);
	}

	/* vrati seznam lidi, ktere tento muze zapisovat */
	public Collection getCanSubscribeList(){
		return this.canSubscribeList;
	}

	//slouzi pro razeni 
	public int getPriority(){
	    if (isMember()) return 2;
	    if (isChild()) return 2;
	    return 0;
	}
	
	/***** getter/setter ******/
	public Integer getIdInteger(){
		return new Integer(id);
	}

	public String getLongName(){
	    String out=this.getName()+" "+this.getSurname();
	    if ((apendix!=null)&&(apendix.length()>0)){
	        out+="("+apendix+")";
	    }
	    return out;
	}
	        
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getGranted_by() {
		return granted_by;
	}

	public void setGranted_by(int granted_by) {
		this.granted_by = granted_by;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	public boolean isMember() {
		return member;
	}

	public void setMember(boolean member) {
		this.member = member;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public boolean isValid() {
		return isValid;
	}
	
	public ArrayList getGrantNonMemberList() {
		return this.grantNonMemberList;
	}

	public ArrayList getCanBeSubscribedList() {
		return canBeSubscribedList;
	}

	public Object getGranterPerson() {
		return granterPerson;
	}


	public void readFromArgumentSource(ArgumentSource as) {
		this.setAddress(as.getString("address"));
		this.setAdmin(as.getBool("admin")); //TODO: check if user can do this
		this.setEmail(as.getString("email"));
		//this.setLogin(as.getString("login")); // TODO: should it be possible?
		this.setManager(as.getBool("manager")); //TODO: check if user can do this
		this.setMember(as.getBool("member")); //TODO: only for new Person.
		this.setChild(as.getBool("child"));
		this.setName(as.getString("name"));
		this.setPassword(as.getString("password")); //TODO: confimation with second PW.
		this.setPhone1(as.getString("phone1"));
		this.setPhone2(as.getString("phone2"));
		this.setSurname(as.getString("surname"));
		this.setApendix(as.getString("apendix"));
		
		//read can be subscribed by list
		int len=as.sizeOf("canBeSubscribed");
		this.canBeSubscribedList=new ArrayList(len);
		for (int i=0;i<len;i++){
			Person p=new Person(as.getInt("canBeSubscribed",i));
			this.canBeSubscribedList.add(p);
		}
	}

	public void updateDB(Connection connection) throws SQLException{
		PreparedStatement pst=connection.prepareStatement("UPDATE person SET password=?, name=?, surname=?, apendix=?, email=?, phone1=?, phone2=?, address=?, granted_by=?, member=?, manager=?, admin=?, child=?  WHERE id=?");
		pst.setString(1,getPassword());
		pst.setString(2,getName());
		pst.setString(3,getSurname());
		pst.setString(4,getApendix());
		pst.setString(5,getEmail());
		pst.setString(6,getPhone1());
		pst.setString(7,getPhone2());
		pst.setString(8,getAddress());
		pst.setInt(9,getGranted_by());
		pst.setBoolean(10,isMember());
		pst.setBoolean(11,isManager());
		pst.setBoolean(12,isAdmin());
		pst.setBoolean(13,isChild());
		
		pst.setInt(14,getId());
		pst.execute();
		pst.close();
		//update can_subscribe table
		pst=connection.prepareStatement("DELETE FROM can_subscribe WHERE subject=?");
		pst.setInt(1,getId());
		pst.execute();
		pst.close();
		
		if (this.canBeSubscribedList!=null){
			for (int i=0; i<this.canBeSubscribedList.size();i++){
				pst=connection.prepareStatement("INSERT INTO can_subscribe SET subscriber=?, subject=?");
				pst.setInt(1,((Person)this.canBeSubscribedList.get(i)).getId());
				pst.setInt(2,getId());
				pst.execute();
				pst.close();
			}	

		}
	}

	public static ResultSet getAllMembers(Connection conn) throws SQLException {
		PreparedStatement pst=conn.prepareStatement("SELECT id, name, surname FROM person WHERE member=1");
		ResultSet rs=pst.executeQuery();
		return rs;	
	}
	
	public int[] getCanBeSubscribedIdsList(){
		int len=this.canBeSubscribedList.size();
		int[] out=new int[len];
		for (int i=0;i<len;i++){
			out[i]=((Person)this.canBeSubscribedList.get(i)).getId();
		}
		return out;
	}

	public boolean isChildOrMemeber(){    
	    return isChild()||isMember();
	}

	/** Test rovnosti - musi si odpovidat 
	 * IDcka osob. Je to hlavne pro vkladani do collection canSubscribe apod 
	 **/
	public boolean equals(Object obj) {
		if (obj instanceof Person) return id==((Person)obj).getId();
		else return super.equals(obj);
	}
	
	
	public String getApendix() {
		return apendix;
	}

	public void setApendix(String apendix) {
		this.apendix = apendix;
	}

	public boolean isChild() {
		return child;
	}
	public void setChild(boolean child) {
		this.child = child;
	}
	
}
