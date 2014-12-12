package com.winers.winetastic.model.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.util.Log;

import com.google.gson.Gson;

public class SystemDAO {
	private final String API_KEY = "7sft6abh56pjc52byts04mq9vpok18ufzksn5r4g92amybdy";
	private final String SNOOTH_URL = "http://api.snooth.com";
	private final String WINE_RESOURCE_ID = "/wines/";
	private final String WINERY_RESOURCE_ID = "/winery/";
	private final String MY_WINES_RESOURCE_ID = "/my-wines/";
	private final String RATE_WINE_RESOURCE_ID = "/rate/";
	private final String RANDOM_STRING = "k8d9j5h8g4u7tr4";
	private final int API_RETVAL_LENGTH_FOR_ERROR = 120;
	

	
	/**
	 * performSearch takes two parameters and returns a JSON string
	 * with the result of the API call.
	 * @param searchArgs: An ArrayList of strings of search arguments
	 * @param numResults: The maximum number of results to return
	 */
	public String performQuickSearch(ArrayList<String> searchArgs,
								int numResults) {
		
		String url = SNOOTH_URL + WINE_RESOURCE_ID + "?akey=" + API_KEY;
		url += "&n=" + numResults;
		url += "&t=wine";
		url += "&a=1";
		url += "&q=";
		for (String arg : searchArgs) {
			url += arg + "+";
		}
		
		// take off last "+"
		url = url.substring(0, (url.length()-1));
        return callSnoothAPIWineSearch(url);
	}
	
	
	
	/**
	 * performAdvancedSearch takes two parameters and returns a JSON string
	 * containing the result of the API call.
	 * 
	 * @param searchParameters: The object containing the search parameters
	 * @param numResults: The maximum number of results to return
	 */
	public String performAdvancedSearch(WineSearchObject searchParameters,
										int numResults) {
		
		String url = SNOOTH_URL + WINE_RESOURCE_ID + "?akey=" + API_KEY;
		
		url += "&a=1";
	
		if (searchParameters.getType() != "") {
			url += "&q=" + searchParameters.getType();
			if (searchParameters.getAccent() != "")
				url += "+" + searchParameters.getAccent();
		}
		else if (searchParameters.getAccent() != "")
			url += "&q=" + searchParameters.getAccent();
		
		url += "&n=" + numResults;
		if (searchParameters.getColor() != "") 
			url += "&color=" + searchParameters.getColor();
		if (searchParameters.getPrice() != "") {
			url += searchParameters.parsePriceString();
			url += "&s=price+desc";
			
		}
		url += "&t=wine";

		
		return callSnoothAPIWineSearch(url);
	}
	
	public String performCombinedSearch(WineSearchObject searchParameters, 
								 int numResults){
		String url = SNOOTH_URL + WINE_RESOURCE_ID + "?akey=" + API_KEY;
		
		boolean addedPlus = false;
		
		url += "&a=1";
	
		if (!searchParameters.getType().equals("") && searchParameters.getType() != null) {
			System.err.println("Printed from type");
			url += "&q=" + searchParameters.getType() + "+";
			addedPlus = true;
			if (!searchParameters.getAccent().equals("") && searchParameters.getAccent() != null)
			{
				System.err.println("Printed from accent with type");
				url += searchParameters.getAccent() + "+";
				addedPlus = true;
			}
		}
		else if (!searchParameters.getAccent().equals("") && searchParameters.getAccent() != null)
		{
			System.err.println("Printed from accent only");
			url += "&q=" + searchParameters.getAccent() + "+";
			addedPlus = true;
		}
		else{
			if (searchParameters.stringList.size() > 0){
				if (!searchParameters.stringList.get(0).equals("")) {
					url += "&q=";
				}
			}
		}
		for (String arg : searchParameters.stringList) {
			System.err.println("Adding searchList parameter to search URL");
			url += arg + "+";
			addedPlus = true;
		}
		
		// take off last "+"
		if (addedPlus)
			url = url.substring(0, (url.length()-1));
		
		url += "&n=" + numResults;
		if (!searchParameters.getColor().equals("")) 
			url += "&color=" + searchParameters.getColor();
		if (!searchParameters.getPrice().equals("")) {
			url += searchParameters.parsePriceString();
			url += "&s=price+asc";
		}
		url += "&t=wine";
		url += "&f=" + searchParameters.firstResult;
		
		System.err.println("Searching with URL: " + url);
		
		return callSnoothAPIWineSearch(url);
	}
	
	public boolean isWineInWishlist(String email, String wineID) {
		String url = SNOOTH_URL + MY_WINES_RESOURCE_ID + "?akey=" + API_KEY;
		url += "&u=" + RANDOM_STRING + email;
		url += "&p=" + RANDOM_STRING;
		Gson gson = new Gson();
		InputStream source = retrieveStream(url);  
		if (source != null) {
	        Reader reader = new InputStreamReader(source);
	        APISnoothResponseMyWines snoothResponse = gson.fromJson(reader, APISnoothResponseMyWines.class);
	        boolean wineFound = false;
	        List<APISnoothResponseMyWineArray> wineArray = snoothResponse.myWineResults;
	        if (wineArray == null) return false;
	        APISnoothResponseMyWineArray wineComparator = new APISnoothResponseMyWineArray();
	        for (APISnoothResponseMyWineArray wine : wineArray) {
	        	if (wine.code.equals(wineID)) {
	        		wineFound = true;
	        		wineComparator = wine;
	        		break;
	        	}
	        }
	        if (wineFound) {
	        	if (wineComparator.wishlist.equals("1")) {
	        		return true;
	        	}
	        }
	    return false;
		} else {
			return false;
		}
	}
	
	public boolean isWineInCellar(String email, String wineID) {
		String url = SNOOTH_URL + MY_WINES_RESOURCE_ID + "?akey=" + API_KEY;
		url += "&u=" + RANDOM_STRING + email;
		url += "&p=" + RANDOM_STRING;
		Gson gson = new Gson();
		InputStream source = retrieveStream(url);  
		if (source != null){
	        Reader reader = new InputStreamReader(source);
	        APISnoothResponseMyWines snoothResponse = gson.fromJson(reader, APISnoothResponseMyWines.class);
	        boolean wineFound = false;
	        List<APISnoothResponseMyWineArray> wineArray = snoothResponse.myWineResults;
	        if (wineArray == null) return false;
	        APISnoothResponseMyWineArray wineComparator = new APISnoothResponseMyWineArray();
	        for (APISnoothResponseMyWineArray wine : wineArray) {
	        	if (wine.code.equals(wineID)) {
	        		wineFound = true;
	        		wineComparator = wine;
	        		break;
	        	}
	        }
	        if (wineFound) {
	        	if (wineComparator.cellared.equals("2")) {
	        		return true;
	        	}
	        }
	        return false;
		}else {
			return false; // The method returns false if Snooth is down
		}
	}
	
	public void addWineToWishlist(String email, String wineID) {
		String url = SNOOTH_URL + RATE_WINE_RESOURCE_ID + "?akey=" + API_KEY;
		url += "&u=" + RANDOM_STRING + email;
		url += "&p=" + RANDOM_STRING;
		url += "&id=" + wineID;
		url += "&w=1";
		
		retrieveStream(url);
	}
	
	public void addWineToCellar(String email, String wineID) {
		String url = SNOOTH_URL + RATE_WINE_RESOURCE_ID + "?akey=" + API_KEY;
		url += "&u=" + RANDOM_STRING + email;
		url += "&p=" + RANDOM_STRING;
		url += "&id=" + wineID;
		url += "&c=2";
		
		retrieveStream(url);
	}
	
	public void removeWineFromWishlist(String email, String wineID) {
		String url = SNOOTH_URL + RATE_WINE_RESOURCE_ID + "?akey=" + API_KEY;
		url += "&u=" + RANDOM_STRING + email;
		url += "&p=" + RANDOM_STRING;
		url += "&id=" + wineID;
		url += "&w=0";
		
		retrieveStream(url);
	}
	
	public void removeWineFromCellar(String email, String wineID) {
		String url = SNOOTH_URL + RATE_WINE_RESOURCE_ID + "?akey=" + API_KEY;
		url += "&u=" + RANDOM_STRING + email;
		url += "&p=" + RANDOM_STRING;
		url += "&id=" + wineID;
		url += "&c=1";
		System.err.println("URL TO REMOVE WINE: ");
		System.err.println(url);
		retrieveStream(url);
	}
	
	
	public String returnMyWines(String email) {
		String url = SNOOTH_URL + MY_WINES_RESOURCE_ID + "?akey=" + API_KEY;
		url += "&u=" + RANDOM_STRING + email;
		url += "&p=" + RANDOM_STRING;
		return callSnoothAPIMyWines(url);
	}
	
	/**
	 * getRandomWine returns a JSON string containing a single random wine
	 */
	public String getRandomWine() {
		Random rng = new Random();
		final int MAX_INT = 1000;
		int randomInt = rng.nextInt(MAX_INT);
		
		String url = SNOOTH_URL + WINE_RESOURCE_ID + "?akey=" + API_KEY;
		url += "&n=1";
		url += "&a=1";
		url += "&f=" + randomInt;
		
		System.err.println("getRandomWine: " + url);
		return callSnoothAPIWineSearch(url);
	}
	
	/**
	 * getWineryDetails returns a JSON string containing the details of the
	 * winery specified by wineryID.
	 * The wineryID here is the same as the winery_id returned by a wine search
	 * API call which gets stored in an APISnoothResponse object.
	 */
	public String getWineryDetails(String wineryID) {
		String url = SNOOTH_URL + WINERY_RESOURCE_ID + "?akey=" + API_KEY;
		url += "&id=" + wineryID;
		
		System.err.println("url: "+ url);
		System.err.println("callSnoothAPI(url) = " + callSnoothAPIWinerySearch(url));
		return callSnoothAPIWinerySearch(url);
	}

	/**
	 * hasSearchResults returns true if the JSON response string has more
	 * than API_RETVAL_LENGTH_FOR_ERROR characters, meaning that the search 
	 * had at least one result.
	 * @param searchResultString: the resulting string of the API call
	 */
	public Boolean hasSearchResults(String searchResultString) {
		return (searchResultString.length() > API_RETVAL_LENGTH_FOR_ERROR);
	}
	
	private String callSnoothAPIMyWines(String url){
		// For converting to and from JSON/Java objects
		Gson gson = new Gson();
		// Make API call
		InputStream source = retrieveStream(url);
		
		if (source != null) {
			//System.err.println("callSnoothAPI: " + url);
	        Reader reader = new InputStreamReader(source);
		    
	        // Convert JSON object to Java object
	        APISnoothResponseMyWines snoothResponse = gson.fromJson(reader, APISnoothResponseMyWines.class);
	        
	        // Return JSON array
	        return gson.toJson(snoothResponse);
		} else {
			return "";
		}
		
	}
	
	private String callSnoothAPIWineSearch(String url) {
		
		// For converting to and from JSON/Java objects
		Gson gson = new Gson();
		// Make API call
		InputStream source = retrieveStream(url);  
		
		if (source != null) {
			//System.err.println("callSnoothAPI: " + url);
	        Reader reader = new InputStreamReader(source);
		    
	        // Convert JSON object to Java object
	        APISnoothResponse snoothResponse = gson.fromJson(reader, APISnoothResponse.class);
	        
	        // Return JSON array
	        return gson.toJson(snoothResponse);
        
			} else 
		return "";
	}
	
private String callSnoothAPIWinerySearch(String url) {
		
		// For converting to and from JSON/Java objects
		Gson gson = new Gson();
		// Make API call
		InputStream source = retrieveStream(url);  
		//System.err.println("callSnoothAPI: " + url);
		if (source != null) {
	        Reader reader = new InputStreamReader(source);
		    
	        // Convert JSON object to Java object
	        APISnoothResponseWinery snoothResponseWinery = gson.fromJson(reader, APISnoothResponseWinery.class);
	        
	        // Return JSON array
	        return gson.toJson(snoothResponseWinery);
		} else {
			return "";
		}
	}
	
	private InputStream retrieveStream(String url) {
    	
    	DefaultHttpClient client = new DefaultHttpClient(); 
        
        HttpGet getRequest = new HttpGet(url);
          
        try {
           
           HttpResponse getResponse = client.execute(getRequest);
           final int statusCode = getResponse.getStatusLine().getStatusCode();
           
           if (statusCode != HttpStatus.SC_OK) { 
              Log.w(getClass().getSimpleName(), "Error " + statusCode + " for URL " + url); 
              return null;
           }

           HttpEntity getResponseEntity = getResponse.getEntity();
           return getResponseEntity.getContent();
           
        } 
        catch (IOException e) {
           getRequest.abort();
           Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
           System.err.println(e.toString());
        }
        
        return null;  
     }
}
