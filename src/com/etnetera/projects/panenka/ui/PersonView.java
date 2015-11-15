/*
 * Created on 23.12.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.etnetera.projects.panenka.ui;

import java.sql.SQLException;
import java.util.Map;

import org.zweistein.tfs.TemplateData;
import org.zweistein.tfs.parser.PartStatement;
import org.zweistein.wbeans.Context;
import org.zweistein.wbeans.Memento;
import org.zweistein.wbeans.tfs.WBeanEnvironment;

import com.etnetera.projects.panenka.model.Person;


/**
 * @author Jiri Stepan
 *
 */
public class PersonView extends AplicationWBeanComposite {
   	Integer personId;
	Person person;
	Context ctx;
	
	public void setPersonId(Integer pid) throws SQLException{
		personId = pid;
		this.person=new Person(ctx.getDefaultConnection(),personId.intValue());
	}
	
	public Integer getPersonId(){
		return this.personId;
	}

	public void burden(Context ctx) throws Exception {
		Memento mem=pickMemento(ctx);
		this.ctx=ctx;
		Integer pid=(Integer)mem.get("PersonViewPersonId",null);
		if (pid!=null) this.setPersonId(pid);
		super.burden(ctx);
	}

	protected void prepareEnviroment(
		Context ctx,
		WBeanEnvironment env,
		Map parameters,
		TemplateData tpl,
		PartStatement part)
		throws Exception {
		env.pushValue("person",person);
		super.prepareEnviroment(ctx, env, parameters, tpl, part);
	}

	public void relieve(Context ctx) {
		Memento mem=pickMemento(ctx);
		mem.set("PersonViewPersonId",this.personId);
		super.relieve(ctx);
	}

}
