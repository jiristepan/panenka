package com.etnetera.projects.panenka.ui;

import java.util.List;
import java.util.Map;

import org.zweistein.tfs.TemplateData;
import org.zweistein.tfs.parser.PartStatement;
import org.zweistein.wbeans.ArgumentSource;
import org.zweistein.wbeans.Context;
import org.zweistein.wbeans.Memento;
import org.zweistein.wbeans.tfs.WBeanEnvironment;
import org.zweistein.wck.grid.DataGrid;
import org.zweistein.wck.grid.MySqlModel;

/**
 * @author Jiri Stepan
 *
 */
public class UsersComposite extends AplicationWBeanComposite {
	public static final int DISPLAY_LIST=0;
	public static final int DISPLAY_DETAIL=1;
	public static final int DISPLAY_FORM=2;
	
	private int displayMode;
		
	PersonView personView;
	DataGrid memberListDataGrid;
	DataGrid nonMemberListDataGrid;
	
	MySqlModel memberModel, nonMemberModel;
	private PersonForm personForm;
	
	public void burden(Context ctx) throws Exception {
		super.burden(ctx);

		Memento mem=pickMemento(ctx);
		Integer dispModeI=(Integer)mem.get("UsersCompositeDisplayMode",new Integer(DISPLAY_LIST));
		this.displayMode=dispModeI.intValue();
	}

	protected boolean executeWebMethod(Context ctx, String method)
		throws Exception {
		ArgumentSource as=ctx.getArgumentSource();
		if (method.equals("showDetail")){
			this.personView.setPersonId(as.getInteger("personId"));
			this.displayMode=DISPLAY_DETAIL;
		}
		else if (method.equals("editPerson")){
			this.personForm.setPersonId(as.getInteger("personId"));
			this.displayMode=DISPLAY_FORM;			
			((MainPage)this.parent).setDisplayMode(MainPage.DISPLAY_USERS);
		}
		else if (method.equals("showList")){
			this.displayMode=DISPLAY_LIST;
		}
		return super.executeWebMethod(ctx, method);
	}

	/* (non-Javadoc)
	 * @see org.zweistein.wbeans.WBean#initialize()
	 */
	public void initialize() {
		memberListDataGrid=new DataGrid();
		memberListDataGrid.setName("memberListDataGrid");
		memberListDataGrid.setDefaultTemplate("userListDataGrid.tfs");
		memberListDataGrid.setPage(350);
		
		memberModel=new MySqlModel();
		memberModel.setSelectCommand("SELECT * FROM person");
		memberModel.setCountingCommand("SELECT count(*) FROM person");
		memberModel.setWhereClause(" member = 1 ");
		memberModel.setOrderingSupported(true);
		memberModel.clearOrdering();
		memberModel.addOrdering("member",true);
		memberModel.addOrdering("surname",false);
	
		memberListDataGrid.setModel(memberModel);
		this.addChild(memberListDataGrid);

		nonMemberListDataGrid=new DataGrid();
		nonMemberListDataGrid.setName("nonMemberListDataGrid");
		nonMemberListDataGrid.setDefaultTemplate("userListDataGrid.tfs");
		nonMemberListDataGrid.setPage(350);
		
		nonMemberModel=new MySqlModel();
		nonMemberModel.setSelectCommand("SELECT p.*, g.name as g_name, g.surname as g_surname FROM person p, person g WHERE p.member = 0 AND g.id=p.granted_by ");
		nonMemberModel.setCountingCommand("SELECT count(*) FROM person");
		
		nonMemberModel.setOrderingSupported(true);
		nonMemberModel.clearOrdering();
		nonMemberModel.addOrdering("member",true);
		nonMemberModel.addOrdering("surname",false);
	
		nonMemberListDataGrid.setModel(nonMemberModel);
		this.addChild(nonMemberListDataGrid);

		//user detail view
		personView=new PersonView();
		personView.setName("personView");
		personView.setDefaultTemplate("personView.tfs");
		this.addChild(personView);

		//user form view
		personForm=new PersonForm();
		personForm.setName("personForm");
		personForm.setDefaultTemplate("personForm.tfs");
		this.addChild(personForm);
		
		super.initialize();
	}

	/* (non-Javadoc)
	 * @see org.zweistein.wbeans.impl.AbstractWBean#prepareEnviroment(org.zweistein.wbeans.Context, org.zweistein.wbeans.tfs.WBeanEnvironment, java.util.Map, org.zweistein.tfs.TemplateData, org.zweistein.tfs.parser.PartStatement)
	 */
	protected void prepareEnviroment(
		Context ctx,
		WBeanEnvironment env,
		Map parameters,
		TemplateData tpl,
		PartStatement part)
		throws Exception {
		env.pushValue("displayList",new Boolean(this.displayMode==DISPLAY_LIST));
		env.pushValue("displayDetail",new Boolean(this.displayMode==DISPLAY_DETAIL));
		env.pushValue("displayForm",new Boolean(this.displayMode==DISPLAY_FORM));

		List ord=memberListDataGrid.getOrdering();
		if (ord.size()==0)	memberListDataGrid.addOrdering("surname",false);

		ord=nonMemberListDataGrid.getOrdering();
		if (ord.size()==0)	nonMemberListDataGrid.addOrdering("surname",false);

		super.prepareEnviroment(ctx, env, parameters, tpl, part);
	}

	public void relieve(Context ctx) {
		Memento mem=pickMemento(ctx);
		mem.set("UsersCompositeDisplayMode",new Integer(this.displayMode));
		super.relieve(ctx);
	}

	public int getDisplayMode() {
		return displayMode;
	}

	public void setDisplayMode(int displayMode) {
		this.displayMode = displayMode;
	}

}
