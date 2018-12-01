package com.pushertest.www.budgetcatcher.View.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pushertest.www.budgetcatcher.BudgetCatcher;
import com.pushertest.www.budgetcatcher.Config;
import com.pushertest.www.budgetcatcher.Network.QueryCallback;
import com.pushertest.www.budgetcatcher.Network.URL;
import com.pushertest.www.budgetcatcher.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private SharedPreferences sharedPreferences;

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

                    saveDataToServer();

                }

                break;
            }

        }

    }

    private void saveDataToServer() {

        com.pushertest.www.budgetcatcher.Model.SignUp signUp = new com.pushertest.www.budgetcatcher.Model.SignUp(userName.getText().toString(), email.getText().toString(), password.getText().toString(), phone.getText().toString(), "general", question.getText().toString(), answer.getText().toString());

        BudgetCatcher.apiManager.signUp(signUp, new QueryCallback<String>() {
            @Override
            public void onSuccess(String response) {

                JSONObject jsonObject = null;
                try {

                    jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray(URL.API_KEY_DATA);
                    JSONObject userObject = data.getJSONObject(0);
                    String userID = userObject.getString(URL.API_KEY_USER_ID);

                    if (storeUserInformationInSharedPreference(userID)) {

                        Toast.makeText(SignUp.this, "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, ProfileSetup.class));
                        finish();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(String data) {

                email.setText("");
                email.setError("Email already exist!");

            }

            @Override
            public void onError(Throwable th) {

            }
        });

    }

    private boolean storeUserInformationInSharedPreference(String userId) {

        sharedPreferences = getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Config.SP_USER_ID, userId);
        editor.putInt(Config.SP_USER_CREATED_LEVEL, Config.SP_USER_CREATED_LEVEL_SIGN_UP);
        editor.putBoolean(Config.SP_LOGGED_IN, true);
        return editor.commit();

    }

}
