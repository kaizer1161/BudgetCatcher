package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.Adapter.CategoryListAdapter;
import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.AddCategory;
import com.budgetcatcher.www.budgetcatcher.Model.Category;
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
    @BindView(R.id.add_categories)
    TextView addCategories;
    @BindView(R.id.category_recycler_view)
    RecyclerView categoryRecyclerView;

    private ProgressDialog dialog, categoryDialog;
    private String userID;
    private CategoryListAdapter categoryListAdapter;

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
                fetchCategory();

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
                    Toast.makeText(getActivity(), th.toString(), Toast.LENGTH_SHORT).show();
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

            if (userDetails.getProfilePicUrl() != null) {

                byte[] decodedString = Base64.decode(userDetails.getProfilePicUrl(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                profileImage.setImageBitmap(decodedByte);

            }

        }

    }

    @OnClick({R.id.edit_profile, R.id.add_categories})
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

                        Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();

                    }

                }

                break;
            }

            case R.id.add_categories: {

                if (BudgetCatcher.getConnectedToInternet()) {

                    addCategory();

                } else {

                    Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();

                }

                break;
            }

        }

    }

    private void addCategory() {

        final Activity activity = getActivity();

        final AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setCancelable(true);

        LayoutInflater inflater = this.getLayoutInflater();
        View alertView = inflater.inflate(R.layout.add_category_alert_box, null);
        builder1.setView(alertView);

        final EditText categoryName = alertView.findViewById(R.id.add_catego);

        builder1.setPositiveButton(
                getContext().getResources().getString(R.string.done),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                }).setNeutralButton(getContext().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.cancel();
            }
        });

        final AlertDialog alert11 = builder1.create();

        alert11.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert11.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.RED);
                alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getResources().getColor(R.color.colorAccent));
                alert11.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!categoryName.getText().toString().equals("")) {

                            AddCategory category = new AddCategory(categoryName.getText().toString(), Config.CATEGORY_BILL_TAG_ID, userID);

                            categoryDialog.show();

                            BudgetCatcher.apiManager.addCategory(category, new QueryCallback<String>() {
                                @Override
                                public void onSuccess(String data) {

                                    categoryDialog.dismiss();
                                    alert11.dismiss();
                                    Toast.makeText(getActivity(), "Successfully category added", Toast.LENGTH_SHORT).show();
                                    if (categoryListAdapter.getItemCount() > 0) {

                                        categoryListAdapter.clearList();

                                    }

                                    fetchCategory();

                                }

                                @Override
                                public void onFail() {

                                    Toast.makeText(getActivity(), "Failed to add category", Toast.LENGTH_SHORT).show();
                                    categoryDialog.dismiss();

                                }

                                @Override
                                public void onError(Throwable th) {

                                    categoryDialog.dismiss();
                                    Log.e("SerVerErr", th.toString());
                                    if (th instanceof SocketTimeoutException) {
                                        Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), th.toString(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                        } else {

                            categoryName.setError("Empty");

                        }

                    }
                });

            }
        });

        alert11.show();

    }

    private void showCategory(ArrayList<Category> categories) {

        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryListAdapter = new CategoryListAdapter(getActivity(), categories, Config.TAG_LIST_CATEGORY);
        categoryRecyclerView.setAdapter(categoryListAdapter);

    }

    private void fetchCategory() {

        categoryDialog.show();
        BudgetCatcher.apiManager.getCategory(Config.CATEGORY_BILL_TAG_ID, userID, new QueryCallback<ArrayList<Category>>() {
            @Override
            public void onSuccess(ArrayList<Category> data) {

                ArrayList<Category> categories = new ArrayList<>();

                for (int i = 0; i < data.size(); i++) {

                    if (data.get(i).getUserId() != null) {

                        categories.add(data.get(i));

                    }

                }

                categoryDialog.dismiss();
                showCategory(categories);

            }

            @Override
            public void onFail() {

                categoryDialog.dismiss();
                Toast.makeText(getActivity(), "Failed to fetch Category", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable th) {

                categoryDialog.dismiss();
                if (getActivity() != null) {
                    Log.e("SerVerErrAddBill", th.toString());
                    if (th instanceof SocketTimeoutException) {
                        Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), th.toString(), Toast.LENGTH_SHORT).show();
                    }
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
