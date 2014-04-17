package com.j3nsen.hiof.mat;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UserManager {
	private static String loggedInUser;
	private static String passwordHash;
	private static boolean loggedIn;
	
	private static final String SETTINGS_FILE = "MatUserSettings";
	private static final String USERNAME_KEY = "saved_username";
	private static final String PASSWORD_HASH_KEY = "saved_password_hash";
	
	public static boolean loginUser(String username, String password) {
		if(Utilities.stringNullOrEmpty(username) || Utilities.stringNullOrEmpty(password))
			return false;
		
		ArrayList<NameValuePair> arr = new ArrayList<NameValuePair>();
		String hash = Utilities.sha256Hash(password);
		String loginUrl = "http://j3nsen.com/mat_login.php";
		
		Log.d("HASH:", hash);
		
		arr.add(new BasicNameValuePair("username", username));
		arr.add(new BasicNameValuePair("password", hash));

		try {
			String response = HttpHelper.ReadUrlPost(loginUrl, arr);
			if (response == "1") {
				loggedInUser = username;
				passwordHash = hash;
				loggedIn = true;

				return true;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return false;
	}
	
	public static void saveUser(Context context) {
		SharedPreferences settings = context.getSharedPreferences(SETTINGS_FILE, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putString(USERNAME_KEY, loggedInUser);
		editor.putString(PASSWORD_HASH_KEY, passwordHash);
		editor.commit();
	}
	
	public static boolean getSavedUser(Context context) {
		SharedPreferences settings = context.getSharedPreferences(SETTINGS_FILE, 0);
		
		String username = settings.getString(USERNAME_KEY, null);
		String password = settings.getString(PASSWORD_HASH_KEY, null);
		if(username == null || password == null)
			return false;
		
		loggedInUser = username;
		passwordHash = password;
		loggedIn = true;
		
		return true;
	}

	public static void logoutUser() {
		loggedIn = false;
		loggedInUser = null;
		passwordHash = null;
	}

	public static boolean isUserLoggedIn() {
		return loggedIn;
	}
	
	public static String getUser() {
		return loggedInUser;
	}
	public static String getPasswordHash(){
		return passwordHash;
	}
}
