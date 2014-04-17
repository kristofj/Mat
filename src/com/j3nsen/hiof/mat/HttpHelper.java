package com.j3nsen.hiof.mat;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;


public class HttpHelper {

	private static final int TIMEOUT_MILLISEC = 100000;

	//Leser en nettside og returnerer teksten fra siden.
	public static String ReadUrl(String url) throws IOException {
		if (url == null || url.isEmpty())
			throw new IllegalArgumentException();

		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		return client.execute(post, responseHandler);
	}

	//Sender post-data og leser respons.
	public static String ReadUrlPost(String url, ArrayList<NameValuePair> data) throws IOException {
		if (url == null || url.isEmpty())
			throw new IllegalArgumentException();

		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		post.setEntity(new UrlEncodedFormEntity(data, "UTF-8"));

		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		return client.execute(post, responseHandler);
	}
	
	private class HttpReadTask extends AsyncTask<ArrayList<NameValuePair>, Void, String> {

		@Override
		protected String doInBackground(ArrayList<NameValuePair>... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
