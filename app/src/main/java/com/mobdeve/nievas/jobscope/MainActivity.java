package com.mobdeve.nievas.jobscope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        /*
            TODO conditional for login/homepage
            there will be some sort of conditional here.
            If user is not logged in, go to LoginActivity
            else, go to homepage of employer

            plan: implement in shared preferences if the user is logged in.
            when login is successful, save in sharedpreferences.
            when user logs out, delete login data in shared preferences.
         */

        // goes to LoginActivity
        Intent intentLogin = new Intent(this,  LoginActivity.class);
        startActivity(intentLogin);
        finish();


        // TODO codes for going to the employer's homepage






    }



}
