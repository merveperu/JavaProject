package com.example.rickmorty2;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class ThirdAct extends AppCompatActivity {

    String all_episodes="https://rickandmortyapi.com/api/episode";
    String base= "https://rickandmortyapi.com/api/character/?name=";
    String url;
    RequestQueue requestQueue;
    TextView status;
    TextView headline;
    TextView specy;
    TextView gender;
    TextView origin;
    TextView location;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        status = findViewById(R.id.status);
        headline = findViewById(R.id.headline);
        specy = findViewById(R.id.specy);
        gender = findViewById(R.id.gender);
        origin=findViewById(R.id.origin);
        location=findViewById(R.id.location);

        Bundle intentExtras = getIntent().getExtras();
        String currentChar = intentExtras.getString("Username");
        headline.setText(currentChar);
        Log.d("TAG", "onCreate: Üçüncü sayfa "+currentChar);
        //This is the url to fetch from api
        url=base+currentChar;
        //Log.d("TAG", "Created URL now is: "+url);
        fetch();


        //It makes it full-screen page
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


    public void my_episodes(String my_character){
        Log.d(TAG, "my_episodes: Çalışıyor");



        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, all_episodes, null, new Response.Listener<JSONObject>() {

            // Takes the response from the JSON request
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject obj = new JSONObject(String.valueOf(response));
                    JSONArray results = obj.getJSONArray("results");
                    //Her bir bölüm
                    for (int i = 0; i < results.length(); i++) {
                        //Bu bölümde oynayan karakterlerin URL leri
                        //Bu URL den id çekmeliyim çekmeliyim
                        JSONObject tutorialsObject = results.getJSONObject(i);
                        JSONArray  URLs = tutorialsObject.getJSONArray("characters");
                        String my_url;
                        for(int j=0;j<10;j++){
                            my_url=URLs.getString(j);
                            Log.d("URL", "Çekilen URL: " + URLs.getString(j));



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
    }
    public void get_second_url(String url){

        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,  null, new Response.Listener<JSONObject>() {

            // Takes the response from the JSON request
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject obj = new JSONObject(String.valueOf(response));
                    String location_from_url = response.getString("name");
                    Log.d("Name of location from origin url: ", location_from_url.toString());
                    location.setText(location_from_url);

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
    }
    private void fetch() {
        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);
        Log.d("TAG", "url is: "+url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,  null, new Response.Listener<JSONObject>() {

            // Takes the response from the JSON request
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    JSONArray tutorialsArray = obj.getJSONArray("results");

                    for (int i = 0; i < tutorialsArray.length(); i++) {
                        JSONObject tutorialsObject = tutorialsArray.getJSONObject(i);
                        String name = tutorialsObject.getString("name");
                        Log.d("TAG","Name from api: "+ name.toString());
                        //status
                        String status_from_api = tutorialsObject.getString("status");
                        Log.d("TAG","Status from api: "+ status_from_api.toString());
                        status.setText(status_from_api);
                        //specy
                        String specy_from_api = tutorialsObject.getString("species");
                        Log.d("TAG","Specy from api: "+ specy_from_api.toString());
                        specy.setText(specy_from_api);
                        //gender
                        String gender_from_api = tutorialsObject.getString("gender");
                        Log.d("TAG","Specy from api: "+ gender_from_api.toString());
                        gender.setText(gender_from_api);

                        //origin
                        JSONObject origin_from_api = tutorialsObject.getJSONObject("origin");
                        String get_origin_from_api= origin_from_api.getString("name");
                        String get_origin_url_from_api= origin_from_api.getString("url");
                        Log.d("TAG", "origin name: "+get_origin_from_api);
                        Log.d("TAG", "origin url: "+get_origin_url_from_api);
                        origin.setText(get_origin_from_api);
                        get_second_url(get_origin_url_from_api);

                        //getting episodes array
                        my_episodes(url);
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



    }


}