package com.winers.winetastic.model.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Class to store a list of wines returned by a search
 * 
 * Remember you have to do the API call from the doInBackground() method of
 * a class that extends AsyncTask. Look at WineSearch.java for an example.
 * 
 */
public class APISnoothResponse {
	
	@SerializedName("wines")
	public List<APISnoothResponseWineArray> wineResults;
	
	@SerializedName("meta")
	public APISnoothResponseMetaData metaResults;
	
}