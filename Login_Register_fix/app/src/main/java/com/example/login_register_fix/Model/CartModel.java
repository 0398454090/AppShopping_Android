package com.example.login_register_fix.Model;

import android.widget.ImageView;

import java.io.Serializable;

public class CartModel implements Serializable {
    String name;
    String price;
    String image;
    ImageView minus, plus;
    int num;

    public CartModel() {
    }

    public CartModel(String name, String price, String image, int num, ImageView minus, ImageView plus) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.num = num;
        this.minus = minus;
        this.plus = plus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
