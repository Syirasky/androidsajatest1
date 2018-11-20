package com.example.user.cameraloo;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userID")
    private String userID;
    @SerializedName("email")
    private String email;
    public User(){
        userID = "";
        email = "";
    }

    public User(String u,String e){
        userID=u;
        email=e;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
