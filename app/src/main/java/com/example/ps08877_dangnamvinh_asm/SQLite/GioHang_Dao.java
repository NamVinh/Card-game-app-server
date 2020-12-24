package com.example.ps08877_dangnamvinh_asm.SQLite;


import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ps08877_dangnamvinh_asm.Model.AddCard;
import com.example.ps08877_dangnamvinh_asm.Model.Card;
import com.example.ps08877_dangnamvinh_asm.Model.Cart;
import com.example.ps08877_dangnamvinh_asm.Model.Category;

import java.util.ArrayList;


public class GioHang_Dao {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public static final String TABLE_NAME = "Yugioh";
    public static final String TAG = "GioHang";
    public static final String SQL_Yugioh ="CREATE TABLE Yugioh (_id integer primary key autoincrement,idsanpham Text ,name text,price text,description text,image text,category text,soluong text)";

    public GioHang_Dao(Context context){
        dbHelper = new DBHelper(context);
    }



    public Cursor getDataQuery(String sql){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);
        return c;
    }

    //insert
    public boolean insert (Cart card) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idsanpham", card.getCard().get_id());
        values.put("name", card.getCard().getName());
        values.put("price", card.getCard().getPrice());
        values.put("description", card.getCard().getDescription());
        values.put("image", card.getCard().getImage());
        values.put("category", card.getCard().getCategory().getName());
        values.put("soluong", card.getNum());
        long row = db.insert(TABLE_NAME, null, values);
        if (row > 0) {
            return true;
        } else {
            return false;
        }
    }

    // getAll
    public ArrayList<Cart> getAllCard() {
        ArrayList<Cart> listcard = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Card card1 = new Card();
                card1.set_id(c.getString(1));
                card1.setName( c.getString(2));
                card1.setPrice(c.getString(3));
                card1.setDescription(c.getString(4));
                card1.setImage(c.getString(5));
                Category category = new Category();
                category.setName(c.getString(6));
                card1.setCategory(category);
                Cart cart1 = new Cart(c.getInt(0), card1, c.getString(7));
                listcard.add(cart1);
            } while (c.moveToNext());
        }
        return listcard;
    }

    //checkID
    public ArrayList<String> getALLID () {
        ArrayList<String> dsid = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor c = db.query("Yugioh",null,null,null,null,null,null);
        if (c.moveToFirst()){
            do {

                String id = c.getString(1);
                dsid.add(id);
            } while (c.moveToNext());
            c.close();
        }
        return  dsid;
    }

    //delete
    public boolean delete(Cart card){
        db = dbHelper.getWritableDatabase();
        int result = db.delete(TABLE_NAME,"_id =?",new String[]{String.valueOf(card.getId())});
        if (result >= 0) {
            return true;
        } else {
            return false;
        }
    }

    //update
    public boolean update(Cart cart) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soluong", cart.getNum());
        Log.d("cartnum",cart.getCard().get_id());
        long row = db.update(TABLE_NAME, values, "idsanpham =?" , new String[]{cart.getCard().get_id()});
        if (row >= 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean getSanphamByID(String id){
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE idsanpham='"+id+"'", null);
        if(c.getCount()>0) {
            return true;
        }
        return false;
    }
    public int getToTal(){
        int total =0;
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(soluong*price) FROM Yugioh",null);
        if (c.moveToFirst()){
            do {
                total = c.getInt(0);
            } while (c.moveToNext());
        }
        return total;
    }
}

