package com.pushertest.www.budgetcatcher.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pushertest.www.budgetcatcher.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.signIn, R.id.forgot_password})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.signIn: {

                startActivity(new Intent(SignIn.this, MainActivity2.class));
                finish();

                break;
            }

            case R.id.forgot_password: {

                /*startActivity(new Intent(SignIn.this, SignUp.class));*/

                break;
            }
        }

    }

}
