package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BookLoader extends AsyncTaskLoader<String> {

    private final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    private final String QUERY_PARAM = "q";
    private final String PRINT_TYPE = "printType";
    private String queryString;
    private String printType;

    public BookLoader(@NonNull Context context, String queryString, String printType) {
        super(context);
        this.queryString = queryString;
        this.printType = printType;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return getBookInfoJson(this.queryString, this.printType);
    }

    public void onStartLoading(){
        forceLoad();
    }

    public String getBookInfoJson(String queryString, String printType) {
        String contentAsString;
        Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(PRINT_TYPE, printType)
                .build();

        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            URL requestURL = new URL(builtURI.toString());
            conn = (HttpURLConnection) requestURL.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            int response = conn.getResponseCode();

            is = conn.getInputStream();
            return convertIsToString(is);
        } catch (Exception e) {
            return null;
        } finally {
            conn.disconnect();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String convertIsToString(InputStream stream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(stream));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line + "\n");
        }
        if (builder.length() == 0) {
            return null;
        }
        return builder.toString();

    }
}
