package com.example.user.cameraloo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Edit extends AppCompatActivity {
    Button btnConfirm,btnCancel;
    ImageView imgview;
    EditText txtID;
    String uri;
    String newstud_id,stud_id,exam_code;
    ImageDB imdb;
    Image im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        btnConfirm = findViewById(R.id.btnConfirm2);
        btnCancel = findViewById(R.id.btnCancel2);
        txtID = findViewById(R.id.txtID);

        Intent a = getIntent();
        uri = a.getStringExtra("uri");

        ArrayList<Image> imlist = new ArrayList<>();
        im = new Image();

        imdb = new ImageDB(getApplicationContext());

        //query from db
        im = imdb.getImage(uri);
        stud_id = im.getStudentID();

        txtID.setText(stud_id, TextView.BufferType.EDITABLE);

        imgview.setImageURI(Uri.parse(im.getUri()));
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newstud_id = txtID.getText().toString();
                imdb.updateStudentID(newstud_id,uri);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
