package com.example.user.cameraloo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainForAll extends AppCompatActivity {
    Button btnGradePage;
    Button btnResultPage;
    ImageDB dbhelper;
    private SessionHandler sharedP;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_all);

        btnGradePage = findViewById(R.id.btnGradeNow);
        btnResultPage = findViewById(R.id.btnViewResult);
        dbhelper = new ImageDB(this);
        sharedP = new SessionHandler(getApplicationContext());

        if(!sharedP.isLoggedIn()){
            Intent i = new Intent(MainForAll.this, Login.class);
            startActivity(i);
            finish();
        }

        userid = sharedP.getUser();
        User user = new User(userid,"dummy@gmail.com");
        dbhelper.addUser(user);

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
