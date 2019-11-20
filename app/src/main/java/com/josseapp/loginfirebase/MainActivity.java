package com.josseapp.loginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    EditText email,pass;
    Button login;
    TextView signup;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Halaman Login");
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.et1);
        pass = findViewById(R.id.et2);
        login = findViewById(R.id.bt);

        auth = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Toast.makeText(MainActivity.this,"Kamu berhasil login!",Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(MainActivity.this,"Harap Login terlebih dahulu!",Toast.LENGTH_SHORT);
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em= email.getText().toString();
                String pw= pass.getText().toString();
                if(em.isEmpty()){
                    email.setError("Email kosong!");
                    email.requestFocus();
                }else if(pw.isEmpty()){
                    pass.setError("Password kosong!");
                    pass.requestFocus();
                }else if(!(em.isEmpty() && pw.isEmpty())){
                    firebaseAuth.signInWithEmailAndPassword(em,pw)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                            	email.setText("");
                            	pass.setText("");
                                Toast.makeText(MainActivity.this,"Login error, coba lagi!",Toast.LENGTH_SHORT).show();
                            }else{
                                Intent i = new Intent(MainActivity.this,HomeActivity.class);
                                startActivity(i);
                            }
                        }
                    });
                }
            }
        });
        signup = findViewById(R.id.tv);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Register.class);
                startActivity(i);
            }
        });
    }
}
