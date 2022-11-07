package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

            Intent data = new Intent();
            data.putExtra("name", namename);
            data.putExtra("tel", teltel);
            data.putExtra("id", "add");
            setResult(RESULT_OK, data);
            finish();

        });
    }
}