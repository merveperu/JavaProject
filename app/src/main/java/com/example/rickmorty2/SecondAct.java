package com.example.rickmorty2;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Window;
import android.os.Bundle;
import android.os.Build;
import android.view.WindowManager;
import android.util.Log;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class SecondAct extends AppCompatActivity {

    //All locations
    String location_url = "https://rickandmortyapi.com/api/location";

    Adapter adapter;
    CustomAdapter customAdapter;
    RecyclerView rv;

    //This is the ArayList for locations
    ArrayList<String> button;
    ArrayList<Characters> filmler;
    LinearLayoutManager linearLayoutManager;



    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_rv);

        //Horizontal RecyclerView
        rv = findViewById(R.id.horizontalRv);
        rv.setLayoutManager(linearLayoutManager);

        button= new ArrayList<>();

        //Setting the button source----
        add_to_button();

        //Gridview
        add_to_grid();






        //It makes it full-screen page
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }
    //Functions

    private void add_to_button(){

        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, location_url,  null, new Response.Listener<JSONObject>() {

            // Takes the response from the JSON request
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    JSONArray tutorialsArray = obj.getJSONArray("results");

                    for (int i = 0; i < tutorialsArray.length(); i++) {
                        JSONObject tutorialsObject = tutorialsArray.getJSONObject(i);
                        String name = tutorialsObject.getString("name");
                        Log.d(TAG, name.toString());
                        button.add(name.toString());
                        if (adapter == null) {
                            // Adapter'i oluşturup RecyclerView'e atayın
                            adapter = new Adapter(button) {
                            };
                            rv.setAdapter(adapter);
                        }
                        // Adapter'e veri eklendiğini bildirin
                        adapter.notifyItemInserted(button.size() - 1);
                    }
                }
                // Try and catch are included to handle any errors due to JSON
                catch (JSONException e) {
                    // If an error occurs, this prints the error to the log
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(jsonObjectRequest);
        linearLayoutManager = new LinearLayoutManager(SecondAct.this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(linearLayoutManager);


    }
    private void add_to_grid(){
        GridView gridView=(GridView)findViewById(R.id.gridView);
        filmler = new ArrayList<Characters>();



        requestQueue = Volley.newRequestQueue(this);
        String grid_url="https://rickandmortyapi.com/api/character";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, grid_url,  null, new Response.Listener<JSONObject>() {

            // Takes the response from the JSON request
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject obj = new JSONObject(String.valueOf(response));
                    JSONArray results = obj.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject tutorialsObject = results.getJSONObject(i);
                        String name = tutorialsObject.getString("name");
                        String image = tutorialsObject.getString("image");

                        Log.d(TAG, name.toString());
                        Log.d(TAG, image.toString());


                        Characters film1 = new Characters(name.toString(),image);
                        filmler.add(film1);

                        // Adapter'e veri eklendiğini bildirin
                        adapter.notifyItemInserted(button.size() - 1);
                    }
                }
                // Try and catch are included to handle any errors due to JSON
                catch (JSONException e) {
                    // If an error occurs, this prints the error to the log
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(jsonObjectRequest);



        // Özel Adapter
        CustomAdapter adapter = new CustomAdapter(this, filmler);


        gridView.setAdapter(adapter);
    }
}







