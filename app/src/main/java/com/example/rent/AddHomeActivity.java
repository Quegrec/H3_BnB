package com.example.rent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddHomeActivity extends AppCompatActivity {
    Button btnGallery, btDecode; // btnGallery == btEncode
    TextView textView;
    ImageView imgGallery;
    String sImage;
    private final int GALLERT_RAQ_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home);

        imgGallery = findViewById(R.id.home_img);
        btnGallery = findViewById(R.id.btn_add_img);
        btDecode = findViewById(R.id.bt_decode);
        textView = findViewById(R.id.text_view);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERT_RAQ_CODE);
            }
        });

        btDecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Decode base64 string
                byte[] bytes = Base64.decode(sImage, Base64.DEFAULT);
                // Initialize bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imgGallery.setImageBitmap(bitmap);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK && requestCode==GALLERT_RAQ_CODE){
            // For Gallery
            //imgGallery.setImageURI(data.getData());

            // When result is ok
            // Initialize uri
            Uri uri = data.getData();
            try{
                // Inirialise bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                // Initialize bitmap
                byte[] bytes = stream.toByteArray();
                // Get base64 encoded string
                sImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                textView.setText(sImage);
            }catch( IOException e){
                e.printStackTrace();
            }

        }
    }
}