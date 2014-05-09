package com.pesse.sms.common;

import com.pesse.sms.common.Config;
import com.pesse.sms.common.ConfigKeys;

public class WebPages {

	public static final String LOGIN_PAGE 		= Config.getProperty(ConfigKeys.SERVER_CONTEXT) + "/login";
	public static final String LOGOUT_PAGE 		= Config.getProperty(ConfigKeys.SERVER_CONTEXT) + "/logout";
	public static final String HOME_PAGE 		= Config.getProperty(ConfigKeys.SERVER_CONTEXT) + "/";
	public static final String REGISTER_PAGE 	= Config.getProperty(ConfigKeys.SERVER_CONTEXT) + "/register";
	public static final String SUPER_USER_PAGE	= Config.getProperty(ConfigKeys.SERVER_CONTEXT) + "/su";
	
	
	public static final String SET_GOALS		= Config.getProperty(ConfigKeys.SERVER_CONTEXT) + "/set_goals.jsp";
}
