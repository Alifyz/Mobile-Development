package com.example.alify.bakingapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alify.bakingapp.network.NetworkUtils;
import com.example.alify.bakingapp.recipes.RecipeObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<RecipeObject>>{

    private final String TAG_NAME = MainActivity.class.getSimpleName();
    private final int LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();

    }

    @Override
    public Loader<List<RecipeObject>> onCreateLoader(int i, Bundle bundle) {
        return new DownloadRecipes(this);
    }

    @Override
    public void onLoadFinished(Loader<List<RecipeObject>> loader, List<RecipeObject> recipeObjects) {

    }

    @Override
    public void onLoaderReset(Loader<List<RecipeObject>> loader) {

    }


    private static class DownloadRecipes extends AsyncTaskLoader<List<RecipeObject>> {

        public DownloadRecipes(Context context) {
            super(context);
        }

        @Override
        public List<RecipeObject> loadInBackground() {
            String json = NetworkUtils.makeHttpRequest();
            NetworkUtils.extractRecipe(json);
            return null;
        }
    }
}
