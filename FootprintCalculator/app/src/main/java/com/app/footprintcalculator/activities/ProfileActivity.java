package com.app.footprintcalculator.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.app.footprintcalculator.R;
import com.app.footprintcalculator.models.HomeLocationModel;
import com.app.footprintcalculator.models.UsersModel;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

//User Profile Screen
public class ProfileActivity extends BaseActivity {

    private static final int PICK_FILE_REQUEST = 101;
    private static final int STORAGE_PERMISSION_CODE = 100;
    ImageView imgEdit, imgUserPic, selectpic, imgLoc;
    EditText edtName, edtPhone, edtEmail, editPassword;
    Button btnUpdate, btnUpdatePass;
    TextView tvLaundryScore, tvEnergyScore;
    String userId, userName, userPhone, url, password;
    DatabaseReference databaseReference;
    StorageReference mStorageRef;
    private StorageTask mUploadTask;
    boolean mGranted;
    Uri fileUri = Uri.EMPTY;
    int count = 0;
    LocationManager locationManager;
    LocationListener locationListener;
    Switch switchLaundry, switchEnergy;
    Spinner spnTime;
    SharedPreferences sharedPrefLaundry, sharedPrefEnergy, sharedPrefScore;
    SharedPreferences.Editor editor, editor2, editor4;
    boolean laundry, energy;
    double laundryScore, energyScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Get user data from user screen..

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userName = UserActivity.userName;
        userPhone = UserActivity.userPhone;
        url = UserActivity.url;
        password = UserActivity.password;

        //Firebase and screen views initialization..

        databaseReference = FirebaseDatabase.getInstance().getReference("UsersData");
        mStorageRef = FirebaseStorage.getInstance().getReference("UsersData/");

        sharedPrefLaundry = getSharedPreferences("Laundry",MODE_PRIVATE);
        editor = sharedPrefLaundry.edit();
        sharedPrefEnergy = getSharedPreferences("Energy",MODE_PRIVATE);
        editor2 = sharedPrefEnergy.edit();
        sharedPrefScore = getSharedPreferences("Scores",MODE_PRIVATE);
        editor4 = sharedPrefScore.edit();

        imgEdit = findViewById(R.id.imgEdit);
        imgUserPic = findViewById(R.id.imgUserPic);
        selectpic = findViewById(R.id.selectpic);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        imgLoc = findViewById(R.id.imgLoc);
        edtEmail = findViewById(R.id.edtEmail);
        editPassword = findViewById(R.id.editPassword);
        switchLaundry = findViewById(R.id.switchLaundry);
        switchEnergy = findViewById(R.id.switchEnergy);
        spnTime = findViewById(R.id.spnTime);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdatePass = findViewById(R.id.btnUpdatePass);
        tvLaundryScore = findViewById(R.id.tvLaundryScore);
        tvEnergyScore = findViewById(R.id.tvEnergyScore);

        edtName.setText(userName);
        edtPhone.setText(userPhone);
        YoYo.with(Techniques.StandUp).duration(100000).repeat(15).playOn(imgLoc);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        edtEmail.setText(firebaseUser.getEmail());
        editPassword.setText(password);

        if(!url.isEmpty()){
            Picasso.with(this).load(url).placeholder(R.drawable.profile).into(imgUserPic);
        }

        edtName.setEnabled(false);
        edtPhone.setEnabled(false);
        edtEmail.setEnabled(false);
        editPassword.setEnabled(false);

        energyScore = sharedPrefScore.getInt("energyScore",0);
        laundryScore = sharedPrefScore.getInt("laundryScore",0);

        double es = sharedPrefScore.getInt("energyScore",0);
        double ls = sharedPrefScore.getInt("laundryScore",0);

        tvEnergyScore.setText("Energy Saving : "+ DecimalFormat.getCurrencyInstance().format(es + 0.40));
        tvLaundryScore.setText("Laundry Saving : "+DecimalFormat.getCurrencyInstance().format(ls + 0.75));

        laundry = sharedPrefLaundry.getBoolean("notify",false);
        energy = sharedPrefEnergy.getBoolean("notify",false);

        final List<String> arr = Arrays.asList(getResources().getStringArray(R.array.time));

        if(laundry){
            switchLaundry.setChecked(true);
            tvLaundryScore.setVisibility(View.VISIBLE);
        }else {
            tvLaundryScore.setVisibility(View.GONE);
        }
        if(energy){
            tvEnergyScore.setVisibility(View.VISIBLE);
            String time = sharedPrefEnergy.getString("time","");
            for(int i=0;i<arr.size();i++){
                if(time.equals(arr.get(i))){
                    spnTime.setVisibility(View.VISIBLE);
                    spnTime.setSelection(i);
                    switchEnergy.setChecked(true);
                    break;
                }
            }
        }else {
            tvEnergyScore.setVisibility(View.GONE);
        }


        //Switch laundry
        switchLaundry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.clear();
                    editor.apply();
                    editor.putBoolean("notify",true);
                    editor.apply();
                }else {
                    editor.clear();
                    editor.apply();
                }
            }
        });
        //switch energy
        switchEnergy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    spnTime.setVisibility(View.VISIBLE); //display spinner time
                }else {
                    spnTime.setVisibility(View.GONE);
                    editor2.clear();
                    editor2.apply();
                }
            }
        });
        spnTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = arr.get(position);
                editor2.clear();
                editor2.apply();
                editor2.putBoolean("notify",true);
                editor2.putString("time",value);
                editor2.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Edit sign clicks code
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count%2 != 0) {
                    edtName.setEnabled(true);
                    edtName.requestFocus();
                    edtPhone.setEnabled(true);
                }else {
                    edtName.setEnabled(false);
                    edtPhone.setEnabled(false);
                    editPassword.setEnabled(false);
                }
            }
        });

        imgLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHomeLocation();
            }
        });
        //Select user picture code
        selectpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!mGranted) {
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                            return;
                        }
                    }
                }

                //Move user to gallery of phone
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_FILE_REQUEST);
            }
        });

        //Update button click code
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = edtName.getText().toString().trim();
                userPhone = edtPhone.getText().toString().trim();

                //Validations to all data fileds..
                if(TextUtils.isEmpty(userName)){
                    edtName.setError("Required!");
                    edtName.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(userPhone)){
                    edtPhone.setError("Required!");
                    edtPhone.requestFocus();
                    return;
                }

                updateAccount();
            }
        });

        //Send update passowrd email code..
        btnUpdatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
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

            }
        });

    }

    private void updateHomeLocation() {
        showProgressDialog();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //map.clear();
                MediaPlayer mp =  MediaPlayer.create(getApplicationContext(), R.raw.access_granted);
                mp.start();
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                HomeLocationModel model = new HomeLocationModel(latitude,longitude);
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("HomeLocation").child(userId);
                dbRef.setValue(model);
                Toast.makeText(ProfileActivity.this, "Home location updated", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }
            @Override
            public void onProviderEnabled(String provider) { }
            @Override
            public void onProviderDisabled(String provider) { }};

        if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 100, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 100, locationListener);
        } else {
            if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 100, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 100, locationListener);
            }
        }
    }

    //Update account detials function..

    private void updateAccount() {
        final UsersModel user = new UsersModel(userId,userName,userPhone,url,password);
        databaseReference.child(userId).setValue(user);
        edtName.setText(userName);
        edtPhone.setText(userPhone);
        edtName.setEnabled(false);
        edtPhone.setEnabled(false);
        editPassword.setEnabled(false);

        Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();

    }

    //Check permissions of read and write storage of mobile phone
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                mGranted = true;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_FILE_REQUEST);
            } else {
                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 100, locationListener);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 100, locationListener);
                }
            }
        }
    }
    @Override

    //This function checks which file user select from his mobile gallery or file explorer..
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && data != null) {
            fileUri = data.getData();
            Picasso.with(this).load(fileUri).into(imgUserPic);
            updatePic();
        }
    }

    private  String getExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver() ;
        MimeTypeMap mime = MimeTypeMap.getSingleton() ;
        return  mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    //This function update user profile pic and store it to firebase storage..
    private void updatePic() {
        if(fileUri !=null){
            showProgressDialog();
            final StorageReference fileref = mStorageRef.child(System.currentTimeMillis() + "." + getExtension(fileUri));
            mUploadTask =   fileref.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            hideProgressDialog();
                            try {
                                databaseReference.child(userId).child("picUrl").setValue(uri.toString());
                                Toast.makeText(ProfileActivity.this, "Picture updated", Toast.LENGTH_SHORT).show();
                                fileUri = Uri.EMPTY;
                            } catch (Exception ex ){
                                Toast.makeText(getApplicationContext()  , "Error : " + ex.toString() , Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    hideProgressDialog();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                }
            });
        }
    }
}
