package com.enfotrix.hazir.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.enfotrix.hazir.MainActivity;
import com.enfotrix.hazir.R;
import com.enfotrix.hazir.Utils;
import com.enfotrix.hazir.databinding.ActivitySpalshBinding;


public class ActivitySpalsh extends AppCompatActivity {


    final Handler handler = new Handler();

    private Context context;
    ActivitySpalshBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpalshBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
        context= ActivitySpalsh.this;
        binding.layNav.setVisibility(View.INVISIBLE);





        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Utils utils= new Utils(context);
                if(!utils.IsLogedIn()) binding.layNav.setVisibility(View.VISIBLE);
                else startActivity(new Intent(context, MainActivity.class));
            }
        }, 2000);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ActivitySignIn.class));
                finish();
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ActivitySignUp.class));
                finish();
            }
        });


    }
}