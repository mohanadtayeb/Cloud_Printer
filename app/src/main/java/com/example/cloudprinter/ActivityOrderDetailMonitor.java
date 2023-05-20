package com.example.cloudprinter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ActivityOrderDetailMonitor extends AppCompatActivity {
Intent mIntent;
Button finished_button;

TextView order,bind_paper,colored,copies,cover,plastic_cover,status;
    private FirebaseStorage storage;

StorageReference storageRef;
    String orderRef;
DatabaseReference mDatabaseReference;

String fileRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_monitor);
        mIntent = getIntent();

        order = findViewById(R.id.order_title);
        bind_paper = findViewById(R.id.bind_paper);
        colored = findViewById(R.id.colored);
        copies = findViewById(R.id.copies);
        cover = findViewById(R.id.cover);
        plastic_cover = findViewById(R.id.plastic_cover);
        status = findViewById(R.id.status);
        orderRef = mIntent.getStringExtra("order_ref");

        finished_button = findViewById(R.id.finished_button);


      getDataFromFireBase();

    }
    public void downloadPdfFile(String pdfPath, String fileName) {
        StorageReference pdfRef = storageRef.child(pdfPath);
        pdfRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("ActivityOrderDetailMonitor", "PDF file download URL: " + uri);

                // Download the PDF file.

                DownloadManager downloadManager = (DownloadManager) ActivityOrderDetailMonitor.this.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle(fileName);
                request.setDescription("Downloading PDF file...");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                downloadManager.enqueue(request);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ActivityOrderDetailMonitor", "Error downloading PDF file: " + e.getMessage());
                Toast.makeText(ActivityOrderDetailMonitor.this, "error: file not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void download_clicked(View view) {
        downloadPdfFile(fileRef,"order.pdf");
        Toast.makeText(this, fileRef, Toast.LENGTH_SHORT).show();

    }

    public void finished_clicked(View view) {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mDatabaseReference.child("status").setValue("finished");
                Toast.makeText(ActivityOrderDetailMonitor.this, "status updated to finished", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityOrderDetailMonitor.this, "error: cannot change the status", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void back_clicked(View view) {
        finish();
    }

    public void getDataFromFireBase() {



        mDatabaseReference = FirebaseDatabase.getInstance().getReference("orders").child(orderRef);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                order.setText("order: "+ orderRef);
                bind_paper.setText("bind paper: " + snapshot.child("bind_paper").getValue().toString());
                colored.setText("colored: " + snapshot.child("colored").getValue().toString());
                cover.setText("cover: " + snapshot.child("cover").getValue().toString());
                copies.setText("number of copies: " + snapshot.child("copies").getValue().toString());
                plastic_cover.setText("plastic cover: " + snapshot.child("plastic_cover").getValue().toString());
                fileRef = snapshot.child("order_file_ref").getValue().toString();
                try {
                    status.setText(snapshot.child("status").getValue().toString());

                    if (status.getText().equals("on progress")) {
                        status.setTextColor(Color.YELLOW);
                    } else {
                        status.setTextColor(Color.GREEN);
                        finished_button.setVisibility(View.GONE);

                    }
                } catch (NullPointerException err ) {
                    Toast.makeText(ActivityOrderDetailMonitor.this, "status is empty", Toast.LENGTH_SHORT).show();
                    status.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            Log.w("database error",error.toException());
            }
        });
    }
}