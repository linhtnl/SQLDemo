package com.linhtnl.sqldemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String COLUMN_CODE = "Code";
    public static final String COLUMN_FULLNAME = "fullname";
    public static final String COLUMN_DATE_OF_BIRTH = "dateOfBirth";
    public static final String TABLE_STUDENT = "Student";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "student.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table
        String createStudentTable = "CREATE TABLE " + TABLE_STUDENT + " (" + COLUMN_CODE + " TEXT PRIMARY KEY, " + COLUMN_FULLNAME + " TEXT, " + COLUMN_DATE_OF_BIRTH + " TEXT )";
        sqLiteDatabase.execSQL(createStudentTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean checkExistStudentCode(String studentCode){
        boolean result=false;
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT * FROM "+TABLE_STUDENT+" where "+ COLUMN_CODE+" = '"+studentCode+"'";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            result=true;
        }
        cursor.close();
        db.close();
        return result;
    }
    public boolean createNewStudent(StudentModel student) {
        //getWritableDatabase for insert actions
        //getReadableDatabase for select actions
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_CODE, student.getStudentCode());
        contentValues.put(COLUMN_FULLNAME, student.getFullName());
        contentValues.put(COLUMN_DATE_OF_BIRTH, student.getDateOfBirth());

        long insert = db.insert(TABLE_STUDENT, null, contentValues);
        if (insert == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public List<StudentModel> getAllStudents() {
        List<StudentModel> list = new ArrayList<>();

        String queryStr = "SELECT * FROM " + TABLE_STUDENT;
        SQLiteDatabase db = this.getReadableDatabase();

        //Cursor is the result set from a SQL statement
        Cursor cursor = db.rawQuery(queryStr, null);
        if (cursor.moveToFirst()) {
            do {
                String code = cursor.getString(0);
                String fullName = cursor.getString(1);
                String DOB = cursor.getString(2);
                StudentModel student = new StudentModel(code, fullName, DOB);
                list.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean DeleteAStudent(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result=db.delete(TABLE_STUDENT, COLUMN_CODE + " = '" + code + "'", null) > 0;
        db.close();
        return result;
    }

    public StudentModel findStudentByCode(String code) {
        StudentModel student = null;
        String queryStr = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + COLUMN_CODE + " = '" + code + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryStr, null);
        if (cursor.moveToFirst()) {
            String stuCode = cursor.getString(0);
            String fullName = cursor.getString(1);
            String DOB = cursor.getString(2);
            student = new StudentModel(stuCode, fullName, DOB);
        }
        return student;
    }
    public boolean updateStudent(StudentModel student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FULLNAME, student.getFullName());
        contentValues.put(COLUMN_DATE_OF_BIRTH, student.getDateOfBirth());

        return db.update(TABLE_STUDENT,contentValues,"code = '"+student.getStudentCode()+"'",null)>0;
    }
}
