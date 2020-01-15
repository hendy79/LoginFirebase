package com.josseapp.loginfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

public class HomeActivity extends AppCompatActivity {
    Button logout;
    TextView uname,telp;
    DataSnapshot dataSnapshot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Beranda");
        User user = dataSnapshot.getValue(User.class);
        uname = findViewById(R.id.tvs1);
        telp = findViewById(R.id.tvs2);
        uname.setText(user.name);
        telp.setText(user.phoneNum);
        logout = findViewById(R.id.bt);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
            }
        });
    }
}
