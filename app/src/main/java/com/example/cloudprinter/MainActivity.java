package com.example.cloudprinter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Intent to_login_activity,to_googleMaps_activity,to_order_Activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        to_login_activity = new Intent(this, login.class);
        to_googleMaps_activity = new Intent(this,MapsActivity2.class);
        to_order_Activity = new Intent(this,order.class);

    }


    public void text_clicked(View view) {
        Toast.makeText(this, "an activity starter", Toast.LENGTH_SHORT).show();


    }

    public void start_button(View view) {
        startActivity(to_login_activity);
    }

}