package com.example.firebaseconnectionfragment;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    Button btnClear, btnCopy, btnGetImage, btnHindiText, btnSaveText;
    EditText recognizedText;

    Uri imageUri;

    TextRecognizer textRecognizer;
    TextRecognizer textRecognizerDevanagri;
    TextRecognizer textRecognizerEnglish;

    Boolean english=true;


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
            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = recognizedText.getText().toString();

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

                if (english){
                    english = false;
                    btnHindiText.setText("English");
                    btnGetImage.setText("Capture Image Hindi");
                    textRecognizer = textRecognizerDevanagri;


                }else {
                    english = true;
                    btnHindiText.setText("Hindi");
                    btnGetImage.setText("Capture Image English");
                    textRecognizer = textRecognizerEnglish;




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

                                        String recogniseText = text.getText();
                                        processText(text);

                                        recognizedText.setText(scannedText);


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


    private void processText(Text text1){

        scannedText = text1.getText();

//        for (Text.TextBlock block : text1.getTextBlocks()) {
//            String blockText = block.getText();
//            Point[] blockCornerPoints = block.getCornerPoints();
//            Rect blockFrame = block.getBoundingBox();
//            for (Text.Line line : block.getLines()) {
//                String lineText = line.getText();
//                Point[] lineCornerPoints = line.getCornerPoints();
//                Rect lineFrame = line.getBoundingBox();
//                for (Text.Element element : line.getElements()) {
//                    String elementText = element.getText();
//                    Point[] elementCornerPoints = element.getCornerPoints();
//                    Rect elementFrame = element.getBoundingBox();
//                    for (Text.Symbol symbol : element.getSymbols()) {
//                        String symbolText = symbol.getText();
//                        scannedText += symbolText;
//                        Point[] symbolCornerPoints = symbol.getCornerPoints();
//                        Rect symbolFrame = symbol.getBoundingBox();
//                    }
//                }
//            }
  //      }


    }

}
