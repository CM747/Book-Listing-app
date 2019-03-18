package com.example.chandan.booklisting;

import android.graphics.Bitmap;

public class book {
    private String bookname;
    private String author;
    private Bitmap image;
    private String selflink;

    public book(String n,String a,Bitmap imag,String sl){
        bookname = n;
        author = a;
        image = imag;
        selflink = sl;
    }

    public String getBookname() {
        return bookname;
    }

    public String getAuthor() {
        return author;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getSelflink() {
        return selflink;
    }
}
