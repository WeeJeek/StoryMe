package com.licun.storyme.storyme_phone;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.Calendar;
import java.util.Date;

@IgnoreExtraProperties
public class Question implements Parcelable {

    private String uid;
    private String content;
    private Date create_time;
    private Boolean is_new;

    public Question(){}

    public Question( String uid, String content){
        this.uid = uid;
        this.content = content;
        this.create_time = Calendar.getInstance().getTime();
        this.is_new = true;
    }

    public Boolean getIs_new() {
        return is_new;
    }

    public void setIs_new(Boolean is_new) {
        this.is_new = is_new;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public String getUid() {
        return uid;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(uid);
        dest.writeSerializable(create_time);
    }
}
