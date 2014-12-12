package com.winers.winetastic;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public abstract class AbstractFragmentActivity extends FragmentActivity {

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
//	  System.err.println("(FragmentAbstractActivity) onCreate() called");
	  super.onCreate(savedInstanceState);
//	  System.err.println("FragmentAbstractActivity: super.onCreate successful.");	  
	  //check if customTitlebar is supported.
	  final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);        
	  setContentView(R.layout.mycustomtitle);
	  		if ( customTitleSupported ) {
	        	// if customTitlebar is supports, set the titlebar layout for it.
	            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.mycustomtitle);
	        }
	        
	        final TextView myTitleText = (TextView) findViewById(R.id.my_title);
	        ImageButton homeButton = (ImageButton)findViewById(R.id.home_button);
	        homeButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(AbstractFragmentActivity.this, Home.class);
					startActivity(i);
					
				}
	        	
	        });
//	        System.err.println("Before" + getTitleText() + "===========");
	        if ( myTitleText != null ) {
//	        	System.err.println("Makes it here" + getTitleText() + "===========");
	        	myTitleText.setText(getText(getTitleText()));
	        }
	       
	        
//	  System.err.println("Exiting AbstractActivity onCreate method" + getTitleText() + "<-----");
	 }
	 
	 /**
	  * Implement this method to return a string resource id from the strings.xml file
	  * 
	  * @return
	  */
	 protected abstract int getTitleText() ;

}
