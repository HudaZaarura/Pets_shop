package com.example.login;

import android.content.Context;
import android.widget.Toast;

public class dbMessage {

    public static void Message(Context context,String error){

        Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
    }
}
