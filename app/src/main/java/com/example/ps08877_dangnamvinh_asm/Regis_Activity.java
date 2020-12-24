package com.example.ps08877_dangnamvinh_asm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ps08877_dangnamvinh_asm.API.API;
import com.example.ps08877_dangnamvinh_asm.API.RetrofitClient;
import com.example.ps08877_dangnamvinh_asm.Model.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Regis_Activity extends AppCompatActivity {
    EditText txtFullname,txtPhone,txtEmail,txtPassword;
    Button btnRegister,btnCancle;
    RetrofitClient retrofitClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_regis);

        txtFullname = findViewById(R.id.txtFullname);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnCancle = findViewById(R.id.btnCancle);

        Register();
    }

    private void Register() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                API api = retrofitClient.getClient().create(API.class);
                api.Sinup(txtFullname.getText().toString(),
                        txtPhone.getText().toString(),
                        txtEmail.getText().toString(),
                        txtPassword.getText().toString()).enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
                        if(!response.isSuccessful()) {
                            Log.d("Error", "onResponse: Error" + response.code() + " : " + response.message());
                            return;
                        }
                        Register register = response.body();
                        if(register.getStatus()) {
                            Log.d("Success", "Dang ky thanh cong: " + register.getUser().getFullname());
                            Intent intent = new Intent(Regis_Activity.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            Log.d("Error", "Dang ky khong thanh cong: " + register.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {

                    }
                });
            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }
}
