package com.josseapp.loginfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    Button logout;
    TextView uname,telp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Toast.makeText(HomeActivity.this,"Berhasil Login, dengan Uid = "+Uid,Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Beranda");
        uname = findViewById(R.id.tvs1);
        telp = findViewById(R.id.tvs2);
        logout = findViewById(R.id.bt);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
            }
        });
        Query userQuery = FirebaseDatabase.getInstance().getReference("users_login_firebase").child(Uid);
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uname.setText(dataSnapshot.child("name").getValue(String.class));
                telp.setText(dataSnapshot.child("phoneNum").getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this,"DB Error!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
