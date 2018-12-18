package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.Allowance;
import com.budgetcatcher.www.budgetcatcher.Model.Category;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyAllowanceBody;
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

import static android.content.Context.MODE_PRIVATE;

public class EditAllowance extends Fragment {

    private static final int SPINNER_INITIAL_POSITION = 0;
    @BindView(R.id.category)
    Spinner categorySpinner;
    /*@BindView(R.id.status)
    Spinner statusSpinner;*/
    @BindView(R.id.allowance_name)
    EditText allowanceName;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.amount)
    EditText amount;
    private ArrayList<String> status, categoryListName, categoryListId;
    private ArrayAdapter<String> /*statusAdapter,*/ categoryAdapter;
    private Boolean statusSelected = false, categoryNameSelected = false;
    private String date = "";
    private Allowance allowance;
    private ProgressDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            Gson gson = new Gson();
            String json = getArguments().getString(Config.KEY_SERIALIZABLE);

            allowance = gson.fromJson(json, Allowance.class);

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_allowance, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            dialog = ProgressDialog.show(getActivity(), "",
                    getString(R.string.loading), true);
            dialog.dismiss();

            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Edit Allowance");
            statusList();
            fetchCategory();

        }

        showAllDataInUI();
        //

        initializeAllSpinnerSelectedListener();

        return rootView;
    }

    private void initializeAllSpinnerSelectedListener() {

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*
                 * If position == SPINNER_INITIAL_POSITION,
                 * then financialGoalSpinnerSelected = false
                 * else financialGoalSpinnerSelected = true.
                 */
                categoryNameSelected = position != SPINNER_INITIAL_POSITION;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                *//*
         * If position == SPINNER_INITIAL_POSITION,
         * then financialGoalSpinnerSelected = false
         * else financialGoalSpinnerSelected = true.
         *//*
                statusSelected = position != SPINNER_INITIAL_POSITION;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

    }

    private void fetchCategory() {

        dialog.show();

        if (getActivity() != null) {

            String userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

            BudgetCatcher.apiManager.getCategory(Config.CATEGORY_ALLOWANCE_TAG_ID, userID, new QueryCallback<ArrayList<Category>>() {
                @Override
                public void onSuccess(ArrayList<Category> data) {

                    dialog.dismiss();
                    categoryListId = new ArrayList<>();
                    categoryListName = new ArrayList<>();

                    categoryListId.add("");
                    categoryListName.add("Select category");

                    for (int i = 0; i < data.size(); i++) {

                        categoryListId.add(data.get(i).getCategoryId());
                        categoryListName.add(data.get(i).getCategoryName());

                    }

                    if (getActivity() != null) {

                        categoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, R.id.spinner_item_text, categoryListName);
                        categorySpinner.setAdapter(categoryAdapter);

                        for (int i = 0; i < categoryListName.size(); i++) {

                            if (categoryListId.get(i).equals(allowance.getCategoryId())) {

                                categorySpinner.setSelection(i, true);
                                break;

                            }

                        }

                    }

                }

                @Override
                public void onFail() {

                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Failed to fetch Category", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(Throwable th) {

                    dialog.dismiss();
                    if (getActivity() != null) {
                        Log.e("SerVerErrEditBill", th.toString());
                        if (th instanceof SocketTimeoutException) {
                            Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), th.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });

        }

    }

    private void statusList() {

        status = new ArrayList<>();
        status.add("Select status");
        status.add("Unpaid");
        status.add("Paid");

        /*statusAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, R.id.spinner_item_text, status);
        statusSpinner.setAdapter(statusAdapter);*/

    }

    @OnClick({R.id.save})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.save: {

                boolean hasError = false;

                if (allowanceName.getText().toString().equals("")) {
                    allowanceName.setError("Empty");
                    hasError = true;
                }
                if (description.getText().toString().equals("")) {
                    description.setError("Empty");
                    hasError = true;
                }
                if (amount.getText().toString().equals("")) {
                    amount.setError("Empty");
                    hasError = true;
                }
                if (/*!statusSelected ||*/ !categoryNameSelected /*|| date.equals("")*/) {

                    Toast.makeText(getActivity(), "Please finish the form and select all menu", Toast.LENGTH_SHORT).show();

                    hasError = true;
                }
                if (!BudgetCatcher.getConnectedToInternet()) {

                    hasError = true;
                    Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();

                }
                if (!hasError) {

                    saveDataToServer();

                }

                break;
            }

        }

    }

    private void saveDataToServer() {

        if (getActivity() != null) {

            dialog.show();
            String userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

            ModifyAllowanceBody modifyAllowanceBody = new ModifyAllowanceBody(categoryListId.get(categorySpinner.getSelectedItemPosition()), amount.getText().toString(), description.getText().toString(), allowanceName.getText().toString());

            BudgetCatcher.apiManager.modifyAllowance(userID, allowance.getAllowanceId(), modifyAllowanceBody, new QueryCallback<String>() {
                @Override
                public void onSuccess(String data) {

                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Successfully allowance edited", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();

                }

                @Override
                public void onFail() {

                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Failed to edit allowance", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(Throwable th) {

                    dialog.dismiss();
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

    }

    private void showAllDataInUI() {

        allowanceName.setText(allowance.getAllowanceName());
        description.setText(allowance.getDescription());
        amount.setText(allowance.getAllowanceAmount());

        /*for (int i = 0; i < status.size(); i++) {

            if (status.get(i).equals(bill.getStatus())) {

                statusSpinner.setSelection(i, true);
                break;
            }

        }*/


    }

}
