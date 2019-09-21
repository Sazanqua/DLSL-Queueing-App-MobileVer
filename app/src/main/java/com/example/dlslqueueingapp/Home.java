package com.example.dlslqueueingapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final ImageView image1 = findViewById(R.id.dlsllogo_home);
        final ImageView image2 = findViewById(R.id.capilla_home);
        final ImageView image3 = findViewById(R.id.bulk_1_home);
        final ImageView image4 = findViewById(R.id.bulk_2_home);

        Button button = findViewById(R.id.qnButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, QueueingNumber.class);

                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View, String>(image1, "example_transition");
                pairs[1] = new Pair<View, String>(image2, "capilla_transition");
                pairs[2] = new Pair<View, String>(image3, "bulk1_transition");
                pairs[3] = new Pair<View, String>(image4, "bulk2_transition");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Home.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        Button button2 = findViewById(R.id.qlButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, QueueLine.class);

                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View, String>(image1, "example_transition");
                pairs[1] = new Pair<View, String>(image2, "capilla_transition");
                pairs[2] = new Pair<View, String>(image3, "bulk1_transition");
                pairs[3] = new Pair<View, String>(image4, "bulk2_transition");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Home.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }

}
