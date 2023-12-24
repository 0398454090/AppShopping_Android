package com.example.login_register_fix.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.login_register_fix.Model.CartModel;
import com.example.login_register_fix.Model.FoodModel;
import com.example.login_register_fix.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    ArrayList<CartModel>  arrayList;
    Context context;

    public CartAdapter(ArrayList<CartModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.price.setText(String.valueOf(arrayList.get(position).getPrice()));
        holder.num.setText(String.valueOf(arrayList.get(position).getNum()));


        Glide.with(context)
                .load(arrayList.get(position).getImage())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.image);

        // Thiết lập sự kiện onClick cho minus
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCartItem(holder, position, -1); // Trừ đi 1
            }
        });

        // Thiết lập sự kiện onClick cho plus
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCartItem(holder, position, 1); // Cộng thêm 1
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, minus, plus;
        TextView name, price, num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_img);
            name = itemView.findViewById(R.id.product_Name);
            price = itemView.findViewById(R.id.product_price);
            minus = itemView.findViewById(R.id.minus);
            plus = itemView.findViewById(R.id.plus);
            num = itemView.findViewById(R.id.num);
        }
    }

    private void updateCartItem(ViewHolder holder, int position, int change) {
        int currentNum = arrayList.get(position).getNum();
        currentNum += change;
        arrayList.get(position).setNum(currentNum);
        holder.num.setText(String.valueOf(currentNum));

        // Gọi hàm để cập nhật dữ liệu trong Firebase
        updateFirebaseCartItem(position, currentNum);
    }
    private void updateFirebaseCartItem(int position, int newNum) {
        CartModel cartItem = arrayList.get(position);

        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Cart");
        DatabaseReference itemRef = cartRef.child(cartItem.getName());

        // Cập nhật dữ liệu trong Firebase
        itemRef.child("num").setValue(newNum);

        float price = Float.parseFloat(cartItem.getPrice().replaceAll(",", ""));
        float newTotal = newNum * price;
        itemRef.child("total").setValue(newTotal);
    }


}
