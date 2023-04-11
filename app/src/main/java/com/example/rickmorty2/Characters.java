package com.example.rickmorty2;

import android.graphics.Bitmap;

import java.net.URL;

public class Characters {
    private String name;
    private String image;

    public Characters(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
