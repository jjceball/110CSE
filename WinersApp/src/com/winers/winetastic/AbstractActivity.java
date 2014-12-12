package com.winers.winetastic;

import com.winers.winetastic.R;
import com.winers.winetastic.model.manager.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public abstract class AbstractActivity extends Activity {

	protected UserFunctions uF;
	private boolean isLoggedIn;
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  System.err.println("(AbstractActivity) onCreate() called");
	  isLoggedIn = true;
      uF = new UserFunctions();
      if (!uF.isUserLoggedIn(getApplicationContext())) {
    	isLoggedIn = false;
//      	Intent i = new Intent(AbstractActivity.this, Intro.class);
//		startActivity(i);
      }
	  super.onCreate(savedInstanceState);
	  System.err.println("AbstractActivity: super.onCreate successful.");	  
	  //check if customTitlebar is supported.
	  final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);        
	  setContentView(R.layout.mycustomtitle);
	  		if ( customTitleSupported ) {
	        	// if customTitlebar is supports, set the titlebar layout for it.
	            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.mycustomtitle);
	        }
	        
	        final TextView myTitleText = (TextView) findViewById(R.id.my_title);
	        ImageButton homeButton = (ImageButton)findViewById(R.id.home_button);
	        ImageButton logoutButton = (ImageButton)findViewById(R.id.logout_button);
	        if(!isLoggedIn) logoutButton.setVisibility(View.GONE);
	        homeButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(AbstractActivity.this, Home.class);
					startActivity(i);
					
				}
	        	
	        });
	        
	        logoutButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					uF.logoutUser(getApplicationContext());
					Toast.makeText(AbstractActivity.this, "You have been logged out", Toast.LENGTH_LONG).show();
					Intent i = new Intent(AbstractActivity.this, Intro.class);
					startActivity(i);
					
				}
	        	
	        });
	        
	        
	        System.err.println("Before" + getTitleText() + "===========");
	        if ( myTitleText != null ) {
	        	System.err.println("Makes it here" + getTitleText() + "===========");
	        	myTitleText.setText(getText(getTitleText()));
	        }
	       
	        
	  System.err.println("Exiting AbstractActivity onCreate method" + getTitleText() + "<-----");
	 }
	 
	 /**
	  * Implement this method to return a string resource id from the strings.xml file
	  * 
	  * @return
	  */
	 protected abstract int getTitleText() ;
}
