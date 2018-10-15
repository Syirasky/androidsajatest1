package com.example.user.cameraloo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewImage extends AppCompatActivity {
    // Array of strings for ListView Title
    String[] listviewTitle = new String[]{
            "ListView Title 1", "ListView Title 2", "ListView Title 3", "ListView Title 4",
            "ListView Title 5", "ListView Title 6", "ListView Title 7", "ListView Title 8",
    };


    int[] listviewImage = new int[]{
            R.drawable.profile_pc, R.drawable.profile_pc, R.drawable.profile_pc, R.drawable.profile_pc,
            R.drawable.profile_pc, R.drawable.profile_pc, R.drawable.profile_pc, R.drawable.profile_pc,
    };

    String[] listviewShortDescription = new String[]{
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
    };
    String examcode,uri,studentID;
    ArrayList<Image> imgobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        imgobj = new ArrayList<>();
        Intent _intent =  getIntent();
        Bundle datass = _intent.getBundleExtra("objass");


        if (!datass.isEmpty()) {
            imgobj = datass.getParcelableArrayList("obj");
            if (!imgobj.isEmpty()) {
                Image im = new Image();
                Image im2 = new Image();
                if (imgobj.size()>0) {
                    im = imgobj.get(0);
                    im2 = imgobj.get(1);
                }
                Log.d("321", im.getUri()+im2.getUri());
            }else {
                Log.d("321", "failed hu");
            }
        }else
        {
            Log.d("3211","damnnedd");
        }
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 8; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_discription", listviewShortDescription[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};
         if (!aList.isEmpty()){
            SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.custom_listlayout, from, to);
            ListView androidListView = findViewById(R.id.list_view);
            androidListView.setAdapter(simpleAdapter);
         }
    }
}