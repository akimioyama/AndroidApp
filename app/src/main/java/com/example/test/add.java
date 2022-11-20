package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

public class add extends AppCompatActivity {

    HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        EditText name = findViewById(R.id.addName);
        EditText tel = findViewById(R.id.addNumber);
        Button btn = findViewById(R.id.btnAdd);

        btn.setOnClickListener(v -> {

            String namename = String.valueOf(name.getText());
            String teltel = String.valueOf(tel.getText());
            String api = "http://192.168.0.82:7866/api/qwe/" + namename + "/" + teltel;

            Intent data = new Intent();
            data.putExtra("name", namename);
            data.putExtra("tel", teltel);
            data.putExtra("api", api);
            data.putExtra("id", "add");
            setResult(RESULT_OK, data);
//            getPerson(api);
            finish();


        });
    }

    private void getPerson(String api) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("api", "onResponse: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse: " + error.getLocalizedMessage() + error.toString());
            }
        });
        queue.add(stringRequest);
    }
}