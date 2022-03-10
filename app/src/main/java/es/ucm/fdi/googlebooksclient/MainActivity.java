package es.ucm.fdi.googlebooksclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private static final int BOOK_LOADER_ID = 0; //seguramente no sea 0
    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks();

    private static TextInputEditText bookAuthors;
    private static TextInputEditText bookTitle;
    private static android.widget.RadioGroup radioGroupSelection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //conectando ids del layout a valores
        MainActivity.bookAuthors = findViewById(R.id.textInputEditText);
        MainActivity.bookTitle = findViewById(R.id.textInputEditText2);
        MainActivity.radioGroupSelection = findViewById(R.id.RadioGroupId);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(BOOK_LOADER_ID) != null){
            loaderManager.initLoader(BOOK_LOADER_ID,null, bookLoaderCallbacks);
        }
    }

    public void searchBooks (android.view.View view){
        String queryString1 = String.valueOf(bookAuthors.getText().toString());
        String queryString2 = String.valueOf(bookTitle.getText().toString());
        String queryString = queryString1 + queryString2;

        //no se si está bien
        String printType = String.valueOf(radioGroupSelection.getCheckedRadioButtonId());
        
        Bundle queryBundle = new Bundle();
        //no se cuales son los valores de EXTRAQUERY y EXTRAPRINTTYPE ni de donde salen
        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);
        LoaderManager.getInstance(this)
                .restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);
    }
}