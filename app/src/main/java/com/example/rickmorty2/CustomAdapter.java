package com.example.rickmorty2;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class CustomAdapter extends BaseAdapter {

    //private Context context;
      ArrayList<Characters> filmler;

    public CustomAdapter(ArrayList<Characters> filmler) {
        //this.context = context;
        this.filmler = filmler;
    }


    @Override
    public int getCount() {
        return filmler.size();
    }

    @Override
    public Object getItem(int i) {
        return filmler.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Characters mevcutFilm = (Characters) getItem(i);
        String  my_gender = mevcutFilm.getGender();
        Log.d("TAG", "gender of "+mevcutFilm.getName()+" is "+mevcutFilm.getGender() );

        // inflate the layout for each list row
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.card_design, viewGroup, false);

        }
        if(Objects.equals(my_gender, "Female")){
            view.setBackgroundResource(R.drawable.female);
        }
        else if(Objects.equals(my_gender, "Male")){
            view.setBackgroundResource(R.drawable.male);
        }
        else{
            view.setBackgroundResource(R.drawable.corner);
        }







        TextView textViewFilmAdi = (TextView)view.findViewById(R.id.textViewFilmAdi);
        ImageView imageViewPoster = (ImageView) view.findViewById(R.id.imageViewPoster);
        textViewFilmAdi.setText(mevcutFilm.getName());
        ImageView ivBasicImage = (ImageView) view.findViewById(R.id.imageViewPoster);
        Picasso.get().load(mevcutFilm.getImage()).into(ivBasicImage);




        //GridView'e tıklayınca gelen isim
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String getText= mevcutFilm.getCharacter_url();
                Log.d("name:", "onClick: grid deki isim: "+getText.toString());
                Intent intent =new Intent(v.getContext(),ThirdAct.class);
                Bundle extras = new Bundle();
                extras.putString("Username", getText);
                intent.putExtras(extras);
                v.getContext().startActivity(intent);
            }


        });
        return view;
    }
}
