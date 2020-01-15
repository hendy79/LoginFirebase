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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText email,pass,Cpass,uname,telp;
    Button regis;
    FirebaseAuth firebaseAuth;
    DatabaseReference dataRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        setTitle("Halaman Register");
        email=findViewById(R.id.et1);
        pass=findViewById(R.id.et2);
        Cpass=findViewById(R.id.et3);
        uname=findViewById(R.id.et4);
        telp=findViewById(R.id.et5);
        regis=findViewById(R.id.bt);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String em= email.getText().toString().trim();
                final String pw= pass.getText().toString().trim();
                final String cpw= Cpass.getText().toString();
                final String unm= uname.getText().toString();
                final String tp= telp.getText().toString();
                if(em.isEmpty()){
                    email.setError("Email kosong!");
                    email.requestFocus();
                }else if(pw.isEmpty()){
                    pass.setError("Password kosong!");
                    pass.requestFocus();
                }else if(cpw.isEmpty()){
                    Cpass.setError("Konfirmasi Password kosong!");
                    Cpass.requestFocus();
                }else if(unm.isEmpty()){
                    Cpass.setError("Username kosong!");
                    Cpass.requestFocus();
                }else if(tp.isEmpty()){
                    Cpass.setError("Nomor Telepon kosong!");
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
                                uname.setText("");
                                telp.setText("");
                                Toast.makeText(Register.this,"Gagal Register!",Toast.LENGTH_SHORT).show();
                            }else{
                                User user = new User(
                                        unm,
                                        em,
                                        tp,
                                        pw
                                );
                                FirebaseDatabase.getInstance().getReference("users_login_firebase")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Register.this,"Berhasil Masuk ke DB!", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(Register.this,"Gagal Masuk ke DB!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                Toast.makeText(Register.this,"Selamat, anda Berhasil Register!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Register.this,MainActivity.class));
                            }
                        }
                    });
                }
            }
        });
    }
    public static void writeNewUser(DatabaseReference databaseReference, String userId, String name, String email, String phoneNum, String password){
        User user = new User(name, email, phoneNum, password);
        databaseReference.child("users").child(userId).setValue(user);
    }
}
