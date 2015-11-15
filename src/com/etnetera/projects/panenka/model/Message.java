package com.etnetera.projects.panenka.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/* Created on 20.11.2004 by Jiri Stepan*/

/**
 * Jednoduchy model pro praci s message board
 * @author Jiri Stepan
 */
public class Message {
    private int id;
    private Date date;
    private String message;
    private String title;
    boolean published;
    int categoryId;
    int authorId;
    
    public Message(Connection conn, int id){
        readData(conn,id);
    }
    
    private Message readData(Connection conn, int id){
        
		try {
            PreparedStatement ps=conn.prepareStatement("SELECT b.* from board b WHERE b.id=?");
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                this.setId(rs.getInt("id"));
                this.setDate(rs.getDate("date"));
                this.setMessage(rs.getString("message"));
                this.setTitle(rs.getString("title"));
                this.setAuthorId(rs.getInt("author"));
                this.setCategoryId(rs.getInt("category"));
            }
            else return null;
        } catch (SQLException e) {
            return null;
        }
        return this;
    }
    
    
    public int getAuthorId() {
        return authorId;
    }
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isPublished() {
        return published;
    }
    public void setPublished(boolean published) {
        this.published = published;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
