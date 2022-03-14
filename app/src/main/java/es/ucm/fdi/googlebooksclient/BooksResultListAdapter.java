package es.ucm.fdi.googlebooksclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BooksResultListAdapter extends RecyclerView.Adapter<BooksResultListAdapter.ViewHolder>{

    private ArrayList<BookInfo> mBooksData;
    private LayoutInflater mInflater;
    private Context context;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView bookItemView;
        private BooksResultListAdapter mAdapter;

        public ViewHolder(View itemView, BooksResultListAdapter adapter) {
            super(itemView);

            this.bookItemView = itemView.findViewById(R.id.CardView);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TextView text = this.bookItemView.findViewById(R.id.textView4);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(text.getText().toString()));
            context.startActivity(intent);
        }
    }

    public BooksResultListAdapter(Context context, List<BookInfo> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mBooksData = (ArrayList<BookInfo>) data;
    }

    public BooksResultListAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mBooksData = new ArrayList<BookInfo>();
    }

    @Override
    public BooksResultListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.recycle_view_item, parent, false);
        return new ViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(BooksResultListAdapter.ViewHolder holder, int position) {
        BookInfo mCurrent = this.mBooksData.get(position);
        TextView title = holder.bookItemView.findViewById(R.id.textView2);
        TextView author = holder.bookItemView.findViewById(R.id.textView3);
        TextView url = holder.bookItemView.findViewById(R.id.textView4);
        title.setText(mCurrent.getTitle());
        author.setText(mCurrent.getAuthors());
        url.setText(mCurrent.getInfoLink());

    }

    @Override
    public int getItemCount() {
        return this.mBooksData.size();
    }

    public void setBooksData(List<BookInfo> data) {
        this.mBooksData = (ArrayList<BookInfo>) data;
    }
}
