package com.example.login_register_fix.product;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.login_register_fix.Adapter.ClothesAdapter;
import com.example.login_register_fix.Adapter.SliderAdapter;
import com.example.login_register_fix.BottomNavBar.Notification;
import com.example.login_register_fix.BottomNavBar.Profile;
import com.example.login_register_fix.BottomNavBar.Wishlist;
import com.example.login_register_fix.CartActivity;
import com.example.login_register_fix.HomeActivity;
import com.example.login_register_fix.Model.ClothesModel;
import com.example.login_register_fix.Model.SliderItem;
import com.example.login_register_fix.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClouthesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ViewPager2 viewPager2;
    private Handler sliderHander = new Handler();
    private ImageView imgreturn, imgProfile, imgCart, imgWishlist, imgNotification, imgHome;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clouthes);

        imgreturn = findViewById(R.id.imgreturn);
        imgProfile = findViewById(R.id.imgProfile);
        imgCart = findViewById(R.id.imgCart);
        imgWishlist = findViewById(R.id.imgWishlist);
        imgNotification = findViewById(R.id.imgNotification);
        imgHome = findViewById(R.id.imgHome);


        /////////////////slide
        viewPager2 = findViewById(R.id.viewPaper2);

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.clothes1));
        sliderItems.add(new SliderItem(R.drawable.clothes2));
        sliderItems.add(new SliderItem(R.drawable.clothes3));
        sliderItems.add(new SliderItem(R.drawable.clothes4));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.2f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHander.removeCallbacks(sliderRunnable);
                sliderHander.postDelayed(sliderRunnable, 3000);

            }
        });


        imgreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClouthesActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClouthesActivity.this, Profile.class);
                startActivity(intent);
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClouthesActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        imgWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClouthesActivity.this, Wishlist.class);
                startActivity(intent);
            }
        });

        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClouthesActivity.this, Notification.class);
                startActivity(intent);
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClouthesActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        initClothesMenu();
    }

    private void initClothesMenu() {
        DatabaseReference clothesRef = FirebaseDatabase.getInstance().getReference("ChillClothes");

        recyclerView = findViewById(R.id.viewClothes);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        clothesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ClothesModel> clothesList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ClothesModel clothes = snapshot.getValue(ClothesModel.class);
                    if (clothes != null) {
                        clothesList.add(clothes);
                    }
                }
                if (!clothesList.isEmpty()) {
                    ClothesAdapter adapter = new ClothesAdapter(clothesList, ClouthesActivity.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d(TAG, "No clothes data found");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHander.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHander.postDelayed(sliderRunnable, 3000);
    }
}
