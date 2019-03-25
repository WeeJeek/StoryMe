package com.licun.storyme.storyme_phone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionContentActivity extends AppCompatActivity {

    private Intent intent;
    private String TAG="QuestionContentActivity";
    private TextView tvQuesion;
    //private Question question;
    private ListView lvRecord;
    private ImageView imback;
    //    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
//    private SimpleAdapter simAdapt;
    private Record record;

    private ArrayList filelist = new ArrayList();
    //private RecordAdapter adapters = new RecordAdapter(this);
    private ArrayList<Record> mMusicBeanList = new ArrayList<Record>();// 装音乐列表的数组
    private ArrayList<File> mFileList = new ArrayList<File>();
    //private File musicfile = new File(Environment.getExternalStorageDirectory()+"/PhotoM");;
    private static MediaPlayer mediaPlayer = new MediaPlayer();
    private ImageView imBack;
    private File question;
    private ArrayList<File> records;
    private boolean playing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_content);
        Intent intent = getIntent();
        question = (File)intent.getSerializableExtra("question");
        records = (ArrayList<File>) intent.getSerializableExtra("record");
        if(records != null) {
            lvRecord = (ListView) findViewById(R.id.lv_record);
            RecordAdapter rec = new RecordAdapter(QuestionContentActivity.this, R.layout.item_record, records);
            lvRecord.setAdapter(rec);

        }
        set_views();
        set_on_clicks();
    }

    private void set_views(){
        imBack = (ImageView)findViewById(R.id.imback);
    }

    private void set_on_clicks(){
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop(); finish();
            }
        });

        if(records != null) {
            lvRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (!playing) {
                        initMediaPlayer(records.get(position));
                    } else {
                        stop();
                    }
                }
            });
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
    /*
     *单击list
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG,filelist.get(position).toString());
        Log.i(TAG,position+"");
        initMediaPlayer((File)filelist.get(position));
    }

    /**
     * 遍历搜索音乐文件
     */
    public void seacrhMusicFile(File rootsFile) {
        File[] files = rootsFile.listFiles();// 列出当前目录下的所有文件
        if (files.length > 0) {
            for (File file : files) {
                String name = file.getName();
                if (name.endsWith(".m4a")||name.endsWith(".mp3")) {
                    filelist.add(file);
                    //下面这几行代码是为了后面传递音乐路径而整理的音乐集合，如果只需要把本地音乐显示在listview列表上的话，就不需要下面的代码
                    // 构造音乐播放列表，遍历所有音乐后把所有音乐放在一个集合里
                    //Record record = new Record();
                   //do something
                    mMusicBeanList.add(record);
                }
            }
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(mediaPlayer != null&& mediaPlayer.isPlaying()){
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
    }

    public void onBackPressed() {
        if(mediaPlayer != null&&mediaPlayer != null&& mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        finish();

    }
}
