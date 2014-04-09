package com.j3nsen.hiof.mat;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.j3nsen.hiof.mat.Utilities;

public class UserManager {
	@SuppressWarnings("unused")
	private static String loggedInUser;
	private static boolean loggedIn;

	public static boolean loginUser(String username, String password) {
		ArrayList<NameValuePair> arr = new ArrayList<NameValuePair>();
		String hash = Utilities.sha256Hash(password);
		String loginUrl = "http://j3nsen.com/mat_login.php";

		arr.add(new BasicNameValuePair("username", username));
		arr.add(new BasicNameValuePair("password", hash));

		try {
			String response = HttpHelper.ReadUrlPost(loginUrl, arr);
			if (response == "1") {
				loggedInUser = username;
				loggedIn = true;

				return true;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return false;
	}

	public static void logoutUser() {
		loggedIn = false;
		loggedInUser = null;
	}

	public static boolean isUserLoggedIn() {
		return loggedIn;
	}
}
