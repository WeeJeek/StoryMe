package com.licun.storyme.storyme_pad;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class StoryActivity extends AppCompatActivity {

    //private FirebaseAuth mAuth;
    //private FirebaseUser mUser;
    //private RecordManager _mRecordManager;
    PhotoManager mPhotoManager;
    MediaRecorder mRecorder = null;
    MediaPlayer mPlayer = null;
    private String mRecordFileName = "";

    private boolean photoSelect=true;
    private boolean onUp = false;
    private boolean onDown = false;
    private boolean onRecording = false;

    private AudioUtil audioUtil;
    private int cur_photo_index = 0;
    private int cur_question_index = 0;
    private boolean photo_mode = true;
    private String record_name = "";

    private File image_path;
    private File question_path;
    private File record_path;

    private File[] images;
    private File[] questions;
    private File[] records;
    private boolean no_image_to_show = true;
    private boolean no_question_to_show = true;

    private List<HashMap> image_record_s = new ArrayList<HashMap>();
    private List<HashMap> question_record_s = new ArrayList<HashMap>();

    private ImageView ivPhotos;
    private ImageView ivPhotosc;
    private ImageView ivStart;
    private ImageView ivEnd;
    private ImageView ivQuestions;
    private ImageView ivQuestionsc;
    private ImageView ivPicture;
    private TextView tvQuestion;
    private ImageView ivUp;
    private ImageView ivDown;
    private Chronometer chronometer;
    private TextView tvLogout;

    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        //for_the_very_beginning_time();
        initialize();
        //this._mRecordManager = new RecordManager();
        //this.mAuth = FirebaseAuth.getInstance();
        //this.mUser = mAuth.getCurrentUser();
        this.set_views();
        this.set_on_clicks();
        display();
    }


    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 3);

    }

    private void initialize(){
        requestPermission();
        image_path = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/licun");
        create_path(image_path);
        question_path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/licun");
        create_path(question_path);
        record_path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString() + "/licun");
        create_path(record_path);
        refresh();

    }

    private void refresh(){

        images = image_path.listFiles();
        questions = question_path.listFiles();
        records = record_path.listFiles();
        if(images.length> 0){no_image_to_show = false;}
        if(questions.length > 0){no_question_to_show = false;}
//        for (File image: images){
//            HashMap image_record = get_map(image, records);
//            image_record_s.add(image_record);
//        }
//
//        for (File question: questions){
//            HashMap question_record = get_map(question, records);
//            question_record_s.add(question_record);
//        }
    }


//    private HashMap get_map(File key, File[] records){
//        HashMap map = new HashMap();
//        String key_realname = key.getName();
//        key_realname = key_realname.replace(".jpg","");
//        for (File record: records){
//            String record_name = record.getName();
//            if (record_name.contains(key_realname)){
//                map.put(key.getName(), record.getName());
//            }
//        }
//        return map;
//    }


    private void create_path(File pathname){
        if(!pathname.exists())
        {
            pathname.mkdirs();
        }
    }

    private int find_image_index(File file){
        for(HashMap h: image_record_s){
            if (h.containsKey(file.getName())){
                return h.size();
            }
        }
        return 0;
    }

    private int find_question_index(File file){
        for(HashMap h: question_record_s){
            if (h.containsKey(file.getName())){
                return h.size();
            }
        }
        return 0;
    }

    private void set_recording_environment(){
        // Record to the external cache directory for visibility
        if(photo_mode) {
            long index = System.currentTimeMillis();
            mRecordFileName += record_path.toString() + "/" + real_name(images[cur_photo_index].toString());
            mRecordFileName = mRecordFileName.replace(".jpg","");
            mRecordFileName = mRecordFileName +"_"+index + ".3gp";
        }
        else{
            long index = System.currentTimeMillis();
            mRecordFileName += record_path.toString() + "/" + real_name(questions[cur_question_index].toString());
            mRecordFileName = mRecordFileName.replace(".txt","");
            mRecordFileName = mRecordFileName +"_"+index + ".3gp";
        }
    }

    private String real_name(String name){
        int i = name.length()-1;
        for(int j = name.length() - 1; j>0; j--){
            if (name.charAt(j)== '/'){
                i = j+1;
                break;
            }
        }
        return name.substring(i);
    }



    private void set_views(){
        ivUp = (ImageView)findViewById(R.id.iv_up);
        ivDown = (ImageView)findViewById(R.id.iv_down);
        ivPicture = (ImageView)findViewById(R.id.iv_picture);
        ivPhotos = (ImageView)findViewById(R.id.iv_photos);
        ivPhotosc = (ImageView)findViewById(R.id.iv_photosc);
        ivQuestions = (ImageView)findViewById(R.id.iv_questions);
        ivQuestionsc = (ImageView)findViewById(R.id.iv_questionsc);
        tvQuestion = (TextView)findViewById(R.id.tv_question);
        ivStart = (ImageView)findViewById(R.id.iv_start);
        ivEnd = (ImageView)findViewById(R.id.iv_end);
        tvLogout = (TextView)findViewById(R.id.tv_main_logout);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
    }

    private void set_on_clicks(){
        this.ivQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToQuestion();
            }
        });
        this.ivPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToImage();
            }
        });

        this.ivUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photo_mode && !no_question_to_show){
                    lastPhoto();
                }
                if (!photo_mode && images.length>0)
                {
                    lastQuestion();
                }
            }
        });

        this.ivDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photo_mode && !no_image_to_show){
                    nextPhoto();
                }
                if (!photo_mode && images.length>0)
                {
                    nextQuestion();
                }
            }
        });

        this.ivQuestionsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToQuestion();
                startActivity(new Intent(StoryActivity.this, SignupActivity.class));
                finish();
            }
        });

        this.ivStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((photo_mode && !no_image_to_show) || (!photo_mode && !no_image_to_show)) {
                    mRecordFileName = "";
                    set_recording_environment();
                    ivStart.setVisibility(View.INVISIBLE);
                    ivEnd.setVisibility(View.VISIBLE);
                    chronometer.start();// 开始计时
                    //requestAudioPermissions();
                    startRecording();
                }
            }
        });

        this.ivEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivEnd.setVisibility(View.INVISIBLE);
                chronometer.stop();// 停止计时
                stopRecording();
                String type = "";
                chronometer.setBase(SystemClock.elapsedRealtime());// 复位
               /* if(!photo_mode){
                    type = "q";
                    PublicVariables.mFirebaseManager.upload_record(mRecordFileName,
                            PublicVariables.mQuestions.get(cur_question_index).getUid(),
                            PublicVariables.mQuestions.get(cur_question_index).getCreate_time(),
                            getApplicationContext(),
                            type);
                }
                else if (photo_mode){
                    type = "p";
                    PublicVariables.mFirebaseManager.upload_record(mRecordFileName,
                            PublicVariables.mPhotos.get(cur_photo_index).getUid(),
                            PublicVariables.mPhotos.get(cur_photo_index).getCreate_time(),
                            getApplicationContext(),
                            type);
                }


                //Toast.makeText(getApplicationContext(), "Uploading, please wait!" ,Toast.LENGTH_SHORT).show();
                //recordEnd(mUser.getUid());*/
                refresh();
                ivStart.setVisibility(View.VISIBLE);
            }
        });

        /*this.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });*/
    }

    private void switchToQuestion(){
        photo_mode =false;
        ivPhotos.setVisibility(View.VISIBLE);
        ivPhotosc.setVisibility(View.INVISIBLE);
        ivQuestions.setVisibility(View.INVISIBLE);
        ivQuestionsc.setVisibility(View.VISIBLE);
        ivPicture.setVisibility(View.INVISIBLE);
        tvQuestion.setVisibility(View.VISIBLE);
    }

    private void switchToImage(){
        photo_mode =true;
        ivPhotos.setVisibility(View.INVISIBLE);
        ivPhotosc.setVisibility(View.VISIBLE);
        ivQuestions.setVisibility(View.VISIBLE);
        ivQuestionsc.setVisibility(View.INVISIBLE);
        ivPicture.setVisibility(View.VISIBLE);
        tvQuestion.setVisibility(View.INVISIBLE);

        if(questions.length > 0){this.display_current_question();}
    }

    private void display(){
        if(photo_mode && !no_image_to_show) { display_current_photo();}
        if(!photo_mode && !no_question_to_show){display_current_question();}
    }

    private void display_current_question(){
        //Question question = PublicVariables.mQuestions.get(cur_question_index);

        //Read text from file
        StringBuilder question = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(questions[cur_question_index]));
            String line;

            while ((line = br.readLine()) != null) {
                question.append(line);
                question.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        tvQuestion.setText(question);
    }

    private void display_current_photo(){
        Bitmap bMap = BitmapFactory.decodeFile(images[cur_photo_index].toString());
        ivPicture.setImageBitmap(bMap);
    }

    private void nextQuestion() {
        if(cur_question_index < questions.length - 1){
            cur_question_index ++;
        }else{
            cur_question_index = 0;
        }

        display_current_question();
    }

    private void lastQuestion() {
        if(cur_question_index > 0){
            cur_question_index --;
        }else{
            cur_question_index = questions.length - 1;
        }

        display_current_question();
    }

    private void nextPhoto() {
        if(cur_photo_index < images.length - 1){
            cur_photo_index ++;
        }else{
            cur_photo_index = 0;
        }

        display_current_photo();
    }

    private void lastPhoto() {
        if(cur_photo_index > 0){
            cur_photo_index --;
        }else{
            cur_photo_index = images.length - 1;
        }

        display_current_photo();
    }

    private void requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now
            recordStart();
        }
    }
    public void recordStart() {
        //⑧申请录制音频的动态权限
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    android.Manifest.permission.RECORD_AUDIO},1);
        }else {
            //获取当前时间戳
            long timeStamp = System.currentTimeMillis();
            Calendar cal;
            String year;
            String month;
            String day;
            String hour;
            String minute;
            String second;
            String my_time_1;
            String my_time_2;
            cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

            year = String.valueOf(cal.get(Calendar.YEAR));
            month = String.valueOf(cal.get(Calendar.MONTH)+1);
            day = String.valueOf(cal.get(Calendar.DATE));
            if (cal.get(Calendar.AM_PM) == 0)
                hour = String.valueOf(cal.get(Calendar.HOUR));
            else
                hour = String.valueOf(cal.get(Calendar.HOUR)+12);
            minute = String.valueOf(cal.get(Calendar.MINUTE));
            second = String.valueOf(cal.get(Calendar.SECOND));

            my_time_1 = year + "-" + month + "-" + day+"-";
            my_time_2 = hour + "-" + minute + "-" + second;
            this.record_name = my_time_1+my_time_2;
            audioUtil=null;
            audioUtil = AudioUtil.getInstance(this.record_name);
            audioUtil.startRecord();
            audioUtil.recordData();
        }
    }

    public void recordEnd(String uid) {
        audioUtil.stopRecord();
        audioUtil.convertWaveFile(this.record_name);
        //PublicVariables.mFirebaseManager.upload_record(this, uid, audioUtil, this.record_name);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    recordStart();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permissions Denied to record audio", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void logout() {/*
        mAuth.signOut();
        Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();*/
    }


    /////////
    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode){
//            case REQUEST_RECORD_AUDIO_PERMISSION:
//                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                break;
//        }
//        if (!permissionToRecordAccepted ) finish();
//
//    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mRecordFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        mRecorder.setOutputFile(mRecordFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
    } catch (IOException e) {
    }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
    }

}
