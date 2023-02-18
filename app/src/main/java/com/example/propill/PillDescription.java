package com.example.propill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PillDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_description);
        Intent intent = getIntent();

        Button button = (Button) findViewById(R.id.registerBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(PillDescription.this, Register.class);
                registerIntent.putExtra("pill",intent.getStringExtra("pill"));
                startActivity(registerIntent);
            }
        });

    }

}