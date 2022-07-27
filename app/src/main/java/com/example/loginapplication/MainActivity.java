package com.example.loginapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
        private EditText Email;
        private EditText Password;
        private TextView Info;
        private CheckBox remember;
        private Button Login;
        private int counter = 5;
        private TextView userRegistration;
        private FirebaseAuth firebaseAuth;
        private ProgressDialog progressDialog;
        private TextView forgotPassword;
        private EditText Temp;
        DatabaseReference databaseReference;
        Temparature temparature;
        private  UserProfile userProfile;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Email = (EditText)findViewById(R.id.etEmail);
            Password = (EditText)findViewById(R.id.etPassword);
            Info = (TextView)findViewById(R.id.tvInfo);
            Login = (Button)findViewById(R.id.btnLogin);
            userRegistration = (TextView)findViewById(R.id.tvRegister);
            forgotPassword = (TextView)findViewById(R.id.tvForgotpassword);
            Temp = findViewById(R.id.etTem);


            Info.setText("No of attempts remaining: 5");

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.getInstance();
            databaseReference = database.getReference();
            temparature = new Temparature();


            firebaseAuth = FirebaseAuth.getInstance();
            progressDialog = new ProgressDialog(this);

            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user != null){
                finish();
                startActivity(new Intent(MainActivity.this, HomePageActivity.class));
            }
            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendUsertemp();
                    float Temperature = Float.parseFloat(Temp.getText().toString());
                    if (Temperature >= 37.5) {
                        Toast.makeText(MainActivity.this,"Your temperature is high, it can't be used", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        validate(Email.getText().toString(), Password.getText().toString(), Temp.getText().toString());
                        return;

                    }
                }

            });

            userRegistration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
                }
            });
            forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, PasswordActivity.class));
                }
            });

            /*remember.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(buttonView.isChecked()){
                        SharedPreferences preferences =getSharedPreferences( "Checkbox",MODE_PRIVATE );
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString( "remember","true" );
                        editor.apply();
                        //Toast.makeText( MainActivity.this,"Checked",Toast.LENGTH_SHORT ).show();
                    }else if(!buttonView.isChecked()){
                        SharedPreferences preferences =getSharedPreferences( "Checkbox",MODE_PRIVATE );
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString( "remember","false" );
                        editor.apply();
                        Toast.makeText( MainActivity.this,"Unchecked",Toast.LENGTH_SHORT ).show();

                    }
                }
            });*/

        }



    private void getValue(){

        temparature.setUsertemp(Temp.getText().toString());
        temparature.getUsertemp( Email.getText().toString());
        //DateFormat df= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //Date date= new Date();
        //databaseReference.child( "Temp" ).child( "Date" ).setValue(df.format(date));

        }

        private void validate(String userName, String userPassword, String s) {

            progressDialog.setMessage("Wait a Minute");
            progressDialog.show();


            firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        checkEmailVerification();
                        //Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(MainActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                        counter--;
                        progressDialog.dismiss();
                        Info.setText("No of attempts remaining: "+ counter);
                        if(counter == 0){
                            Login.setEnabled(false);

                        }
                    }
                }
            });

        }
        public void sendUsertemp() {
            databaseReference.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    getValue();
                    databaseReference.child( "Temp" ).setValue( temparature );
                    DateFormat df= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date= new Date();
                    databaseReference.child( "DateLogin" ).child( "Date" ).setValue(df.format(date).toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );
        }
        public  static  void UpdatesInfo(String UserPhone){



    }

        private  void checkEmailVerification(){
            FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
            boolean emailflad = firebaseUser.isEmailVerified();
            //startActivity(new Intent(MainActivity.this, HomePageActivity.class));

            if(emailflad){
               Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
               finish();
               startActivity(new Intent(MainActivity.this, HomePageActivity.class));
           }else {
               Toast.makeText(this,"Verify your email", Toast.LENGTH_SHORT).show();
               firebaseAuth.signOut();
            }
        }



    }

