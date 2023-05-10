package com.example.cloudprinter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
  Intent to_signup_activity,to_order_activity,to_orderHistory,to_orderHistoryAdapter;
    FirebaseAuth mAuth;

    EditText password_editText, email_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        to_signup_activity = new Intent(login.this, signup.class);
        to_order_activity = new Intent(login.this,order.class);
        to_orderHistory = new Intent(login.this,orderHistory.class);
        to_orderHistoryAdapter = new Intent(login.this, orderHistoryAdapter.class);
        mAuth = FirebaseAuth.getInstance();
        password_editText = findViewById(R.id.login_password);
        email_editText = findViewById(R.id.login_email);
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
                    startActivity(to_orderHistory);
                } else {
                    Toast.makeText(login.this, "email or password is not valid, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}