package com.example.propill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.ArrayList;

public class scanFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int MY_FRAGMENT_RESULT_OK = Activity.RESULT_OK;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_CODE = 2;
    public static Context contextOfApplication;
    Button btn_detection_image, pic_btn, Button_add;
    Uri uri;                // 갤러리에서 가져온 이미지에 대한 Uri
    Bitmap bitmap;          // 갤러리에서 가져온 이미지를 담을 비트맵
    InputImage image;       // ML 모델이 인식할 인풋 이미지
    TextView text_info;     // ML 모델이 인식한 텍스트를 보여줄 뷰
    TextRecognizer recognizer;    //텍스트 인식에 사용될 모델
    String a;
    int imageSize = 224;

    private ImageView imageView;
    private ActivityResultLauncher<Intent> launcher;

    private static final int REQUEST_IMAGE_CODE = 101;

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button ocr;
    private View view;
    private Bitmap img;

    public scanFragment() {
        // Required empty public constructor
    }

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
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String hexCode = "#ffffff";
            int redColor = Color.parseColor(hexCode);

            window.setStatusBarColor(redColor);
        }
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
        imageView = v.findViewById(R.id.imageView1234);
        text_info = v.findViewById(R.id.text_info);
        recognizer = TextRecognition.getClient(new TextRecognizerOptions.Builder().build());    //텍스트 인식에 사용될 모델
        Log.d("redog", "bla");

        pic_btn = (Button) v.findViewById(R.id.pic_btn);
        /*
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra("image")) {
            Log.d("imageView", "load");
            Bitmap tmpimage = (Bitmap) intent.getExtras().get("image");
            imageView.setImageBitmap(tmpimage);
            this.image = InputImage.fromBitmap(tmpimage, 0);
            if (image == null) Log.d("image", "no");
        }*/

        pic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
        btn_detection_image = v.findViewById(R.id.btn_detection_image);
        btn_detection_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextRecognition(recognizer);
            }
        });

        Button_add = (Button) v.findViewById(R.id.Button_add);
        Button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = text_info.getText().toString();
                ArrayList<String> list = new ArrayList<String>();
                list.add(text);
                list.add("ocr");

                Intent intent = new Intent(getContext(), ChatGpt.class);
                intent.putStringArrayListExtra("list", list);
                startActivity(intent);
            }
        });
        return v;

    }

/*    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Toast.makeText(getContext(), "camera", Toast.LENGTH_SHORT).show();
        if (requestCode == 1 && resultCode == MY_FRAGMENT_RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            Intent intent2 = new Intent(getContext(), scanFragment.class);
            intent2.putExtra("image", image);
            startActivity(intent2);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }*/
private ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    int dimension = Math.min(image.getWidth(), image.getHeight());
                    image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                    this.img = image;
                    imageView.setImageBitmap(image);
                    this.image = InputImage.fromBitmap(image, 0);
                    if (image == null) Log.d("image", "no");
                    /*
                    Intent intent2 = new Intent(getContext(), scanFragment.class);
                    intent2.putExtra("image", image);
                    startActivity(intent2);
                     */
                }
            }
        }
);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Assuming you have a button or some other action to start the camera activity
        Button pic_btn = view.findViewById(R.id.pic_btn);
        pic_btn.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(cameraIntent);
        });
    }

    // uri를 비트맵으로 변환시킨후 이미지뷰에 띄워주고 InputImage를 생성하는 메서드
    private void setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        image = InputImage.fromBitmap(bitmap, 0);
        Log.e("setImage", "이미지 to 비트맵");
    }

    private void TextRecognition(TextRecognizer recognizer) {
        Task<Text> result = recognizer.process(image)
                // 이미지 인식에 성공하면 실행되는 리스너
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text visionText) {
                        // Task completed successfully
                        String resultText = visionText.getText();
                        text_info.setText(resultText);  // 인식한 텍스트를 TextView에 세팅
                    }
                })
                // 이미지 인식에 실패하면 실행되는 리스너
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                text_info.setText("텍스트 인식에 실패했습니다");
                                Log.e("텍스트 인식", "실패: " + e.getMessage());
                            }
                        });

}





 /*       btn_picture = (Button) v.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Ocr1.class);
                startActivity(intent);
            }
        });*/
    }
