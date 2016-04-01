package com.example.aniss.inse6140;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class Show_received_image extends AppCompatActivity {
    private ImageView imageView1;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_received_image);
        imageView1 = (ImageView) findViewById(R.id.ivImage1);
        bundle = getIntent().getExtras();
        byte[] aesPlainTextByteArray = bundle.getByteArray("AESPlainTextByteArray");
        Bitmap bitmap = BitmapFactory.decodeByteArray(aesPlainTextByteArray, 0, aesPlainTextByteArray.length);
        imageView1.setImageBitmap(bitmap);


    }

}
