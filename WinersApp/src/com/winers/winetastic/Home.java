
package com.winers.winetastic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.winers.winetastic.R;
import com.winers.winetastic.R.id;
import com.winers.winetastic.R.layout;
import com.winers.winetastic.R.menu;
import com.winers.winetastic.R.string;
import com.winers.winetastic.model.data.FunFact;
import com.winers.winetastic.model.manager.NetworkTaskManager;
import com.winers.winetastic.model.manager.SystemManager;
import com.winers.winetastic.model.manager.UserFunctions;

public class Home extends AbstractActivity {

	private UserFunctions uF;
	FunFact random; 

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	System.err.println("Attempting to create");
    	System.out.println("hello");
        super.onCreate(savedInstanceState);
        uF = new UserFunctions();
        if (!uF.isUserLoggedIn(getApplicationContext())) {
        	Intent i = new Intent(Home.this, Intro.class);
			startActivity(i);
        }
        
        
        
    	System.err.println("Created. Getting layout...");          
        setContentView(R.layout.activity_main);
    	System.err.println("Got layout.");   
    	
    	 if (!isOnline()) {
             new AlertDialog.Builder(this).setTitle("Internet Connection Required").setMessage("You must have an active internet connection to use this app. Please connect to the internet before pressing OK.").setPositiveButton("OK", null).show();  
     	}
    	
    	random = new FunFact(); 
    	TextView text = (TextView) findViewById(R.id.randButton); 
    	text.setText("\tFun Fact: "+random.randomFact() + "\t\n "); 
    	
    	ImageButton homeButton = (ImageButton) findViewById(R.id.home_button);
    	homeButton.setVisibility(View.GONE);
        
    	Button search_but = (Button)findViewById(R.id.search);
        Button my_wines_but = (Button)findViewById(R.id.myWines);
        Button cal_but = (Button)findViewById(R.id.calendarView1);
        Button map_but = (Button)findViewById(R.id.map);
        ImageButton daily_vine_but = (ImageButton)findViewById(R.id.dailyVineButton);
        
        // search
        search_but.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this, WineSearch.class);
				startActivity(i);
			}
        });
        
        // my wines
        my_wines_but.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NetworkTaskManager.startMyWinesIntent(Home.this);
				
			}
        });

        // event calendar 
        
        cal_but.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this, EventCalendar.class);
				startActivity(i);
			}
        });
        
        // map
        map_but.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				if (!SystemManager.isOnline(getApplicationContext())) {
					Toast.makeText(getApplicationContext(), "You must be connected to the Internet to use this feature", Toast.LENGTH_SHORT).show();
				} else {
					String url = "http://google.com/maps?q=wineries"; 
		        	Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		            startActivity(i); // Go go go!
				}
			}
        });
        
        // daily vine
        daily_vine_but.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// Start AsyncTask to perform network operation (API call)
				NetworkTaskManager.startDailyVineIntent(Home.this);
			}  	
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	protected int getTitleText() {
		// TODO Auto-generated method stub
		return R.string.app_name;
	}
	
	// Tests for internet connectivity
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
    
}
