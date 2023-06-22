package com.example.firebaseconnectionfragment;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.devanagari.DevanagariTextRecognizerOptions;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;


public class ScannerActivity extends AppCompatActivity {

    ImageButton btnClear, btnCopy, btnGetImage, btnHindiText, btnSaveText;
    EditText recognizedText;

    Uri imageUri;

    ImageView imageScanner;

    TextRecognizer textRecognizer;
    TextRecognizer textRecognizerDevanagri;
    TextRecognizer textRecognizerEnglish;

    Boolean isEnglish =true;


    String scannedText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        btnClear = findViewById(R.id.btnClearText);
        btnCopy = findViewById(R.id.btnCopyText);
        btnGetImage = findViewById(R.id.btnGetImage);
        recognizedText =findViewById(R.id.edtRecognizedText);
        btnHindiText = findViewById(R.id.btnHindiText);
        btnSaveText = findViewById(R.id.btnSaveText);
        imageScanner = findViewById(R.id.imageScanner);

        //textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        textRecognizerEnglish = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        textRecognizerDevanagri = TextRecognition.getClient(new DevanagariTextRecognizerOptions.Builder().build());
        textRecognizer = textRecognizerEnglish;



        btnGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(ScannerActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

               // CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(ScannerActivity.this);
            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = recognizedText.getText().toString();
                Toast.makeText(ScannerActivity.this, "Text copied", Toast.LENGTH_SHORT).show();

                if (text.isEmpty()){
                    Toast.makeText(ScannerActivity.this, "No Text to copy", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSaveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iResult = new Intent();
                String text = recognizedText.getText().toString();
                iResult.putExtra("text_data",text);
                setResult(Activity.RESULT_OK,iResult);
                finish();
            }
        });

        btnHindiText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (isEnglish){
                    isEnglish = false;
                    btnHindiText.setImageDrawable(ContextCompat.getDrawable(ScannerActivity.this,R.drawable.eng_letter_h));
                   // btnGetImage.setText("Capture Image Hindi");
                    textRecognizer = textRecognizerDevanagri;


                }else {
                    isEnglish = true;
                    btnHindiText.setImageDrawable(ContextCompat.getDrawable(ScannerActivity.this,R.drawable.letter_e));
                    //btnGetImage.setText("Capture Image English");
                    textRecognizer = textRecognizerEnglish;


                }

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (recognizedText!=null){
                    recognizedText.setText("");
                }
            }
        });


    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){

            if (data!=null){

                imageUri = data.getData();

                recognizeText();
                //imageScanner.setVisibility(View.GONE);


            }else {
                Toast.makeText(this, "Imaged not selected", Toast.LENGTH_SHORT).show();
            }

        }
    }



    private void recognizeText() {

        if (imageUri!=null){

            try {
                InputImage inputImage = InputImage.fromFilePath(this,imageUri);


                Task<Text> result =
                        textRecognizer.process(inputImage)
                                .addOnSuccessListener(new OnSuccessListener<Text>() {
                                    @Override
                                    public void onSuccess(Text text) {

                                        String recogniseText1 = text.getText();


                                        recognizedText.setText(recogniseText1);


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(ScannerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





}
