package com.example.dlslqueueingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
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

public class QueueLine extends AppCompatActivity {

    private TextView cashier1TxtVw1, cashier1TxtVw2, cashier1TxtVw3, cashier1TxtVw4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_line);

        cashier1TxtVw1 = findViewById(R.id.cashier1TxtVw1);
        cashier1TxtVw2 = findViewById(R.id.cashier1TxtVw2);
        cashier1TxtVw3 = findViewById(R.id.cashier1TxtVw3);
        cashier1TxtVw4 = findViewById(R.id.cashier1TxtVw4);

        viewCashier4();

        //cashier1TxtVw1.setText(SharedPrefManager.getInstance(this).getCashier4Num());
        //cashier1TxtVw2.setText("heee");



    }

    private void viewCashier4() {
        final String cashierNumber4 = "";
        final String cashier4Type = "";

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.URL_CASHIER4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(
//                                getApplicationContext(),
//                                response,
//                                Toast.LENGTH_LONG
//                        ).show();
                        try {
                            JSONObject obj = new JSONObject(response);
                            SharedPrefManager.getInstance(getApplicationContext())
                                    .cashier4(
                                            obj.getString("CashierNumber"),
                                            obj.getString("CashierType")
                                    );
                            Toast.makeText(
                                    getApplicationContext(),
                                    obj.getString("cashier4Type"),
                                    Toast.LENGTH_LONG
                            ).show();

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
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cashierNumber4", cashierNumber4);
                params.put("cashier4Type", cashier4Type);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
