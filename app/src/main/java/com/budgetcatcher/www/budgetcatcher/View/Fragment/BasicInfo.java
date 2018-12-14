package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.User;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.R;
import com.budgetcatcher.www.budgetcatcher.View.Activity.MainActivity;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
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

        if (getActivity() != null) {

            String userJson = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_INFO, "");

            if (!userJson.equals("")) {

                Gson gson = new Gson();
                userDetail = gson.fromJson(userJson, User.class);

                userName.setText(userDetail.getUsername());
                phone.setText(userDetail.getUserPhoneNo());
                email.setText(userDetail.getUserEmailId());
                password.setText(userDetail.getUserPassword());
                question.setText(userDetail.getSecurityQuestion());
                answer.setText(userDetail.getSecurityAnswer());

            }

        } else {

            Toast.makeText(getActivity(), "User info not found", Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().popBackStack();

        }

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

                    userDetail.setUsername(userName.getText().toString());
                    userDetail.setUserPhoneNo(phone.getText().toString());
                    userDetail.setUserEmailId(email.getText().toString());
                    userDetail.setUserPassword(password.getText().toString());
                    userDetail.setSecurityQuestion(question.getText().toString());
                    userDetail.setSecurityAnswer(answer.getText().toString());

                    saveDataToServer();

                }

                break;
            }

            case R.id.back_to_sign_in: {

                goToProfileInfo();

                break;
            }

        }

    }

    private void goToProfileInfo() {

        if (getActivity() != null) {

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new ProfileInfo(), Config.TAG_PROFILE_INFO_FRAGMENT)
                    .addToBackStack(null)
                    .commit();

        }
    }

    private void saveDataToServer() {

        dialog.show();

        BudgetCatcher.apiManager.userProfileUpdate(userDetail.getUserId(), userDetail, new QueryCallback<String>() {
            @Override
            public void onSuccess(String data) {

                dialog.dismiss();

                Toast.makeText(getActivity(), "Successfully updated", Toast.LENGTH_SHORT).show();

                if (storeUserInformationInSharedPreference(userDetail)) {

                    goToProfileInfo();

                }

            }

            @Override
            public void onFail() {

                dialog.dismiss();
                Toast.makeText(getActivity(), "Failed to update profile", Toast.LENGTH_SHORT).show();

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

    }

    private boolean storeUserInformationInSharedPreference(User user) {

        if (getActivity() != null) {

            Gson gson = new Gson();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(Config.SP_USER_INFO, gson.toJson(user));
            return editor.commit();

        } else {
            return false;
        }

    }

}
