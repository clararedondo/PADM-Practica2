package es.ucm.fdi.googlebooksclient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int BOOK_LOADER_ID = 0;

    private BookLoaderCallbacks bookLoaderCallbacks = new BookLoaderCallbacks(this);

    private static TextInputEditText bookAuthors;
    private static TextInputEditText bookTitle;
    private static RecyclerView mRecyclerView;
    private static android.widget.RadioGroup radioGroupSelection;
    private static TextView resultText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.bookAuthors = findViewById(R.id.textInputEditText);
        MainActivity.bookTitle = findViewById(R.id.textInputEditText2);
        MainActivity.radioGroupSelection = findViewById(R.id.RadioGroupId);
        MainActivity.resultText = findViewById(R.id.textView5);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(BOOK_LOADER_ID) != null){
            loaderManager.initLoader(BOOK_LOADER_ID,null, bookLoaderCallbacks);
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(new BooksResultListAdapter(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void searchBooks (android.view.View view){
        MainActivity.resultText.setText("Loading...");
        String queryString1 = String.valueOf(bookAuthors.getText().toString());
        String queryString2 = String.valueOf(bookTitle.getText().toString());
        String queryString = (queryString1 + " " + queryString2).trim();

        String printType;
        switch (radioGroupSelection.getCheckedRadioButtonId()) {
            case R.id.books:
                printType = "books";
                break;
            case R.id.magazines:
                printType = "magazines";
                break;
            default:
                printType = "all";

        }
        
        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);
        LoaderManager.getInstance(this).restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);
    }

    public void updateBooksResultList(List<BookInfo> bookInfos) {
        BooksResultListAdapter adapter= (BooksResultListAdapter) mRecyclerView.getAdapter();
        adapter.setBooksData(bookInfos);
        adapter.notifyDataSetChanged();
    }

    public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>>{
        public static final String EXTRA_QUERY = "extra query";
        public static final String EXTRA_PRINT_TYPE = "extra print type";

        private Context context;

        public BookLoaderCallbacks(Context context) {
            this.context = context;
        }

        @Override
        public Loader<List<BookInfo>> onCreateLoader(int id, @Nullable Bundle args) {
            return new BookLoader(this.context, args.getString(EXTRA_QUERY), args.getString(EXTRA_PRINT_TYPE));
        }

        @Override
        public void onLoadFinished(Loader<List<BookInfo>> loader, List<BookInfo> data) {
            if (data.size() != 0) {
                MainActivity.resultText.setText("Results");
            }
            else {
                MainActivity.resultText.setText("No Results Found");
            }

            updateBooksResultList(data);
        }

        @Override
        public void onLoaderReset(Loader<List<BookInfo>> loader) {

        }
    }
}