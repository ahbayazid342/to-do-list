package com.example.to_do_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    private TextView gotoRegPage;
    private EditText etEmail, etPass;
    private Button loginBtn;

    private ProgressDialog loader;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loader = new ProgressDialog(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();

        findView();

//        ON CLICK LISTNER PERFORM HERE
        gotoRegPage.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    private void findView() {

        gotoRegPage = findViewById(R.id.goToRegPage);
        loginBtn = findViewById(R.id.logInBtnId);
        etEmail = findViewById(R.id.logInEmailId);
        etPass = findViewById(R.id.logInPassId);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.goToRegPage:
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                break;

            case R.id.logInBtnId:
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();

//                Toast.makeText(getApplicationContext(), email + " " + pass, Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(email)){
                    etEmail.setError("Email Required");
                    return;
                } else if (TextUtils.isEmpty(pass)){
                    etPass.setError("Password Required");
                    return;
                } else {
                    /*loader.setMessage("Log in processing....");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();*/
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                               /* finish();
                                loader.dismiss();*/
                            } else {
                                Toast.makeText(getApplicationContext(), "log In Failed !!!", Toast.LENGTH_SHORT).show();
                               /* loader.dismiss();
                                return;*/
                            }
                        }
                    });
                }
                break;
        }

    }
}