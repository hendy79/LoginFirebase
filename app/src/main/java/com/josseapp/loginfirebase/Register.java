package com.josseapp.loginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText email,pass,Cpass;
    Button regis;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        setTitle("Halaman Register");
        email=findViewById(R.id.et1);
        pass=findViewById(R.id.et2);
        Cpass=findViewById(R.id.et3);
        regis=findViewById(R.id.bt);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em= email.getText().toString().trim();
                String pw= pass.getText().toString().trim();
                String cpw= Cpass.getText().toString();
                if(em.isEmpty()){
                    email.setError("Email kosong!");
                    email.requestFocus();
                }else if(pw.isEmpty()){
                    pass.setError("Password kosong!");
                    pass.requestFocus();
                }else if(cpw.isEmpty()){
                    Cpass.setError("Konfirmasi Password kosong!");
                    Cpass.requestFocus();
                }else if (!pw.equals(cpw)){
                    Cpass.setError("Konfirmasi Password tidak sesuai!");
                    Cpass.requestFocus();
                }else if(!(em.isEmpty() && pw.isEmpty() && cpw.isEmpty())){
                    firebaseAuth.createUserWithEmailAndPassword(em,pw)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                email.setText("");
                                pass.setText("");
                                Cpass.setText("");
                                Toast.makeText(Register.this,"Gagal Register!",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Register.this,"Selamat, anda Berhasil Register!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Register.this,MainActivity.class));
                            }
                        }
                    });
                }
            }
        });
    }
}
