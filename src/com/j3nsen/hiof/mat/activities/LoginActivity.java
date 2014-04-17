package com.j3nsen.hiof.mat.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.j3nsen.hiof.mat.R;
import com.j3nsen.hiof.mat.UserManager;
import com.j3nsen.hiof.mat.Utilities;

public class LoginActivity extends ActionBarActivity {
	private EditText et_username;
	private EditText et_password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		if(UserManager.getSavedUser(this))
			loginSuccess(true);
		
		et_username = (EditText) findViewById(R.id.editText_username);
		et_password = (EditText) findViewById(R.id.editText_password);
	}

	private boolean checkInput() {
		String username = et_username.getText().toString();
		String password = et_password.getText().toString();

		if (Utilities.stringNullOrEmpty(username)
				|| Utilities.stringNullOrEmpty(password))
			return false;

		return true;
	}

	public void button_login_onClick(View view) {
		if(checkInput())
			new LoginUserTask().execute((Void) null);
		else
			makeToast("Fyll inn alle felt");
	}
	
	private void makeToast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}
	
	private void loginSuccess(boolean savedUser) {
		if(!savedUser)
			UserManager.saveUser(this);
		
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
	
	private class LoginUserTask extends AsyncTask<Void, Void, Boolean> {

		private String username;
		private String password;
		
		@Override
		protected void onPreExecute() {
			username = et_username.getText().toString();
			password = et_password.getText().toString();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			if(UserManager.loginUser(username, password)) {
				return true;
			}
			return false;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if(result)
				loginSuccess(false);
			else
				makeToast("Ugyldig brukernavn eller passord");
		}
	}
}
