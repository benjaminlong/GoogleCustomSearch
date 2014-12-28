package com.example.benjamin.augmentinterviewsamplendk;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.app.ListActivity;

//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AugmentListActivity extends ListActivity {
    /** This is the Adapter being used to display the list's data */
    private AugmentListAdapter mAdapter;

    /** Google API client */
    private Customsearch mCustomSearch;

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    private static HttpRequestInitializer httpRequestInitializer;

    private Customsearch.Cse mCse;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_list);

        try {
            httpRequestInitializer = new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {
                    request.setConnectTimeout(20 * 1000);
                    request.setReadTimeout(10 * 1000);
                }
            };

            // It is possible to use GoogleNetHttpTransport.newTrustedTransport()
            // Reach java.security.KeyStoreException: java.security.NoSuchAlgorithmException:
            // KeyStore JKS implementation not found
            // Using ApacheHttpTransport instead.
            httpTransport = new ApacheHttpTransport();

            this.mCustomSearch = new Customsearch(httpTransport,
                    JSON_FACTORY, httpRequestInitializer);

            this.mCse = this.mCustomSearch.cse();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // Todo: Recover list from c++ library
//        ArrayMap<String, String> mListMap = this.getList();
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        String[] values = new String[] { "Android", "iPhone", "Windows Mobile" };

        for (int i = 0; i < values.length; ++i) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("title", values[i]);
            item.put("subTitle", "Search this word on the given website");
            list.add(item);
        }

        this.mAdapter = new AugmentListAdapter(this, list);
        this.setListAdapter(this.mAdapter);
        this.getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
        String query = this.mAdapter.getItem(position).get("title");
        Log.v("DEBUG", query);

//        AugmentCustomSearchTask searchTask = new AugmentCustomSearchTask();
//        searchTask.setCustomSearch(this.mCse);
//        searchTask.execute(query);

        Intent showResult = new Intent(getApplicationContext(),
                AugmentSearchResultActivity.class);
        showResult.putExtra("query", query);

        // Verify that the intent will resolve to an activity
        if (showResult.resolveActivity(getPackageManager()) != null) {
            startActivity(showResult);
        }
    }
}
