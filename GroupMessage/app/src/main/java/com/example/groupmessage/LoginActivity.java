package com.example.groupmessage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button login,signup;
    EditText login_email,login_pass;
    FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup=findViewById(R.id.register_login);
        login=findViewById(R.id.register_signup);
        login_email=findViewById(R.id.register_email);
        login_pass=findViewById(R.id.register_password);

        mAuth=FirebaseAuth.getInstance();

        signup.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            finish();
        });

        login.setOnClickListener(view -> {
            String mail=login_email.getText().toString();
            String password=login_pass.getText().toString();

            mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Giriş başarılı",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this,"Giriş başarısız",Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}