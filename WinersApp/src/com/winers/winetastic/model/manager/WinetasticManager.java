
package com.winers.winetastic.model.manager;

import java.util.ArrayList;

import com.winers.winetastic.model.data.WineSearchObject;
import com.winers.winetastic.model.data.SystemDAO;

public class WinetasticManager {
	
	static SystemDAO dao = new SystemDAO();
	
	WinetasticManager() { }
	
	// Call DAO layer's performSearch
	public static String performQuickSearch(ArrayList<String> searchArgs,
			int numResults) {
		return WinetasticManager.dao.performQuickSearch(searchArgs, numResults);
	}
	
	public static String performCombinedSearch(WineSearchObject searchParameters, int numResults){
		return WinetasticManager.dao.performCombinedSearch(searchParameters, numResults);
	}
	
	public static String returnMyWines(String email) {
		return WinetasticManager.dao.returnMyWines(email);
	}
	
	public static void addWineToWishlist(String email, String wineID){
		WinetasticManager.dao.addWineToWishlist(email, wineID);
	}
	
	public static void addWineToCellar(String email, String wineID){
		WinetasticManager.dao.addWineToCellar(email, wineID);
	}
	
	public static void removeWineFromWishlist(String email, String wineID){
		WinetasticManager.dao.removeWineFromWishlist(email, wineID);
	}
	
	public static void removeWineFromCellar(String email, String wineID){
		WinetasticManager.dao.removeWineFromCellar(email, wineID);
	}
	
	public static boolean isWineInCellar(String email, String wineID) {
		return WinetasticManager.dao.isWineInCellar(email, wineID);
	}
	
	public static boolean isWineInWishlist(String email, String wineID) {
		return WinetasticManager.dao.isWineInWishlist(email, wineID);
	}
	
	// Call DAO layer's performAdvancedSearch
	public static String performAdvancedSearch(WineSearchObject searchParameters,
			int numResults) {
		return WinetasticManager.dao.performAdvancedSearch(searchParameters, numResults);
	}
	
	// Call DAO layer's getRandomWine
	public static String getRandomWine() {
		return WinetasticManager.dao.getRandomWine();
	}
	
	// Call DAO layer's getWineryDetails
	public static String getWineryDetails(String wineryID) {
		return WinetasticManager.dao.getWineryDetails(wineryID);
	}
	
	// Call DAO layer's hasSearchResults
	public static Boolean hasSearchResults(String searchResultString) {
		return WinetasticManager.dao.hasSearchResults(searchResultString);
	}
	
	
}
