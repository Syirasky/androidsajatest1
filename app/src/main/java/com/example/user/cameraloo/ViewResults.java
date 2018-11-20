package com.example.user.cameraloo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewResults extends AppCompatActivity {
    private ArrayList<Image> imgdata;
    private SubjectExamAdapter examresultAdapter;
    private String userid;
    private ImageDB imghelper;
    private SessionHandler sharedP;
    private ListView listView;
    private Button btnNext;
    Image im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);

        listView = findViewById(R.id.main_result_view);
        imgdata = new ArrayList<Image>();
        imghelper = new ImageDB(this);

        im= new Image();
        sharedP = new SessionHandler(getApplicationContext());
        userid = sharedP.getUser();
        Log.d("viewresult userid",userid);
        imgdata = imghelper.getRelatedImages(userid);

        if (!imgdata.isEmpty()) {
            im = imgdata.get(0);
            examresultAdapter = new SubjectExamAdapter(this, imgdata);
            listView.setAdapter(examresultAdapter);
            Log.d("viewresult imgdata ui",imgdata.get(0).getUserID());
            Log.d("viewresult imgdata si",imgdata.get(0).getSubject_id());
        }else
        {
            Toast.makeText(getApplicationContext(),"No image found. Please take picture first.",Toast.LENGTH_SHORT);
        }



    }
}
