package com.pushertest.www.budgetcatcher.View.Activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pushertest.www.budgetcatcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInSignUp extends AppCompatActivity {

    @BindView(R.id.signIn)
    Button signIn;
    @BindView(R.id.signUp)
    Button signUp;

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

                Toast.makeText(this, "signIN", Toast.LENGTH_SHORT).show();

                break;
            }

            case R.id.signUp: {

                Toast.makeText(this, "signUp", Toast.LENGTH_SHORT).show();

                break;
            }
        }

    }

}
