package com.winers.winetastic.controller;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.winers.winetastic.DailyVine;
import com.winers.winetastic.model.data.APISnoothResponse;
import com.winers.winetastic.model.data.APISnoothResponseWineArray;
import com.winers.winetastic.model.manager.SystemManager;
import com.winers.winetastic.model.manager.WinetasticManager;

/**
 * Network operations must be performed in an AsyncTask, so that's
 * what this class is for.
 * Postcondition: upon successful search of at least one result, user
 *                is redirected to the search results page.
 */
public class DailyVineIntentTask extends AsyncTask<Void, Void, String> {
	private String wineryResponse;
	private String wineResponse;
	private ProgressDialog dialog;
	private boolean isOnline;
	private boolean snoothDown;
	private Context context;
    private Gson gson;
    private APISnoothResponse snoothResponse;
    private List<APISnoothResponseWineArray> wineAPIResponse;
    
    public DailyVineIntentTask(Context context) {
    	this.context = context;
    }
    
    
	@Override
	protected void onPreExecute() {
		// This is where the "searching" overlay will go
		super.onPreExecute();
		dialog = ProgressDialog.show(context, "","Loading...");
	}
	
	// This gets executed after onPreExecute()
	@Override
	protected String doInBackground(Void... arg0) {
		if (!SystemManager.isOnline(context)) {
			isOnline = false;
		} else {
			isOnline = true;
			wineResponse = WinetasticManager.getRandomWine();
			if (!wineResponse.equals("")) { // Check for empty response from Snooth
				snoothDown = false;
				gson = new Gson();
			    snoothResponse = gson.fromJson(wineResponse, APISnoothResponse.class);
			    wineAPIResponse = (List<APISnoothResponseWineArray>) snoothResponse.wineResults;
				wineryResponse = WinetasticManager.getWineryDetails(wineAPIResponse.get(0).wineryID);
			}
			else {
				snoothDown = true;
			}
		}
		return "";
	}
	
	// This gets executed after doInBackground()
	protected void onPostExecute(String result) {
		if(dialog.isShowing())
			dialog.dismiss();
		if (snoothDown) {
			Toast.makeText(context.getApplicationContext(), "Our online wine database is currently unavailable. Please try again later.", Toast.LENGTH_SHORT).show();
		}
		else if (!isOnline) {
			Toast.makeText(context.getApplicationContext(), "You must be connected to the Internet to use this feature", Toast.LENGTH_SHORT).show();
		} else {
			Intent i = new Intent(context, DailyVine.class);
			i.putExtra("Search Query", wineResponse);
			i.putExtra("Winery", wineryResponse);
			context.startActivity(i);
		}
	}
}