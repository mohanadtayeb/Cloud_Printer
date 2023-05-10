package com.example.cloudprinter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class signup extends AppCompatActivity {
    Button signup_button;

 Intent to_login_activity,to_order_activity;
    EditText email_editText, password_editText,libName_editText;
    CheckBox owner_checkbox;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    libLocation mLibLocation;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        libName_editText = findViewById(R.id.lib_name_edittext);
        owner_checkbox = findViewById(R.id.owner_checkbox);
        libName_editText.setVisibility(View.GONE);
        email_editText = findViewById(R.id.sing_up_Email);
        password_editText = findViewById(R.id.sing_up_password);
        mAuth = FirebaseAuth.getInstance();
        to_login_activity = new Intent(this,login.class);
        to_order_activity = new Intent(signup.this,order.class);
        mLibLocation = new libLocation();


        mFirebaseDatabase = FirebaseDatabase.getInstance();


    }

    public void owner_checkbox_clicked(View view) {
        if (owner_checkbox.isChecked()) {
            libName_editText.setVisibility(View.VISIBLE);
        } else {
            libName_editText.setVisibility(View.GONE);
        }
    }

    public void signup_clicked(View view) {
        register();
    }

    public void register() {
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

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (owner_checkbox.isChecked()) {
                        addDataToFirebase(email);
                    }

                    Toast.makeText(signup.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    startActivity(to_login_activity);
                } else {
                    Toast.makeText(signup.this, "Registration failed, please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void addDataToFirebase(String mEmail) {
        GPSHelper gpsHelper = new GPSHelper(signup.this);
        gpsHelper.getMyLocation();
        Double lat = gpsHelper.getLatitude();
        Double log = gpsHelper.getLongitude();
        mLibLocation.setLatitude(lat.toString());
        mLibLocation.setLongitude(log.toString());
        mLibLocation.setEmail(mEmail);
        mDatabaseReference = mFirebaseDatabase.getReference("lib_location").child(libName_editText.getText().toString().toLowerCase());

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mDatabaseReference.setValue(mLibLocation);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}