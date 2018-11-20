package com.example.user.cameraloo;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class SubjectExamAdapter extends ArrayAdapter<Image>{
    private ArrayList<Image> imlist;
    private Context cnt;
    private static class ViewHolder {

        TextView examcode;
        TextView subjectid;
        Button btnviewresult;
    }

    public SubjectExamAdapter(Context context, ArrayList<Image> images) {
        super(context, 0, images);
        cnt = context;
        imlist= images;
    }

    @Override public View getView(final int position, View convertView, ViewGroup parent) {
        // view lookup cache stored in tag

        final ViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the
        // item view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_subject, parent, false);

            viewHolder.examcode = convertView.findViewById(R.id.txtECode);
            viewHolder.subjectid = convertView.findViewById(R.id.txtSubID);
            viewHolder.btnviewresult = convertView.findViewById(R.id.btnResult);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Get the data item for this position
        Image image = getItem(position);
        // set description text
        if (image.getUri()!=null) {
            Log.d("321", "ok kat imageadapter");
            }else {
            Log.d("321", "x ok kat imgadapter");
            }
        viewHolder.examcode.setText(image.getExamcode());
        viewHolder.subjectid.setText(image.getSubject_id());


        // Return the completed view to render on screen
        viewHolder.btnviewresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent viewResults = new Intent(getContext(),resultdetail.class);
                Image im = getItem(position);
                viewResults.putExtra("subjectid",im.getSubject_id());
                viewResults.putExtra("userid",im.getUserID());
                viewResults.putExtra("examcode",im.getExamcode());
                getContext().startActivity(viewResults);


            }
        });

        return convertView;
    }


    public void refreshView(){
        notifyDataSetChanged();
    }




}
