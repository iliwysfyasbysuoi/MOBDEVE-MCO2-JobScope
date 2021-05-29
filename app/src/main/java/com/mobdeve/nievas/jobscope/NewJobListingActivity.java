package com.mobdeve.nievas.jobscope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewJobListingActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTitle;
    private EditText etDescription;
    private EditText etLocation;
    private EditText etResponsibilities;
    private EditText etSpecialization;
    private EditText etEducation;
    private Button btnPost;

    private SharedPreferences sharedPreferences;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000"; //localhost of computer or emulator idk


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_job_listing);

        // for connecting to mongodb/js server
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);


        this.etTitle = findViewById(R.id.etTitle);
        this.etDescription = findViewById(R.id.etDescription);
        this.etLocation = findViewById(R.id.etLocation);
        this.etResponsibilities = findViewById(R.id.etResponsibilities);
        this.etSpecialization = findViewById(R.id.etSpecialization);
        this.etEducation = findViewById(R.id.etEducation);
        this.btnPost = findViewById(R.id.btnPost);


        this.btnPost.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        String location = etLocation.getText().toString();
        String responsibilities = etResponsibilities.getText().toString();
        String specialization = etSpecialization.getText().toString();
        String education = etEducation.getText().toString();
        sharedPreferences = getSharedPreferences("SavedPreferences", Context.MODE_PRIVATE);
        String employer = sharedPreferences.getString("LOGGED_USER_KEY", "");

        if(isComplete(title, description, location, responsibilities, specialization, education) == true){
            // serves as a bridge for data from UI to server
            HashMap<String, String> map = new HashMap<>();
            map.put("employer", employer);
            map.put("title", title);
            map.put("description", description);
            map.put("location", location);
            map.put("responsibilities", responsibilities);
            map.put("specialization", specialization);
            map.put("education", education);

            Call<Void> call = retrofitInterface.executeNewJobListingActivity(map);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    // if successful
                    if (response.code() == 200) {
                        Toast.makeText(NewJobListingActivity.this, "New job listing posted!", Toast.LENGTH_LONG).show();
                        finish(); //closes the NewJobListingActivity | no need to intent because magbaback lang naman after posting a new job listing
                    }
                    //
                    else if (response.code() == 400) {
                        //Toast.makeText(NewJobListingActivity.this, "Already registered", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    //shows the error
                    Toast.makeText(NewJobListingActivity.this, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }else{
            //if fields nto complete, Toast an error
            Toast.makeText(NewJobListingActivity.this, "All fields required!",
                    Toast.LENGTH_LONG).show();
        }
    }

    private Boolean isComplete(String title,String description ,  String location, String responsibilities, String specialization, String education){
        if(title.length()>0 && description.length()>0 && location.length()>0 && responsibilities.length()>0 && specialization.length()>0 && education.length()>0 )
            return true;
        else
            return false;
    }
}
