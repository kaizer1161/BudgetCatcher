package com.pushertest.www.budgetcatcher.View.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignIn extends AppCompatActivity {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @OnClick({R.id.signIn, R.id.forgot_password})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.signIn: {

                boolean hasError = false;

                if (password.getText().toString().equals("")) {
                    password.setError("Empty");
                    hasError = true;
                }
                if (email.getText().toString().equals("")) {
                    email.setError("Empty");
                    hasError = true;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {

                    email.setError("Wrong email format");
                    hasError = true;

                }
                if (!hasError) {

                    BudgetCatcher.apiManager.userSignIn(email.getText().toString(), password.getText().toString(), new QueryCallback<String>() {
                        @Override
                        public void onSuccess(String response) {

                            JSONObject jsonObject = null;
                            try {

                                jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray(URL.API_KEY_DATA);
                                JSONObject userObject = data.getJSONObject(0);
                                String userID = userObject.getString(URL.API_KEY_USER_ID);

                                if (storeUserInformationInSharedPreference(userID)) {

                                    Toast.makeText(SignIn.this, "Success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignIn.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFail() {

                            email.setText("");
                            password.setText("");

                            email.setError("Wrong email");
                            password.setError("Wrong password");

                        }

                        @Override
                        public void onError(Throwable th) {

                        }
                    });

                }

                break;
            }

            case R.id.forgot_password: {

                /*startActivity(new Intent(SignIn.this, SignUpBody.class));*/

                break;
            }
        }

    }

    private boolean storeUserInformationInSharedPreference(String userId) {

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Config.SP_USER_ID, userId);
        editor.putBoolean(Config.SP_LOGGED_IN, true);
        return editor.commit();

    }

}
