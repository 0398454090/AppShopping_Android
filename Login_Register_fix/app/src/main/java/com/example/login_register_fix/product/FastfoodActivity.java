package com.example.login_register_fix.product;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.login_register_fix.Adapter.DinnerAdapter;
import com.example.login_register_fix.Adapter.FastfoodAdapter;
import com.example.login_register_fix.Model.DinnerModel;
import com.example.login_register_fix.Model.FastfoodModel;
import com.example.login_register_fix.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FastfoodActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView imgreturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastfood);

        imgreturn = findViewById(R.id.imgreturn);

        imgreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FastfoodActivity.this, FoodActivity.class);
                startActivity(intent);
            }
        });

        initFastfoodMenu();
    }

    private void initFastfoodMenu() {
        DatabaseReference fastfoodRef = FirebaseDatabase.getInstance().getReference("Fastfood");

        recyclerView = findViewById(R.id.viewFast);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        fastfoodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<FastfoodModel> fastfoodList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FastfoodModel fastfoodItem = snapshot.getValue(FastfoodModel.class);
                    if (fastfoodItem != null) {
                        fastfoodList.add(fastfoodItem);
                    }
                }
                if (!fastfoodList.isEmpty()) {
                    FastfoodAdapter adapter = new FastfoodAdapter(fastfoodList, FastfoodActivity.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d(TAG, "No fast food data found");
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
