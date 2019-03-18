package com.example.chandan.booklisting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    public static String bookname;
    public static String authorname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final EditText name = (EditText) findViewById(R.id.name);
        final EditText author = (EditText)findViewById(R.id.author);

        LinearLayout search = (LinearLayout) findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookname = name.getText().toString();
                authorname = author.getText().toString();

                if(TextUtils.isEmpty(bookname) && TextUtils.isEmpty(authorname))
                    Toast.makeText(getApplicationContext(),"Enter atleast 1 field",Toast.LENGTH_SHORT).show();
                else
                    startActivity(new Intent(SearchActivity.this,ListActivity.class));
            }
        });
    }
}
