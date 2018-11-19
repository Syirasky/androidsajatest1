package com.example.user.cameraloo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainForAll extends AppCompatActivity {
    Button btnGradePage;
    Button btnResultPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_all);
        btnGradePage = findViewById(R.id.btnGradeNow);
        btnResultPage = findViewById(R.id.btnViewResult);

        btnGradePage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent a = new Intent(MainForAll.this,EnterExamInfo.class);
                startActivity(a);

            }
        });
        btnResultPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent a = new Intent(MainForAll.this,ViewResults.class);
                startActivity(a);
            }
        });
    }
}
