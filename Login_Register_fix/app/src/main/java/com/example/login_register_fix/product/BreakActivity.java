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

import com.example.login_register_fix.Adapter.BreakAdapter;
import com.example.login_register_fix.Adapter.StationeryAdapter;
import com.example.login_register_fix.HomeActivity;
import com.example.login_register_fix.Model.BreakModel;
import com.example.login_register_fix.Model.StationeryModel;
import com.example.login_register_fix.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BreakActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView imgreturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break);

        imgreturn = findViewById(R.id.imgreturn);

        imgreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BreakActivity.this, FoodActivity.class);
                startActivity(intent);
            }
        });

        initBreakMenu();
    }

    private void initBreakMenu() {
        DatabaseReference breakRef = FirebaseDatabase.getInstance().getReference("Breakfast");

        recyclerView = findViewById(R.id.viewBreak);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        breakRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<BreakModel> breakList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BreakModel breakItem = snapshot.getValue(BreakModel.class);
                    if (breakItem != null) {
                        breakList.add(breakItem);
                    }
                }
                if (!breakList.isEmpty()) {
                    BreakAdapter adapter = new BreakAdapter(breakList, BreakActivity.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d(TAG, "No breakfast data found");
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
