package com.example.rickmorty2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
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
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class SecondAct extends AppCompatActivity {

    //All locations
    String location_url = "https://rickandmortyapi.com/api/location";
    ArrayList<String> button;
    Adapter adapter;
    Adapter adapter2=new Adapter(button);
    String clicked_button_url;
    GridView gridView ;
    CustomAdapter customAdapter;
    RecyclerView rv;
    String names;

    //This is the ArayList for locations

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

        filmler=new ArrayList<>();

        //Setting the button source----
        add_to_button();
        clicked_button_url= adapter2.getAdapter_url().toString();


        Log.d(TAG, "onResponse: SecondAct'te url yazdırma "+clicked_button_url);
        Log.d(TAG, "onResponse: SecondAct'te buton tıklandı mı?: "+adapter2.clicked);
        //Setting gridview source----
        gridView=findViewById(R.id.gridView);

        filter_chars();

        //Bunları kullanma
        //get_single_char("https://rickandmortyapi.com/api/character/2");
        //add_to_grid();

        //It makes it full-screen page
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }

    public void filter_chars() {

        GridView gridView = (GridView) findViewById(R.id.gridView);
        filmler = new ArrayList<Characters>();
        //String location_url = "https://rickandmortyapi.com/api/location/?name= Citadel Of Ricks";

        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, adapter2.getAdapter_url(), null, new Response.Listener<JSONObject>() {

            // Takes the response from the JSON request
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject obj = new JSONObject(String.valueOf(response));
                    JSONArray results = obj.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        //Bu lokasyonda yaşayan karakterlerin URL si
                        //Bu URL den isim ve fotoğraf çekmeliyim
                        JSONObject tutorialsObject = results.getJSONObject(i);
                        JSONArray  URLs = tutorialsObject.getJSONArray("residents");
                        String my_url;
                        for(int j=0;j<10;j++){
                            my_url=URLs.getString(j);
                            Log.d("URL", "Çekilen resident URL'leri: " + my_url);
                            get_single_char(my_url.toString());


                        }


                        // Adapter'e veri eklendiğini bildirin
                        //adapter.notifyItemInserted(filmler.size() - 1);
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
        CustomAdapter adapter = new CustomAdapter( filmler);
        gridView.setAdapter(adapter);

    }

    public void get_single_char(String grid_url) {
        GridView gridView=(GridView)findViewById(R.id.gridView);
        filmler = new ArrayList<Characters>();



        requestQueue = Volley.newRequestQueue(this);
        //String grid_url="https://rickandmortyapi.com/api/character/2";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, grid_url,  null, new Response.Listener<JSONObject>() {

            // Takes the response from the JSON request
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject obj = new JSONObject(String.valueOf(response));




                        String name = response.getString("name");
                        String image = response.getString("image");
                        String gender= response.getString("gender");

                        Log.d("name of character", name.toString());
                        //Log.d("image url", image.toString());


                        Characters film1 = new Characters(name.toString(),image.toString(),grid_url.toString(),gender);
                        filmler.add(film1);

                    if (customAdapter == null) {
                        // Adapter'i oluşturup RecyclerView'e atayın
                        customAdapter = new CustomAdapter(filmler) {
                        };
                        gridView.setAdapter(customAdapter);
                    }
                        // Adapter'e veri eklendiğini bildirin
                        //adapter.notifyItemInserted(filmler.size() - 1);

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
        CustomAdapter adapter = new CustomAdapter( filmler);
        gridView.setAdapter(adapter);
    }




    //Functions
    //This function for adding "location" names into button
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
                        //adapter.notifyItemInserted(button.size() - 1);
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
    //This function for adding "characters" into gridview
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


                        //Characters film1 = new Characters(name.toString(),image,grid_url);
                        //filmler.add(film1);

                        // Adapter'e veri eklendiğini bildirin
                        //adapter.notifyItemInserted(button.size() - 1);
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
        CustomAdapter adapter = new CustomAdapter( filmler);
        gridView.setAdapter(adapter);
    }
}







