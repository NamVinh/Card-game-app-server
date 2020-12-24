package com.example.ps08877_dangnamvinh_asm.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ps08877_dangnamvinh_asm.API.API;
import com.example.ps08877_dangnamvinh_asm.API.RetrofitClient;
import com.example.ps08877_dangnamvinh_asm.Adapter.Adapter_CardYugioh;
import com.example.ps08877_dangnamvinh_asm.Adapter.Adapter_CardYugiohAll;
import com.example.ps08877_dangnamvinh_asm.Adapter.SliderAdapterExample;
import com.example.ps08877_dangnamvinh_asm.Model.Card;
import com.example.ps08877_dangnamvinh_asm.Model.CardList;
import com.example.ps08877_dangnamvinh_asm.Model.Login;
import com.example.ps08877_dangnamvinh_asm.Model.Register;
import com.example.ps08877_dangnamvinh_asm.Model.Slideritem;
import com.example.ps08877_dangnamvinh_asm.Model.User;
import com.example.ps08877_dangnamvinh_asm.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_CardYugioh extends Fragment {
    Toolbar toolbar;
    TextView txt_ten;
    RetrofitClient retrofit;
    RecyclerView recyclerViewNguyenHop,recyclerViewBaiLe,recyclerViewAll;
    Adapter_CardYugioh adapterCardYugioh;
    Adapter_CardYugiohAll adapterCardYugiohAll;

    List<Card> listCard;
    SliderView sliderView;
    SliderAdapterExample adapter;
    EditText search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store,container,false);
        toolbar = view.findViewById(R.id.tool_bar123);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_store);
        recyclerViewNguyenHop = view.findViewById(R.id.recyclerview_NguyenHop);
        recyclerViewBaiLe = view.findViewById(R.id.recyclerview_BaiLe);
        recyclerViewAll = view.findViewById(R.id.recyclerview_All);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerViewNguyenHop.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNguyenHop.setLayoutManager(linearLayoutManager);
        recyclerViewBaiLe.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBaiLe.setLayoutManager(linearLayoutManager1);
        recyclerViewAll.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAll.setLayoutManager(gridLayoutManager);



        search = view.findViewById(R.id.edSearch);
        sliderView = view.findViewById(R.id.imageSlider);

        adapter = new SliderAdapterExample(getContext());
        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

       // addNewItem();

        renewItems();
        //removeLastItem();
        searchCard();
        getData();



        return view;
    }

    private void searchCard() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              filter(s.toString());
            }
        });
    }
    private void filter(String text) {
        ArrayList<Card> filterListitem = new ArrayList<>();

        for (Card item : listCard) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())){
                filterListitem.add(item);
            }
        }
        adapterCardYugioh.filterlist(filterListitem);
        adapterCardYugiohAll.filterlist(filterListitem);

    }
    private void getData() {
        API api = retrofit.getClient().create(API.class);
        //get token da luu
        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("Me",Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        if(!token.isEmpty()) {
            // api.getUserIsLogged("Bearer " + token).enqueue(new Callback<Register>() {
//                 @Override
//                 public void onResponse(Call<Register> call, Response<Register> response) {
//                     if(response.isSuccessful()) {
//                         Register register = response.body();
//                         User user = register.getUser();
//                         Log.d("User", "onResponse: " + user.getFullname());
//                     }
//                 }
//                 @Override
//                 public void onFailure(Call<Register> call, Throwable t) {
//                       Log.e("Result", "onResponse: " + t.getMessage());
//                 }
//             });
            api.getCardsByCat("Bearer " + token,"5e97105e6913053ccc849f34").enqueue(new Callback<CardList>() {
                @Override
                public void onResponse(Call<CardList> call, Response<CardList> response) {
                    if(!response.isSuccessful()) {
                        Log.d("Error", "onResponse: Error");
                        return;
                    }
                    CardList cardList = response.body();
                    if(cardList.getStatus()) {
                        listCard = cardList.getCardsList();
                        for(Card card : listCard) {
                            Log.d("Result", "onResponse: " + card.getName());
                            Log.d("Result", "onResponse: " + card.getCategory().getName());
                        }
                    }
                    adapterCardYugioh = new Adapter_CardYugioh(listCard,getContext());
                    recyclerViewNguyenHop.setAdapter(adapterCardYugioh);
                }

                @Override
                public void onFailure(Call<CardList> call, Throwable t) {
                    Log.e("Result", "onResponse: " + t.getMessage());
                }
            });


            api.getCardsByCat("Bearer " + token,"5e96fa02f038b330c0712f92").enqueue(new Callback<CardList>() {
                @Override
                public void onResponse(Call<CardList> call, Response<CardList> response) {
                    if(!response.isSuccessful()) {
                        Log.d("Error", "onResponse: Error");
                        return;
                    }
                    CardList cardList = response.body();
                    if(cardList.getStatus()) {
                        listCard = cardList.getCardsList();
                        for(Card card : listCard) {
                            Log.d("Result", "onResponse: " + card.getName());
                            Log.d("Result", "onResponse: " + card.getCategory().getName());
                        }
                    }
                    adapterCardYugioh = new Adapter_CardYugioh(listCard,getContext());
                    recyclerViewBaiLe.setAdapter(adapterCardYugioh);
                }

                @Override
                public void onFailure(Call<CardList> call, Throwable t) {
                    Log.e("Result", "onResponse: " + t.getMessage());
                }
            });

            api.getCards("Bearer " + token).enqueue(new Callback<CardList>() {
                @Override
                public void onResponse(Call<CardList> call, Response<CardList> response) {
                    if(!response.isSuccessful()) {
                        Log.d("Error", "onResponse: Error");
                        return;
                    }
                    CardList cardList = response.body();
                    if(cardList.getStatus()) {
                        listCard = cardList.getCardsList();
                        for(Card card : listCard) {
                            Log.d("Result", "onResponse: " + card.getName());
                            Log.d("Result", "onResponse: " + card.getCategory().getName());
                        }
                    }
                    adapterCardYugiohAll = new Adapter_CardYugiohAll(listCard,getContext());
                    recyclerViewAll.setAdapter(adapterCardYugiohAll);
                }

                @Override
                public void onFailure(Call<CardList> call, Throwable t) {
                    Log.e("Result", "onResponse: " + t.getMessage());
                }
            });

        }

    }

    public void renewItems() {
        List<Slideritem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 6; i++) {
            Slideritem sliderItem = new Slideritem();
            sliderItem.setDescription("Slider Item " + i);
            if (i  == 0) {
                sliderItem.setImageUrl("https://static1.squarespace.com/static/5b90acbae2ccd103c4e1515f/5b90ae0fcd83667e00e3a25c/5e60b204879e630db435128f/1585255272928/1.jpg?format=1500w");
            } else if (i == 1){
                sliderItem.setImageUrl("https://wallpaperaccess.com/full/252082.jpg");
            } else  if (i == 2) {
                sliderItem.setImageUrl("https://scontent-xsp1-2.xx.fbcdn.net/v/t1.15752-9/92829783_265854817768558_8176737095784595456_n.jpg?_nc_cat=104&_nc_sid=b96e70&_nc_oc=AQkGNUTB_NK6l5kiwe5AN2fEQQkaJqn5tnTJhF-PbDdMPHD2J7gI-b4VNrGBozD9psJ22Q3VMJqjqD0cTqK7H8Jz&_nc_ht=scontent-xsp1-2.xx&oh=2be86e7b5521dbfe4222fea03c2d7c2f&oe=5EC07D9C");
            } else  if (i == 3) {
                sliderItem.setImageUrl("https://scontent-xsp1-2.xx.fbcdn.net/v/t1.15752-9/93790693_160130065355312_3338803096535957504_n.jpg?_nc_cat=106&_nc_sid=b96e70&_nc_oc=AQlyKlTBEWL42mY0bTE25-s88fOv2FzNKR0JvaPsChum8LprPX2VDGuWzfXBRShACbw8VP3NDV8wMKe4mKIcVKbr&_nc_ht=scontent-xsp1-2.xx&oh=4940f0de108a72ea7455296a1972be31&oe=5EC2F427");
            } else if (i == 4) {
                sliderItem.setImageUrl("https://scontent-xsp1-1.xx.fbcdn.net/v/t1.15752-9/93805485_866076283909489_3228291758353809408_n.jpg?_nc_cat=105&_nc_sid=b96e70&_nc_oc=AQkN8y_3yuIqWv-QTjXFARdqOdvqNoLl-QQbqNfhtI_dzqCQ9Ec48_vybm0HopT-g1zdkYcMJ3sjrSm7X_64jotV&_nc_ht=scontent-xsp1-1.xx&oh=c3edf6f7c927e446dfda8f32c18a6d74&oe=5EC0AF01");
            }else if (i == 5) {
                sliderItem.setImageUrl("https://wallpaperaccess.com/full/1429133.jpg");
            }


           sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

//    public void removeLastItem() {
//        if (adapter.getCount() - 1 >= 0)
//            adapter.deleteItem(adapter.getCount() - 1);
//    }

//    public void addNewItem() {
//        Slideritem sliderItem = new Slideritem();
//        sliderItem.setDescription("Slider Item Added Manually");
//        adapter.addItem(sliderItem);
//    }
}
