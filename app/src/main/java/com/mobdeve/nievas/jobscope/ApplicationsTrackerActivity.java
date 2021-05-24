package com.mobdeve.nievas.jobscope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ApplicationsTrackerActivity extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView rvJobListings;
    private Button btnNew;
    private Button btnProfile;
    private Button btnLogout;
    private Button btnMyJobListing;

    private SharedPreferences sharedpreferences; //for storing current logged user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications_tracker);

        rvJobListings = findViewById(R.id.rvJobListings);
        btnNew = findViewById(R.id.btnNew);
        btnProfile = findViewById(R.id.btnProfile);
        btnLogout = findViewById(R.id.btnLogout);
        btnMyJobListing = findViewById(R.id.btnMyJobListing);

        btnNew.setOnClickListener(this);
        btnProfile.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnMyJobListing.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.btnNew: {

                // goes to New Job Listing
                Intent intentNewJob = new Intent(this, NewJobListingActivity.class);
                startActivity(intentNewJob);

            }
            break;
            case R.id.btnProfile: {

            }
            break;

            case R.id.btnMyJobListing: {


            }
            break;

            case R.id.btnLogout: {

                Toast.makeText(ApplicationsTrackerActivity.this, "pressed logout", Toast.LENGTH_LONG).show();
                sharedpreferences = getSharedPreferences("SavedPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                //sets LOGGED_USER_KEY value to "" indicating that no user is logged in.
                editor.putString("LOGGED_USER_KEY", "");
                editor.commit();

                Intent intentLogout = new Intent(this, LoginActivity.class);
                startActivity(intentLogout);
                finish();
            }
            break;

        }


    }
}
