package com.winers.winetastic.model.data;
import com.google.gson.annotations.SerializedName;

public class APISnoothResponseWineArray {
	public String region;
	
	
	
	@SerializedName("snoothrank")
	public String snoothRank;
	
	@SerializedName("num_merchants")
	public String numMerchants;
	public String vintage;
	public String link;
	public String image;
	public String available;
	public String code;
	public String type;
	public String varietal;
	public String price;
	
	@SerializedName("num_reviews")
	public String numReviews;
	public String name;
	
	@SerializedName("winery_id")
	public String wineryID;
	public String winery;
	
	public APISnoothResponseWineArray() {
		 region = "";	
		 snoothRank = "";	
		 numMerchants = "";
		 vintage = "";
		 link = "";
		 image = "";
		 available = "";
		 code = "";
		 type = "";
		 varietal = "";
		 price = "";
		 numReviews = "";
		 name = "";		
		 wineryID = "";
		 winery = "";
	}
	
	
}