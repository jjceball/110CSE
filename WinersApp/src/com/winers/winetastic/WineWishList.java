package com.winers.winetastic;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.winers.winetastic.R;
import com.winers.winetastic.R.color;
import com.winers.winetastic.R.layout;
import com.winers.winetastic.R.menu;
import com.winers.winetastic.R.string;
import com.winers.winetastic.controller.SearchResultsController;
import com.winers.winetastic.model.data.APISnoothResponseMyWineArray;
import com.winers.winetastic.model.data.APISnoothResponseMyWines;
import com.winers.winetastic.model.data.APISnoothResponseWineArray;
import com.winers.winetastic.model.manager.DatabaseHandler;
import com.winers.winetastic.model.manager.WinetasticManager;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WineWishList extends Activity {

	private String myWinesQuery;
	private String wineCodeToRemove;
	private String wineNameToRemove;
	private ArrayList<ArrayList<String>> wines;
	private boolean removeMode;
	private DatabaseHandler db;
	SearchResultsController adapter;
	private int adapterClearPosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
//		  final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);        
//	        if ( customTitleSupported ) {
//	        	// if customTitlebar is supports, set the titlebar layout for it.
//	            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.mycustomtitle);
//	        }
//      final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
//      if ( myTitleText != null ) {
//          myTitleText.setText(getText(getTitleText()));
//      }
//      
//	  System.err.println("Exiting AbstractActivity onCreate method");
		
		setContentView(R.layout.activity_wine_wish_list);
		
		myWinesQuery = (String) getIntent().getExtras().get("MyWines Query");
		
		
		final Gson gson = new Gson();
        final APISnoothResponseMyWines myWinesResponse = gson.fromJson(myWinesQuery, APISnoothResponseMyWines.class);
        final List<APISnoothResponseMyWineArray> winesAPIResponse = myWinesResponse.myWineResults;

        wines = new ArrayList<ArrayList<String>>();

        insertWines(winesAPIResponse);
		adapter = new SearchResultsController(this, wines);
		ListView lv = (ListView) findViewById(android.R.id.list);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int pos,
					long id) {			


				// HOLY SHIT HERE WE GO
				
				List<APISnoothResponseWineArray> wineArrayForInfoPage = new ArrayList<APISnoothResponseWineArray>();
				APISnoothResponseWineArray tempArray;
				for (APISnoothResponseMyWineArray wineZZZ : winesAPIResponse) {
		    		if (wineZZZ.wishlist.equals("1")) {
		    			tempArray = new APISnoothResponseWineArray();
		    			tempArray.code = wineZZZ.code;
		    			tempArray.image = wineZZZ.image;
		    			tempArray.link = wineZZZ.link;
		    			tempArray.name = wineZZZ.name;
		    			tempArray.price = wineZZZ.price;
		    			tempArray.region = wineZZZ.region;
		    			tempArray.type = wineZZZ.type;
		    			tempArray.varietal = wineZZZ.varietal;
		    			tempArray.winery = wineZZZ.winery;
		    			tempArray.snoothRank = wineZZZ.snoothRank;
		    			tempArray.wineryID = wineZZZ.wineryID;
		    			wineArrayForInfoPage.add(tempArray);
		    		}
		    	}
					
				if (removeMode == false) {
					Intent i = new Intent(WineWishList.this, WineInfoPage.class);
					String wineArraySerialized = gson.toJson(wineArrayForInfoPage.get(pos));
					
	
					//List<APISnoothResponseWineArray> wineAPIResponse = snoothResponse.wineResults;	
					//String wineArraySerialized = gson.toJson(wineAPIResponse.get(pos));
	
					i.putExtra("wine_data", wineArraySerialized);
					startActivity(i);
				}
				else { // REMOVE MODE
					adapterClearPosition = pos;
					wineCodeToRemove = wineArrayForInfoPage.get(pos).code;
					wineNameToRemove = wineArrayForInfoPage.get(pos).name;
					new RemoveFromWishlist().execute();
				}
			}
		});

	 
	}
	
	private void clearItemFromAdapter(int position) {
		adapter.clear(position);
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wine_wish_list, menu);
		return true;
	}
	
	public void toggleRemove(View v) {
		removeMode = !removeMode;
		
		if(removeMode) {
			v.setBackgroundColor(getResources().getColor(R.color.red));
			((Button) v).setText(getResources().getString(R.string.removeModeTrue));
			
		} else {
			v.setBackgroundColor(getResources().getColor(R.color.charcoal));
			((Button) v).setText(getResources().getString(R.string.removeModeFalse));			
			
			// REFRESH PAGE
			/*
			Intent i = new Intent(WineWishList.this, Home.class);
			i.putExtra("mywines_reload", "true");
			startActivity(i);
			*/
		}
	}	
	
	protected int getTitleText() {
		// TODO Auto-generated method stub
		return R.string.wish_list_title;
	}
	
	
	public void insertWines (List<APISnoothResponseMyWineArray> winesArray) {
    	
    	ArrayList<String> temp;
    	
    	for (APISnoothResponseMyWineArray wineZZZ : winesArray) {
    		if (wineZZZ.wishlist.equals("1")) {
		    	temp = new ArrayList<String>();
		    	temp.add(wineZZZ.name);
	    		temp.add(wineZZZ.region);
	    		temp.add(wineZZZ.price);
	    		temp.add(wineZZZ.image);
		    	wines.add(temp);
    		}
    	}
    }
	
	private class RemoveFromWishlist extends AsyncTask<Void, Void, String> {
    	@Override
		protected String doInBackground(Void... arg0) {
			System.err.println("Adding wine to cellar.");
			db = new DatabaseHandler(getApplicationContext());
			String email = db.getUserDetails().get("email");
	    	WinetasticManager.removeWineFromWishlist(email, wineCodeToRemove);
	    	//System.err.println("Wine code to remove: " + wineCodeToRemove);
	    	return "";
		}
    	
    	protected void onPostExecute(String result) {
    		clearItemFromAdapter(adapterClearPosition);
    		Toast.makeText(WineWishList.this, wineNameToRemove + " has been removed from your wishlist", Toast.LENGTH_SHORT).show();
    	}
    }
    
	
}
