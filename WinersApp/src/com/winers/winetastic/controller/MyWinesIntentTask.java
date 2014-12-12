package com.winers.winetastic.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.winers.winetastic.WineCellTabLayout;
import com.winers.winetastic.model.data.APISnoothResponseMetaData;
import com.winers.winetastic.model.data.APISnoothResponseMyWines;
import com.winers.winetastic.model.manager.DatabaseHandler;
import com.winers.winetastic.model.manager.SystemManager;
import com.winers.winetastic.model.manager.WinetasticManager;

public class MyWinesIntentTask extends AsyncTask<Void, Void, String> {

	private String email;
	private ProgressDialog dialog;
    private Gson gson;
    private Context context;
    private boolean isOnline;
    
    public MyWinesIntentTask(Context c) {
    	this.context = c;
    }
    
	@Override
	protected void onPreExecute() {
		
		email = new DatabaseHandler(context.getApplicationContext()).getUserDetails().get("email");
		super.onPreExecute();
		dialog = ProgressDialog.show(context, "","Loading...");
	}
	
	// This gets executed after onPreExecute()
	@Override
	protected String doInBackground(Void... arg0) {
		String myWinesResponse = "";
		if (!SystemManager.isOnline(context)) {
			isOnline = false;
		} else {
			isOnline = true;
			myWinesResponse = WinetasticManager.returnMyWines(email);
		}
		return myWinesResponse;
		
	}
	
	// This gets executed after doInBackground()
	protected void onPostExecute(String result) {
		if(dialog.isShowing())
			dialog.dismiss();
		if (!isOnline) {
			Toast.makeText(context.getApplicationContext(), "You must be connected to the Internet to use this feature", Toast.LENGTH_SHORT).show();
		} else {
			if (!result.equals("")) {
				gson = new Gson();
				final APISnoothResponseMyWines myWinesResponse = gson.fromJson(result, APISnoothResponseMyWines.class);
				final APISnoothResponseMetaData meta = myWinesResponse.metaResults;
		        if (meta.results.equals("") || meta.results.equals("0")) {
		        	Toast.makeText(context, "You must add a wine through search results before you can access My Wines", Toast.LENGTH_LONG).show();
		        }
		        else {
					Intent i = new Intent(context, WineCellTabLayout.class);
					i.putExtra("MyWines Query", result);
					context.startActivity(i);
		        }
			} else {
				Toast.makeText(context, "You must be conected to the Internet to access My Wines", Toast.LENGTH_LONG).show();
			}
		}
	}
}