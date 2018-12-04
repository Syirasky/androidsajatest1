package com.example.user.cameraloo;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Akshay Raj on 05/02/18.
 * akshay@snowcorp.org
 * www.snowcorp.org
 */

public interface ApiService {

    @Headers({"Accept: multipart/form-data",})
    @Multipart
    @POST("upload.php")
    Call<ResponseBody> uploadMultiple(
            @Part("description") RequestBody description,
            @Part("size") RequestBody size,
            @Part("listID") RequestBody listID,
            @Part List<MultipartBody.Part> files);

    @POST("receiveInfoImage.php")
    Call<JsonObject> getUserValidity(
            @Body JsonObject imgdetails);

    @POST("interfaceToDb.php")
    Call<ResultFromDBHandler> fetchDbData();
}
