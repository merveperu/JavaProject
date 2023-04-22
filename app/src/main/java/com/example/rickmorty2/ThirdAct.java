package com.example.rickmorty2;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

public class ThirdAct extends AppCompatActivity {

    String all_episodes="https://rickandmortyapi.com/api/episode";
    RequestQueue requestQueue;
    TextView status;
    TextView headline;
    TextView specy;
    TextView gender;
    TextView origin;
    TextView location;
    TextView episodes;
    ImageView image;
    TextView created;
    ArrayList<String> list_of_episodes = new ArrayList<String>();
    SimpleDateFormat simpleDateFormat;


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
        episodes=findViewById(R.id.episodes);
        image=findViewById(R.id.imageView2);
        created=findViewById(R.id.created);

        //Back Button
        ImageButton back = (ImageButton)findViewById(R.id.backToSecond);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_button();
            }
        });

        Bundle intentExtras = getIntent().getExtras();
        String currentChar = intentExtras.getString("Username");
        Log.d("TAG", "onCreate: Third Page "+currentChar);
        Log.d("TAG", "My character url inside OnCreate: "+currentChar);
        fetch(currentChar);

        //It makes it full-screen page
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
    private void back_button(){
        Log.d(TAG, "onClick: Back Button Working");
        Intent myIntent = new Intent(getApplicationContext(), SecondAct.class);
        startActivity(myIntent);
        finish();
    }

    private void fetch(String my_url) {
        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);
        Log.d("TAG", "fetch fonksiyonu: "+my_url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, my_url,  null, new Response.Listener<JSONObject>() {

            // Takes the response from the JSON request
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                        //Name
                        String name = response.getString("name");
                        headline.setText(name);
                        Log.d("TAG","Name from api: "+ name.toString());

                        //Status
                        String status_from_api = response.getString("status");
                        Log.d("TAG","Status from api: "+ status_from_api.toString());
                        status.setText(status_from_api);

                        //Specy
                        String specy_from_api = response.getString("species");
                        Log.d("TAG","Specy from api: "+ specy_from_api.toString());
                        specy.setText(specy_from_api);

                        //Gender
                        String gender_from_api = response.getString("gender");
                        Log.d("TAG","Specy from api: "+ gender_from_api.toString());
                        gender.setText(gender_from_api);

                        //Origin
                        JSONObject origin_from_api = response.getJSONObject("origin");
                        String get_origin_from_api= origin_from_api.getString("name");
                        String get_origin_url_from_api= origin_from_api.getString("url");
                        Log.d("TAG", "origin name: "+get_origin_from_api);
                        origin.setText(get_origin_from_api);

                        //Location
                        JSONObject location_from_api = response.getJSONObject("location");
                        String get_location_from_api= location_from_api.getString("name");
                        location.setText(get_location_from_api);

                        //Image
                        String image_from_api = response.getString("image");
                        Picasso.get().load(image_from_api).into(image);

                        //Episode
                        JSONArray  current_episodes = response.getJSONArray("episode");
                        for(int n=0;n< current_episodes.length();n++){
                            String str=current_episodes.getString(n);
                            str=str.replaceFirst(".*/(\\w+).*","$1");
                            Log.d(TAG, "son değer: "+str);
                            list_of_episodes.add(str);
                        }

                        String str = list_of_episodes.stream().collect(Collectors.joining(","));
                        episodes.setText(str);

                        //Created at
                        String created_at_from_api = response.getString("created");
                        DateFormat outputFormat = new SimpleDateFormat("dd LLLL yyyy HH:mm:ss", Locale.US);
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);
                        Date date = inputFormat.parse(created_at_from_api);
                        String outputText = outputFormat.format(date);
                        Log.d(TAG, "created at: "+outputText);
                        created.setText(outputText);




                }
                // Try and catch are included to handle any errors due to JSON
                catch (JSONException e) {
                    // If an error occurs, this prints the error to the log
                    e.printStackTrace();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
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