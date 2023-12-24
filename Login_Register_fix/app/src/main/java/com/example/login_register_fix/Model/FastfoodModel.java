package com.example.login_register_fix.Model;

import java.io.Serializable;

public class FastfoodModel implements Serializable {
    String name;
    String price;
    String image;
    String start;

    public FastfoodModel() {
    }

    public FastfoodModel(String name, String price, String image, String start) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.start = start;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
