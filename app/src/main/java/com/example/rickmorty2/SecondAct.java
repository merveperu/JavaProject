package com.example.rickmorty2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;


public class SecondAct extends AppCompatActivity{

    //Hello trying github
    //All locations
    String location_url = "https://rickandmortyapi.com/api/location";
    ArrayList<String> button;
    Adapter adapter;
    Adapter adapter2=new Adapter(button);
    GridView gridView ;
    CustomAdapter customAdapter;
    RecyclerView rv;
    String names;
    //This is the ArayList for locations
    ArrayList<Characters> filmler;
    LinearLayoutManager linearLayoutManager;
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;
    TextView my_no_text;
    ImageView my_no_image;

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
        my_no_image=findViewById(R.id.default_image);
        my_no_image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.select2));
        my_no_text = findViewById(R.id.my_default);
        //Setting the button source----
        add_to_button();
        //Text shows if there is no button clicked

        //when no button clicked at the beginning


        my_no_text.setText("Please select a location!");
        my_no_text.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);





        //Setting gridview source----
        gridView=findViewById(R.id.gridView);

        //It makes it full-screen page
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }


    public void filter_chars(String api_url) {

        my_no_text.setText("");
        my_no_image.setImageDrawable(null);
        filmler = new ArrayList<Characters>();
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, api_url, null, new Response.Listener<JSONObject>() {

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
                        int my_size;
                        if(URLs.length()>30){
                            my_size=20;
                            for(int j=0;j<my_size;j++){

                                Log.d("URL", "Çekilen resident URL'leri: " + URLs.getString(j));
                                get_single_char(URLs.getString(j));


                            }
                        }
                        else if(URLs.length()<30 & URLs.length()>0){
                            my_size=URLs.length();
                            for(int j=0;j<my_size;j++){

                                Log.d("URL", "Çekilen resident URL'leri: " + URLs.getString(j));
                                get_single_char(URLs.getString(j));


                            }
                        }
                        else{
                            //No residents
                            Log.d(TAG, "onResponse: No residents here!");
                            filmler =new ArrayList<>();
                            CustomAdapter my_empty_adapter = new CustomAdapter(filmler);
                            gridView.setAdapter(my_empty_adapter);
                            my_no_text.setText("No Residents here!");


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
                        customAdapter = new CustomAdapter(filmler);
                        gridView.setAdapter(customAdapter);
                    }

                    // Özel Adapter
                    CustomAdapter my_custom_adapter = new CustomAdapter( filmler);
                    gridView.setAdapter(my_custom_adapter);
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







