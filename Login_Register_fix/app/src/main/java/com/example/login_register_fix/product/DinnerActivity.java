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
import com.example.login_register_fix.Adapter.LunchAdapter;
import com.example.login_register_fix.Model.DinnerModel;
import com.example.login_register_fix.Model.LunchModel;
import com.example.login_register_fix.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DinnerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView imgreturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);

        imgreturn = findViewById(R.id.imgreturn);

        imgreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DinnerActivity.this, FoodActivity.class);
                startActivity(intent);
            }
        });

        initDinnerMenu();
    }

    private void initDinnerMenu() {
        DatabaseReference dinnerRef = FirebaseDatabase.getInstance().getReference("Dinner");

        recyclerView = findViewById(R.id.viewDinner);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        dinnerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<DinnerModel> dinnerList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DinnerModel dinnerItem = snapshot.getValue(DinnerModel.class);
                    if (dinnerItem != null) {
                        dinnerList.add(dinnerItem);
                    }
                }
                if (!dinnerList.isEmpty()) {
                    DinnerAdapter adapter = new DinnerAdapter(dinnerList, DinnerActivity.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d(TAG, "No dinner data found");
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