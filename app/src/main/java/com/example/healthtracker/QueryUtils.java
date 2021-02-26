package com.example.healthtracker;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import static com.example.healthtracker.AddItemActivity.REQUEST_URL;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();;

    private QueryUtils() {

    }

    static String extractData(String url) {
        URL Url = URLResponse(REQUEST_URL);

        String JSONResponse = null;
        try{
            JSONResponse = makeHTTPRequest(Url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String res = extractItem(JSONResponse);
        return res;
    }

    private static URL URLResponse(String str)
    {
        URL url = null;
        try{
            url = new URL(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHTTPRequest(URL url) throws IOException
    {
        String JSONResponse = "";

        if(url == null)
            return JSONResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                JSONResponse = readFromStream(inputStream);
            }
            else
            {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }

        }
        return JSONResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if(inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
        }
        return builder.toString();
    }
    private static String extractItem(String SAMPLE_JSON_RESPONSE) {

        if(TextUtils.isEmpty(SAMPLE_JSON_RESPONSE))
            return null;

        String res = "";

        try {

            JSONObject JSONitem = new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray features = JSONitem.getJSONArray("features");
            for(int i=0;i<features.length();i++)
            {
                JSONObject earthquake = features.getJSONObject(i);
                JSONObject properties = earthquake.getJSONObject("properties");

                Double mag = properties.getDouble("mag");
                Long time = properties.getLong("time");
                String loc = properties.getString("place");

            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return res;
    }


}






