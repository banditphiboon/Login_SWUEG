package com.example.loginapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class HomePageActivity extends AppCompatActivity {
    private Button TempDaily;
    private Button BMI;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    //private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        firebaseAuth = FirebaseAuth.getInstance();

        TempDaily = (Button)findViewById( R.id.TempDaily );
        BMI = (Button)findViewById(R.id.BMI );

        TempDaily.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(HomePageActivity.this, TempDaily.class) );
            }
        });

        BMI.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(HomePageActivity.this, BMIActivity.class) );
            }
        });


        //logout = (Button)findViewById(R.id.Logout);

        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });*/

    }
    private  void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(HomePageActivity.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.logoutMenu:{
                Logout();
                break;
            }
            case R.id.profileMenu:
                startActivity(new Intent(HomePageActivity.this, ProfileActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}