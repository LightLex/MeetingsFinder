package com.fiec.meetingsfinder.util;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by damato on 03/17/2015.
 */
public class Globals {

    public static int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static String KEY_MEETINGID = "meetingid";
}
