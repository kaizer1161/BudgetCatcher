package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.Month;
import com.budgetcatcher.www.budgetcatcher.Model.MonthData;
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
    EditText addToBill;
    @BindView(R.id.reduce_debts)
    EditText reduceDebts;
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
    private boolean isMonthSelected = false;
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

                fetchCurrentWeek();
                fetchCurrentMonth();
                fetchWeekBreakdown();
                fetchMonthBreakdown();

            }

        } else {

            Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

        }

        editTextCursorVisibility(false);

        weeklyMonthlySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                isMonthSelected = isChecked;
                homeUiDataUpdateBasedOnMonthOrWeek();

            }
        });

        return rootView;
    }

    private void editTextCursorVisibility(boolean visibility) {

        addToBill.setCursorVisible(visibility);
        reduceDebts.setCursorVisible(visibility);

        addToBill.setImeOptions(EditorInfo.IME_ACTION_DONE);
        reduceDebts.setImeOptions(EditorInfo.IME_ACTION_DONE);

    }

    @OnClick({R.id.projected_balance, R.id.done_bottom_sheet, R.id.add_to_saving, R.id.reduce_debts, R.id.left_arrow, R.id.right_arrow})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.projected_balance: {

                if (getActivity() != null)
                    ((MainActivity) getActivity()).projectedBalanceBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                break;
            }

            case R.id.done_bottom_sheet: {

                final Activity activity = getActivity();

                final AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                builder1.setCancelable(true);

                LayoutInflater inflater = this.getLayoutInflater();
                View alertView = inflater.inflate(R.layout.projected_balance_alert_box, null);
                builder1.setView(alertView);

                TextView weekSum = alertView.findViewById(R.id.week_sum);
                TextView weekNumber = alertView.findViewById(R.id.week_number);

                if (isMonthSelected) {

                    weekNumber.setText(monthDate[weekPicker.getValue()]);

                } else {

                    weekNumber.setText(weekDate[weekPicker.getValue()]);
                }

                final AlertDialog alert11 = builder1.create();

                alert11.show();

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
                            updateHomeData(monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getFirstDayOfMonth());

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
                            updateHomeData(monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getFirstDayOfMonth());

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
                            updateHomeData(monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getFirstDayOfMonth());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                } else {

                    Log.d(TAG, "onClick: weekindex" + weekIndex);
                    Log.d(TAG, "onClick: week size" + weekArrayList.size());

                    if (weekIndex < weekArrayList.size() - 1) {

                        weekIndex++;

                        try {

                            startDate = format.parse(weekArrayList.get(weekIndex).getFirstDayOfEveryWeek());
                            endDate = format.parse(weekArrayList.get(weekIndex).getLastDayOfEveryWeek());
                            updateHeader(startDate, endDate, "Week of");
                            updateHomeData(monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getFirstDayOfMonth());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                }

                break;
            }

        }

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

                monthDate[i] = monthArrayList.get(i).getFirstDayOfMonth() + " - " + monthArrayList.get(i).getLastDayOfMonth();

            }
            try {

                startDate = format.parse(currentMonth.getFirstDayOfMonth());
                endDate = format.parse(currentMonth.getLastDayOfMonth());
                monthIndex = Integer.parseInt(currentMonth.getMonthNumber()) - 1;
                updateHeader(startDate, endDate, "Month of");
                updateHomeData(currentMonth.getFirstDayOfMonth(), currentMonth.getLastDayOfMonth());

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

                weekDate[i] = weekArrayList.get(i).getFirstDayOfEveryWeek() + " - " + weekArrayList.get(i).getLastDayOfEveryWeek();

            }

            try {

                startDate = format.parse(currentWeek.getFirstDayOfEveryWeek());
                endDate = format.parse(currentWeek.getLastDayOfEveryWeek());
                weekIndex = Integer.parseInt(currentWeek.getWeekNumber()) - 1;
                updateHeader(startDate, endDate, "Week of");

                updateHomeData(currentWeek.getFirstDayOfEveryWeek(), currentWeek.getLastDayOfEveryWeek());

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

            }

            @Override
            public void onFail() {

                dialog.dismiss();
                if (getActivity() != null)
                    Toast.makeText(getActivity(), "Failed to load data", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable th) {

                dialog.dismiss();
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
