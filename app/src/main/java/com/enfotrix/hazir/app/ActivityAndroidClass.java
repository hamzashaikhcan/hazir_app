package com.enfotrix.hazir.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.enfotrix.hazir.Models.ModelCarAd;
import com.enfotrix.hazir.R;

public class ActivityAndroidClass extends AppCompatActivity {


    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_class);

        context= ActivityAndroidClass.this;

       /* Button btnLogin= findViewById(R.id.btnLogin_);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String data= btnLogin.getText().toString() ;
                //Toast.makeText(context, data, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(context,ActivityAddCar.class));



            }
        });*/





    }
























}