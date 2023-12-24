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

import com.example.login_register_fix.Adapter.StationeryAdapter;
import com.example.login_register_fix.BottomNavBar.Notification;
import com.example.login_register_fix.BottomNavBar.Profile;
import com.example.login_register_fix.BottomNavBar.Wishlist;
import com.example.login_register_fix.CartActivity;
import com.example.login_register_fix.HomeActivity;
import com.example.login_register_fix.Model.StationeryModel;
import com.example.login_register_fix.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;

public class StationeryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView imgreturn, imgProfile, imgCart, imgWishlist, imgNotification, imgHome;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationery);

        imgreturn = findViewById(R.id.imgreturn);
        imgProfile = findViewById(R.id.imgProfile);
        imgCart = findViewById(R.id.imgCart);
        imgWishlist = findViewById(R.id.imgWishlist);
        imgNotification = findViewById(R.id.imgNotification);
        imgHome = findViewById(R.id.imgHome);

        imgreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StationeryActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StationeryActivity.this, Profile.class);
                startActivity(intent);
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StationeryActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        imgWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StationeryActivity.this, Wishlist.class);
                startActivity(intent);
            }
        });

        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StationeryActivity.this, Notification.class);
                startActivity(intent);
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StationeryActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        initStationeryMenu();
    }

    private void initStationeryMenu() {
        DatabaseReference stationeryRef = FirebaseDatabase.getInstance().getReference("Stationary");

        recyclerView = findViewById(R.id.viewStationery);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        stationeryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<StationeryModel> stationeryList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    StationeryModel stationery = snapshot.getValue(StationeryModel.class);
                    if (stationery != null) {
                        stationeryList.add(stationery);
                    }
                }
                if (!stationeryList.isEmpty()) {
                    StationeryAdapter adapter = new StationeryAdapter(stationeryList, StationeryActivity.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d(TAG, "No stationery data found");
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
