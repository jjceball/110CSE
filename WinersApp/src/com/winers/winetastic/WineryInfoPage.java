package com.winers.winetastic;

import com.google.gson.Gson;
import com.winers.winetastic.R;
import com.winers.winetastic.R.id;
import com.winers.winetastic.R.layout;
import com.winers.winetastic.model.data.APISnoothResponseWinery;
import com.winers.winetastic.model.data.APISnoothResponseWineryDetails;
import com.winers.winetastic.model.manager.ImageLoader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WineryInfoPage extends InfoPage {

	private	APISnoothResponseWineryDetails 	info;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.err.println("WineryInfoPage: Starting intent");
        
        
        // Get winery details from caller
        Bundle extras = getIntent().getExtras();    
        if(extras != null) {
        	System.err.println("extras non-null");
        	Gson gson = new Gson();
        	APISnoothResponseWinery obj = gson.fromJson(extras.getString("winery_data"), APISnoothResponseWinery.class);
        	info = obj.wineryDetails;
        }        
        
        setContentView(R.layout.activity_info_winery);  
        statsTable = (TableLayout) findViewById(R.id.info_winery_module_statistics);        
        
        // Set name
        TextView namev = (TextView) findViewById(R.id.info_winery_name);
        namev.setText(info.name, TextView.BufferType.NORMAL);      
	
        // Set image
        ImageView img = (ImageView) findViewById(R.id.info_winery_pic);        
        ImageLoader.loadFromWeb(info.image, img);	
        
        // Get address layout elements
        TextView address1 = (TextView) findViewById(R.id.info_winery_address1);        
        TextView address2 = (TextView) findViewById(R.id.info_winery_address2);         
        TextView address3 = (TextView) findViewById(R.id.info_winery_address3);         
        
        // First address line
        address1.setText(info.address, TextView.BufferType.NORMAL);
        
        // Second address line
        String addr2 = info.city;
        if(!(addr2.isEmpty()))  addr2 += ", ";
	    address2.setText(addr2 + info.state);   
	    
	    // Third address line
        if(!info.country.isEmpty()) {
        	if(!(info.zip.isEmpty())) 
        		address3.setText(info.zip);
        	else 
        		address3.setText("");
        } else {
        	if(!(info.country.equals(""))) address3.setText(info.country);
        	else address3.setText("");
        }
        
        // Set description
        TableLayout descTable = (TableLayout) findViewById(R.id.info_winery_module_desc);
        if(info.description == null || info.description.isEmpty()) {
        	addRow(descTable, "No information available.");
        } else {
        	String about = parseString(Html.fromHtml(info.description).toString(), 40, true);
        	addRow(descTable, about);        	
        }
        
        // Populate table
        int closed = Integer.valueOf((info.closed));
        if(closed != 0)					addRow(statsTable, "This winery is closed."); 
        if(!(info.phone.isEmpty()))	 	addRow(statsTable, "Number", info.phone);    
        if(!(info.email.isEmpty()))	 	addRow(statsTable, "Email", info.email);  
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_info_winery, menu);
        return true;
    }
    
    public void openURL(View v) {
    	if(info.url != null && !(info.url.isEmpty())) {
	    	Intent i = new Intent(Intent.ACTION_VIEW);
	    	i.setData(Uri.parse(info.url));
	    	startActivity(i);      
    	} else {
    		Toast.makeText(WineryInfoPage.this, "Sorry, no website available.", Toast.LENGTH_SHORT).show();
    	}
    }    
    
    public void openInMaps(View v) {
        String url = "http://google.com/maps?q=" 
        		+ info.name + "+"
        		+ info.address + "+" 
        		+ info.country;
        Intent i = new Intent(Intent.ACTION_VIEW); // Create a new intent - stating you want to 'view something'
        i.setData(Uri.parse(url));  // Add the url data (allowing android to realise you want to open the browser)
        startActivity(i); // Go go go!    	
    }
}
