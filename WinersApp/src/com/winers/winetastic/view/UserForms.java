package com.winers.winetastic.view;

import android.content.Context;
import android.widget.TextView;

import com.winers.winetastic.controller.UserActivity;


public class UserForms {

	public static void attemptLogin( Context context, 
			String email, 
			String password, 
			TextView errorMsg) {
		UserActivity.attemptLogin(context, email, password, errorMsg);
	}
	
	public static void attemptRegistration( Context context, 
			String email, 
			String password, 
			TextView errorMsg) {
		UserActivity.attemptRegistration(context, email, password, errorMsg);
	}
}
