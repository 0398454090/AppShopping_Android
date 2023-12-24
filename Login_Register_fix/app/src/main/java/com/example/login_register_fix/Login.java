package com.example.login_register_fix;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class Login extends AppCompatActivity {

    EditText editText_Login_Account, editText_Login_Password;
    Button btn_Login_Login;
    CheckBox checkBox_Login_RememberMe;
    ImageView imgView_Login_Facebook, imgView_Login_Google, imgView_Login_Twitter;
    TextView txt_Login_ForgotPassword, txt_Login_Register;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    CallbackManager callbackManager;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://appbanhang-5d802-default-rtdb.firebaseio.com/");


    private boolean getCheckboxState() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        // If the key is not found, default value is false
        return sharedPreferences.getBoolean("rememberMeChecked", false);
    }

    // Save the state of the "Remember Me" checkbox
    private void saveCheckboxState(boolean isChecked) {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("rememberMeChecked", isChecked);
        editor.apply();
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Save the state of the "Remember Me" checkbox
        boolean rememberMeChecked = checkBox_Login_RememberMe.isChecked();
        saveCheckboxState(rememberMeChecked);
    }

    //Check user da dang nhap chua, neu roi thi chuyen huong den HomeActivity
    @Override
    public void onStart() {
        super.onStart();
        boolean rememberMeChecked = getCheckboxState();
        if (rememberMeChecked){
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if(currentUser != null){
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }


        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_Login_Account = findViewById(R.id.editText_Login_Account);
        editText_Login_Password = findViewById(R.id.editText_Login_Password);
        btn_Login_Login = findViewById(R.id.btn_Login_Login);
        checkBox_Login_RememberMe = findViewById(R.id.checkBox_Login_RememberMe);
        imgView_Login_Facebook = findViewById(R.id.imgView_Login_Facebook);
        imgView_Login_Google = findViewById(R.id.imgView_Login_Google);
        imgView_Login_Twitter = findViewById(R.id.imgView_Login_Twitter);
        txt_Login_ForgotPassword = findViewById(R.id.txt_Login_ForgotPassword);
        txt_Login_Register = findViewById(R.id.txt_Login_Register);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Retrieve the state of the "Remember Me" checkbox
        boolean rememberMeChecked = getCheckboxState();
        checkBox_Login_RememberMe.setChecked(rememberMeChecked);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);



        //Google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);



        btn_Login_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                String mail, password;
                mail = String.valueOf(editText_Login_Account.getText());
                password = String.valueOf(editText_Login_Password.getText());

                int index = mail.indexOf("@");
                String email = mail.substring(0, index);

                if(TextUtils.isEmpty(mail)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Please enter your Email number", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(password)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    return;
                } else{

                    databaseReference.child("Account").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Check email co ton tai trong database khong
                            if (snapshot.child(email).exists()) {
                                //Lay password tu database
                                String passwordFromDatabase = snapshot.child(email).child("Password").getValue().toString();
                                //So sanh password nhap vao voi password trong database
                                if (password.equals(passwordFromDatabase)) {
                                    mAuth.signInWithEmailAndPassword(mail, password)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        // Sign in success, update UI with the signed-in user's information
                                                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                        FirebaseUser user = mAuth.getCurrentUser();
                                                        updateUI(user);
                                                    } else {
                                                        // If sign in fails, display a message to the user.
                                                        Toast.makeText(Login.this, "Authentication failed.",
                                                                Toast.LENGTH_SHORT).show();
                                                        updateUI(null);
                                                    }
                                                }
                                            });
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Login.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Login.this, "Phone number does not exist", Toast.LENGTH_SHORT).show();
                                return;
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }


            }
        });

        imgView_Login_Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackManager = CallbackManager.Factory.create();
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {

                                handleFacebookAccessToken(loginResult.getAccessToken());

                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(Login.this, "Canceled Facebook", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Toast.makeText(Login.this, "Facebook-Error", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        imgView_Login_Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });

        imgView_Login_Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        txt_Login_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivityIfNeeded(intent,1);
            }
        });

        txt_Login_ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void SignIn() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data); //Facebook
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){ // Kiểm tra nếu requestCode là 100 (Google sign-in)
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // Chuyển hướng đến hoạt động thứ hai chỉ khi đăng nhập thành công
                if (account != null) {
                    NavigateToSecondActivity();
                }
            } catch (ApiException e) {
                if(e instanceof ApiException) {
                    ApiException exception = (ApiException) e;

                    // handling network error
                    if(exception.getStatusCode() == CommonStatusCodes.NETWORK_ERROR) {
                        Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();

                        // handling cancellation
                    } else if(exception.getStatusCode() == CommonStatusCodes.CANCELED) {
                        Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();

                        // other specific error codes
                    } else {
                        Toast.makeText(this, "Authentication failed with error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // general error
                    Toast.makeText(this, "Sign in failed with error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    //Chuyển hướng đăng nhập từ main đến trang chủ
    private void NavigateToSecondActivity() {

        Intent intent = new Intent(this, HomeActivity.class); //Sửa lại intent chuyển hướng đến trang chủ
        startActivity(intent);
        finish();
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }


}