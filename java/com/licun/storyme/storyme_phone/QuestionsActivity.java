package com.licun.storyme.storyme_phone;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    //FirebaseAuth mAuth;
    //FirebaseUser mUser;

    //PhotoManager mPhotoManager;

    private ImageView iv_camera, iv_photo, iv_question, iv_setting, iv_add;
    private ListView listView;

    public final static int REQUEST_PICTURE_CHOOSE = 1;
    public final static int  REQUEST_CAMERA_IMAGE = 2;
    public final static int REQUEST_CROP_IMAGE = 3;

    private QuestionAdapter que_adapter;
    private File[] questions, images;
    private HashMap question_record_s, image_record_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        //mAuth = FirebaseAuth.getInstance();
        //mUser = mAuth.getCurrentUser();
        Intent intent =getIntent();
        images = (File[]) intent.getSerializableExtra("image");
        questions = (File[])intent.getSerializableExtra("question");
        question_record_s= (HashMap) intent.getSerializableExtra("map_question");
        image_record_s = (HashMap) intent.getSerializableExtra("map_image");
        set_views();
        set_on_clicks();

       // questionList = PublicVariables.getmQuestions();
        try {
            this.que_adapter = new QuestionAdapter(QuestionsActivity.this, R.layout.item_question, questions);
            listView.setAdapter(que_adapter);
        }catch (NullPointerException e){
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Intent intent=new Intent(QuestionsActivity.this,QuestionContentActivity.class);
                ArrayList<File> record = (ArrayList<File>) question_record_s.get(questions[position]);
                if(record != null){
                intent.putExtra("record", record);
                intent.putExtra("question",questions[position]);
                startActivity(intent);}
                else{
                    //Toast.makeText(getApplicationContext(), "No corresponding record!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void set_views(){
        iv_camera = (ImageView)findViewById(R.id.iv_camera);
        iv_photo = (ImageView)findViewById(R.id.iv_photo);
        iv_question = (ImageView)findViewById(R.id.iv_question);
        //iv_setting = (ImageView)findViewById(R.id.iv_setting);
        iv_add = (ImageView)findViewById(R.id.iv_add);
        listView = (ListView) findViewById(R.id.list_view);
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
                intent.putExtra("image", images);
                intent.putExtra("map_image", (Serializable) image_record_s);
                intent.putExtra("question", questions);
                intent.putExtra("map_question", (Serializable) question_record_s);
                startActivity(intent);
                finish();
            }
        });
        iv_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), QuestionsActivity.class);
                intent.putExtra("image", images);
                intent.putExtra("map_image", (Serializable) image_record_s);
                intent.putExtra("question", questions);
                intent.putExtra("map_question", (Serializable) question_record_s);
                startActivity(intent);
                finish();
            }
        });
//        iv_setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), SettingActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        iv_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(QuestionsActivity.this, QuestionAddActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
