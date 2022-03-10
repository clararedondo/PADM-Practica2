package es.ucm.fdi.googlebooksclient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<String>{
    public static final String EXTRA_QUERY = null;
    public static final String EXTRA_PRINT_TYPE = null;

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
     //fix this
        BookLoader bookLoader  = new BookLoader(context);
        return bookLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
