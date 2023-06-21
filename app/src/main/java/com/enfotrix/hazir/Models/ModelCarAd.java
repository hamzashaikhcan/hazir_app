package com.enfotrix.hazir.Models;

import android.graphics.Bitmap;

public class ModelCarAd {
        int photo;
        String name;


    public ModelCarAd(int photo, String name) {
        this.photo = photo;
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
