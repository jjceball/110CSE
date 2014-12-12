package com.winers.winetastic.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import android.content.Context;

import com.winers.winetastic.model.manager.DatabaseHandler;
import com.winers.winetastic.model.manager.SystemManager;
import com.winers.winetastic.model.manager.UserFunctions;
import com.winers.winetastic.model.manager.WinetasticManager;

public class AddToCellarTask extends AsyncTask<Void, Void, String> {
	private boolean hasWine = false;
	private ProgressDialog dialog;
	private boolean isGuest = false;
	private boolean isOnline;
	private Context context;
	private DatabaseHandler db;
	
	private String name, code;
	
	public AddToCellarTask(Context context, String name, String code) {
		this.context = context;
		this.name = name;
		this.code = code;
	}
	
	
	@Override
	protected void onPreExecute() {
		// This is where the "searching" overlay will go
		super.onPreExecute();
		dialog = ProgressDialog.show(context, "","Loading...");
	}
	
	@Override
	protected String doInBackground(Void ... args) {
		if (!SystemManager.isOnline(context)) {
			isOnline = false;
		} else {
			isOnline = true;
			UserFunctions uf = new UserFunctions();
			if (!uf.isUserLoggedIn(context.getApplicationContext())) {
				isGuest = true;
			}
				else {
				db = new DatabaseHandler(context.getApplicationContext());
				String email = db.getUserDetails().get("email");
				if (WinetasticManager.isWineInCellar(email, code)) {
					hasWine = true;
				} else {
					WinetasticManager.addWineToCellar(email, code);	
				}
			}
		}
    	return "";
	}
	
	protected void onPostExecute(String result) {
		if(dialog.isShowing())
			dialog.dismiss();
		if (!isOnline) {
			Toast.makeText(context.getApplicationContext(), "You must be connected to the Internet to use this feature", Toast.LENGTH_SHORT).show();
		} else {
			if (isGuest) {
				Toast.makeText(context.getApplicationContext(), "You must be logged in to use this feature", Toast.LENGTH_SHORT).show();
			} else {
	    		if (hasWine) {
	    			Toast.makeText(context, name + " is already in your Cellar", Toast.LENGTH_SHORT).show();
	    		} else {
	    			Toast.makeText(context, name + " has been added to your Cellar", Toast.LENGTH_SHORT).show();
	    		}
			}
		}
	}
}