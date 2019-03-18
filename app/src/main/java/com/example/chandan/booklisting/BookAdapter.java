package com.example.chandan.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class BookAdapter extends ArrayAdapter<book>{

    public BookAdapter(Context context,List<book> booklist){super(context,0,booklist);}


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ListView=convertView;
        if(ListView==null){
            ListView = LayoutInflater.from(getContext()).inflate(R.layout.listitem,parent,false);
        }

        LinearLayout rootview = (LinearLayout) ListView.findViewById(R.id.rootview);
        TextView textView = (TextView) ListView.findViewById(R.id.text);
        TextView author = (TextView) ListView.findViewById(R.id.author);
        ImageView imageView = (ImageView) ListView.findViewById(R.id.image);

        final book currentitem = getItem(position);

        textView.setText(currentitem.getBookname());
        author.setText("- By "+currentitem.getAuthor());
        imageView.setImageBitmap(currentitem.getImage());



        /*imageView.setImageBitmap(null);
        imageView.setTag(currentitem.getImgres());
        new ImageAsyncTask().execute(imageView);*/


        return ListView;
    }

    /*private class ImageAsyncTask extends AsyncTask<ImageView,Void,Bitmap>{

        ImageView i=null;

        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            i = imageViews[0];
            Bitmap bitmap = null;
            try{

                URL imglink = new URL((String)i.getTag());
                InputStream inputStream = imglink.openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
            catch (Exception e){
                Log.e("BookAdapter","image exception",e);
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            i.setImageBitmap(bitmap);
        }
    }*/
}
