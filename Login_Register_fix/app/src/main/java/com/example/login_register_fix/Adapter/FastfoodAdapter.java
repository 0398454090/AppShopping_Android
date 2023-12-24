package com.example.login_register_fix.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.login_register_fix.DetailActivity;
import com.example.login_register_fix.Model.FastfoodModel;
import com.example.login_register_fix.R;

import java.util.ArrayList;

public class FastfoodAdapter extends RecyclerView.Adapter<FastfoodAdapter.ViewHolder> {
    ArrayList<FastfoodModel> arrayList;
    Context context;

    public FastfoodAdapter(ArrayList<FastfoodModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FastfoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_product, parent, false);
        return new FastfoodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FastfoodAdapter.ViewHolder holder, int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.price.setText(arrayList.get(position).getPrice());
        holder.start.setText(arrayList.get(position).getStart());

        Glide.with(context)
                .load(arrayList.get(position).getImage())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.image);

        holder.itemView.setOnClickListener(v ->  {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("objects9", arrayList.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price, start;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_img);
            name = itemView.findViewById(R.id.product_Name);
            price = itemView.findViewById(R.id.product_price);
            start = itemView.findViewById(R.id.start);
        }
    }
}
