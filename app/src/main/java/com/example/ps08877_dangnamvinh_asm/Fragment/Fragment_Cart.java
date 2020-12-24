package com.example.ps08877_dangnamvinh_asm.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps08877_dangnamvinh_asm.Adapter.Adapter_Cart;
import com.example.ps08877_dangnamvinh_asm.Model.AddCard;
import com.example.ps08877_dangnamvinh_asm.Model.Card;
import com.example.ps08877_dangnamvinh_asm.Model.Cart;
import com.example.ps08877_dangnamvinh_asm.R;
import com.example.ps08877_dangnamvinh_asm.SQLite.GioHang_Dao;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Cart extends Fragment {
    RecyclerView recyclerView;
    List<Cart> listcard;
    Adapter_Cart adapterCart;
    AddCard addCard;
    GioHang_Dao gioHangDao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart,container,false);

        recyclerView = view.findViewById(R.id.recyclerview_Cart);

        gioHangDao = new GioHang_Dao(getContext());
        listcard = new ArrayList<>();
        listcard = gioHangDao.getAllCard();

        recyclerView.setHasFixedSize(true);

        adapterCart = new Adapter_Cart(listcard,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterCart);

        return view;
    }


}
