package com.example.ps08877_dangnamvinh_asm.Adapter;

import android.content.Context;
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
import com.example.ps08877_dangnamvinh_asm.Model.Card;
import com.example.ps08877_dangnamvinh_asm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_CardYugioh extends RecyclerView.Adapter<Adapter_CardYugioh.ViewHolder> {
    List<Card> cardyugiohList;
    Context context;
    String BaseURL = "http://192.168.1.7:4000/";


    public Adapter_CardYugioh(List<Card> cardyugiohList, Context context){
        this.context = context;
        this.cardyugiohList = cardyugiohList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardview_store,parent,false);
        
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Card card = cardyugiohList.get(position);
        Picasso.get().load(BaseURL + card.getImage()).into(holder.img_card);
        holder.txt_tencard.setText(card.getName());
        holder.txt_tentheloai.setText(card.getCategory().getName());
        holder.txt_giatien.setText(String.valueOf(card.getPrice() + ""));
        //holder.txt_mota.setText(card.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Position : "+position,Toast.LENGTH_LONG).show();
                Intent i = new Intent(context, DetailActivity.class);
                //bắn dữ liệu qua activity chi tiết để bên activity lấy du liệu
                Bundle bundle = new Bundle();
                bundle.putString("tien", cardyugiohList.get(position).getPrice() + "");
                bundle.putString("anh", cardyugiohList.get(position).getImage());
                bundle.putString("ten", cardyugiohList.get(position).getName());
                bundle.putString("mota", cardyugiohList.get(position).getDescription());
                bundle.putString("tentheloai",cardyugiohList.get(position).getCategory().getName());
                bundle.putString("idtentheloai",cardyugiohList.get(position).getCategory().get_id());
                bundle.putString("_id",cardyugiohList.get(position).get_id());

                i.putExtras(bundle);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardyugiohList.size();
    }

    public void filterlist (List<Card> filteredlist) {
        cardyugiohList = filteredlist;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_card;
        TextView txt_tencard,txt_giatien,txt_tentheloai,txt_mota;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            img_card = itemView.findViewById(R.id.img_card);
            txt_tencard = itemView.findViewById(R.id.txt_tencard);
            txt_giatien = itemView.findViewById(R.id.txt_giatien);
            txt_tentheloai = itemView.findViewById(R.id.txt_tentheloai);
           // txt_mota = itemView.findViewById(R.id.txt_mota);
        }
    }
}
