package com.licun.storyme.storyme_phone;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class QuestionAddActivity extends AppCompatActivity {

    private Button btAdd;
    private EditText etQuestion;
    private ImageView iv_camera, iv_photo, iv_question, iv_setting;

    private FirebaseAuth mAuth;
    private FirebaseManager mFirebase;
    private FirebaseUser mUser;
    private  File question_path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/licun");

    private File[] questions, images;
    private HashMap question_record_s, image_record_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_add);
        //mAuth = FirebaseAuth.getInstance();
        //mUser = mAuth.getCurrentUser();
        Intent intent =getIntent();
        images = (File[]) intent.getSerializableExtra("image");
        questions = (File[])intent.getSerializableExtra("question");
        question_record_s= (HashMap) intent.getSerializableExtra("map_question");
        image_record_s = (HashMap) intent.getSerializableExtra("map_image");

        set_views();
        set_on_clicks();
    }

    private void set_views(){
        iv_camera = (ImageView)findViewById(R.id.iv_camera);
        iv_photo = (ImageView)findViewById(R.id.iv_photo);
        iv_question = (ImageView)findViewById(R.id.iv_question);
        //iv_setting = (ImageView)findViewById(R.id.iv_setting);
        btAdd = (Button)findViewById(R.id.bt_add);
        etQuestion = (EditText)findViewById(R.id.et_question);
    }

    private void set_on_clicks(){
        final Toolbox tl = new Toolbox();

        this.btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question_content = etQuestion.getText().toString();
                long timestamp = System.currentTimeMillis();
                tl.writetext(question_content, String.valueOf(timestamp), question_path);
                questions = question_path.listFiles();
                //PublicVariables.mFirebaseManager.save_question(mUser.getUid(), question_content);
                Intent intent = new Intent(QuestionAddActivity.this, QuestionsActivity.class);
                intent.putExtra("image", images);
                intent.putExtra("map_image", (Serializable) image_record_s);
                intent.putExtra("question", questions);
                intent.putExtra("map_questions", (Serializable) question_record_s);
                startActivity(intent);
                finish();
            }
        });

        this.iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
                finish();
            }
        });

        this.iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), PhotosActivity.class);
                intent.putExtra("image", images);
                intent.putExtra("map_image", (Serializable) image_record_s);
                intent.putExtra("question", questions);
                intent.putExtra("map_questions", (Serializable) question_record_s);
                startActivity(intent);
                finish();
            }
        });

        this.iv_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), QuestionsActivity.class);
                intent.putExtra("image", images);
                intent.putExtra("map_image", (Serializable) image_record_s);
                intent.putExtra("question", questions);
                intent.putExtra("map_questions", (Serializable) question_record_s);
                startActivity(intent);
                finish();
            }
        });

//        this.iv_setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), SettingActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }


}
