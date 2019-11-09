package com.example.dlslqueueingapp;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

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
                                                obj.getString("studentNumber"),
                                                obj.getString("pass")
                                        );
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
                                error.getMessage(),
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
