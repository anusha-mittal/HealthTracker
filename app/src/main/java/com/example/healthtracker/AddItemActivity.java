package com.example.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {//implements LoaderCallbacks<String> {

    public static final String LOG_TAG = AddItemActivity.class.getSimpleName();
    public static final String REQUEST_URL = "";  //fill id
    TextView reqdCalorie,calculatedBMI,category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        reqdCalorie=findViewById(R.id.reqdCalorie);
        calculatedBMI=findViewById(R.id.calculatedBMI);
        category=findViewById(R.id.category);

        Intent intentThatStartedThis = getIntent();
        int bmi= intentThatStartedThis.getIntExtra("bmi", 0);
        int cal_count= intentThatStartedThis.getIntExtra("cal_count", 0);
        int obesity= intentThatStartedThis.getIntExtra("obesity", 0);
        calculatedBMI.setText(String.valueOf(bmi));
        reqdCalorie.setText(String.valueOf(cal_count));
        category.setText(obesity);
      //  getLoaderManager().initLoader(1, null, this).forceLoad();

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



