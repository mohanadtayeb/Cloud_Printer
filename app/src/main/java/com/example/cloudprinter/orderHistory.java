package com.example.cloudprinter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class orderHistory extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    BottomNavigationView mBottomNavigationView;
    Intent to_order_history,to_order_page,to_home;
    orderHistoryAdapter mAdapter;
DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("orders");
        mRecyclerView = findViewById(R.id.recycler1);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBottomNavigationView = findViewById(R.id.bottonnav);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        to_home = new Intent(this, MainUserPage.class);
        to_order_page = new Intent(this,order.class);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.order:
                startActivity(to_order_page);
                break;
            case R.id.home:
                startActivity(to_home);
                finish();
                break;
            case R.id.order_history:

                break;
        }
        if (fragment != null) {
            loadFragment(fragment);
        }
        return true;
    }
    void loadFragment(Fragment fragment) {
        //to attach fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout, fragment).commit();
    }

}