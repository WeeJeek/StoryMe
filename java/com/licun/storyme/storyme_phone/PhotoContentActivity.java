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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PhotoContentActivity extends Activity  implements OnClickListener{
    private Intent intent;

    private ImageView imageView;
    private String TAG="PhotoContentActivity------------------";
    private Photo photo_uri;
    private ImageAndText imageAndText;
    private Bitmap bitmap;
    private ImageView imBack;
    private ImageView ivPlay;
    private TextView tvRecordname;
    private static MediaPlayer mediaPlayer = new MediaPlayer();
    ArrayList<File> uri;
    boolean audio_ready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_content);
        imageView=(ImageView) findViewById(R.id.iview);
        imBack =findViewById(R.id.imback);
        imBack.setOnClickListener(PhotoContentActivity.this);
        ivPlay =findViewById(R.id.iv_play);
        ivPlay.bringToFront();
        ivPlay.setOnClickListener(this);
        tvRecordname=findViewById(R.id.tv_record_name);
        intent = getIntent();
        uri = (ArrayList<File>)intent.getSerializableExtra("record");

        if(uri == null)
            ivPlay.setVisibility(View.INVISIBLE);

        this.set_image();
        imageAndText = (ImageAndText) getIntent().getSerializableExtra("imageAndText");
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.ruiku_layout:
                intent=new Intent(this, CameraActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.yuyue_layout:
                intent=new Intent(this, PhotosActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.xiaoxi_layout:
                intent=new Intent(this, QuestionsActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.wode_layout:
                intent=new Intent(this, SettingActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.imback:
                finish();
                break;
            case R.id.iv_play:
                initMediaPlayer(uri.get(0).toString());
                break;

        }
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
    private void initMediaPlayer(String uri) {
        try {
            //File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(uri);//指定音频文件路径
            mediaPlayer.prepare();//初始化播放器MediaPlayer
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}