package com.example.dlslqueueingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QueueLine extends AppCompatActivity implements View.OnClickListener{

    private TextView cashier1TxtVw1, cashier1TxtVw2, cashier1TxtVw3, cashier1TxtVw4;

    private Handler mhandler = new Handler();

    private LinearLayout l1, l2, l3, l4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_line);

        l1 = findViewById(R.id.cashier1Holder);
        l1.setOnClickListener(this);
        l2 = findViewById(R.id.cashier2Holder);
        l2.setOnClickListener(this);
        l3 = findViewById(R.id.cashier3Holder);
        l3.setOnClickListener(this);
        l4 = findViewById(R.id.cashier4Holder);
        l4.setOnClickListener(this);

        cashier1TxtVw1 = findViewById(R.id.cashier1CurrentQueueNumber);
        cashier1TxtVw2 = findViewById(R.id.cashier2CurrentQueueNumber);
        cashier1TxtVw3 = findViewById(R.id.cashier3CurrentQueueNumber);
        cashier1TxtVw4 = findViewById(R.id.cashier4CurrentQueueNumber);

        callFunc();

    }

    @Override
    public void onClick(View view) {
        if (view == l1) {
            openDialog_cashier1();
        }
        if (view == l2) {
            openDialog_cashier2();
        }
        if (view == l3) {
            openDialog_cashier3();
        }
        if (view == l4) {
            openDialog_cashier4();
        }

    }

    public void openDialog_cashier1(){
        Cashier1QueueDialog c1_dialog = new Cashier1QueueDialog();
        c1_dialog.show(getSupportFragmentManager(), "Cashier 1");
    }
    public void openDialog_cashier2(){
        Cashier2QueueDialog c2_dialog = new Cashier2QueueDialog();
        c2_dialog.show(getSupportFragmentManager(), "Cashier 2");
    }
    public void openDialog_cashier3(){
        Cashier3QueueDialog c3_dialog = new Cashier3QueueDialog();
        c3_dialog.show(getSupportFragmentManager(), "Cashier 3");
    }
    public void openDialog_cashier4(){
        Cashier4QueueDialog c4_dialog = new Cashier4QueueDialog();
        c4_dialog.show(getSupportFragmentManager(), "Cashier 4");
    }

    public void callFunc(){
        mhandler.postDelayed(runnableFunc, 5000);
        runnableFunc.run();
    }

    public Runnable runnableFunc = new Runnable() {
        @Override
        public void run() {
            viewCashier1();
            viewCashier2();
            viewCashier3();
            viewCashier4();
            mhandler.postDelayed(this, 5000);
        }
    };

    private void viewCashier1() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.URL_CASHIER1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if(obj.getString("queue_no")=="null"){
                                cashier1TxtVw1.setText("No Queue");
                            }
                            else{
                                cashier1TxtVw1.setText(obj.getString("queue_no"));
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
        )
        {};
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void viewCashier2() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.URL_CASHIER2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if(obj.getString("queue_no")=="null"){
                                cashier1TxtVw2.setText("No Queue");
                            }
                            else{
                                cashier1TxtVw2.setText(obj.getString("queue_no"));
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
        )
        {};
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void viewCashier3() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.URL_CASHIER3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if(obj.getString("queue_no")=="null"){
                                cashier1TxtVw3.setText("No Queue");
                            }
                            else{
                                cashier1TxtVw3.setText(obj.getString("queue_no"));
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
        )
        {};
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void viewCashier4() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.URL_CASHIER4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if(obj.getString("queue_no")=="null"){
                                cashier1TxtVw4.setText("No Queue");
                            }
                            else{
                                cashier1TxtVw4.setText(obj.getString("queue_no"));
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
        )
        {};
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
