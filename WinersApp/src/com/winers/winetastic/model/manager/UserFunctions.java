package com.winers.winetastic.model.manager;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

 
import android.content.Context;
 
public class UserFunctions {
     
    private JSONParser jsonParser;
     
    private static String loginURL = "http://winers.thevse.com";
    private static String registerURL = "http://winers.thevse.com";
     
    private static String login_tag = "login";
    private static String register_tag = "register";
    
    // Key to verify API call to
    // make sure nobody is hacking us!
    private static String VERIFICATION_KEY = "r4t5Y^U&i8O(p0";
     
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
     
    /**
     * Function to make login request to remote database
     */
    public JSONObject loginUser(String email, String password){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key",VERIFICATION_KEY));
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

        return json;
    }
     
    /**
     * Function to make registration request to remote database
     */
    public JSONObject registerUser(String email, String password){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key",VERIFICATION_KEY));
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
         
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);

        return json;
    }
     
    /**
     * Function to get status - is user logged in?
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            return true;
        }
        return false;
    }
     
    /**
     * Function to log out user
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
     
}