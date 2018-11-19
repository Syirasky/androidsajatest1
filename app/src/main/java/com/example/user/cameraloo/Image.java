package com.example.user.cameraloo;
import com.google.gson.annotations.SerializedName;
import android.os.Parcel;
import android.os.Parcelable;



public class Image implements Parcelable{
    @SerializedName("examcode")
    private String examcode;
    @SerializedName("uri")
    private String uri;
    @SerializedName("studentID")
    private String studentID;
    @SerializedName("subject_id")
    private String subject_id;
    @SerializedName("answer")
    private String answer;
    @SerializedName("score")
    private String score;

    public Image(){
        examcode="";
        uri = "";
        studentID = "";
        setSubject_id("");
        answer = "";
        score = "";
    }
    public Image(String e,String st,String u,String si,String a,String sc){
        examcode = e;
        studentID = st;
        uri = u;
        setSubject_id(si);
        answer = a;
        score = sc;
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

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
