package com.example.cloudprinter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class monitorActivity extends AppCompatActivity {
    ListView mListView;
    ArrayList order_arr;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    Intent to_order_detail_monitor;

    Intent mIntent;

    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        mListView = findViewById(R.id.list);
        mIntent = getIntent();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
       mDatabaseReference = mFirebaseDatabase.getReference("orders");
       to_order_detail_monitor = new Intent(this, ActivityOrderDetailMonitor.class);
        order_arr = new ArrayList<String>();
           mDatabaseReference.addChildEventListener(new ChildEventListener() {
               @Override
               public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                       order_arr.add(snapshot.getKey());
                       addToList();


               }

               @Override
               public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

               }

               @Override
               public void onChildRemoved(@NonNull DataSnapshot snapshot) {

               }

               @Override
               public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    to_order_detail_monitor.putExtra("order_ref",adapterView.getSelectedItem().toString());
                    String selectedItem = (String) adapterView.getItemAtPosition(i);
                   to_order_detail_monitor.putExtra("order_ref",adapterView.getItemAtPosition(i).toString());
                   startActivity(to_order_detail_monitor);
                }
            });


    }

    public void addToList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(monitorActivity.this, android.R.layout.simple_list_item_1,order_arr);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        mListView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
    }
}