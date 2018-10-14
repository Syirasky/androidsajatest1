package com.example.user.cameraloo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class ViewCaptured extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_captured);
        Intent a = getIntent();
        String uri = a.getStringExtra("uri");
        ImageView imgView = findViewById(R.id.viewImg);

        imgView.setImageURI(Uri.parse(uri));


    }
    public void confirm(){//save to list then return result to list in main then return to main activity

    }
    public void reject(){//return to main

    }



}
