package com.example.benjamin.augmentinterviewsamplendk;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

import java.util.ArrayList;

public class AugmentSearchResultAdapter extends BaseAdapter {
    private ArrayList<Result> mData;
    private static LayoutInflater inflater = null;

    // ------------------------------------------------------------------------
    public AugmentSearchResultAdapter(Activity act, ArrayList<Result> d)
    {
        mData=d;
        inflater = (LayoutInflater)act.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    // ------------------------------------------------------------------------
    public void setResponse(ArrayList<Search> data) {
        int count = data.size();
        for (int i = 0; i < count; i++) {
            if (!data.get(i).containsKey("items")) {
                continue;
            }

            this.mData.addAll(data.get(i).getItems());
        }
    }

    // ------------------------------------------------------------------------
    public int getCount()
    {
        return mData.size();
    }

    // ------------------------------------------------------------------------
    public Result getItem(int position)
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
            view = inflater.inflate(R.layout.result_list_row, null);

        // title
        TextView title = (TextView)view.findViewById(R.id.search_result_title);
        // url
        TextView url = (TextView)view.findViewById(R.id.search_result_link_url);
        // snippet
        TextView snippet = (TextView)view.findViewById(R.id.search_result_snippet);
        // image
        ImageView image = (ImageView)view.findViewById(R.id.search_result_thumbnail);

        Result item = mData.get(position);

        // Setting all values in listView
        // Title list
        title.setText(item.getTitle());
        // Info List
        url.setText(item.getLink());
        // Snippet List
        snippet.setText(item.getSnippet());
        // show The Image
//        Result.Image im = item.getImage();
//        String link = im.getThumbnailLink();
//
//        new AugmentDownloadImageTask(image).execute(item.getImage().getThumbnailLink());

        return view;
    }
}
