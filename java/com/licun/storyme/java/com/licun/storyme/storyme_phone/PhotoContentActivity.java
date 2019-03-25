package com.licun.storyme.storyme_phone;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PhotoContentActivity extends Activity{
    private Intent intent;

    private ListView lvRecord;
    private ImageView imageView;
    private String TAG="PhotoContentActivity------------------";
    private Photo photo_uri;
    private ImageAndText imageAndText;
    private Bitmap bitmap;
    private ImageView imBack;
    //private ImageView ivPlay;
    private TextView tvRecordname;
    private static MediaPlayer mediaPlayer = new MediaPlayer();
    ArrayList<File> records;
    boolean audio_ready = false;
    private boolean playing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_content);
        imageView=(ImageView) findViewById(R.id.iview);
        imBack =findViewById(R.id.imback);
//        imBack.setOnClickListener(PhotoContentActivity.this);
//        ivPlay =findViewById(R.id.iv_play);
//        ivPlay.bringToFront();
//        ivPlay.setOnClickListener(this);
        lvRecord =  (ListView) findViewById(R.id.list_record);
        //tvRecordname=findViewById(R.id.tv_record_name);
        intent = getIntent();
        records = (ArrayList<File>)intent.getSerializableExtra("record");
        if(records != null) {
            RecordAdapter rec = new RecordAdapter(PhotoContentActivity.this, R.layout.item_question, records);
            lvRecord.setAdapter(rec);
        }
        //imageAndText = (ImageAndText) getIntent().getSerializableExtra("imageAndText");
        this.set_image();
        set_on_clicks();
    }

    private void set_on_clicks(){
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
                finish();
            }
        });

        lvRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                if(!playing){initMediaPlayer(records.get(position));}
                else{stop();}
            }
        });
    }

    //获取权限返回
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //initMediaPlayer();
                }else{
                    Toast.makeText(this, "拒绝权限，将无法使用程序。", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
        }
    }

    private void set_image(){
        File image_uri = (File) intent.getSerializableExtra("photo");
        Picasso.get().load(image_uri).into(imageView);
    }

    //初始化播放器
    private void initMediaPlayer(File file) {
        try {
            //File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(file.getPath());//指定音频文件路径
            //mediaPlayer.setLooping(true);//设置为循环播放
            mediaPlayer.prepare();//初始化播放器MediaPlayer
            mediaPlayer.start();
            playing = true;
        } catch (Exception e) {
            Log.i(TAG,e.getMessage());
            e.printStackTrace();
        }
    }


    private void stop(){
        mediaPlayer.stop();
        playing = false;
    }


}