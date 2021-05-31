package com.mobdeve.nievas.jobscope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationsTrackerActivity extends AppCompatActivity implements View.OnClickListener, JobListingAdapter.OnOrderListener {


    private RecyclerView rvJobListings;
    private Button btnNew;
    private Button btnProfile;
    private Button btnLogout;
    private Button btnMyJobListing;

    private SharedPreferences sharedpreferences; //for storing current logged user
    private ArrayList<JobListing> arrJobListing = new ArrayList<>();

    private SharedPreferences sharedPreferences;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000"; //localhost of computer or emulator idk

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

        // for connecting to mongodb/js server
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        sharedPreferences = getSharedPreferences("SavedPreferences", Context.MODE_PRIVATE);

        // serves as a bridge for data from UI to server
        arrJobListing.clear();

        HashMap<String, String> map1 = new HashMap<>();
        map1.put("employer", sharedPreferences.getString("LOGGED_USER_KEY", ""));

        String x = map1.get("employer");

        Toast.makeText(this, x, Toast.LENGTH_LONG).show();

        Call<JobListingResult> call = retrofitInterface.GetMyJobListing(map1);

        // calls an http request
        call.enqueue(new Callback<JobListingResult>() {
            @Override
            public void onResponse(Call<JobListingResult> call, Response<JobListingResult> response) {
                if(response.code() == 200){
                    Gson gson = new Gson();
                    JobListingResult result = response.body();

//                    Log.d("LIST",  "result.zise() " + result.getArrJobListing().get(0).getJobListingID());



                    int i;
                    JobListing JO, job;
                    for(  i = 0; i < result.getArrJobListing().size() ; i++ ){
                        JO =  result.getArrJobListing().get(i);
                        job = new JobListing(JO.getJobListingID(), JO.getEmployer(),JO.getTitle(),JO.getDescription(),JO.getLocation(),JO.getResponsibilities(),JO.getSpecialization(),JO.getEducation());
                        arrJobListing.add(job);
                    }



                    // lists the jobs in recyclerview
                    if (arrJobListing.size() != 0) {
                        JobListingAdapter jobListingAdapter = new JobListingAdapter(ApplicationsTrackerActivity.this, arrJobListing, ApplicationsTrackerActivity.this);
                        rvJobListings.setAdapter(jobListingAdapter);
                        rvJobListings.setLayoutManager(new LinearLayoutManager(ApplicationsTrackerActivity.this));
                    }
                }
            }
            @Override
            public void onFailure(Call<JobListingResult> call, Throwable t) {
                //shows the error
                Toast.makeText(ApplicationsTrackerActivity.this, t.getMessage() ,
                        Toast.LENGTH_LONG).show();
                Log.d("LIST",  t.getMessage());

            }
        });



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

    @Override
    public void onOrderClick(int position) {
        Intent intent = new Intent(this,  JobDetailsActivity.class);
        //putting the Serialized object in Intent
        intent.putExtra("job", arrJobListing.get(position));
        startActivity(intent);

//        Toast.makeText(ApplicationsTrackerActivity.this, "CLICKED", Toast.LENGTH_LONG).show();

    }
}
