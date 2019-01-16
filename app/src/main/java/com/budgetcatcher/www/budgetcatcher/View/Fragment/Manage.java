package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.Adapter.AccountListAdapter;
import com.budgetcatcher.www.budgetcatcher.Adapter.CategoryListAdapter;
import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.AccountItem;
import com.budgetcatcher.www.budgetcatcher.Model.AddCategory;
import com.budgetcatcher.www.budgetcatcher.Model.Allowance;
import com.budgetcatcher.www.budgetcatcher.Model.Bill;
import com.budgetcatcher.www.budgetcatcher.Model.CatcherResponse;
import com.budgetcatcher.www.budgetcatcher.Model.Category;
import com.budgetcatcher.www.budgetcatcher.Model.Expenses;
import com.budgetcatcher.www.budgetcatcher.Model.Income;
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

public class Manage extends Fragment {

    private static final String TAG = "Manage";
    private static final int totalFetchCount = 4;

    @BindView(R.id.income)
    RecyclerView income;
    @BindView(R.id.bills)
    RecyclerView bills;
    @BindView(R.id.allowance)
    RecyclerView allowance;
    @BindView(R.id.incidental_recycler_view)
    RecyclerView incidental;
    @BindView(R.id.income_swipe_down)
    SwipeRefreshLayout incomeSwipeDown;
    @BindView(R.id.bill_swipe_down)
    SwipeRefreshLayout billSwipeDown;
    @BindView(R.id.allowance_swipe_down)
    SwipeRefreshLayout allowanceSwipeDown;
    @BindView(R.id.incidental_swipe_down)
    SwipeRefreshLayout incidentalSwipeDown;
    @BindView(R.id.swipe_down)
    SwipeRefreshLayout refreshAllList;
    @BindView(R.id.header_top)
    TextView headerTop;
    @BindView(R.id.header_bottom)
    TextView headerBottom;
    @BindView(R.id.month_picker)
    NumberPicker monthPicker;
    @BindView(R.id.week_picker)
    NumberPicker weekPicker;
    @BindView(R.id.weekly_monthly)
    Switch weeklyMonthlySwitch;
    @BindView(R.id.projected_balance_layout)
    CoordinatorLayout projectedBalanceLayoutBottomSheet;
    @BindView(R.id.add_categories)
    TextView addCategories;
    @BindView(R.id.category_recycler_view)
    RecyclerView categoryRecyclerView;
    @BindView(R.id.category_swipe_down)
    SwipeRefreshLayout categorySwipeDown;

    private ArrayList<Week> weekArrayList;
    private ArrayList<Month> monthArrayList;
    private Week currentWeek;
    private Month currentMonth;
    private boolean isMonthSelected = false;
    private int weekIndex, monthIndex;
    private String[] weekDate, monthDate;
    private SharedPreferences sharedPreferences;
    private int dataFetchCount = 0;
    private ProgressDialog dialog;
    private CategoryListAdapter categoryListAdapter;

    private AccountListAdapter incomeListAdapter, billsListAdapter, allowanceListAdapter, incidentalListAdapter;
    private String userID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manage, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            dialog = ProgressDialog.show(getActivity(), "",
                    getString(R.string.loading), true);
            dialog.dismiss();

            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Manage");
            ((MainActivity) getActivity()).navigationView.setCheckedItem(R.id.nav_manage);

            userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

            ((MainActivity) getActivity()).projectedBalanceBottomSheetBehavior = BottomSheetBehavior.from(projectedBalanceLayoutBottomSheet);
            ((MainActivity) getActivity()).projectedBalanceBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            fetchAllList();

        }

        refreshAllList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchAllList();
                refreshAllList.setRefreshing(false);

            }
        });

        weeklyMonthlySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                isMonthSelected = isChecked;
                homeUiDataUpdateBasedOnMonthOrWeek();

            }
        });

        return rootView;
    }

    private void fetchAllList() {

        if (BudgetCatcher.getConnectedToInternet()) {

            /*getIncomeFromServer();
            getBillFromServer();
            getAllowanceFromServer();
            getExpensesFromServer();*/

            getCurrentDateRange();
            fetchCategory();

        } else {

            Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

        }

    }

    private void showFeedIncomes(ArrayList<AccountItem> accountItemArrayList, ArrayList<Income> incomeArrayList) {

        income.setLayoutManager(new LinearLayoutManager(getContext()));
        incomeListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, Config.TAG_LIST_INCOME, null, null, null, incomeArrayList);
        income.setAdapter(incomeListAdapter);

    }

    private void showFeedBills(ArrayList<AccountItem> accountItemArrayList, ArrayList<Bill> billArrayList) {

        bills.setLayoutManager(new LinearLayoutManager(getContext()));
        billsListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, Config.TAG_LIST_BILL, billArrayList, null, null, null);
        bills.setAdapter(billsListAdapter);

    }

    private void showFeedSpendingAllowance(ArrayList<AccountItem> accountItemArrayList, ArrayList<Allowance> allowanceArrayList) {

        allowance.setLayoutManager(new LinearLayoutManager(getContext()));
        allowanceListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, Config.TAG_LIST_SPENDING_ALLOWANCE, null, allowanceArrayList, null, null);
        allowance.setAdapter(allowanceListAdapter);
    }

    /*private void showFeedIncidental(ArrayList<AccountItem> accountItemArrayList, ArrayList<Expenses> expensesArrayList) {

        incidental.setLayoutManager(new LinearLayoutManager(getContext()));
        incidentalListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, Config.TAG_LIST_INCIDENTAL, null, null, expensesArrayList, null);
        incidental.setAdapter(incidentalListAdapter);

    }*/

    private void getCurrentDateRange() {

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

                monthDate[i] = monthArrayList.get(i).getMonth();

            }
            try {

                startDate = format.parse(currentMonth.getFirstDayOfMonth());
                endDate = format.parse(currentMonth.getLastDayOfMonth());
                monthIndex = Integer.parseInt(currentMonth.getMonthNumber()) - 1;
                updateHeader(startDate, endDate, "Month of");
                updateManageData(currentMonth.getFirstDayOfMonth(), currentMonth.getLastDayOfMonth());

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

                updateManageData(currentWeek.getFirstDayOfEveryWeek(), currentWeek.getLastDayOfEveryWeek());

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

    @OnClick({R.id.add_bill, R.id.add_allowance, R.id.add_income_setting, R.id.left_arrow, R.id.right_arrow, R.id.date_display, R.id.done_bottom_sheet, R.id.add_incidentals, R.id.add_categories})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.add_categories: {

                if (BudgetCatcher.getConnectedToInternet()) {

                    addCategory();

                } else {

                    Toast.makeText(getActivity(), getString(R.string.connect_to_internet), Toast.LENGTH_SHORT).show();

                }

                break;
            }

            case (R.id.add_incidentals): {

                if (getActivity() != null)
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, new AddIncident(), Config.TAG_ADD_INCIDENTAL_FRAGMENT)
                            .addToBackStack(null)
                            .commit();

                break;
            }

            case R.id.date_display: {

                if (getActivity() != null)
                    ((MainActivity) getActivity()).projectedBalanceBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                break;
            }

            case R.id.done_bottom_sheet: {

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
                        updateManageData(monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getLastDayOfMonth());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else {

                    try {

                        weekIndex = weekPicker.getValue();
                        startDate = format.parse(weekArrayList.get(weekPicker.getValue()).getFirstDayOfEveryWeek());
                        endDate = format.parse(weekArrayList.get(weekPicker.getValue()).getLastDayOfEveryWeek());
                        updateHeader(startDate, endDate, "Week of");
                        updateManageData(weekArrayList.get(weekIndex).getFirstDayOfEveryWeek(), weekArrayList.get(weekIndex).getLastDayOfEveryWeek());

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
                            Log.d(TAG, "onClick: " + startDate + " - " + endDate);
                            updateHeader(startDate, endDate, "Month of");
                            updateManageData(monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getLastDayOfMonth());

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
                            updateManageData(weekArrayList.get(weekIndex).getFirstDayOfEveryWeek(), weekArrayList.get(weekIndex).getLastDayOfEveryWeek());

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
                            updateManageData(monthArrayList.get(monthIndex).getFirstDayOfMonth(), monthArrayList.get(monthIndex).getLastDayOfMonth());

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
                            updateManageData(weekArrayList.get(weekIndex).getFirstDayOfEveryWeek(), weekArrayList.get(weekIndex).getLastDayOfEveryWeek());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                }

                break;
            }

            case (R.id.add_bill): {

                if (getActivity() != null)
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, new AddBill(), Config.TAG_ADD_BILL_FRAGMENT)
                            .addToBackStack(null)
                            .commit();

                break;
            }
            case (R.id.add_allowance): {

                if (getActivity() != null)
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, new AddAllowance(), Config.TAG_ADD_ALLOWANCE_FRAGMENT)
                            .addToBackStack(null)
                            .commit();

                break;
            }

            case (R.id.add_income_setting): {

                if (getActivity() != null)
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, new AddIncome(), Config.TAG_ADD_INCOME_FRAGMENT)
                            .addToBackStack(null)
                            .commit();

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

                            categorySwipeDown.setRefreshing(true);

                            BudgetCatcher.apiManager.addCategory(category, new QueryCallback<String>() {
                                @Override
                                public void onSuccess(String data) {

                                    categorySwipeDown.setRefreshing(false);
                                    alert11.dismiss();
                                    Toast.makeText(getActivity(), getString(R.string.successfully_added), Toast.LENGTH_SHORT).show();
                                    if (categoryListAdapter.getItemCount() > 0) {

                                        categoryListAdapter.clearList();

                                    }

                                    fetchCategory();

                                }

                                @Override
                                public void onFail() {

                                    Toast.makeText(getActivity(), getString(R.string.failed_to_added), Toast.LENGTH_SHORT).show();
                                    categorySwipeDown.setRefreshing(false);

                                }

                                @Override
                                public void onError(Throwable th) {

                                    categorySwipeDown.setRefreshing(false);
                                    Log.e("SerVerErr", th.toString());
                                    if (th instanceof SocketTimeoutException) {
                                        Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
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

        categorySwipeDown.setRefreshing(true);

        BudgetCatcher.apiManager.getCategory(Config.CATEGORY_BILL_TAG_ID, userID, new QueryCallback<ArrayList<Category>>() {
            @Override
            public void onSuccess(ArrayList<Category> data) {

                categorySwipeDown.setRefreshing(false);
                ArrayList<Category> categories = new ArrayList<>();

                for (int i = 0; i < data.size(); i++) {

                    if (data.get(i).getUserId() != null) {

                        categories.add(data.get(i));

                    }

                }

                showCategory(categories);

            }

            @Override
            public void onFail() {

                categorySwipeDown.setRefreshing(false);
                Toast.makeText(getActivity(), "Failed to fetch Category", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable th) {

                categorySwipeDown.setRefreshing(false);
                if (getActivity() != null) {
                    Log.e("SerVerErrAddBill", th.toString());
                    if (th instanceof SocketTimeoutException) {
                        Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void updateManageData(String startDate, String endDate) {

        billSwipeDown.setRefreshing(true);
        incomeSwipeDown.setRefreshing(true);
        incidentalSwipeDown.setRefreshing(true);
        allowanceSwipeDown.setRefreshing(true);
        dialog.show();

        BudgetCatcher.apiManager.getCatcher(userID, startDate, endDate, new QueryCallback<CatcherResponse>() {
            @Override
            public void onSuccess(CatcherResponse data) {

                billSwipeDown.setRefreshing(false);
                incidentalSwipeDown.setRefreshing(false);
                allowanceSwipeDown.setRefreshing(false);
                incomeSwipeDown.setRefreshing(false);
                dialog.dismiss();

                if (incomeListAdapter != null)
                    incomeListAdapter.clear();
                if (billsListAdapter != null)
                    billsListAdapter.clear();
                if (allowanceListAdapter != null)
                    allowanceListAdapter.clear();
                if (incidentalListAdapter != null)
                    incidentalListAdapter.clear();

                ArrayList<AccountItem> incomeArrayList = new ArrayList<>();
                ArrayList<AccountItem> billsArrayList = new ArrayList<>();
                ArrayList<AccountItem> spendingAllowanceArrayList = new ArrayList<>();
                ArrayList<AccountItem> expensesArrayList = new ArrayList<>();

                for (int i = 0; i < data.getIncomesData().size(); i++) {

                    Income income = data.getIncomesData().get(i);

                    String year, month, day;

                    year = income.getNextPayDay().substring(0, 4);
                    month = income.getNextPayDay().substring(5, 7);
                    day = income.getNextPayDay().substring(8, 10);

                    incomeArrayList.add(new AccountItem(income.getFrequency(), month + "-" + day + "-" + year, "$" + income.getAmount(), income.getIncomeId()));

                }

                for (int i = 0; i < data.getBillsData().size(); i++) {
                    Bill bill = data.getBillsData().get(i);

                    String year, month, day;

                    year = bill.getDueDate().substring(0, 4);
                    month = bill.getDueDate().substring(5, 7);
                    day = bill.getDueDate().substring(8, 10);

                    billsArrayList.add(new AccountItem(bill.getBillName(), month + "-" + day + "-" + year, "$" + bill.getAmount(), bill.getBillId()));
                }

                for (int i = 0; i < data.getAllowancesData().size(); i++) {

                    Allowance allowance = data.getAllowancesData().get(i);
                    spendingAllowanceArrayList.add(new AccountItem(allowance.getCategoryName(), "$" + allowance.getAllowanceAmount(), allowance.getAllowanceId()));

                }

                for (int i = 0; i < data.getIncidentalsData().size(); i++) {

                    Expenses expenses = data.getIncidentalsData().get(i);

                    /*String dateTime = expenses.getDateTime();
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
                    Date date = null;
                    try {
                        date = formatter.parse(dateTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-DD");
                    String finalString = newFormat.format(date);*/

                    expensesArrayList.add(new AccountItem(expenses.getExpenseName(), expenses.getDateTime(), "$" + expenses.getAmount(), expenses.getExpenseId()));

                }

                if (getActivity() != null) {

                    showFeedIncomes(incomeArrayList, data.getIncomesData());
                    showFeedIncidental(expensesArrayList, data.getIncidentalsData());
                    showFeedSpendingAllowance(spendingAllowanceArrayList, data.getAllowancesData());
                    showFeedBills(billsArrayList, data.getBillsData());

                }

            }

            @Override
            public void onFail() {

                billSwipeDown.setRefreshing(false);
                incidentalSwipeDown.setRefreshing(false);
                allowanceSwipeDown.setRefreshing(false);
                incomeSwipeDown.setRefreshing(false);
                dialog.dismiss();

            }

            @Override
            public void onError(Throwable th) {

                billSwipeDown.setRefreshing(false);
                incidentalSwipeDown.setRefreshing(false);
                allowanceSwipeDown.setRefreshing(false);
                incomeSwipeDown.setRefreshing(false);
                dialog.dismiss();
                if (getActivity() != null) {
                    Log.e("SerVerErrCatcher", th.toString());
                    if (th instanceof SocketTimeoutException) {
                        Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void showFeedIncidental(ArrayList<AccountItem> accountItemArrayList, ArrayList<Expenses> expensesArrayList) {

        incidental.setLayoutManager(new LinearLayoutManager(getContext()));
        incidentalListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, Config.TAG_LIST_INCIDENTAL, null, null, expensesArrayList, null);
        incidental.setAdapter(incidentalListAdapter);

    }

}
