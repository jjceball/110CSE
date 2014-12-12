package com.winers.winetastic.model.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class APISnoothResponseMyWines {
	@SerializedName("wines")
	public List<APISnoothResponseMyWineArray> myWineResults;
	
	@SerializedName("meta")
	public APISnoothResponseMetaData metaResults;
	
}




