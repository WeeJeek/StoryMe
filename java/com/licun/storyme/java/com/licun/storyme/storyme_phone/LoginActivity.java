package com.licun.storyme.storyme_phone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    private FirebaseAuth mAuth;

    private EditText et_user_name, et_password;
    private TextView tv_signup;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        sign_views();
        set_onclicks();

        //for test
        //for test
        //for test
        et_user_name.setText("5@gmail.com");
        et_password.setText("123456");

        OnCompleteListener completeListener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, CameraActivity.class));
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        mAuth.signInWithEmailAndPassword(et_user_name.getText().toString(), et_password.getText().toString())
                .addOnCompleteListener(completeListener);
        //for test
        //for test
        //for test

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void sign_views() {
        this.et_user_name = (EditText) findViewById(R.id.et_username);
        this.et_password = (EditText) findViewById(R.id.et_password);
        this.btn_login = (Button) findViewById(R.id.btn_login);
        this.tv_signup = (TextView) findViewById(R.id.tv_signup);
    }


    private void set_onclicks() {
        this.tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });

        this.btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_user_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "password longer than 6 characters!", Toast.LENGTH_LONG).show();
                    return;
                }

                OnCompleteListener completeListener = new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, CameraActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(completeListener);

            }
        });

    }
}
