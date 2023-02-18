package com.example.propill;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.FileNotFoundException;
import java.io.InputStream;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


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
    Uri uri;                // 갤러리에서 가져온 이미지에 대한 Uri
    Bitmap bitmap;          // 갤러리에서 가져온 이미지를 담을 비트맵
    InputImage image;       // ML 모델이 인식할 인풋 이미지
    TextView text_info;     // ML 모델이 인식한 텍스트를 보여줄 뷰
    Button btn_get_image, btn_detection_image;  // 이미지 가져오기 버튼, 이미지 인식 버튼
    TextRecognizer recognizer;    //텍스트 인식에 사용될 모델
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
        view = inflater.inflate(R.layout.fragment_scan, container, false);





 /*       imageView = getView().findViewById(R.id.imageView);
        text_info = (TextView) view.findViewById(R.id.text_info);
        a = text_info.getText().toString();
        recognizer = TextRecognition.getClient();    //텍스트 인식에 사용될 모델

        // GET IMAGE 버튼

        btn_get_image = (Button) view.findViewById(R.id.btn_get_image);
        btn_get_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        // IMAGE DETECTION 버튼
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
            // 갤러리에서 선택한 사진에 대한 uri를 가져온다.
            uri = data.getData();

            setImage(uri);
        }
    }

    // uri를 비트맵으로 변환시킨후 이미지뷰에 띄워주고 InputImage를 생성하는 메서드
    private void setImage(Uri uri) {
        try{
            InputStream in = getActivity().getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bitmap);

            image = InputImage.fromBitmap(bitmap, 0);
            Log.e("setImage", "이미지 to 비트맵");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void TextRecognition(TextRecognizer recognizer){
        Task<Text> result = recognizer.process(image)
                // 이미지 인식에 성공하면 실행되는 리스너
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text visionText) {
                        Log.e("텍스트 인식", "성공");
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
                                Log.e("텍스트 인식", "실패: " + e.getMessage());
                            }
                        });
    }*/

return view;
    }


}
