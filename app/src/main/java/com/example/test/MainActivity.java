package com.example.test;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> fioAndNumber = new ArrayList<>();
    HashMap<String, String> map;
    int idItem = -1;

//    String api = "http://jsonplaceholder.typicode.com/todos/1";
    String api = "http://10.0.2.2:7866/api/qwe";
//    String api = "https://jsonplaceholder.typicode.com/photos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview = findViewById(R.id.listview1);
        Button btnAddMain = findViewById(R.id.btnAddMain);

        map = new HashMap<>();
        map.put("Name", "Антон");
        map.put("Tel", "79209837611");
        fioAndNumber.add(map);

        map = new HashMap<>();
        map.put("Name", "Дима");
        map.put("Tel", "79209289848");
        fioAndNumber.add(map);

        SimpleAdapter adapter = new SimpleAdapter(this, fioAndNumber, android.R.layout.simple_list_item_2,
                new String[]{"Name", "Tel"},
                new int[]{android.R.id.text1, android.R.id.text2});
        listview.setAdapter(adapter);

        ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent intent = result.getData();
                            String name = intent.getStringExtra("name");
                            String tel = intent.getStringExtra("tel");
                            String id = intent.getStringExtra("id");
                            if(Objects.equals(id, "add")){
                                map = new HashMap<>();
                                map.put("Name", name);
                                map.put("Tel", tel);
                                fioAndNumber.add(map);
                                adapter.notifyDataSetChanged();
                            }
                            else if (Objects.equals(id, "put")){
                                map = new HashMap<>();
                                map.put("Name", String.valueOf(name));
                                map.put("Tel", String.valueOf(tel));
                                fioAndNumber.set(idItem, map);
                                adapter.notifyDataSetChanged();
                            }
                            else if (Objects.equals(id, "delete")){
                                fioAndNumber.remove(idItem);
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }
                });
        btnAddMain.setOnClickListener(v -> {
            Intent intent = new Intent(this, add.class);
            mStartForResult.launch(intent);
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("qwe", "itemClick: position = " + position + ", id = " + id);
                HashMap<String,String> temp = new HashMap<>();
                temp.put("Name", fioAndNumber.get(position).get("Name"));
                temp.put("Tel", fioAndNumber.get(position).get("Tel"));
                idItem = position;


                Intent zzz = new Intent(MainActivity.this, put_and_delet.class);
                zzz.putExtra("name", fioAndNumber.get(position).get("Name"));
                zzz.putExtra("tel", fioAndNumber.get(position).get("Tel"));
                mStartForResult.launch(zzz);
            }
        });


        getPerson();
//        new getPersonAPI().execute(api);
    }

    private void getPerson() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("api", "onResponse: " + response.toString());
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++){
                                JSONObject singleObject = array.getJSONObject(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse: " + error.getLocalizedMessage());
            }
        });
        queue.add(stringRequest);
    }
    private class getPersonAPI extends AsyncTask<String, String, String>{

        protected void onPreExecute(){
            super.onPreExecute();
            Log.e("api", "Ожидаем ответ от сервера");
        }
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null)
                    buffer.append(line).append("\n");

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Log.e("api", "result: " + result);
        }
    }
}