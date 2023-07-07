package com.example.propill;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
public class Register extends AppCompatActivity {
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
/*
    Boolean sunFlag = false;
    Boolean monFlag = false;
    Boolean tueFlag = false;
    Boolean wedFlag = false;
    Boolean thuFlag = false;
    Boolean friFlag = false;
    Boolean satFlag = false;
*/
    private TimePicker timePicker;
/*
    TextToSpeech tts;
*/
    EditText editText;
    Button button;
    int hour,min;
    EditText pillName;
    EditText strPillinventory;
    AppCompatButton canBtn, saveBtn, cancelBtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String hexCode = "#eff1ff";
            int redColor = Color.parseColor(hexCode);

            getWindow().setStatusBarColor(redColor);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regiser);

/*
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                        Log.e("TTS", "Language not supported.");
                    } else {
                        button.setText("Ready To Speak");
                    }
                } else {
                    Log.e("TTS", "Initialization failed.");
                }
            }
        });
*/
        editText = findViewById(R.id.pillname);
/*
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence text = editText.getText();
                tts.setPitch((float)1.0); // Sets the speech pitch for the TextToSpeech engine.
                tts.setSpeechRate((float)1.0); // Sets the speech rate.

                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "uid");
                // QUEUE_ADD - Queue mode where the new entry is added at the end of the playback queue.
                // QUEUE_FLUSH - Queue mode where all entries in the playback queue (media to be played
                // and text to be synthesized) are dropped and replaced by the new entry.
            }
        });
*/



        timePicker = findViewById(R.id.timepicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int a, int b) {
                hour = a;
                min = b;
            }
        });

        Intent intent = getIntent();
        pillName = (EditText) findViewById(R.id.pillname);
        strPillinventory = (EditText) findViewById((R.id.pillinventory));
        pillName.setText(intent.getStringExtra("pill"));

/*
        sunBtn = (AppCompatButton) findViewById(R.id.sunBtn);
        monBtn = (AppCompatButton) findViewById(R.id.monBtn);
        tueBtn = (AppCompatButton) findViewById(R.id.tueBtn);
        wedBtn = (AppCompatButton) findViewById(R.id.wedBtn);
        thuBtn = (AppCompatButton) findViewById(R.id.thuBtn);
        friBtn = (AppCompatButton) findViewById(R.id.friBtn);
        satBtn = (AppCompatButton) findViewById(R.id.satBtn);
*/
        canBtn = (AppCompatButton) findViewById(R.id.cancelBtn);
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
/*
        sunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sunFlag) {
                    sunBtn.setBackgroundColor(Color.parseColor("#eff1ff"));
                    sunFlag = false;
                } else {
                    sunBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                    sunFlag = true;
                }
            }
        });
        monBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (monFlag) {
                    monBtn.setBackgroundColor(Color.parseColor("#eff1ff"));
                    monFlag = false;
                } else {
                    monBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                    monFlag = true;
                }
            }
        });
        tueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tueFlag) {
                    tueBtn.setBackgroundColor(Color.parseColor("#eff1ff"));
                    tueFlag = false;
                } else {
                    tueBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                    tueFlag = true;
                }
            }
        });
        wedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wedFlag) {
                    wedBtn.setBackgroundColor(Color.parseColor("#eff1ff"));
                    wedFlag = false;
                } else {
                    wedBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                    wedFlag = true;
                }
            }
        });
        thuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thuFlag) {
                    thuBtn.setBackgroundColor(Color.parseColor("#eff1ff"));
                    thuFlag = false;
                } else {
                    thuBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                    thuFlag = true;
                }
            }
        });
        friBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (friFlag) {
                    friBtn.setBackgroundColor(Color.parseColor("#eff1ff"));
                    friFlag = false;
                } else {
                    friBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                    friFlag = true;
                }
            }
        });
        satBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sunFlag) {
                    satBtn.setBackgroundColor(Color.parseColor("#eff1ff"));
                    friFlag = false;
                } else {
                    satBtn.setBackgroundColor(Color.parseColor("#ffffff"));
                    satFlag = true;
                }
            }
        });
*/
        Button canBtn = findViewById(R.id.cancelBtn);
        canBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), mainapge.class);
                startActivity(intent);
            }
        });
        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),mainapge.class);
                startActivity(intent);
                String pillname = String.valueOf(pillName.getText());
                String Pillinventory = String.valueOf(strPillinventory.getText());
                saveData(pillname,Pillinventory);
                Toast.makeText(Register.this, pillname + " , " + Pillinventory, Toast.LENGTH_LONG).show();
                Toast.makeText(Register.this, hour + " , " + min, Toast.LENGTH_LONG).show();
            }
        });
        Button cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

/*
    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            // Interrupts the current utterance (whether played or rendered to file) and
            // discards other utterances in the queue.
            tts.shutdown();
            // Releases the resources used by the TextToSpeech engine.
        }
        super.onDestroy();
    }
*/
    private void saveData(String pillname,String Pillinventory){
        SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String id= preferences.getString("id", "text");

//final data
        Map<String, Object> data = new HashMap<>();

//saved data for each day of the week
/*
        data.put("sun",sunFlag);
        data.put("mon",monFlag);
        data.put("tue",tueFlag);
        data.put("wed",wedFlag);
        data.put("thu",thuFlag);
        data.put("fri",friFlag);
        data.put("sat",satFlag);

*/
//user info and pill info
        data.put("email",id);
        data.put("pillName",pillname);
        data.put("pillInventory",Pillinventory);
        data.put("hour",hour);
        data.put("min",min);
        userRef.push().setValue(data);
    }

}