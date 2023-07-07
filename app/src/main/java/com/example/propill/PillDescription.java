package com.example.propill;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.propill.ml.ModelUnquant;
import com.example.propill.ml.PoscoModel;

import org.checkerframework.checker.units.qual.A;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class PillDescription extends AppCompatActivity {
    TextView pillId;
    Button chatgptBtn, plus_btn;
    int imageSize = 224;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_description);


        Intent intent = getIntent();
        pillId = (TextView) findViewById(R.id.pillId);
        if (intent.hasExtra("pill")) {
            pillId.setText(intent.getStringExtra("pill"));
        } else {
            Log.d("check0", "0");
            ArrayList<String> sequence = intent.getStringArrayListExtra("intentList");
            String str="";
            for (String element : sequence) {
                Log.d("check``", "~");
                str += element;

            }
            pillId.setText(str);
        }

/*
        String name = getIntent().getStringExtra("name");


        if (name="pill") {
            pillId.setText(intent.getStringExtra("intentList"));
        } else {
            pillId.setText(intent.getStringExtra("pill"));
        }

*/
        Button button = (Button) findViewById(R.id.registerBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(PillDescription.this, Register.class);
                registerIntent.putExtra("pill", intent.getStringExtra("pill"));
                startActivity(registerIntent);
            }
        });
        chatgptBtn = (Button) findViewById(R.id.chatgptBtn);
        chatgptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerIntent = new Intent(PillDescription.this, ChatGpt.class);
                registerIntent.putExtra("pill", intent.getStringExtra("pill"));
                startActivity(registerIntent);
            }
        });


        pillId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(PillDescription.this, ChatGpt.class);
                registerIntent.putExtra("pill", intent.getStringExtra("pill"));
                startActivity(registerIntent);
            }
        });
        plus_btn = findViewById(R.id.plus_btn);
        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Launch camera if we have permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.d("check2", "2");
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Log.d("check3", "3");
                        startActivityForResult(cameraIntent, 1);
                    } else {
                        //Request camera permission if we don't have it.
                        Log.d("check", "1");
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                    }
                }
            }
        });
    }
/*
                Intent intent = new Intent(PillDescription.this, cameraFragment.class);
*/


   public void classifyImage(Bitmap image){
       try {
           PoscoModel model = PoscoModel.newInstance(getApplicationContext());

           TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
           ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
           inputFeature0.loadBuffer(byteBuffer);
           byteBuffer.order(ByteOrder.nativeOrder());

           // get 1D array of 224 * 224 pixels in image
           int [] intValues = new int[imageSize * imageSize];
           image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());


                    // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
                    int pixel = 0;
                    for (int i = 0; i < imageSize; i++) {
                        for (int j = 0; j < imageSize; j++) {
                            int val = intValues[pixel++]; // RGB
                            byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                            byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                            byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                        }
                    }
                    inputFeature0.loadBuffer(byteBuffer);
                    PoscoModel.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    float[] confidences = outputFeature0.getFloatArray();
                    // find the index of the class with the biggest confidence.
                    int maxPos = 0;
                    float maxConfidence = 0;
                    for (int i = 0; i < confidences.length; i++) {
                        if (confidences[i] > maxConfidence) {
                            maxConfidence = confidences[i];
                            maxPos = i;
                        }
                    }
                    String[] classes = {"양파", "소고기", "비트", "로메인", "당근"};

                    ArrayList<String> intentList = new ArrayList<>();
                    String text = pillId.getText().toString();
                    Log.d("check``", "~");
                    intentList.add(text + ",");
                    intentList.add(classes[maxPos]);
                    Log.d("check4", "4");
                    Intent intent = new Intent(PillDescription.this, PillDescription.class);
                    intent.putStringArrayListExtra("intentList", new ArrayList<>(intentList));
                    startActivity(intent);





                    /*intent.putExtra("image", image);*/

                    model.close();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    // TODO Handle the exception
                }
            }
            public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                if (requestCode == 1 && resultCode == RESULT_OK) {
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    int dimension = Math.min(image.getWidth(), image.getHeight());
                    image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                    classifyImage(image);
                    Log.d("check5", "5");
                }
                super.onActivityResult(requestCode, resultCode, data);
            }
}