package com.example.rickmorty2;

import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.os.Bundle;
import android.os.Build;
import android.view.WindowManager;

public class SecondAct extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().hide();
        //It makes it full-screen page
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}