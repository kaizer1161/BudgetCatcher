package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
                float savingsAmount = 0;
                float reduceDebtsAmount = 0;
                float deficitAmount = Float.parseFloat(deficit.getText().toString().replace("$ ", ""));

                if (s.toString().equals(".")) {

                    addToSavings.setText("0.");
                    addToSavings.setSelection(addToSavings.getText().toString().length());

                }

                if (s.toString().length() < 11) {

                    if (s.toString().isEmpty()) {
                        savingsAmount = 0;
                    } else {
                        savingsAmount = Float.parseFloat(addToSavings.getText().toString());
                    }
                    if (reduceDebts.getText().toString().isEmpty()) {
                        reduceDebtsAmount = 0;
                    } else {
                        reduceDebtsAmount = Float.parseFloat(reduceDebts.getText().toString());
                    }
                    Float sum = deficitAmount - savingsAmount - reduceDebtsAmount;
                    addToCash.setText(String.valueOf(sum));
                    float temp = sum + Float.parseFloat(startCashBalance.getText().toString().replace("$ ", ""));
                    endingBalance.setText(Float.toString(temp));

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
                String valStr = reduceDebts.getText().toString();
                float deficitAmount = Float.parseFloat(deficit.getText().toString().replace("$ ", ""));

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
                    Float sum = deficitAmount - savingsAmount - reduceDebtsAmount;
                    addToCash.setText(String.valueOf(sum));
                    float temp = sum + Float.parseFloat(startCashBalance.getText().toString().replace("$ ", ""));
                    endingBalance.setText(Float.toString(temp));

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

    @OnClick({R.id.projected_balance, R.id.done_bottom_sheet, R.id.add_to_saving, R.id.reduce_debts, R.id.left_arrow, R.id.right_arrow, R.id.date_display, R.id.save_to_adjust, R.id.outstanding_balance})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.date_display: {

                if (getActivity() != null)
                    ((MainActivity) getActivity()).projectedBalanceBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                isProjectedBalanceSelected = false;

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

                    /*final Activity activity = getActivity();

                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                    builder1.setCancelable(true);

                    LayoutInflater inflater = this.getLayoutInflater();
                    View alertView = inflater.inflate(R.layout.add_outstanding_bills, null);
                    builder1.setView(alertView);

                    TextView weekSum = alertView.findViewById(R.id.week_sum);
                    TextView weekNumber = alertView.findViewById(R.id.week_number);

                    if (isMonthSelected) {

                        weekNumber.setText(monthDate[monthPicker.getValue()]);

                    } else {

                        weekNumber.setText(weekDate[weekPicker.getValue()]);
                    }

                    final AlertDialog alert11 = builder1.create();

                    alert11.show();*/

                } else {

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate = null, endDate = null;

                    if (getActivity() != null) {

                        ((MainActivity) getActivity()).projectedBalanceBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                    }

                    if (isMonthSelected) {

                        try {

                            monthIndex = monthPicker.getValue();
                            startDate = format.parse(monthArrayList.get(monthPicker.getValue()).getFirstDayOfMonth());
                            endDate = format.parse(monthArrayList.get(monthPicker.getValue()).getLastDayOfMonth());
                            updateHeader(startDate, endDate, "Month of");
                            updateHomeData(monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getLastDayOfMonth());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    } else {

                        try {

                            weekIndex = weekPicker.getValue();
                            startDate = format.parse(weekArrayList.get(weekPicker.getValue()).getFirstDayOfEveryWeek());
                            endDate = format.parse(weekArrayList.get(weekPicker.getValue()).getLastDayOfEveryWeek());
                            updateHeader(startDate, endDate, "Week of");
                            updateHomeData(weekArrayList.get(weekIndex).getFirstDayOfEveryWeek(), weekArrayList.get(weekIndex).getLastDayOfEveryWeek());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
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


                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = null, endDate = null;

                ModifyHomeBody modifyHomeBody = new ModifyHomeBody(endingBalance.getText().toString().replace("$ ", ""), Float.toString(savingsAmount), Float.toString(reduceDebtsAmount));

                if (isMonthSelected) {

                    try {

                        startDate = format.parse(monthArrayList.get(monthPicker.getValue()).getFirstDayOfMonth());
                        endDate = format.parse(monthArrayList.get(monthPicker.getValue()).getLastDayOfMonth());
                        updateEndingBalance(userID, monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getLastDayOfMonth(), modifyHomeBody);

                        /*updateHeader(startDate, endDate, "Month of");
                        updateHomeData(monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getLastDayOfMonth());*/

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else {

                    try {

                        startDate = format.parse(weekArrayList.get(weekPicker.getValue()).getFirstDayOfEveryWeek());
                        endDate = format.parse(weekArrayList.get(weekPicker.getValue()).getLastDayOfEveryWeek());
                        updateEndingBalance(userID, weekArrayList.get(weekIndex).getFirstDayOfEveryWeek(), weekArrayList.get(weekIndex).getLastDayOfEveryWeek(), modifyHomeBody);
                        /*updateHeader(startDate, endDate, "Week of");
                        updateHomeData(weekArrayList.get(weekIndex).getFirstDayOfEveryWeek(), weekArrayList.get(weekIndex).getLastDayOfEveryWeek());*/

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                break;

            }

            case R.id.left_arrow: {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = null, endDate = null;

                if (isMonthSelected) {

                    if (monthIndex > 0) {

                        monthIndex--;

                        try {

                            startDate = format.parse(monthArrayList.get(monthIndex).getFirstDayOfMonth());
                            endDate = format.parse(monthArrayList.get(monthIndex).getLastDayOfMonth());
                            updateHeader(startDate, endDate, "Month of");
                            updateHomeData(monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getLastDayOfMonth());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                } else {

                    if (weekIndex > 0) {

                        weekIndex--;

                        try {

                            startDate = format.parse(weekArrayList.get(weekIndex).getFirstDayOfEveryWeek());
                            endDate = format.parse(weekArrayList.get(weekIndex).getLastDayOfEveryWeek());
                            updateHeader(startDate, endDate, "Week of");
                            updateHomeData(weekArrayList.get(weekIndex).getFirstDayOfEveryWeek(), weekArrayList.get(weekIndex).getLastDayOfEveryWeek());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                }

                break;
            }

            case R.id.right_arrow: {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = null, endDate = null;

                if (isMonthSelected) {

                    if (monthIndex < monthArrayList.size() - 1) {

                        monthIndex++;

                        try {

                            startDate = format.parse(monthArrayList.get(monthIndex).getFirstDayOfMonth());
                            endDate = format.parse(monthArrayList.get(monthIndex).getLastDayOfMonth());
                            updateHeader(startDate, endDate, "Month of");
                            updateHomeData(monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getLastDayOfMonth());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                } else {

                    if (weekIndex < weekArrayList.size() - 1) {

                        weekIndex++;

                        try {

                            startDate = format.parse(weekArrayList.get(weekIndex).getFirstDayOfEveryWeek());
                            endDate = format.parse(weekArrayList.get(weekIndex).getLastDayOfEveryWeek());
                            updateHeader(startDate, endDate, "Week of");
                            updateHomeData(weekArrayList.get(weekIndex).getFirstDayOfEveryWeek(), weekArrayList.get(weekIndex).getLastDayOfEveryWeek());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

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
                    final AlertDialog alert11 = builder1.create();

                    TextView close = alertView.findViewById(R.id.close);
                    TextView addOS = alertView.findViewById(R.id.add_os);

                    TextView sCBalance = alertView.findViewById(R.id.start_cash_balance);
                    TextView totalOS = alertView.findViewById(R.id.total_os);
                    TextView bankBalance = alertView.findViewById(R.id.bank_balance);
                    RecyclerView recyclerView = alertView.findViewById(R.id.oc_list);

                    sCBalance.setText(startCashBalance.getText().toString());
                    totalOS.setText("$" + data.getTotal());
                    bankBalance.setText(String.format("$%s", Float.parseFloat(startCashBalance.getText().toString().replace("$ ", "")) - Float.parseFloat(data.getTotal())));

                    ArrayList<AccountItem> accountItems = new ArrayList<>();

                    for (int i = 0; i < data.getOutstandingChecks().size(); i++) {

                        OutstandingChecks outstandingChecks = data.getOutstandingChecks().get(i);
                        accountItems.add(new AccountItem(outstandingChecks.getCheckNo(), outstandingChecks.getOutBalance(), outstandingChecks.getOcId()));

                    }

                    showFeedOutstandingChecks(accountItems, data.getOutstandingChecks(), recyclerView);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            alert11.dismiss();

                        }
                    });

                    addOS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addOutstandingCheckLayout();


                        }
                    });

                    alert11.show();

                }

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

    private void showFeedOutstandingChecks(ArrayList<AccountItem> accountItemArrayList, ArrayList<OutstandingChecks> outstandingChecksArrayList, RecyclerView recyclerView) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AccountListAdapter accountListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, Config.TAG_LIST_OUTSTANDING_CHECKS, null, null, null, null, outstandingChecksArrayList);
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

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null, endDate = null;

        if (isMonthSelected) {

            monthPicker.setVisibility(View.VISIBLE);
            weekPicker.setVisibility(View.GONE);

            monthDate = new String[monthArrayList.size()];
            for (int i = 0; i < monthArrayList.size(); i++) {

                monthDate[i] = monthArrayList.get(i).getMonth();

            }
            try {

                startDate = format.parse(currentMonth.getFirstDayOfMonth());
                endDate = format.parse(currentMonth.getLastDayOfMonth());
                monthIndex = Integer.parseInt(currentMonth.getMonthNumber()) - 1;
                updateHeader(startDate, endDate, "Month of");
                //updateHomeData(currentMonth.getFirstDayOfMonth(), currentMonth.getLastDayOfMonth());
                updateHomeDataAndEndBalance(currentMonth.getFirstDayOfMonth(), currentMonth.getLastDayOfMonth(), isMonthSelected);

            } catch (ParseException e) {
                e.printStackTrace();
            }

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

            try {

                startDate = format.parse(currentWeek.getFirstDayOfEveryWeek());
                endDate = format.parse(currentWeek.getLastDayOfEveryWeek());
                weekIndex = Integer.parseInt(currentWeek.getWeekNumber()) - 1;
                updateHeader(startDate, endDate, "Week of");
                //updateHomeData(currentWeek.getFirstDayOfEveryWeek(), currentWeek.getLastDayOfEveryWeek());
                updateHomeDataAndEndBalance(currentWeek.getFirstDayOfEveryWeek(), currentWeek.getLastDayOfEveryWeek(), isMonthSelected);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            weekPicker.setMinValue(0);
            weekPicker.setMaxValue(weekDate.length - 1);
            weekPicker.setDisplayedValues(weekDate);

        }

    }

    private void updateHeader(Date startDate, Date endDate, String topHeader) {

        headerTop.setText(topHeader);

        if (startDate != null && endDate != null) {

            Calendar startCalender = Calendar.getInstance(), endCalender = Calendar.getInstance();
            startCalender.setTime(startDate);
            endCalender.setTime(endDate);

            DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
            String monthInWord = dateFormatSymbols.getMonths()[startCalender.get(Calendar.MONTH)];

            int startDay = startCalender.get(Calendar.DAY_OF_MONTH);
            int endDay = endCalender.get(Calendar.DAY_OF_MONTH);

            headerBottom.setText(String.format("%s %d - %d", monthInWord, startDay, endDay));

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
                endingBalance.setText("$ " + decimalFormat.format(Float.parseFloat(home.getEndingBalance())));

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
                endingBalance.setText("$ " + decimalFormat.format(Float.parseFloat(home.getEndingBalance())));
                /*endingBalance.setText("$ " + decimalFormat.format(Float.parseFloat(home.getStartingBalance())));*/

                if (forMonth) {

                    String[] temp = monthDate.clone();

                    for (int i = monthIndex + 1; i < monthDate.length; i++) {

                        temp[i] = temp[i] + " - ($ " + home.getEndingBalance() + ")";

                    }

                    monthPicker.setMinValue(0);
                    monthPicker.setMaxValue(monthDate.length - 1);
                    monthPicker.setDisplayedValues(temp);

                } else {

                    String[] temp = weekDate.clone();

                    for (int i = weekIndex + 1; i < weekDate.length; i++) {

                        temp[i] = temp[i] + " - ($ " + home.getEndingBalance() + ")";

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
