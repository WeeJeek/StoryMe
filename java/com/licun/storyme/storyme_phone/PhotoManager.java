package com.licun.storyme.storyme_phone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoManager {

    private String cur_photo_path;
    private Uri cur_photo_uri;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private final static int  REQUEST_IMAGE_CAPTURE  = 2;
    private final static int  RESULT_OK  = 11;

    public PhotoManager(){
    }

    public Uri getCur_photo_uri() {
        return cur_photo_uri;
    }

    public void setCur_photo_uri(Uri cur_photo_uri) {
        this.cur_photo_uri = cur_photo_uri;
    }

    public void upload_photo(Context context, String uid) throws IOException {
        File photo = new File(cur_photo_uri.toString());

        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),cur_photo_uri);
//        PublicVariables.mFirebaseManager.upload_photo(context, uid, bitmap);
//        if(photo.exists()){
//            Toast.makeText(context, "photo exitst",Toast.LENGTH_LONG );
//            PublicVariables.mFirebaseManager.upload_photo(context, uid, photo);
//        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();
        //PublicVariables.mFirebaseManager.upload_photo_to_storage(cur_photo_uri);
    }
}
