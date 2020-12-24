package com.example.ps08877_dangnamvinh_asm.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ps08877_dangnamvinh_asm.API.API;
import com.example.ps08877_dangnamvinh_asm.API.RetrofitClient;
import com.example.ps08877_dangnamvinh_asm.Model.Card;
import com.example.ps08877_dangnamvinh_asm.Model.CardList;
import com.example.ps08877_dangnamvinh_asm.Model.Register;
import com.example.ps08877_dangnamvinh_asm.Model.User;
import com.example.ps08877_dangnamvinh_asm.Model.UserList;
import com.example.ps08877_dangnamvinh_asm.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Account extends Fragment {
    TextView tvFullname,tvPhone,tvEmail;
    ImageView imageExit;
    RetrofitClient retrofitClient;
    User user;
    List<User> listuser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);
        tvFullname = view.findViewById(R.id.tvFullname);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvEmail = view.findViewById(R.id.tvEmail);
        imageExit = view.findViewById(R.id.imgExit);

        imageExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        getData();
        return view;
    }

    private void getData() {
        API api = retrofitClient.getClient().create(API.class);
        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("Me", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        if(!token.isEmpty()) {
            api.getUserIsLogged("Bearer "+token).enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    if(response.isSuccessful()){
                        Register register = response.body();
                        tvEmail.setText(register.getUser().getEmail());
                        tvFullname.setText(register.getUser().getFullname());
                        tvPhone.setText(register.getUser().getPhone());
                    }
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {

                }
            });
        }
    }
}
