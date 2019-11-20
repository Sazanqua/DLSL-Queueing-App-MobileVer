package com.example.dlslqueueingapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Cashier2QueueDialog extends AppCompatDialogFragment {
    private TextView cashier1_queue2, cashier1_queue3, cashier1_queue4, cashier1_queue5, cashier1_queue6
            , cashier1_lane2, cashier1_lane3, cashier1_lane4, cashier1_lane5, cashier1_lane6;
    private Handler mhandler = new Handler();

    @Override
    public void onStart() {
        super.onStart();
        cashier1_queue2 = getDialog().findViewById(R.id.c1_queue2);
        cashier1_queue3 = getDialog().findViewById(R.id.c1_queue3);
        cashier1_queue4 = getDialog().findViewById(R.id.c1_queue4);
        cashier1_queue5 = getDialog().findViewById(R.id.c1_queue5);
        cashier1_queue6 = getDialog().findViewById(R.id.c1_queue6);


        cashier1_lane2 =  getDialog().findViewById(R.id.c1_lane2);
        cashier1_lane3 =  getDialog().findViewById(R.id.c1_lane3);
        cashier1_lane4 =  getDialog().findViewById(R.id.c1_lane4);
        cashier1_lane5 =  getDialog().findViewById(R.id.c1_lane5);
        cashier1_lane6 =  getDialog().findViewById(R.id.c1_lane6);

        callFunc();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_cashier2holder, null);

        builder.setView(view).setTitle("Cashier 2").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }

        });

        return builder.create();
    }

    public void callFunc(){
        mhandler.postDelayed(runnableFunc, 5000);
        runnableFunc.run();
    }
    public Runnable runnableFunc = new Runnable() {
        @Override
        public void run() {
            viewCashier1_nextQueue2();
            viewCashier1_nextQueue3();
            viewCashier1_nextQueue4();
            viewCashier1_nextQueue5();
            viewCashier1_nextQueue6();
            mhandler.postDelayed(this, 5000);
        }
    };
    private void viewCashier1_nextQueue2() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.CASHIER2_NEXTQUEUE2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if(obj.getString("queue_no")=="null"){
                                cashier1_queue2.setText("NO QUEUE");
                                cashier1_lane2.setText("NO QUEUE");
                            }
                            else{
                                cashier1_queue2.setText(obj.getString("queue_no"));
                                cashier1_lane2.setText(obj.getString("service_lane"));
                                if(obj.getString("service_lane").equals("PRIORITY")){
                                    cashier1_lane2.setTextColor(Color.parseColor("#FF0000"));

                                }
                                if(obj.getString("service_lane").equals("REGULAR")){
                                    cashier1_lane2.setTextColor(Color.parseColor("#808080"));

                                }
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
                                getContext(),
                                ("No internet connection."),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        )
        {};
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
    private void viewCashier1_nextQueue3() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.CASHIER2_NEXTQUEUE3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if(obj.getString("queue_no")=="null"){
                                cashier1_queue3.setText("NO QUEUE");
                                cashier1_lane3.setText("NO QUEUE");
                            }
                            else{
                                cashier1_queue3.setText(obj.getString("queue_no"));
                                cashier1_lane3.setText(obj.getString("service_lane"));
                                if(obj.getString("service_lane").equals("PRIORITY")){
                                    cashier1_lane3.setTextColor(Color.parseColor("#FF0000"));

                                }
                                if(obj.getString("service_lane").equals("REGULAR")){
                                    cashier1_lane3.setTextColor(Color.parseColor("#808080"));

                                }
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
                                getContext(),
                                ("No internet connection."),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        )
        {};
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
    private void viewCashier1_nextQueue4() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.CASHIER2_NEXTQUEUE4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if(obj.getString("queue_no")=="null"){
                                cashier1_queue4.setText("NO QUEUE");
                                cashier1_lane4.setText("NO QUEUE");
                            }
                            else{
                                cashier1_queue4.setText(obj.getString("queue_no"));
                                cashier1_lane4.setText(obj.getString("service_lane"));
                                if(obj.getString("service_lane").equals("PRIORITY")){
                                    cashier1_lane4.setTextColor(Color.parseColor("#FF0000"));

                                }
                                if(obj.getString("service_lane").equals("REGULAR")){
                                    cashier1_lane4.setTextColor(Color.parseColor("#808080"));

                                }
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
                                getContext(),
                                ("No internet connection."),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        )
        {};
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
    private void viewCashier1_nextQueue5() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.CASHIER2_NEXTQUEUE5,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if(obj.getString("queue_no")=="null"){
                                cashier1_queue5.setText("NO QUEUE");
                                cashier1_lane5.setText("NO QUEUE");
                            }
                            else{
                                cashier1_queue5.setText(obj.getString("queue_no"));
                                cashier1_lane5.setText(obj.getString("service_lane"));
                                if(obj.getString("service_lane").equals("PRIORITY")){
                                    cashier1_lane5.setTextColor(Color.parseColor("#FF0000"));

                                }
                                if(obj.getString("service_lane").equals("REGULAR")){
                                    cashier1_lane5.setTextColor(Color.parseColor("#808080"));

                                }
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
                                getContext(),
                                ("No internet connection."),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        )
        {};
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
    private void viewCashier1_nextQueue6() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.CASHIER2_NEXTQUEUE6,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if(obj.getString("queue_no")=="null"){
                                cashier1_queue6.setText("NO QUEUE");
                                cashier1_lane6.setText("NO QUEUE");
                            }
                            else{
                                cashier1_queue6.setText(obj.getString("queue_no"));
                                cashier1_lane6.setText(obj.getString("service_lane"));
                                if(obj.getString("service_lane").equals("PRIORITY")){
                                    cashier1_lane6.setTextColor(Color.parseColor("#FF0000"));

                                }
                                if(obj.getString("service_lane").equals("REGULAR")){
                                    cashier1_lane6.setTextColor(Color.parseColor("#808080"));

                                }
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
                                getContext(),
                                ("No internet connection."),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        )
        {};
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
