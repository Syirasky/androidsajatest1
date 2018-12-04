package com.example.user.cameraloo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ExamInfo implements Parcelable{
    @SerializedName("examcode2")
    private String examcode2;
    @SerializedName("subjectid2")
    private String subjectid2;
    @SerializedName("answer2")
    private String answer2;
    @SerializedName("lecturerid")
    private String lecturerid;

    public ExamInfo(){
        examcode2="";
        subjectid2="";
        lecturerid="";
        answer2="";
    }

    public ExamInfo(String e,String s, String le,String a){
        examcode2=e;
        subjectid2=s;
        lecturerid=le;
        answer2=a;
    }

    protected ExamInfo(Parcel in) {
        examcode2 = in.readString();
        subjectid2 = in.readString();
        answer2 = in.readString();
        lecturerid = in.readString();
    }

    public static final Creator<ExamInfo> CREATOR = new Creator<ExamInfo>() {
        @Override
        public ExamInfo createFromParcel(Parcel in) {
            return new ExamInfo(in);
        }

        @Override
        public ExamInfo[] newArray(int size) {
            return new ExamInfo[size];
        }
    };

    public String getExamcode2() {
        return examcode2;
    }

    public void setExamcode2(String examcode2) {
        this.examcode2 = examcode2;
    }

    public String getSubjectid2() {
        return subjectid2;
    }

    public void setSubjectid2(String subjectid2) {
        this.subjectid2 = subjectid2;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getLecturerid() {
        return lecturerid;
    }

    public void setLecturerid(String lecturerid) {
        this.lecturerid = lecturerid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(examcode2);
        dest.writeString(subjectid2);
        dest.writeString(answer2);
        dest.writeString(lecturerid);
    }
}
