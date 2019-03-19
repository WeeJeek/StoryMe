package com.licun.storyme.storyme_phone;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CameraActivity extends Activity {
    //FirebaseAuth mAuth;
    //FirebaseManager mFirebase;
    //FirebaseUser mUser;

    //PhotoManager mPhotoManager;

    Button btn_take_photo;
    ImageView iv_camera, iv_photo, iv_question, iv_setting, iv_picture;

    Uri picUri;
    private String cur_photo_path;

    static final int REQUEST_TAKE_PHOTO = 1;
    public final static int CAPTURE_IMAGE = 4;

    private File image_path;
    private File question_path;
    private File record_path;

    private File[] images;
    private File[] questions;
    private File[] records;


    private HashMap<File, ArrayList<File>> image_record_s;
    private HashMap<File, ArrayList<File>> question_record_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        //mAuth = FirebaseAuth.getInstance();
        //PublicVariables.mFirebaseManager = new FirebaseManager();
        //mUser = mAuth.getCurrentUser();
        //mPhotoManager = new PhotoManager();
        set_views();
        set_on_clicks();
        initialize();
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 3);

    }

    private void initialize(){
        requestPermission();
        image_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        create_path(image_path);
        question_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        create_path(question_path);
        record_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        create_path(record_path);
        refresh();
    }

    private void refresh(){
        Toolbox tl = new Toolbox();
        images = image_path.listFiles();
        questions = question_path.listFiles();
        records = record_path.listFiles();

        image_record_s = tl.get_map(images, records);
        question_record_s = tl.get_map(questions, records);

    }

    private void create_path(File pathname){
        if(!pathname.exists())
        {
            pathname.mkdirs();
        }
    }



    private void set_views() {
        btn_take_photo = (Button) findViewById(R.id.btn_take_photo);
        iv_camera = (ImageView) findViewById(R.id.iv_camera);
        iv_photo = (ImageView) findViewById(R.id.iv_photo);
        iv_question = (ImageView) findViewById(R.id.iv_question);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        iv_picture = (ImageView) findViewById(R.id.iv_picture);
    }

    private void set_on_clicks() {
        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
                finish();
            }
        });
        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhotosActivity.class);
                intent.putExtra("image", images);
                intent.putExtra("map_image", (Serializable) image_record_s);
                intent.putExtra("question", questions);
                intent.putExtra("map_questions", (Serializable) question_record_s);
                startActivityForResult(intent, 0);
                finish();
            }
        });
        iv_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                intent.putExtra("image", images);
                intent.putExtra("map_image", (Serializable) image_record_s);
                intent.putExtra("question", questions);
                intent.putExtra("map_questions", (Serializable) question_record_s);
                startActivity(intent);
                finish();
            }
        });
        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            refresh();
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Toolbox tl = new Toolbox();
//            long timestamp = System.currentTimeMillis();
//            Uri imageUri = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                tl.writeimage(bitmap, String.valueOf(timestamp), image_path);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        Long timeStamp = System.currentTimeMillis();
        //String imageFileName = image_path + "/" + timeStamp;
        File image = File.createTempFile(
                String.valueOf(timeStamp),  /* prefix */
             ".jpg",         /* suffix */
                image_path
        );

        // Save a file: path for use with ACTION_VIEW intents
        cur_photo_path = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                picUri = FileProvider.getUriForFile(this,
                        "com.licun.storyme.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);

                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }



    }

}

