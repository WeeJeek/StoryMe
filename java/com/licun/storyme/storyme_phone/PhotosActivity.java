package com.licun.storyme.storyme_phone;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PhotosActivity extends AppCompatActivity {
    //FirebaseAuth mAuth;
    //FirebaseManager mFirebase;
    //FirebaseUser mUser;

    //PhotoManager mPhotoManager;

    ImageView iv_camera, iv_photo, iv_question, iv_setting;

    private GridView gridView;
    private PhotoAdapter photoAdapter;
    private List<ImageAndText> imageAndTextList = new ArrayList<ImageAndText>();
    private ImageAndTextListAdapter imageAndTextListAdapter;
    private File[] images;
    private HashMap image_record_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        //mAuth = FirebaseAuth.getInstance();
        //mUser = mAuth.getCurrentUser();
        //mPhotoManager = new PhotoManager();
        //photoList = PublicVariables.getmPhotos();
        //this.set_photos_list(getApplicationContext());
        Intent intent =getIntent();
        images = (File[])intent.getSerializableExtra("image");
        image_record_map = (HashMap) intent.getSerializableExtra("map_image");
        set_views();
        photoAdapter = new PhotoAdapter(this);
        photoAdapter.set_resource_images(images, getApplicationContext());
        gridView.setAdapter(photoAdapter);
        set_on_clicks();
    }

    private void set_views(){
        iv_camera = (ImageView)findViewById(R.id.iv_camera);
        iv_photo = (ImageView)findViewById(R.id.iv_photo);
        iv_question = (ImageView)findViewById(R.id.iv_question);
        iv_setting = (ImageView)findViewById(R.id.iv_setting);
        gridView=(GridView)findViewById(R.id.gridview);
    }

    private void set_on_clicks(){
        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
                finish();
            }
        });
        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), PhotosActivity.class);
                startActivity(intent);
                finish();
            }
        });
        iv_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), QuestionsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent=new Intent(PhotosActivity.this,PhotoContentActivity.class);
//                ImageAndText p=imageAndTextList.get(position);
//                intent.putExtra("imageAndText",p);
                intent.putExtra("photo",images[position]);
                ArrayList<File> record = (ArrayList<File>) image_record_map.get(images[position]);
                if(record != null)intent.putExtra("record", record);
                startActivity(intent);
            }
        });
    }



    public void set_photos_list(Context context){
        //PublicVariables.mFirebaseManager.download_photos(context, mUser.getUid());
    }
}
