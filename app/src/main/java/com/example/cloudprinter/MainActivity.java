package com.example.cloudprinter;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void text_clicked(View view) {
        Toast.makeText(this, "an activity starter", Toast.LENGTH_SHORT).show();

    }
}