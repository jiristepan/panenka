/*
 * Created on 8.12.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.etnetera.projects.panenka.ui;

import java.util.Map;

import org.zweistein.tfs.TemplateData;
import org.zweistein.tfs.parser.PartStatement;
import org.zweistein.wbeans.Context;
import org.zweistein.wbeans.Memento;
import org.zweistein.wbeans.tfs.WBeanEnvironment;

/**
 * @author Jiri Stepan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LoggedUserInfoView extends AplicationWBeanComposite {

	private boolean logout;
	
	public LoggedUserInfoView(){
		super();
		logout=false;
	}
	protected void prepareEnviroment(
		Context ctx,
		WBeanEnvironment env,
		Map parameters,
		TemplateData tpl,
		PartStatement part)
		throws Exception {
		super.prepareEnviroment(ctx, env, parameters, tpl, part);
	}


	public void burden(Context ctx) throws Exception {
		//Memento mem=pickMemento(ctx);
		//person=(Person)ctx.getSession().getAttribute(LoggedUserInfoView.SESSION_OBJECT_KEY);
		//int pid=((Integer)ctx.getSession().getAttribute(LoggedUserInfoView.SESSION_KEY)).intValue();
		//if ((this.person==null) || (this.person.getId()!=pid)) this.person=this.getUser(ctx);
		super.burden(ctx);
	}

	public void relieve(Context ctx) {
		Memento mem=pickMemento(ctx);
		super.relieve(ctx);
	}

	/* (non-Javadoc)
	 * @see org.zweistein.wbeans.impl.AbstractWBean#executeWebMethod(org.zweistein.wbeans.Context, java.lang.String)
	 */
	protected boolean executeWebMethod(Context ctx, String method)
		throws Exception {
		if (method.equals("logout")){
			try{
				ctx.getSession().setAttribute(LoggedUserInfoView.SESSION_KEY,null);
				ctx.getSession().setAttribute(LoggedUserInfoView.SESSION_OBJECT_KEY,null);
				this.loggedPerson=null;
			}catch(Exception e){}
			((MainPage)this.parent).logoutEvent();
			ctx.redirect(ctx.getAddress().getTopClass());
		}
		return super.executeWebMethod(ctx, method);
	}

}
