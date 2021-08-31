package com.app.footprintcalculator.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.app.footprintcalculator.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Login Screen of user...

public class UserLoginActivity extends BaseActivity {

    private static final String TAG = "EmailPasswordActivity";
    EditText edtEmail, editPassword;
    Button btnLogin, btnRegLogin;
    TextView forgotTextView, textView2;
    String email, passowrd;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        //All the Views of screen and firebase initialization..
        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edtEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        forgotTextView = findViewById(R.id.forgotTextView);
        textView2 = findViewById(R.id.textView2);
        btnRegLogin = findViewById(R.id.btnRegLogin);

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(UserLoginActivity.this, "Sign out", Toast.LENGTH_SHORT).show();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Press login button and user user credentials..
                email = edtEmail.getText().toString().trim();
                passowrd = editPassword.getText().toString().trim();

                if(!isConnectionAvailable(UserLoginActivity.this)){
                    Toast.makeText(UserLoginActivity.this, "Check your network!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    edtEmail.setError("Required!");
                    edtEmail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(passowrd)){
                    editPassword.setError("Required!");
                    editPassword.requestFocus();
                    return;
                }

                //Call signIn method to signin user using firebase authentication..
                signIn();
            }
        });

        forgotTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Forgot password email send code for reset passowrd..
                String email = edtEmail.getText().toString();
                if(TextUtils.isEmpty(email)){
                    edtEmail.setError("Required!");
                    edtEmail.requestFocus();
                    return;
                }
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Password reset email sent successfully", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Error : "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

               // startActivity(new Intent(getApplicationContext(), AddPlacesDataActivity.class));
            }
        });
        btnRegLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start sign up screen for creating new account..
                startActivity(new Intent(getApplicationContext(), AuthenticationActivity.class));
            }
        });
    }

    //Sign in function that checks user account credentials from firebase...
    private void signIn() {
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, passowrd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                if (task.isSuccessful()) {
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("UsersData").child(userId);

                    dbRef.child("password").setValue(passowrd);
                    startActivity(new Intent(getApplicationContext(), UserActivity.class));
                    Toast.makeText(UserLoginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect details", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                }

            }
        });
    }

    //This function checks mobile data connection or wifi is on or off..
    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }


    //This method checks user login status..
    @Override
    protected void onStart() {
        super.onStart();

        if(isOnline()){
            startActivity(new Intent(getApplicationContext(), UserActivity.class));
            finish();
        }
    }

    boolean isOnline(){
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            return true;
        }
        return false;
    }
}