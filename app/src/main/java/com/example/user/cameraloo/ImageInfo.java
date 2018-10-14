package com.example.user.cameraloo;

public class ImageInfo {
    private String student_id;
    private String uri_img;

    public ImageInfo(String id,String uri){
        student_id = id;
        uri_img = uri;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getUri_img() {
        return uri_img;
    }

    public void setUri_img(String uri_img) {
        this.uri_img = uri_img;
    }
}
