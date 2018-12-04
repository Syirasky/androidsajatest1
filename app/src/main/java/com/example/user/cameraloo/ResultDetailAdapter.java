package com.example.user.cameraloo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultDetailAdapter extends ArrayAdapter<Image>{
    private ArrayList<Image> imlist;
    private Context cnt;
    private static class ViewHolder {

        TextView textStudentID;
        TextView textScore;


    }

    public ResultDetailAdapter(Context context, ArrayList<Image> images) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.score_detail, parent, false);

            viewHolder.textStudentID = convertView.findViewById(R.id.txtStudentID);
            viewHolder.textScore = convertView.findViewById(R.id.txtScore);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Get the data item for this position
        Image image = getItem(position);
        // set description text
        if (image.getUri()!=null) {
        //    Log.d("321", "ok kat imageadapter");
            viewHolder.textScore.setText(image.getScore());
            viewHolder.textStudentID.setText(image.getStudentID());

        }else {
            //Log.d("321", "x ok kat imgadapter");
            }


        // Return the completed view to render on screen
        return convertView;
    }


    public void refreshView(){
        notifyDataSetChanged();
    }




}
