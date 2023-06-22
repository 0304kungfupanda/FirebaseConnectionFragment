package com.example.firebaseconnectionfragment;

import static android.Manifest.permission.CAMERA;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.devanagari.DevanagariTextRecognizerOptions;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class TextScanner extends AppCompatActivity {

   ImageButton btnCaptureImage, btnCopyText, btnSaveText, btnHindiText,btnClearText;
   ImageView imageScanner;
   TextView txtViewScanComplete;


    Uri resultUri;
    TextRecognizer textRecognizer;
    TextRecognizer textRecognizerDevanagari;
    TextRecognizer textRecognizerEnglish;
    EditText edtRecognizedText;

    Boolean isEnglish = false;


   private static final int REQUEST_CAMERA_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_scanner);

        btnCaptureImage = findViewById(R.id.btnGetImage);
        btnCopyText = findViewById(R.id.btnCopyText);
        edtRecognizedText= findViewById(R.id.edtRecognizedText);
        btnClearText = findViewById(R.id.btnClearText);
        btnHindiText = findViewById(R.id.btnHindiText);
        btnSaveText = findViewById(R.id.btnSaveText);
        imageScanner = findViewById(R.id.imageScanner);
        txtViewScanComplete = findViewById(R.id.txtViewScanComplete);


        textRecognizerDevanagari = TextRecognition.getClient(new DevanagariTextRecognizerOptions.Builder().build());
        textRecognizerEnglish = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        textRecognizer = textRecognizerDevanagari;


        if (ContextCompat.checkSelfPermission(this, CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(TextScanner.this, new String[]{
                    CAMERA
            },REQUEST_CAMERA_CODE);
        }

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(TextScanner.this);
            }
        });

        btnCopyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = edtRecognizedText.getText().toString();
                Toast.makeText(TextScanner.this, "Text Copied", Toast.LENGTH_SHORT).show();

                if (text.isEmpty()){
                    Toast.makeText(TextScanner.this, "Enter Text to Copy", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnHindiText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEnglish){
                    isEnglish = false;
                    btnHindiText.setImageDrawable(ContextCompat.getDrawable(TextScanner.this,R.drawable.eng_letter_h));
                    textRecognizer = textRecognizerDevanagari;

                }else{
                    isEnglish = true;
                    btnHindiText.setImageDrawable(ContextCompat.getDrawable(TextScanner.this,R.drawable.letter_e));
                    textRecognizer = textRecognizerEnglish;
                }

            }
        });

        btnSaveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iResult = new Intent();
                String text = edtRecognizedText.getText().toString();
                iResult.putExtra("text_data",text);
                setResult(Activity.RESULT_OK,iResult);
                finish();

            }
        });

        btnClearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtRecognizedText!=null) {
                    edtRecognizedText.setText("");
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK && data!=null){

                resultUri = result.getUri();
                 recogniseText();
            }else{
                Toast.makeText(this, "Failed to load Image uri", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void recogniseText() {

        if(resultUri != null){

            try {
                InputImage inputImage = InputImage.fromFilePath(this,resultUri);
                // InputImage image = InputImage.fromBitmap(bitmap, rotationDegree);

                Task<Text> result =
                        textRecognizer.process(inputImage)
                                .addOnSuccessListener(new OnSuccessListener<Text>() {
                                    @Override
                                    public void onSuccess(Text text) {
                                        String recogniseText = text.getText();
                                        edtRecognizedText.setText(recogniseText);
                                        imageScanner.setImageDrawable(ContextCompat.getDrawable(TextScanner.this,R.drawable.text_box));
                                        txtViewScanComplete.setVisibility(View.VISIBLE);


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(TextScanner.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}