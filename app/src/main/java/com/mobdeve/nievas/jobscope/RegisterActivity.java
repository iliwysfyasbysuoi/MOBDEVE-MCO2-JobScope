package com.mobdeve.nievas.jobscope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etFullName;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000"; //localhost of computer or emulator idk


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // for connecting to mongodb/js server
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);


        this.btnLogin = findViewById(R.id.btnLogin);
        this.btnRegister = findViewById(R.id.btnRegister);
        // sets onclick listeners
        this.btnLogin.setOnClickListener(this);
        this.btnRegister.setOnClickListener(this);

//        handleSignupDialog();

        this.etPassword = findViewById(R.id.etPassword);
        this.etUsername= findViewById(R.id.etUsername);
        this.etFullName= findViewById(R.id.etFullName);
        this.etEmail= findViewById(R.id.etEmail);





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

                 */

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String name = etFullName.getText().toString();
                String email = etEmail.getText().toString();

                //if all fields has input
                if(username.length()>0 && password.length()>0 && name.length()>0 && email.length()>0) {
                    // serves as a bridge for data from UI to server
                    HashMap<String, String> map = new HashMap<>();
                    map.put("username", username);
                    map.put("password", password);
                    map.put("name", name);
                    map.put("email", email);


                    Call<Void> call = retrofitInterface.executeEmployeeRegister(map);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            // if successful
                            if (response.code() == 200) {
                                Toast.makeText(RegisterActivity.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                                //                            AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
                                //                            builder1.setTitle("Successfuly registered!");
                                //                            builder1.setMessage(" ");
                                //                            builder1.show();

                                Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intentLogin);
                                finish();
                            }
                            // if username/email already registered
                            else if (response.code() == 400) {
                                Toast.makeText(RegisterActivity.this, "Already registered", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            //shows the error
                            Toast.makeText(RegisterActivity.this, t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "All fields required!",
                            Toast.LENGTH_LONG).show();
                }
            }

            break;



        }
    }

    private void handleSignupDialog() {

//        View view = getLayoutInflater().inflate(R.layout.activity_login, null);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(view).show();


        this.etPassword = findViewById(R.id.etPassword);
        this.etUsername= findViewById(R.id.etUsername);
        this.etFullName= findViewById(R.id.etFullName);
        this.etEmail= findViewById(R.id.etEmail);

        this.btnRegister= findViewById(R.id.btnRegister);

        String username = this.etUsername.toString();
        String password = this.etPassword.toString();
        String name = this.etFullName.toString();
        String email = this.etEmail.toString();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // serves as a bridge for data from UI to server
                HashMap<String, String> map = new HashMap<>();
                map.put("username", username);
                map.put("password", password);
                map.put("name", name);
                map.put("email", email);


                Call<Void> call = retrofitInterface.executeEmployeeRegister(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        // if successful
                        if(response.code() == 200){
                            Toast.makeText(RegisterActivity.this, "Signed up successfully", Toast.LENGTH_LONG).show();
                        }
                        // if username/email already registered
                        else if(response.code() == 400){
                            Toast.makeText(RegisterActivity.this, "Already registered", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        //shows the error
                        Toast.makeText(RegisterActivity.this, t.getMessage() ,
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }
}
