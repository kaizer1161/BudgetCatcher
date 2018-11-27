package com.pushertest.www.budgetcatcher.View.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pushertest.www.budgetcatcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUp extends AppCompatActivity {

    @BindView(R.id.sign_up_now)
    Button signUpNow;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.question)
    EditText question;
    @BindView(R.id.answer)
    EditText answer;

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

                boolean hasError = false;

                if (userName.getText().toString().equals("")) {
                    userName.setError("Empty");
                    hasError = true;
                }
                if (password.getText().toString().equals("")) {
                    password.setError("Empty");
                    hasError = true;
                }
                if (phone.getText().toString().equals("")) {
                    phone.setError("Empty");
                    hasError = true;
                }
                if (email.getText().toString().equals("")) {
                    email.setError("Empty");
                    hasError = true;
                }
                if (question.getText().toString().equals("")) {
                    question.setError("Empty");
                    hasError = true;
                }
                if (answer.getText().toString().equals("")) {
                    answer.setError("Empty");
                    hasError = true;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {

                    email.setError("Wrong email format");
                    hasError = true;

                }
                if (!hasError) {

                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    saveDataToServer();

                }

                /*BudgetCatcher.apiManager.signUp(signUp, new QueryCallback<String>() {
                    @Override
                    public void onSuccess(String data) {

                    }

                    @Override
                    public void onError(Throwable th) {

                    }
                });*/

                /*startActivity(new Intent(SignUp.this, ProfileSetup.class));
                finish();*/

                break;
            }

        }

    }

    private void saveDataToServer() {

        com.pushertest.www.budgetcatcher.Model.SignUp signUp = new com.pushertest.www.budgetcatcher.Model.SignUp(userName.getText().toString(), email.getText().toString(), password.getText().toString(), phone.getText().toString(), "null", question.getText().toString(), answer.getText().toString(), "");

    }

}
