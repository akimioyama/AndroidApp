package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class put_and_delet extends AppCompatActivity {

    HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_and_delet);

        Button btnPut = findViewById(R.id.btnPut);
        Button btnDel = findViewById(R.id.btnDel);

        Bundle extras = getIntent().getExtras();
        EditText name = findViewById(R.id.putdelName);
        EditText tel =  findViewById(R.id.putdelNumber);
        TextView testtest = findViewById(R.id.testtest);

        String idid = extras.getString("ididid");

        name.setText(extras.getString("name"));
        tel.setText(extras.getString("tel"));

        testtest.setText(idid);


        btnDel.setOnClickListener(v -> {
            Intent data = new Intent();
            data.putExtra("id", "delete");
            setResult(RESULT_OK, data);
            finish();
        });

        btnPut.setOnClickListener(v -> {
            String namename = String.valueOf(name.getText());
            String teltel = String.valueOf(tel.getText());

            Intent data = new Intent();
            data.putExtra("name", namename);
            data.putExtra("tel", teltel);
            data.putExtra("id", "put");
            setResult(RESULT_OK, data);
            finish();
        });
    }
}