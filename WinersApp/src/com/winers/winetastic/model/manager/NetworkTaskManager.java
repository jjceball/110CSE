package com.winers.winetastic.model.manager;

import android.content.Context;

import com.winers.winetastic.controller.AddToCellarTask;
import com.winers.winetastic.controller.AddToWishlistTask;
import com.winers.winetastic.controller.CombinedSearchTask;
import com.winers.winetastic.controller.MyWinesIntentTask;
import com.winers.winetastic.controller.RemoveFromCellarTask;
import com.winers.winetastic.controller.WineryIntentTask;
import com.winers.winetastic.controller.DailyVineIntentTask;
import com.winers.winetastic.model.data.WineSearchObject;

/**
 * Manager class for network tasks that need to be run on another thread.
 * @author victoria
 *
 */

public class NetworkTaskManager {

	public static void doCombinedSearch(Context c, WineSearchObject sP) {
		new CombinedSearchTask(c).execute(sP);
	}
	
	public static void searchMore(Context c, WineSearchObject sP) {
		sP.firstResult += 20;
		new CombinedSearchTask(c).execute(sP);
	}
	
	public static void startWineryIntent(Context c, String wineryID) {
		new WineryIntentTask(c).execute(wineryID);
	}
	
	public static void addToCellar(Context c, String name, String code) {
		new AddToCellarTask(c, name, code).execute();
	}
	
	public static void addToWishlist(Context c, String name, String code) {
		new AddToWishlistTask(c, name, code).execute();
	}
	
	public static void startMyWinesIntent(Context c) {
		new MyWinesIntentTask(c).execute();
	}
	
	public static void startDailyVineIntent(Context c) {
		new DailyVineIntentTask(c).execute();
	}
	
	public static void removeFromCellar(Context c, String code) {
		new RemoveFromCellarTask(c).execute(code);
	}
}
