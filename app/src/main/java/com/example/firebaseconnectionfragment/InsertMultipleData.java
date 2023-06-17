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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognizer;

public class InsertMultipleData extends AppCompatActivity {

    EditText edtTxt1,edtTxt2,edtTxt3,edtTxt4,edtTxt5,edtTxt6,edtTxt7,edtTxt8;
    ImageButton imgBtn1,imgBtn2,imgBtn3,imgBtn4,imgBtn5,imgBtn6,imgBtn7,imgBtn8;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_multiple_data);

        imgBtn3 = findViewById(R.id.imgBtn3);
        imgBtn4 = findViewById(R.id.imgBtn4);
        imgBtn5 = findViewById(R.id.imgBtn5);
        imgBtn6 = findViewById(R.id.imgBtn6);

        imgBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iNextScanner = new Intent(getApplicationContext(), TextScanner.class);
                startActivity(iNextScanner);
            }
        });

        imgBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iBtn6 = new Intent(InsertMultipleData.this,ScannerActivity.class);
                startActivityForResult(iBtn6, 123);





            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode==123 && resultCode== Activity.RESULT_OK && data!=null){

            String text = data.getStringExtra("text_data");
            edtTxt6.setText(text);


        }
        super.onActivityResult(requestCode, resultCode, data);


    }

    public void imprint(View view){

        edtTxt3 = findViewById(R.id.EdtTxt3);
        edtTxt4 = findViewById(R.id.EdtTxt4);
        edtTxt5 = findViewById(R.id.EdtTxt5);
        edtTxt6 = findViewById(R.id.EdtTxt6);


        String roll = edtTxt3.getText().toString().trim();
        String name = edtTxt4.getText().toString().trim();
        String course = edtTxt5.getText().toString().trim();
        String duration = edtTxt6.getText().toString().trim();

        DataHolder obj = new DataHolder(name,course,duration);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference node = db.getReference("students");

        node.child(roll).setValue(obj);

        edtTxt3.setText("");
        edtTxt4.setText("");
        edtTxt5.setText("");
        edtTxt6.setText("");

        Toast.makeText(getApplicationContext(), "Insert Multiple Data", Toast.LENGTH_SHORT).show();

    }
}