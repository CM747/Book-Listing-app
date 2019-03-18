package com.example.chandan.booklisting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class QueryUtils {

    private static String JSON_RESPONSE="";

    private QueryUtils(){
    }

    public static List<book> fetchlistofbooks(String purl){
        if(TextUtils.isEmpty(purl))
            return null;

        URL url = createUrl(purl);
        getjsonresponse(url);

        ArrayList<book> books=new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(JSON_RESPONSE);
            JSONArray items = jsonObject.getJSONArray("items");
            JSONObject volumeinfo, imagelinks;
            JSONArray authors;

            Log.d("QueryUtils","items:"+items.length());

            book item;
            Bitmap bitmap=null;

            for (int i=0;i<items.length();i++){
                volumeinfo = items.getJSONObject(i).getJSONObject("volumeInfo");
                imagelinks = volumeinfo.getJSONObject("imageLinks");
                authors = volumeinfo.getJSONArray("authors");

                try{
                    URL imglink = new URL(imagelinks.getString("thumbnail"));
                    InputStream inputStream = imglink.openStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }catch (Exception e){}

                item = new book(volumeinfo.getString("title"),authors.getString(0),bitmap,volumeinfo.getString("infoLink"));

                Log.d("QueryUtils","item no:"+i);

                books.add(item);
            }
        }catch (JSONException e){}

        Log.d("QueryUtils","found: "+books.size());

        return books;
    }

    private static URL createUrl(String u){
        URL murl = null;
        try {
            murl = new URL(u);
        }catch (MalformedURLException e){}

        return murl;
    }

    private static void  getjsonresponse(URL u){
        HttpsURLConnection httpsURLConnection=null;
        InputStream inputStream = null;
        try {
            httpsURLConnection = (HttpsURLConnection)u.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.connect();
            StringBuilder stringBuilder = new StringBuilder();

            if(httpsURLConnection.getResponseCode()==200){
                inputStream = httpsURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = bufferedReader.readLine();
                while(line!=null){
                    stringBuilder.append(line);
                    line=bufferedReader.readLine();
                }
                JSON_RESPONSE = stringBuilder.toString();
            }

        }catch (IOException e){}
    }
}
