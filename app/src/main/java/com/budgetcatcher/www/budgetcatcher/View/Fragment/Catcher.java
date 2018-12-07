package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private AccountListAdapter billListAdapter, spendingAllowanceListAdapter, incidentalListAdapter;
    private String userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cather, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Catcher");

            userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

            if (BudgetCatcher.getConnectedToInternet()) {
                getBillFromServer();
                getAllowanceFromServer();
                getExpensesFromServer();
            } else {
                Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
            }
        }
        return rootView;
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

        BudgetCatcher.apiManager.getBill(userID, new QueryCallback<ArrayList<Bill>>() {
            @Override
            public void onSuccess(ArrayList<Bill> billList) {

                ArrayList<AccountItem> billsArrayList = new ArrayList<>();
                for (int i = 0; i < billList.size(); i++) {
                    Bill bill = billList.get(i);
                    billsArrayList.add(new AccountItem(bill.getBillName(), bill.getDueDate(), "$" + bill.getAmount(), bill.getBillId()));
                }
                showFeedBills(billsArrayList);
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onError(Throwable th) {

            }
        });
    }

    private void getAllowanceFromServer() {

        BudgetCatcher.apiManager.getAllowance(userID, new QueryCallback<ArrayList<Allowance>>() {
            @Override
            public void onSuccess(ArrayList<Allowance> allowancesList) {

                ArrayList<AccountItem> spendingAllowanceArrayList = new ArrayList<>();

                for (int i = 0; i < allowancesList.size(); i++) {

                    Allowance allowance = allowancesList.get(i);
                    spendingAllowanceArrayList.add(new AccountItem(allowance.getAllowanceName(), "$" + allowance.getAllowanceAmount(), allowance.getAllowanceId()));

                }
                showFeedSpendingAllowance(spendingAllowanceArrayList);
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onError(Throwable th) {

            }
        });

    }

    private void getExpensesFromServer() {

        BudgetCatcher.apiManager.getExpenses(userID, "december", "2018", new QueryCallback<ArrayList<Expenses>>() {
            @Override
            public void onSuccess(ArrayList<Expenses> expensesList) {

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

            }

            @Override
            public void onError(Throwable th) {

            }
        });

    }

}
