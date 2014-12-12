package com.winers.winetastic;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

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
import com.winers.winetastic.model.manager.NetworkTaskManager;

public class MyWines extends ListActivity {

	private ArrayList<ArrayList<String>> wines;
	private String myWinesQuery;
	private String wineCodeToRemove;
	private boolean removeMode = false;
	private int adapterClearPosition;
	SearchResultsController adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_wines);
		
//        //Convert back to POJO
//        final Gson gson = new Gson();
//        final APISnoothResponse snoothResponse = gson.fromJson(searchQuery, APISnoothResponse.class);
//        final List<APISnoothResponseWineArray> wineAPIResponse = snoothResponse.wineResults;
        System.err.println("Got this far");
		
		myWinesQuery = (String) getIntent().getExtras().get("MyWines Query");
		
		//System.err.println("MY WINES QUERY FROM INSIDE MY WIIIIIINESL: " + myWinesQuery);
		
		final Gson gson = new Gson();
        final APISnoothResponseMyWines myWinesResponse = gson.fromJson(myWinesQuery, APISnoothResponseMyWines.class);
        final List<APISnoothResponseMyWineArray> winesAPIResponse = myWinesResponse.myWineResults;
		
        wines = new ArrayList<ArrayList<String>>();

        insertWines(winesAPIResponse);
		adapter = new SearchResultsController(this, wines);
		getListView().setAdapter(adapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int pos,
					long id) {			
				
				adapterClearPosition = pos;
				// HOLY SHIT HERE WE GO
				
				List<APISnoothResponseWineArray> wineArrayForInfoPage = new ArrayList<APISnoothResponseWineArray>();
				APISnoothResponseWineArray tempArray;
				for (APISnoothResponseMyWineArray wineZZZ : winesAPIResponse) {
		    		if (wineZZZ.cellared.equals("2")) {
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
					Intent i = new Intent(MyWines.this, WineInfoPage.class);
					String wineArraySerialized = gson.toJson(wineArrayForInfoPage.get(pos));
					
	
					//List<APISnoothResponseWineArray> wineAPIResponse = snoothResponse.wineResults;	
					//String wineArraySerialized = gson.toJson(wineAPIResponse.get(pos));
	
					i.putExtra("wine_data", wineArraySerialized);
					startActivity(i);
				}
				else { // REMOVE MODE
					wineCodeToRemove = wineArrayForInfoPage.get(pos).code;
					String wineNameToRemove = wineArrayForInfoPage.get(pos).name;
					adapterClearPosition = pos;
					
					NetworkTaskManager.removeFromCellar(MyWines.this, wineCodeToRemove);
					clearItemFromAdapter(adapterClearPosition);
					Toast.makeText(MyWines.this, wineNameToRemove + " has been removed from your cellar", Toast.LENGTH_SHORT).show();  					
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
		getMenuInflater().inflate(R.menu.activity_search_results, menu);
		return true;
	}


	protected int getTitleText() {
		// TODO Auto-generated method stub
		return R.string.my_wines_title;
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
			Intent i = new Intent(MyWines.this, Home.class);
			i.putExtra("mywines_reload", "true");
			startActivity(i);
			*/
		}
	}
	
	
    public void insertWines (List<APISnoothResponseMyWineArray> winesArray) {
    	
    	ArrayList<String> temp;
    	
    	for (APISnoothResponseMyWineArray wineZZZ : winesArray) {
    		if (wineZZZ.cellared.equals("2")) {
		    	temp = new ArrayList<String>();
		    	temp.add(wineZZZ.name);
	    		temp.add(wineZZZ.region);
	    		temp.add(wineZZZ.price);
	    		temp.add(wineZZZ.image);
		    	wines.add(temp);
    		}
    	}
    }
}
