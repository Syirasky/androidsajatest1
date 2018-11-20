package com.example.user.cameraloo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class resultdetail extends AppCompatActivity {
    private ListView listView ;
    private ImageDB imghelper;
    String subjectid,examcode,userid;
    ArrayList<Image> imgdata;
    Image im;
    ResultDetailAdapter resultdetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultdetail);

        listView = findViewById(R.id.main_scoredetail_view);
        Intent a =  getIntent();
        subjectid = a.getStringExtra("subjectid");
        examcode = a.getStringExtra("examcode");
        userid = a.getStringExtra("userid");
        imghelper = new ImageDB(getApplicationContext());
        imgdata = imghelper.getScoreUIDSubjectExam(userid,subjectid,examcode);

        if (!imgdata.isEmpty()) {
            im = imgdata.get(0);
            resultdetailAdapter = new ResultDetailAdapter(this, imgdata);
            listView.setAdapter(resultdetailAdapter);
        //  Log.d("viewresult imgdata ui",imgdata.get(0).getUserID());
        //  Log.d("viewresult imgdata si",imgdata.get(0).getSubject_id());
        }else
        {
            Log.d("resultdetail","xde data");
        }


    }
}
