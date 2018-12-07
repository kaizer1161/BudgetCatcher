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
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.Bill;
import com.budgetcatcher.www.budgetcatcher.Model.Category;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyBillBody;
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

public class EditBill extends Fragment {

    private static final int SPINNER_INITIAL_POSITION = 0;
    @BindView(R.id.category)
    Spinner categorySpinner;
    /*@BindView(R.id.status)
    Spinner statusSpinner;*/
    @BindView(R.id.bill_name)
    EditText billName;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.date_edit_text)
    TextView dateTextView;
    @BindView(R.id.date_picker)
    CalendarView datePicker;
    @BindView(R.id.amount)
    EditText amount;
    private ArrayList<String> status, categoryListName, categoryListId;
    private ArrayAdapter<String> /*statusAdapter,*/ categoryAdapter;
    private Boolean statusSelected = false, categoryNameSelected = false;
    private String date = "";
    private Bill bill;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            Gson gson = new Gson();
            String json = getArguments().getString(Config.KEY_SERIALIZABLE);

            bill = gson.fromJson(json, Bill.class);

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_bill, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Edit Bill");
            statusList();
            fetchCategory();

        }

        showAllDataInUI();
        //

        datePicker.setVisibility(View.GONE);
        datePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@androidx.annotation.NonNull @NonNull CalendarView view, int year, int month, int dayOfMonth) {

                dateTextView.setVisibility(View.VISIBLE);
                datePicker.setVisibility(View.GONE);

                date = year + "-" + month + "-" + dayOfMonth;
                dateTextView.setText(date);

            }
        });

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

                for (int i = 0; i < categoryListName.size(); i++) {

                    if (categoryListName.get(i).equals(bill.getCategory())) {

                        categorySpinner.setSelection(i, true);
                        break;

                    }

                }

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

    private void statusList() {

        status = new ArrayList<>();
        status.add("Select status");
        status.add("Unpaid");
        status.add("Paid");

        /*statusAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, R.id.spinner_item_text, status);
        statusSpinner.setAdapter(statusAdapter);*/

    }

    @OnClick({R.id.save, R.id.date_edit_text})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.save: {

                boolean hasError = false;

                if (billName.getText().toString().equals("")) {
                    billName.setError("Empty");
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
                if (/*!statusSelected ||*/ !categoryNameSelected || date.equals("")) {

                    Toast.makeText(getActivity(), "Please finish the form and select all menu", Toast.LENGTH_SHORT).show();

                    hasError = true;
                }

                if (!hasError) {

                    saveDataToServer();

                }

                break;
            }

            case R.id.date_edit_text: {

                dateTextView.setVisibility(View.GONE);
                datePicker.setVisibility(View.VISIBLE);

                break;
            }

        }

    }

    private void saveDataToServer() {

        if (getActivity() != null) {

            String userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

            ModifyBillBody modifyBillBody = new ModifyBillBody(categoryListId.get(categorySpinner.getSelectedItemPosition()), billName.getText().toString(), amount.getText().toString(), description.getText().toString(), date, "null" /*status.get(statusSpinner.getSelectedItemPosition())*/);

            BudgetCatcher.apiManager.modifyBill(userID, bill.getBillId(), modifyBillBody, new QueryCallback<String>() {
                @Override
                public void onSuccess(String data) {

                    Toast.makeText(getActivity(), "Bill successfully edited", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, new Manage(), Config.TAG_MANAGE_FRAGMENT)
                            .commit();

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

    private void showAllDataInUI() {

        billName.setText(bill.getBillName());
        description.setText(bill.getDescription());
        amount.setText(bill.getAmount());
        dateTextView.setText(bill.getDueDate());
        date = bill.getDueDate();

        /*for (int i = 0; i < status.size(); i++) {

            if (status.get(i).equals(bill.getStatus())) {

                statusSpinner.setSelection(i, true);
                break;
            }

        }*/


    }

}