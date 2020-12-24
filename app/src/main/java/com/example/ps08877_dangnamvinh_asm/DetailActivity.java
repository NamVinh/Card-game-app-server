package com.example.ps08877_dangnamvinh_asm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ps08877_dangnamvinh_asm.API.API;
import com.example.ps08877_dangnamvinh_asm.Fragment.Fragment_Cart;
import com.example.ps08877_dangnamvinh_asm.Model.AddCard;
import com.example.ps08877_dangnamvinh_asm.Model.Card;
import com.example.ps08877_dangnamvinh_asm.Model.Cart;
import com.example.ps08877_dangnamvinh_asm.Model.Category;
import com.example.ps08877_dangnamvinh_asm.SQLite.GioHang_Dao;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {
    API API;
    Retrofit retrofit;
    ImageView img, backbtn;
    Date currentTime = Calendar.getInstance().getTime();
    Button btn_mua_ngay, btn_them_vao_gio;
   // String stringcurrenttime;
    TextView tv_tengame, tv_tentheloai, tv_giatien, tv_description,tv_id;
    String ten, tien, anh, nph, mota,tentheloai,_id, num,idtheloai;
    GioHang_Dao gioHangDao;
    String BaseURL = "http://192.168.1.7:4000/";
    List<Cart> cartlist = new ArrayList<>();
    ElegantNumberButton buttonnumber;
    boolean checkMa = true ;
   // SimpleDateFormat dataformat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietgame);
       // stringcurrenttime = dataformat.format(currentTime);
        getSupportActionBar().hide(); // hide the title bar

        //anh xa
        img = findViewById(R.id.iv_chitietgame);

        tv_tengame = findViewById(R.id.tv_tengamechitiet);
        tv_tentheloai = findViewById(R.id.tv_tentheloai);
        tv_giatien = findViewById(R.id.tv_giatienchitiet);
        tv_description = findViewById(R.id.tv_description);
        backbtn = findViewById(R.id.img_back_chi_tiet_game);
        btn_them_vao_gio = findViewById(R.id.btn_themvaogio);
        buttonnumber = (ElegantNumberButton) findViewById(R.id.btnplus);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        button.setOnClickListener(new ElegantNumberButton.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                 //num = button.getNumber();
//            }
//        });
        buttonnumber.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                num = buttonnumber.getNumber();
                Log.d("TAG", "num: "+num);

            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API = retrofit.create(API.class);

        //lấy dữ liệu
        Bundle bundle = getIntent().getExtras();
        tien = bundle.getString("tien");
        anh = bundle.getString("anh");
        ten = bundle.getString("ten");
        mota = bundle.getString("mota");
        tentheloai = bundle.getString("tentheloai");
        idtheloai = bundle.getString("idtentheloai");
        _id = bundle.getString("_id");
        //set dữ liệu lên view
        tv_giatien.setText("$" + tien);
        Picasso.get().load(BaseURL + anh).into(img);
        tv_tengame.setText(ten);
       // tv_nhaphathanh.setText(nph);
        tv_tentheloai.setText(tentheloai);
        tv_description.setText(mota);

        Log.d("TAG", "createCart: "+_id);
        Log.d("TAG", "createCart: "+ten);
        Log.d("TAG", "createCart: "+mota);
        Log.d("TAG", "createCart: "+anh);
        Log.d("TAG", "createCart: "+tien);
        Log.d("TAG", "createCart: "+idtheloai);
        Log.d("TAG", "createCart: "+tentheloai);

        btn_them_vao_gio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //createCart();
                gioHangDao = new GioHang_Dao(DetailActivity.this);
                Cart cart = new Cart();
                Card card = new Card();
                card.set_id(_id);
                card.setName(ten);
                card.setPrice(tien);
                card.setImage(anh);
                card.setDescription(mota);
                Category cat = new Category();
                cat.setName(tentheloai);
                card.setCategory(cat);
                cart.setCard(card);
                cart.setNum(num);
                boolean check = gioHangDao.getSanphamByID(_id);
                    if (check){
                        if( gioHangDao.update(cart)) {
                            Toast.makeText(DetailActivity.this, "Update Số lượng thành công", Toast.LENGTH_SHORT).show();
                            finish();
                            checkMa = false;
                        }else{
                            Toast.makeText(DetailActivity.this, "fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        //AddCard card = new AddCard(_id,ten,mota,anh,tien,num,tentheloai);
                        gioHangDao.insert(cart);
                        Toast.makeText(DetailActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "createCart: " + _id);
                        finish();
                    }

            }
        });
    }

}
