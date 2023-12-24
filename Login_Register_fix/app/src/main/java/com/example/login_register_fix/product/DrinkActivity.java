package com.example.login_register_fix.product;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_register_fix.Adapter.DrinkAdapter;
import com.example.login_register_fix.BottomNavBar.Notification;
import com.example.login_register_fix.BottomNavBar.Profile;
import com.example.login_register_fix.BottomNavBar.Wishlist;
import com.example.login_register_fix.CartActivity;
import com.example.login_register_fix.HomeActivity;
import com.example.login_register_fix.Model.DrinkModel;
import com.example.login_register_fix.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DrinkActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView  imgreturn, imgProfile,  imgCart, imgWishlist, imgNotification, imgHome;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        imgreturn = findViewById(R.id.imgreturn);
        imgProfile = findViewById(R.id.imgProfile);
        imgCart = findViewById(R.id.imgCart);
        imgWishlist = findViewById(R.id.imgWishlist);
        imgNotification = findViewById(R.id.imgNotification);
        imgHome = findViewById(R.id.imgHome);

        imgreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrinkActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrinkActivity.this, Profile.class);
                startActivity(intent);
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrinkActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        imgWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrinkActivity.this, Wishlist.class);
                startActivity(intent);
            }
        });

        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrinkActivity.this, Notification.class);
                startActivity(intent);
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrinkActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        initDrinkMenu();
    }



    private void initDrinkMenu() {
        DatabaseReference drinkRef = FirebaseDatabase.getInstance().getReference("Drink");

        recyclerView = findViewById(R.id.viewDrink);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        drinkRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<DrinkModel> drinkList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DrinkModel drink = snapshot.getValue(DrinkModel.class);
                    if (drink != null) {
                        drinkList.add(drink);
                    }
                }
                if (!drinkList.isEmpty()) {
                    DrinkAdapter adapter = new DrinkAdapter(drinkList, DrinkActivity.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d(TAG, "No drink data found");
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