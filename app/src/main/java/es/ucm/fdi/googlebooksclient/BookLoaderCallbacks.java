package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<String>{
    public static final String EXTRA_QUERY = "extra query";
    public static final String EXTRA_PRINT_TYPE = "extra print type";

    public BookLoaderCallbacks() {}
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        //TODO fix context
        return new BookLoader(context, args.getString(EXTRA_QUERY), args.getString(EXTRA_PRINT_TYPE));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
