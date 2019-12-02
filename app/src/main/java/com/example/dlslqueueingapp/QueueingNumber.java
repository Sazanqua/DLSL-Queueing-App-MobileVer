package com.example.dlslqueueingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QueueingNumber extends AppCompatActivity {

    private TextView queueNumberTxtVw, cashierNumberTxtVw, studentNumberTxtVw, serviceTypeTxtVw, serviceLaneTxtVw, dateTxtVw, timeTxtVw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queueing_number);

        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        String qn = sharedPreferences.getString("qn", "");
        String cn = sharedPreferences.getString("cn", "");
        String sn = sharedPreferences.getString("sn", "");
        String st = sharedPreferences.getString("st", "");
        String sl = sharedPreferences.getString("sl", "");
        String d = sharedPreferences.getString("d", "");
        String t = sharedPreferences.getString("t", "");

        queueNumberTxtVw = findViewById(R.id.queueNumberTxtVw);
        cashierNumberTxtVw = findViewById(R.id.cashierNumberTxtVw);
        studentNumberTxtVw = findViewById(R.id.studentNumberTxtVw);
        serviceTypeTxtVw = findViewById(R.id.serviceTypeTxtVw);
        serviceLaneTxtVw = findViewById(R.id.serviceLaneTxtVw);
        serviceLaneTxtVw = findViewById(R.id.serviceLaneTxtVw);
        dateTxtVw = findViewById(R.id.dateTxtVw);
        timeTxtVw = findViewById(R.id.timeTxtVw);

        queueNumberTxtVw.setText(qn);
        cashierNumberTxtVw.setText("Cashier "+cn);
        studentNumberTxtVw.setText(sn);
        serviceTypeTxtVw.setText(st);
        serviceLaneTxtVw.setText(sl);
        dateTxtVw.setText(d);
        timeTxtVw.setText(t);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

}
