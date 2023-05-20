package com.example.cloudprinter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainUserPage extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener{
    Intent to_order_history,to_order_page,to_home;
BottomNavigationView mBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_page);
        mBottomNavigationView = findViewById(R.id.bottonnav);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);


        to_order_history = new Intent(this,orderHistory.class);
        to_order_page = new Intent(this,order.class);

    }

    public void order_clicked(View view) {
        startActivity(to_order_page);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.order:
                startActivity(to_order_page);
                finish();
                break;
            case R.id.home:

                break;
            case R.id.order_history:
                startActivity(to_order_history);
                finish();
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