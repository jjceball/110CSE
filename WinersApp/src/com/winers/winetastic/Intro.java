package com.winers.winetastic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.winers.winetastic.model.manager.UserFunctions;
import com.winers.winetastic.view.LoginView;
import com.winers.winetastic.view.RegisterAccountView;
public class Intro extends Activity {
	
	private UserFunctions uF;
	private Handler mHandler = new Handler();	// Handles background rotation	
	private int currentFrame = 0;				// The current background image
	private static final int TRANSITION_TIME = 5000; // Milliseconds b/w bgs

    ImageView browse; 	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        uF = new UserFunctions();
        
        // Check if user is logged in. If so, redirect to Home screen
        /*
         * Uncomment the following to automatically take logged in user to
         * Home screen when app starts
         *
         */
        if (uF.isUserLoggedIn(getApplicationContext())) {
        	Intent i = new Intent(Intro.this, Home.class);
			startActivity(i);
        }
        
        
        setContentView(R.layout.activity_intro);
        
        if (!isOnline()) {
            new AlertDialog.Builder(this).setTitle("Internet Connection Required").setMessage("You must have an active internet connection to use this app. Please connect to the internet before pressing OK.").setPositiveButton("OK", null).show();  
    	}
        
        // Views   
        Button register = (Button)findViewById(R.id.guest_register_button);
        browse = (ImageView)findViewById(R.id.guest_find_wines); 	        
        
        // Click event: Go to the Browse Wines module
        browse.setOnClickListener(new View.OnClickListener(){
    		@Override
    		public void onClick(View v) {
    			System.out.println("baaahhh");
    			Intent i = new Intent(Intro.this, WineSearch.class);
    			startActivity(i);
    		}
        });  
        
        
     // Click event: Go to the Home Screen
        register.setOnClickListener(new View.OnClickListener(){
    		@Override
    		public void onClick(View v) {
        		System.err.println("Detected click for REGISTER.");    			
    			Intent i = new Intent(Intro.this, RegisterAccountView.class);
    			startActivity(i);
    		}
        });
        
        // Rotates background every x seconds 
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(8000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                changeBackground();
                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        }).start();        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.winers_app_main, menu);
        return true;
    }
    
    /** Use Case: Login Window
     * Author: Victoria Do
     * Controls opening and closing the Login window.
     * @param view  The view to be filled with the login layout
     */
    
    
    public void showLogin(View view) { 	
    	Intent intent = new Intent();
    	intent.setClass(this, LoginView.class);
    	startActivity(intent);
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
    
    /* Controls background rotation. 
     * Author: Victoria Do
     */
    private void changeBackground() { 	   	
    	RelativeLayout layout = (RelativeLayout) findViewById(R.id.intro_layout);
    	TransitionDrawable td = (TransitionDrawable) layout.getBackground().getCurrent();
    	if(currentFrame == 0) {
    		td.startTransition(TRANSITION_TIME);    	
    		currentFrame++;
    	} else {
    		td.reverseTransition(TRANSITION_TIME);
    		currentFrame--;
    	}
    }  
}

