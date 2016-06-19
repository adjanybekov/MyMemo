package com.blogspot.adjanybekov.ZhurnalZametok;

import java.util.Date;

/**
 * Created by User on 18/06/2016.
 */
public class MyMemo {
    private Date _id;
    private String title;
    private String text;


    public Date get_id() {
        return _id;
    }

    public void set_id(Date id) {
        this._id = id;
    }

    public MyMemo() {
    }

    public MyMemo( String title,String text) {
        this.text = text;
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        if(title.length()>27)
            return title.substring(0,27);
        return title;
    }



    public void setTitle(String title) {
        this.title = title;
    }
}
