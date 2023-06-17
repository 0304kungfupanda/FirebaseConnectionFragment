package com.example.firebaseconnectionfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText txt1;

    Button btn;

    Button btnCameraImage;

    Button btnPdf2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.BtnMultipleData);
        
        btnPdf2 = findViewById(R.id.btnPdf2);

        btnCameraImage = findViewById(R.id.BtnCameraImage);


        btnPdf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iNextPdf = new Intent(MainActivity.this, InsertPdf.class);
                startActivity(iNextPdf);
            }
        });


        btnCameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iNextCamera = new Intent(MainActivity.this, InsertCameraImage.class);
                startActivity(iNextCamera);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iNext = new Intent(MainActivity.this,InsertMultipleData.class);
                startActivity(iNext);
            }
        });
    }

    public void process(View view){

        txt1 = (EditText) findViewById(R.id.txt1);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference();

        root.setValue(txt1.getText().toString());
        txt1.setText("");
        Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();

    }


}