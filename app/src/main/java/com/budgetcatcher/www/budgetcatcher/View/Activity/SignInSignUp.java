package com.budgetcatcher.www.budgetcatcher.View.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.budgetcatcher.www.budgetcatcher.R;

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

                /*if (getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getInt(Config.SP_USER_CREATED_LEVEL, Config.SP_USER_CREATED_LEVEL_NONE) == Config.SP_USER_CREATED_LEVEL_SIGN_UP) {

                    startActivity(new Intent(SignInSignUp.this, ProfileSetup.class));

                } else {*/

                    startActivity(new Intent(SignInSignUp.this, SignIn.class));

                /*}*/

                break;
            }

            case R.id.signUp: {

                /*if (getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getInt(Config.SP_USER_CREATED_LEVEL, Config.SP_USER_CREATED_LEVEL_NONE) == Config.SP_USER_CREATED_LEVEL_SIGN_UP) {

                    startActivity(new Intent(SignInSignUp.this, ProfileSetup.class));

                } else {*/

                    startActivity(new Intent(SignInSignUp.this, SignUp.class));

                /*}*/

                break;
            }
        }

    }

}
