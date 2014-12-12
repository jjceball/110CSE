package com.winers.winetastic.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.winers.winetastic.SearchResults;
import com.winers.winetastic.model.data.WineSearchObject;
import com.winers.winetastic.model.manager.SystemManager;
import com.winers.winetastic.model.manager.WinetasticManager;

/**
 * Network operations must be performed in an AsyncTask, so that's
 * what this class is for.
 * Postcondition: upon successful search of at least one result, user
 *                is redirected to the search results page.
 */



public class CombinedSearchTask extends AsyncTask<WineSearchObject, Void, String> {
	
	private ProgressDialog dialog;
	private Context context;
	private WineSearchObject sP;
	private boolean isOnline;
	
	public CombinedSearchTask(Context context) {
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
	protected String doInBackground(WineSearchObject ... searchObjects) {
		String rv = "";
		if (!SystemManager.isOnline(context)) {
			isOnline = false;
		} else {
			isOnline = true;
			this.sP = searchObjects[0];
			rv = WinetasticManager.performCombinedSearch(sP, 20);
		}
		return rv;
	}
	
	// This gets executed after doInBackground()
	@Override
	protected void onPostExecute(String result) {
		if(dialog.isShowing())
			dialog.dismiss();
		if (result.equals("")) {
			Toast.makeText(context.getApplicationContext(), "Our online wine database is currently unavailable. Please try again later.", Toast.LENGTH_SHORT).show();
		} 
		else if (!isOnline) {
			Toast.makeText(context.getApplicationContext(), "You must be connected to the Internet to use this feature", Toast.LENGTH_SHORT).show();
		} else {
			if (WinetasticManager.hasSearchResults(result)) {
				// Search has results. Send to SearchResult page
				Intent i = new Intent(context, SearchResults.class);
				i.putExtra("Search Query", result);
				i.putExtra("WineSearchObject", sP);
				context.startActivity(i);
			} else {
				// No search results. Notify user to search again.
				Toast.makeText(context, "No matches were found. Please try your search again.", Toast.LENGTH_LONG).show();
			}
		}
	}
}	