/*
 * Created on 24.12.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.etnetera.projects.panenka.ui;

import java.util.HashMap;
import java.util.Map;

import org.zweistein.tfs.TemplateData;
import org.zweistein.tfs.parser.PartStatement;
import org.zweistein.wbeans.ArgumentSource;
import org.zweistein.wbeans.Context;
import org.zweistein.wbeans.Memento;
import org.zweistein.wbeans.tfs.WBeanEnvironment;
import org.zweistein.wck.containers.Composite;

import com.etnetera.projects.panenka.model.Person;

/**
 * @author Jiri Stepan 
 * Vychozi trida udrzujici zakladni stavova data aplikace.
 * Person loggedPerson .... prihlaseny uzivatel
 */
public class AplicationWBeanComposite extends Composite {
	public static final String SESSION_KEY="authorizedUser";
	public static final String SESSION_OBJECT_KEY="authorizedUserObject";
	protected Person loggedPerson=null;
	protected String message;
	protected String alert;
	
	public AplicationWBeanComposite(){
	    this.message="";
	    this.alert="";
	}
	
	public void burden(Context ctx) throws Exception {
		//zkusim ho vzit z mementa
		Memento mem=pickMemento(ctx);
		loggedPerson=(Person)mem.get("ApplicationLoggedUser",null);
		
		//pokud ne, zkusim ho vzit ze session
		if (loggedPerson==null) loggedPerson=(Person)ctx.getSession().getAttribute(LoggedUserInfoView.SESSION_OBJECT_KEY);
		
		//pokud ne zkusim ho naxist z databaze (situace po loginu)
		Object o=ctx.getSession().getAttribute(AplicationWBeanComposite.SESSION_KEY);
		//if (o==null) //throw new Exception("No user in session");
		if (o!=null){
			loggedPerson = new Person(ctx.getDefaultConnection(),((Integer)o).intValue());
			if (!loggedPerson.isValid()) throw new Exception("ERROR - invalid user");
		}
		super.burden(ctx);
	}

	public Person getLoggedPerson() {		
		return loggedPerson;
	}
	
	public void relieve(Context ctx) {
		Memento mem=pickMemento(ctx);
		mem.set("ApplicationLoggedUser",null);
		ctx.getSession().setAttribute(AplicationWBeanComposite.SESSION_OBJECT_KEY,this.loggedPerson);
		super.relieve(ctx);
	}

	protected void prepareEnviroment(Context ctx, WBeanEnvironment env,
			Map arg2, TemplateData arg3, PartStatement arg4) throws Exception {
		env.pushValue("loggedPerson",loggedPerson);
		env.pushValue("message",message);
		env.pushValue("alert",alert);
		super.prepareEnviroment(ctx, env, arg2, arg3, arg4);
	}
	
	public void printToMessage(String str){
	    message+=str+"<br>\n";
	}
	
	public void printToAlert(String str){
	    alert+=str+"<br>\n";
	}
    protected boolean executeWebMethod(Context ctx, String method)
            throws Exception {
        ArgumentSource as=ctx.getArgumentSource();
        if (method.equals("showMessage")){
            this.message=as.getString("message");
            this.alert=as.getString("alert");
        }
        return super.executeWebMethod(ctx,method);
    }
    
    protected void redirectToShowMessage(Context ctx) throws Exception {
        HashMap map=new HashMap();
        map.put("message",this.message);
        map.put("alert",this.alert);
        ctx.redirect(ctx.getAddress().changeMethod("showMessage"),map);
    }

}
