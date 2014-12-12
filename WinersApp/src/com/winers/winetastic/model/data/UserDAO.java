package com.winers.winetastic.model.data;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class UserDAO {
	private static final String API_KEY = "7sft6abh56pjc52byts04mq9vpok18ufzksn5r4g92amybdy";
	private static final String SNOOTH_URL = "http://api.snooth.com";
	private static final String CREATE_ACCOUNT_RESOURCE_ID = "/create-account/";
	private static final String RANDOM_STRING = "k8d9j5h8g4u7tr4";
	
	public static void createSnoothAccount(String email) {
		String url = SNOOTH_URL + CREATE_ACCOUNT_RESOURCE_ID + "?akey=" + API_KEY;
		String newEmail = RANDOM_STRING + email;
		url += "&e=" + newEmail;
		url += "&p=" + RANDOM_STRING;
		
		MessageDigest m;
		String hashtext = new String();
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(newEmail.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			hashtext = bigInt.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		hashtext = hashtext.substring(0,19);
		System.err.println("sn = " + hashtext);
		url += "&s=" + hashtext;
		System.err.println("Created snooth account using the following URL:");
		System.err.println(url);
		InputStream source = retrieveStream(url);
		if (source != null) {
			//Reader reader = new InputStreamReader(source);
			// Snooth is up. Yay.
		} else {
			// Snooth is down or there was some other error. Respond accordingly.
		}
	}
	
private static InputStream retrieveStream(String url) {
    	
    	DefaultHttpClient client = new DefaultHttpClient(); 
        
        HttpGet getRequest = new HttpGet(url);
          
        try {
           
           HttpResponse getResponse = client.execute(getRequest);
           final int statusCode = getResponse.getStatusLine().getStatusCode();
           
           if (statusCode != HttpStatus.SC_OK) { 
              //Log.w(getClass().getSimpleName(), "Error " + statusCode + " for URL " + url); 
              return null;
           }

           HttpEntity getResponseEntity = getResponse.getEntity();
           return getResponseEntity.getContent();
           
        } 
        catch (IOException e) {
           getRequest.abort();
           //Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
           System.err.println(e.toString());
        }
        
        return null;  
     }
}
