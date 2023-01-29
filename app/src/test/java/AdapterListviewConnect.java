import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.propill.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<mainpage_pilldiary_data_class> pilllist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        this.InitializeMovieData();

        ListView listView = (ListView)findViewById(androidx.appcompat.R.id.listview);
        final PillMyadapter myAdapter = new PillMyadapter(this,pilllist);

        listView.setAdapter(myAdapter);
            }


    public void InitializeMovieData()
    {
        pilllist = new ArrayList<mainpage_pilldiary_data_class>();

        pilllist.add(new mainpage_pilldiary_data_class(R.drawable.mainapgepilldiary, "11:00"," 타이레놀"));
        pilllist.add(new mainpage_pilldiary_data_class(R.drawable.mainapgepilldiary, "15:00"," 비타민"));
        pilllist.add(new mainpage_pilldiary_data_class(R.drawable.mainapgepilldiary, "17:00"," 아무거나"));
    }
}
