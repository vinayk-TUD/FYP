package com.app.footprintcalculator.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.footprintcalculator.R;
import com.app.footprintcalculator.models.UsersModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Sign up form screen fro getting name, address etc from user
public class RegistrationActivity extends AppCompatActivity {

    EditText edtName, edtPhone;
    Button btnRegister;
    DatabaseReference databaseReference;
    String fullName, phone, address;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Firebase database and authentication intialization...
        databaseReference = FirebaseDatabase.getInstance().getReference("UsersData");
        mAuth = FirebaseAuth.getInstance();

        //All the Views of screen and firebase initialization..
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName = edtName.getText().toString().trim();
                phone = edtPhone.getText().toString().trim();

                if(TextUtils.isEmpty(fullName)){
                   edtName.setError("Required!");
                   edtName.requestFocus();
                   return;
                }
                if(TextUtils.isEmpty(phone)){
                    edtPhone.setError("Required!");
                    edtPhone.requestFocus();
                    return;
                }
                createAccount();
            }
        });
    }

    //This method store all the data fileds of user to firebase...
    private void createAccount(){
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        String userId = firebaseUser.getUid();
        UsersModel model = new UsersModel(userId,fullName,phone,"",AuthenticationActivity.password);
        databaseReference.child(userId).setValue(model);
        Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //If user press back button without email verification the show warning dialog and delete user account if he press yes..
    @Override
    public void onBackPressed() {
      //  super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure to leave without creating account?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential = EmailAuthProvider.getCredential(AuthenticationActivity.email, AuthenticationActivity.password);
                firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "Account doesn't created", Toast.LENGTH_LONG).show();
                                finish();
                                if (task.isSuccessful()) {

                                }
                            }
                        });
                    }
                });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
