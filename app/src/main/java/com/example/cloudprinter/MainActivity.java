package com.example.cloudprinter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Intent mIntent;
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