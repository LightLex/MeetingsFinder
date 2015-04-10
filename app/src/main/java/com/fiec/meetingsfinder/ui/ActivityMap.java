package com.fiec.meetingsfinder.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fiec.meetingsfinder.R;
import com.fiec.meetingsfinder.model.Result;
import com.fiec.meetingsfinder.service.Core;
import com.fiec.meetingsfinder.util.Globals;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by damato on 04/09/2015.
 */
public class ActivityMap extends ActionBarActivity implements OnFindMeeting, DialogInterface.OnCancelListener{

    private Toolbar toolbar;
    private GoogleMap map;
    private Dialog dialog;
    private String linktoregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle(R.string.maintitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(this);
        dialog.setTitle(getResources().getString(R.string.txtloadingtitle));
        dialog.setContentView(R.layout.dialog_loading);
        String ID = getIntent().getStringExtra(Globals.KEY_MEETINGID);
        if(ID!=null){
            dialog.show();
            new Core(this,ID);
        }
        else{

        }

        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mapmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home: onBackPressed(); break;//NavUtils.navigateUpFromSameTask(this); break;
            case R.id.register:
                if(linktoregister!=null && linktoregister.length()>5){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linktoregister));
                    startActivity(browserIntent);
                }
                else{
                    Toast.makeText(this,getResources().getString(R.string.msgregerror),Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void findMeeting(Result result) {
        if(dialog!=null && result!=null){
            dialog.dismiss();
            putMarker(result);
        }
        else{
            dialog.cancel();
        }
    }

    private void putMarker(Result result) {
        if(map!=null){
            linktoregister = result.getRegistrationURL();
            String when = result.getWhen();
            if(when.contains("When\nDate:")) when=when.substring(14);
            String where = result.getWhere();
            if(where.startsWith("\nWhere\nBuilding:")) where=where.substring(17);
            else if(where.startsWith("\nWhere")) where=where.substring(7);
            PointF points = result.getPoints();
            LatLng latLng = new LatLng(points.x,points.y);
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(where)
                    .snippet(when));
            marker.showInfoWindow();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
            map.animateCamera(CameraUpdateFactory.zoomTo(15),2000,null);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        Toast.makeText(this,getResources().getString(R.string.txtcantload),Toast.LENGTH_SHORT).show();
        onBackPressed();
        //NavUtils.navigateUpFromSameTask(this);
    }
}
