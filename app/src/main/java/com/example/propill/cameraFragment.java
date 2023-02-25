package com.example.propill;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import android.media.ThumbnailUtils;

import com.example.propill.ml.ModelUnquant;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cameraFragment extends Fragment implements View.OnClickListener  {
    Button Button2;
    int imageSize = 224;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public cameraFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cameraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static cameraFragment newInstance(String param1, String param2) {
        cameraFragment fragment = new cameraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_camera, container, false);
        Button2 = root.findViewById(R.id.Button2);
        Button2.setOnClickListener((View.OnClickListener) this);
        // Inflate the layout for this fragment
    return root;
}
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Button2: {
                // Launch camera if we have permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.d("check2", "2");
                    if (view.getContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
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
        }
    }
            public void classifyImage(Bitmap image){
                try {
                    ModelUnquant model = ModelUnquant.newInstance(this.getContext().getApplicationContext());

                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
                    inputFeature0.loadBuffer(byteBuffer);
                    byteBuffer.order(ByteOrder.nativeOrder());

                    // get 1D array of 224 * 224 pixels in image
                    int [] intValues = new int[imageSize * imageSize];
                    image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

                    // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
                    int pixel = 0;
                    for(int i = 0; i < imageSize; i++){
                        for(int j = 0; j < imageSize; j++){
                            int val = intValues[pixel++]; // RGB
                            byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                            byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                            byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                        }
                    }

                    inputFeature0.loadBuffer(byteBuffer);

                    ModelUnquant.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    float[] confidences = outputFeature0.getFloatArray();
                    // find the index of the class with the biggest confidence.
                    int maxPos = 0;
                    float maxConfidence = 0;
                    for(int i = 0; i < confidences.length; i++){
                        if(confidences[i] > maxConfidence){
                            maxConfidence = confidences[i];
                            maxPos = i;
                        }
                    }
                    String[] classes = {"Tylenol", "Advil", "Atorvastatin", "Amoxicillin","Lisinopril"};

                    Intent intent1 = new Intent(this.getContext(), PillDescription.class);

                    intent1.putExtra("pill",classes[maxPos]);
                    /*intent.putExtra("image", image);*/
                    startActivity(intent1);

                    model.close();
                } catch (IOException e) {
                    // TODO Handle the exception
                }
            }


            @Override
            public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                if (requestCode == 1 && resultCode == RESULT_OK) {
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    int dimension = Math.min(image.getWidth(), image.getHeight());
                    image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                    classifyImage(image);

                }
                super.onActivityResult(requestCode, resultCode, data);
            }
        }