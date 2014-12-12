package com.winers.winetastic.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.winers.winetastic.model.manager.DatabaseHandler;
import com.winers.winetastic.model.manager.SystemManager;
import com.winers.winetastic.model.manager.UserFunctions;
import com.winers.winetastic.model.manager.WinetasticManager;

public class AddToWishlistTask extends AsyncTask<Void, Void, String> {
	private boolean hasWine = false;
	private ProgressDialog dialog;
	private boolean isGuest;
	private boolean isOnline;
	
	DatabaseHandler db;
	Context context;
	String name, code;
	
	public AddToWishlistTask(Context context, String name, String code) {
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
	protected String doInBackground(Void... arg0) {
		if (!SystemManager.isOnline(context)) {
			isOnline = false;
		} else {
			isOnline = true;
			System.err.println("Adding wine to wishlist.");
			UserFunctions uf = new UserFunctions();
			if (!uf.isUserLoggedIn(context.getApplicationContext())) {
				isGuest = true;
			}
				else {
				db = new DatabaseHandler(context.getApplicationContext());
				String email = db.getUserDetails().get("email");
				if (WinetasticManager.isWineInWishlist(email, code)) {
					hasWine = true;
				} else {
					WinetasticManager.addWineToWishlist(email, code);	
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
	    			Toast.makeText(context, name + " is already in your Wishlist", Toast.LENGTH_SHORT).show();
	    		} else {
	    			Toast.makeText(context, name + " has been added to your Wishlist", Toast.LENGTH_SHORT).show();
	    		}
			}
		}
	}
}