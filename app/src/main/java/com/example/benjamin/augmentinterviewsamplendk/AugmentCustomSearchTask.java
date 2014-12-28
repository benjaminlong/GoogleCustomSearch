package com.example.benjamin.augmentinterviewsamplendk;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Search;

import java.util.ArrayList;

public class AugmentCustomSearchTask extends AsyncTask<String, Integer, ArrayList<Search>> {

    private Customsearch.Cse mCse;

    private AugmentSearchResultAdapter mAdapter;

    public AugmentCustomSearchTask(AugmentSearchResultAdapter adapter) {

        this.mAdapter = adapter;
    }

    public void setCustomSearch(Customsearch.Cse cse) {

        this.mCse = cse;
    }

    protected ArrayList<Search> doInBackground(String... queries) {
        int count = queries.length;
        try {
            ArrayList<Search> result = new ArrayList<Search>();
            for (int i = 0; i < count; i++) {
                Customsearch.Cse.List list = this.mCse.list(queries[i]);
                list.setCx("014147428684274519266:p8cautsmgta");
                list.setKey("AIzaSyDX3WYbARco4XDUZUiSPu351wxpifklj58");

                result.add(list.execute());
            }

            return result;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    protected void onProgressUpdate(Integer... progress) {
        // Todo: Use publishProgress from doInBackground to
        // start onProgressUpdate method.
    }

    protected void onPostExecute(ArrayList<Search> response) {
        if (response == null) {
            // Todo: No result Update the UI according to it
            return;
        }

        // Update the adapter content
        this.mAdapter.setResponse(response);
        this.mAdapter.notifyDataSetChanged();
    }
}
