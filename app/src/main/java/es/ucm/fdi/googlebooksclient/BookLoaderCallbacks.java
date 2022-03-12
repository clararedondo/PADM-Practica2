package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>>{
    public static final String EXTRA_QUERY = "extra query";
    public static final String EXTRA_PRINT_TYPE = "extra print type";
    Context context;

    public BookLoaderCallbacks(Context context) {
        this.context = context;
    }

    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, @Nullable Bundle args) {
        //TODO fix context
        return new BookLoader(context, args.getString(EXTRA_QUERY), args.getString(EXTRA_PRINT_TYPE));
    }

    @Override
    public void onLoadFinished(Loader<List<BookInfo>> loader, List<BookInfo> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<BookInfo>> loader) {

    }
}
