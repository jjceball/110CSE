package com.winers.winetastic.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.winers.winetastic.AbstractActivity;
import com.winers.winetastic.R;

/**
 * Activity which displays a login screen to the user.
 */
public class LoginView extends AbstractActivity implements OnClickListener {
	
    /*
     * Stuff for login with email/password
     */
	Button btnLogin;
	EditText inputEmail;
	EditText inputPassword;
	TextView loginErrorMsg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 	
		setContentView(R.layout.activity_login);
		
		// Import assets for email login
		inputEmail = (EditText) findViewById(R.id.login_email);
		inputPassword = (EditText) findViewById(R.id.login_password);
		btnLogin = (Button) findViewById(R.id.login_submit);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);
				
		// Login button click event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();				
				UserForms.attemptLogin(LoginView.this, email, password, loginErrorMsg);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public void onClick(View v) {
		
	}

	@Override
	protected int getTitleText() {
		return R.string.title_activity_login;
	}
}
