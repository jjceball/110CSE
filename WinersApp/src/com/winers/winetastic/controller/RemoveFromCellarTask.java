package com.winers.winetastic.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.winers.winetastic.model.manager.DatabaseHandler;
import com.winers.winetastic.model.manager.SystemManager;
import com.winers.winetastic.model.manager.WinetasticManager;

public class RemoveFromCellarTask extends AsyncTask<String, Void, Void> {
	private Context context;
	private DatabaseHandler db;
	
	public RemoveFromCellarTask(Context context) {
		this.context = context;
	}
	
	
	@Override
	protected Void doInBackground(String ... code) {
		if (!SystemManager.isOnline(context)) {
			Toast.makeText(context.getApplicationContext(), "You must be connected to the Internet to use this feature", Toast.LENGTH_SHORT).show();
		} else {
			System.err.println("Adding wine to cellar.");
			db = new DatabaseHandler(context.getApplicationContext());
			String email = db.getUserDetails().get("email");
	    	WinetasticManager.removeWineFromCellar(email, code[0]);
	    	System.err.println("Wine code to remove: " + code[0]);
		}
    	return null;
	}
}