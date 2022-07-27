package com.example.loginapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity  {

    private EditText userName, userPassword, userEmail, userAddress, userPhone;
    private Button regButton;
    private TextView userLogin;
    private ImageView userProfilePic;
    private FirebaseAuth firebaseAuth;
    String email, name, address, password, phone;
    private FirebaseStorage firebaseStorage;
    private  static  int PICK_IMAGE = 123;
    Uri imagePath;
    private  StorageReference storageReference;
    DatabaseReference databaseReference;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                userProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("user");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        storageReference = firebaseStorage.getReference();

        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*"); // application/* audio/*
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), PICK_IMAGE);
            }
        });



        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    if(userPassword.length() <6){
                        userPassword.setError("Password Must be >= 6 Characters");
                        return;
                    }
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendEmailVerification();
                                //sendUserData();
                                //Toast.makeText(RegistrationActivity.this, "Successfully Registered, Upload complete!", Toast.LENGTH_SHORT).show();
                                //firebaseAuth.signOut();
                                //finish();


                            }else{
                                Toast.makeText(RegistrationActivity.this,"Registration Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });

    }

    private  void  setupUIViews(){
        userName = (EditText)findViewById(R.id.etUserName);
        userPassword = (EditText)findViewById(R.id.etUserPassword);
        userEmail = (EditText)findViewById(R.id.etUserEmail);
        userAddress = (EditText)findViewById(R.id.etUserAddress);
        userPhone = (EditText)findViewById(R.id.etUserphone);
        regButton = (Button)findViewById(R.id.btnRegister);
        userLogin = (TextView)findViewById(R.id.tvRegister);
        userProfilePic = (ImageView)findViewById(R.id.ivProfile);
      }

    private boolean validate(){
        boolean result = false;
         name = userName.getText().toString();
         password = userPassword.getText().toString();
         email = userEmail.getText().toString();
         address = userAddress.getText().toString();
         phone = userPhone.getText().toString();




        if (name.isEmpty() || password.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty() || (imagePath == null)  ){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();

        }else{
            result = true;
        }
        return  result;
    }

    private  void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendUserData();
                        Toast.makeText( RegistrationActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT ).show();
                        firebaseAuth.signOut();
                        finish();
                        //startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                    } else {
                        Toast.makeText( RegistrationActivity.this, "Verification mail has'nt been sent !", Toast.LENGTH_SHORT ).show();

                    }
                }
            });
        }
    }
    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic"); // User id/Images/profile_pic
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(RegistrationActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
            }
        });
        UserProfile userProfile = new UserProfile(email, address, phone, name);
        myRef.setValue(userProfile);

    }


}