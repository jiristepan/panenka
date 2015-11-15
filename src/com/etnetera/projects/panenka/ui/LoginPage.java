/*
 * Created on 8.12.2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.etnetera.projects.panenka.ui;

import java.util.Map;

import org.zweistein.wbeans.Context;
import org.zweistein.wbeans.impl.DefaultTopWBean;
import org.zweistein.wck.login.LoginForm;
import org.zweistein.wck.login.SqlLoginModel;

/**
 * @author Jiri Stepan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LoginPage extends DefaultTopWBean {
	
	public LoginPage(){
		super();
		this.setDefaultTemplate("loginPage.tfs");
	}
	
	protected boolean executeWebMethod(Context arg0, String arg1)
		throws Exception {
		return super.executeWebMethod(arg0, arg1);
	}

	public void initialize() {
		LoginForm loginForm=new LoginForm();
		loginForm.setName("loginForm");
		loginForm.setDefaultTemplate("loginForm.tfs");
		SqlLoginModel loginModel=new SqlLoginModel();
		loginModel.setSql("SELECT * from person WHERE login=? AND password=?");
		loginForm.setModel(loginModel);
		loginForm.setRedirectToBean("/member/MainPage");
		loginForm.setSessionKey(AplicationWBeanComposite.SESSION_KEY);
		this.addChild(loginForm);
	
		MessageBoard publicBoard=new MessageBoard();
		publicBoard.setName("publicBoard");
		publicBoard.setDefaultTemplate("messageBoard.tfs");
		this.addChild(publicBoard); 
		super.initialize();
	}	
	
	
	public void render(Context ctx, Map arg1) throws Exception {
		ctx.getResponse().setHeader("Pragma","No-cache");
		super.render(ctx, arg1);
	}
}
