package com.example.propill;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.propill.ml.ModelUnquant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView registerbutton;
    Button Button2;
    Button Button3;
    Button Button4;
    Button Button5;
    Button Button6;
    private int imageview;
    private String time;
    private String pillname;
    Date mDate;
    String strDay;

    private View view;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("users");


    ArrayList<SampleData> pillDataList;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        registerbutton = (ImageView) view.findViewById(R.id.registerbutton);
        registerbutton.setOnClickListener(this);

        this.InitializeMovieData(container);

        mDate = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd", Locale.getDefault());  // 2월 15일

        //오늘 일자
        String todayDate = format.format(mDate);

        //today
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        int dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeekNumber == 1){
            strDay = "Sun";
        }
        else if(dayOfWeekNumber == 2){
            strDay = "Mon";
        }
        else if(dayOfWeekNumber == 3) {
            strDay = "Tue";
        }
        else if(dayOfWeekNumber == 4) {
            strDay = "Wed";
        }
        else if(dayOfWeekNumber == 5) {
            strDay = "Thu";
        }
        else if(dayOfWeekNumber == 6) {
            strDay = "Fri";
        }
        else if(dayOfWeekNumber == 7) {
            strDay = "Sat";
        }

Button4 = view.findViewById(R.id.button4);
Button4.setText(todayDate + "\n" +strDay);

calendar.add(Calendar.DATE,-1);
        mDate = calendar.getTime();
        //오늘 일자
        String yesterDate = format.format(mDate);
        dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeekNumber == 1){
            strDay = "Sun";
        }
        else if(dayOfWeekNumber == 2){
            strDay = "Mon";
        }
        else if(dayOfWeekNumber == 3) {
            strDay = "Tue";
        }
        else if(dayOfWeekNumber == 4) {
            strDay = "Wed";
        }
        else if(dayOfWeekNumber == 5) {
            strDay = "Thu";
        }
        else if(dayOfWeekNumber == 6) {
            strDay = "Fri";
        }
        else if(dayOfWeekNumber == 7) {
            strDay = "Sat";
        }

        Button3 = view.findViewById(R.id.button3);
        Button3.setText(yesterDate + "\n" +strDay);

        calendar.add(Calendar.DATE,-1);
        mDate = calendar.getTime();
        //오늘 일자
        String dayDate = format.format(mDate);
        dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeekNumber == 1){
            strDay = "Sun";
        }
        else if(dayOfWeekNumber == 2){
            strDay = "Mon";
        }
        else if(dayOfWeekNumber == 3) {
            strDay = "Tue";
        }
        else if(dayOfWeekNumber == 4) {
            strDay = "Wed";
        }
        else if(dayOfWeekNumber == 5) {
            strDay = "Thu";
        }
        else if(dayOfWeekNumber == 6) {
            strDay = "Fri";
        }
        else if(dayOfWeekNumber == 7) {
            strDay = "Sat";
        }

        Button2 = view.findViewById(R.id.button2);
        Button2.setText(dayDate + "\n" +strDay);

        calendar.add(Calendar.DATE,+3);
        mDate = calendar.getTime();
        //오늘 일자
        String plusDate = format.format(mDate);
        dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeekNumber == 1){
            strDay = "Sun";
        }
        else if(dayOfWeekNumber == 2){
            strDay = "Mon";
        }
        else if(dayOfWeekNumber == 3) {
            strDay = "Tue";
        }
        else if(dayOfWeekNumber == 4) {
            strDay = "Wed";
        }
        else if(dayOfWeekNumber == 5) {
            strDay = "Thu";
        }
        else if(dayOfWeekNumber == 6) {
            strDay = "Fri";
        }
        else if(dayOfWeekNumber == 7) {
            strDay = "Sat";
        }

        Button5 = view.findViewById(R.id.button5);
        Button5.setText(plusDate + "\n" +strDay);

        calendar.add(Calendar.DATE,+1);
        mDate = calendar.getTime();
        //오늘 일자
        String plusplusDate = format.format(mDate);
        dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeekNumber == 1){
            strDay = "Sun";
        }
        else if(dayOfWeekNumber == 2){
            strDay = "Mon";
        }
        else if(dayOfWeekNumber == 3) {
            strDay = "Tue";
        }
        else if(dayOfWeekNumber == 4) {
            strDay = "Wed";
        }
        else if(dayOfWeekNumber == 5) {
            strDay = "Thu";
        }
        else if(dayOfWeekNumber == 6) {
            strDay = "Fri";
        }
        else if(dayOfWeekNumber == 7) {
            strDay = "Sat";
        }

        Button6 = view.findViewById(R.id.button6);
        Button6.setText(plusplusDate + "\n" +strDay);

        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

       /* Button2 = (R.id.button2);

        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
*/

        GradientDrawable gradientDrawable = (GradientDrawable)ContextCompat.getDrawable(container.getContext(),R.drawable.mainpageweekbutton);
        gradientDrawable.setColor(Color.BLACK);
        Button3.setBackground(gradientDrawable);
        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(getActivity(),Register.class);
        startActivity(intent);
    }



/*    public void classifyImage(Bitmap image){
        try {
            ModelUnquant model = ModelUnquant.newInstance(getApplicationContext());

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

            Intent intent = new Intent(mainapge.this, PillDescription.class);
            intent.putExtra("pill",classes[maxPos]);
            startActivity(intent);

            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }*/


/*    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

    public void InitializeMovieData(ViewGroup container)
    {
        pillDataList = new ArrayList<SampleData>();
        SharedPreferences preferences = this.getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
        String id= preferences.getString("id", "text");

        List list = new ArrayList();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Object obj = postSnapshot.getValue();
                    Map map = (Map) obj;
                    if(map.get("email").equals(id) ){
                        list.add(obj);
                    }

                }
                for(int i=0; i<list.size(); i++){
                    Map tempData = ((Map)list.get(i));
                    String tempTime = tempData.get("hour")+":"+tempData.get("min");
                    pillDataList.add(new SampleData(tempTime, (String) tempData.get("pillName")));
                }

                ListView listView = (ListView)view.findViewById(R.id.pilllist);
                final MyAdapter myAdapter = new MyAdapter(container.getContext(),pillDataList);

                listView.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}