package com.example.user.cameraloo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ExamDetailPage extends AppCompatActivity {
    EditText txtSubjectID,txtExamCode;
    Button btnNext,btnCancel;
    String Examcode,SubjectId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail_page);

        txtExamCode = findViewById(R.id.txtExamCode);
        txtSubjectID = findViewById(R.id.txtSubjectID);
        btnNext = findViewById(R.id.btnNext12);
        btnCancel = findViewById(R.id.btnCancel2);
        btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Examcode = txtExamCode.getText().toString();
                SubjectId = txtSubjectID.getText().toString();
                Intent a  = new Intent(ExamDetailPage.this,MainActivity.class);
                a.putExtra("SubjectId",SubjectId);
                a.putExtra("Examcode",Examcode);
                startActivity(a);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });
    }
}
