package com.example.cloudprinter;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class signup extends AppCompatActivity {
    Button signup_button, location_button;

    EditText email_editText, password_editText;
    CheckBox owner_checkbox;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        location_button = findViewById(R.id.location_button);
        owner_checkbox = findViewById(R.id.owner_checkbox);
        location_button.setVisibility(View.GONE);
        email_editText = findViewById(R.id.sing_up_Email);
        password_editText = findViewById(R.id.sing_up_password);
        mAuth = FirebaseAuth.getInstance();


    }

    public void owner_checkbox_clicked(View view) {
        if (owner_checkbox.isChecked()) {
            location_button.setVisibility(View.VISIBLE);
        } else {
            location_button.setVisibility(View.GONE);
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
                    Toast.makeText(signup.this, "Registration successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(signup.this, "Registration failed, please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}