package com.example.ps08877_dangnamvinh_asm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.ps08877_dangnamvinh_asm.API.API;
import com.example.ps08877_dangnamvinh_asm.API.RetrofitClient;
import com.example.ps08877_dangnamvinh_asm.Model.Login;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText txt_user, txt_password;
    private Button btn_login;
    private TextView tv_resgitered;
    RetrofitClient retrofitClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        setContentView(R.layout.activity_login);
        //Ánh xạ
        control();
        //Xử lý sự kiện
        event();
    }

    private void event() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API api = retrofitClient.getClient().create(API.class);
                api.loginUser(txt_user.getText().toString(), txt_password.getText().toString()).enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if(!response.isSuccessful()) {
                            Log.d("Error", "onResponse: Error" + response.code() + " : " + response.message());
                            return;
                        }
                        Login login = response.body();
                        if(login.getStatus()) {
                            Log.d("Tokennnn", "onResponse: " + login.getToken() + "");
                            SharedPreferences preferences = getSharedPreferences("Me", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("token", login.getToken()); // cai login.gettoken co gia tri ko

                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            Log.d("Error", "onResponse: " + login.getMsg());
                        }

                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Log.e("Error", "onFailure: " + t.getMessage() );
                    }
                });

            }
        });
        tv_resgitered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this, Regis_Activity.class);
                startActivity(in);
            }
        });
    }

    private void control() {
        txt_user = findViewById(R.id.txt_user);
        txt_password = findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.btn_login);
        tv_resgitered = findViewById(R.id.tv_resgitered);
    }
}
