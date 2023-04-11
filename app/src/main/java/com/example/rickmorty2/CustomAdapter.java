package com.example.rickmorty2;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Characters> filmler;

    public CustomAdapter(Context context, ArrayList<Characters> filmler) {
        this.context = context;
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

        // inflate the layout for each list row
        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.card_design, viewGroup, false);

        }


        Characters mevcutFilm = (Characters) getItem(i);


        TextView textViewFilmAdi = (TextView)view.findViewById(R.id.textViewFilmAdi);

        ImageView imageViewPoster = (ImageView) view.findViewById(R.id.imageViewPoster);


        textViewFilmAdi.setText(mevcutFilm.getName());
        //imageViewPoster.setImageURI(Uri.parse(mevcutFilm.getImage()));
        ImageView ivBasicImage = (ImageView) view.findViewById(R.id.imageViewPoster);
        Picasso.get().load(mevcutFilm.getImage()).into(ivBasicImage);





        return view;
    }
}
