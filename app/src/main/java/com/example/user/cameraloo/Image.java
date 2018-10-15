package com.example.user.cameraloo;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable{
    private String examcode;
    private String uri;
    private String studentID;

    public Image(){
        examcode="";
        uri = "";
        studentID = "";
    }
    protected Image(Parcel in) {
        setExamcode(in.readString());
        setUri(in.readString());
        setStudentID(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getExamcode());
        dest.writeString(getUri());
        dest.writeString(getStudentID());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getExamcode() {
        return examcode;
    }

    public void setExamcode(String examcode) {
        this.examcode = examcode;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
