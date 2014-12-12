package com.winers.winetastic.controller;

import com.winers.winetastic.model.manager.UserManager;

import android.content.Context;
import android.widget.TextView;

public class UserActivity {
	public static void attemptLogin( Context context, 
			String email, 
			String password, 
			TextView errorMsg) {
		UserManager.attemptLogin(context, email, password, errorMsg);
	}
	
	public static void attemptRegistration( Context context, 
			String email, 
			String password, 
			TextView errorMsg) {
		UserManager.attemptRegistration(context, email, password, errorMsg);
	}
}
