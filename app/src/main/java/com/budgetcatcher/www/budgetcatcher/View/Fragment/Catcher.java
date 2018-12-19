package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.Adapter.AccountListAdapter;
import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.AccountItem;
import com.budgetcatcher.www.budgetcatcher.Model.Allowance;
import com.budgetcatcher.www.budgetcatcher.Model.Bill;
import com.budgetcatcher.www.budgetcatcher.Model.Expenses;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.R;
import com.budgetcatcher.www.budgetcatcher.View.Activity.MainActivity;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class Catcher extends Fragment {

    private static final String TAG = "Catcher";

    @BindView(R.id.bill_recycler_view)
    RecyclerView bills;
    @BindView(R.id.sending_allowance_recycler_view)
    RecyclerView spendingAllowance;
    @BindView(R.id.incidental_recycler_view)
    RecyclerView incidental;
    @BindView(R.id.bill_swipe_down)
    SwipeRefreshLayout billSwipeDown;
    @BindView(R.id.allowance_swipe_down)
    SwipeRefreshLayout allowanceSwipeDown;
    @BindView(R.id.incidental_swipe_down)
    SwipeRefreshLayout incidentalSwipeDown;
    @BindView(R.id.swipe_down)
    SwipeRefreshLayout refreshAllList;

    private AccountListAdapter billListAdapter, spendingAllowanceListAdapter, incidentalListAdapter;
    private String userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cather, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Catcher");
            ((MainActivity) getActivity()).navigationView.setCheckedItem(R.id.nav_catcher);

            userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

            fetchAllList();

        }

        refreshAllList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchAllList();
                refreshAllList.setRefreshing(false);

            }
        });

        return rootView;
    }

    private void fetchAllList() {

        if (BudgetCatcher.getConnectedToInternet()) {

            getBillFromServer();
            getAllowanceFromServer();
            getExpensesFromServer();

        } else {

            Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

        }

    }

    private void showFeedBills(ArrayList<AccountItem> accountItemArrayList) {
        bills.setLayoutManager(new LinearLayoutManager(getContext()));
        billListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, Config.TAG_LIST_BILL);
        bills.setAdapter(billListAdapter);
    }

    private void showFeedSpendingAllowance(ArrayList<AccountItem> accountItemArrayList) {
        spendingAllowance.setLayoutManager(new LinearLayoutManager(getContext()));
        spendingAllowanceListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, Config.TAG_LIST_SPENDING_ALLOWANCE);
        spendingAllowance.setAdapter(spendingAllowanceListAdapter);
    }

    private void showFeedIncidental(ArrayList<AccountItem> accountItemArrayList) {
        incidental.setLayoutManager(new LinearLayoutManager(getContext()));
        incidentalListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, Config.TAG_LIST_INCIDENTAL);
        incidental.setAdapter(incidentalListAdapter);
    }

    private void getBillFromServer() {

        billSwipeDown.setRefreshing(true);
        BudgetCatcher.apiManager.getBill(userID, new QueryCallback<ArrayList<Bill>>() {
            @Override
            public void onSuccess(ArrayList<Bill> billList) {

                billSwipeDown.setRefreshing(false);
                ArrayList<AccountItem> billsArrayList = new ArrayList<>();
                for (int i = 0; i < billList.size(); i++) {
                    Bill bill = billList.get(i);
                    billsArrayList.add(new AccountItem(bill.getBillName(), bill.getDueDate(), "$" + bill.getAmount(), bill.getBillId()));
                }
                showFeedBills(billsArrayList);
            }

            @Override
            public void onFail() {

                billSwipeDown.setRefreshing(false);

            }

            @Override
            public void onError(Throwable th) {

                billSwipeDown.setRefreshing(false);
                if (getActivity() != null) {
                    Log.e("SerVerErrCatcher", th.toString());
                    if (th instanceof SocketTimeoutException) {
                        Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), th.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void getAllowanceFromServer() {

        allowanceSwipeDown.setRefreshing(true);
        BudgetCatcher.apiManager.getAllowance(userID, new QueryCallback<ArrayList<Allowance>>() {
            @Override
            public void onSuccess(ArrayList<Allowance> allowancesList) {

                allowanceSwipeDown.setRefreshing(false);
                ArrayList<AccountItem> spendingAllowanceArrayList = new ArrayList<>();

                for (int i = 0; i < allowancesList.size(); i++) {

                    Allowance allowance = allowancesList.get(i);
                    spendingAllowanceArrayList.add(new AccountItem(allowance.getCategoryName(), "$" + allowance.getAllowanceAmount(), allowance.getAllowanceId()));

                }
                showFeedSpendingAllowance(spendingAllowanceArrayList);
            }

            @Override
            public void onFail() {

                allowanceSwipeDown.setRefreshing(false);

            }

            @Override
            public void onError(Throwable th) {

                allowanceSwipeDown.setRefreshing(false);

                /*if (th instanceof SocketTimeoutException){

                    if (getActivity() != null){

                        Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();

                    }

                }*/

            }
        });

    }

    private void getExpensesFromServer() {

        incidentalSwipeDown.setRefreshing(true);
        BudgetCatcher.apiManager.getExpenses(userID, "january", "2019", new QueryCallback<ArrayList<Expenses>>() {
            @Override
            public void onSuccess(ArrayList<Expenses> expensesList) {

                incidentalSwipeDown.setRefreshing(false);
                ArrayList<AccountItem> expensesArrayList = new ArrayList<>();

                for (int i = 0; i < expensesList.size(); i++) {

                    Expenses expenses = expensesList.get(i);
                    String dateTime = expenses.getDateTime();
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
                    Date date = null;
                    try {
                        date = formatter.parse(dateTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-DD");
                    String finalString = newFormat.format(date);


                    expensesArrayList.add(new AccountItem(expenses.getExpenseName(), finalString, "$" + expenses.getAmount(), expenses.getExpenseId()));

                }

                showFeedIncidental(expensesArrayList);

            }

            @Override
            public void onFail() {

                incidentalSwipeDown.setRefreshing(false);

            }

            @Override
            public void onError(Throwable th) {

                incidentalSwipeDown.setRefreshing(false);
                /*if (th instanceof SocketTimeoutException){

                    if (getActivity() != null){

                        Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();

                    }

                }*/

            }
        });

    }

}
