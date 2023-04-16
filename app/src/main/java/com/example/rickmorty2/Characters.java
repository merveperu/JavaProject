package com.example.rickmorty2;

import android.graphics.Bitmap;

import java.net.URL;

public class Characters {
    private String name;
    private String image;
    private String character_url;
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



    public String getCharacter_url() {
        return character_url;
    }

    public void setCharacter_url(String character_url) {
        this.character_url = character_url;
    }



    public Characters(String name, String image,String character_url,String gender) {
        this.name = name;
        this.image = image;
        this.character_url = character_url;
        this.gender = gender;
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
