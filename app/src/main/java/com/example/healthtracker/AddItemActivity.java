package com.example.healthtracker;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.healthtracker.PhysicalHealthActivity.bmi;
import static com.example.healthtracker.PhysicalHealthActivity.cal_count;
import static com.example.healthtracker.PhysicalHealthActivity.obesity;

public class AddItemActivity extends AppCompatActivity {//implements LoaderCallbacks<String> {

    public static final String LOG_TAG = AddItemActivity.class.getSimpleName();
    public static final String REQUEST_URL = "";  //fill id
    TextView reqdCalorie,calculatedBMI,category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        calculatedBMI.setText(String.valueOf(bmi));
        reqdCalorie.setText(String.valueOf(cal_count));
        category.setText(obesity);
        //getLoaderManager().initLoader(1, null, this).forceLoad();

    }

    /*@Override
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

    }*/

}



