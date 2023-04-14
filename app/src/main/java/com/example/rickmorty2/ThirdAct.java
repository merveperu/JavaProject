package com.example.rickmorty2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class ThirdAct extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Bundle intentExtras = getIntent().getExtras();
        String currentChar = intentExtras.getString("Username");
        Log.d("TAG", "onCreate: Üçüncü sayfa "+currentChar);


        //It makes it full-screen page
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }





}