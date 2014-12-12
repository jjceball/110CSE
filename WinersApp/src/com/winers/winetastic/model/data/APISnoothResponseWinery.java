package com.winers.winetastic.model.data;

import com.google.gson.annotations.SerializedName;


/**
 * Class to store the details of a winery from a "Winery Details" Snooth 
 * API call.
 * 
 * To get a populated APISnoothResponseWinery object in your code, you'll first
 * have to either do a search to get a wineryID or use one from a previous
 * search.
 * 
 * Remember you have to do the API call from the doInBackground() method of
 * a class that extends AsyncTask. Look at WineSearch.java for an example.
 * 
 */
public class APISnoothResponseWinery {
	
	@SerializedName("winery")
	public APISnoothResponseWineryDetails wineryDetails;
	
	// This class is in APISnoothResponse.java for now...
	@SerializedName("meta")
	public APISnoothResponseMetaData metaResults; 
	
}

