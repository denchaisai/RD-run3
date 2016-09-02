package com.saisupatpon.denchai.rdrun;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 9/2/2016.
 */
public class MyOpenHelper extends SQLiteOpenHelper {

    //Explicit
    public static final String database_name = "rdRun.db";//ค่าคงที่แก้ไขไม่ได้ ชื่อไฟล์ฐานข้อมูล
    private static final int database_version = 1;
    private static final String create_user_table = "create table userTABLE (" +
            "_id integer primary key, " +
            "User text," +
            "Password text," +
            "Name text," +
            "Surname text," +
            "Avata text," +
            "idUser text);";



    //สส้าง constructor alt enter
    //ต่อท่อด้วย context
    public MyOpenHelper(Context context) {
        super(context, database_name, null, database_version);

    }//constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_user_table);//open helper = ยามเดินเข้าไปข้างใน
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}//main class
