package com.example.firebaseconnectionfragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.net.URL;

public class InsertCameraImage extends AppCompatActivity {

    ImageView img ;
    Button browse, upload;
    Uri filepath ;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_camera_image);

        img =  findViewById(R.id.imgViewCamera);
        browse = findViewById(R.id.BtnImageBrowser);
        upload = findViewById(R.id.BtnUploadImage);



        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Dexter.withContext(InsertCameraImage.this)
                        .withPermission(Manifest.permission.READ_MEDIA_IMAGES)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                openFile(filepath);

//                                Intent intent = new Intent(Intent.ACTION_PICK);
//                                intent.setType("image/*");
//                                startActivityForResult(Intent.createChooser(intent,"Please select Image"),1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                                permissionToken.continuePermissionRequest();

                            }
                        }).check();

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadToFirebase();

            }
        });


    }

    private void UploadToFirebase() {

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("File Uploader");
        dialog.show();


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploader = storage.getReference().child("image1");
        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                dialog.dismiss();
                Toast.makeText(InsertCameraImage.this, "File Uploaded", Toast.LENGTH_SHORT).show();

            }
        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded : "+(int)percent+"%");


                    }
                });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode ==1 && resultCode == RESULT_OK){

            filepath = data.getData();

            try {

                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);

            }catch (Exception ex){

                ex.printStackTrace();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Request code for selecting a PDF document.
    private static final int PICK_PDF_FILE = 1;

    private void openFile(Uri pickerInitialUri) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(intent, PICK_PDF_FILE);
    }

//    private void UploadToFirebase() {
//
//        ProgressDialog dialog = new ProgressDialog(this);
//        dialog.setTitle("File Uploader");
//        dialog.show();
//
//
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference uploader = storage.getReference().child("image1");
//        uploader.putFile(filepath)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                dialog.dismiss();
//                Toast.makeText(InsertCameraImage.this, "File Uploaded", Toast.LENGTH_SHORT).show();
//
//            }
//        })
//                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//
//                        float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
//                        dialog.setMessage("Uploaded : "+(int)percent+"%");
//
//
//                    }
//                });
//
//
//    }
}

