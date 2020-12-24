package com.example.ps08877_dangnamvinh_asm.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbYugioh";
    public static final int VERSION = 2;




    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null ,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(GioHang_Dao.SQL_Yugioh);
        db.execSQL(CategoryDao.SQL_Category);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+CategoryDao.TABLE_NAME);
        db.execSQL("Drop table if exists "+GioHang_Dao.TABLE_NAME);


        onCreate(db);
    }
}
