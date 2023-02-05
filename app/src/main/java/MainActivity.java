import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.propill.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<SampleData> pillDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        this.InitializeMovieData();

        ListView listView = (ListView)findViewById(R.id.pilllist);
        final MyAdapter myAdapter = new MyAdapter(this,pillDataList);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),
                        myAdapter.getItem(position).getPillname(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void InitializeMovieData()
    {
        pillDataList = new ArrayList<SampleData>();

        pillDataList.add(new SampleData(R.drawable.mainapgepilldiary, "미션임파서블","15세 이상관람가"));
        pillDataList.add(new SampleData(R.drawable.mainapgepilldiary, "아저씨","19세 이상관람가"));
        pillDataList.add(new SampleData(R.drawable.mainapgepilldiary, "어벤져스","12세 이상관람가"));
    }
}