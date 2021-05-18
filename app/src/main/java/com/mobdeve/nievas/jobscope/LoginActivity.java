package com.mobdeve.nievas.jobscope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // finds the views
        this.etUsername = findViewById(R.id.etUsername);
        this.etPassword = findViewById(R.id.etPassword);
        this.btnLogin = findViewById(R.id.btnLogin);
        this.btnRegister = findViewById(R.id.btnRegister);

        // sets onclick listeners
        this.btnLogin.setOnClickListener(this);
        this.btnRegister.setOnClickListener(this);










    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.btnLogin:{
                /* TODO code for credential validation
                   use MongoDB database
                 */
            } break;

            case R.id.btnRegister: {

                Log.d("LOGIN", "Register button clicked");

                // goes to RegisterActivity
                Intent intentRegister = new Intent(this,  RegisterActivity.class);
                startActivity(intentRegister);
                finish(); //close LoginActivity because there's a button for navigating back to LoginActivity na

            } break;



        }

    }
}
