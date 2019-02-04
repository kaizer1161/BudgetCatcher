package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.User;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.R;
import com.budgetcatcher.www.budgetcatcher.View.Activity.MainActivity;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class Settings extends Fragment {

    private static final String TAG = "Settings";

    @BindView(R.id.edit_profile)
    TextView editProfile;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.financial_goal)
    TextView financialGoal;
    @BindView(R.id.risk_level)
    TextView riskLevel;
    @BindView(R.id.skill_level)
    TextView skillLevel;

    private ProgressDialog dialog, categoryDialog;
    private String userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            dialog = ProgressDialog.show(getActivity(), "",
                    getString(R.string.loading), true);
            dialog.dismiss();

            categoryDialog = ProgressDialog.show(getActivity(), "",
                    getString(R.string.loading), true);
            categoryDialog.dismiss();

            userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");
            ((MainActivity) getActivity()).navigationView.setCheckedItem(R.id.nav_settings);

            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Settings");

            String userJson = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_INFO, "");

            if (!userJson.equals("")) {

                Gson gson = new Gson();
                setDisplayValues(gson.fromJson(userJson, User.class));

            } else {

                dialog.show();

            }

            if (BudgetCatcher.getConnectedToInternet()) {

                fetchUserInfo();

            }

        }

        return rootView;
    }

    private void fetchUserInfo() {

        BudgetCatcher.apiManager.getUserInfo(userID, new QueryCallback<ArrayList<User>>() {
            @Override
            public void onSuccess(ArrayList<User> user) {

                dialog.dismiss();
                setDisplayValues(user.get(0));
                storeUserInformationInSharedPreference(user.get(0));

            }

            @Override
            public void onFail() {

                dialog.dismiss();
                Toast.makeText(getActivity(), "Error finding user information", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable th) {

                dialog.dismiss();
                Log.e("SerVerErr", th.toString());
                if (th instanceof SocketTimeoutException) {
                    Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.server_reach_error), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void setDisplayValues(User userDetails) {

        if (getActivity() != null) {

            name.setText(userDetails.getUsername());
            email.setText(userDetails.getUserEmailId());
            phone.setText(userDetails.getUserPhoneNo());
            financialGoal.setText(userDetails.getFinancialGoal());
            riskLevel.setText(userDetails.getRiskLevel());
            skillLevel.setText(userDetails.getSkillLevel());

            if (getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_PROFILE_PIC, "").equals("")) {

                profileImage.setImageDrawable(getActivity().getDrawable(R.drawable.propic));

            } else {

                byte[] decodedString = Base64.decode(getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_PROFILE_PIC, ""), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                profileImage.setImageBitmap(decodedByte);

            }

        }

    }

    @OnClick({R.id.edit_profile})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.edit_profile: {

                if (getActivity() != null) {

                    if (BudgetCatcher.getConnectedToInternet()) {

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content, new BasicInfo(), Config.TAG_BASIC_INFO_FRAGMENT)
                                .addToBackStack(null)
                                .commit();

                    } else {

                        Toast.makeText(getActivity(), getString(R.string.connect_to_internet), Toast.LENGTH_SHORT).show();

                    }

                }

                break;
            }

        }

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
