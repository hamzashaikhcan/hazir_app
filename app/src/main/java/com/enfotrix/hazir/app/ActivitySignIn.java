package com.enfotrix.hazir.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enfotrix.hazir.Constant;
import com.enfotrix.hazir.Loading;
import com.enfotrix.hazir.MainActivity;
import com.enfotrix.hazir.R;
import com.enfotrix.hazir.Utils;
import com.enfotrix.hazir.databinding.ActivitySignInBinding;
import com.enfotrix.hazir.databinding.ActivitySpalshBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivitySignIn extends AppCompatActivity {


    private Context context;
    ActivitySignInBinding binding;
    RequestQueue requestQueue;
    Utils utils;

    String BASE_API = new Constant().getBaseURL();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context= ActivitySignIn.this;
        utils= new Utils(context);
        binding.tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ActivityForgetPassword.class));

            }
        });
        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ActivitySignUp.class));

            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    login();
                }
                else Toast.makeText(context, "Please fill complete Form!", Toast.LENGTH_SHORT).show();



            }
        });
    }


    private boolean validate() {

        boolean validate=true;
        if(binding.etEmail.getText().toString().isEmpty()) validate=false;
        else if(binding.etPassword.getText().toString().isEmpty()) validate=false;
        return validate;
    }


    public void login(){

        Loading loading= new Loading(context);
        loading.start();
        requestQueue = Volley.newRequestQueue(this);

        StringRequest myReq = new StringRequest(Request.Method.POST,
                BASE_API+"driver/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);


                            if(obj.getString("message").equals("Driver login successfully.")){

                                JSONObject driver=obj.getJSONObject("driver");

                                utils.setName(driver.getString("name")+"");
                                utils.setCNICNumber(driver.getString("cnic")+"");
                                utils.setPhoneNumber(driver.getString("phone_no")+"");
                                utils.UserLogin(driver.getInt("id")+"");
                                utils.UserLogin(driver.getInt("id")+"");

                                loading.end();
                                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(context, MainActivity.class));
                                finish();
                            }
                            //Toast.makeText(context, obj.getString("message")+"", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            loading.end();

                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.end();
                        Toast.makeText(context, error+"", Toast.LENGTH_SHORT).show();
                    }
                })
        {

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", binding.etEmail.getText().toString());
                params.put("password", binding.etPassword.getText().toString());

                return params;
            };
        };
        requestQueue.add(myReq);

    }
}