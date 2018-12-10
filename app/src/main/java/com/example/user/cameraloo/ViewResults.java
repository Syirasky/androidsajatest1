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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewResults extends AppCompatActivity {
    private ArrayList<Image> imgdata;
    private SubjectExamAdapter examresultAdapter;
    private String userid;
    private ImageDB imghelper;
    private SessionHandler sharedP;
    private ListView listView;
    private Button btnFetch;
    private ExamInfo exm;
    Image im;
    ArrayList<Image> imglist;
    ArrayList<ExamInfo> exmlist ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);

        listView = findViewById(R.id.main_result_view);
        imgdata = new ArrayList<Image>();
        exmlist = new ArrayList<ExamInfo>();
        imghelper = new ImageDB(this);
        btnFetch = findViewById(R.id.btnFetchResult);

        im= new Image();
        exm = new ExamInfo();

        sharedP = new SessionHandler(getApplicationContext());
        userid = sharedP.getUser();
        Log.d("viewresult userid",userid);

       //imgdata = imghelper.getRelatedImages(userid);
        exmlist = imghelper.getRelatedExamInfo(userid);

        if (!exmlist.isEmpty()) {
            exm = exmlist.get(0);
            examresultAdapter = new SubjectExamAdapter(this, exmlist);
            listView.setAdapter(examresultAdapter);
            Log.d("viewresult exm subject",exm.getSubjectid2());
            Log.d("viewresult exm exam",exm.getExamcode2());
        }else
        {
            Toast.makeText(getApplicationContext(),"No image found. Please take picture first.",Toast.LENGTH_SHORT);
        }

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchData();
            }
        });



    }

    private void FetchData()
    {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl("http://192.168.43.244/TestLoginSaja2/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiService service2 = retrofit2.create(ApiService.class);

        Call<ResultFromDBHandler> call = service2.fetchDbData();

        call.enqueue(new Callback<ResultFromDBHandler>() {
            @Override
            public void onResponse(Call<ResultFromDBHandler> call, Response<ResultFromDBHandler> response) {
                imglist = response.body().getDataResults();
                if(response.body()!=null){
                    Log.d("results","response.body x null");

                }
                if (imglist!=null) {
                    for (int i = 0;i<imglist.size();i++){
                        im = imglist.get(i);
                        Log.d("results","Score "+im.getScore()+"Student id "+im.getStudentID()+" Examcode "+im.getExamcode() + "Subject ID "+ im.getSubject_id());

                        Toast.makeText(getApplicationContext(),"Result Fetched Successfully.",Toast.LENGTH_SHORT);
                        imghelper.updateScore(im.getStudentID(),im.getSubject_id(),im.getExamcode(),im.getScore());
                    }
                }else{
                    Log.d("results","No Results :(");
                }
            }

            @Override
            public void onFailure(Call<ResultFromDBHandler> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error Getting Results.",Toast.LENGTH_SHORT);

            }
        });



    }
}
