package com.alifyz.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private ListView listView;
    private ArrayAdapter<News> adapter;
    private ProgressBar progressBar;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        emptyView = (TextView) findViewById(R.id.emptyView);

        getLoaderManager().initLoader(0, null, this).forceLoad();

        if (!isNetworkAvailable()) {
            emptyView.setText(getString(R.string.noInternet));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent newsLink = new Intent(Intent.ACTION_VIEW, Uri.parse(adapter.getItem(i).getmLink()));
                if (newsLink.resolveActivity(getPackageManager()) != null) {
                    startActivity(newsLink);
                }
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(NewsActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> finalNews) {
        adapter = new NewsAdapter(getApplicationContext(), finalNews);
        listView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }

    //This function was provided thanks to the User Alexandre Jasmin, from StackOverflow
    //https://goo.gl/GHA7wt
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
