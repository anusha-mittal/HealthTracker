package com.example.healthtracker;

import android.content.Loader;
import android.os.Bundle;

import android.app.LoaderManager.LoaderCallbacks;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity implements LoaderCallbacks<String> {

    public static final String LOG_TAG = AddItemActivity.class.getSimpleName();
    public static final String REQUEST_URL = "https://api.spoonacular.com/recipes/716429/information";  //fill id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        getLoaderManager().initLoader(1, null, this).forceLoad();

    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return null;
    }

    
    
    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
            TextView textView = (TextView)findViewById(R.id.reqdCalorie);
            textView.clearComposingText();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

}



