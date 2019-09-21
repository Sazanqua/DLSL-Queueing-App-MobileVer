package com.example.dlslqueueingapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.util.Pair;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView image1 = findViewById(R.id.dlsllogo);
        final ImageView image2 = findViewById(R.id.capilla);
        final ImageView image3 = findViewById(R.id.bulk_1);
        final ImageView image4 = findViewById(R.id.bulk_2);

        Button button = findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Home.class);

                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View, String>(image1, "example_transition");
                pairs[1] = new Pair<View, String>(image2, "capilla_transition");
                pairs[2] = new Pair<View, String>(image3, "bulk1_transition");
                pairs[3] = new Pair<View, String>(image4, "bulk2_transition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
