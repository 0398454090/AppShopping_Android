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

import com.example.login_register_fix.Adapter.LunchAdapter;
import com.example.login_register_fix.Model.LunchModel;
import com.example.login_register_fix.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LunchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView imgreturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);

        imgreturn = findViewById(R.id.imgreturn);

        imgreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LunchActivity.this, FoodActivity.class);
                startActivity(intent);
            }
        });

        initLunchMenu();
    }

    private void initLunchMenu() {
        DatabaseReference lunchRef = FirebaseDatabase.getInstance().getReference("Lunch");

        recyclerView = findViewById(R.id.viewLunch);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        lunchRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<LunchModel> lunchList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    LunchModel lunchItem = snapshot.getValue(LunchModel.class);
                    if (lunchItem != null) {
                        lunchList.add(lunchItem);
                    }
                }
                if (!lunchList.isEmpty()) {
                    LunchAdapter adapter = new LunchAdapter(lunchList, LunchActivity.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d(TAG, "No lunch data found");
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
