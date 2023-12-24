package com.example.login_register_fix.BottomNavBar;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login_register_fix.CartActivity;
import com.example.login_register_fix.R;

public class Notification extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);




        //BottomNavBar
        //Chuyen den Profile
        imageView = findViewById(R.id.imgProfile);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(Notification.this,  Profile.class);
                startActivity(intent);
            }
        });

        //Chuyen den gio hang
        imageView = findViewById(R.id.imgCart);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(Notification.this,  CartActivity.class);
                startActivity(intent);
            }
        });

        //Chuyen den wishlist
        imageView = findViewById(R.id.imgWishlist);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(Notification.this,  Wishlist.class);
                startActivity(intent);
            }
        });

        //Chuyen den Notification
        imageView = findViewById(R.id.imgNotification);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(Notification.this,  Notification.class);
                startActivity(intent);
            }
        });

        //Chuyen den home
        imageView = findViewById(R.id.imgHome);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(Notification.this,  com.example.login_register_fix.HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}