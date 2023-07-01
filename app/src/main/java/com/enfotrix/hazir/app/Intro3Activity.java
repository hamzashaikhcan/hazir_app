package com.enfotrix.hazir.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.enfotrix.hazir.MainActivity;
import com.enfotrix.hazir.R;

public class Intro3Activity extends AppCompatActivity {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro3);

        next = findViewById(R.id.introthree_next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intro3Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}