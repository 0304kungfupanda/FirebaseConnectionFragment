package com.example.firebaseconnectionfragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class InsertPdf extends AppCompatActivity {

    Button btnBrowsePdf, btnUploadPdf;

    Uri pdfFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_pdf);

        btnBrowsePdf = findViewById(R.id.btnBrowsePdf);
        btnUploadPdf = findViewById(R.id.btnUploadPdf);


        btnBrowsePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFile(pdfFilePath);

            }
        });

        btnUploadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadPdf();
            }
        });
    }

    private void UploadPdf() {

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("File Uploader");
        dialog.show();



        Uri file = pdfFilePath;

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploader = storage.getReference().child("sample");
        uploader.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        dialog.dismiss();
                        Toast.makeText(InsertPdf.this, "File Uploaded", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded : "+(int)percent+"%");


                    }
                });
//        StorageReference riversRef = storageRef.child("doc/"+file.getLastPathSegment());
//        uploadTask = riversRef.putFile(file);
//
//// Register observers to listen for when the download is done or if it fails
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle unsuccessful uploads
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//            }
//        });
    }

    // Request code for selecting a PDF document.
    private static final int PICK_PDF_FILE = 1;

    private void openFile(Uri pickerInitialUri) {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(intent, PICK_PDF_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode==1 && resultCode == RESULT_OK){

            pdfFilePath = data.getData();
            Log.d("TAG", "onActivityResult: "+ pdfFilePath.toString());

//            try {
//
//            }catch (Exception ex){
//
//                ex.printStackTrace();
//
//            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}