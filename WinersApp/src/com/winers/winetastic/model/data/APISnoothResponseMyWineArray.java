package com.winers.winetastic.model.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class APISnoothResponseMyWineArray {
	
	public String region;
	
	@SerializedName("snoothrank")
	public String snoothRank;
	
	@SerializedName("num_merchants")
	public String numMerchants;
	public String vintage;
	public String link;
	public String image;
	public String code;
	public String type;
	public String varietal;
	public String price;
	public String tags;
	public String wishlist;
	
	@SerializedName("my-rating")
	public String my_rating;
	
	@SerializedName("my-review")
	public String my_review;
	public String cellared;
	public String cellar_location;
	public String cellar_currency;
	public String cellar_value;
	public String cellar_format;
	public String date_activity;
	
	
	public List<String> lists;
	
	public String name;
	
	
	@SerializedName("winery_id")
	public String wineryID;
	public String winery;
	
	
}
