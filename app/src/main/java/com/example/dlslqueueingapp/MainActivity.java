package com.example.dlslqueueingapp;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.NotificationCompat;

import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private EditText studNumET, passwordET;

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Account Login Session
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Home.class));
            return;
        }

        studNumET = findViewById(R.id.studNumET);
        passwordET = findViewById(R.id.passwordET);
        passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
    }

    private void userLogin() {
        final ImageView image1 = findViewById(R.id.dlsllogo);
        final ImageView image2 = findViewById(R.id.capilla);
        final ImageView image3 = findViewById(R.id.bulk_1);
        final ImageView image4 = findViewById(R.id.bulk_2);
        final String studentNumber = studNumET.getText().toString().trim();
        final String pass = passwordET.getText().toString().trim();
        final String queueNumber="asd";
        final String cashierNumber="wew";

        final Intent intent = new Intent(MainActivity.this, Home.class);

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")) {
                                progressDialog.dismiss();
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getString("pass")
                                        );

                                if(obj.getString("hasQueueingNumber")=="NO"){
                                    Toast.makeText(
                                            getApplicationContext(),
                                            ("User has no Queueing Number. Please queue a ticket first."),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                                else {
                                    SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString("qn", obj.getString("queueNumber"));
                                    editor.putString("cn", obj.getString("cashierNumber"));
                                    editor.putString("sn", obj.getString("studentNumber"));
                                    editor.putString("st", obj.getString("serviceType"));
                                    editor.putString("sl", obj.getString("serviceLane"));
                                    editor.putString("d", obj.getString("date"));
                                    editor.putString("t", obj.getString("time"));
                                    editor.commit();

                                    Pair[] pairs = new Pair[4];
                                    pairs[0] = new Pair<View, String>(image1, "example_transition");
                                    pairs[1] = new Pair<View, String>(image2, "capilla_transition");
                                    pairs[2] = new Pair<View, String>(image3, "bulk1_transition");
                                    pairs[3] = new Pair<View, String>(image4, "bulk2_transition");
                                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                                    startActivity(intent, options.toBundle());
                                    finish();
                                }

                                SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("qn", obj.getString("queueNumber"));
                                editor.putString("cn", obj.getString("cashierNumber"));
                                editor.putString("sn", obj.getString("studentNumber"));
                                editor.putString("st", obj.getString("serviceType"));
                                editor.putString("sl", obj.getString("serviceLane"));
                                editor.putString("d", obj.getString("date"));
                                editor.putString("t", obj.getString("time"));
                                editor.commit();

                                Pair[] pairs = new Pair[4];
                                pairs[0] = new Pair<View, String>(image1, "example_transition");
                                pairs[1] = new Pair<View, String>(image2, "capilla_transition");
                                pairs[2] = new Pair<View, String>(image3, "bulk1_transition");
                                pairs[3] = new Pair<View, String>(image4, "bulk2_transition");
                                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                                startActivity(intent, options.toBundle());
                                finish();
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
                        progressDialog.dismiss();

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
                params.put("studentNumber", studentNumber);
                params.put("pass", pass);
                params.put("queueNumber", queueNumber);
                params.put("cashierNumber", cashierNumber);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view == loginButton) {
            userLogin();
        }
    }
}
