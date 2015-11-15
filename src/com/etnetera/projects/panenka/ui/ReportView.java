package com.etnetera.projects.panenka.ui;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.zweistein.tfs.TemplateData;
import org.zweistein.tfs.parser.PartStatement;
import org.zweistein.wbeans.ArgumentSource;
import org.zweistein.wbeans.Context;
import org.zweistein.wbeans.Memento;
import org.zweistein.wbeans.tfs.WBeanEnvironment;

import com.etnetera.projects.panenka.model.ReportModel;

/**
 * @author Jiri Stepan
 */
public class ReportView extends AplicationWBeanComposite {
	private Date dateFrom;
	private Date dateTo;

	public ReportView(){
		this.setDefaultTemplate("reportView.tfs");
	}

	public void burden(Context ctx) throws Exception {
		Memento mem=pickMemento(ctx);
		this.dateFrom=(Date)mem.get("reportCompositeFromDate",null);
		this.dateTo=(Date)mem.get("reportCompositeToDate",null);
		super.burden(ctx);
	}

	protected boolean executeWebMethod(Context ctx, String method)
		throws Exception {
		if (method.equals("setInterval")){
			ArgumentSource as=ctx.getArgumentSource();
			this.dateFrom=as.getDate("dateFrom");
			this.dateTo=as.getDate("dateTo");
		}
		if (dateFrom==null) 
		    printToAlert("Chybnì zadané datum OD");
		if (dateTo==null) 
		    printToAlert("Chybnì zadané datum DO");
		//ctx.redirect(ctx.getAddress().getTopClass());
		return super.executeWebMethod(ctx, method);
	}

	protected void prepareEnviroment(
		Context ctx,
		WBeanEnvironment env,
		Map parameters,
		TemplateData tpl,
		PartStatement part)
		throws Exception {
	    
		if ((this.dateFrom!=null)&&(this.dateTo!=null)){
		    ReportModel repm=new ReportModel(ctx.getDefaultConnection(),new java.sql.Date(dateFrom.getTime()),new java.sql.Date(dateTo.getTime()));
		    Collection allDays=repm.getAllDays();

		    env.pushValue("dateFrom",dateFrom);
			env.pushValue("dateTo",dateTo);
			env.pushValue("allDays",allDays);
			env.pushValue("allPersons",repm.getAllPersons());	
			if (dateFrom.after(dateTo)) printToAlert("Datum OD je vìtší než datum DO");
		}
		else {
		    env.pushValue("dateFrom",ctx.getArgumentSource().getString("dateFrom"));
		    env.pushValue("dateTo",ctx.getArgumentSource().getString("dateTo"));
		    env.pushValue("alert",alert);
		}
		super.prepareEnviroment(ctx, env, parameters, tpl, part);
	}

	public void relieve(Context ctx) {
		Memento mem=pickMemento(ctx);
		mem.set("reportCompositeFromDate",this.dateFrom);
		mem.set("reportCompositeToDate",this.dateTo);
		super.relieve(ctx);
	}

}
