package com.pushertest.www.budgetcatcher.View.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pushertest.www.budgetcatcher.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_up);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.signIn, R.id.signUp})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.signIn: {

                startActivity(new Intent(SignInSignUp.this, SignIn.class));
                finish();

                break;
            }

            case R.id.signUp: {

                startActivity(new Intent(SignInSignUp.this, SignUp.class));
                finish();

                break;
            }
        }

    }

}
