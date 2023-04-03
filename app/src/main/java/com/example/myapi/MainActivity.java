package com.example.myapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String url ="https://fakestoreapi.com/products";

    TextView textView;

    Adapter adapter;

    ArrayList<Model> list = new ArrayList<>();

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recy);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray res=response;


                    for (int i=0 ; i<res.length(); i++){

                        JSONObject object = res.getJSONObject(i);

                        String id = object.getString("id");
                        String title = object.getString("title");
                        String description= object.getString("description");


                      //  String userId = object.getString("userId");
                      //  String date = object.getString("date");

                        Log.d("TAG", "onResponse: ");

                        Model model = new Model(id, title, description);
                        list.add(model);
                    }
                    adapter = new Adapter(MainActivity.this, list);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String>header=new HashMap<>();
                header.put("User_agent","Mozilla/5.0");
                return header;
            }

        };

        requestQueue.add(jsonArrayRequest);
    }
}