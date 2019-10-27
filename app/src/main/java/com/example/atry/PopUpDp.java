package com.example.atry;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class PopUpDp extends AppCompatActivity {


    static  ImageView dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_dp);

        dp=findViewById(R.id.popup1);
        dp.setImageBitmap(bmp);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int height=800*2;
        int width=600*2;

        getWindow().setLayout((int)(height), (int)(width));

    }
}
