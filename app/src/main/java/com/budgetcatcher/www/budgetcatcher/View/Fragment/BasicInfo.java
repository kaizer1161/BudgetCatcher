package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.User;
import com.budgetcatcher.www.budgetcatcher.R;
import com.budgetcatcher.www.budgetcatcher.View.Activity.MainActivity;
import com.google.gson.Gson;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class BasicInfo extends Fragment {

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
    private User userDetail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            Gson gson = new Gson();
            String json = getArguments().getString(Config.KEY_SERIALIZABLE);

            userDetail = gson.fromJson(json, User.class);

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.update_basic_info, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            dialog = ProgressDialog.show(getActivity(), "",
                    getString(R.string.loading), true);
            dialog.dismiss();

            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Basic info");


        }

        setDisplayValues();

        return rootView;
    }

    private void setDisplayValues() {

        userName.setText(userDetail.getUsername());
        phone.setText(userDetail.getUserPhoneNo());
        email.setText(userDetail.getUserEmailId());
        password.setText(userDetail.getUserPassword());
        question.setText(userDetail.getSecurityQuestion());
        answer.setText(userDetail.getSecurityAnswer());


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
                    Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();

                }
                if (!hasError) {

                    /*saveDataToServer();*/
                    Toast.makeText(getActivity(), "Everything seems ok. This feature is coming soon", Toast.LENGTH_SHORT).show();

                }

                break;
            }

            case R.id.back_to_sign_in: {

                if (getActivity() != null) {

                    Gson gson = new Gson();
                    Bundle bundle = new Bundle();
                    bundle.putString(Config.KEY_SERIALIZABLE, gson.toJson(userDetail));

                    ProfileInfo profileInfo = new ProfileInfo();
                    profileInfo.setArguments(bundle);

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, profileInfo, Config.TAG_PROFILE_INFO_FRAGMENT)
                            .addToBackStack(null)
                            .commit();

                }

                break;
            }

        }

    }

    /*private void saveDataToServer() {

        dialog.show();

        SignUpBody signUpBody = new SignUpBody(userName.getText().toString(), email.getText().toString(), password.getText().toString(), phone.getText().toString(), "general", question.getText().toString(), answer.getText().toString());

        BudgetCatcher.apiManager.userSignUp(signUpBody, new QueryCallback<Response<String>>() {
            @Override
            public void onSuccess(Response<String> response) {

                dialog.dismiss();

                *//*if (response.code() == URL.STATUS_SERVER_CREATED) {

                    JSONObject jsonObject = null;
                    try {

                        jsonObject = new JSONObject(response.body());
                        JSONArray data = jsonObject.getJSONArray(URL.API_KEY_DATA);
                        JSONObject userObject = data.getJSONObject(0);
                        String userID = userObject.getString(URL.API_KEY_USER_ID);

                        if (storeUserInformationInSharedPreference(userID)) {

                            Toast.makeText(SignUp.this, "Successfully account created", Toast.LENGTH_SHORT).show();


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else if (response.code() == URL.STATUS_BAD_REQUEST) {

                    email.setText("");
                    email.setError("Email already exist!");

                }*//*


            }

            @Override
            public void onFail() {

                dialog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong: server error", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable th) {

                dialog.dismiss();
                Log.e("SerVerErr", th.toString());
                if (th instanceof SocketTimeoutException) {
                    Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), th.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }*/

    private boolean storeUserInformationInSharedPreference(String userId) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        /*editor.putString(Config.SP_USER_ID, userId);
        editor.putInt(Config.SP_USER_CREATED_LEVEL, Config.SP_USER_CREATED_LEVEL_SIGN_UP);*/
        return editor.commit();

    }


}
