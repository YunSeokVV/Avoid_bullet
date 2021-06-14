package com.example.avoid_bullet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class experiment extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiment);

//        Intent intnet = getIntent();
//        byte[] arr = getIntent().getByteArrayExtra("image");
//        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
//        ImageView BigImage = (ImageView) findViewById(R.id.이미지);
//        BigImage.setImageBitmap(image);
    }
}