package com.example.dlslqueueingapp;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Home extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog progressDialog;
    private Button qnButton, qlButton, logoutButton;
    private Handler mhandler = new Handler();
    private NotificationHelper mNotificationHelper;
    private String queueAlertHolder;
    private String studNumHolder;
    private String passHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mNotificationHelper = new NotificationHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        String studentNumber = sharedPreferences.getString("sn", "");
        String queueAlert = sharedPreferences.getString("qn", "");
        String pass = sharedPreferences.getString("p", "");
        queueAlertHolder = queueAlert;
        studNumHolder = studentNumber;
        passHolder = pass;
        callFunc();
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



    }

    public void callFunc(){
        mhandler.postDelayed(mToastRunnable, 1000);
        mToastRunnable.run();
    }

    public void sendOnNotif(String title, String msg){
        NotificationCompat.Builder nb = mNotificationHelper.getChannerlNotification(title, msg);
        mNotificationHelper.getManeger().notify(1, nb.build());
    }

    private void alertQueryFunc() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("queueAlert", obj.getString("number_of_queues_before_you"));
                            editor.commit();
                            queueAlertHolder = obj.getString("number_of_queues_before_you");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                getApplicationContext(),
                                ("No internet connection."),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("studentNumber", studNumHolder);
                params.put("pass", passHolder);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }



    public Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            alertQueryFunc();
            if(queueAlertHolder.equals("3")){
                sendOnNotif("Queue Status", "You are currently 3 queues away from being served.");
            }
            mhandler.postDelayed(this, 1000);
        }
    };

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
