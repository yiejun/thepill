package com.example.propill;

import static java.lang.System.in;

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


import java.io.FileNotFoundException;
import java.io.InputStream;


public class Ocr1 extends AppCompatActivity {
    static final int REQUEST_CODE = 2;

    ImageView imageView;    // 갤러리에서 가져온 이미지를 보여줄 뷰
    Uri uri;                // 갤러리에서 가져온 이미지에 대한 Uri
    Bitmap bitmap;          // 갤러리에서 가져온 이미지를 담을 비트맵
    InputImage image;       // ML 모델이 인식할 인풋 이미지
    TextView text_info;     // ML 모델이 인식한 텍스트를 보여줄 뷰
    Button pic_btn, btn_detection_image;  // 이미지 가져오기 버튼, 이미지 인식 버튼
    TextRecognizer recognizer;    //텍스트 인식에 사용될 모델
    int imageSize = 224;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr1);

        imageView = findViewById(R.id.imageView1234);
        text_info = findViewById(R.id.text_info);
        recognizer = TextRecognition.getClient(new TextRecognizerOptions.Builder().build());    //텍스트 인식에 사용될 모델
        Log.d("redog","bla");
        // GET IMAGE 버튼
        pic_btn = findViewById(R.id.pic_btn);
        Intent intent = getIntent();
        if(intent.hasExtra("image")) {
            Log.d("imageView","load");
            Bitmap tmpimage = (Bitmap) intent.getExtras().get("image");
            imageView.setImageBitmap(tmpimage);
            this.image = InputImage.fromBitmap(tmpimage, 0);
            if(image == null) Log.d("image","no");
        }


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


                /*
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE);*/
            }
        });

        // IMAGE DETECTION 버튼
        btn_detection_image = findViewById(R.id.btn_detection_image);
        btn_detection_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("111","34");
                TextRecognition(recognizer);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Log.d("asdfd", "asdfdffsad");
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            Intent intent2 = new Intent(Ocr1.this, Ocr1.class);
            intent2.putExtra("image", image);
            startActivity(intent2);


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // uri를 비트맵으로 변환시킨후 이미지뷰에 띄워주고 InputImage를 생성하는 메서드
    private void setImage(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);

            image = InputImage.fromBitmap(bitmap, 0);
            Log.e("setImage", "이미지 to 비트맵");
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
    }


}
/*
    private SurfaceView surfaceView;
    private TextView textView;
    private CameraSource cameraSource;
    private static final int PERMISSION = 100;


    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_ocr1);

        surfaceView = findViewById(R.id.ocr1_camera);
        textView = findViewById(R.id.ocr1_text1);

        startCameraSource();

    }

    private void startCameraSource() {
        final TextRecognizer textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        if(false *//*!textRecognizer.isOperational()*//*) {
            Log.w("Tag", "Dependencies not loaded yet");
        }else{
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK).setRequestedPreviewSize(1280,1024)
                    .setAutoFocusEnabled(true).setRequestedFps(2.0f).build();
            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(@NonNull SurfaceHolder holder) {
                    try {
                        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(Ocr1.this, new String[]{Manifest.permission.CAMERA},
                                    PERMISSION);
                            return;
                        }
                        cameraSource.start(surfaceView.getHolder());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(@NonNull SurfaceHolder holder, int i, int i1, int i2) {
                    //Release source for cameraSource


                }

                @Override
                public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                    cameraSource.stop();

                }
            });
            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {
                    //detect all text from camera
                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if(items.size() != 0){
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for(int i=0; i<items.size();i++){
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                textView.setText(stringBuilder.toString());
                            }
                        });
                    }
                }
            });
        }
    }*/
/*
    Bitmap image; //사용되는 이미지
    private TessBaseAPI mTess; //Tess API reference
    String datapath = ""; //언어데이터가 있는 경로
    TextView OCRTextView; // OCR 결과뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr1);

        OCRTextView = findViewById(R.id.OCRTextView);

        //이미지 디코딩을 위한 초기화
        image = BitmapFactory.decodeResource(getResources(), R.drawable.sample_eng); //샘플이미지파일
        //언어파일 경로

        datapath = getFilesDir() + "/tesseract/";

        //트레이닝데이터가 카피되어 있는지 체크
        checkFile(new File(datapath + "tessdata/"));

        //Tesseract API 언어 세팅
        String lang = "eng";

        //OCR 세팅
        mTess = new TessBaseAPI();
        mTess.init(datapath, lang);
    }

    public void processImage(View view) {
        String OCRresult = null;
        mTess.setImage(image);
        OCRresult = mTess.getUTF8Text();

        OCRTextView.setText(OCRresult);
    }


    *//***
     *  언어 데이터 파일, 디바이스에 복사
     *//*
    private void copyFiles() {
        try{
            String filepath = datapath + "파일경로";
            AssetManager assetManager = getAssets();
            InputStream instream = assetManager.open("파일경로");
            OutputStream outstream = new FileOutputStream(filepath);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    *//***
     *  디바이스에 언어 데이터 파일 존재 유무 체크
     * @param dir
     *//*
    private void checkFile(File dir) {
        //디렉토리가 없으면 디렉토리를 만들고 그후에 파일을 카피
        if(!dir.exists()&& dir.mkdirs()) {
            copyFiles();
        }
        //디렉토리가 있지만 파일이 없으면 파일카피 진행
        if(dir.exists()) {
            String datafilepath = datapath+ "파일경로";
            File datafile = new File(datafilepath);
            if(!datafile.exists()) {
                copyFiles();
            }
        }
    }*/