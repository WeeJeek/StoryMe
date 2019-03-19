package com.licun.storyme.storyme_phone;

import android.net.Uri;

import com.google.firebase.database.IgnoreExtraProperties;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;


@IgnoreExtraProperties
public class Photo {

    private String uid;
    private String uri;

    private String url;
    private Date create_time;
    private Boolean is_new;

    public Photo(){}

    public Photo(String uid, URL url){
        this.uid = uid;
        this.url = url.toString();
        this.create_time = Calendar.getInstance().getTime();
    }

    public Photo(String uid, Uri uri){
        this.uid = uid;
        this.uri = uri.toString();
        this.create_time = Calendar.getInstance().getTime();
        this.is_new = true;
    }

    public Boolean getIs_new() {
        return is_new;
    }

    public void setIs_new(Boolean is_new) {
        this.is_new = is_new;
    }

    public String getUrl() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }
    public String getUid() {
        return uid;
    }

    public String getUri() {
        return uri;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
