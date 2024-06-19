package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText etUsername, etPassword;
    private FirebaseAuth auth; // يتم استخدامه لإدارة المصادقة في Firebase
    private Utilities utils; // كائن لإجراء الأنشطة العامة مثل التحقق من البريد الإلكتروني وكلمة المرور
    private FireBaseServices fbs; // كائن للوصول إلى خدمات Firebase مثل Firestore و Storage
    private ImageView loader; // عنصر تحميل لعرض رمز التحميل أثناء عملية تسجيل الدخول
    private Button forgotPasswordButton; // زر لنسيان كلمة المرور

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); // إخفاء شريط العنوان
        connectComponents();  // ربط عناصر الواجهة مع المتغيرات
    }

    private void connectComponents(){
        etUsername = findViewById(R.id.username1); // حقل اسم المستخدم
        etPassword = findViewById(R.id.password1); // حقل كلمة المرور
        auth = FirebaseAuth.getInstance(); // الحصول على كائن FirebaseAuth لإدارة المصادقة
        utils = Utilities.getInstance(); // الحصول على كائن Utilities لإجراء الأنشطة العامة
        fbs = FireBaseServices.getInstance(); // الحصول على كائن FireBaseServices للوصول إلى خدمات Firebase
        loader = findViewById(R.id.loader); // حقل الصورة المتحركة أثناء التحميل
        forgotPasswordButton = findViewById(R.id.forgotpw); // زر نسيان كلمة المرور

       /* if(auth.getCurrentUser() != null) {
            Intent intent = new Intent(MainActivity.this, AllPet.class);
            startActivity(intent);
        }*/

        // تعيين مستمع للنقر على زر نسيان كلمة المرور
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();// استدعاء الوظيفة لنسيان كلمة المرور
            }
        });
    }

    // استدعاء الوظيفة لنسيان كلمة المرور
    public void StartSignUp(View view){
        Intent i = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(i);
    }

    // وظيفة لعملية تسجيل الدخول
    public void login(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        // إنشاء كائن مستخدم لتمرير اسم المستخدم
        User user = new User();
        user.setUserName(username);

        // التحقق من صحة مدخلات اسم المستخدم وكلمة المرور
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username or password is missing!", Toast.LENGTH_SHORT).show();
            return;
        }

        // التحقق من صحة بريد المستخدم وكلمة المرور باستخدام الوظائف الموجودة في Utilities
        if (!utils.validateEmail(username) || !utils.validatePassword(password)) {
            Toast.makeText(this, R.string.err_incorrect_user_password, Toast.LENGTH_SHORT).show();
            return;
        }

        // إخفاء لوحة المفاتيح بعد النقر على زر تسجيل الدخول
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
        dbControls.showControl(loader);       // إظهار رمز التحميل

        // عملية تسجيل الدخول باستخدام Firebase
        auth.signInWithEmailAndPassword(username.trim(), password.trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // في حالة نجاح عملية تسجيل الدخول، انتقل إلى الشاشة التالية (AllPet)
                            try {
                                Intent i = new Intent(MainActivity.this, AllPet.class);
                                i.putExtra("user", user);
                                startActivity(i);
                                finish();
                            } catch (Exception ex) {
                                // Handle exception
                            }
                        } else {
                            // إخفاء رمز التحميل وعرض رسالة الخطأ إذا فشلت عملية تسجيل الدخول
                            dbControls.hideControl(loader);
                            Toast.makeText(MainActivity.this, "User Not Exists!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // وظيفة للانتقال إلى شاشة التسجيل
    public void gotoSignup(View view) {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }

    // وظيفة للانتقال إلى شاشة إضافة الحيوانات الأليفة
    public void gotoAddPet(View view) {
        Intent i = new Intent(this, PetAdd.class);
        startActivity(i);
    }

    // وظيفة للانتقال إلى شاشة جميع الحيوانات الأليفة
    public void gotoAllPets(View view) {
        Intent i = new Intent(this, AllPet.class);
        startActivity(i);
    }

    // وظيفة لإرسال بريد إلكتروني لإعادة تعيين كلمة المرور
    private void forgotPassword() {
        String email = etUsername.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }
        // إرسال طلب إعادة تعيين كلمة المرور إلى Firebase
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(MainActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "Error sending password reset email", task.getException());
                            Toast.makeText(MainActivity.this, "Error sending password reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
