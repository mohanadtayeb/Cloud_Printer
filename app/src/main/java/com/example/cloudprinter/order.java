package com.example.cloudprinter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class order extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    Button upload_button;
    DatabaseReference mDatabaseReference,mDatabaseLibraryReference;

    FirebaseStorage storage;
    StorageReference mStorageReference;

    orderInfo mOrderInfo;

     EditText copies;

    Spinner bind_paper,cover,lib_spinner;

    CheckBox plastic_cover,colored;

    TextView total_price;

    LinearLayout order_activity;

    Intent to_order_history;

    Uri pdfFile;

    ArrayList lib_arr;
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
        lib_spinner = findViewById(R.id.printing_house_spinner);
        upload_button = findViewById(R.id.upload_button);

        mDatabaseLibraryReference = FirebaseDatabase.getInstance().getReference("lib_location");
        lib_arr = new ArrayList<String>();
        mDatabaseLibraryReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                lib_arr.add(snapshot.getKey());
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(order.this, android.R.layout.simple_spinner_dropdown_item,lib_arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lib_spinner.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        adapter.add("none");





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

        String mCopies, mBind_paper, mCover,mPlasic_cover, mColored, mEmail,mLocation;
        mCopies = copies.getText().toString();
        mBind_paper = bind_paper.getSelectedItem().toString();
        mCover = cover.getSelectedItem().toString();
        mLocation = lib_spinner.getSelectedItem().toString();
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
                addDataToFirebase(mCopies,mBind_paper,mCover,mPlasic_cover,mColored,mEmail,mLocation);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "please enter a valid number of copies", Toast.LENGTH_SHORT).show();
        }




    }

    public void addDataToFirebase(String mCopies,String mBind_paper,String mCover,String mPlasic_cover,String mColored,String mEmail,String mLocation) {
        mOrderInfo.setColored(mColored);
        mOrderInfo.setBind_paper(mBind_paper);
        mOrderInfo.setCopies(mCopies);
        mOrderInfo.setCover(mCover);
        mOrderInfo.setPlastic_cover(mPlasic_cover);
        mOrderInfo.setEmail(mEmail);
        mOrderInfo.setLocation(mLocation);

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

public void upload_clicked(View view) {
openFileChooser();
}

private void uploadPDFFileToFirebase(Uri data) {
        StorageReference reference = mStorageReference.child("uploadPDF" + System.currentTimeMillis() + ".pdf");

        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(order.this, "file uploaded successfully ", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) {
                    Uri uri = uriTask.getResult();
                }




            }
        });
}


    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        fileChoose.launch(Intent.createChooser(intent,"PDF FILE SELECET"));
}


ActivityResultLauncher<Intent> fileChoose = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
    @Override
    public void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Uri data = Uri.parse(result.getData().toString());
            uploadPDFFileToFirebase(data);
    } else {
            Toast.makeText(order.this, "error", Toast.LENGTH_SHORT).show();
        }
}
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//        if(resultCode == 12 && requestCode == RESULT_OK && data != null && data.getData() != null){
//
//            uploadPDFFileToFirebase(data.getData());
//
//
//
//
//        } else {
//            Toast.makeText(this, "error:  please try again", Toast.LENGTH_SHORT).show();
//        }
    });
}