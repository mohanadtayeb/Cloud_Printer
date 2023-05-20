package com.example.cloudprinter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;

public class DownloadPdfFile {


   private static final String TAG = "DownloadPdfFile";

   private FirebaseStorage storage;
   private StorageReference storageRef;
   final DownloadPdfFile c = this;

   public DownloadPdfFile() {
      storage = FirebaseStorage.getInstance();
      storageRef = storage.getReference();
   }
   Context mContext;
   public DownloadPdfFile(Context mContext) {
      this.mContext = mContext;
   }

   public void downloadPdfFile(String pdfPath, String fileName) {
      StorageReference pdfRef = storageRef.child(pdfPath);
      pdfRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
         @Override
         public void onSuccess(Uri uri) {
            Log.d(TAG, "PDF file download URL: " + uri);

            // Download the PDF file.

            DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(fileName);
            request.setDescription("Downloading PDF file...");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            downloadManager.enqueue(request);
         }
      }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {
            Log.e(TAG, "Error downloading PDF file: " + e.getMessage());
         }
      });
   }

}
