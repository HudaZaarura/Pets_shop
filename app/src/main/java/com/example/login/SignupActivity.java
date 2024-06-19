package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaCodec;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    // مكونات واجهة المستخدم

    private EditText  person,etUsername,etPassword,confirmpwd;
    private Utilities utils;
    private FireBaseServices fbs;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private TextView title;
    long count=0;
    private String url="https://log-in-21a6e-default-rtdb.firebaseio.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("create new user");
        getSupportActionBar().hide();
      //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        connectComponents();
        createFirebase();
    }
    // تهيئة قاعدة البيانات Firebase
    public void createFirebase(){
        firebaseDatabase=FirebaseDatabase.getInstance(url);
        databaseReference=firebaseDatabase.getReference("users");
    }
    // ربط مكونات واجهة المستخدم
    private void connectComponents() {
        title=findViewById(R.id.title);
        person=findViewById(R.id.person);
        etUsername = findViewById(R.id.usernamesignup);
        etPassword = findViewById(R.id.passwordsignup);
        confirmpwd=findViewById(R.id.confirmpassword);
        utils = Utilities.getInstance();
        fbs = FireBaseServices.getInstance();
        animate();
    }
    // إضافة حركة إلى العنوان
    private void animate(){
        title.setX(-1000);
        title.animate().translationX(-100).setDuration(600).translationX(0);
    }
    // تعيين رسالة خطأ إذا كانت خانة النص فارغة
    public  void SetError(View view){
        if(view instanceof  EditText){
            EditText v=(EditText) view;
            String str=String.valueOf(v.getText());
            if(str.isEmpty()){
                v.setError("is empty");
            }
        }
    }
    // التعامل مع زر التسجيل
    public void signup(View view) {

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String pwdconfirm=confirmpwd.getText().toString();
        String name=person.getText().toString();
        boolean isEmpty=false;
        boolean requestLength=true;
        User user=new User();
        user.setName(name);
        user.setUserName(username);
       // user.setPwd(password);

        // التحقق مما إذا كانت أي من الحقول فارغة
        if (username.trim().isEmpty() || password.trim().isEmpty()|| pwdconfirm.trim().isEmpty())
        {

            isEmpty=true;
           // return;
        }

        if(isEmpty){
            // إظهار رسالة خطأ والعودة في حالة وجود حقل فارغ
            SetError(etUsername);
            SetError(etPassword);
            SetError(person);
            SetError(confirmpwd);
            Toast.makeText(this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
            return;
        }



        if(password.length()<6)
        {

            requestLength=false;
        }
        if(!password.equals(pwdconfirm)){
            dbMessage.Message(getApplicationContext(),"incorrect confirm password ");
            return;
        }
        if(!requestLength){


            dbMessage.Message(getApplicationContext(),"Password must be at least 6 characters");
           // Toast.makeText(getApplicationContext(),)
            return;
        }
        if(Patterns.EMAIL_ADDRESS.matcher(username).matches()==false){

            SetError(etUsername);
            dbMessage.Message(getApplicationContext(),"Email Not Correct!!!");
            return;
        }
//        if (!utils.validateEmail(username) || !utils.validatePassword( password))
//        {
//          //  Toast.makeText(this, "Incorrect email or password!", Toast.LENGTH_SHORT).show();
//            return;
//        }


        try {

            FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();


            firebaseAuth.fetchSignInMethodsForEmail(username).addOnCompleteListener(this, new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                    boolean isEmpty=task.getResult().getSignInMethods().isEmpty();
                    if(isEmpty){
                        firebaseAuth.createUserWithEmailAndPassword(username, password)
                                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                         // إضافة المستخدم إلى قاعدة البيانات
                                            firebaseDatabase=FirebaseDatabase.getInstance(url);
                                            databaseReference=firebaseDatabase.getReference("users");
                                            if(databaseReference!=null) {
                                                try {

                                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                                                                                                         @Override
                                                                                                         public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                                                             count = snapshot.getChildrenCount();
                                                                                                             databaseReference.child(String.format("user%s", count + 1)).setValue(user);
                                                                                                         }

                                                                                                         @Override
                                                                                                         public void onCancelled(@NonNull DatabaseError error) {

                                                                                                         }
                                                                                                     }
                                                    );
                                                    //  dbMessage.Message(getApplicationContext(),"done");
                                                }
                                                catch (Exception ex){
                                                    dbMessage.Message(getApplicationContext(),ex.getMessage());
                                                }
                                            }
//                                            Intent i = new Intent(SignupActivity.this, AllPet.class);
//                                            startActivity(i);
//                                            finish();

                                        } else {
                                            // الانتقال إلى النشاط AllPet في حالة فشل التسجيل
                                            Intent i = new Intent(SignupActivity.this, AllPet.class);
                                            startActivity(i);

                            /*
                            Toast.makeText(SignupActivity.this, R.string.err_firebase_general, Toast.LENGTH_SHORT).show();
                            */

                                        }
                                    }
                                });
                    }
                    else{
                        dbMessage.Message(getApplicationContext(),"User Is Exists!!");
                    }
                }
            });
//            firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//
//                    if(!task.isSuccessful()){
//
//                    }
//                    else {
//
//                    }
//                }
//            });
            // fbs.getAuth()

        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    // التعامل مع زر إضافة الحيوانات الأليفة
    public void addpet(View view) {
        Intent i = new Intent(SignupActivity.this, PetAdd.class);
        startActivity(i);

    }
}