package com.licun.storyme.storyme_pad;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private EditText et_user_name, et_password;
    private Button btn_signup;
    private FirebaseAuth mAuth;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.mAuth = FirebaseAuth.getInstance();
        this.sign_views();
        this.set_onclicks();
    }

    private void sign_views(){
        this.et_user_name = (EditText) findViewById(R.id.et_signup_username);
        this.et_password = (EditText) findViewById(R.id.et_signup_password);
        this.btn_signup = (Button) findViewById(R.id.bt_signup);
        this.tv_title = (TextView) findViewById(R.id.tv_title);
    }

    private void set_onclicks(){
        this.btn_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = et_user_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Enter email address!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Enter password!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(password.length()<6){
                    Toast.makeText(getApplicationContext(),"password longer than 6 characters!", Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(SignupActivity.this, "failed", Toast.LENGTH_LONG).show();
                                }else{
                                    Intent i = new Intent(SignupActivity.this, StoryActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }


                        });
            }
        });

        this.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
