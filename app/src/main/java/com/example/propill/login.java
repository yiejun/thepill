package com.example.propill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    TextView createBtn;
    Button loginBtn;
    EditText loginId, loginPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        createBtn = (TextView) findViewById(R.id.createBtn);
        loginId = (EditText) findViewById(R.id.loginId);
        loginPw = (EditText) findViewById(R.id.loginPw);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = loginId.getText().toString();
                String pw = loginPw.getText().toString();

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(id, pw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("id",id);
                                    editor.putString("pw",pw);
                                    editor.commit();

                                    Intent mainpage = new Intent(login.this, mainapge.class);
                                    startActivity(mainpage);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                                }
                            }
                    });
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(login.this, Signup.class);
                startActivity(signUp);
            }
        });
    }
}