package com.winers.winetastic.model.manager;

import android.content.Context;
import android.widget.TextView;

import com.winers.winetastic.controller.UserDispatch;
import com.winers.winetastic.model.data.UserDAO;

public class UserManager {
	
	public static void createSnoothAccount(String email) {
		UserDAO.createSnoothAccount(email);
	}
	
	// well... lulz
	public static void attemptLogin( Context context, 
			String email, 
			String password, 
			TextView errorMsg) {
		new UserDispatch(context, email, password, errorMsg).validateLogin();		
	}
	
	public static void attemptRegistration( Context context, 
			String email, 
			String password, 
			TextView errorMsg) {
		new UserDispatch(context, email, password, errorMsg).validateRegistration();		
	}
}
