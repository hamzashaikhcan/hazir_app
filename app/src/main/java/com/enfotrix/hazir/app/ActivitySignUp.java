package com.enfotrix.hazir.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enfotrix.hazir.Constant;
import com.enfotrix.hazir.Loading;
import com.enfotrix.hazir.MainActivity;
import com.enfotrix.hazir.R;
import com.enfotrix.hazir.Utils;
import com.enfotrix.hazir.databinding.ActivitySignInBinding;
import com.enfotrix.hazir.databinding.ActivitySignUpBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarException;

public class ActivitySignUp extends AppCompatActivity {

    private Context context;
    ActivitySignUpBinding binding;

    RequestQueue requestQueue;
    Utils utils;

    Constant constant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context= ActivitySignUp.this;
        constant= new Constant();
        utils= new Utils(context);
        binding.tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ActivitySignIn.class));
                finish();

            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(context, MainActivity.class));


                if(validate()){
                    reg();
                }

                //register();

               /* String serve_mode = "";
                String address = "";
                String label = "";
                String weight = "";
                try {
                    JSONObject data = response.getJSONObject("data");
                    JSONArray region = data.getJSONArray("region");
                    JSONObject pool = region.getJSONObject(0);
                    JSONArray item = pool.getJSONArray("pool");

                    //NOW ITERATE OVER THE NESTED ARRAY "pool"
                    for(int i = 0; i < item.length(); i++) {
                        JSONObject pool_item = item.getJSONObject(i);
                        serve_mode = pool_item.getString("serve_mode");
                        address = pool_item.getString("address");
                        label = pool_item.getString("label");
                        weight = pool_item.getString("weight");

                        Log.d(TAG, "serve_mode: "+serve_mode);
                        Log.d(TAG, "address: "+address);
                        Log.d(TAG, "label: "+label);
                        Log.d(TAG, "weight: "+weight);
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "JSONException: "+e.toString());
                }*/

            }


        });





    }
    private boolean validate() {

        boolean validate=true;
        if(binding.etName.getText().toString().isEmpty()) validate=false;
        else if(binding.etEmail.getText().toString().isEmpty()) validate=false;
        else if(binding.etPhone.getText().toString().isEmpty()) validate=false;
        else if(binding.etCNIC.getText().toString().isEmpty()) validate=false;
        else if(binding.etPassword.getText().toString().isEmpty()) validate=false;
        else if(binding.etCPassword.getText().toString().isEmpty()) validate=false;
        else if(!binding.etPassword.getText().toString().equals(binding.etCPassword.getText().toString())){
            validate=false;
            Toast.makeText(context, "Both Passwords not match!", Toast.LENGTH_SHORT).show();
        }
        else if(binding.etPassword.getText().toString().length()<8){
            validate=false;
            Toast.makeText(context, "Password length must be 8 characters!", Toast.LENGTH_SHORT).show();
        }
        else if(binding.etCNIC.getText().toString().length()<13){
            validate=false;
            Toast.makeText(context, "Incorrect CNIC Number!", Toast.LENGTH_SHORT).show();
        }
        else if(binding.etPhone.getText().toString().length()<11){
            validate=false;
            Toast.makeText(context, "Incorrect Phone Number!", Toast.LENGTH_SHORT).show();
        }
        return validate;
    }


    public void reg(){

        Loading loading= new Loading(context);
        loading.start();
        requestQueue = Volley.newRequestQueue(this);

        StringRequest myReq = new StringRequest(Request.Method.POST,
                "https://gaarihazir.com/api/driver/register",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);


                            if(obj.getString("message").equals("Driver registered successfully.")){


                                loading.end();
                                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(context, ActivitySignIn.class));
                                finish();
                            }
                            //Toast.makeText(context, obj.getString("message").equals("Driver registered successfully.")+"", Toast.LENGTH_SHORT).show();

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

                params.put("name", binding.etName.getText().toString());
                params.put("email", binding.etEmail.getText().toString());
                params.put("phone_no", binding.etPhone.getText().toString());
                params.put("password", binding.etPassword.getText().toString());
                params.put("cnic", binding.etCNIC.getText().toString());
                return params;
            };
        };
        requestQueue.add(myReq);

    }
}


