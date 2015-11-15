/*
 * Created on 25.12.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.etnetera.projects.panenka.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.zweistein.tfs.TemplateData;
import org.zweistein.tfs.parser.PartStatement;
import org.zweistein.wbeans.ArgumentSource;
import org.zweistein.wbeans.Context;
import org.zweistein.wbeans.tfs.WBeanEnvironment;

import com.etnetera.projects.panenka.model.DayConstraint;
import com.etnetera.projects.panenka.model.EventLogger;
import com.etnetera.projects.panenka.model.LoggedEvent;
import com.etnetera.projects.panenka.model.Person;

/**
 * @author Jiri Stepan
*/
public class PersonForm extends PersonView {
	protected boolean executeWebMethod(Context ctx, String method)
		throws Exception {
		ArgumentSource as=ctx.getArgumentSource();
		if (method.equals("updatePerson")){
		  if (loggedPerson.isAdmin()||loggedPerson.isManager()||(loggedPerson.getId()==personId.intValue())){
			((UsersComposite)this.parent).setDisplayMode(UsersComposite.DISPLAY_LIST);

			String action=as.getString("action");

			if (action==null) action="new";
			Person person=null;;
			if (action.equals("new")){
				person=Person.createNew(ctx.getDefaultConnection());
			}
			else if (action.equals("update")){
				person=new Person(ctx.getDefaultConnection(),this.personId.intValue());
			}
			else{
				throw new Exception("No action found in argument source");
			}
			person.readFromArgumentSource(as);
			
			if (!person.isMember()) person.setGranted_by(((UsersComposite)parent).getLoggedPerson().getId());
			
			person.updateDB(ctx.getDefaultConnection());	
		
			ctx.redirect(ctx.getAddress().getTopClass());
		  }
		  else { //neautorizace!!!
			EventLogger.logEvent(ctx,new LoggedEvent(LoggedEvent.EVENT_TYPE_UNAUTHORIZED,"Pokus o ulozeni nastaveni uzivatele "+person.getLongName()+"("+person.getId()+")"),loggedPerson);		  	
		  }
		  	
		}
		else if(method.equals("addNonMember")){
			if (loggedPerson.isAdmin()||loggedPerson.isManager()||(loggedPerson.getId()==personId.intValue())){
				
			    //TODO: presunout do tridy PERSON
				String name=as.getString("new_non_member_name");
				String surname=as.getString("new_non_member_surname");
				boolean isChild=as.getBool("new_non_member_ischild");
				if ((!name.equals(""))&&(!surname.equals(""))){
					Person p=Person.createNew(ctx.getDefaultConnection());
					p.setMember(false);
					p.setGranted_by(loggedPerson.getId());
					p.setName(name);
					p.setSurname(surname);
					//TODO: p.setChild(isChild);
					p.updateDB(ctx.getDefaultConnection());
					person.addNonMember(p);
				}
				String redir="MainPage$usersComposite-editPerson?personId="+loggedPerson.getId()+"#newnonmember";
				ctx.redirect(redir);
			}
			else { //neautorizace!!!
				EventLogger.logEvent(ctx,new LoggedEvent(LoggedEvent.EVENT_TYPE_UNAUTHORIZED,"Pokus o ulozeni nastaveni uzivatele "+person.getLongName()+"("+person.getId()+")"),loggedPerson);		  	
			}
		}
		return super.executeWebMethod(ctx, method);
	}

	protected void prepareEnviroment(
		Context ctx,
		WBeanEnvironment env,
		Map parameters,
		TemplateData tpl,
		PartStatement part)
		throws Exception {
		
		if ((loggedPerson.getId()==person.getId())
		        ||(loggedPerson.isAdmin())
		        ||(loggedPerson.isManager())){
			env.pushValue("authorized",Boolean.TRUE);	
		}
		else{
			env.pushValue("authorized",Boolean.FALSE);
			EventLogger.logEvent(ctx,new LoggedEvent(LoggedEvent.EVENT_TYPE_UNAUTHORIZED,"Pokus o editaci uzivatele "+person.getLongName()+"("+person.getId()+")"),loggedPerson);
		}
		env.pushValue("allMembers",Person.getAllMembers(ctx.getDefaultConnection()));
		
		super.prepareEnviroment(ctx, env, parameters, tpl, part);
	}

	public static Collection getManagers(Connection conn) {
		ArrayList out=new ArrayList();
		try {

            PreparedStatement ps=conn.prepareStatement("SELECT * FROM person where manager=1 and id>1");
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
				Person p=new Person(conn, rs, false);
				out.add(p);
			}
            
        } catch (SQLException e) {
			e.printStackTrace();
            return null;
        }
        return out;
	}

}
