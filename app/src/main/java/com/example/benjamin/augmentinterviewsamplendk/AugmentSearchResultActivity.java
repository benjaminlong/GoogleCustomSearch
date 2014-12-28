package com.example.benjamin.augmentinterviewsamplendk;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

import java.io.IOException;
import java.util.ArrayList;


public class AugmentSearchResultActivity extends ActionBarActivity {

    private AugmentSearchResultAdapter mAdapter;

    /** Google API client */
    private Customsearch mCustomSearch;

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    private static HttpRequestInitializer httpRequestInitializer;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private Customsearch.Cse mCse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augment_search_result);

        String query = getIntent().getExtras().getString("query");

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

        this.mAdapter = new AugmentSearchResultAdapter(this, new ArrayList<Result>());

        AugmentCustomSearchTask searchTask = new AugmentCustomSearchTask(this.mAdapter);
        searchTask.setCustomSearch(this.mCse);
        searchTask.execute(query);

        ListView listView = (ListView) findViewById(R.id.search_result_list_view);
        listView.setAdapter(this.mAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_augment_search_result, menu);
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
}
