package com.example.user.cameraloo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends ArrayAdapter<Image>{
    private static class ViewHolder {
        ImageView imgIcon;
        TextView description;
    }

    public ImageAdapter(Context context, ArrayList<Image> images) {
        super(context, 0, images);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        // view lookup cache stored in tag

        ViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the
        // item view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image, parent, false);
            viewHolder.description = (TextView) convertView.findViewById(R.id.item_img_infor);
            viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.item_img_icon);
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
        viewHolder.description.setText(image.getStudentID());
        // set image icon
        final int THUMBSIZE = 96;
        //        viewHolder.imgIcon.setImageURI(Uri.fromFile(new File(image
        // .getPath())));
        // -----------------------------TAMBAH SINI-----------------------
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options(); //tmbah
        bitmapOptions.inJustDecodeBounds = true;//tmbah
        // find the best scaling factor for the desired dimensions
        int desiredWidth = 400;
        int desiredHeight = 300;
        float widthScale = (float)bitmapOptions.outWidth/desiredWidth;
        float heightScale = (float)bitmapOptions.outHeight/desiredHeight;
        float scale = Math.min(widthScale, heightScale);

        int sampleSize = 1;
        while (sampleSize < scale) {
            sampleSize *= 2;
        }
        bitmapOptions.inSampleSize = sampleSize; // this value must be a power of 2,
        // this is why you can not have an image scaled as you would like
        bitmapOptions.inJustDecodeBounds = false; // now we want to load the image
        //----------------------------------done tmbah--------------------------------------

        Uri uri = Uri.parse(image.getUri());
        String test = uri.getPath();
        Log.d("321","file path "+ test);
        viewHolder.imgIcon.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(test,bitmapOptions), THUMBSIZE, THUMBSIZE));

        // Return the completed view to render on screen
        return convertView;
    }



}
