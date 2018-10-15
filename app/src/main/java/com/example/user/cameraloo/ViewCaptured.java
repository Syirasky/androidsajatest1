package com.example.user.cameraloo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ViewCaptured extends AppCompatActivity {
    Button confirmBtn;
    Button cancelBtn;
    EditText editTextField;
    String studentID ;
    String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_captured);
        Intent a = getIntent();
        uri = a.getStringExtra("uri");
        ImageView imgView = findViewById(R.id.viewImg);
        confirmBtn = findViewById(R.id.btnConfirm);
        cancelBtn = findViewById(R.id.btnCancel);
        editTextField = findViewById(R.id.textStudentId);
        imgView.setImageURI(Uri.parse(uri));
        confirmBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent i = new Intent();
                studentID = editTextField.getText().toString();

                i.putExtra("studentID",studentID);
                i.putExtra("uri",uri);
                setResult(Activity.RESULT_OK,i);
                finish();
            }
        });


    }
    public void confirm(){//save to list then return result to list in main then return to main activity

    }
    public void reject(){//return to main

    }



}
