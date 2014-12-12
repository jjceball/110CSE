package com.winers.winetastic;


import java.util.ArrayList;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SearchView;

import com.winers.winetastic.R;
import com.winers.winetastic.R.id;
import com.winers.winetastic.R.layout;
import com.winers.winetastic.R.string;
import com.winers.winetastic.controller.AdvancedSearchController;
import com.winers.winetastic.model.data.WineSearchObject;
import com.winers.winetastic.model.manager.NetworkTaskManager;


public class WineSearch extends AbstractActivity
implements OnChildClickListener {

	private ArrayList<String> groups = new ArrayList<String>();
	private ArrayList<Object> children = new ArrayList<Object>();
	private AdvancedSearchController searchAdapter;

	private ArrayList<String> stringArgs = new ArrayList<String>();
	private WineSearchObject sP;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wine_search);


		ExpandableListView searchOptions = (ExpandableListView) findViewById(android.R.id.list);
		//		LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
		//        mLocalActivityManager.dispatchCreate(savedInstanceState);
		//        searchOptions.setup();
		//ExpandableListView searchOptions = getExpandableListView();
		searchOptions.setDividerHeight(2);
		searchOptions.setGroupIndicator(null);
		searchOptions.setClickable(true);

		setGroups();
		setChildren();

		searchAdapter = new AdvancedSearchController(groups, children);
		searchAdapter.setInflater(
				(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
				this);
		searchAdapter.initializeSelections();
		searchOptions.setAdapter(searchAdapter);
		searchOptions.setOnChildClickListener(this);

		Button search = (Button) findViewById(R.id.search);
						
		search.setOnClickListener(new OnClickListener(){
		 // set to true if nothing in quick search
			@Override
			public void onClick(View v) {
				// Start AsyncTask to perform network operation (API call)

				SearchView searchVal = (SearchView) findViewById(R.id.search_bar);
					stringArgs = new ArrayList<String>();
			    	String name = searchVal.getQuery().toString();
			    	// gray out the advanced search options
			    	
			    	//Toast.makeText(getApplicationContext(), "searching for "+ name, 0).show();
			    	String[] splitted = name.split("\\s+");
			    	for(String split : splitted){
			    		if ((!split.equals(" ")) && (!split.equals("")))
			    			stringArgs.add(split); // fill array list 
			    	}
			    	//Toast.makeText(getApplicationContext(), "filled array list", 0).show();
			    	sP = new WineSearchObject();
			    	sP = searchAdapter.getSearchParameters();
					sP.stringList = stringArgs;	
					NetworkTaskManager.doCombinedSearch(WineSearch.this, sP);
			}	

		});

		Button reset = (Button) findViewById(R.id.reset);
		reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searchAdapter.clear();
			}
		});	

		SearchView quickSearch = (SearchView) findViewById(R.id.search_bar);
		final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() { 

		    @Override 
		    public boolean onQueryTextChange(String newText) { 
		        return true; 
		    } 

		    @Override 
		    public boolean onQueryTextSubmit(String query) { 
		    	SearchView searchVal = (SearchView) findViewById(R.id.search_bar);
				stringArgs = new ArrayList<String>();
		    	String name = searchVal.getQuery().toString();
		    	// gray out the advanced search options
		    	
		    	//Toast.makeText(getApplicationContext(), "searching for "+ name, 0).show();
		    	String[] splitted = name.split("\\s+");
		    	for(String split : splitted){
		    		if ((!split.equals(" ")) && (!split.equals("")))
		    			stringArgs.add(split); // fill array list 
		    	}
		    	//Toast.makeText(getApplicationContext(), "filled array list", 0).show();
		    	sP = new WineSearchObject();
		    	sP = searchAdapter.getSearchParameters();
				sP.stringList = stringArgs;
				
				NetworkTaskManager.doCombinedSearch(WineSearch.this, sP);
				return true; 
			} 
		};

		quickSearch.setOnQueryTextListener(queryTextListener); 

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search_bar);
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		InputMethodManager imm = (InputMethodManager)getSystemService(
				Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(searchView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
		return true;
	}


	protected int getTitleText() {
		// TODO Auto-generated method stub
		return R.string.wine_search_title;
	}


	public void setGroups () {
		groups.add("Type");
		groups.add("Price");
		groups.add("Varietal");
		groups.add("Accent");
	}

	public void setChildren () {
		ArrayList<String> tempChild = new ArrayList<String>();

		// Type group
		tempChild.add("Red");
		tempChild.add("Rose");
		tempChild.add("White");
		children.add(tempChild);

		// Price group
		tempChild = new ArrayList<String>();
		tempChild.add("Less than $15/bottle");
		tempChild.add("$15 - $50/bottle");
		tempChild.add("$50 - $150/bottle");
		tempChild.add("Over $150/bottle");
		children.add(tempChild);

		// Varietal group
		tempChild = new ArrayList<String>();
		tempChild.add("Cabernet Sauvignon");
		tempChild.add("Champagne");
		tempChild.add("Chardonnay");
		tempChild.add("Merlot");
		tempChild.add("Muscatto");
		tempChild.add("Pinot Gris");
		tempChild.add("Pinot Noir");
		tempChild.add("Port");
		tempChild.add("Reisling");
		tempChild.add("Sangiovese");	
		tempChild.add("Sauvignon Blanc");
		tempChild.add("Shiraz");
		tempChild.add("Sweet Sherry");
		tempChild.add("White Zinfandel");
		tempChild.add("Zinfandel");
		children.add(tempChild);

		// Accent group
		tempChild = new ArrayList<String>();
		tempChild.add("Clear");
		tempChild.add("Dry");
		tempChild.add("Elegant");
		tempChild.add("Flat");
		tempChild.add("Fruit");
		tempChild.add("Full");
		tempChild.add("Hard");
		tempChild.add("Mature");
		tempChild.add("Peachy");
		tempChild.add("Rich");
		tempChild.add("Soft");
		tempChild.add("Sweet");
		tempChild.add("Tart");
		children.add(tempChild);

	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPos,
			int childPos, long id) {
		//Toast.makeText(WineSearch.this, "Clicked On Child", Toast.LENGTH_SHORT).show();
		return true;
	}

}
