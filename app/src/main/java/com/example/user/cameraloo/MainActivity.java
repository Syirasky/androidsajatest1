package com.example.user.cameraloo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback{
    Camera camera;
    SurfaceView surfaceView,transparentView;
    SurfaceHolder surfaceHolder,transparentHolder;
    Button capture,btnLogout,btnViewImg;
    Camera.PictureCallback rawCallback;
    Camera.ShutterCallback shutterCallback;
    Camera.PictureCallback jpegCallback;
    SessionHandler sharedP;
    private float RectLeft, RectTop,RectRight,RectBottom ;
    int  deviceHeight,deviceWidth;
    int activity_viewimg_code = 2;
    String studentID,uri;
    private static  final int FOCUS_AREA_SIZE= 300;
    ImageInfo imginfo ;
    Image img;
    ArrayList<Image> a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedP = new SessionHandler(getApplicationContext());
        btnLogout = findViewById(R.id.btnLogout);
        capture = findViewById(R.id.button2);
        btnViewImg = findViewById(R.id.btnViewImg);
        surfaceView = findViewById(R.id.surfaceView3);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        img = new Image();
        a = new ArrayList<>();
        //surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.setSecure(true);

        transparentView = (SurfaceView) findViewById(R.id.surfaceView2);
        transparentHolder = transparentView.getHolder();
        transparentHolder.addCallback(this);
        transparentHolder.setFormat(PixelFormat.TRANSLUCENT);
        transparentView.setZOrderMediaOverlay(true);

        deviceWidth=getScreenWidth();
        deviceHeight=getScreenHeight();

        storageStuff();

        btnLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                sharedP.logoutUser();
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                finish();
            }
        });
        btnViewImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){

                Intent i = new Intent(MainActivity.this, ViewImage.class);
                Bundle datass = new Bundle();

                datass.putParcelableArrayList("obj",getArrayTu());
                i.putExtra("objass",datass);
                startActivity(i);

                if (!a.isEmpty()) {
                    Log.d("123", img.getStudentID());
                }
                else {
                    Log.d("1234", "failed");

                }
            }
        });

        surfaceView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    focusOnTouch(event);
                }
                return true;
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==activity_viewimg_code)
        {
            if(resultCode==RESULT_OK) {
                studentID = data.getStringExtra("studentID");
                uri = data.getStringExtra("uri");
                Log.d("studentID", studentID);
                img.setUri(uri);
                img.setStudentID(studentID);
                img.setExamcode("test");
                addIntoArray(img);
            }
        }
    }


    public void addIntoArray(Image im){

        a.add(im);
    }
    public ArrayList<Image> getArrayTu(){
        return a;
    }

    public void storageStuff(){

        jpegCallback = new Camera.PictureCallback() {
            @SuppressLint("WrongConstant")
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {



                File a = saveImage(data);
                ///storage/emulated/0/Pictures/OMR/OMR_1539395350101.jpg cth picfile


                Uri uripic = Uri.fromFile(a);
                Log.d("uriPic",uripic.toString());
                viewImage(uripic);

                refreshCamera();
                refreshGallery(a);
            }
        };
    }
    public File saveImage(byte[] data){
        String timestamp = Long.toString(System.currentTimeMillis());
        String imagename = "OMR_"+timestamp+".jpg";

        File file_image = getDir();
        if (!file_image.exists()&& !file_image.mkdirs()){
            Toast.makeText(getApplicationContext(),"Dir error",Toast.LENGTH_SHORT).show();
        }


        String file_name = file_image.getAbsolutePath()+"/"+imagename;
        File picfile = new File(file_name);
        try{
            FileOutputStream out = null;
            out = new FileOutputStream(picfile);
            out.write(data);
            Toast.makeText(getApplicationContext(),"Picture Saved",Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException fe){
            Log.d("capture photo","error file not found");
        }catch (IOException e){
        }
        return picfile;
    }

    public void viewImage(Uri uri){
        Intent viewcaptured = new Intent(MainActivity.this,ViewCaptured.class);
        viewcaptured.putExtra("uri",uri.toString());
        startActivityForResult(viewcaptured,activity_viewimg_code);

    }
    public File getDir(){
        File dest = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(dest,"OMR");
    }
    public void refreshGallery(File file){
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }
    public static int getScreenWidth() {

        return Resources.getSystem().getDisplayMetrics().widthPixels;

    }
    public static int getScreenHeight() {

        return Resources.getSystem().getDisplayMetrics().heightPixels;

    }

    public void Draw()
    {
        Canvas canvas = transparentHolder.lockCanvas();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);
        Bitmap guiderect = BitmapFactory.decodeResource(getResources(),R.drawable.guidepls3);
        RectTop = 20;
        RectLeft = 90;
        RectRight = deviceWidth - 90;
        Rect rec = new Rect();
        int i = 0;
        /**while(i<15){

            RectBottom =RectTop+ 50;

            rec = new Rect((int)RectLeft,(int)RectTop,(int)RectRight,(int)RectBottom);
            canvas.drawRect(rec,paint);
            Log.d("Draw",Integer.toString(i));
            RectTop = RectBottom; //now bottom is top for another box
            i++;
        }**/
        Log.d("sizescw","hehe");
        Log.d("hehe",Integer.toString(deviceWidth));
        Log.d("hehe",Integer.toString(deviceHeight));

        canvas.drawBitmap(guiderect,100,50,null);
        transparentHolder.unlockCanvasAndPost(canvas);
    }

    public void captureImage(View v){
        camera.takePicture(shutterCallback,rawCallback,jpegCallback);
    }

    public void refreshCamera(){
        if(surfaceHolder.getSurface()==null){
            return;
        }
        try {
            camera.stopPreview();
        }catch (Exception e) {

        }
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        }catch (Exception e){

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        try{
            synchronized (holder){
                Draw();
            }

            camera = Camera.open();

        }catch (RuntimeException re){

        }

        Camera.Parameters param;
        param = camera.getParameters();
        param.setPreviewSize(352,288);
        param.setRotation(90);
        param.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setDisplayOrientation(90);
        camera.setParameters(param);

        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        }catch (Exception e){

        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
       if (camera != null) {
           camera.stopPreview();
           camera.release();
           camera = null;
       }
       camera = null;
    }
    private void focusOnTouch(MotionEvent event) {
        if (camera != null ) {

            Camera.Parameters parameters = camera.getParameters();
            if (parameters.getMaxNumMeteringAreas() > 0){
                Log.d("focusOnTouch","fancy !");
                Rect rect = calculateFocusArea(event.getX(), event.getY());

                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
                meteringAreas.add(new Camera.Area(rect, 800));
                parameters.setFocusAreas(meteringAreas);

                camera.setParameters(parameters);
                camera.autoFocus(mAutoFocusTakePictureCallback);
            }else {
                camera.autoFocus(mAutoFocusTakePictureCallback);
            }
        }
    }
    private Rect calculateFocusArea(float x, float y) {
        int left = clamp(Float.valueOf((x / surfaceView.getWidth()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);
        int top = clamp(Float.valueOf((y / surfaceView.getHeight()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);

        return new Rect(left, top, left + FOCUS_AREA_SIZE, top + FOCUS_AREA_SIZE);
    }
    private int clamp(int touchCoordinateInCameraReper, int focusAreaSize) {
        int result;
        if (Math.abs(touchCoordinateInCameraReper)+focusAreaSize/2>1000){
            if (touchCoordinateInCameraReper>0){
                result = 1000 - focusAreaSize/2;
            } else {
                result = -1000 + focusAreaSize/2;
            }
        } else{
            result = touchCoordinateInCameraReper - focusAreaSize/2;
        }
        return result;
    }
    private Camera.AutoFocusCallback mAutoFocusTakePictureCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {
                // do something...
                Log.d("tap_to_focus","success!");
            } else {
                // do something...
                Log.d("tap_to_focus","fail!");
            }
        }
    };



}
