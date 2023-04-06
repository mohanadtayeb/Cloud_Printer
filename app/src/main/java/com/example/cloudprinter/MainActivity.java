package com.example.cloudprinter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import jp.wasabeef.blurry.Blurry;

public class MainActivity extends AppCompatActivity {
    Intent mIntent;

    Bitmap printer_background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIntent = new Intent(this, login.class);
    }


    public void text_clicked(View view) {
        Toast.makeText(this, "an activity starter", Toast.LENGTH_SHORT).show();


    }

    public void start_button(View view) {
        startActivity(mIntent);
    }

}