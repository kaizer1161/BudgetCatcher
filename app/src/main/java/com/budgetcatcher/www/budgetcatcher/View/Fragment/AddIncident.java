package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.R;
import com.budgetcatcher.www.budgetcatcher.View.Activity.MainActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddIncident extends Fragment {

    private static final int SPINNER_INITIAL_POSITION = 0;
    /*@BindView(R.id.category)
    Spinner categorySpinner;*/
    /*@BindView(R.id.status)
    Spinner statusSpinner;*/
    @BindView(R.id.bill_name)
    EditText billName;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.date_edit_text)
    TextView dateEditText;
    @BindView(R.id.date_picker)
    CalendarView datePicker;
    @BindView(R.id.amount)
    EditText amount;

    /*private ArrayList<String> status, categoryListName, categoryListId;
    private ArrayAdapter<String> *//*statusAdapter,*//* categoryAdapter;
    private Boolean statusSelected = false, categoryNameSelected = false;*/

    private String date = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_bill, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Add Bill");
            /*statusList();
            fetchCategory();*/

        }

        datePicker.setVisibility(View.GONE);
        datePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@androidx.annotation.NonNull @NonNull CalendarView view, int year, int month, int dayOfMonth) {

                dateEditText.setVisibility(View.VISIBLE);
                datePicker.setVisibility(View.GONE);

                date = year + "-" + month + "-" + dayOfMonth;
                dateEditText.setText(date);

            }
        });

        /*categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                *//*
         * If position == SPINNER_INITIAL_POSITION,
         * then financialGoalSpinnerSelected = false
         * else financialGoalSpinnerSelected = true.
         *//*
                categoryNameSelected = position != SPINNER_INITIAL_POSITION;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

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

        return rootView;
    }

    /*private void fetchCategory() {

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

            }
        });

    }

    private void statusList() {

        status = new ArrayList<>();
        status.add("Select status");
        status.add("Unpaid");
        status.add("Paid");

        *//*statusAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, R.id.spinner_item_text, status);
        statusSpinner.setAdapter(statusAdapter);*//*

    }*/

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
                if (/*!statusSelected ||*/ /*!categoryNameSelected ||*/ date.equals("")) {

                    Toast.makeText(getActivity(), "Please finish the form and select all menu", Toast.LENGTH_SHORT).show();

                    hasError = true;
                }

                if (!hasError) {

                    /*saveDataToServer();*/

                }

                break;
            }

            case R.id.date_edit_text: {

                dateEditText.setVisibility(View.GONE);
                datePicker.setVisibility(View.VISIBLE);

                break;
            }

        }

    }

    /*private void saveDataToServer() {

        if (getActivity() != null) {

            String userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

            InsertBillBody insertBillBody = new InsertBillBody(userID, categoryListId.get(categorySpinner.getSelectedItemPosition()), amount.getText().toString(), description.getText().toString(), date, "null", *//*status.get(statusSpinner.getSelectedItemPosition())*//* "null", billName.getText().toString());

            BudgetCatcher.apiManager.insertBill(insertBillBody, new QueryCallback<String>() {
                @Override
                public void onSuccess(String data) {

                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();

                }

                @Override
                public void onFail() {

                }

                @Override
                public void onError(Throwable th) {

                }
            });

        }

    }*/

}
