package com.example.guadiannews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String NEWS_REQUEST_URL ="http://content.guardianapis.com/search?api-key=test";

    private static final int NEWS_LOADER_ID = 1;

    private TextView emptyText;

    NewsAdapter mAdapter;
    ProgressBar progressBar;

    public static final String LOG_TAG = NewsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ListView newsListView = (ListView) findViewById(R.id.list);

        emptyText = (TextView) findViewById(R.id.tvEmptyView);
        //set the empty textview if no news are found
        newsListView.setEmptyView(emptyText);

        mAdapter = new NewsAdapter(this, 0, new ArrayList<News>());

        // Get a reference to the ListView, and attach the adapter to the listView.
        newsListView.setAdapter(mAdapter);

        //Set OnItemClick listeners on the list items
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Find the current earthquake that was clicked on
                News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri url = Uri.parse(currentNews.getmUrl());
                Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                urlIntent.setData(url);
                startActivity(urlIntent);

                // This says something like "Open with" or "Choose app..."
                String title = getString(R.string.open_with);
                // Create intent to show the chooser dialog
                urlIntent = Intent.createChooser(urlIntent, title);

                // Verify that the intent will resolve to an activity
                if (urlIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(urlIntent);
                }
            }
        });

        // Check if there is internet connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

        if (isConnected) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        }
        else{
            progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
            progressBar.setVisibility(View.GONE); // Hide progress bar
            emptyText = (TextView) findViewById(R.id.tvEmptyView);
            emptyText.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        Uri baseUri = Uri.parse(NEWS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        //Hide the progress bar
        progressBar.setVisibility(View.GONE);

        // Clear the adapter of previous news data
        mAdapter.clear();

        //Set empty text in case there is no news to display
        emptyText = (TextView) findViewById(R.id.tvEmptyView);
        emptyText.setText(R.string.empty_text);

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();

    }
}
