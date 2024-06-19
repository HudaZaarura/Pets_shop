package com.example.login;

import java.io.Serializable;
// نموذج للمستخدم
public class User  implements Serializable {
    private String Name;

    public  User(){

    }
    // إنشاء مستخدم بالاسم واسم المستخدم
    public User(String name, String userName) {
        Name = name;
        this.userName = userName;
//        this.pwd = pwd;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

//    public String getPwd() {
//        return pwd;
//    }
//
//    public void setPwd(String pwd) {
//        this.pwd = pwd;
//    }

    private String userName;
//    private String pwd;
}
