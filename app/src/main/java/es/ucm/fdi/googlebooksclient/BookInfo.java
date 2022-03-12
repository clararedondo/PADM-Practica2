package es.ucm.fdi.googlebooksclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookInfo {
    private String title;
    private String authors;
    private URL infoLink;

    public BookInfo(String title, String authors, URL infoLink) {
        this.title = title;
        this.authors = authors;
        this.infoLink = infoLink;
    }

    public static List<BookInfo> fromJsonResponse(String s) throws JSONException, MalformedURLException {
        List<BookInfo> ret_list = new ArrayList<BookInfo>();
        JSONObject data = new JSONObject(s);
        JSONArray book_list = data.getJSONArray("items");
        int i = 0;
        while (!book_list.isNull(i)) {
            JSONObject book = (JSONObject) book_list.get(i);
            JSONArray author_list = book.getJSONObject("volumeInfo").getJSONArray("authors");
            String authors = author_list.getString(0);
            int j = 1;
            while (!author_list.isNull(j)) {
                authors = authors + ", " + author_list.getString(j);
                j++;
            }
            ret_list.add(new BookInfo(book.getJSONObject("volumeInfo").getString("title"),
                    authors,
                    new URL(book.getString("selfLink"))));
        i++;
        };
        return ret_list;
    }
}
