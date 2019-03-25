package com.licun.storyme.storyme_pad;

import android.net.Uri;
import com.google.firebase.database.IgnoreExtraProperties;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

@IgnoreExtraProperties
public class Record {

    private String uid;
    private Date create_time;
    private String uri;
    private Date resource_create_time;
    private Boolean is_new;
    private String r_type;

    private String filename;

    public Record(){}

    public Record(String uid,Date resource_create_time, String uri, String r_type, String filename){
        this.uid = uid;
        this.create_time = Calendar.getInstance().getTime();
        this.uri = uri.toString();
        this.is_new = true;
        this.resource_create_time = resource_create_time;
        this.r_type = r_type;
        this.filename = filename;
    }
    public Boolean getIs_new() {
        return is_new;
    }
    public String getR_type() {
        return r_type;
    }

    public void setR_type(String r_type) {
        this.r_type = r_type;
    }
    public void setIs_new(Boolean is_new) {
        this.is_new = is_new;
    }
    public String getRid() {
        return uid;
    }
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    public Date getCreate_time() {
        return create_time;
    }

    public String getUri() {
        return uri;
    }

    public void setRid(String rid) {
        this.uid = rid;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public void setRecord(File record) {
        this.uri = uri;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public Date getResource_create_time() {
        return resource_create_time;
    }

    public void setResource_create_time(Date resource_create_time) {
        this.resource_create_time = resource_create_time;
    }
}
