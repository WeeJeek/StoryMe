package com.licun.storyme.storyme_pad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
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

    public File getOutputMediaFile(int type, Context context, String filename) throws IOException {
        File f = new File(context.getCacheDir(), filename);
        f.createNewFile();
//
//        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
//
////Convert bitmap to byte array
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
//        byte[] bitmapdata = bos.toByteArray();
//
////write the bytes in file
//        FileOutputStream fos = new FileOutputStream(f);
//        fos.write(bitmapdata);
//        fos.flush();
//        fos.close();
        File mediaStorageDir = new File(context.getExternalCacheDir(), "NewDirectory");

        /**Create the storage directory if it does not exist*/
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }

        /**Create a media file name*/
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".png");
        } else {
            return null;
        }

        return f;
    }

    //缩略图
    public Bitmap get_thumbnail(int requestCode, int resultCode, Intent data, ImageView mImageView){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
            return imageBitmap;
        }
        return null;
    }

    //创建图片文件
    private File create_image_file(Context context) throws IOException{
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        cur_photo_path = image.getAbsolutePath();
        return image;
    }

    //拍照并且保存
    public void dispatchTakePictureIntent(Context context, Activity activity){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = create_image_file(context);
            } catch (IOException ex) {
                Toast.makeText(context, "failed in taking a photo", Toast.LENGTH_LONG);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.licun.storyme.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //保存图片至相册
    public void add_photo_to_gallery(Context context){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(cur_photo_path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    //缩小
    public void decode_scaled_image(ImageView mImageView, Context context) throws IOException {
       //get image
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),cur_photo_uri);
        mImageView.setImageBitmap(bitmap);
    }





}
