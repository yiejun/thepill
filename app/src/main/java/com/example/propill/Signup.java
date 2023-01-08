package com.example.propill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Signup extends AppCompatActivity {
    Button loginBtn;
    EditText loginId,signupPw1, signupPw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loginBtn = (Button) findViewById(R.id.signup);
        loginId = (EditText) findViewById(R.id.signupId);
        signupPw1 = (EditText) findViewById(R.id.signupPw1);
        signupPw2 = (EditText) findViewById(R.id.signupPw2);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = loginId.getText().toString();
                String pw1 = signupPw1.getText().toString();
                String pw2 = signupPw2.getText().toString();

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                if(!pw1.equals(pw2)){
                    Toast.makeText(Signup.this, "비밀번호가 같지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(id, pw1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent mainpage = new Intent(Signup.this, login.class);
                                    startActivity(mainpage);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}