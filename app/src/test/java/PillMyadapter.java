import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.propill.R;

import java.util.ArrayList;

public class PillMyadapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<mainpage_pilldiary_data_class> sample;

    public PillMyadapter(Context context, ArrayList<mainpage_pilldiary_data_class> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public Object getItem(int i) {
        return sample.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.itemlistbar, null);

        TextView time = (TextView) view.findViewById(R.id.time);
        TextView pillname = (TextView)view.findViewById(R.id.pillname);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView6);


        time.setText(sample.get(position).getTime());
        pillname.setText(sample.get(position).getPillName());
        imageView.setImageResource(sample.get(position).getimageView6());


        return view;
    }
}
