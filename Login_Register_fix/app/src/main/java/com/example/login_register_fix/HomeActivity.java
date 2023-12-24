package com.example.login_register_fix;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.login_register_fix.Adapter.PopularAdapter;
import com.example.login_register_fix.BottomNavBar.Notification;
import com.example.login_register_fix.BottomNavBar.Profile;
import com.example.login_register_fix.BottomNavBar.Wishlist;
import com.example.login_register_fix.Model.PopularModel;
import com.example.login_register_fix.product.ClouthesActivity;
import com.example.login_register_fix.product.DrinkActivity;
import com.example.login_register_fix.product.FoodActivity;
import com.example.login_register_fix.product.StationeryActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private EditText searchProducts;

    private ImageView imgFood,imgDrink, imgClothes, imgStationery, imgGioHang, imgProfile, imgCart, imgWishlist, imgNotification, imgHome;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imgFood = findViewById(R.id.imgFood);
        imgDrink = findViewById(R.id.imgDrink);
        imgClothes = findViewById(R.id.imgClothes);
        imgStationery = findViewById(R.id.imgStationery);
        imgGioHang = findViewById(R.id.giohang);
        imgProfile = findViewById(R.id.imgProfile);
        imgCart = findViewById(R.id.imgCart);
        imgWishlist = findViewById(R.id.imgWishlist);
        imgNotification = findViewById(R.id.imgNotification);
        imgHome = findViewById(R.id.imgHome);


        searchProducts = findViewById(R.id.searchProducts);

        //lowercase name
        updateLowercaseNames();



        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.img1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img2, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        if (user == null && googleSignInAccount == null ){
            Intent intent = new Intent(getApplicationContext(), com.example.login_register_fix.Login.class);
            startActivity(intent);
            finish();
        }

        //today's suggestion
        initRecyclerViewSugestion();


        //Search bar
        searchProducts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Trước khi thay đổi văn bản
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Trong quá trình thay đổi văn bản
                searchDatalist(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Sau khi văn bản đã thay đổi
            }
        });






        //chuyen den trang FoodActivity
        imgFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FoodActivity.class);
                startActivity(intent);
            }
        });

        //chuyen den trang DrinkActivity
        imgDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DrinkActivity.class);
                startActivity(intent);
            }
        });

        imgClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ClouthesActivity.class);
                startActivity(intent);
            }
        });

        imgStationery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, StationeryActivity.class);
                startActivity(intent);
            }
        });

        imgGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Profile.class);
                startActivity(intent);
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        imgWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Wishlist.class);
                startActivity(intent);
            }
        });

        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Notification.class);
                startActivity(intent);
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }



    //today's suggestion
    private void initRecyclerViewSugestion() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("popularproducts");

        recyclerView = findViewById(R.id.view1);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PopularModel> popularList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PopularModel popular = snapshot.getValue(PopularModel.class);
                    if (popular != null) {
                        popularList.add(popular);
                    }
                }
                if (!popularList.isEmpty()) {
                    PopularAdapter adapter = new PopularAdapter(popularList, HomeActivity.this);
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

    //Search bar
    private void searchDatalist(String s) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("popularproducts");

        String lowercaseQuery = s.toLowerCase();

        myRef.orderByChild("lowercasename").startAt(s).endAt(lowercaseQuery + "\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PopularModel> popularList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PopularModel popular = snapshot.getValue(PopularModel.class);
                    if (popular != null) {
                        popularList.add(popular);
                    }
                }

                if (!popularList.isEmpty()) {
                    PopularAdapter adapter = (PopularAdapter) recyclerView.getAdapter();
                    adapter.updateData(popularList);
                } else {
                    Log.d(TAG, "No matching data found");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    private void updateLowercaseNames() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("popularproducts");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy tên sản phẩm
                    String productName = snapshot.child("name").getValue(String.class);

                    // Nếu tên sản phẩm không rỗng và chưa có trường lowercasename
                    if (!TextUtils.isEmpty(productName) && !snapshot.hasChild("lowercasename")) {
                        // Chuyển đổi tên thành chữ thường
                        String lowercaseName = productName.toLowerCase();

                        // Cập nhật dữ liệu
                        snapshot.child("lowercasename").getRef().setValue(lowercaseName);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }



}