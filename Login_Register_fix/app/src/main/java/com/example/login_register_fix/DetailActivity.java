package com.example.login_register_fix;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.login_register_fix.BottomNavBar.Notification;
import com.example.login_register_fix.Model.BreakModel;
import com.example.login_register_fix.Model.ClothesModel;
import com.example.login_register_fix.Model.DinnerModel;
import com.example.login_register_fix.Model.DrinkModel;
import com.example.login_register_fix.Model.FastfoodModel;
import com.example.login_register_fix.Model.FoodModel;
import com.example.login_register_fix.Model.LunchModel;
import com.example.login_register_fix.Model.PopularModel;
import com.example.login_register_fix.Model.StationeryModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {
    TextView nameTxt, priceTxt, desTxt, rateTxt, totalTxt, txtNum;
    RatingBar ratingBar;
    ImageView backBtn, pic, favBtn1, favBtn2, btnMinus, btnPlus;
    FoodModel objects;
    PopularModel objects2;
    DrinkModel objects3;
    ClothesModel objects4;
    StationeryModel objects5;
    BreakModel objects6;
    LunchModel objects7;
    DinnerModel objects8;
    FastfoodModel objects9;
    Button addBtn;
    int num = 0;
    boolean isFavorite;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://appbanhang-5d802-default-rtdb.firebaseio.com/");

    @Override
    protected void onStart() {
        super.onStart();

        if (objects != null) {
            // Check if item is favorite
            checkFavorite(objects);
        } else if (objects2 != null) {
            // Check if item is favorite for objects2
            checkFavorite(objects2);
        } else if (objects3 != null) {
            // Check if item is favorite for objects3
            checkFavorite(objects3);
        } else if (objects4 != null) {
            // Check if item is favorite for objects4
            checkFavorite(objects4);
        } else if (objects5 != null) {
            // Check if item is favorite for objects5
            checkFavorite(objects5);
            checkFavorite(objects5);
        }else if (objects6 != null) {
            // Check if item is favorite for objects6
            checkFavorite(objects6);
        }else if (objects7 != null) {
            // Check if item is favorite for objects7
            checkFavorite(objects7);
        }else if (objects8 != null) {
            // Check if item is favorite for objects8
            checkFavorite(objects8);
        }else if (objects9 != null) {
            // Check if item is favorite for objects9
            checkFavorite(objects9);
        }
    }

    private void checkFavorite(Object object) {
        String name = null;
        if (object instanceof FoodModel) {
            name = ((FoodModel) object).getName();
        } else if (object instanceof PopularModel) {
            name = ((PopularModel) object).getName();
        } else if (object instanceof DrinkModel) {
            name = ((DrinkModel) object).getName();
        } else if (object instanceof ClothesModel) {
            name = ((ClothesModel) object).getName();
        } else if (object instanceof StationeryModel) {
            name = ((StationeryModel) object).getName();
        } else if (object instanceof BreakModel) {
            name = ((BreakModel) object).getName();
        }else if (object instanceof LunchModel) {
            name = ((LunchModel) object).getName();
        }else if (object instanceof DinnerModel) {
            name = ((DinnerModel) object).getName();
        }else if (object instanceof FastfoodModel) {
            name = ((FastfoodModel) object).getName();
        }

        if (name != null) {
            //check if item is favorite
            databaseReference.child("Favorite").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        isFavorite = true;
                        favBtn1.setVisibility(View.GONE);
                        favBtn2.setVisibility(View.VISIBLE);
                    } else {
                        isFavorite = false;
                        favBtn2.setVisibility(View.GONE);
                        favBtn1.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            // Handle the case where the name is null
            // For example, display an error message or finish the activity
            Toast.makeText(this, "Object Name is Null ", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nameTxt = findViewById(R.id.nameTxt);
        priceTxt = findViewById(R.id.priceTxt);
        backBtn = findViewById(R.id.backBtn);
        pic = findViewById(R.id.pic);
        totalTxt = findViewById(R.id.totalTxt);
        favBtn1 = findViewById(R.id.favBtn1);
        favBtn2 = findViewById(R.id.favBtn2);
        ratingBar = findViewById(R.id.ratingBar);
        addBtn = findViewById(R.id.addbtn);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        txtNum = findViewById(R.id.txtNum);




        getIntentExtra();
        setVariable();

        favBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = true;
                favBtn1.setVisibility(View.GONE);
                favBtn2.setVisibility(View.VISIBLE);

                String name = null;
                String price = null;
                String image = null;
                String start = null;

                if(objects != null){
                    name = objects.getName();
                    price = objects.getPrice();
                    image = objects.getImage();
                    start = objects.getStart();
                } else if (objects2 != null) {
                    name = objects2.getName();
                    price = objects2.getPrice();
                    image = objects2.getImage();
                    start = objects2.getStart();
                } else if (objects3 != null) {
                    name = objects3.getName();
                    price = objects3.getPrice();
                    image = objects3.getImage();
                    start = objects3.getStart();
                } else if (objects4 != null ) {
                    name = objects4.getName();
                    price = objects4.getPrice();
                    image = objects4.getImage();
                    start = objects4.getStart();
                } else if (objects5 != null) {
                    name = objects5.getName();
                    price = objects5.getPrice();
                    image = objects5.getImage();
                    start = objects5.getStart();
                }else if (objects6 != null) {
                    name = objects6.getName();
                    price = objects6.getPrice();
                    image = objects6.getImage();
                    start = objects6.getStart();
                }else if (objects7 != null) {
                    name = objects7.getName();
                    price = objects7.getPrice();
                    image = objects7.getImage();
                    start = objects7.getStart();
                }else if (objects8 != null) {
                    name = objects8.getName();
                    price = objects8.getPrice();
                    image = objects8.getImage();
                    start = objects8.getStart();
                }else if (objects9 != null) {
                    name = objects9.getName();
                    price = objects9.getPrice();
                    image = objects9.getImage();
                    start = objects9.getStart();
                }

                String finalName = name;
                String finalPrice = price;
                String finalImage = image;
                String finalStart = start;
                databaseReference.child("Favorite").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child("Favorite").child(finalName).child("name").setValue(finalName);
                        databaseReference.child("Favorite").child(finalName).child("price").setValue(finalPrice);
                        databaseReference.child("Favorite").child(finalName).child("image").setValue(finalImage);
                        databaseReference.child("Favorite").child(finalName).child("start").setValue(finalStart);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DetailActivity.this, "Can not add Item to Wishlist", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        favBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = false;
                favBtn2.setVisibility(View.GONE);
                favBtn1.setVisibility(View.VISIBLE);

                String name = null;

                if(objects != null){
                    name = objects.getName();
                } else if (objects2 != null) {
                    name = objects2.getName();
                } else if (objects3 != null) {
                    name = objects3.getName();
                } else if (objects4 != null ) {
                    name = objects4.getName();
                } else if (objects5 != null) {
                    name = objects5.getName();
                }else if (objects6 != null) {
                    name = objects6.getName();
                }else if (objects7 != null) {
                    name = objects7.getName();
                }else if (objects8 != null) {
                    name = objects8.getName();
                }else if (objects9 != null) {
                    name = objects9.getName();
                }

                String finalName = name;

                databaseReference.child("Favorite").child(finalName).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // Xóa thành công
                                Toast.makeText(DetailActivity.this, "Item removed from Favorites", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Xóa thất bại
                                Toast.makeText(DetailActivity.this, "Failed to remove item from Favorites", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num > 0) {
                    num--;
                    txtNum.setText(num + "");

                    String priceString = "";

                    if (objects != null) {
                        priceString = objects.getPrice().replaceAll(",", "");
                    } else if (objects2 != null) {
                        priceString = objects2.getPrice().replaceAll(",", "");
                    } else if (objects3 != null) {
                        priceString = objects3.getPrice().replaceAll(",", "");
                    } else if (objects4 != null) {
                        priceString = objects4.getPrice().replaceAll(",", "");
                    } else if (objects5 != null) {
                        priceString = objects5.getPrice().replaceAll(",", "");
                    } else if (objects6 != null) {
                        priceString = objects6.getPrice().replaceAll(",", "");
                    } else if (objects7 != null) {
                        priceString = objects7.getPrice().replaceAll(",", "");
                    } else if (objects8 != null) {
                        priceString = objects8.getPrice().replaceAll(",", "");
                    } else if (objects9 != null) {
                        priceString = objects9.getPrice().replaceAll(",", "");
                    }

                    if (!priceString.isEmpty()) {
                        float price = Float.parseFloat(priceString);
                        totalTxt.setText((num * price) + "");
                    }

                }
                btnMinus.setEnabled(num > 0);
            }
        });


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(objects != null) {
                    num++;
                    txtNum.setText(num + "");
                    String priceString = objects.getPrice().replaceAll(",", "");
                    float price = Float.parseFloat(priceString);
                    totalTxt.setText((num * price) + "");
                    btnMinus.setEnabled(true);
                } else if (objects2 != null) {
                    num++;
                    txtNum.setText(num + "");
                    String priceString = objects2.getPrice().replaceAll(",", "");
                    float price = Float.parseFloat(priceString);
                    totalTxt.setText((num * price) + "");
                    btnMinus.setEnabled(true);
                } else if (objects3 != null) {
                    num++;
                    txtNum.setText(num + "");
                    String priceString = objects3.getPrice().replaceAll(",", "");
                    float price = Float.parseFloat(priceString);
                    totalTxt.setText((num * price) + "");
                    btnMinus.setEnabled(true);
                } else if (objects4 != null) {
                    num++;
                    txtNum.setText(num + "");
                    String priceString = objects4.getPrice().replaceAll(",", "");
                    float price = Float.parseFloat(priceString);
                    totalTxt.setText((num * price) + "");
                    btnMinus.setEnabled(true);
                } else if (objects5 != null) {
                    num++;
                    txtNum.setText(num + "");
                    String priceString = objects5.getPrice().replaceAll(",", "");
                    float price = Float.parseFloat(priceString);
                    totalTxt.setText((num * price) + "");
                    btnMinus.setEnabled(true);
                }else if (objects6 != null) {
                    num++;
                    txtNum.setText(num + "");
                    String priceString = objects6.getPrice().replaceAll(",", "");
                    float price = Float.parseFloat(priceString);
                    totalTxt.setText((num * price) + "");
                    btnMinus.setEnabled(true);
                }else if (objects7 != null) {
                    num++;
                    txtNum.setText(num + "");
                    String priceString = objects7.getPrice().replaceAll(",", "");
                    float price = Float.parseFloat(priceString);
                    totalTxt.setText((num * price) + "");
                    btnMinus.setEnabled(true);
                }else if (objects8 != null) {
                    num++;
                    txtNum.setText(num + "");
                    String priceString = objects8.getPrice().replaceAll(",", "");
                    float price = Float.parseFloat(priceString);
                    totalTxt.setText((num * price) + "");
                    btnMinus.setEnabled(true);
                }else if (objects9 != null) {
                    num++;
                    txtNum.setText(num + "");
                    String priceString = objects9.getPrice().replaceAll(",", "");
                    float price = Float.parseFloat(priceString);
                    totalTxt.setText((num * price) + "");
                    btnMinus.setEnabled(true);
                }
            }
        });

        //Button back
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Button add
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = null;
                String price = null;
                String image = null;
                String start = null;

                if (objects instanceof FoodModel) {
                    FoodModel foodModel = (FoodModel) objects;
                    name = foodModel.getName();
                    price = foodModel.getPrice();
                    image = foodModel.getImage();
                    start = foodModel.getStart();
                } else if (objects2 instanceof PopularModel) {
                    PopularModel popularModel = (PopularModel) objects2;
                    name = popularModel.getName();
                    price = popularModel.getPrice();
                    image = popularModel.getImage();
                    start = popularModel.getStart();
                } else if (objects3 instanceof DrinkModel) {
                    DrinkModel drinkModel = (DrinkModel) objects3;
                    name = drinkModel.getName();
                    price = drinkModel.getPrice();
                    image = drinkModel.getImage();
                    start = drinkModel.getStart();
                } else if (objects4 instanceof ClothesModel) {
                    ClothesModel clothesModel = (ClothesModel) objects4;
                    name = clothesModel.getName();
                    price = clothesModel.getPrice();
                    image = clothesModel.getImage();
                    start = clothesModel.getStart();
                } else if (objects5 instanceof StationeryModel) {
                    StationeryModel stationeryModel = (StationeryModel) objects5;
                    name = stationeryModel.getName();
                    price = stationeryModel.getPrice();
                    image = stationeryModel.getImage();
                    start = stationeryModel.getStart();
                }else if (objects6 instanceof BreakModel) {
                    BreakModel breakModel = (BreakModel) objects6;
                    name = breakModel.getName();
                    price = breakModel.getPrice();
                    image = breakModel.getImage();
                    start = breakModel.getStart();
                }else if (objects7 instanceof LunchModel) {
                    LunchModel lunchModel = (LunchModel) objects7;
                    name = lunchModel.getName();
                    price = lunchModel.getPrice();
                    image = lunchModel.getImage();
                    start = lunchModel.getStart();
                }else if (objects8 instanceof DinnerModel) {
                    DinnerModel dinnerModel = (DinnerModel) objects8;
                    name = dinnerModel.getName();
                    price = dinnerModel.getPrice();
                    image = dinnerModel.getImage();
                    start = dinnerModel.getStart();
                }else if (objects9 instanceof FastfoodModel) {
                    FastfoodModel fastfoodModel = (FastfoodModel) objects9;
                    name = fastfoodModel.getName();
                    price = fastfoodModel.getPrice();
                    image = fastfoodModel.getImage();
                    start = fastfoodModel.getStart();
                }


                String finalName = name;
                String finalPrice = price;
                String finalImage = image;
                String finalStart = start;


                if (finalName != null && finalPrice != null && finalImage != null && finalStart != null) {
                    DatabaseReference cartRef = databaseReference.child("Cart");

                    // Kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
                    cartRef.child(finalName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // Sản phẩm đã tồn tại trong giỏ hàng, cập nhật thông tin
                                int currentNum = snapshot.child("num").getValue(Integer.class);
                                float currentTotal = snapshot.child("total").getValue(Float.class);

                                int newNum = currentNum + num;
                                String priceString = finalPrice.replaceAll(",", "");
                                float newTotal = currentTotal + (num * Float.parseFloat(priceString));

                                snapshot.getRef().child("num").setValue(newNum);
                                snapshot.getRef().child("total").setValue(newTotal);

                                Toast.makeText(DetailActivity.this, "Add Item to Cart Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                // Sản phẩm chưa tồn tại trong giỏ hàng, thêm một mục mới
                                String priceString = finalPrice.replaceAll(",", "");
                                float total = num * Float.parseFloat(priceString);
                                DatabaseReference newItemRef = cartRef.child(finalName);
                                newItemRef.child("name").setValue(finalName);
                                newItemRef.child("price").setValue(finalPrice);
                                newItemRef.child("image").setValue(finalImage);
                                newItemRef.child("start").setValue(finalStart);
                                newItemRef.child("num").setValue(num);
                                newItemRef.child("total").setValue(total);

                                Toast.makeText(DetailActivity.this, "Add Item to Cart Successful", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(DetailActivity.this, "Can not add Item to Cart", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Handle the case where one of the properties is null
                    // For example, display an error message
                    Toast.makeText(DetailActivity.this, "Item information is incomplete", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void setVariable() {
        backBtn.setOnClickListener(v -> finish());

        if (objects != null) {
            Glide.with(DetailActivity.this)
                    .load(objects.getImage())
                    .into(pic);
            priceTxt.setText(objects.getPrice());
            nameTxt.setText(objects.getName());
            String priceString = objects.getPrice().replaceAll(",", "");
            float price = Float.parseFloat(priceString);
            totalTxt.setText((num * price) + "");
        } else if (objects2 != null) {
            Glide.with(DetailActivity.this)
                    .load(objects2.getImage())
                    .into(pic);
            priceTxt.setText(objects2.getPrice());
            nameTxt.setText(objects2.getName());
            String priceString = objects2.getPrice().replaceAll(",", "");
            float price = Float.parseFloat(priceString);
            totalTxt.setText((num * price) + "");
        } else if (objects3 != null) {
            // Hiển thị thông tin từ objects3 (DrinkModel)
            Glide.with(DetailActivity.this)
                    .load(objects3.getImage())
                    .into(pic);
            priceTxt.setText(objects3.getPrice());
            nameTxt.setText(objects3.getName());
            String priceString = objects3.getPrice().replaceAll(",", "");
            float price = Float.parseFloat(priceString);
            totalTxt.setText((num * price) + "");
        }
        else if (objects4 != null) {
            // Hiển thị thông tin từ objects4 (ClothesModel)
            Glide.with(DetailActivity.this)
                    .load(objects4.getImage())
                    .into(pic);
            priceTxt.setText(objects4.getPrice());
            nameTxt.setText(objects4.getName());
            String priceString = objects4.getPrice().replaceAll(",", "");
            float price = Float.parseFloat(priceString);
            totalTxt.setText((num * price) + "");
        } else if (objects5 != null) {
            // Hiển thị thông tin từ objects5 (StationeryModel)
            Glide.with(DetailActivity.this)
                    .load(objects5.getImage())
                    .into(pic);
            priceTxt.setText(objects5.getPrice());
            nameTxt.setText(objects5.getName());
            String priceString = objects5.getPrice().replaceAll(",", "");
            float price = Float.parseFloat(priceString);
            totalTxt.setText((num * price) + "");
        }else if (objects6 != null) {
            // Hiển thị thông tin từ objects6 (BreakModel)
            Glide.with(DetailActivity.this)
                    .load(objects6.getImage())
                    .into(pic);
            priceTxt.setText(objects6.getPrice());
            nameTxt.setText(objects6.getName());
            String priceString = objects6.getPrice().replaceAll(",", "");
            float price = Float.parseFloat(priceString);
            totalTxt.setText((num * price) + "");
        }else if (objects7 != null) {
            // Hiển thị thông tin từ objects7 (LunchModel)
            Glide.with(DetailActivity.this)
                    .load(objects7.getImage())
                    .into(pic);
            priceTxt.setText(objects7.getPrice());
            nameTxt.setText(objects7.getName());
            String priceString = objects7.getPrice().replaceAll(",", "");
            float price = Float.parseFloat(priceString);
            totalTxt.setText((num * price) + "");
        }else if (objects8 != null) {
            // Hiển thị thông tin từ objects8 (DinnerModel)
            Glide.with(DetailActivity.this)
                    .load(objects8.getImage())
                    .into(pic);
            priceTxt.setText(objects8.getPrice());
            nameTxt.setText(objects8.getName());
            String priceString = objects8.getPrice().replaceAll(",", "");
            float price = Float.parseFloat(priceString);
            totalTxt.setText((num * price) + "");
        }else if (objects9 != null) {
            // Hiển thị thông tin từ objects9 (FastfoodModel)
            Glide.with(DetailActivity.this)
                    .load(objects9.getImage())
                    .into(pic);
            priceTxt.setText(objects9.getPrice());
            nameTxt.setText(objects9.getName());
            String priceString = objects9.getPrice().replaceAll(",", "");
            float price = Float.parseFloat(priceString);
            totalTxt.setText((num * price) + "");
        }
    }

    private void getIntentExtra() {
        objects = (FoodModel) getIntent().getSerializableExtra("objects");
        objects2 = (PopularModel) getIntent().getSerializableExtra("objects2");
        objects3 = (DrinkModel) getIntent().getSerializableExtra("objects3");
        objects4 = (ClothesModel) getIntent().getSerializableExtra("objects4");
        objects5 = (StationeryModel) getIntent().getSerializableExtra("objects5");
        objects6 = (BreakModel) getIntent().getSerializableExtra("objects6");
        objects7 = (LunchModel) getIntent().getSerializableExtra("objects7");
        objects8 = (DinnerModel) getIntent().getSerializableExtra("objects8");
        objects9 = (FastfoodModel) getIntent().getSerializableExtra("objects9");
    }

}