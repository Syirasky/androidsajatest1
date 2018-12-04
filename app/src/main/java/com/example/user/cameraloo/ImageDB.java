package com.example.user.cameraloo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
public class ImageDB extends  SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "Image_DB";


    private static final String TABLE= "image2";
    private static final String image_uri = "image_uri";
    private static final String student_id = "student_id";
    private static final String exam_code = "exam_code";
    private static final String subject_id = "subject_id";
    private static final String answer = "answer";
    private static final String score = "score";

    private static final String TABLEUSER = "user";
    private static final String user_id = "user_id";
    private static final String email = "email";


    private static final String TABLE_EXAM = "exam";
    private static final String examid = "examid";
    private static final String exam_code2 = "exam_code2";
    private static final String subject_id2 = "subject_id2";
    private static final String lecturer_id = "lecturer_id";
    private static final String answer2 = "answer";

    public ImageDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE + "("+ image_uri + " TEXT PRIMARY KEY," + student_id + " TEXT," + user_id + " TEXT,"  + exam_code + " TEXT," +  subject_id + " TEXT," + answer + " TEXT," + score + " TEXT"+")";
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLEUSER + "("+ user_id + " TEXT PRIMARY KEY,"+ email + " TEXT"+")";
        String CREATE_EXAM_TABLE = "CREATE TABLE exam ("+ exam_code2 +" UNIQUE,"
                                                        + subject_id2 + " UNIQUE,"
                                                        + lecturer_id + " ,"
                                                        + answer2 + ""
                                                        +")";
        db.execSQL(CREATE_IMAGE_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_EXAM_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLEUSER);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EXAM);
        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    void addImg(Image img) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(image_uri,img.getUri());
        values.put(student_id, img.getStudentID());// Contact Name
        values.put(user_id,img.getUserID());
        values.put(exam_code, img.getExamcode());
        values.put(subject_id,img.getSubject_id());
        values.put(answer,img.getAnswer());// Contact Phone
        values.put(score,img.getScore());
        // Inserting Row
        db.insert(TABLE, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    void addExamInfo(ExamInfo ex) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(exam_code2,ex.getExamcode2());
        values.put(subject_id2,ex.getSubjectid2());
        values.put(lecturer_id,ex.getLecturerid());
        values.put(answer2,ex.getAnswer2());

        // Inserting Row
        db.insert(TABLE_EXAM, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    // code to add the new contact
    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(user_id,user.getUserID());
        values.put(email, user.getEmail());// Contact Name

        // Inserting Row
        db.insert(TABLEUSER, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    // code to get the single contact
    Image getImage(String uri) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE, new String[] { image_uri, student_id,user_id ,exam_code,subject_id }, image_uri + "=?", new String[] { uri }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Image img = new Image(  cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6));
        // return contact
        return img;
    }


    public Image getSingle(String uri){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM image WHERE image_uri = ? ", new String[] {uri});
        if (cursor != null)
            cursor.moveToFirst();

        Image img = new Image(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6));
        // return contact
        return img;

    }

    public Integer deleteImage (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE, "image_uri = ? ", new String[] { id });
    }
    public void LogoutUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLEUSER);
        db.close();
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE);
        return numRows;
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

    } public int updateScore(String stid,String subj, String exm, String sc){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(score,sc);

        return db.update(TABLE, cv,
                student_id+"= ? and " + subject_id + " = ? and "+ exam_code + " = ? ", new String[]{ String.valueOf(stid), String.valueOf(subj), String.valueOf(exm) });

    }

   /* public void updateScore(String stud_id,String exmcode, String sb_id,String sc){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(score,"10");
        String strSQL = "UPDATE image2 SET score = '"+sc+"'" +
                " WHERE student_id = '"+ stud_id+"' and " +
                        "subject_id ='"+sb_id+"' and "+
                        "exam_code ='"+exmcode+"'";

        db.execSQL(strSQL);
     //  return db.update(TABLE, cv,student_id+"= ? AND "+exam_code+" = ? AND "+subject_id+"= ?"               , new String[]{stud_id,exmcode,sb_id});
    }*/

 /*   private static final String image_uri = "image_uri";
    private static final String student_id = "student_id";
    private static final String exam_code = "exam_code";
    private static final String subject_id = "subject_id";
    private static final String answer = "answer";
    private static final String score = "score";
*/

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
                img.setUserID(cursor.getString(2));
                img.setExamcode(cursor.getString(3));
                img.setSubject_id(cursor.getString(4));
                img.setAnswer(cursor.getString(5));
                img.setScore(cursor.getString(6));
                // Adding contact to list
                imageList.add(img);
            } while (cursor.moveToNext());
        }

        // return contact list
        return imageList;
    }



    // code to get all contacts in a list view
    public ArrayList<Image> getAllRelatedImages(String sid,String ecode) {
        ArrayList<Image> imageList = new ArrayList<Image>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE + " WHERE subject_id ='"+sid+"' AND exam_code = '"+ecode+"'";
        Log.d("sql",selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Image img = new Image();
                img.setUri(cursor.getString(0));
                img.setStudentID(cursor.getString(1));
                img.setUserID(cursor.getString(2));
                img.setExamcode(cursor.getString(3));
                img.setSubject_id(cursor.getString(4));
                img.setAnswer(cursor.getString(5));
                img.setScore(cursor.getString(6));
                // Adding contact to list
                imageList.add(img);
            } while (cursor.moveToNext());
        }

        // return contact list
        return imageList;
    }
    // code to get all contacts in a list view
    public ArrayList<Image> getRelatedImages(String usid) {
        ArrayList<Image> imageList = new ArrayList<Image>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE + " WHERE user_id ='"+usid+"'";
        Log.d("sql",selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Image img = new Image();
                img.setUri(cursor.getString(0));
                img.setStudentID(cursor.getString(1));
                img.setUserID(cursor.getString(2));
                img.setExamcode(cursor.getString(3));
                img.setSubject_id(cursor.getString(4));
                img.setAnswer(cursor.getString(5));
                img.setScore(cursor.getString(6));
                // Adding contact to list
                imageList.add(img);
            } while (cursor.moveToNext());
        }

        // return contact list
        return imageList;
    }
    // code to get all contacts in a list view
    public ArrayList<ExamInfo> getRelatedExamInfo(String lec_id) {
        ArrayList<ExamInfo> ExamList = new ArrayList<ExamInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXAM + " WHERE lecturer_id ='"+lec_id+"'";
        Log.d("sql",selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ExamInfo exm = new ExamInfo();
                exm.setExamcode2(cursor.getString(0));
                exm.setSubjectid2(cursor.getString(1));
                exm.setLecturerid(cursor.getString(2));
                exm.setAnswer2(cursor.getString(3));

                // Adding contact to list
                ExamList.add(exm);
            } while (cursor.moveToNext());
        }

        // return contact list
        return ExamList;
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

    public List<String> getAllStudentID() {
        ArrayList<Image> imageList = new ArrayList<Image>();
        List<String> studentID = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT student_id FROM " + TABLE ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                studentID.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // return contact list
        return studentID;
    }
    // code to get all contacts in a list view
    public ArrayList<Image> getScoreUIDSubjectExam(String usid,String sabjekid, String eksemcode) {
        ArrayList<Image> imageList = new ArrayList<Image>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE + " WHERE user_id ='"+usid+ "' AND subject_id = '"+sabjekid +"' AND exam_code = '"+eksemcode+"'";
        Log.d("sql",selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Image img = new Image();
                img.setUri(cursor.getString(0));
                img.setStudentID(cursor.getString(1));
                img.setUserID(cursor.getString(2));
                img.setExamcode(cursor.getString(3));
                img.setSubject_id(cursor.getString(4));
                img.setAnswer(cursor.getString(5));
                img.setScore(cursor.getString(6));
                // Adding contact to list
                imageList.add(img);
            } while (cursor.moveToNext());
        }

        // return contact list
        return imageList;
    }








}
