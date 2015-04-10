package com.fiec.meetingsfinder.model;

import android.graphics.drawable.Drawable;

/**
 * Created by damato on 04/04/2015.
 */
public class DrawerMenu {
    private Drawable image;
    private String text;

    public DrawerMenu(Drawable image, String text) {
        this.image = image;
        this.text = text;
    }

    public Drawable getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}
