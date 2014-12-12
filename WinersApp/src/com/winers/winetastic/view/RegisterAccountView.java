package com.winers.winetastic.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.winers.winetastic.AbstractActivity;
import com.winers.winetastic.R;


public class RegisterAccountView extends AbstractActivity {
	Button btnRegister;
	Button btnLinkToLogin;
	EditText inputFullName;
	EditText inputEmail;
	EditText inputPassword;
	TextView registerErrorMsg;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// Import assets
		inputEmail = (EditText) findViewById(R.id.register_email);
		inputPassword = (EditText) findViewById(R.id.register_password);
		btnRegister = (Button) findViewById(R.id.register_submit);
		registerErrorMsg = (TextView) findViewById(R.id.register_error);
		ImageButton logoutButton = (ImageButton) findViewById(R.id.logout_button);
		logoutButton.setVisibility(View.GONE);
		// Register Button Click event
		btnRegister.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View view) {
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();				
				UserForms.attemptRegistration(RegisterAccountView.this, email, password, registerErrorMsg);
			}
		});
	}


	@Override
	protected int getTitleText() {
		return R.string.title_activity_register;
	}

}
