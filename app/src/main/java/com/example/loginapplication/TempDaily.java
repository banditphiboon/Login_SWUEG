package com.example.loginapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TempDaily extends AppCompatActivity {

    ListView myListView;
    ArrayList<String> myArrayList = new ArrayList<>();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_temp_daily );
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>( TempDaily.this, android.R.layout.simple_list_item_1,myArrayList );
        myListView = (ListView)findViewById( R.id.listview );
        myListView.setAdapter( myArrayAdapter );

        myRef = FirebaseDatabase.getInstance().getReference("Temp");

        myRef.addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(String.class);
                myArrayList.add( value );
                myArrayAdapter.notifyDataSetInvalidated();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myArrayAdapter.notifyDataSetInvalidated();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
    }
}