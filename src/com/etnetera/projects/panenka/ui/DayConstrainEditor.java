package com.etnetera.projects.panenka.ui;

import java.sql.Connection;
import java.sql.Date;
import java.util.Map;

import org.zweistein.tfs.TemplateData;
import org.zweistein.tfs.parser.PartStatement;
import org.zweistein.wbeans.ArgumentSource;
import org.zweistein.wbeans.Context;
import org.zweistein.wbeans.tfs.WBeanEnvironment;

import com.etnetera.projects.panenka.model.DayConstraint;

public class DayConstrainEditor extends AplicationWBeanComposite {

	public DayConstrainEditor(){
		this.setDefaultTemplate("dayConstrainEditor.tfs");
	}

	protected void prepareEnviroment(Context ctx, WBeanEnvironment env, Map arg2, TemplateData arg3, PartStatement arg4) throws Exception {
		env.pushValue("dayConstraintList",DayConstraint.getList(ctx.getDefaultConnection()));
		env.pushValue("manager_list",PersonForm.getManagers(ctx.getDefaultConnection()));
		super.prepareEnviroment(ctx, env, arg2, arg3, arg4);
	}

	protected boolean executeWebMethod(Context ctx, String method) throws Exception {
		ArgumentSource as=ctx.getArgumentSource();
		if ("update".equals(method)){
			Integer id=as.getInteger("id");
			if (id!=null){
				DayConstraint dc=new DayConstraint(ctx.getDefaultConnection(),id.intValue());
				dc.setCapacity(as.getInt("capacity"));
				dc.setNonmemberCapacity(as.getInt("nonmember_capacity"));
				dc.setFromDate(as.getDate("from_date"));
				dc.setToDate(as.getDate("to_date"));
				dc.setNote(as.getString("note"));
				dc.setManagerId(as.getInt("manager_id")); //TODO: provest kontrolu na managera
				dc.setMemberPriorityDeadline(as.getDate("member_priority_deadline"));
				dc.updateDB(ctx.getDefaultConnection());
				this.printToMessage("Zajezd zmenen.");
			}
		}
		else if ("delete".equals(method)){
			Integer id=as.getInteger("id");
			if (id!=null){
				DayConstraint.delete(ctx.getDefaultConnection(), id.intValue());
				printToMessage("Zajezd smazan.");
			}
		}
		else if ("addnew".equals(method)){
			DayConstraint.createNew(ctx.getDefaultConnection());
			printToMessage("Zajezd pridan, prosim upravte jej.");
		}
			
		
		return super.executeWebMethod(ctx, method);
	}
	
	
	
	
}
