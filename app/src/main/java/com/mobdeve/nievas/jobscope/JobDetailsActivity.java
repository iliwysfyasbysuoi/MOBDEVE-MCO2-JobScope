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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobDetailsActivity extends AppCompatActivity implements JobApplicationsAdapter.OnOrderListener  {

    private TextView tvEmployer;
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvLocation;
    private TextView tvResponsibilities;
    private TextView tvSpecialization;
    private TextView tvEducation;
    private RecyclerView rvJobLists;
    private SharedPreferences sharedPreferences;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000"; //localhost of computer or emulator idk

    private int JOBLISTINGID;
    private ArrayList<UserObject> arrApplicants = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        // for connecting to mongodb/js server
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        tvEmployer = (TextView) findViewById(R.id.tvEmployer);
        tvTitle = (TextView) findViewById(R.id.tvJobTitle);
        tvDescription =  (TextView)findViewById(R.id.tvDescription);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvResponsibilities = (TextView) findViewById(R.id.tvResponsibilities);
        tvSpecialization = (TextView) findViewById(R.id.tvSpecialization);
        tvEducation = (TextView) findViewById(R.id.tvEducation);
        rvJobLists = (RecyclerView) findViewById(R.id.rvJobLists);


        Bundle bundle = getIntent().getExtras();
        sharedPreferences = getSharedPreferences("SavedPreferences", Context.MODE_PRIVATE);
        JobListing joblisting = (JobListing) getIntent().getSerializableExtra("job");

        tvEmployer.setText("Employer: " + joblisting.getEmployer() );
        tvTitle.setText("Job Title: " + joblisting.getTitle());
        tvDescription.setText("Job Description: " + joblisting.getDescription());
        tvLocation.setText("Location: " + joblisting.getLocation());
        tvResponsibilities.setText("Responsibilities: " + joblisting.getResponsibilities());
        tvSpecialization.setText("Specialziation: " + joblisting.getSpecialization());
        tvEducation.setText("Education: " + joblisting.getEducation());

        JOBLISTINGID = joblisting.getJobListingID();



        UserObject user = new UserObject("adsf", "asf","asf","asf","asf");
        arrApplicants.add(user);

        JobApplicationsAdapter jobListingAdapter = new JobApplicationsAdapter(JobDetailsActivity.this, arrApplicants, JobDetailsActivity.this);
        rvJobLists.setAdapter(jobListingAdapter);
        rvJobLists.setLayoutManager(new LinearLayoutManager(JobDetailsActivity.this));

        // serves as a bridge for data from UI to server
        arrApplicants.clear();

        HashMap<String, Integer> map = new HashMap<>();
        map.put("jobListingID", JOBLISTINGID);



        Call<ApplicantsResult> call = retrofitInterface.GetJobApplicants(map);

        // calls an http request
        call.enqueue(new Callback<ApplicantsResult>() {
            @Override
            public void onResponse(Call<ApplicantsResult> call, Response<ApplicantsResult> response) {
                if(response.code() == 200){
                    Gson gson = new Gson();
                    ApplicantsResult result = response.body();
//                    Log.d("LIST",  "result.zise() " + result.getArrJobListing().get(0).getJobListingID());

                    int i;
                    UserObject JO, job;
                    for(  i = 0; i < result.getArrApplicants().size() ; i++ ){
                        JO =  result.getArrApplicants().get(i);
                        job = new UserObject( JO.getName(), JO.getEmail(),JO.getUsername(),JO.getPassword(),JO.getType() );
                        arrApplicants.add(job);
                    }
                    // lists the jobs in recyclerview
                    if (arrApplicants.size() != 0) {
                        JobApplicationsAdapter jobListingAdapter = new JobApplicationsAdapter(JobDetailsActivity.this, arrApplicants, JobDetailsActivity.this);
                        rvJobLists.setAdapter(jobListingAdapter);
                        rvJobLists.setLayoutManager(new LinearLayoutManager(JobDetailsActivity.this));
                    }
                }
            }
            @Override
            public void onFailure(Call<ApplicantsResult> call, Throwable t) {
                //shows the error
                Toast.makeText(JobDetailsActivity.this, t.getMessage() ,
                        Toast.LENGTH_LONG).show();
                Log.d("LIST",  t.getMessage());

            }
        });


    }

    @Override
    public void onOrderClick(int position) {

    }
}
