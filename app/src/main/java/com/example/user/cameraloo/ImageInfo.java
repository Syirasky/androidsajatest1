package com.example.user.cameraloo;


import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ImageInfo implements Parcelable {
    public abstract String student_id();
    public abstract String uri_img();

    public static ImageInfo create(String student_id, String uri_img) {
        return new AutoValue_ImageInfo(student_id,uri_img);
    }

}
