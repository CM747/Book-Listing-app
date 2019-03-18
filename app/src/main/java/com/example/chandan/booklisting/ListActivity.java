package com.example.chandan.booklisting;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<book>> {

    public static String url;
    private BookAdapter mAdapter;
    TextView textView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        textView = findViewById(R.id.error);

        getUrl();
        Log.d("ListAct","got Url"+url);

        ListView listView = (ListView)findViewById(R.id.list_items);
        mAdapter = new BookAdapter(this,new ArrayList<book>());
        listView.setAdapter(mAdapter);
        listView.setEmptyView(textView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri webpage = Uri.parse(mAdapter.getItem(position).getSelflink());
                Intent intent = new Intent(Intent.ACTION_VIEW,webpage);

                if(intent.resolveActivity(getPackageManager())!=null)
                    startActivity(intent);
            }
        });


        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConnected = (networkInfo!=null && networkInfo.isConnectedOrConnecting());

        if(isConnected) {
            getLoaderManager().initLoader(0, null, this);
            getLoaderManager().initLoader(1, null, this);
            getLoaderManager().initLoader(2, null, this);
            getLoaderManager().initLoader(3, null, this);
            getLoaderManager().initLoader(4, null, this);
        }
        else {
            ((LinearLayout)findViewById(R.id.progress)).setVisibility(View.GONE);
            textView.setText("Check Your Connection");
        }
    }

    private void getUrl(){
        StringBuilder h = new StringBuilder("https://www.googleapis.com/books/v1/volumes?q=");

        if(!(TextUtils.isEmpty(SearchActivity.bookname))){
            String[] s = SearchActivity.bookname.split(" ");
            h.append(s[0]);
            for(int i = 1;i<s.length;i++)
                h.append("+" + s[i]);
        }

        if(!(TextUtils.isEmpty(SearchActivity.bookname)) && !(TextUtils.isEmpty(SearchActivity.authorname)))
            h.append("&");

        if(!(TextUtils.isEmpty(SearchActivity.authorname))){
            String[] k = SearchActivity.authorname.split(" ");
            h.append("inauthor:"+k[0]);
            for(int i = 1;i<k.length;i++)
                h.append("+" + k[i]);
        }

        url = h.toString();
    }

    @Override
    public Loader<List<book>> onCreateLoader(int id, Bundle args) {
        return new BooksLoader(getApplicationContext(),id);
    }

    @Override
    public void onLoadFinished(Loader<List<book>> loader, List<book> data) {
        ((LinearLayout) findViewById(R.id.progress)).setVisibility(View.GONE);

        if(data!=null && !data.isEmpty()){
            mAdapter.addAll(data);
        }
        textView.setText("No Books Found");
    }

    @Override
    public void onLoaderReset(Loader<List<book>> loader) {
        mAdapter.clear();
    }
}
