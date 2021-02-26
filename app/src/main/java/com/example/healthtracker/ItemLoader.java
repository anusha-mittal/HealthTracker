package com.example.healthtracker;

import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;

public class ItemLoader extends AsyncTaskLoader<String> {

    private String url;
    public ItemLoader(Context context,String URL) {
        super(context);
        url = URL;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        if (url == null) {
            return null;
        }
        String calories = QueryUtils.extractData(url);
        return calories;
    }
}