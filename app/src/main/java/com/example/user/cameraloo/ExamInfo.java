package com.example.user.cameraloo;

public class ExamInfo {
    private String examcode;
    private String subjectid;
    private String answer;

    public ExamInfo(){
        examcode=null;
        subjectid=null;
        answer=null;

    }
    public ExamInfo(String e,String s,String a){
        examcode = e;
        subjectid = s;
        answer = a;
    }


    public String getExamcode() {
        return examcode;
    }

    public void setExamcode(String examcode) {
        this.examcode = examcode;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
