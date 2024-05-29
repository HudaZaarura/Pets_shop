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
    private FirebaseAuth auth;
    private Utilities utils;
    private FireBaseServices fbs;
    private ImageView loader;
    private Button forgotPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        connectComponents();
    }

    private void connectComponents(){
        etUsername = findViewById(R.id.username1);
        etPassword = findViewById(R.id.password1);
        auth = FirebaseAuth.getInstance();
        utils = Utilities.getInstance();
        fbs = FireBaseServices.getInstance();
        loader = findViewById(R.id.loader);
        forgotPasswordButton = findViewById(R.id.forgotpw);

        if(auth.getCurrentUser() != null) {
            Intent intent = new Intent(MainActivity.this, AllPet.class);
            startActivity(intent);
        }

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });
    }

    public void StartSignUp(View view){
        Intent i = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(i);
    }

    public void login(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        User user = new User();
        user.setUserName(username);

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username or password is missing!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!utils.validateEmail(username) || !utils.validatePassword(password)) {
            Toast.makeText(this, R.string.err_incorrect_user_password, Toast.LENGTH_SHORT).show();
            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
        dbControls.showControl(loader);

        auth.signInWithEmailAndPassword(username.trim(), password.trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            try {
                                Intent i = new Intent(MainActivity.this, AllPet.class);
                                i.putExtra("user", user);
                                startActivity(i);
                                finish();
                            } catch (Exception ex) {
                                // Handle exception
                            }
                        } else {
                            dbControls.hideControl(loader);
                            Toast.makeText(MainActivity.this, "User Not Exists!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void gotoSignup(View view) {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }

    public void gotoAddPet(View view) {
        Intent i = new Intent(this, PetAdd.class);
        startActivity(i);
    }

    public void gotoAllPets(View view) {
        Intent i = new Intent(this, AllPet.class);
        startActivity(i);
    }

    private void forgotPassword() {
        String email = etUsername.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

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
