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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private TextView gotoLogIn;
    private EditText etEmail, etPass;
    private Button regBtn;

    private ProgressDialog loader;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findView();

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(getApplicationContext());

//        ON CLICK LISTNER WORK DONE HERE
        gotoLogIn.setOnClickListener(this);
        regBtn.setOnClickListener(this);


    }

    private void findView() {
        gotoLogIn = findViewById(R.id.goToLogInpage);
        regBtn = findViewById(R.id.regBtnId);
        etEmail = findViewById(R.id.regEmailId);
        etPass = findViewById(R.id.regPassId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goToLogInpage:
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(intent);
                break;

            case R.id.regBtnId:
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();

                if (TextUtils.isEmpty(email)){
                    etEmail.setError("Email required");
                    return;
                } else if (TextUtils.isEmpty(pass)){
                    etPass.setError("Password required");
                } else {
                  /*  loader.setMessage("Regitration Loading..");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();*/
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                               /* finish();
                                loader.dismiss();*/
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration Failed !!!", Toast.LENGTH_SHORT).show();
/*
                                loader.dismiss();
*/
                            }
                        }
                    });
                }

//                Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}