package com.etnetera.projects.panenka.ui;

import java.util.Map;

import org.zweistein.tfs.TemplateData;
import org.zweistein.tfs.parser.PartStatement;
import org.zweistein.wbeans.Context;
import org.zweistein.wbeans.Memento;
import org.zweistein.wbeans.impl.DefaultTopWBean;
import org.zweistein.wbeans.tfs.WBeanEnvironment;

import com.etnetera.projects.panenka.model.Person;

/**
 * @author Jiri Stepan 
 */
public class MainPage extends DefaultTopWBean {
	public static final int DISPLAY_CALENDAR=0;
	public static final int DISPLAY_USERS=1;
	public static final int DISPLAY_REPORT=2;
	public static final int DISPLAY_BOARD=3; 
	public static final int DISPLAY_RESERVATIONS=4;
    public static final int DISPLAY_DAYCONSTRAINTEDITOR=5;	
	
	protected Person loggedPerson=null;
	private Boolean showHelp;
	
	private MessageBoard messageBoard;
	
	private int displayMode=0;
	public MainPage(){
		setDefaultTemplate("mainPage.tfs");
		showHelp=Boolean.TRUE;
	}
	
	public void initialize() {
		
		ReservationCalendarComposite calendarComposite=new ReservationCalendarComposite();
		calendarComposite.setDefaultTemplate("reservationCalendar.tfs");
		calendarComposite.setName("calendarComposite");
		this.addChild(calendarComposite);

		UsersComposite usersComposite=new UsersComposite();
		usersComposite.setName("usersComposite");
		usersComposite.setDefaultTemplate("usersComposite.tfs");
		this.addChild(usersComposite);

		LoggedUserInfoView userView=new LoggedUserInfoView();
		userView.setName("userView");
		userView.setDefaultTemplate("userView.tfs");
		this.addChild(userView); 

		ReportView reportView=new ReportView();
		reportView.setName("reportView");
		this.addChild(reportView);

		messageBoard=new MessageBoard();
		messageBoard.setName("messageBoard");
		messageBoard.setDefaultTemplate("messageBoard.tfs");
		this.addChild(messageBoard);
		
		ReservationsView reservationsView=new ReservationsView();
		reservationsView.setName("reservationsView");
		this.addChild(reservationsView);
		
		DayConstrainEditor dayConstrainEditor = new DayConstrainEditor();
		dayConstrainEditor.setName("dayConstrain");
		this.addChild(dayConstrainEditor);


		super.initialize();
	}

	public void burden(Context ctx) throws Exception {
		Memento mem=pickMemento(ctx);
		Integer displayModeI=(Integer)mem.get("MainPageDisplayMode",new Integer(DISPLAY_CALENDAR));
		this.displayMode=displayModeI.intValue();
		this.showHelp=(Boolean)mem.get("MainPageShowHelp", Boolean.TRUE);
		
		//zkusim ho vzit z mementa
		mem=pickMemento(ctx);
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
		env.pushValue("loggedPerson",loggedPerson);

		env.pushValue("displayCalendar",new Boolean(displayMode==DISPLAY_CALENDAR));
		env.pushValue("displayUsers",new Boolean(displayMode==DISPLAY_USERS));
		env.pushValue("displayReport",new Boolean(displayMode==DISPLAY_REPORT));
		env.pushValue("displayBoard", new Boolean(displayMode==DISPLAY_BOARD));
		env.pushValue("displayReservations", new Boolean(displayMode==DISPLAY_RESERVATIONS));
		env.pushValue("displayDayConstrainEditor", new Boolean(displayMode==DISPLAY_DAYCONSTRAINTEDITOR));
		env.pushValue("showHelp",showHelp);
		env.pushValue("displayMode",new Integer(displayMode));
		super.prepareEnviroment(ctx, env, parameters, tpl, part);
	}

	public void relieve(Context ctx) {
		Memento mem=pickMemento(ctx);
		mem.set("MainPageDisplayMode",new Integer(this.displayMode));
		mem.set("MainPageShowHelp",this.showHelp);
		super.relieve(ctx);
	}

	protected boolean executeWebMethod(Context ctx, String method)
		throws Exception {
		
		if (method.equals("displayUsers")){
			this.displayMode=DISPLAY_USERS;
		}
		else if (method.equals("displayCalendar")){
			this.displayMode=DISPLAY_CALENDAR;
		}
		else if (method.equals("displayReport")){
			this.displayMode=DISPLAY_REPORT;
		}
		else if (method.equals("displayBoard")){
			this.displayMode=DISPLAY_BOARD;
			this.messageBoard.setDisplayMode(MessageBoard.DISPLAY_MESSAGES);
		
		}
		else if (method.equals("displayReservations")){
			this.displayMode=DISPLAY_RESERVATIONS;		
		}
		else if (method.equals("displayDayConstrainEditor")){
			this.displayMode=DISPLAY_DAYCONSTRAINTEDITOR;		
		}
		else if (method.equals("showHelp")){
		    this.showHelp=Boolean.TRUE;
		}
		else if (method.equals("hideHelp")){
		    this.showHelp=Boolean.FALSE;
		}
	
		return super.executeWebMethod(ctx, method);
	}

	public int getDisplayMode() {
		return displayMode;
	}

	public void setDisplayMode(int displayMode) {
		this.displayMode = displayMode;
	}

	public void logoutEvent() {
		this.displayMode=DISPLAY_CALENDAR;
		ReservationCalendarComposite cv=(ReservationCalendarComposite)this.getChild("calendarComposite");
		cv.logoutEvent();
	}

	public void render(Context ctx, Map arg1) throws Exception {
		ctx.getResponse().setHeader("Pragma","No-cache");
		super.render(ctx, arg1);
	}
	
    public Boolean isShowHelp() {
        return showHelp;
    }
    public void setShowHelp(Boolean showHelp) {
        this.showHelp = showHelp;
    }
}
