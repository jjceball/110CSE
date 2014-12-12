package com.winers.winetastic.model.data;

import com.google.gson.annotations.SerializedName;

/*
 * Helper class for APISnoothResponseWinery to store the actual winery details
 */
public class APISnoothResponseWineryDetails {
	public String id;
    public String name;
    
    @SerializedName("num_wines")
    public String numWines;
    public String address;
    public String city;
    public String state;
    public String zip;
    public String country;
    public String phone;
    public String url;
    public String email;
    public String closed;
    public String lat;
    public String lng;
    public String image;
    public String description;
    
    public APISnoothResponseWineryDetails() {
    	id = "";
        name = "";
        numWines = "";
        address = "";
        city = "";
        state = "";
        zip = "";
        country = "";
        phone = "";
        url = "";
        email = "";
        closed = "";
        lat = "";
        lng = "";
        image = "";
        description = "";
    }
}