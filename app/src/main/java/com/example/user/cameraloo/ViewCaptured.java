package com.example.user.cameraloo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

public class ViewCaptured extends AppCompatActivity {
    Button confirmBtn;
    Button cancelBtn;
    EditText editTextField;
    String studentID ;
    String struri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_captured);
        Intent a = getIntent();
        struri = a.getStringExtra("uri");
        ImageView imgView = findViewById(R.id.viewImg);
        confirmBtn = findViewById(R.id.btnConfirm);
        cancelBtn = findViewById(R.id.btnCancel);
        editTextField = findViewById(R.id.textStudentId);
        imgView.setImageURI(Uri.parse(struri));
        confirmBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent i = new Intent();
                studentID = editTextField.getText().toString();

                i.putExtra("studentID",studentID);
                i.putExtra("uri",struri);
                setResult(Activity.RESULT_OK,i);
                finish();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri uri = Uri.parse(struri);
                String path = uri.getPath();
                deletegambor(path);
                Intent i = new Intent();
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });


    }
    public void deletegambor(String path){
        File fdelete = new File(path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                Log.d("321","file Deleted :" +path);
                getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fdelete)));//refresh gallery

            } else {
                Log.d("321","file not Deleted :" + path);
            }
        }
    }




}
