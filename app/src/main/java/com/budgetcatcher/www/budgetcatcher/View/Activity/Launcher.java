package com.budgetcatcher.www.budgetcatcher.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.R;

import butterknife.ButterKnife;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_screen);
        ButterKnife.bind(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getBoolean(Config.SP_LOGGED_IN, false)) {

                    startActivity(new Intent(Launcher.this, MainActivity.class));
                    finish();

                } else {

                    startActivity(new Intent(Launcher.this, SignInSignUp.class));
                    finish();

                }

            }
        }, 2000);

    }

}
