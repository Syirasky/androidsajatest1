package com.example.user.cameraloo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultFromDBHandler {
    @SerializedName("data_result") //here, the value should be the same to json
    private ArrayList<Image> dataResults;

    public ArrayList<Image> getDataResults() {
        return dataResults;
    }

    public void setDataResults(ArrayList<Image> dataResults) {
        this.dataResults = dataResults;
    }
}
