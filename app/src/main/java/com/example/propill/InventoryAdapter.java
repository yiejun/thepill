package com.example.propill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class InventoryAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<InventoryData> sample;

public InventoryAdapter (Context context, ArrayList<InventoryData> data) {
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
        View view = mLayoutInflater.inflate(R.layout.itemlist_pill_inventory, null);

        TextView pillname = (TextView)view.findViewById(R.id.itemPillName);
        TextView inventory = (TextView)view.findViewById(R.id.itemPillInventory);

        pillname.setText(sample.get(position).getPillname());
        inventory.setText(sample.get(position).getInventory());

        return view;
    }

}
