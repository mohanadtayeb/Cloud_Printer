package com.example.cloudprinter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class orderHistory extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    orderHistoryAdapter mAdapter;
DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("orders");
        mRecyclerView = findViewById(R.id.recycler1);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<orderInfo> options = new FirebaseRecyclerOptions.Builder<orderInfo>().setQuery(mDatabaseReference,orderInfo.class).build();
        mAdapter = new orderHistoryAdapter(options);

        mRecyclerView.setAdapter(mAdapter);






    }

    @Override protected void onStart()
    {
        super.onStart();
        mAdapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        mAdapter.stopListening();
    }
}