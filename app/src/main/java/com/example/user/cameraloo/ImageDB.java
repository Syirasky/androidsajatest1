package com.example.user.cameraloo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
public class ImageDB extends  SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Image_DB";
    private static final String TABLE= "image";
    private static final String image_uri = "image_uri";
    private static final String student_id = "student_id";
    private static final String exam_code = "exam_code";

    public ImageDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE + "("+ image_uri + " TEXT PRIMARY KEY," + student_id + " TEXT,"  + exam_code + " TEXT" + ")";
        db.execSQL(CREATE_IMAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // Create tables again
        onCreate(db);
    }
    // code to add the new contact
    void addImg(Image img) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(image_uri,img.getUri());
        values.put(student_id, img.getStudentID()); // Contact Name
        values.put(exam_code, img.getExamcode()); // Contact Phone

        // Inserting Row
        db.insert(TABLE, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    // code to get the single contact
    Image getImage(String uri) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE, new String[] { image_uri, student_id, exam_code }, image_uri + "=?", new String[] { uri }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Image img = new Image(cursor.getString(0),cursor.getString(1), cursor.getString(2));
        // return contact
        return img;
    }
    public Image getSingle(String uri){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM image WHERE image_uri = ? ", new String[] {uri});
        if (cursor != null)
            cursor.moveToFirst();

        Image img = new Image(cursor.getString(0),cursor.getString(1), cursor.getString(2));
        // return contact
        return img;

    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE);
        return numRows;
    }
    public Integer deleteImage (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE, "image_uri = ? ", new String[] { id });
    }
    // code to update the single contact
    public int updateImage(Image img) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(student_id, img.getStudentID());
        values.put(exam_code, img.getExamcode());

        // updating row
        return db.update(TABLE, values, image_uri + " = ?",
                new String[] { String.valueOf(img.getUri()) });
    }
    public int updateStudentID(String stud_id,String uri){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(student_id,stud_id);

        return db.update(TABLE, cv, image_uri+"= ?", new String[]{String.valueOf(uri)});
    }


    // code to get all contacts in a list view
    public ArrayList<Image> getAllImages() {
        ArrayList<Image> imageList = new ArrayList<Image>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Image img = new Image();
                img.setUri(cursor.getString(0));
                img.setStudentID(cursor.getString(1));
                img.setExamcode(cursor.getString(2));
                // Adding contact to list
                imageList.add(img);
            } while (cursor.moveToNext());
        }

        // return contact list
        return imageList;
    }
    // code to get all contacts in a list view
    public ArrayList<Uri> getAllUri() {
        ArrayList<Image> imageList = new ArrayList<Image>();
        ArrayList<Uri> imguri = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT image_uri FROM " + TABLE ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Image img = new Image();
             //   img.setUri(cursor.getString(0));
                // Adding contact to list
               // imageList.add(img);
                imguri.add(Uri.parse(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        // return contact list
        return imguri;
    }






}
