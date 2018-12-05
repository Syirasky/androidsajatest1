package com.example.user.cameraloo;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.user.cameraloo.JSONParser.json;

public class ShowListView extends AppCompatActivity{
    private ArrayList<Image> images,resultss;
    private ArrayList<Uri>  urionly;
    private ImageAdapter imageAdapter;
    private ListView listView;
    private Uri mCapturedImageURI;
    private static final int RESULT_LOAD_IMAGE = 1;
    private String subjectID;
    private String examcode;
    private ImageDB imghelper;
    private ProgressBar mProgressBar;
    private Button btnUpload;
    private static final String TAG = ShowListView.class.getSimpleName();
    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 124;
    private View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_view);

        File chkfile = null;
        int i = 0;
        mProgressBar = findViewById(R.id.progressBar);
        parentView = findViewById(R.id.parent_layout);
        btnUpload = findViewById(R.id.btnUpload);

        images = new ArrayList<Image>();
        resultss = new ArrayList<Image>();

        Intent _intent =  getIntent();
        subjectID = _intent.getStringExtra("subjectID");
        examcode = _intent.getStringExtra("examcode");
        imghelper = new ImageDB(this);

        Image im = new Image();
        resultss = imghelper.getAllRelatedImages(subjectID,examcode);

        for ( i = 0;i<resultss.size();i++){
            im = resultss.get(i);
            chkfile = new File(URI.create(im.getUri()).getPath());
            if (chkfile.exists()) {
                //Do something
                images.add(im);
            }
        }


        if (!images.isEmpty()) {
            imageAdapter = new ImageAdapter(this, images);
            listView = (ListView) findViewById(R.id.main_list_view);
            listView.setAdapter(imageAdapter);
        }else
        {
            Toast.makeText(getApplicationContext(),"No image found. Please take picture first.",Toast.LENGTH_SHORT);
        }

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImagesToServer();

            }
        });



        Log.d("321","size arraylist resultss " + images.size());
    }



    private void sendImageInfo(){ // json for student_score and examinfo is here
        Image im,im2 = new Image();
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("http://192.168.43.244/TestLoginSaja2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service2 = retrofit2.create(ApiService.class);

        ArrayList<Image> imglist = imghelper.getAllRelatedImages(subjectID,examcode);

            if(imglist.size()>0) {
                JsonObject testjsn;
                JsonObject testjsnparent = new JsonObject();
                JsonArray testarr = new JsonArray();


                int i = 0;
                while (i < imglist.size()) {
                    im = imglist.get(i);

                    testjsn = new JsonObject();
                    testjsn.addProperty("examcode", im.getExamcode());
                    testjsn.addProperty("studentID", im.getStudentID());
                    testjsn.addProperty("uri", im.getUri());
                    testjsn.addProperty("subjectID", im.getSubject_id());
                    testarr.add(testjsn);

                    i++;
                }
                im = imglist.get(0);
                testjsnparent.addProperty("answer", im.getAnswer());
                testjsnparent.add("details", testarr);
                Call<JsonObject> call = service2.getUserValidity(testjsnparent);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {

                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {

                    }
                });


                Log.d("json", testjsnparent.toString());

                /*

                {
          {
          "answer": "ACBADCCADBDDDADDCCAD",
          "details": [
            {
              "examcode": "Sept18",
              "studentID": "12012",
              "uri": "file:///storage/emulated/0/Pictures/OMR/OMR_1543517306516.jpg",
              "subjectID": "IOT"
            },
            {
              "examcode": "Sept18",
              "studentID": "8338",
              "uri": "file:///storage/emulated/0/Pictures/OMR/OMR_1543517333596.jpg",
              "subjectID": "IOT"
            }
          ]
        }*/
            }
            else{
                Toast.makeText(ShowListView.this,"No Images",Toast.LENGTH_SHORT).show();
            }
    }

    private void uploadImagesToServer() {
        sendImageInfo();
        if (true) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.43.244/TestLoginSaja2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            showProgress();

            // create list of file parts (photo, video, ...)
            List<MultipartBody.Part> parts = new ArrayList<>();

            // create upload service client
            ApiService service = retrofit.create(ApiService.class);

            if (images != null) {
                // create part for file (photo, video, ...)
                for (int i = 0; i < images.size(); i++) {
                    Image im = new Image();
                    im = images.get(i);
                    Log.d("checkimages",images.get(i).toString());
                    parts.add(prepareFilePart("image"+i, Uri.parse(im.getUri())));
                }
            }
            Log.d("checkimages",String.valueOf(parts.size()));

            // create a map of data to pass along
            RequestBody description = createPartFromString("www.androidlearning.com");
            RequestBody size = createPartFromString(""+parts.size());

            List<String> all_id = new ArrayList<>();
            all_id = imghelper.getAllStudentID();
            StringBuilder builder = new StringBuilder();

            for (String string : all_id) {
                if (builder.length() > 0) {
                    builder.append(" ");
                }
                builder.append(string);
            }

            String listID = builder.toString();

            RequestBody listid = createPartFromString(listID);

            if(all_id.size()>0){
                // finally, execute the request
                Call<ResponseBody> call = service.uploadMultiple(description, size,listid, parts);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        hideProgress();
                        if(response.isSuccessful()) {
                            Toast.makeText(ShowListView.this,
                                    "Images successfully uploaded!", Toast.LENGTH_SHORT).show();
                            Intent a = new Intent(ShowListView.this,MainForAll.class);
                            startActivity(a);
                        } else {
                            Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        hideProgress();
                        Snackbar.make(parentView, t.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });

                }
                else {
                    hideProgress();
                    Toast.makeText(ShowListView.this,
                            R.string.string_internet_connection_not_available, Toast.LENGTH_SHORT).show();
                }
            }else {
            Toast.makeText(ShowListView.this,"Please take photos first!",Toast.LENGTH_SHORT).show();
            }

    }


    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = new File(fileUri.getPath());

            // create RequestBody instance from file
            Log.d("checkimages",getMimeType(fileUri));
            RequestBody requestFile = RequestBody.create(MediaType.parse(getMimeType(fileUri)), file);

            // MultipartBody.Part is used to send also the actual file name
            return MultipartBody.Part.createFormData(partName, fileUri.toString(), requestFile);


    }
    public String getMimeType(Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }
    /**
     *  Runtime Permission
     */


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    //showChooser();
                } else {
                    // Permission Denied
                    Toast.makeText(ShowListView.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        btnUpload.setEnabled(false);
    }
    private void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        btnUpload.setEnabled(true);
    }


}
