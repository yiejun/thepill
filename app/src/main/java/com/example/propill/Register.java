package com.example.propill;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    Boolean sunFlag = false;
    EditText pillName;
    AppCompatButton sunBtn, monBtn, tueBtn, wedBtn, thuBtn, friBtn, satBtn, canBtn, saveBtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regiser);

        Intent intent = getIntent();
        pillName=(EditText)findViewById(R.id.pillname);
        pillName.setText(intent.getStringExtra("pill"));

        sunBtn = (AppCompatButton) findViewById(R.id.sunBtn);
        monBtn = (AppCompatButton) findViewById(R.id.monBtn);
        tueBtn = (AppCompatButton) findViewById(R.id.tueBtn);
        wedBtn = (AppCompatButton) findViewById(R.id.wedBtn);
        thuBtn = (AppCompatButton) findViewById(R.id.thuBtn);
        friBtn = (AppCompatButton) findViewById(R.id.friBtn);
        satBtn = (AppCompatButton) findViewById(R.id.satBtn);
        canBtn = (AppCompatButton) findViewById(R.id.canBtn);
        saveBtn = (AppCompatButton) findViewById(R.id.saveBtn);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id = null;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sunFlag){
                    sunBtn.setBackgroundColor(Color.parseColor("#eff1ff"));
                    sunFlag = false;
                }else {
                    sunBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                    sunFlag = true;
                }
            }
        });
        monBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sunFlag){
                    monBtn.setBackgroundColor(Color.parseColor("#eff1ff"));
                    sunFlag = false;
                }else {
                    monBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                    sunFlag = true;
                }
            }
        });
        tueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sunFlag){
                    tueBtn.setBackgroundColor(Color.parseColor("#eff1ff"));
                    sunFlag = false;
                }else {
                    tueBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                    sunFlag = true;
                }
            }
        });
        wedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sunFlag){
                    wedBtn.setBackgroundColor(Color.parseColor("#eff1ff"));
                    sunFlag = false;
                }else {
                    wedBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                    sunFlag = true;
                }
            }
        });
        thuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sunFlag){
                    thuBtn.setBackgroundColor(Color.parseColor("#eff1ff"));
                    sunFlag = false;
                }else {
                    thuBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                    sunFlag = true;
                }
            }
        });
        friBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sunFlag){
                    friBtn.setBackgroundColor(Color.parseColor("#eff1ff"));
                    sunFlag = false;
                }else {
                    friBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                    sunFlag = true;
                }
            }
        });
        satBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sunFlag){
                    satBtn.setBackgroundColor(Color.parseColor("#eff1ff"));
                    sunFlag = false;
                }else {
                    satBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                    sunFlag = true;
                }
            }
        });
        Button canBtn = findViewById(R.id.canBtn);
        canBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),mainapge.class);
                startActivity(intent);
            }
        });
        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),mainapge.class);
                startActivity(intent);
                Toast.makeText(Register.this, "Successfully Saved", Toast.LENGTH_SHORT).show();

            }
        });
    }
}