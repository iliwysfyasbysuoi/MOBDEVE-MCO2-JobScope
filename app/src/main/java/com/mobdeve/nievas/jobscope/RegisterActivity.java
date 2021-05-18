package com.mobdeve.nievas.jobscope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


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

                // goes to LoginActivity
                Intent intentLogin = new Intent(this,  LoginActivity.class);
                startActivity(intentLogin);
                finish(); //close RegisterActivity because there's a button for navigating back to LoginActivity na


            } break;

            case R.id.btnRegister: {

                /* TODO code register/ storing credentials to db
                   use MongoDB database
                 */

            } break;



        }
    }
}
