package com.example.propill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Ocr1 extends AppCompatActivity {
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


}