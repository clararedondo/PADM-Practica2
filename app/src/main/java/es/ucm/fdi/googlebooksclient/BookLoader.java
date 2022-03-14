package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.net.Uri;

import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {

    private final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    private final String QUERY_PARAM = "q";
    private final String PRINT_TYPE = "printType";
    private String queryString;
    private String printType;

    public BookLoader(Context context, String queryString, String printType) {
        super(context);
        this.queryString = queryString;
        this.printType = printType;
    }

    @Override
    public List<BookInfo> loadInBackground() {
        return getBookInfoJson(this.queryString, this.printType);
    }

    public void onStartLoading(){
        forceLoad();
    }

    public List<BookInfo> getBookInfoJson(String queryString, String printType) {
        Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(PRINT_TYPE, printType)
                .appendQueryParameter("maxResults", "40")
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
            if (response != 200) {
                return new ArrayList<BookInfo>();
            }
            is = conn.getInputStream();
            return BookInfo.fromJsonResponse(convertIsToString(is));
        } catch (Exception e) {
            return new ArrayList<BookInfo>();
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
