package com.example.benjamin.augmentinterviewsamplendk;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class AugmentListAdapter extends BaseAdapter
{
    private ArrayList<HashMap<String, String>> mData;
    private static LayoutInflater inflater = null;

    // ------------------------------------------------------------------------
    public AugmentListAdapter(Activity act, ArrayList<HashMap<String, String>> d)
    {
        mData=d;
        inflater = (LayoutInflater)act.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    // ------------------------------------------------------------------------
    public int getCount()
    {
        return mData.size();
    }

    // ------------------------------------------------------------------------
    public HashMap<String, String> getItem(int position)
    {
        return this.mData.get(position);
    }

    // ------------------------------------------------------------------------
    public long getItemId(int position)
    {
        return position;
    }

    // ------------------------------------------------------------------------
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        if(convertView==null)
            view = inflater.inflate(R.layout.list_row, null);

        // title
        TextView title = (TextView)view.findViewById(R.id.manualTitle);
        // info
        TextView info = (TextView)view.findViewById(R.id.manualInfo);

        HashMap<String, String> item = new HashMap<String, String>();
        item = mData.get(position);

        // Setting all values in listView
        // Title list
        title.setText(item.get("title"));

        // Info List
        info.setText(item.get("subTitle"));

        return view;
    }
}
