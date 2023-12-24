package com.example.login_register_fix;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    ImageButton btn_Register_Back;
    Button btn_Register_Create_Account;
    EditText edt_Register_Email, edt_Register_Password, edt_Register_ConfirmPassword, edt_Register_FullName, edt_Register_UserName;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://appbanhang-5d802-default-rtdb.firebaseio.com/");

    //Check user da dang nhap chua, neu roi thi chuyen huong den HomeActivity
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_Register_Back = findViewById(R.id.btn_Register_Back);
        btn_Register_Create_Account = findViewById(R.id.btn_Register_Create_Account);
        edt_Register_Email = findViewById(R.id.edt_Register_Email);
        edt_Register_Password = findViewById(R.id.edt_Register_Password);
        edt_Register_ConfirmPassword = findViewById(R.id.edt_Register_ConfirmPassword);
        edt_Register_FullName = findViewById(R.id.edt_Register_FullName);
        edt_Register_UserName = findViewById(R.id.edt_Register_UserName);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        btn_Register_Create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String mail, password, username, fullname, confirmpassword;
                mail = String.valueOf(edt_Register_Email.getText());
                password = String.valueOf(edt_Register_Password.getText());
                username = String.valueOf(edt_Register_UserName.getText());
                fullname = String.valueOf(edt_Register_FullName.getText());
                confirmpassword = String.valueOf(edt_Register_ConfirmPassword.getText());

                int index = mail.indexOf("@");
                String email = mail.substring(0, index);

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username) || TextUtils.isEmpty(fullname) || TextUtils.isEmpty(confirmpassword)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Register.this, "Please enter all Information", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!password.equals(confirmpassword)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Register.this, "Password and Confirm Password are not the same", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child("Account").child(email).exists()) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Register.this, "Phone number already exists", Toast.LENGTH_SHORT).show();
                                return;
                            } else {


                                mAuth.createUserWithEmailAndPassword(mail, password)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                progressBar.setVisibility(View.GONE);
                                                if (task.isSuccessful()) {
                                                    //Luu thong tin vao Firebase Realtime Database
                                                    databaseReference.child("Account").child(email).child("Email").setValue(email + "@gmail.com");
                                                    databaseReference.child("Account").child(email).child("FullName").setValue(fullname);
                                                    databaseReference.child("Account").child(email).child("UserName").setValue(username);
                                                    databaseReference.child("Account").child(email).child("Password").setValue(password);
                                                    //updateName
                                                    Toast.makeText(Register.this, "Account Created.",
                                                            Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    // If sign in fails, display a message to the user.
                                                    Toast.makeText(Register.this, ""+task.getException().getMessage().toString(),
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Register.this, ""+error, Toast.LENGTH_SHORT).show();
                        }
                    });


                }


            }
        });


        btn_Register_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(123, intent);

                finish();
            }
        });

    }
}