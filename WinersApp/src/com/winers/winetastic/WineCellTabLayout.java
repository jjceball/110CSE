package com.winers.winetastic;


import com.winers.winetastic.R;
import com.winers.winetastic.R.layout;
import com.winers.winetastic.R.string;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class WineCellTabLayout extends AbstractActivity {
	
	private String myWinesQuery;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_cell_tab_layout);
         
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        //TabHost tabHost = getTabHost();
        System.err.println("testing");
        myWinesQuery = (String) getIntent().getExtras().get("MyWines Query");
		
		//System.err.println("MY WINES QUERY FROM INSIDE WINECELL: " + myWinesQuery);
        
        
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(mLocalActivityManager);
        //Toast.makeText(this, "tabhost: "+ tabHost, Toast.LENGTH_SHORT).show();
        
        // Tab for MyWines
        TabSpec myWinesTab = tabHost.newTabSpec("My Wines");
        // setting Title and Icon for the Tab
        myWinesTab.setIndicator("Wine Collection");//, getResources().getDrawable(R.drawable.wines));
        Intent myWinesIntent = new Intent(this, MyWines.class);
        myWinesIntent.putExtra("MyWines Query", myWinesQuery);
        myWinesTab.setContent(myWinesIntent);
         
        // Tab for Wishlist 
        TabSpec wishListTab = tabHost.newTabSpec("Wish List");        
        wishListTab.setIndicator("Wish List");//, getResources().getDrawable(R.drawable.wishlist));
        
        Intent wishListIntenet = new Intent(this, WineWishList.class);
        wishListIntenet.putExtra("MyWines Query", myWinesQuery);
        wishListTab.setContent(wishListIntenet);
         
        // Adding all TabSpec to TabHost
        tabHost.addTab(myWinesTab); // Adding photos tab
        tabHost.addTab(wishListTab); // Adding songs tab
        
    }

	@Override
	protected int getTitleText() {
		// TODO Auto-generated method stub
		return R.string.my_wines_title;
	}	
	
	
	

}
