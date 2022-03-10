package es.ucm.fdi.googlebooksclient;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BookLoader extends AsyncTaskLoader<String> {

    public BookLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return null;
    }

    public void onStartLoading(){
        forceLoad();
    }

    public String getBookInfoJson(String queryString, String printType){
        //aqui va el codigo para hacer peticion al servicio
        return "";
    }
}
