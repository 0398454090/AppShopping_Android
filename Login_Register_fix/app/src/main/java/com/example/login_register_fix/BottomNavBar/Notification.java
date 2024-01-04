package com.example.login_register_fix.BottomNavBar;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_register_fix.Adapter.FoodAdapter;
import com.example.login_register_fix.Adapter.NotificationAdapter;
import com.example.login_register_fix.CartActivity;
import com.example.login_register_fix.DetailActivity;
import com.example.login_register_fix.Model.FoodModel;
import com.example.login_register_fix.Model.NotificationModel;
import com.example.login_register_fix.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notification extends AppCompatActivity {

    private ImageView imageView;
    RecyclerView recyclerView;
    boolean isColorChanged = false;


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
                Intent intent = new Intent(Notification.this, Profile.class);
                startActivity(intent);
            }
        });

        //Chuyen den gio hang
        imageView = findViewById(R.id.imgCart);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(Notification.this, CartActivity.class);
                startActivity(intent);
            }
        });

        //Chuyen den wishlist
        imageView = findViewById(R.id.imgWishlist);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(Notification.this, Wishlist.class);
                startActivity(intent);
            }
        });

        //Chuyen den Notification
        imageView = findViewById(R.id.imgNotification);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(Notification.this, Notification.class);
                startActivity(intent);
            }
        });

        //Chuyen den home
        imageView = findViewById(R.id.imgHome);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(Notification.this, com.example.login_register_fix.HomeActivity.class);
                startActivity(intent);
            }
        });

        initFavoriteMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wishlist, menu);
        return true;
    }

    private void initFavoriteMenu() {
        DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference("Notification");

        recyclerView = findViewById(R.id.wishlist_View);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<NotificationModel> favlist = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NotificationModel tb = snapshot.getValue(NotificationModel.class);
                    if (tb != null) {
                        favlist.add(tb);
                    }
                }
                if (!favlist.isEmpty()) {
                    NotificationAdapter adapter = new NotificationAdapter(favlist, Notification.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d(TAG, "No food data found");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}

