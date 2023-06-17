package com.example.firebaseconnectionfragment;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.REQUEST_COMPANION_PROFILE_APP_STREAMING;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    String TAG="TextScanner";
   Button btnCaptureImage, btnCopyText;
   TextView txtView_data;
    EditText etDemo;

    ImageView IVCapImg;

    String resultText="Hellos";
    String resultText1 ="karan ";
   Bitmap bitmap;

   private static final int REQUEST_CAMERA_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_scanner);

        btnCaptureImage = findViewById(R.id.btnCaptureImage);
        btnCopyText = findViewById(R.id.btnCopyText);
        txtView_data = findViewById(R.id.text_data);
        IVCapImg = findViewById(R.id.IVCapImg);




        if (ContextCompat.checkSelfPermission(TextScanner.this, CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(TextScanner.this, new String[]{
                    CAMERA
            }, REQUEST_CAMERA_CODE );

        }

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(TextScanner.this);
            }

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (requestCode== RESULT_OK){
                Uri resultUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),resultUri);
                    getTextFromImage( bitmap);

                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }


    }

    private void getTextFromImage(Bitmap bitmap){


        resultText1 +="marak";
        //TextRecognizer recognizer = TextRecognition.getClient(new DevanagariTextRecognizerOptions.Builder().build());
        //TextRecognition textRecognition = (TextRecognition) TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        // When using Latin script library
        TextRecognizer recognizer =TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        IVCapImg.setImageBitmap(bitmap);
//        Drawable md=getResources().getDrawable(R.drawable.mytextimage);
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.mytextimage);
        InputImage img=InputImage.fromBitmap(bitmap,0);
        txtView_data.setText(toString());

        Task<Text> result =recognizer.process(img)
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text visionText) {
                        // Task completed successfully
                        // ...
                        //Log.e(TAG,"onSuccess !!!");
                        Toast.makeText(TextScanner.this, "FailureListener", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...
                                //Log.e(TAG,e.getMessage());
                                Toast.makeText(TextScanner.this, "FailureListener", Toast.LENGTH_SHORT).show();
                            }
                        });


         resultText = result.getResult().toString();
        Boolean myBool =true;
        for (Text.TextBlock block : result.getResult().getTextBlocks()) {
            String blockText = block.getText();
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (Text.Line line : block.getLines()) {
                String lineText = line.getText();
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (Text.Element element : line.getElements()) {
                    String elementText = element.getText();
                    if(myBool){
                        Toast.makeText(this, elementText, Toast.LENGTH_SHORT).show();

                       // txtView_data.setText(elementText);
                        myBool=false;
                    }

                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();
                    for (Text.Symbol symbol : element.getSymbols()) {
                        String symbolText = symbol.getText();
                        resultText1 += symbolText;
                        Point[] symbolCornerPoints = symbol.getCornerPoints();
                        Rect symbolFrame = symbol.getBoundingBox();
                    }
                }
            }
        }
// When using Devanagari script library
      //  TextRecognizer recognizer1 =
            //    TextRecognition.getClient(new DevanagariTextRecognizerOptions.Builder().build());
    }
}