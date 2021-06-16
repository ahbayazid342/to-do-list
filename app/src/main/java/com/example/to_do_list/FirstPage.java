package com.example.to_do_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class FirstPage extends AppCompatActivity {

    private final int SPLASH = 3300;

    private Animation topAnim, bottomAnim;
    private ImageView firstPageImg;
    private TextView firstPageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        firstPageImg = findViewById(R.id.firstPageImgId);
        firstPageText = findViewById(R.id.firstPageTextId);

        topAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_animation);

        firstPageImg.setAnimation(topAnim);
        firstPageText.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FirstPage.this, Register.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH);

    }
}