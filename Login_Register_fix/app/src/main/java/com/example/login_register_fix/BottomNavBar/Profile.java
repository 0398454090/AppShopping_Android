package com.example.login_register_fix.BottomNavBar;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.login_register_fix.CartActivity;
import com.example.login_register_fix.Login;
import com.example.login_register_fix.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    ImageButton btnEdit;
    TextView lblName, lblPhone, lblAddress, lblMail, lblURL, lblLevel, lblUserName;
    ImageView imgAvatar, imageView;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button btnLogout;
    FirebaseAuth auth;
    FirebaseUser user,currentUser;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://appbanhang-5d802-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnEdit = findViewById(R.id.btnEdit);
        lblLevel = findViewById(R.id.lblLevel);
        lblName = findViewById(R.id.lblName);
        lblPhone = findViewById(R.id.lblPhone);
        lblAddress = findViewById(R.id.lblAddress);
        lblMail = findViewById(R.id.lblMail);
        lblURL = findViewById(R.id.lblURL);
        imgAvatar = findViewById(R.id.imgAvatar);
        lblUserName = findViewById(R.id.lblUserName);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);




        btnLogout = findViewById(R.id.btn_Profile_Logout);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();






        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        //Set profile text
        setTextForProfile();

//        if (user == null && googleSignInAccount == null){
//            Intent intent = new Intent(getApplicationContext(), com.example.login_register_fix.Login.class);
//            startActivity(intent);
//            finish();
//        }
//        else if (user != null){
//            lblMail.setText(user.getEmail());
//            lblName.setText(user.getDisplayName());
//        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutGmail();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();

            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Edit_Profile.class);
                intent.putExtra("level", lblLevel.getText().toString());
                intent.putExtra("username", lblUserName.getText().toString());
                intent.putExtra("name", lblName.getText().toString());
                intent.putExtra("phone", lblPhone.getText().toString());
                intent.putExtra("address", lblAddress.getText().toString());
                intent.putExtra("mail", lblMail.getText().toString());
                intent.putExtra("url", lblURL.getText().toString());
                startActivityIfNeeded(intent,1);

            }
        });



        //BottomNavBar
        //Chuyen den Profile
        imageView = findViewById(R.id.imgProfile);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(Profile.this,  Profile.class);
                startActivity(intent);
            }
        });

        //Chuyen den gio hang
        imageView = findViewById(R.id.imgCart);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(Profile.this,  CartActivity.class);
                startActivity(intent);
            }
        });

        //Chuyen den wishlist
        imageView = findViewById(R.id.imgWishlist);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(Profile.this,  Wishlist.class);
                startActivity(intent);
            }
        });

        //Chuyen den Notification
        imageView = findViewById(R.id.imgNotification);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(Profile.this,  Notification.class);
                startActivity(intent);
            }
        });

        //Chuyen den home
        imageView = findViewById(R.id.imgHome);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến FoodActivity khi người dùng nhấn vào
                Intent intent = new Intent(Profile.this,  com.example.login_register_fix.HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setTextForProfile() {
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(user != null){
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String email = currentUser.getEmail();
            int index = email.indexOf("@");
            String uid = email.substring(0, index);

            DatabaseReference userRef = databaseReference.child("Account").child(uid);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Lấy dữ liệu từ dataSnapshot và set text cho các TextView tương ứng
                        String level = dataSnapshot.child("Level").getValue(String.class);
                        String name = dataSnapshot.child("FullName").getValue(String.class);
                        String username = dataSnapshot.child("UserName").getValue(String.class);
                        String phone = dataSnapshot.child("Phone").getValue(String.class);
                        String address = dataSnapshot.child("Address").getValue(String.class);
                        String mail = dataSnapshot.child("Email").getValue(String.class);
                        String url = dataSnapshot.child("URL").getValue(String.class);

                        // Set text cho các TextView
                        lblLevel.setText(level);
                        lblName.setText(name);
                        lblUserName.setText(username);
                        lblPhone.setText(phone);
                        lblAddress.setText(address);
                        lblMail.setText(mail);
                        lblURL.setText(url);
                    } else {
                        // Không tìm thấy dữ liệu cho user có uid tương ứng
                        Toast.makeText(Profile.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi có lỗi xảy ra trong quá trình truy vấn
                    Toast.makeText(Profile.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (googleSignInAccount != null) {
            // Nếu đăng nhập bằng Google, sử dụng thông tin từ GoogleSignInAccount
            String mail = googleSignInAccount.getEmail();
            int index = mail.indexOf("@");
            String email = mail.substring(0, index);
            lblMail.setText(mail);



            DatabaseReference userRef = databaseReference.child("Account").child(email);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Lấy dữ liệu từ dataSnapshot và set text cho các TextView tương ứng
                        String mail = dataSnapshot.child("Email").getValue(String.class);
                        String name = dataSnapshot.child("FullName").getValue(String.class);
                        String level = dataSnapshot.child("Level").getValue(String.class);
                        String username = dataSnapshot.child("UserName").getValue(String.class);
                        String phone = dataSnapshot.child("Phone").getValue(String.class);
                        String address = dataSnapshot.child("Address").getValue(String.class);
                        String url = dataSnapshot.child("URL").getValue(String.class);


                        // Set text cho các TextView
                        lblLevel.setText(level);
                        lblName.setText(name);
                        lblUserName.setText(username);
                        lblPhone.setText(phone);
                        lblAddress.setText(address);
                        lblMail.setText(mail);
                        lblURL.setText(url);

                        // Lấy và hiển thị ảnh đại diện từ GoogleSignInAccount
                        Uri photoUrl = googleSignInAccount.getPhotoUrl();
                        if (photoUrl != null) {
                            Glide.with(Profile.this).load(photoUrl).into(imgAvatar);
                        }
                    } else {
                        // Không tìm thấy dữ liệu cho user có uid tương ứng
                        Toast.makeText(Profile.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi có lỗi xảy ra trong quá trình truy vấn
                    Toast.makeText(Profile.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void signOutGmail() {
        gsc.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(Profile.this, Login.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

}