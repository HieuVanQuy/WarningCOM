package com.x23_team.warningcom.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.x23_team.warningcom.R;

public class WeatherWarningActivity extends AppCompatActivity {
    ImageButton btnAddImage, btnFindLocation;
    ImageView imgView;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_warning);

        btnAddImage = (ImageButton) findViewById(R.id.btnAddImage);
        imgView = (ImageView) findViewById(R.id.imgView);
        layout = (LinearLayout) findViewById(R.id.layout);
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePicture();
            }
        });

        btnFindLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


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
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgView.setImageBitmap(bitmap);
            layout.setVisibility(View.INVISIBLE);
            imgView.setVisibility(View.VISIBLE);
        } else if (requestCode == RESULT_CANCELED)
            capturePicture();
        else capturePicture();
    }


}
