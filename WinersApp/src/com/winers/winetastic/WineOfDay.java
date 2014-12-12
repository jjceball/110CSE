package com.winers.winetastic;

import com.google.gson.Gson;
import com.winers.winetastic.R;
import com.winers.winetastic.R.color;
import com.winers.winetastic.R.id;
import com.winers.winetastic.R.layout;
import com.winers.winetastic.model.data.APISnoothResponseWineArray;
import com.winers.winetastic.model.manager.ImageLoader;
import com.winers.winetastic.model.manager.NetworkTaskManager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Displays information about the random wine of the day.
 * 
 * @author Helena
 *
 */
public class WineOfDay extends Fragment {	
	
	private TableLayout					statsTable;
	private	APISnoothResponseWineArray 	info;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get wine details from caller
    	Gson gson = new Gson();
    	this.info = gson.fromJson(getArguments().getString("wine_data"), APISnoothResponseWineArray.class);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = (View) inflater.inflate(R.layout.fragment_wine_of_day, container, false);
        
		Button moreInfoButton = (Button) rootView.findViewById(R.id.info_button_more_info);
		
		moreInfoButton.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity().getBaseContext(), WineInfoPage.class);
				i.putExtra("wine_data", getArguments().getString("wine_data", "wineArraySerialized"));
				startActivity(i);
			}  	
	    });
	        
        Button addToWishlistButton = (Button) rootView.findViewById(R.id.info_button_add_wishlist);
        Button addToCellarButton = (Button) rootView.findViewById(R.id.info_button_add_cellar);
        
        addToWishlistButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				NetworkTaskManager.addToWishlist(getActivity(), info.name, info.code);
			}
		});	
        
        addToCellarButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				NetworkTaskManager.addToCellar(getActivity(), info.name, info.code);
			}
		});	
        
        // Get wine detail table elements
        statsTable = (TableLayout) rootView.findViewById(R.id.info_module_wod_statistics);
        
        
        // Set image
        ImageView img = (ImageView) rootView.findViewById(R.id.imageView2);
        ImageLoader.loadFromWeb(getArguments().getString("random_wine_image", "random_wine_image"), img);
        
        
        // Populate table
        if(getArguments().getString("random_wine_name","random_wine_name") != null) 
        	addRow(statsTable, Html.fromHtml("Name").toString(), getArguments().getString("random_wine_name","random_wine_name"));    
        if(getArguments().getString("random_wine_price","random_wine_price") != null) 
        	addRow(statsTable, "Price", getArguments().getString("random_wine_price","random_wine_price"));    
        if(getArguments().getString("random_wine_price","random_wine_price") != null) 
        	addRow(statsTable, Html.fromHtml("Region").toString(), getArguments().getString("random_wine_region","random_wine_region"));    
        if(getArguments().getString("random_wine_price","random_wine_price") != null) 
        	addRow(statsTable, "Varietal", getArguments().getString("random_wine_varietal","random_wine_varietal"));    
        if(getArguments().getString("random_wine_price","random_wine_price") != null) 
        	addRow(statsTable, "Type", getArguments().getString("random_wine_type","random_wine_type"));    
        if(getArguments().getString("random_wine_price","random_wine_price") != null) 
        	addRow(statsTable, "Vintage", getArguments().getString("random_wine_vintage","random_wine_vintage"));
		
        return rootView;
    }
    
    /**
     * Attaches a row to a TableLayout.
     * 
     * @param parent  the TableLayout view to attach the row to
     * @param cols    text to be added to the row
     * 
     * @author Victoria
     */
    protected void addRow(View parent, String ... cols) {
    	// Get width of parent view
    	
    	// Create a table row
    	TableRow r = (TableRow) getActivity().getLayoutInflater().inflate(R.layout.info_row, null);
    	
    	for(int i = 0; i < cols.length; i++) {
    		
    		// Add each string to the row
    		TextView text = new TextView(getActivity());
    		
    		// Bold the first string
    		if(i == 0 && cols.length > 1) {
    			text.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
    			text.setWidth(100);
    		}
    		
    		// Set contents of text, make black, change width
    		text.setText(cols[i], TextView.BufferType.NORMAL);
    		text.setTextColor(getResources().getColor(R.color.black));
    		text.setWidth(140);

    		r.addView(text);
    	}    	
    	((TableLayout)parent).addView(r);
    }

}
