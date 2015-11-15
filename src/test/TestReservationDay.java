package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;

import com.etnetera.projects.panenka.model.DayModel;
import com.etnetera.projects.panenka.model.DayReservationData;
import com.etnetera.projects.panenka.model.ReservationItem;

/* Created on 13.11.2004 by Jiri Stepan*/

/**
 *
 * @author Jiri Stepan
 */
public class TestReservationDay {

    public static void main(String[] args) {
        DayReservationData dayData = null;
        Connection connection = null;
        try {
            // Load the JDBC driver
            String driverName = "org.gjt.mm.mysql.Driver"; // MySQL MM JDBC driver
            Class.forName(driverName);
        
            // Create a connection to the database
            String serverName = "127.0.0.1";
            String mydatabase = "panenka";
            String paramStr="?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=windows-1250";
            String url = "jdbc:mysql://" + serverName +  "/" + mydatabase+paramStr; // a JDBC url
            String username = "root";
            String password = "root";
            System.out.println("Conecting to:"+url);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.err.println("ERRROR: class not found");
        } catch (SQLException e) {
            System.err.println("ERRROR: can't connect");
        }
        
        DayModel day=new DayModel(29,11,2004);
        try {
            dayData=new DayReservationData(connection,day);
            day.setDayData(dayData);
        } catch (SQLException e1) {
            System.err.println("ERRROR: problem reading database");
            e1.printStackTrace(System.err);
        }
        
        Iterator resList=dayData.getReservationList().iterator();
        while (resList.hasNext()){
            ReservationItem resItem=(ReservationItem)resList.next();
            System.out.println("["+resItem.getReservationDate().toLocaleString()+"] "+resItem.getPerson().getLongName());
        }
    }
}
