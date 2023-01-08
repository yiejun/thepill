package com.example.propill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                timer.cancel();

                preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                String id= preferences.getString("id", "text");
                String pw= preferences.getString("pw", "text");
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(id, pw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent mainpage = new Intent(Splash.this, mainapge.class);
                                    startActivity(mainpage);
                                } else {
                                    Intent mainpage = new Intent(Splash.this, login.class);
                                    startActivity(mainpage);
                                }
                            }
                        });
            }
        };
        timer.schedule(timerTask,3000);
    }
}