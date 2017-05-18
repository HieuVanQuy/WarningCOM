package com.x23_team.warningcom.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.x23_team.warningcom.R;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public class TrafficWarningActivity extends AppCompatActivity {

    Bitmap bitmap;

    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000;
    private double longitude, latitude;
    private LocationManager locationManager;

    private SQLiteDatabase database;
    ImageButton btnAddImage;
    Button btnPost;
    EditText edtDescribe, edtLocation;
    ImageView imgView;
    LinearLayout layout;

    Geocoder geocoder;
    public static String DATABASE_NAME = "arirang.sqlite";
    List<Address> addresses;

    public void init(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES, MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, new MyLocationListener());

        geocoder = new Geocoder(this, Locale.getDefault());



        edtLocation = (EditText) findViewById(R.id.edtLocation);
        imgView = (ImageView) findViewById(R.id.imgView);
        edtDescribe = (EditText) findViewById( R.id.edtDescribe );
        btnAddImage = (ImageButton) findViewById(R.id.btnAddImage);
        layout = (LinearLayout) findViewById(R.id.layout);
        btnPost = (Button) findViewById( R.id.btnPost );
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePicture();
            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage();
            }
        });

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            longitude = Double.parseDouble(String.valueOf(location.getLongitude()));
            latitude = Double.parseDouble(String.valueOf(location.getLatitude()));
        }
        edtLocation.setText(getCompleteAddressString(latitude, longitude));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_warning);
        database = openOrCreateDatabase( "WarningCOM.sqlite", MODE_PRIVATE, null );
        try {
            InputStream myInput;
            myInput = getAssets().open( DATABASE_NAME );
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();


        btnPost.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                String content = edtDescribe.getText().toString();
                String latlng = ""+latitude+","+longitude;
                String address = edtLocation.getText().toString();
                String Type_Id = "1";
                final ContentValues values = new ContentValues(  );
                values.put( "Content", content);
                values.put( "Image", encoded);
                values.put( "LatLng", latlng);
                values.put( "Address", address);
                values.put( "Type_ID", 1 );
                String msg="";
                if(database.insert( "Post", null, values )==-1){
                    msg = "Fail to Post";
                }else {
                    msg = "Insert Post is success";
                    Intent intent = new Intent( TrafficWarningActivity.this, FragmentMenu.class );
                    startActivity( intent );
                    finish();
                }
                Toast.makeText( TrafficWarningActivity.this, msg, Toast.LENGTH_LONG ).show();
            }
        } );
    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(", ");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My location", "" + strReturnedAddress.toString());
            } else {
                Log.w("My location", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Cannot get Address!");

        }
        return strAdd;
    }

    private void capturePicture() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(intent, 100);
        } else {
            Toast.makeText(getApplication(), "Camera is not supported", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imgView.setImageBitmap(bitmap);

            layout.setVisibility(View.INVISIBLE);
            imgView.setVisibility(View.VISIBLE);


        } else if (requestCode == RESULT_CANCELED)
            recreate();
        else recreate();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FragmentMenu.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            longitude = Double.parseDouble(String.valueOf(location.getLongitude()));
            latitude = Double.parseDouble(String.valueOf(location.getLatitude()));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    }

    private void viewImage(){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent intent = new Intent(this,ViewImageActivity.class);
        intent.putExtra("image",byteArray);
        startActivity(intent);
    }
}
