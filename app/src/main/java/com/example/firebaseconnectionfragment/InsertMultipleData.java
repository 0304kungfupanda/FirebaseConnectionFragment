package com.example.firebaseconnectionfragment;

import static android.Manifest.permission.CAMERA;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Layout;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
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

    EditText edtTxt1,edtTxt2,edtTxt3,edtTxt4,edtTxt5,edtTxt6,edtTxt7,edtTxt8,edtTxt9;
    ImageButton imgBtn1,imgBtn2,imgBtn3,imgBtn4,imgBtn5,imgBtn6,imgBtn7,imgBtn8,imgBtn9;

    TextView emptyTV9,emptyTV3,emptyTV4,emptyTV5,emptyTV6,emptyTV7;

    EditText editText ;

    LinearLayout llAnsExplain,llEnterQuest,llOptionA,llOptionB,llOptionC,llOptionD;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_multiple_data);

       // imgBtn1 = findViewById(R.id.imgBtn1);
        //imgBtn2 = findViewById(R.id.imgBtn2);
        imgBtn3 = findViewById(R.id.imgBtn3);
        imgBtn4 = findViewById(R.id.imgBtn4);
        imgBtn5 = findViewById(R.id.imgBtn5);
        imgBtn6 = findViewById(R.id.imgBtn6);
        imgBtn7 = findViewById(R.id.imgBtn7);
       // imgBtn8 = findViewById(R.id.imgBtn8);
        imgBtn9 = findViewById(R.id.imgBtn9);

        //

        edtTxt6 = findViewById(R.id.EdtTxt6);
        edtTxt4 = findViewById(R.id.EdtTxt4);
        edtTxt9 = findViewById(R.id.EdtTxt9);
        edtTxt5 = findViewById(R.id.EdtTxt5);
        edtTxt7 = findViewById(R.id.EdtTxt7);
        edtTxt3 = findViewById(R.id.EdtTxt3);
        //edtTxt8 = findViewById(R.id.EdtTxt8);

        emptyTV9 = findViewById(R.id.emptyTV9);
        emptyTV3 = findViewById(R.id.emptyTV3);
        emptyTV4 = findViewById(R.id.emptyTV4);
        emptyTV5 = findViewById(R.id.emptyTV5);
        emptyTV6 = findViewById(R.id.emptyTV6);
        emptyTV7 = findViewById(R.id.emptyTV7);

        llAnsExplain = findViewById(R.id.llAnsExplain);
        llAnsExplain.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        llEnterQuest = findViewById(R.id.llEnterQuest);
        llEnterQuest.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        llOptionA = findViewById(R.id.llOptionA);
        llOptionA.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        llOptionA = findViewById(R.id.llOptionA);
        llOptionA.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        llOptionB = findViewById(R.id.llOptionB);
        llOptionB.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        llOptionC = findViewById(R.id.llOptionC);
        llOptionC.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        llOptionD = findViewById(R.id.llOptionD);
        llOptionD.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);






//        imgBtn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent iBtn2 = new Intent(InsertMultipleData.this, TextScanner.class);
//                startActivityForResult(iBtn2);
//
//            }
//        });

        imgBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iBtn3 = new Intent(InsertMultipleData.this, TextScanner.class);
                startActivityForResult(iBtn3,103 );
            }
        });

        imgBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iBtn4 = new Intent(InsertMultipleData.this,TextScanner.class);
                startActivityForResult(iBtn4,104);
            }
        });

        imgBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iBtn5 = new Intent(InsertMultipleData.this,TextScanner.class);
                startActivityForResult(iBtn5,105);
            }
        });

        imgBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iBtn6 = new Intent(InsertMultipleData.this,TextScanner.class);
                startActivityForResult(iBtn6, 106);


            }
        });

        imgBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iBtn7 = new Intent(InsertMultipleData.this,TextScanner.class);
                startActivityForResult(iBtn7,107);
            }
        });

//        imgBtn8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent iBtn8 = new Intent(InsertMultipleData.this,TextScanner.class);
//                startActivityForResult(iBtn8,108);
//            }
//        });

        imgBtn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iBtn9 = new Intent(InsertMultipleData.this,TextScanner.class);
                startActivityForResult(iBtn9,109);
            }
        });


        //karan


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode==103 && resultCode== Activity.RESULT_OK && data!=null){
            String text = data.getStringExtra("text_data");
            edtTxt3.setText(text);
            emptyTV3.setBackgroundResource(R.color.pink);

        }
        if (requestCode==104 && resultCode== Activity.RESULT_OK && data!=null){
            String text = data.getStringExtra("text_data");
            edtTxt4.setText(text);
            emptyTV4.setBackgroundResource(R.color.pink);
        }
        if (requestCode==105 && resultCode== Activity.RESULT_OK && data!=null){

            String text = data.getStringExtra("text_data");
            edtTxt5.setText(text);
            emptyTV5.setBackgroundResource(R.color.pink);

        }
        if (requestCode==106 && resultCode== Activity.RESULT_OK && data!=null){

            String text = data.getStringExtra("text_data");
            edtTxt6.setText(text);
            emptyTV6.setBackgroundResource(R.color.pink);

        }
        if (requestCode==107 && resultCode== Activity.RESULT_OK && data!=null){

            String text = data.getStringExtra("text_data");
            edtTxt7.setText(text);
            emptyTV7.setBackgroundResource(R.color.pink);

        }
        if (requestCode==108 && resultCode== Activity.RESULT_OK && data!=null){

            String text = data.getStringExtra("text_data");
            edtTxt8.setText(text);


        }
        if (requestCode==109 && resultCode== Activity.RESULT_OK && data!=null){

            String text = data.getStringExtra("text_data");
            edtTxt9.setText(text);
            emptyTV9.setBackgroundResource(R.color.pink);


        }


        super.onActivityResult(requestCode, resultCode, data);


    }

    public void expand9(View view){

        int v = (edtTxt9.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(llAnsExplain, new AutoTransition());
        edtTxt9.setVisibility(v);


    }
    public void expand7(View view){

        int v = (edtTxt7.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(llOptionD, new AutoTransition());
        edtTxt7.setVisibility(v);

    }

    public void expand6(View view){

        int v = (edtTxt6.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(llOptionC, new AutoTransition());
        edtTxt6.setVisibility(v);

    }

    public void expand5(View view){

        int v = (edtTxt5.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(llOptionB, new AutoTransition());
        edtTxt5.setVisibility(v);

    }

    public void expand4(View view){

        int v = (edtTxt4.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(llOptionA, new AutoTransition());
        edtTxt4.setVisibility(v);

    }

    public void expand3(View view){

        int v = (edtTxt3.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(llEnterQuest, new AutoTransition());
        edtTxt3.setVisibility(v);

    }

    public void imprint(View view){

        edtTxt3 = findViewById(R.id.EdtTxt3);
        edtTxt4 = findViewById(R.id.EdtTxt4);
        edtTxt5 = findViewById(R.id.EdtTxt5);
        edtTxt6 = findViewById(R.id.EdtTxt6);
        edtTxt7 = findViewById(R.id.EdtTxt7);
        edtTxt9 = findViewById(R.id.EdtTxt9);


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
        edtTxt7.setText("");
        edtTxt9.setText("");

        Toast.makeText(getApplicationContext(), "Insert Multiple Data", Toast.LENGTH_SHORT).show();

    }
}