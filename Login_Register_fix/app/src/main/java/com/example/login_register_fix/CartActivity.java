package com.example.login_register_fix;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_register_fix.Adapter.CartAdapter;
import com.example.login_register_fix.BottomNavBar.Notification;
import com.example.login_register_fix.BottomNavBar.Profile;
import com.example.login_register_fix.BottomNavBar.Wishlist;
import com.example.login_register_fix.Model.CartModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imageView;
    TextView txtTotal, txtTransport, txtTax, txtTotalAll;
    EditText txtCoupon;
    Button btnCoupon, btnOrder, btnCash, btnOtherPayment;
    ImageView backBtn;
    boolean isCash = false;
    boolean isOtherPayment = false;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://appbanhang-5d802-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        txtCoupon = findViewById(R.id.txtCoupon);
        btnCoupon = findViewById(R.id.btnCoupon);
        btnOrder = findViewById(R.id.btnOrder);
        backBtn = findViewById(R.id.backBtn);
        txtTotal = findViewById(R.id.txtTotal);
        txtTransport = findViewById(R.id.txtTransport);
        txtTax = findViewById(R.id.txtTax);
        txtTotalAll = findViewById(R.id.txtTotalAll);
        btnCash = findViewById(R.id.btnCash);
        btnOtherPayment = findViewById(R.id.btnOtherPayment);



        btnCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCash = true;
                isOtherPayment = false;
                //change color backgroundTint to red
                btnCash.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                //change color btnOtherPayment to grey
                btnOtherPayment.setBackgroundTintList(getResources().getColorStateList(R.color.grey));
            }
        });

        btnOtherPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCash = false;
                isOtherPayment = true;
                //change color backgroundTint to red
                btnOtherPayment.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                //change color Cash to grey
                btnCash.setBackgroundTintList(getResources().getColorStateList(R.color.grey));
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị dialog xác nhận trước khi đặt hàng
                showConfirmationDialog();
            }
        });





        //btnBack
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        //BottomNavBar
        //Chuyen den Profile
        imageView = findViewById(R.id.imgProfile);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(CartActivity.this,  Profile.class);
                startActivity(intent);
            }
        });

        //Chuyen den gio hang
        imageView = findViewById(R.id.imgCart);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(CartActivity.this,  CartActivity.class);
                startActivity(intent);
            }
        });

        //Chuyen den wishlist
        imageView = findViewById(R.id.imgWishlist);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(CartActivity.this,  Wishlist.class);
                startActivity(intent);
            }
        });

        //Chuyen den Notification
        imageView = findViewById(R.id.imgNotification);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(CartActivity.this,  Notification.class);
                startActivity(intent);
            }
        });

        //Chuyen den home
        imageView = findViewById(R.id.imgHome);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(CartActivity.this,  com.example.login_register_fix.HomeActivity.class);
                startActivity(intent);
            }
        });

        initCartMenu();
    }

    private void initCartMenu() {
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Cart");

        recyclerView = findViewById(R.id.cartView);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<CartModel> cartlist = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CartModel cart = snapshot.getValue(CartModel.class);
                    if (cart != null) {
                        cartlist.add(cart);
                    }
                }
                if (!cartlist.isEmpty()) {
                    CartAdapter adapter = new CartAdapter(cartlist, CartActivity.this);
                    recyclerView.setAdapter(adapter);

                    // Gọi hàm để tính toán và cập nhật tổng giá trị
                    calculateTotals(cartlist);
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

    private void calculateTotals(ArrayList<CartModel> cartlist) {
        float total = 0;
        float transport = 0;
        float tax = 0;

        for (CartModel cartItem : cartlist) {
            float price = Float.parseFloat(cartItem.getPrice().replaceAll(",", ""));
            total += cartItem.getNum() * price;
        }

        // Set giá trị cho txtTotal
        txtTotal.setText(String.valueOf(total));
        txtTax.setText(String.valueOf(tax));
        txtTransport.setText(String.valueOf(transport));

        // Cập nhật tổng cộng
        updateSummary(total, transport, tax);
    }
    private void updateSummary(float total, float transport, float tax) {
        DatabaseReference summaryRef = FirebaseDatabase.getInstance().getReference("Summary");

        // Cập nhật tổng vào Firebase
        summaryRef.child("total").setValue(String.valueOf(total));

        // Tính toán và cập nhật txtTotalAll
        float totalAll = total + tax + transport ; // Bạn có thể thêm các phần khác vào đây
        txtTotalAll.setText(String.valueOf(totalAll));
    }

    private void resetUI() {
        // Có thể thêm các thay đổi giao diện khác tại đây nếu cần
        txtTotal.setText("0");
        txtTax.setText("0");
        txtTransport.setText("0");
        txtTotalAll.setText("0");
        // Cập nhật giao diện Recyclerview
        initCartMenu();
    }
    // Hàm hiển thị dialog xác nhận
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setTitle("Xác nhận đặt hàng");
        builder.setMessage("Bạn có chắc muốn đặt hàng?");

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lấy thời gian hiện tại để tạo đơn hàng có tên duy nhất
                String orderKey = String.valueOf(System.currentTimeMillis());

                // Lấy danh sách sản phẩm từ Cart
                DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Cart");
                cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<String> orderedItems = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            CartModel cartItem = snapshot.getValue(CartModel.class);
                            if (cartItem != null) {
                                orderedItems.add(cartItem.getName());
                            }
                        }

                        // Tạo bộ dữ liệu cho đơn hàng
                        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("Orders").child(orderKey);
                        orderRef.child("items").setValue(orderedItems);
                        orderRef.child("totalAll").setValue(txtTotalAll.getText().toString());
                        orderRef.child("tax").setValue(txtTax.getText().toString());
                        orderRef.child("transport").setValue(txtTransport.getText().toString());

                        // Xóa toàn bộ dữ liệu trong Cart
                        cartRef.removeValue();

                        // Đặt trạng thái của btnCash và btnOtherPayment về false
                        isCash = false;
                        isOtherPayment = false;

                        // Đặt màu sắc cho btnCash và btnOtherPayment về mặc định
                        btnCash.setBackgroundTintList(getResources().getColorStateList(R.color.grey));
                        btnOtherPayment.setBackgroundTintList(getResources().getColorStateList(R.color.grey));

                        Toast.makeText(CartActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();

                        // Gọi hàm để làm mới và cập nhật giao diện
                        resetUI();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });

        builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hủy bỏ đặt hàng, không thực hiện bước tiếp theo
                dialog.dismiss();
            }
        });

        // Hiển thị dialog
        builder.create().show();
    }

}