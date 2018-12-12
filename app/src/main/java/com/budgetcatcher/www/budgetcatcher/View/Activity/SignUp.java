package com.budgetcatcher.www.budgetcatcher.View.Activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.SignUpBody;
import com.budgetcatcher.www.budgetcatcher.Network.NetworkChangeReceiver;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.Network.URL;
import com.budgetcatcher.www.budgetcatcher.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

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

    private BroadcastReceiver mNetworkReceiver;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();

        dialog = ProgressDialog.show(SignUp.this, "",
                getString(R.string.loading), true);
        dialog.dismiss();

    }

    @OnClick({R.id.sign_up_now, R.id.back_to_sign_in})
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
                if (!BudgetCatcher.getConnectedToInternet()) {

                    hasError = true;
                    Toast.makeText(SignUp.this, "No internet", Toast.LENGTH_SHORT).show();

                }
                if (!hasError) {

                    saveDataToServer();

                }

                break;
            }

            case R.id.back_to_sign_in: {

                super.onBackPressed();

                break;
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void saveDataToServer() {

        dialog.show();

        SignUpBody signUpBody = new SignUpBody(userName.getText().toString(), email.getText().toString(), password.getText().toString(), phone.getText().toString(), "general", question.getText().toString(), answer.getText().toString());

        BudgetCatcher.apiManager.userSignUp(signUpBody, new QueryCallback<Response<String>>() {
            @Override
            public void onSuccess(Response<String> response) {

                dialog.dismiss();

                if (response.code() == URL.STATUS_SERVER_CREATED) {

                    JSONObject jsonObject = null;
                    try {

                        jsonObject = new JSONObject(response.body());
                        JSONArray data = jsonObject.getJSONArray(URL.API_KEY_DATA);
                        JSONObject userObject = data.getJSONObject(0);
                        String userID = userObject.getString(URL.API_KEY_USER_ID);

                        if (storeUserInformationInSharedPreference(userID)) {

                            Toast.makeText(SignUp.this, "Successfully account created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this, ProfileSetup.class));
                            finish();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else if (response.code() == URL.STATUS_BAD_REQUEST) {

                    email.setText("");
                    email.setError("Email already exist!");

                }


            }

            @Override
            public void onFail() {

                dialog.dismiss();
                Toast.makeText(SignUp.this, "Something went wrong: server error", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable th) {

                dialog.dismiss();
                Log.e("SerVerErr", th.toString());
                if (th instanceof SocketTimeoutException) {
                    Toast.makeText(SignUp.this, getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUp.this, th.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean storeUserInformationInSharedPreference(String userId) {

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Config.SP_USER_ID, userId);
        editor.putInt(Config.SP_USER_CREATED_LEVEL, Config.SP_USER_CREATED_LEVEL_SIGN_UP);
        return editor.commit();

    }

    private void registerNetworkBroadcastForNougat() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
        } catch (Exception e) {
            Log.v("Internet Reg : ", " " + e);
        }

    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }

    @Override
    protected void onPause() {

        super.onPause();
        BudgetCatcher.activityPaused();// On Pause notify the Application
    }

    @Override
    protected void onResume() {

        super.onResume();
        BudgetCatcher.activityResumed();// On Resume notify the Application
    }

}
