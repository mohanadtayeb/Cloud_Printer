package com.example.cloudprinter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class order extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;

    DatabaseReference mDatabaseReference;

    orderInfo mOrderInfo;

     EditText copies;

    Spinner bind_paper,cover;

    CheckBox plastic_cover,colored;

    TextView total_price;

    LinearLayout order_activity;

    Intent to_order_history;

    public int total,before_total,total_copies;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        copies = (EditText) findViewById(R.id.copies);
        bind_paper =(Spinner) findViewById(R.id.bind_paper);
        cover = (Spinner) findViewById(R.id.cover);
        plastic_cover = (CheckBox) findViewById(R.id.plastic_cover);
        colored = (CheckBox) findViewById(R.id.colored);
        total_price = (TextView) findViewById(R.id.total_price);
        order_activity = findViewById(R.id.order_activity);

        mOrderInfo = new orderInfo();

        to_order_history = new Intent(this,orderHistory.class);

        copies.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                total = 0;
                if (bind_paper.getSelectedItemId() != 0) {
                    total = total + 5;
                }

                if (cover.getSelectedItemId() != 0 ){
                    total = total + 3;
                }

                if (plastic_cover.isChecked()) {
                    total = total + 2;
                }

                if (colored.isChecked()) {
                    total = total + 2; // this is temporary for testing purpose only because the colored price depends on the number of pages
                }




            }

            @Override
            public void afterTextChanged(Editable editable) {


                try {
                    total_copies = Integer.parseInt(copies.getText().toString());
                    total = total * total_copies;
                    total_price.setText("total: " + total);
                } catch (NumberFormatException e) {

                    total = 0;
                    total_price.setText("total: 0");
                } catch (IllegalStateException e) {
                    total = 0;
                }





            }
        });

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        String key = mFirebaseDatabase.getReference().push().getKey();
        assert key != null;
        mDatabaseReference = mFirebaseDatabase.getReference("orders").child(key);




    }





    public void checkout_clicked (View v) {

        String mCopies, mBind_paper, mCover,mPlasic_cover, mColored, mEmail;
        mCopies = copies.getText().toString();
        mBind_paper = bind_paper.getSelectedItem().toString();
        mCover = cover.getSelectedItem().toString();
        if (plastic_cover.isChecked()) {
            mPlasic_cover = "yes";
        } else {
            mPlasic_cover = "no";
        }

        if (colored.isChecked()) {
            mColored = "yes";
        } else {
            mColored = "no";
        }
        Intent intent = getIntent();
        mEmail = intent.getStringExtra("email");

        try {
            if (Integer.parseInt(mCopies)<0 ) {
                Toast.makeText(this, "Please enter the number of copies", Toast.LENGTH_SHORT).show();
            } {
                addDataToFirebase(mCopies,mBind_paper,mCover,mPlasic_cover,mColored,mEmail);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "please enter a valid number of copies", Toast.LENGTH_SHORT).show();
        }




    }

    public void addDataToFirebase(String mCopies,String mBind_paper,String mCover,String mPlasic_cover,String mColored,String mEmail) {
        mOrderInfo.setColored(mColored);
        mOrderInfo.setBind_paper(mBind_paper);
        mOrderInfo.setCopies(mCopies);
        mOrderInfo.setCover(mCover);
        mOrderInfo.setPlastic_cover(mPlasic_cover);
        mOrderInfo.setEmail(mEmail);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mDatabaseReference.setValue(mOrderInfo);

                Toast.makeText(order.this, "order completed successfully ", Toast.LENGTH_SHORT).show();
                startActivity(to_order_history);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(order.this, "error: please try again", Toast.LENGTH_SHORT).show();
            }
        });

    }
}