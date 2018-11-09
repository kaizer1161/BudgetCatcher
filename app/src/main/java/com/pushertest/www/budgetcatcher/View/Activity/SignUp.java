package com.pushertest.www.budgetcatcher.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pushertest.www.budgetcatcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUp extends AppCompatActivity {

    @BindView(R.id.sign_up_now)
    Button signUpNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.sign_up_now})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.sign_up_now: {

                startActivity(new Intent(SignUp.this, ProfileSetup.class));

                break;
            }

        }

    }

}
