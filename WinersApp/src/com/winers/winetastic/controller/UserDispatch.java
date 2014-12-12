package com.winers.winetastic.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.winers.winetastic.Home;
import com.winers.winetastic.model.manager.DatabaseHandler;
import com.winers.winetastic.model.manager.UserManager;
import com.winers.winetastic.model.manager.SystemManager;
import com.winers.winetastic.model.manager.UserFunctions;

/**
 * Performs validation tasks for login.
 * 
 *
 */

public class UserDispatch {
	/*
     * JSON response node names
     */
	static final String KEY_SUCCESS = "success";
	static final String KEY_UID = "uid";
	static final String KEY_NAME = "name";
	static final String KEY_EMAIL = "email";
	static final String KEY_CREATED_AT = "created_at";	
	static final String REGEX_EMAIL_VALIDATION = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
	
	private String email, password;
	private TextView errorMsg;
	private Context context;
	
	public UserDispatch(Context context, 
			String email, 
			String password,
			TextView errorMsg) {
		this.email = email;
		this.password = password;
		this.context = context;
		this.errorMsg = errorMsg;
	}
	
	public void validateLogin() {
		if(!hasErrorLogin()) {
			new LoginNetworkTask(context).execute();
		}	
	}
	
	public void validateRegistration() {
		if(!hasErrorRegister()) {
			new RegisterNetworkTask(context).execute();
		}	
	}
	
	private boolean hasErrorLogin() {
		if (email.length() < 1) {
			errorMsg.setText("Please enter an email address");
			return true;
		}
		if (password.length() < 1) {
			errorMsg.setText("Please enter a password");
			return true;
		}
		errorMsg.setText("");
		return false;
	}
	
	private boolean hasErrorRegister() {
		// check for valid email address
		Pattern pattern = Pattern.compile(REGEX_EMAIL_VALIDATION);
		Matcher match = pattern.matcher(email);
		if(!match.find()) {
			errorMsg.setText("Please enter a valid email address");
			return true;
		}
		if (password.length() < 6) {
			errorMsg.setText("Your password must be at least 6 characters");
			return true;
		}
		errorMsg.setText("");
		return false;
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class LoginNetworkTask extends AsyncTask<Void, Void, Void> {
		UserFunctions userFunction = new UserFunctions();
		JSONObject json;
		Context context;
		private boolean isOnline;
		
		public LoginNetworkTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			if(!SystemManager.isOnline(context)) {
				isOnline = false;
			} else {
				isOnline = true;
				UserManager.createSnoothAccount(email);
				json = userFunction.loginUser(email, password);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (!isOnline) {
				Toast.makeText(context, "You must be connected to the internet to use this feature", Toast.LENGTH_SHORT).show();
			} else {
				try {
					if (json.getString(UserDispatch.KEY_SUCCESS) != null) {
						errorMsg.setText("");
						String res = json.getString(UserDispatch.KEY_SUCCESS); 
						if(Integer.parseInt(res) == 1){
							
							// user successfully logged in
							// Store user details in SQLite Database
							DatabaseHandler db = new DatabaseHandler(context.getApplicationContext());
							JSONObject json_user = json.getJSONObject("user");
							
							// Clear all previous data in database
							userFunction.logoutUser(context.getApplicationContext());
							db.addUser(json_user.getString(UserDispatch.KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));						
							
							System.err.println("User logged in");
							
							Intent homeScreen = new Intent(context.getApplicationContext(), Home.class);
							
							// Close all views before launching Home activity
							homeScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							context.startActivity(homeScreen);
							
							// Close Login Screen
							((Activity) context).finish();
						} else {
							// Error in login
							errorMsg.setText("Invalid email address or password. Please try again.");
						}
					}
				} catch (JSONException e) {
					System.err.println("JSON error");
					e.printStackTrace();
				}
			}
		}
	}
	
private class RegisterNetworkTask extends AsyncTask<Void, Void, Boolean> {
		
	UserFunctions userFunction = new UserFunctions();
	JSONObject json;
	Context context;
	private boolean isOnline;
	
	public RegisterNetworkTask(Context context) {
		this.context = context;
	}
		
		
		@Override
		protected Boolean doInBackground(Void... params) {
			if(!SystemManager.isOnline(context)) {
				isOnline = false;
			} else {
				isOnline = true;
				UserManager.createSnoothAccount(email);
				json = userFunction.registerUser(email, password);
			}
			return null;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if (!isOnline) {
				Toast.makeText(context, "You must be connected to the internet to use this feature", Toast.LENGTH_SHORT).show();
			} else {
					try {
						if (json.getString(KEY_SUCCESS) != null) {
							errorMsg.setText("");
							String res = json.getString(KEY_SUCCESS); 
							if(Integer.parseInt(res) == 1){
								// user successfully logged in
								// Store user details in SQLite Database
								DatabaseHandler db = new DatabaseHandler(context);
								JSONObject json_user = json.getJSONObject("user");
								
								// Clear all previous data in database
								userFunction.logoutUser(context);
								db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));						
								
								Intent homeScreen = new Intent(context, Home.class);
								Toast.makeText(context, "Account created successfully!", Toast.LENGTH_LONG).show();
								// Close all views before launching Home activity
								homeScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								context.startActivity(homeScreen);
								
								// Close Register screen
								((Activity) context).finish();
							}else{
								// Error in login
								errorMsg.setText("Email address already exists in database.");
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
	
		}
	}

}
