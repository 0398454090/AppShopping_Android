package com.example.login_register_fix.BottomNavBar;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login_register_fix.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class Edit_Profile extends AppCompatActivity {

    ImageButton btnBack;
    Button btnSave;
    ImageView imgAvt, imgCapture;
    EditText txtLevel, txtName,txtUserName, txtPhone, txtAddress, txtMail, txtURL;
    FirebaseUser currentUser;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://appbanhang-5d802-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);



        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        imgAvt = findViewById(R.id.imgAvt);
        imgCapture = findViewById(R.id.imgCapture);
        txtLevel = findViewById(R.id.txtLevel);
        txtUserName = findViewById(R.id.txtUserName);
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        txtMail = findViewById(R.id.txtMail);
        txtURL = findViewById(R.id.txtLink);


        String level = getIntent().getStringExtra("level");
        String name = getIntent().getStringExtra("name");
        String username = getIntent().getStringExtra("username");
        String phone = getIntent().getStringExtra("phone");
        String mail = getIntent().getStringExtra("mail");
        String address = getIntent().getStringExtra("address");
        String url  = getIntent().getStringExtra("url");
        txtLevel.setText(level);
        txtName.setText(name);
        txtUserName.setText(username);
        txtPhone.setText(phone);
        txtAddress.setText(address);
        txtMail.setText(mail);
        txtURL.setText(url);

        txtMail.setEnabled(false);


        imgCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 1234);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String level = txtLevel.getText().toString();
                String name = txtName.getText().toString();
                String username = txtUserName.getText().toString();
                String phone = txtPhone.getText().toString();
                String address = txtAddress.getText().toString();
                String mail = txtMail.getText().toString();
                String url = txtURL.getText().toString();
//                String imagePath = getImagePath();

                int index = mail.indexOf("@");
                String email = mail.substring(0, index);
                // Lưu đường dẫn ảnh vào cơ sở dữ liệu

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child("Account").child(email).child("Email").setValue(mail);
                        databaseReference.child("Account").child(email).child("Level").setValue(level);
                        databaseReference.child("Account").child(email).child("FullName").setValue(name);
                        databaseReference.child("Account").child(email).child("UserName").setValue(username);
                        databaseReference.child("Account").child(email).child("Phone").setValue(phone);
                        databaseReference.child("Account").child(email).child("Address").setValue(address);
                        databaseReference.child("Account").child(email).child("URL").setValue(url);
//                        databaseReference.child("Account").child(email).child("Avatar").setValue(imagePath);

                        Toast.makeText(Edit_Profile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

                        Intent intentData = new Intent(Edit_Profile.this, Profile.class);
                        startActivity(intentData);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Edit_Profile.this, ""+error.toException(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(123, intent);

                finish();
            }
        });


    }

    // Hàm này trả về đường dẫn của ảnh đại diện
    private String getImagePath() {
        // Lấy URI của ảnh từ ImageView
        Uri imageUri = getImageUri(imgAvt);

        if (imageUri != null) {
            // Directly return the image URI as a string
            return imageUri.toString();
        } else {
            // Handle the case where the image URI is null
            return "";
        }
    }


    // Hàm này chuyển đổi ImageView thành URI
    private Uri getImageUri(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri imageUri = data.getData();
                imgAvt.setImageURI(imageUri);
            } else {
                // Handle the case where the user cancels image selection
                Toast.makeText(Edit_Profile.this, "Image selection canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }



}