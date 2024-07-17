package com.example.movie.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movie.R;
import com.example.movie.constant.AboutUsConfig;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        initUi();
        startMainActivity();
    }

    private void initUi() {
        TextView tvAboutUsTitle = findViewById(R.id.tv_about_us_title);
        TextView tvAboutUsSlogan = findViewById(R.id.tv_about_us_slogan);
        tvAboutUsTitle.setText(AboutUsConfig.ABOUT_US_TITLE);
        tvAboutUsSlogan.setText(AboutUsConfig.ABOUT_US_SLOGAN);
    }

    private void startMainActivity() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }, 1500);
    }
}
