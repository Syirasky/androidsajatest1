package com.example.user.cameraloo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback{
    Camera camera;
    SurfaceView surfaceView,transparentView;
    SurfaceHolder surfaceHolder,transparentHolder;
    Button capture,btnLogout;
    Camera.PictureCallback rawCallback;
    Camera.ShutterCallback shutterCallback;
    Camera.PictureCallback jpegCallback;
    SessionHandler sharedP;
    private float RectLeft, RectTop,RectRight,RectBottom ;
    int  deviceHeight,deviceWidth;
    private static  final int FOCUS_AREA_SIZE= 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedP = new SessionHandler(getApplicationContext());
        btnLogout = findViewById(R.id.btnLogout);
        capture = findViewById(R.id.button2);
        surfaceView = findViewById(R.id.surfaceView3);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
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



    }
    public void storageStuff(){
        String storage = Environment.DIRECTORY_PICTURES;
        final File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Camera");

        final File myDir = new File(root ,"OMR");
        if (!myDir.exists()){
            myDir.mkdirs();
        }
        final String nStorage = myDir.toString();
        jpegCallback = new Camera.PictureCallback() {
            @SuppressLint("WrongConstant")
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(String.format(myDir+"%d.jpg",System.currentTimeMillis()));

                    out.write(data);
                    Log.d("capture photo",myDir.toString());
                    Toast.makeText(getApplicationContext(),"Picture Saved",2000).show();
                }catch (FileNotFoundException fe){
                    Log.d("capture photo","error file not found");
                }catch (IOException e){
                }
                refreshCamera();
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(myDir);
                mediaScanIntent.setData(contentUri);
                sendBroadcast(mediaScanIntent);
            }
        };
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
        RectLeft = 100;
        RectTop = 200;
        RectRight = deviceWidth - 100;
        RectBottom =RectTop+ 550;
        Rect rec = new Rect((int)RectLeft,(int)RectTop,(int)RectRight,(int)RectBottom);
        canvas.drawRect(rec,paint);
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


}
