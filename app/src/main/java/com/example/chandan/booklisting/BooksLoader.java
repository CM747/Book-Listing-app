package com.example.chandan.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BooksLoader extends AsyncTaskLoader<List<book>> {

    private int id;

    public BooksLoader(Context context,int i){
        super(context);
        id = i;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<book> loadInBackground() {
        List<book> mbooks = QueryUtils.fetchlistofbooks(ListActivity.url + "&maxResults=" + 5 + "&startIndex=" + 5*id);
        return mbooks;
    }
}
