package com.example.cloudprinter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
  Intent to_signup_activity,to_order_activity,to_orderHistory,to_orderHistoryAdapter,intent,to_monitor_activity,to_home;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    EditText password_editText, email_editText;
    ArrayList<String> mArrayList;
    CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        to_signup_activity = new Intent(login.this, signup.class);
        to_order_activity = new Intent(login.this,order.class);
        to_orderHistory = new Intent(login.this,orderHistory.class);
        to_orderHistoryAdapter = new Intent(login.this, orderHistoryAdapter.class);
        to_monitor_activity = new Intent(login.this, monitorActivity.class);
        to_home = new Intent(login.this, MainUserPage.class);
        mCheckBox = findViewById(R.id.login_pass_Remember_chekbox);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("lib_location");
        mAuth = FirebaseAuth.getInstance();
        password_editText = findViewById(R.id.login_password);
        email_editText = findViewById(R.id.login_email);
        intent = getIntent();
    }

    public void signup_clicked(View view) {
        startActivity(to_signup_activity);
    }

    public void button_clicked(View view) {
        login();
    }

    public void login() {
        String email, password;

        email = email_editText.getText().toString();
        password = password_editText.getText().toString();
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            Toast.makeText(this, "please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(login.this, "login successful", Toast.LENGTH_SHORT).show();
                    to_orderHistoryAdapter.putExtra("email",email);
                    to_orderHistory.putExtra("email",email);
                    to_order_activity.putExtra("email",email);

                    if(mCheckBox.isChecked()) {
                        startActivity(to_monitor_activity);
                        to_monitor_activity.putExtra("email",email);


                    } else {
                        startActivity(to_home);
                    }

                } else {
                    Toast.makeText(login.this, "email or password is not valid, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}