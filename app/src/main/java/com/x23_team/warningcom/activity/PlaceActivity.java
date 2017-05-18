package com.x23_team.warningcom.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.x23_team.warningcom.Entity.Post;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by weste on 5/10/2017.
 */

public class PlaceActivity {
    private static LatLng Latlng;
    private Marker Maker;
    private GoogleMap mMap;
    private Post post;
    private SQLiteDatabase database;
    public PlaceActivity(){
        database = openOrCreateDatabase( "WarningCOM.sqlite", null );
    }



    public PlaceActivity(LatLng latLng) {
        this.Latlng = latLng;
    }


    public void setPlaceTraffic(LatLng latlng){
        Cursor c = database.query("Post", null, null, null, null, null, null);
        this.Maker = mMap.addMarker(new MarkerOptions().position(latlng)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                            .title("Traffic")
                            .snippet(c.getString( 1 )));
    }
    public void setPlaceWeather(LatLng latlng){
        Cursor c = database.query("Post", null, null, null, null, null, null);
        this.Maker = mMap.addMarker(new MarkerOptions().position(latlng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title("Traffic")
                .snippet(c.getString( 1 )));
    }
    public void setPlaceHelpMe(LatLng latlng){
        Cursor c = database.query("Post", null, null, null, null, null, null);
        this.Maker = mMap.addMarker(new MarkerOptions().position(latlng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .title("Traffic")
                .snippet(c.getString( 1 )));
    }
}
