package com.fiec.meetingsfinder.util;

/**
 * Created by Daniel Martínez on 03/04/2015.
 * Project: Passo
 */
import android.util.Log;

/***
 *
 * @author damato
 * TAG PARA HACER LOGS
 *
 */

public class Tag {
    public static String _TAG = "gmaTag";

    public static void d(String msg){
        Log.d(_TAG, msg);
    }

    public static void e(String msg){
        Log.e(_TAG, msg);
    }

    public static void i(String msg){
        Log.i(_TAG, msg);
    }

    public static void v(String msg){
        Log.v(_TAG, msg);
    }

    public static void w(String msg){
        Log.w(_TAG, msg);
    }

    public static void wtf(String msg){
        Log.wtf(_TAG, msg);
    }

    public static void changeTag(String tag){
        _TAG = tag;
    }
}