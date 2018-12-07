package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.budgetcatcher.www.budgetcatcher.Model.Category;
import com.budgetcatcher.www.budgetcatcher.Model.InsertAllowanceBody;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.R;
import com.budgetcatcher.www.budgetcatcher.View.Activity.MainActivity;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class AddAllowance extends Fragment {

    private static final int SPINNER_INITIAL_POSITION = 0;
    @BindView(R.id.category)
    Spinner categorySpinner;
    @BindView(R.id.allowance_name)
    EditText allowanceName;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.amount)
    EditText amount;

    private ArrayList<String> categoryListName, categoryListId;
    private ArrayAdapter<String> categoryAdapter;
    private Boolean categoryNameSelected = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_allowance, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Add Allowance");
            fetchCategory();

        }

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

        return rootView;
    }

    private void fetchCategory() {

        BudgetCatcher.apiManager.getCategory(new QueryCallback<ArrayList<Category>>() {
            @Override
            public void onSuccess(ArrayList<Category> data) {

                categoryListId = new ArrayList<>();
                categoryListName = new ArrayList<>();

                categoryListId.add("");
                categoryListName.add("Select category");

                for (int i = 0; i < data.size(); i++) {

                    categoryListId.add(data.get(i).getCategoryId());
                    categoryListName.add(data.get(i).getCategoryName());

                }

                categoryAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, R.id.spinner_item_text, categoryListName);
                categorySpinner.setAdapter(categoryAdapter);

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onError(Throwable th) {

                if (th instanceof SocketTimeoutException) {

                    if (getActivity() != null) {

                        Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });

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
                if (!categoryNameSelected) {

                    Toast.makeText(getActivity(), "Please finish the form and select all menu", Toast.LENGTH_SHORT).show();

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

        if (getActivity() != null) {

            String userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

            InsertAllowanceBody insertBillBody = new InsertAllowanceBody(userID, categoryListId.get(categorySpinner.getSelectedItemPosition()), amount.getText().toString(), description.getText().toString(), allowanceName.getText().toString(), "null");

            BudgetCatcher.apiManager.insertAllowance(insertBillBody, new QueryCallback<String>() {
                @Override
                public void onSuccess(String data) {

                    getActivity().onBackPressed();

                }

                @Override
                public void onFail() {

                }

                @Override
                public void onError(Throwable th) {

                    if (th instanceof SocketTimeoutException) {

                        if (getActivity() != null) {

                            Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();

                        }

                    }

                }
            });

        }

    }

}
