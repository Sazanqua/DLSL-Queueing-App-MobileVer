package com.example.dlslqueueingapp;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class Home extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog progressDialog;
    private Button qnButton, qlButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        qnButton = findViewById(R.id.qnButton);
        qnButton.setOnClickListener(this);
        qlButton = findViewById(R.id.qlButton);
        qlButton.setOnClickListener(this);
        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);

//        Button button = findViewById(R.id.qnButton);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Home.this, QueueingNumber.class);
//
//                Pair[] pairs = new Pair[4];
//                pairs[0] = new Pair<View, String>(image1, "example_transition");
//                pairs[1] = new Pair<View, String>(image2, "capilla_transition");
//                pairs[2] = new Pair<View, String>(image3, "bulk1_transition");
//                pairs[3] = new Pair<View, String>(image4, "bulk2_transition");
//
//
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Home.this, pairs);
//                startActivity(intent, options.toBundle());
//            }
//        });
//
//        Button button2 = findViewById(R.id.qlButton);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Home.this, QueueLine.class);
//
//                Pair[] pairs = new Pair[2];
//                pairs[0] = new Pair<View, String>(image3, "bulk1_transition");
//                pairs[1] = new Pair<View, String>(image4, "bulk2_transition");
//
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Home.this, pairs);
//                startActivity(intent, options.toBundle());
//            }
//        });
//
//        Button button3 = findViewById(R.id.logoutButton);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Home.this, MainActivity.class);
//
//                Pair[] pairs = new Pair[2];
//                pairs[0] = new Pair<View, String>(image3, "bulk1_transition");
//                pairs[1] = new Pair<View, String>(image4, "bulk2_transition");
//
//                SharedPrefManager.getInstance(getApplication()).logout();
//                finish();
//
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Home.this, pairs);
//                startActivity(intent, options.toBundle());
//            }
//        });

    }

    private void qnButtonFunc(){
        final ImageView image1 = findViewById(R.id.dlsllogo_home);
        final ImageView image2 = findViewById(R.id.capilla_home);
        final ImageView image3 = findViewById(R.id.bulk_1_home);
        final ImageView image4 = findViewById(R.id.bulk_2_home);
        final Intent intent = new Intent(Home.this, QueueingNumber.class);
        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(image1, "example_transition");
        pairs[1] = new Pair<View, String>(image2, "capilla_transition");
        pairs[2] = new Pair<View, String>(image3, "bulk1_transition");
        pairs[3] = new Pair<View, String>(image4, "bulk2_transition");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Home.this, pairs);
        startActivity(intent, options.toBundle());
    }

    private void qlButtonFunc(){
        final ImageView image3 = findViewById(R.id.bulk_1_home);
        final ImageView image4 = findViewById(R.id.bulk_2_home);
        final Intent intent = new Intent(Home.this, QueueLine.class);
        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View, String>(image3, "bulk1_transition");
        pairs[1] = new Pair<View, String>(image4, "bulk2_transition");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Home.this, pairs);
        startActivity(intent, options.toBundle());
    }

    private void userLogOut(){
        progressDialog.setMessage("Logging Out...");
        progressDialog.show();
        final ImageView image1 = findViewById(R.id.dlsllogo_home);
        final ImageView image2 = findViewById(R.id.capilla_home);
        final ImageView image3 = findViewById(R.id.bulk_1_home);
        final ImageView image4 = findViewById(R.id.bulk_2_home);
        final Intent intent = new Intent(Home.this, MainActivity.class);

        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(image1, "example_transition");
        pairs[1] = new Pair<View, String>(image2, "capilla_transition");
        pairs[2] = new Pair<View, String>(image3, "bulk1_transition");
        pairs[3] = new Pair<View, String>(image4, "bulk2_transition");

        SharedPrefManager.getInstance(getApplication()).logout();
        finish();

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Home.this, pairs);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onClick(View view) {
        if (view == qnButton) {
            qnButtonFunc();
        }
        if (view == qlButton) {
            qlButtonFunc();
        }
        if (view == logoutButton) {
            userLogOut();
        }

    }

}
