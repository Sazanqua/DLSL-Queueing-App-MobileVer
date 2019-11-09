package com.example.dlslqueueingapp;

import androidx.appcompat.app.AppCompatActivity;

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

    private TextView queueNumberTxtVw, cashierNumberTxtVw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queueing_number);

        queueNumberTxtVw = findViewById(R.id.queueNumberTxtVw);
        cashierNumberTxtVw = findViewById(R.id.cashierNumberTxtVw);

        queueNumberTxtVw.setText(SharedPrefManager.getInstance(this).getQueueNumber());
        cashierNumberTxtVw.setText(SharedPrefManager.getInstance(this).getCashierNumber());
    }

}
