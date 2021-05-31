package com.mobdeve.nievas.jobscope;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/EmployeeRegister")
    Call<Void> executeEmployeeRegister(@Body HashMap<String, String> map);

    @POST("/NewJobListing")
    Call<Void> executeNewJobListingActivity(@Body HashMap<String, String> map);

    @POST("/GetMyJobListing")
    Call<JobListingResult> GetMyJobListing(@Body HashMap<String, String> map);

    @POST("/GetJobApplicants")
    Call<ApplicantsResult> GetJobApplicants(@Body HashMap<String, Integer> map);


}
