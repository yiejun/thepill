package com.example.propill;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
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
import android.view.Window;
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
import com.google.firebase.database.collection.LLRBNode;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String NOTIFICATION_CHANNEL_ID = "1001";
    private CharSequence channelName = "노티피케이션 채널";
    private String description = "해당 채널에 대한 설명";
    private int importance = NotificationManager.IMPORTANCE_HIGH;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView registerbutton;
    Button Button2;
    Button Button3;
    Button Button4;
    Button Button5;
    Button Button6;
    String selectDay;
    private int imageview;
    private String time;
    private String pillname;
    Date mDate;
    String strDay;

    private View view;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("users");

    ArrayList<SampleData> pillDataList;
    ArrayList<InventoryData> InventoryList;
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
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String hexCode = "#eff1ff";
            int redColor = Color.parseColor(hexCode);

            window.setStatusBarColor(redColor);
        }

        view = inflater.inflate(R.layout.fragment_home, container, false);
        registerbutton = (ImageView) view.findViewById(R.id.registerbutton);
        registerbutton.setOnClickListener(this);

        this.InitializeMovieData(container);
        this.InitializeInventoryData(container);

        mDate = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd", Locale.getDefault());  // 2월 15일

        //date of today
        String todayDate = format.format(mDate);

        //today
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);

        if(dayOfWeekNumber == 1){
            strDay = "sun";
        }
        else if(dayOfWeekNumber == 2){
            strDay = "mon";
        }
        else if(dayOfWeekNumber == 3) {
            strDay = "tue";
        }
        else if(dayOfWeekNumber == 4) {
            strDay = "wed";
        }
        else if(dayOfWeekNumber == 5) {
            strDay = "thu";
        }
        else if(dayOfWeekNumber == 6) {
            strDay = "fri";
        }
        else if(dayOfWeekNumber == 7) {
            strDay = "sat";
        }
        selectDay=strDay;

Button4 = view.findViewById(R.id.button4);
Button4.setText(todayDate + "\n" +strDay);

calendar.add(Calendar.DATE,-1);
        mDate = calendar.getTime();
        //date of today
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

        //date of today
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

        //date of today
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

        //date of today
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


        GradientDrawable gradientDrawable2 = (GradientDrawable)ContextCompat.getDrawable(container.getContext(),R.drawable.mainpageweekbutton);
        gradientDrawable2.setColor(Color.rgb(54,82,163));
        Button4.setBackground(gradientDrawable2);
        Button4.setTextColor(Color.WHITE);
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GradientDrawable gradientDrawable = (GradientDrawable)ContextCompat.getDrawable(container.getContext(),R.drawable.mainpageweekbutton);
                gradientDrawable.setColor(Color.WHITE);
                Button3.setBackground(gradientDrawable);
                Button3.setTextColor(Color.rgb(54, 82, 163));
                Button2.setBackground(gradientDrawable);
                Button2.setTextColor(Color.rgb(54, 82, 163));
                Button5.setBackground(gradientDrawable);
                Button5.setTextColor(Color.rgb(54, 82, 163));
                Button6.setBackground(gradientDrawable);
                Button6.setTextColor(Color.rgb(54, 82, 163));

                GradientDrawable gradientDrawable2 = (GradientDrawable)ContextCompat.getDrawable(container.getContext(),R.drawable.mainpageweekbutton);
                gradientDrawable2.setColor(Color.rgb(54,82,163));
                Button4.setBackground(gradientDrawable2);
                Button4.setTextColor(Color.WHITE);
                //today
                Date currentDate = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                int dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeekNumber == 1){
                    selectDay = "sun";
                }
                else if(dayOfWeekNumber == 2){
                    selectDay = "mon";
                }
                else if(dayOfWeekNumber == 3) {
                    selectDay = "tue";
                }
                else if(dayOfWeekNumber == 4) {
                    selectDay = "wed";
                }
                else if(dayOfWeekNumber == 5) {
                    selectDay = "thu";
                }
                else if(dayOfWeekNumber == 6) {
                    selectDay = "fri";
                }
                else if(dayOfWeekNumber == 7) {
                    selectDay = "sat";
                }
                InitializeMovieData(container);
            }
        });

        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //color setting when buttons are clicked on mainpage
                GradientDrawable gradientDrawable = (GradientDrawable)ContextCompat.getDrawable(container.getContext(),R.drawable.mainpageweekbutton);
                gradientDrawable.setColor(Color.WHITE);
                Button3.setBackground(gradientDrawable);
                Button3.setTextColor(Color.rgb(54, 82, 163));
                Button4.setBackground(gradientDrawable);
                Button4.setTextColor(Color.rgb(54, 82, 163));
                Button2.setBackground(gradientDrawable);
                Button2.setTextColor(Color.rgb(54, 82, 163));
                Button6.setBackground(gradientDrawable);
                Button6.setTextColor(Color.rgb(54, 82, 163));
                GradientDrawable gradientDrawable2 = (GradientDrawable)ContextCompat.getDrawable(container.getContext(),R.drawable.mainpageweekbutton);
                gradientDrawable2.setColor(Color.rgb(54,82,163));
                Button5.setBackground(gradientDrawable2);
                Button5.setTextColor(Color.WHITE);

                //today
                Date currentDate = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DATE,+1);

                int dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeekNumber == 1){
                    selectDay = "sun";
                }
                else if(dayOfWeekNumber == 2){
                    selectDay = "mon";
                }
                else if(dayOfWeekNumber == 3) {
                    selectDay = "tue";
                }
                else if(dayOfWeekNumber == 4) {
                    selectDay = "wed";
                }
                else if(dayOfWeekNumber == 5) {
                    selectDay = "thu";
                }
                else if(dayOfWeekNumber == 6) {
                    selectDay = "fri";
                }
                else if(dayOfWeekNumber == 7) {
                    selectDay = "sat";
                }
                InitializeMovieData(container);
            }
        });
        Button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //color setting when buttons are clicked on mainpage
                GradientDrawable gradientDrawable = (GradientDrawable)ContextCompat.getDrawable(container.getContext(),R.drawable.mainpageweekbutton);
                gradientDrawable.setColor(Color.WHITE);
                Button3.setBackground(gradientDrawable);
                Button3.setTextColor(Color.rgb(54, 82, 163));
                Button4.setBackground(gradientDrawable);
                Button4.setTextColor(Color.rgb(54, 82, 163));
                Button5.setBackground(gradientDrawable);
                Button5.setTextColor(Color.rgb(54, 82, 163));
                Button2.setBackground(gradientDrawable);
                Button2.setTextColor(Color.rgb(54, 82, 163));
                GradientDrawable gradientDrawable2 = (GradientDrawable)ContextCompat.getDrawable(container.getContext(),R.drawable.mainpageweekbutton);
                gradientDrawable2.setColor(Color.rgb(54,82,163));
                Button6.setBackground(gradientDrawable2);
                Button6.setTextColor(Color.WHITE);

                //today
                Date currentDate = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DATE,+2);

                int dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeekNumber == 1){
                    selectDay = "sun";
                }
                else if(dayOfWeekNumber == 2){
                    selectDay = "mon";
                }
                else if(dayOfWeekNumber == 3) {
                    selectDay = "tue";
                }
                else if(dayOfWeekNumber == 4) {
                    selectDay = "wed";
                }
                else if(dayOfWeekNumber == 5) {
                    selectDay = "thu";
                }
                else if(dayOfWeekNumber == 6) {
                    selectDay = "fri";
                }
                else if(dayOfWeekNumber == 7) {
                    selectDay = "sat";
                }
                InitializeMovieData(container);
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //color setting when buttons are clicked on mainpage
                GradientDrawable gradientDrawable = (GradientDrawable)ContextCompat.getDrawable(container.getContext(),R.drawable.mainpageweekbutton);
                gradientDrawable.setColor(Color.WHITE);
                Button3.setBackground(gradientDrawable);
                Button3.setTextColor(Color.rgb(54, 82, 163));
                Button4.setBackground(gradientDrawable);
                Button4.setTextColor(Color.rgb(54, 82, 163));
                Button5.setBackground(gradientDrawable);
                Button5.setTextColor(Color.rgb(54, 82, 163));
                Button6.setBackground(gradientDrawable);
                Button6.setTextColor(Color.rgb(54, 82, 163));
                GradientDrawable gradientDrawable2 = (GradientDrawable)ContextCompat.getDrawable(container.getContext(),R.drawable.mainpageweekbutton);
                gradientDrawable2.setColor(Color.rgb(54,82,163));
                Button2.setBackground(gradientDrawable2);
                Button2.setTextColor(Color.WHITE);
                //today
                Date currentDate = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DATE,-2);

                int dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeekNumber == 1){
                    selectDay = "sun";
                }
                else if(dayOfWeekNumber == 2){
                    selectDay = "mon";
                }
                else if(dayOfWeekNumber == 3) {
                    selectDay = "tue";
                }
                else if(dayOfWeekNumber == 4) {
                    selectDay = "wed";
                }
                else if(dayOfWeekNumber == 5) {
                    selectDay = "thu";
                }
                else if(dayOfWeekNumber == 6) {
                    selectDay = "fri";
                }
                else if(dayOfWeekNumber == 7) {
                    selectDay = "sat";
                }
                InitializeMovieData(container);
            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //color setting when buttons are clicked on mainpage
                GradientDrawable gradientDrawable = (GradientDrawable)ContextCompat.getDrawable(container.getContext(),R.drawable.mainpageweekbutton);
                gradientDrawable.setColor(Color.WHITE);
                Button2.setBackground(gradientDrawable);
                Button2.setTextColor(Color.rgb(54, 82, 163));
                Button4.setBackground(gradientDrawable);
                Button4.setTextColor(Color.rgb(54, 82, 163));
                Button5.setBackground(gradientDrawable);
                Button5.setTextColor(Color.rgb(54, 82, 163));
                Button6.setBackground(gradientDrawable);
                Button6.setTextColor(Color.rgb(54, 82, 163));
                GradientDrawable gradientDrawable2 = (GradientDrawable)ContextCompat.getDrawable(container.getContext(),R.drawable.mainpageweekbutton);
                gradientDrawable2.setColor(Color.rgb(54,82,163));
                Button3.setBackground(gradientDrawable2);
                Button3.setTextColor(Color.WHITE);

                //today
                Date currentDate = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DATE,-1);

                int dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeekNumber == 1){
                    selectDay = "sun";
                }
                else if(dayOfWeekNumber == 2){
                    selectDay = "mon";
                }
                else if(dayOfWeekNumber == 3) {
                    selectDay = "tue";
                }
                else if(dayOfWeekNumber == 4) {
                    selectDay = "wed";
                }
                else if(dayOfWeekNumber == 5) {
                    selectDay = "thu";
                }
                else if(dayOfWeekNumber == 6) {
                    selectDay = "fri";
                }
                else if(dayOfWeekNumber == 7) {
                    selectDay = "sat";
                }
                InitializeMovieData(container);
            }
        });

        return view;
    }
    @Override
    public void onClick(View v){
        Intent intent = new Intent(getActivity(),Register.class);
        startActivity(intent);
    }

    public void InitializeMovieData(ViewGroup container) {
        pillDataList = new ArrayList<SampleData>();
        SharedPreferences preferences = this.getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
        String id = preferences.getString("id", "text");


        List list = new ArrayList();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pillDataList = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Object obj = postSnapshot.getValue();
                    Map map = (Map) obj;
                    if (map.get("email").equals(id) && (Boolean) map.get(selectDay)) {
                        list.add(obj);
                    }
                }
                for (int i = 0; i < list.size(); i++) {
                    Map tempData = ((Map) list.get(i));
                    String tempTime = tempData.get("hour") + ":" + tempData.get("min");
                    pillDataList.add(new SampleData(tempTime, (String) tempData.get("pillName")));
                }
                /*Collections.sort(pillDataList, new pillListComparator());*/
                ListView listView = (ListView) view.findViewById(R.id.pilllist);
                final MyAdapter myAdapter = new MyAdapter(container.getContext(), pillDataList);
                listView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });

        // Set the desired time for the notification
        int targetHour = 15;
        int targetMinute = 5;

        // Create a Calendar instance for the desired time
        Calendar targetTime = Calendar.getInstance();
        targetTime.set(Calendar.HOUR_OF_DAY, targetHour);
        targetTime.set(Calendar.MINUTE, targetMinute);
        targetTime.set(Calendar.SECOND, 0);
        targetTime.set(Calendar.MILLISECOND, 0);

        Intent notificationIntent = new Intent(getContext(), HomeFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        // Get the AlarmManager instance
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        // Set the notification to trigger at the specified time
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, targetTime.getTimeInMillis(), pendingIntent);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), NOTIFICATION_CHANNEL_ID)
                .setContentTitle("TITLE")
                .setContentText("TEXT")
                .setSmallIcon(R.drawable.propill_logo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence channelName = "노티피케이션 채널";
            String description = "해당 채널에 대한 설명";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
            channel.setDescription(description);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(1234, builder.build());
    }




    public void InitializeInventoryData(ViewGroup Container){
        InventoryList = new ArrayList<InventoryData>();
        SharedPreferences preferences = this.getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
        String id= preferences.getString("id", "text");
        List list = new ArrayList();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                InventoryList = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Object obj = postSnapshot.getValue();
                    Map map = (Map) obj;
                    if(map.get("email").equals(id)){
                        list.add(obj);
                    }
                }
                for(int i=0; i<list.size(); i++){
                    Map tempData2 = ((Map)list.get(i));
                    InventoryList.add(new InventoryData((String) tempData2.get("pillName"), (String) tempData2.get("pillInventory")));
                }
                ListView listViewsts = (ListView)view.findViewById(R.id.InventoryList);
                final InventoryAdapter inventoryAdapter = new InventoryAdapter(Container.getContext(),InventoryList);
                listViewsts.setAdapter(inventoryAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
/*

class pillListComparator implements Comparator<SampleData> {
    @Override
    public int compare(SampleData a, SampleData b){
        int ta = 0;
        String[] tal = a.getTime().split(":");
        ta += Integer.parseInt(tal[0])*60 + Integer.parseInt(tal[1]);
        int tb = 0;
        String[] tbl = b.getTime().split(":");
        tb += Integer.parseInt(tbl[0])*60 + Integer.parseInt(tbl[1]);
        if(ta > tb){
            return 1;
        } else if(ta < tb){
            return -1;
        } else{
            return 0;
        }
    }
}
*/
