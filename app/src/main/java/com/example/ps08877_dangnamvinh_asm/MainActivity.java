package com.example.ps08877_dangnamvinh_asm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.example.ps08877_dangnamvinh_asm.Fragment.Fragment_Account;
import com.example.ps08877_dangnamvinh_asm.Fragment.Fragment_Cart;
import com.example.ps08877_dangnamvinh_asm.Fragment.Fragment_CardYugioh;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom_nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
            bottom_nav = findViewById(R.id.botom_nav);

            // Cho Màn Hình mặc định khi vào app là store
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,
                        new Fragment_CardYugioh()).commit();
                bottom_nav.setSelectedItemId(R.id.store);
            }
//        bottom_nav.setItemIconTintList(null);

            bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.store:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,
                                    new Fragment_CardYugioh()).commit();
                            return true;
                        case R.id.cart:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,
                                    new Fragment_Cart()).commit();
                            return true;
                        case R.id.account:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,
                                    new Fragment_Account()).commit();
                            return true;
                    }
                    return false;
                }
            });
        }
    }

