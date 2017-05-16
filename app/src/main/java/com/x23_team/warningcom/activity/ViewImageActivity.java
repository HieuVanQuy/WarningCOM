package com.x23_team.warningcom.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.x23_team.warningcom.R;

public class ViewImageActivity extends AppCompatActivity {

    ImageView imgEditing;
    Button btnNewImage;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        btnNewImage = (Button) findViewById(R.id.btnNewImage);

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imgEditing = (ImageView) findViewById(R.id.imageViewEditImage);
        imgEditing.setImageBitmap(bmp);

        btnNewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePicture();
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
            bitmap = (Bitmap) data.getExtras().get("data");
            imgEditing.setImageBitmap(bitmap);

        } else if (requestCode == RESULT_CANCELED)
            capturePicture();
        else recreate();

    }


}
