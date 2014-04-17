package com.j3nsen.hiof.mat;

import android.annotation.SuppressLint;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utilities {

	//Sjekker om en string er null er tom.
	public static boolean stringNullOrEmpty(String s) {
		if (s == null)
			return true;
		if (s.isEmpty())
			return true;
		
		return false;
	}

	//Hasher en string med sha256.
	//Inspirert av http://www.mkyong.com/java/java-sha-hashing-example/
	@SuppressLint("DefaultLocale")
	public static String sha256Hash(String s) {
		StringBuffer sb = new StringBuffer();

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(s.getBytes("UTF-8"));

			byte[] bytes = md.digest();

			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}

		return sb.toString().toLowerCase();
	}

}