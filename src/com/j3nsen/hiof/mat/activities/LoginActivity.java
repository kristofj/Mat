package com.j3nsen.hiof.mat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.j3nsen.hiof.mat.R;
import com.j3nsen.hiof.mat.UserManager;
import com.j3nsen.hiof.mat.Utilities;

public class LoginActivity extends ActionBarActivity {

	EditText et_username;
	EditText et_password;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
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
		if (checkInput()) {
			Toast.makeText(this, "Fyll inn alle felt", Toast.LENGTH_SHORT)
					.show();
		} else {
			if (UserManager.loginUser(et_username.getText().toString(),
					et_password.getText().toString())) {
				Intent i = new Intent(this, MainActivity.class);
				startActivity(i);
			} else {
				Toast.makeText(this, "Ugyldig brukernavn eller passord.",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

}
