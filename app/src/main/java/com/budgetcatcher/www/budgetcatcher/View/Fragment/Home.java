package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.Adapter.AccountListAdapter;
import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.AccountItem;
import com.budgetcatcher.www.budgetcatcher.Model.AddOutstandingCheckBody;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyHomeBody;
import com.budgetcatcher.www.budgetcatcher.Model.Month;
import com.budgetcatcher.www.budgetcatcher.Model.MonthData;
import com.budgetcatcher.www.budgetcatcher.Model.OutstandingCheckResponseBody;
import com.budgetcatcher.www.budgetcatcher.Model.OutstandingChecks;
import com.budgetcatcher.www.budgetcatcher.Model.Week;
import com.budgetcatcher.www.budgetcatcher.Model.WeekData;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.R;
import com.budgetcatcher.www.budgetcatcher.View.Activity.MainActivity;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class Home extends Fragment {

    @BindView(R.id.projected_balance_layout)
    CoordinatorLayout projectedBalanceLayoutBottomSheet;
    private static final int totalFetchCount = 4;
    private static final String TAG = "Home";
    @BindView(R.id.add_to_saving)
    EditText addToSavings;
    @BindView(R.id.reduce_debts)
    EditText reduceDebts;
    @BindView(R.id.editTextAddToCash)
    EditText addToCash;
    @BindView(R.id.ending_balance)
    TextView endingBalance;
    @BindView(R.id.week_picker)
    NumberPicker weekPicker;
    @BindView(R.id.month_picker)
    NumberPicker monthPicker;
    @BindView(R.id.weekly_monthly)
    Switch weeklyMonthlySwitch;
    @BindView(R.id.header_top)
    TextView headerTop;
    @BindView(R.id.header_bottom)
    TextView headerBottom;
    @BindView(R.id.start_cash_balance)
    TextView startCashBalance;
    @BindView(R.id.income)
    TextView income;
    @BindView(R.id.expenses)
    TextView expenses;
    @BindView(R.id.deficit)
    TextView deficit;

    private boolean isMonthSelected = false, isProjectedBalanceSelected = false;
    private ProgressDialog dialog;
    private SharedPreferences sharedPreferences;
    private int dataFetchCount = 0;
    private String userID;
    private ArrayList<Week> weekArrayList;
    private ArrayList<Month> monthArrayList;
    private Week currentWeek;
    private Month currentMonth;
    private int weekIndex, monthIndex;
    private String[] weekDate, monthDate;
    private float reduceDebtsAmount = 0, savingsAmount = 0;
    private AlertDialog OCAlert;
    private NumberFormat numberFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            dialog = ProgressDialog.show(getActivity(), "",
                    getString(R.string.loading), true);
            dialog.dismiss();

            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Home");
            ((MainActivity) getActivity()).navigationView.setCheckedItem(R.id.nav_home);

            sharedPreferences = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE);
            userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");


            ((MainActivity) getActivity()).projectedBalanceBottomSheetBehavior = BottomSheetBehavior.from(projectedBalanceLayoutBottomSheet);
            ((MainActivity) getActivity()).projectedBalanceBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            numberFormat = NumberFormat.getNumberInstance(Locale.US);


        } else {

            Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

        }

        editTextCursorVisibility(false);

        if (getActivity() != null) {

            if (getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_WEEK_INFO, "").equals("") || getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_MONTH_INFO, "").equals("") || getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_CURRENT_WEEK_INFO, "").equals("") || getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_CURRENT_MONTH_INFO, "").equals("")) {

                fetchCurrentWeek();
                fetchCurrentMonth();
                fetchWeekBreakdown();
                fetchMonthBreakdown();

            } else {

                Gson gson = new Gson();

                WeekData tempWeekData = gson.fromJson(getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_WEEK_INFO, ""), WeekData.class);

                weekArrayList = tempWeekData.getWeeks();

                MonthData tempMonthData = gson.fromJson(getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_MONTH_INFO, ""), MonthData.class);

                monthArrayList = tempMonthData.getMonths();

                currentWeek = gson.fromJson(getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_CURRENT_WEEK_INFO, ""), WeekData.class).getWeeks().get(0);

                currentMonth = gson.fromJson(getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_CURRENT_MONTH_INFO, ""), MonthData.class).getMonths().get(0);

                homeUiDataUpdateBasedOnMonthOrWeek();

                dataFetchCount--;
                fetchCurrentWeek();
                fetchCurrentMonth();
                fetchWeekBreakdown();
                fetchMonthBreakdown();

            }

        }

        weeklyMonthlySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                isMonthSelected = isChecked;
                homeUiDataUpdateBasedOnMonthOrWeek();

            }
        });

        addToSavings.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {

                float deficitAmount = Float.parseFloat(deficit.getText().toString().replace("$ ", "").replace(",", ""));

                if (s.toString().equals(".")) {

                    addToSavings.setText("0.");
                    addToSavings.setSelection(addToSavings.getText().toString().length());

                }

                if (s.toString().length() < 11) {

                    if (s.toString().isEmpty()) {
                        savingsAmount = 0;
                    } else {
                        savingsAmount = Float.parseFloat(addToSavings.getText().toString().replace(",", ""));
                    }
                    if (reduceDebts.getText().toString().isEmpty()) {
                        reduceDebtsAmount = 0;
                    } else {
                        reduceDebtsAmount = Float.parseFloat(reduceDebts.getText().toString().replace(",", ""));
                    }
                    Float sum = deficitAmount - (savingsAmount + reduceDebtsAmount);
                    addToCash.setText(String.valueOf(sum));
                    float temp = sum + Float.parseFloat(startCashBalance.getText().toString().replace("$ ", "").replace(",", ""));
                    endingBalance.setText("$ " + numberFormat.format(temp));

                }

            }
        });

        reduceDebts.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                float deficitAmount = Float.parseFloat(deficit.getText().toString().replace("$ ", "").replace(",", ""));

                if (s.toString().equals(".")) {

                    reduceDebts.setText("0.");
                    reduceDebts.setSelection(reduceDebts.getText().toString().length());

                }

                if (s.toString().length() < 11) {

                    if (s.toString().isEmpty()) {
                        reduceDebtsAmount = 0;
                    } else {
                        reduceDebtsAmount = Float.parseFloat(reduceDebts.getText().toString());
                    }
                    if (addToSavings.getText().toString().isEmpty()) {
                        savingsAmount = 0;
                    } else {
                        savingsAmount = Float.parseFloat(addToSavings.getText().toString());
                    }
                    Float sum = deficitAmount - (savingsAmount + reduceDebtsAmount);
                    addToCash.setText(String.valueOf(sum));
                    Log.i("startingCash", startCashBalance.getText().toString());
                    String tempStr = startCashBalance.getText().toString().replace("$ ", "").replace(",", "");
                    float temp = sum + Float.parseFloat(tempStr);
                    endingBalance.setText("$ " + numberFormat.format(temp));
                }
            }
        });
        return rootView;
    }

    private void editTextCursorVisibility(boolean visibility) {

        if (getActivity() != null) {

            if (((MainActivity) getActivity()).projectedBalanceBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                ((MainActivity) getActivity()).projectedBalanceBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        }

        addToSavings.setCursorVisible(visibility);
        reduceDebts.setCursorVisible(visibility);

        addToSavings.setImeOptions(EditorInfo.IME_ACTION_DONE);
        reduceDebts.setImeOptions(EditorInfo.IME_ACTION_DONE);

    }

    @OnClick({R.id.projected_balance, R.id.done_bottom_sheet, R.id.add_to_saving, R.id.reduce_debts, R.id.left_arrow, R.id.right_arrow, R.id.date_display, R.id.save_to_adjust, R.id.outstanding_balance, R.id.video})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.date_display: {

                if (getActivity() != null)
                    ((MainActivity) getActivity()).projectedBalanceBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                isProjectedBalanceSelected = false;

                break;
            }

            case R.id.video: {

                /*startActivity(new Intent(getActivity(), VideoActivity.class));*/

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("http://biniyogbondhu.com/storage/videos/reconciliation.mp4"), "video/*");
                startActivity(intent);

                break;
            }

            case R.id.projected_balance: {

                if (getActivity() != null)
                    ((MainActivity) getActivity()).projectedBalanceBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                isProjectedBalanceSelected = true;

                break;
            }

            case R.id.done_bottom_sheet: {

                if (isProjectedBalanceSelected) {

                    if (getActivity() != null) {

                        ((MainActivity) getActivity()).projectedBalanceBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                    }

                } else {

                    if (getActivity() != null) {

                        ((MainActivity) getActivity()).projectedBalanceBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                    }

                    if (isMonthSelected) {

                        monthIndex = monthPicker.getValue();
                        headerTop.setText("Month of");
                        headerBottom.setText(String.format("%s %s", monthArrayList.get(monthPicker.getValue()).getMonth(), monthArrayList.get(monthPicker.getValue()).getYear()));
                        updateHomeData(monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getLastDayOfMonth());

                    } else {

                        weekIndex = weekPicker.getValue();

                        String yearStart, monthStart, dayStart, yearEnd, monthEnd, dayEnd;

                        yearStart = weekArrayList.get(weekIndex).getFirstDayOfEveryWeek().substring(0, 4);
                        monthStart = weekArrayList.get(weekIndex).getFirstDayOfEveryWeek().substring(5, 7);
                        dayStart = weekArrayList.get(weekIndex).getFirstDayOfEveryWeek().substring(8, 10);

                        yearEnd = weekArrayList.get(weekIndex).getLastDayOfEveryWeek().substring(0, 4);
                        monthEnd = weekArrayList.get(weekIndex).getLastDayOfEveryWeek().substring(5, 7);
                        dayEnd = weekArrayList.get(weekIndex).getLastDayOfEveryWeek().substring(8, 10);

                        headerTop.setText(String.format("Week of %s-%s-%s", monthStart, dayStart, yearStart));
                        headerBottom.setText(String.format("to %s-%s-%s", monthEnd, dayEnd, yearEnd));
                        updateHomeData(weekArrayList.get(weekIndex).getFirstDayOfEveryWeek(), weekArrayList.get(weekIndex).getLastDayOfEveryWeek());

                    }

                }

                break;
            }

            case R.id.add_to_saving: {

                editTextCursorVisibility(true);

                break;
            }
            case R.id.reduce_debts: {

                editTextCursorVisibility(true);

                break;
            }

            case R.id.save_to_adjust: {


                ModifyHomeBody modifyHomeBody = new ModifyHomeBody(endingBalance.getText().toString().replace("$ ", "").replace(",",""), Float.toString(savingsAmount), Float.toString(reduceDebtsAmount));

                if (isMonthSelected) {

                    updateEndingBalance(userID, monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getLastDayOfMonth(), modifyHomeBody);

                        /*updateHeader(startDate, endDate, "Month of");
                        updateHomeData(monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getLastDayOfMonth());*/


                } else {

                    updateEndingBalance(userID, weekArrayList.get(weekIndex).getFirstDayOfEveryWeek(), weekArrayList.get(weekIndex).getLastDayOfEveryWeek(), modifyHomeBody);
                        /*updateHeader(startDate, endDate, "Week of");
                        updateHomeData(weekArrayList.get(weekIndex).getFirstDayOfEveryWeek(), weekArrayList.get(weekIndex).getLastDayOfEveryWeek());*/

                }

                break;

            }

            case R.id.left_arrow: {

                if (isMonthSelected) {

                    if (monthIndex > 0) {

                        monthIndex--;

                        headerTop.setText("Month of");
                        headerBottom.setText(String.format("%s %s", monthArrayList.get(monthIndex).getMonth(), monthArrayList.get(monthIndex).getYear()));
                        updateHomeData(monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getLastDayOfMonth());

                    }

                } else {

                    if (weekIndex > 0) {

                        weekIndex--;

                        String yearStart, monthStart, dayStart, yearEnd, monthEnd, dayEnd;

                        yearStart = weekArrayList.get(weekIndex).getFirstDayOfEveryWeek().substring(0, 4);
                        monthStart = weekArrayList.get(weekIndex).getFirstDayOfEveryWeek().substring(5, 7);
                        dayStart = weekArrayList.get(weekIndex).getFirstDayOfEveryWeek().substring(8, 10);

                        yearEnd = weekArrayList.get(weekIndex).getLastDayOfEveryWeek().substring(0, 4);
                        monthEnd = weekArrayList.get(weekIndex).getLastDayOfEveryWeek().substring(5, 7);
                        dayEnd = weekArrayList.get(weekIndex).getLastDayOfEveryWeek().substring(8, 10);

                        headerTop.setText(String.format("Week of %s-%s-%s", monthStart, dayStart, yearStart));
                        headerBottom.setText(String.format("to %s-%s-%s", monthEnd, dayEnd, yearEnd));
                        updateHomeData(weekArrayList.get(weekIndex).getFirstDayOfEveryWeek(), weekArrayList.get(weekIndex).getLastDayOfEveryWeek());

                    }

                }

                break;
            }

            case R.id.right_arrow: {

                if (isMonthSelected) {

                    if (monthIndex < monthArrayList.size() - 1) {

                        monthIndex++;

                        headerTop.setText("Month of");
                        headerBottom.setText(String.format("%s %s", monthArrayList.get(monthIndex).getMonth(), monthArrayList.get(monthIndex).getYear()));
                        updateHomeData(monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getLastDayOfMonth());

                    }

                } else {

                    if (weekIndex < weekArrayList.size() - 1) {

                        weekIndex++;

                        String yearStart, monthStart, dayStart, yearEnd, monthEnd, dayEnd;

                        yearStart = weekArrayList.get(weekIndex).getFirstDayOfEveryWeek().substring(0, 4);
                        monthStart = weekArrayList.get(weekIndex).getFirstDayOfEveryWeek().substring(5, 7);
                        dayStart = weekArrayList.get(weekIndex).getFirstDayOfEveryWeek().substring(8, 10);

                        yearEnd = weekArrayList.get(weekIndex).getLastDayOfEveryWeek().substring(0, 4);
                        monthEnd = weekArrayList.get(weekIndex).getLastDayOfEveryWeek().substring(5, 7);
                        dayEnd = weekArrayList.get(weekIndex).getLastDayOfEveryWeek().substring(8, 10);

                        headerTop.setText(String.format("Week of %s-%s-%s", monthStart, dayStart, yearStart));
                        headerBottom.setText(String.format("to %s-%s-%s", monthEnd, dayEnd, yearEnd));
                        updateHomeData(weekArrayList.get(weekIndex).getFirstDayOfEveryWeek(), weekArrayList.get(weekIndex).getLastDayOfEveryWeek());

                    }

                }

                break;
            }

            case R.id.outstanding_balance: {

                getOutstandingChecks();

                break;
            }

        }

    }

    private void getOutstandingChecks() {

        dialog.show();
        BudgetCatcher.apiManager.getOutstandingChecks(userID, new QueryCallback<OutstandingCheckResponseBody>() {
            @Override
            public void onSuccess(OutstandingCheckResponseBody data) {

                dialog.dismiss();
                if (getContext() != null) {

                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setCancelable(false);

                    LayoutInflater inflater = getLayoutInflater();
                    View alertView = inflater.inflate(R.layout.outstanding_bills, null);
                    builder1.setView(alertView);
                    OCAlert = builder1.create();

                    TextView close = alertView.findViewById(R.id.close);
                    TextView addOS = alertView.findViewById(R.id.add_os);

                    TextView sCBalance = alertView.findViewById(R.id.start_cash_balance);
                    TextView totalOS = alertView.findViewById(R.id.total_os);
                    TextView bankBalance = alertView.findViewById(R.id.bank_balance);
                    RecyclerView recyclerView = alertView.findViewById(R.id.oc_list);
                    sCBalance.setText(startCashBalance.getText().toString());
                    ArrayList<AccountItem> accountItems = new ArrayList<>();

                    if (data != null) {

                        Float val = Float.parseFloat(data.getTotal());
                        String valStr = String.format("%.2f", val);

                        Float val1 = Float.parseFloat(startCashBalance.getText().toString().replace("$ ", "")) - Float.parseFloat(data.getTotal());
                        String valStr1 = String.format("%.2f", val1);

                        totalOS.setText("$ " + numberFormat.format(Float.parseFloat(valStr)));
                        bankBalance.setText(String.format("$ %s", numberFormat.format(Float.parseFloat(valStr1))));

                        for (int i = 0; i < data.getOutstandingChecks().size(); i++) {

                            OutstandingChecks outstandingChecks = data.getOutstandingChecks().get(i);
                            accountItems.add(new AccountItem(outstandingChecks.getCheckNo(), outstandingChecks.getOutBalance(), outstandingChecks.getOcId()));

                        }

                        showFeedOutstandingChecks(accountItems, data.getOutstandingChecks(), recyclerView);

                    } else {

                        totalOS.setText("$0");
                        bankBalance.setText(String.format("$%s", Float.parseFloat(startCashBalance.getText().toString().replace("$ ", "").replace(",",""))));

                    }

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            OCAlert.dismiss();

                        }
                    });

                    addOS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addOutstandingCheckLayout();


                        }
                    });

                    OCAlert.show();

                }

            }

            @Override
            public void onFail() {

                dialog.dismiss();
                if (getActivity() != null)
                    Toast.makeText(getActivity(), "Failed to load data: Try again later", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable th) {

                dialog.dismiss();

                if (getActivity() != null) {
                    Log.e("SerVerErrAddBill", th.toString());
                    if (th instanceof SocketTimeoutException) {
                        Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.server_reach_error), Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });

    }

    private void showFeedOutstandingChecks(ArrayList<AccountItem> accountItemArrayList, ArrayList<OutstandingChecks> outstandingChecksArrayList, RecyclerView recyclerView) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AccountListAdapter accountListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, Config.TAG_LIST_OUTSTANDING_CHECKS, outstandingChecksArrayList);
        recyclerView.setAdapter(accountListAdapter);

    }

    private void addOutstandingCheckLayout() {

        if (getContext() != null) {

            final AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setCancelable(false);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View alertView = inflater.inflate(R.layout.add_outstanding_bills, null);
            builder1.setView(alertView);
            final AlertDialog alert11 = builder1.create();

            final TextView checkNumber = alertView.findViewById(R.id.check_number);
            final TextView checkAmount = alertView.findViewById(R.id.check_amount);
            TextView cancel = alertView.findViewById(R.id.cancel);
            TextView add = alertView.findViewById(R.id.add);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alert11.dismiss();

                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean hasError = false;

                    if (checkNumber.getText().toString().equals("")) {
                        checkNumber.setError("Empty");
                        hasError = true;
                    }
                    if (checkAmount.getText().toString().equals("")) {
                        checkAmount.setError("Empty");
                        hasError = true;
                    }
                    if (!BudgetCatcher.getConnectedToInternet()) {

                        hasError = true;
                        Toast.makeText(getActivity(), getString(R.string.connect_to_internet), Toast.LENGTH_SHORT).show();

                    }
                    if (!hasError) {

                        addOutstandingCheck(checkNumber.getText().toString(), checkAmount.getText().toString(), alert11);

                    }


                }
            });

            alert11.show();

        }

    }

    private void addOutstandingCheck(String checkNumber, String checkAmount, final AlertDialog alertDialog) {

        dialog.show();
        AddOutstandingCheckBody addOutstandingCheckBody = new AddOutstandingCheckBody(userID, checkNumber, checkAmount);

        BudgetCatcher.apiManager.addOC(addOutstandingCheckBody, new QueryCallback<String>() {
            @Override
            public void onSuccess(String data) {

                alertDialog.dismiss();
                dialog.dismiss();

                final InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(imm).hideSoftInputFromWindow(Objects.requireNonNull(getView()).getWindowToken(), 0);

                Toast.makeText(getActivity(), getString(R.string.successfully_added), Toast.LENGTH_SHORT).show();

                OCAlert.dismiss();
                getOutstandingChecks();

            }

            @Override
            public void onFail() {

                dialog.dismiss();
                if (getActivity() != null)
                    Toast.makeText(getActivity(), "Failed to save data: Try again later", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable th) {

                dialog.dismiss();

                if (getActivity() != null) {
                    Log.e("SerVerErrAddBill", th.toString());
                    if (th instanceof SocketTimeoutException) {
                        Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.server_reach_error), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void updateEndingBalance(String userID, String startDate, String endDate, ModifyHomeBody modifyHomeBody) {

        dialog.show();

        BudgetCatcher.apiManager.modifyHome(userID, startDate, endDate, modifyHomeBody, new QueryCallback<String>() {
            @Override
            public void onSuccess(String data) {

                dialog.dismiss();
                if (getActivity() != null)
                    Toast.makeText(getActivity(), getString(R.string.successfully_updated), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFail() {

                dialog.dismiss();
                if (getActivity() != null)
                    Toast.makeText(getActivity(), "Failed to save data: Try again later", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable th) {

                dialog.dismiss();

                if (getActivity() != null) {
                    Log.e("SerVerErrAddBill", th.toString());
                    if (th instanceof SocketTimeoutException) {
                        Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.server_reach_error), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void fetchWeekBreakdown() {

        BudgetCatcher.apiManager.getWeekBreakDown(Config.WEEK_TAG_ID, new QueryCallback<String>() {
            @Override
            public void onSuccess(String response) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Config.SP_WEEK_INFO, response);
                editor.apply();

                Gson gson = new Gson();
                WeekData weekData = gson.fromJson(response, WeekData.class);
                weekArrayList = weekData.getWeeks();

                dataFetchCount++;
                updateData();

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onError(Throwable th) {

            }
        });

    }

    private void fetchCurrentWeek() {

        BudgetCatcher.apiManager.getCurrentWeek(Config.WEEK_TAG_ID, new QueryCallback<String>() {
            @Override
            public void onSuccess(String response) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Config.SP_CURRENT_WEEK_INFO, response);
                editor.apply();

                Gson gson = new Gson();
                currentWeek = gson.fromJson(response, WeekData.class).getWeeks().get(0);

                dataFetchCount++;
                updateData();

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onError(Throwable th) {

            }
        });

    }

    private void fetchCurrentMonth() {

        BudgetCatcher.apiManager.getCurrentMonth(Config.MONTH_TAG_ID, new QueryCallback<String>() {
            @Override
            public void onSuccess(String response) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Config.SP_CURRENT_MONTH_INFO, response);
                editor.apply();

                Gson gson = new Gson();
                currentMonth = gson.fromJson(response, MonthData.class).getMonths().get(0);

                dataFetchCount++;
                updateData();

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onError(Throwable th) {

            }
        });

    }

    private void fetchMonthBreakdown() {

        BudgetCatcher.apiManager.getMonthBreakDown(Config.MONTH_TAG_ID, new QueryCallback<String>() {
            @Override
            public void onSuccess(String response) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Config.SP_MONTH_INFO, response);
                editor.apply();

                Gson gson = new Gson();
                MonthData monthData = gson.fromJson(response, MonthData.class);
                monthArrayList = monthData.getMonths();

                dataFetchCount++;
                updateData();

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onError(Throwable th) {

            }
        });

    }

    private void updateData() {

        if (dataFetchCount == totalFetchCount) {
            homeUiDataUpdateBasedOnMonthOrWeek();

        }

    }

    private void homeUiDataUpdateBasedOnMonthOrWeek() {


        if (isMonthSelected) {

            monthPicker.setVisibility(View.VISIBLE);
            weekPicker.setVisibility(View.GONE);

            monthDate = new String[monthArrayList.size()];
            for (int i = 0; i < monthArrayList.size(); i++) {

                monthDate[i] = monthArrayList.get(i).getMonth();

            }

            monthIndex = Integer.parseInt(currentMonth.getMonthNumber()) - 1;
            headerTop.setText("Month of");
            headerBottom.setText(String.format("%s %s", monthArrayList.get(monthIndex).getMonth(), monthArrayList.get(monthPicker.getValue()).getYear()));
            //updateHomeData(currentMonth.getFirstDayOfMonth(), currentMonth.getLastDayOfMonth());
            updateHomeDataAndEndBalance(currentMonth.getFirstDayOfMonth(), currentMonth.getLastDayOfMonth(), isMonthSelected);

            monthPicker.setMinValue(0);
            monthPicker.setMaxValue(monthDate.length - 1);
            monthPicker.setDisplayedValues(monthDate);

        } else {

            monthPicker.setVisibility(View.GONE);
            weekPicker.setVisibility(View.VISIBLE);

            weekDate = new String[weekArrayList.size()];
            for (int i = 0; i < weekArrayList.size(); i++) {

                weekDate[i] = weekArrayList.get(i).getWeek();

            }

            weekIndex = Integer.parseInt(currentWeek.getWeekNumber()) - 1;
            String yearStart, monthStart, dayStart, yearEnd, monthEnd, dayEnd;

            yearStart = weekArrayList.get(weekIndex).getFirstDayOfEveryWeek().substring(0, 4);
            monthStart = weekArrayList.get(weekIndex).getFirstDayOfEveryWeek().substring(5, 7);
            dayStart = weekArrayList.get(weekIndex).getFirstDayOfEveryWeek().substring(8, 10);

            yearEnd = weekArrayList.get(weekIndex).getLastDayOfEveryWeek().substring(0, 4);
            monthEnd = weekArrayList.get(weekIndex).getLastDayOfEveryWeek().substring(5, 7);
            dayEnd = weekArrayList.get(weekIndex).getLastDayOfEveryWeek().substring(8, 10);

            headerTop.setText(String.format("Week of %s-%s-%s", monthStart, dayStart, yearStart));
            headerBottom.setText(String.format("to %s-%s-%s", monthEnd, dayEnd, yearEnd));
            //updateHomeData(currentWeek.getFirstDayOfEveryWeek(), currentWeek.getLastDayOfEveryWeek());
            updateHomeDataAndEndBalance(currentWeek.getFirstDayOfEveryWeek(), currentWeek.getLastDayOfEveryWeek(), isMonthSelected);

            weekPicker.setMinValue(0);
            weekPicker.setMaxValue(weekDate.length - 1);
            weekPicker.setDisplayedValues(weekDate);

        }

    }

    private void updateHomeData(String startDate, String endDate) {

        dialog.show();
        BudgetCatcher.apiManager.getHome(userID, startDate, endDate, new QueryCallback<com.budgetcatcher.www.budgetcatcher.Model.Home>() {
            @Override
            public void onSuccess(com.budgetcatcher.www.budgetcatcher.Model.Home home) {

                dialog.dismiss();

                DecimalFormat decimalFormat = new DecimalFormat();
                decimalFormat.setMaximumFractionDigits(2);

                startCashBalance.setText("$ " + decimalFormat.format(Float.parseFloat(home.getStartingBalance())));
                income.setText("$ " + decimalFormat.format(Float.parseFloat(home.getIncome())));
                expenses.setText("($ " + decimalFormat.format(Float.parseFloat(home.getExpense())) + ")");
                deficit.setText("$ " + decimalFormat.format(Float.parseFloat(home.getIncome()) - Float.parseFloat(home.getExpense())));
                endingBalance.setText("$ " + decimalFormat.format(Float.parseFloat(home.getStartingBalance()) + (Float.parseFloat(home.getIncome()) - Float.parseFloat(home.getExpense()) - (Float.parseFloat(home.getSavings() + Float.parseFloat(home.getDebts()))))));

                reduceDebts.setText(home.getDebts());
                addToSavings.setText(home.getSavings());
                addToCash.setText(decimalFormat.format(Float.parseFloat(home.getIncome()) - Float.parseFloat(home.getExpense()) - (Float.parseFloat(home.getSavings()) + Float.parseFloat(home.getDebts()))));

                if (home.getDebts().equals("0")) {
                    reduceDebts.getText().clear();
                    reduceDebts.setHint("Enter amount");
                }

                if (home.getSavings().equals("0")) {
                    addToSavings.getText().clear();
                    addToSavings.setHint("Enter amount");
                }

                if (home.getSavings().equals("0") && home.getDebts().equals("0")) {

                    addToCash.getText().clear();
                    addToCash.setText("0");

                }

            }

            @Override
            public void onFail() {

                dialog.dismiss();
                if (getActivity() != null)
                    Toast.makeText(getActivity(), "Failed to load data", Toast.LENGTH_SHORT).show();

                startCashBalance.setText("$ 0");
                income.setText("$ 0");
                expenses.setText("($ 0" + ")");
                deficit.setText("$ 0");
                endingBalance.setText("$ 0");

            }

            @Override
            public void onError(Throwable th) {

                dialog.dismiss();

                startCashBalance.setText("$ 0");
                income.setText("$ 0");
                expenses.setText("($ 0" + ")");
                deficit.setText("$ 0");
                endingBalance.setText("$ 0");

                if (getActivity() != null) {
                    Log.e("SerVerErrAddBill", th.toString());
                    if (th instanceof SocketTimeoutException) {
                        Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.server_reach_error), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void updateHomeDataAndEndBalance(String startDate, String endDate, final Boolean forMonth) {

        dialog.show();
        BudgetCatcher.apiManager.getHome(userID, startDate, endDate, new QueryCallback<com.budgetcatcher.www.budgetcatcher.Model.Home>() {
            @Override
            public void onSuccess(com.budgetcatcher.www.budgetcatcher.Model.Home home) {

                dialog.dismiss();

                DecimalFormat decimalFormat = new DecimalFormat();
                decimalFormat.setMaximumFractionDigits(2);

                startCashBalance.setText("$ " + decimalFormat.format(Float.parseFloat(home.getStartingBalance())));
                income.setText("$ " + decimalFormat.format(Float.parseFloat(home.getIncome())));
                expenses.setText("($ " + decimalFormat.format(Float.parseFloat(home.getExpense())) + ")");
                deficit.setText("$ " + decimalFormat.format(Float.parseFloat(home.getIncome()) - Float.parseFloat(home.getExpense())));
                endingBalance.setText("$ " + decimalFormat.format(Float.parseFloat(home.getStartingBalance()) + (Float.parseFloat(home.getIncome()) - Float.parseFloat(home.getExpense()) - (Float.parseFloat(home.getSavings()) + Float.parseFloat(home.getDebts())))));

                reduceDebts.setText(home.getDebts());
                addToSavings.setText(home.getSavings());
                addToCash.setText(decimalFormat.format(Float.parseFloat(home.getIncome()) - Float.parseFloat(home.getExpense()) - (Float.parseFloat(home.getSavings()) + Float.parseFloat(home.getDebts()))));

                if (home.getDebts().equals("0")) {
                    reduceDebts.getText().clear();
                    reduceDebts.setHint("Enter amount");
                }

                if (home.getSavings().equals("0")) {
                    addToSavings.getText().clear();
                    addToSavings.setHint("Enter amount");
                }

                if (home.getSavings().equals("0") && home.getDebts().equals("0")) {

                    addToCash.getText().clear();
                    addToCash.setText("0");

                }
                /*endingBalance.setText("$ " + decimalFormat.format(Float.parseFloat(home.getStartingBalance())));*/

                if (forMonth) {

                    String[] temp = monthDate.clone();

                    for (int i = monthIndex + 1; i < monthDate.length; i++) {

                        temp[i] = temp[i] + " - ($ " + String.format("%.2f", Float.parseFloat(home.getEndingBalance())) + ")";

                    }

                    monthPicker.setMinValue(0);
                    monthPicker.setMaxValue(monthDate.length - 1);
                    monthPicker.setDisplayedValues(temp);

                } else {

                    String[] temp = weekDate.clone();

                    for (int i = weekIndex + 1; i < weekDate.length; i++) {

                        temp[i] = temp[i] + " - ($ " + String.format("%.2f", Float.parseFloat(home.getEndingBalance())) + ")";

                    }

                    weekPicker.setMinValue(0);
                    weekPicker.setMaxValue(weekDate.length - 1);
                    weekPicker.setDisplayedValues(temp);

                }

            }

            @Override
            public void onFail() {

                dialog.dismiss();
                if (getActivity() != null)
                    Toast.makeText(getActivity(), "Failed to load data", Toast.LENGTH_SHORT).show();

                startCashBalance.setText("$ 0");
                income.setText("$ 0");
                expenses.setText("($ 0" + ")");
                deficit.setText("$ 0");
                endingBalance.setText("$ 0");

            }

            @Override
            public void onError(Throwable th) {

                dialog.dismiss();

                startCashBalance.setText("$ 0");
                income.setText("$ 0");
                expenses.setText("($ 0" + ")");
                deficit.setText("$ 0");
                endingBalance.setText("$ 0");

                if (getActivity() != null) {
                    Log.e("SerVerErrAddBill", th.toString());
                    if (th instanceof SocketTimeoutException) {
                        Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.server_reach_error), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

}
