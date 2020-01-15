package com.josseapp.loginfirebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String email;
    public String name;
    public String password;
    public String phoneNum;

    public User(){

    }

    public User(String name, String email, String phoneNum, String password){
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.password = password;
    }
}
