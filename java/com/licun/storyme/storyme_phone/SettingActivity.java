package com.licun.storyme.storyme_phone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseManager mFirebase;
    FirebaseUser mUser;

    ImageView iv_camera, iv_photo, iv_question, iv_setting;
    Button btn_logout;

    public final static int REQUEST_PICTURE_CHOOSE = 1;
    public final static int  REQUEST_CAMERA_IMAGE = 2;
    public final static int REQUEST_CROP_IMAGE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mAuth = FirebaseAuth.getInstance();
        mFirebase = new FirebaseManager();
        mUser = mAuth.getCurrentUser();
        set_views();
        set_on_clicks();
    }

    private void set_views(){
        iv_camera = (ImageView)findViewById(R.id.iv_camera);
        iv_photo = (ImageView)findViewById(R.id.iv_photo);
        iv_question = (ImageView)findViewById(R.id.iv_question);
        iv_setting = (ImageView)findViewById(R.id.iv_setting);
        btn_logout = (Button)findViewById(R.id.btn_logout);
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
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
