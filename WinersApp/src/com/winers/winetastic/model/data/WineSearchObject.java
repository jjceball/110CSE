package com.winers.winetastic.model.data;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;


import com.google.gson.Gson;
/***
 * 
 * @author Jack
 * 
 * This is a class for an object to pass to the search functions.
 * This object should be used to perform a search via the search bar
 * and advanced search (browse)
 * 
 */

public class WineSearchObject implements Serializable {
	private String 				color;
	private String 				price;
	private String 				type;
	private String 				accent;
	public int					firstResult;
	public ArrayList<String> 	stringList;
	static final long 			serialVersionUID = 927482;
	
	public WineSearchObject () {
		color = "";
		price = "";
		type = "";		
		accent = "";
		firstResult = 1;
		stringList = new ArrayList<String>();
	}
	
	
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setAccent(String accent) {
		this.accent = accent;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public String getPrice() {
		return this.price;
	}
	
	public String parsePriceString() {
		String retVal = new String();
		if (price.equals("")) retVal = "";
		else if (price.equals("Less than $15/bottle")) retVal = "&xp=15";
		else if (price.equals("$15 - $50/bottle")) retVal = "&mp=15.01&xp=50";
		else if (price.equals("$50 - $150/bottle")) retVal = "&mp=50.01&xp=150";
		else retVal = "&mp=150.01";
		return retVal;
	}
	
	public String getType() {
		if (type == "Sauvignon Blanc") return "sauvignon+blanc";
		else if (this.type == "Pinot Gris") return "pinot+gris";
		else if (this.type == "White Zinfandel") return "white+zinfandel";
		else if (this.type == "Sweet Sherry") return "sweet+sherry";
		else if (this.type == "Pinot Noir") return "pinot+noir";
		else if (this.type == "Cabernet Sauvignon") return "cabernet+sauvignon";
		return this.type;
	}
	
	public String getAccent() {
		return this.accent;
	}
	
	
	public void clear() {
		color = "";
		price = "";
		type = "";		
		accent = "";
	}
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
	
	public APISnoothResponse performSearch() throws IOException, JSONException {
		JSONObject json = readJsonFromUrl("http://api.snooth.com/wines/?akey=7sft6abh56pjc52byts04mq9vpok18ufzksn5r4g92amybdy&ip=66.75.24.166&q=napa+port&n=5&color=red&t=wine");
		Gson gson = new Gson();
		APISnoothResponse snoothResponse = new APISnoothResponse();
	    snoothResponse = gson.fromJson(json.toString(), APISnoothResponse.class);
	    return snoothResponse;
	   
	}
}