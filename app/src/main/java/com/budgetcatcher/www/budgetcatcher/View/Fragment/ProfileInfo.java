package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.ProfileSetupBody;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.R;
import com.budgetcatcher.www.budgetcatcher.View.Activity.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class ProfileInfo extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int SPINNER_INITIAL_POSITION = 0;

    @BindView(R.id.financial_goal)
    Spinner financialGoalSpinner;
    @BindView(R.id.risk_level)
    Spinner riskLevelSpinner;
    @BindView(R.id.skill_level)
    Spinner skillLevelSpinner;
    @BindView(R.id.profile_image)
    ImageView profileImage;

    ProgressDialog dialog;

    private BroadcastReceiver mNetworkReceiver;

    private String imageString;
    private Boolean financialGoalSpinnerSelected = false, riskLevelSpinnerSelected = false,
            skillLevelSpinnerSelected = false, profileImageSelected = false;

    private ArrayList<String> financialGoal;
    private ArrayList<String> riskLevel;
    private ArrayList<String> skillLevel;

    private ArrayAdapter<String> financialGoalAdapter;
    private ArrayAdapter<String> riskLevelAdapter;
    private ArrayAdapter<String> skillLevelAdapter;

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

        financialGoalList();
        riskLevelList();
        skillLevelList();

        financialGoalAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, R.id.spinner_item_text, financialGoal);
        financialGoalSpinner.setAdapter(financialGoalAdapter);

        riskLevelAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, R.id.spinner_item_text, riskLevel);
        riskLevelSpinner.setAdapter(riskLevelAdapter);

        skillLevelAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, R.id.spinner_item_text, skillLevel);
        skillLevelSpinner.setAdapter(skillLevelAdapter);

        financialGoalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*
                 * If position == SPINNER_INITIAL_POSITION,
                 * then financialGoalSpinnerSelected = false
                 * else financialGoalSpinnerSelected = true.
                 */
                financialGoalSpinnerSelected = position != SPINNER_INITIAL_POSITION;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        riskLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*
                 * If position == SPINNER_INITIAL_POSITION,
                 * then riskLevelSpinnerSelected = false
                 * else riskLevelSpinnerSelected = true.
                 */
                riskLevelSpinnerSelected = position != SPINNER_INITIAL_POSITION;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        skillLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*
                 * If position == SPINNER_INITIAL_POSITION,
                 * then skillLevelSpinnerSelected = false
                 * else skillLevelSpinnerSelected = true.
                 */
                skillLevelSpinnerSelected = position != SPINNER_INITIAL_POSITION;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return rootView;
    }

    public void skillLevelList() {

        skillLevel = new ArrayList<>();
        skillLevel.add("Select your financial skill level");
        skillLevel.add("1 - Not good at numbers!");
        skillLevel.add("2 - I can count to 10!");
        skillLevel.add("3 - I'm as good as the next person");
        skillLevel.add("4 - I aced collage math");
        skillLevel.add("5 - Professional");

    }

    public void financialGoalList() {

        financialGoal = new ArrayList<>();
        financialGoal.add("Select financial goals");
        financialGoal.add("Add to Savings");
        financialGoal.add("Add to Cash");
        financialGoal.add("Reduce Your Debt");

    }

    public void riskLevelList() {

        riskLevel = new ArrayList<>();
        riskLevel.add("Select your risk level");
        riskLevel.add("Take No Risk");
        riskLevel.add("Mild Risk is Ok");
        riskLevel.add("Moderate & Ready to Play");
        riskLevel.add("All or NOTHING");


    }

    @OnClick({R.id.save, R.id.choose_from_gallery})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.save: {

                dialog.show();

                if (profileImageSelected && financialGoalSpinnerSelected && riskLevelSpinnerSelected && skillLevelSpinnerSelected) {

                    String userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

                    ProfileSetupBody profileSetupBody = new ProfileSetupBody(imageString, riskLevel.get(riskLevelSpinner.getSelectedItemPosition()), skillLevel.get(skillLevelSpinner.getSelectedItemPosition()), financialGoal.get(financialGoalSpinner.getSelectedItemPosition()));

                    if (!userID.equals("")) {

                        if (BudgetCatcher.getConnectedToInternet()) {
                            BudgetCatcher.apiManager.userProfileSetup(userID, profileSetupBody, new QueryCallback<String>() {
                                @Override
                                public void onSuccess(String data) {

                                    dialog.dismiss();

                                    /*if (storeUserInformationInSharedPreference()) {

                                        Toast.makeText(getActivity(), "Welcome to Budget Catcher", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getActivity(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                                    }*/

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
                        } else {

                            Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();

                        }
                    }

                } else {

                    Toast.makeText(getActivity(), "Please select all fields", Toast.LENGTH_SHORT).show();

                }

                break;
            }

            case R.id.choose_from_gallery: {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);

                break;
            }

        }

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp = Bitmap.createScaledBitmap(bmp, 1484, 2914, true);
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        profileImage.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && null != resultData && resultData.getData() != null) {
            Uri filePath = resultData.getData();
            Bitmap bitmap = null;
            try {

                if (getActivity() != null) {

                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                    imageString = getStringImage(bitmap);
                    profileImageSelected = true;

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private boolean storeUserInformationInSharedPreference() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        /*editor.putInt(Config.SP_USER_CREATED_LEVEL, Config.SP_USER_CREATED_LEVEL_NONE);
        editor.putBoolean(Config.SP_LOGGED_IN, true);*/
        return editor.commit();

    }

}