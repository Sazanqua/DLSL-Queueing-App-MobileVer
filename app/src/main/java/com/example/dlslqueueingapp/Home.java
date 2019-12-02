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
    public static String queueAlertHolder, queueAlertHolder2, queueAlertHolder3;
    public static String studNumHolder;
    private String passHolder;
    private String cashierNumberHolder;
    private String URL_HOLDER;
    public boolean stopper=true, autoLogoutStopper=true;
    private int cHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mNotificationHelper = new NotificationHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        String studentNumber = sharedPreferences.getString("sn", "");
        String queueAlert = sharedPreferences.getString("qn", "");
        String queueAlert2 = sharedPreferences.getString("qn2", "");
        String queueAlert3 = sharedPreferences.getString("qn3", "");
        String cashierNumber = sharedPreferences.getString("cn", "");
        String pass = sharedPreferences.getString("p", "");
        queueAlertHolder = queueAlert;
        queueAlertHolder2 = queueAlert2;
        queueAlertHolder3 = queueAlert3;
        studNumHolder = studentNumber;
        cashierNumberHolder = cashierNumber;

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
    public Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            if(stopper==true){
//                Toast.makeText(getApplicationContext(), String.valueOf(NotificationBroadcastReceiver.holder) +" | "+ String.valueOf(cHolder),
//                        Toast.LENGTH_LONG).show();

                if(NotificationBroadcastReceiver.holder==0){
                    if(queueAlertHolder.equals(studNumHolder)){
                        sendOnNotif("Queue Status", "You are currently 2 queues away from being served.");
                        cHolder=1;
                    }
                    else if(queueAlertHolder2.equals(studNumHolder)){
                        sendOnNotif("Queue Status", "You are currently 1 queue away from being served.");
                        cHolder=2;
                    }
                    else if(queueAlertHolder3.equals(studNumHolder)){
                        sendOnNotif("Queue Status", "Your queue ticket is now currently being served!");
                        cHolder=3;
                    }
                }
                if(cHolder==1){
                    if(!queueAlertHolder.equals(studNumHolder)){
                        NotificationBroadcastReceiver.holder = 0;
                        NotificationBroadcastReceiver.counter = 0;
                    }
                }
                else if(cHolder==2){
                    if(!queueAlertHolder2.equals(studNumHolder)){
                        NotificationBroadcastReceiver.holder = 0;
                        NotificationBroadcastReceiver.counter = 0;
                    }
                }
                else if(cHolder==3){
                    if(!queueAlertHolder3.equals(studNumHolder)){
                        NotificationBroadcastReceiver.holder = 0;
                        NotificationBroadcastReceiver.counter = 0;
                    }
                }

            }
            alertQueryFunc();
            alertQueryFunc2();
            alertQueryFunc3();

            if(autoLogoutStopper==true){
                autoLogoutIfUserHasNoQueueingNumber();
            }
            mhandler.postDelayed(this, 1000);
        }
    };

    public void sendOnNotif(String title, String msg){
        NotificationCompat.Builder nb = mNotificationHelper.getChannerlNotification(title, msg);
        mNotificationHelper.getManeger().notify(1, nb.build());
    }

    private void autoLogoutIfUserHasNoQueueingNumber(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_AUTOLOGOUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (obj.getString("studentNumber").equals("null")) {
                                stopper=false;
                                autoLogoutStopper=false;
                                Toast.makeText(
                                        getApplicationContext(),
                                        ("USER TRANSACTION DONE. LOGGING OUT."),
                                        Toast.LENGTH_LONG
                                ).show();
                                userLogOut();
                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                                progressDialog.dismiss();
                            }
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
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void alertQueryFunc() {
        if(cashierNumberHolder.equals("1")){
            URL_HOLDER = Constants.URL_FETCH_IFQUEUEIS3_CASHIER1;
        }
        if(cashierNumberHolder.equals("2")){
            URL_HOLDER = Constants.URL_FETCH_IFQUEUEIS3_CASHIER2;
        }
        if(cashierNumberHolder.equals("3")){
            URL_HOLDER = Constants.URL_FETCH_IFQUEUEIS3_CASHIER3;
        }
        if(cashierNumberHolder.equals("4")){
            URL_HOLDER = Constants.URL_FETCH_IFQUEUEIS3_CASHIER4;
        }
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_HOLDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("thirdStudentNum", obj.getString("student_number"));
                            editor.commit();
                            queueAlertHolder = obj.getString("student_number");
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
        ) ;
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void alertQueryFunc2() {
        if(cashierNumberHolder.equals("1")){
            URL_HOLDER = Constants.URL_FETCH_IFQUEUEIS2_CASHIER1;
        }
        if(cashierNumberHolder.equals("2")){
            URL_HOLDER = Constants.URL_FETCH_IFQUEUEIS2_CASHIER2;
        }
        if(cashierNumberHolder.equals("3")){
            URL_HOLDER = Constants.URL_FETCH_IFQUEUEIS2_CASHIER3;
        }
        if(cashierNumberHolder.equals("4")){
            URL_HOLDER = Constants.URL_FETCH_IFQUEUEIS2_CASHIER4;
        }
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_HOLDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("secondStudentNum", obj.getString("student_number"));
                            editor.commit();
                            queueAlertHolder2 = obj.getString("student_number");
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
        ) ;
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void alertQueryFunc3() {
        if(cashierNumberHolder.equals("1")){
            URL_HOLDER = Constants.URL_FETCH_IFQUEUEIS1_CASHIER1;
        }
        if(cashierNumberHolder.equals("2")){
            URL_HOLDER = Constants.URL_FETCH_IFQUEUEIS1_CASHIER2;
        }
        if(cashierNumberHolder.equals("3")){
            URL_HOLDER = Constants.URL_FETCH_IFQUEUEIS1_CASHIER3;
        }
        if(cashierNumberHolder.equals("4")){
            URL_HOLDER = Constants.URL_FETCH_IFQUEUEIS1_CASHIER4;
        }
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_HOLDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("firstStudentNum", obj.getString("student_number"));
                            editor.commit();
                            queueAlertHolder3 = obj.getString("student_number");
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
        ) ;
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
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
            stopper=false;
            userLogOut();
        }

    }

}
