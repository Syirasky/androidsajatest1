package com.example.user.cameraloo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnterExamInfo extends AppCompatActivity {
    Button btnNext;
    Button btnBack;
    EditText txtExamCode;
    EditText txtSubjectID;
    EditText txtAnswer;
    ImageDB imghelper;
    private SessionHandler sharedP;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_exam_info);
        txtAnswer = findViewById(R.id.txtInAnswer);
        txtExamCode = findViewById(R.id.txtInExamCode);
        txtSubjectID = findViewById(R.id.txtInSubjectID);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);
        sharedP = new SessionHandler(getApplicationContext());
        userid = sharedP.getUser();
        btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent a = new Intent(EnterExamInfo.this,MainActivity.class);
                String examcode = txtExamCode.getText().toString();
                String subjectid = txtSubjectID.getText().toString();
                String answer = txtAnswer.getText().toString();

                a.putExtra("examcode",examcode);
                a.putExtra("subjectID",subjectid);
                a.putExtra("answer",answer);
                a.putExtra("lecturerid",userid);

                startActivity(a);
            }
        });




    }
}
