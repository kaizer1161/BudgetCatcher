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
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.Income;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyIncomeBody;
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

public class EditIncome extends Fragment {

    private static final int SPINNER_INITIAL_POSITION = 0;

    @BindView(R.id.pay_frequency)
    Spinner payFrequencySpinner;
    /*@BindView(R.id.status)
    Spinner statusSpinner;*/
    @BindView(R.id.net_pay)
    EditText netPay;
    @BindView(R.id.date_edit_text)
    TextView dateEditText;
    @BindView(R.id.date_picker)
    CalendarView datePicker;

    private Boolean payFrequencySpinnerSelected = false;
    private ArrayList<String> payFrequencyList;
    private ArrayAdapter<String> payFrequencyListAdapter;

    private String date = "";
    private ProgressDialog dialog;
    private String userID;
    private Income income;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            Gson gson = new Gson();
            String json = getArguments().getString(Config.KEY_SERIALIZABLE);

            income = gson.fromJson(json, Income.class);

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_income, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

            dialog = ProgressDialog.show(getActivity(), "",
                    getString(R.string.loading), true);
            dialog.dismiss();

            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Edit Income");
            setPayFrequencyList();
            payFrequencyListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, R.id.spinner_item_text, payFrequencyList);
            payFrequencySpinner.setAdapter(payFrequencyListAdapter);
        }

        showAllDataInUI();

        datePicker.setVisibility(View.GONE);
        datePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@androidx.annotation.NonNull @NonNull CalendarView view, int year, int month, int dayOfMonth) {

                dateEditText.setVisibility(View.VISIBLE);
                datePicker.setVisibility(View.GONE);

                date = year + "-" + (month + 1) + "-" + dayOfMonth;
                dateEditText.setText(date);

            }
        });

        payFrequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*
                 * If position == SPINNER_INITIAL_POSITION,
                 * then financialGoalSpinnerSelected = false
                 * else financialGoalSpinnerSelected = true.
                 */
                payFrequencySpinnerSelected = position != SPINNER_INITIAL_POSITION;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        netPay.requestFocus();

        return rootView;
    }

    @OnClick({R.id.save, R.id.date_edit_text})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.save: {

                boolean hasError = false;

                if (netPay.getText().toString().equals("")) {
                    netPay.setError("Empty");
                    hasError = true;
                }

                if (/*!statusSelected ||*/ !payFrequencySpinnerSelected || date.equals("")) {

                    Toast.makeText(getActivity(), "Please finish the form and select all menu", Toast.LENGTH_SHORT).show();

                    hasError = true;
                }
                if (!BudgetCatcher.getConnectedToInternet()) {

                    hasError = true;
                    Toast.makeText(getActivity(), getString(R.string.connect_to_internet), Toast.LENGTH_SHORT).show();

                }
                if (!hasError) {

                    saveDataToServer();

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

    public void setPayFrequencyList() {

        payFrequencyList = new ArrayList<>();
        payFrequencyList.add("Select pay frequency");
        payFrequencyList.add("Weekly");
        payFrequencyList.add("Bi weekly");
        payFrequencyList.add("Monthly");
        payFrequencyList.add("Bi monthly");

    }

    private void saveDataToServer() {

        if (getActivity() != null) {

            dialog.show();

            ModifyIncomeBody modifyIncomeBody = new ModifyIncomeBody(netPay.getText().toString(), payFrequencyList.get(payFrequencySpinner.getSelectedItemPosition()), date, "null", "null");

            String userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

            BudgetCatcher.apiManager.modifyIncome(userID, income.getIncomeId(), modifyIncomeBody, new QueryCallback<String>() {
                @Override
                public void onSuccess(String data) {

                    dialog.dismiss();
                    Toast.makeText(getActivity(), getString(R.string.successfully_edited), Toast.LENGTH_SHORT).show();
                    if (getActivity() != null) {

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content, new Manage(), Config.TAG_MANAGE_FRAGMENT)
                                .commit();

                    }

                }

                @Override
                public void onFail() {

                    dialog.dismiss();
                    Toast.makeText(getActivity(), getString(R.string.failed_to_edited), Toast.LENGTH_SHORT).show();

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

        Float val = Float.parseFloat(income.getAmount());
        String valStr = String.format("%.2f", val);

        netPay.setText(valStr);
        dateEditText.setText(income.getNextPayDay());
        date = income.getNextPayDay();

        for (int i = 0; i < payFrequencyList.size(); i++) {

            if (income.getFrequency().equals(payFrequencyList.get(i))) {

                payFrequencySpinner.setSelection(i);
                break;

            }

        }

    }

}
