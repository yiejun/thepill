package com.example.propill;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognizer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.Manifest;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link scanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class scanFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_CODE = 2;
    public static Context contextOfApplication;

    private Button btn_picture;
    private ImageView imageView;

    private static final int REQUEST_IMAGE_CODE = 101;

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button ocr;
    Uri uri;                // ??????????????? ????????? ???????????? ?????? Uri
    Bitmap bitmap;          // ??????????????? ????????? ???????????? ?????? ?????????
    InputImage image;       // ML ????????? ????????? ?????? ?????????
    TextView text_info;     // ML ????????? ????????? ???????????? ????????? ???
    Button btn_get_image, btn_detection_image;  // ????????? ???????????? ??????, ????????? ?????? ??????
    TextRecognizer recognizer;    //????????? ????????? ????????? ??????
    String a;


    private View view;

    public scanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment scanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static scanFragment newInstance(String param1, String param2) {
        scanFragment fragment = new scanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_scan, container, false);

        imageView = (ImageView) v.findViewById(R.id.imageView1234);

        btn_picture = (Button) v.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Ocr1.class);
                startActivity(intent);
            }
        });

        /*btn_picture = (Button) v.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();*//*
            }
        });*/
        return v;
    }
/*
    //????????????
    public void takePicture(){

        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageTakeIntent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CODE);
        }
    }

    //????????? ????????????
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CODE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();

            Bitmap imageBitmap = (Bitmap) extras.get("data");

            imageView.setImageBitmap(imageBitmap);
        }
    }*/






 /*       imageView = getView().findViewById(R.id.imageView);
        text_info = (TextView) view.findViewById(R.id.text_info);
        a = text_info.getText().toString();
        recognizer = TextRecognition.getClient();    //????????? ????????? ????????? ??????

        // GET IMAGE ??????

        btn_get_image = (Button) view.findViewById(R.id.btn_get_image);
        btn_get_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        // IMAGE DETECTION ??????
        btn_detection_image = view.findViewById(R.id.btn_detection_image);
        btn_detection_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextRecognition(recognizer);
            }
        });

        return inflater.inflate(R.layout.fragment_scan, container, false);

    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_CODE) {
            // ??????????????? ????????? ????????? ?????? uri??? ????????????.
            uri = data.getData();

            setImage(uri);
        }
    }

    // uri??? ??????????????? ??????????????? ??????????????? ???????????? InputImage??? ???????????? ?????????
    private void setImage(Uri uri) {
        try{
            InputStream in = getActivity().getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bitmap);

            image = InputImage.fromBitmap(bitmap, 0);
            Log.e("setImage", "????????? to ?????????");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void TextRecognition(TextRecognizer recognizer){
        Task<Text> result = recognizer.process(image)
                // ????????? ????????? ???????????? ???????????? ?????????
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text visionText) {
                        Log.e("????????? ??????", "??????");
                        // Task completed successfully
                        String resultText = visionText.getText();
                        text_info.setText(resultText);  // ????????? ???????????? TextView??? ??????
                    }
                })
                // ????????? ????????? ???????????? ???????????? ?????????
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("????????? ??????", "??????: " + e.getMessage());
                            }
                        });
    }*/





}
