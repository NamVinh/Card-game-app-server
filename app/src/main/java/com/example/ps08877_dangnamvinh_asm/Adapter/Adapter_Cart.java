package com.example.ps08877_dangnamvinh_asm.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps08877_dangnamvinh_asm.DetailActivity;
import com.example.ps08877_dangnamvinh_asm.Model.AddCard;
import com.example.ps08877_dangnamvinh_asm.Model.Card;
import com.example.ps08877_dangnamvinh_asm.Model.Cart;
import com.example.ps08877_dangnamvinh_asm.R;
import com.example.ps08877_dangnamvinh_asm.SQLite.GioHang_Dao;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;

public class Adapter_Cart extends RecyclerView.Adapter<Adapter_Cart.ViewHolder> {
    List<Cart> cardyugiohList;
    Context context;
    GioHang_Dao gioHangDao;
    String BaseURL = "http://192.168.1.7:4000/";


    public Adapter_Cart(List<Cart> cardyugiohList, Context context){
        this.cardyugiohList = cardyugiohList;
        this.context = context;
        this.gioHangDao = new GioHang_Dao(context) ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cart,parent,false);
        
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Cart card = cardyugiohList.get(position);
        Picasso.get().load(BaseURL + card.getCard().getImage()).into(holder.img_card);
        holder.txt_tencard.setText(card.getCard().getName());
        holder.txt_tentheloai.setText(card.getCard().getCategory().getName());
        holder.txt_giatien.setText(card.getCard().getPrice());
        holder.txt_num.setText(card.getNum()+"");
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // lấy vị trí
                final Cart cart = cardyugiohList.get(position);

                // tạo dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa Card ");
                builder.setMessage("Bạn Có Muốn Xóa Card Này Không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Đã Hủy", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Xóa ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // delete hóa đơn
                        if (gioHangDao.delete(cart)){
                            Toast.makeText(context,"Đã xóa",Toast.LENGTH_SHORT).show();
                            cardyugiohList.clear();

                                cardyugiohList.addAll(gioHangDao.getAllCard());

                            notifyDataSetChanged();
                            dialogInterface.dismiss();
                        }
                        else {
                            Toast.makeText(context,"xóa thất bại",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return cardyugiohList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_card,imageDelete;
        TextView txt_tencard,txt_giatien,txt_tentheloai,txt_num;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            img_card = itemView.findViewById(R.id.img_cart);
            imageDelete = itemView.findViewById(R.id.imageDelete);
            txt_tencard = itemView.findViewById(R.id.txt_tencart);
            txt_giatien = itemView.findViewById(R.id.txt_giatiencart);
            txt_tentheloai = itemView.findViewById(R.id.txt_tentheloaicart);
            txt_num = itemView.findViewById(R.id.txt_soluong);
        }
    }
}
