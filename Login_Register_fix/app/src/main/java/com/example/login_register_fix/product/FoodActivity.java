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

import com.example.login_register_fix.Adapter.FoodAdapter;
import com.example.login_register_fix.BottomNavBar.Notification;
import com.example.login_register_fix.BottomNavBar.Profile;
import com.example.login_register_fix.BottomNavBar.Wishlist;
import com.example.login_register_fix.CartActivity;
import com.example.login_register_fix.HomeActivity;
import com.example.login_register_fix.Model.FoodModel;
import com.example.login_register_fix.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView imgBreak, imgLunch, imgDinner, imgfast, imgreturn, imgProfile,  imgCart, imgWishlist, imgNotification, imgHome;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        imgBreak = findViewById(R.id.imgBreak);
        imgLunch = findViewById(R.id.imgLunch);
        imgDinner = findViewById(R.id.imgDinner);
        imgfast = findViewById(R.id.imgfast);
        imgreturn = findViewById(R.id.imgreturn);
        imgProfile = findViewById(R.id.imgProfile);
        imgCart = findViewById(R.id.imgCart);
        imgWishlist = findViewById(R.id.imgWishlist);
        imgNotification = findViewById(R.id.imgNotification);
        imgHome = findViewById(R.id.imgHome);


        //chuyen den trang BreakActivity
        imgBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, BreakActivity.class);
                startActivity(intent);
            }
        });

        //chuyen den trang LunchActivity
        imgLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, LunchActivity.class);
                startActivity(intent);
            }
        });

        //chuyen den trang DinnerActivity
        imgDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, DinnerActivity.class);
                startActivity(intent);
            }
        });

        //chuyen den trang DinnerActivity
        imgfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, FastfoodActivity.class);
                startActivity(intent);
            }
        });

        imgreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, Profile.class);
                startActivity(intent);
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        imgWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, Wishlist.class);
                startActivity(intent);
            }
        });

        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, Notification.class);
                startActivity(intent);
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        initFoodMenu();
    }



    private void initFoodMenu() {
        DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference("Food");

        recyclerView = findViewById(R.id.view3);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<FoodModel> foodList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodModel food = snapshot.getValue(FoodModel.class);
                    if (food != null) {
                        foodList.add(food);
                    }
                }
                if (!foodList.isEmpty()) {
                    FoodAdapter adapter = new FoodAdapter(foodList, FoodActivity.this);
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
