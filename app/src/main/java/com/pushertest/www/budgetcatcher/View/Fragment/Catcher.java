package com.pushertest.www.budgetcatcher.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pushertest.www.budgetcatcher.Adapter.AccountListAdapter;
import com.pushertest.www.budgetcatcher.BudgetCatcher;
import com.pushertest.www.budgetcatcher.Config;
import com.pushertest.www.budgetcatcher.Model.AccountItem;
import com.pushertest.www.budgetcatcher.Model.Bill;
import com.pushertest.www.budgetcatcher.Network.QueryCallback;
import com.pushertest.www.budgetcatcher.R;
import com.pushertest.www.budgetcatcher.View.Activity.MainActivity;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Catcher extends Fragment {

    private static final String TAG = "Catcher";

    @BindView(R.id.bill_recycler_view)
    RecyclerView bills;
    @BindView(R.id.sending_allowance_recycler_view)
    RecyclerView spendingAllowance;
    @BindView(R.id.incidental_recycler_view)
    RecyclerView incidental;

    private AccountListAdapter billListAdapter, spendingAllowanceListAdapter, incidentalListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cather, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Catcher");

            getBillFromServer();

        }

        ArrayList<AccountItem> spendingAllowanceArrayList = new ArrayList<>();
        spendingAllowanceArrayList.add(new AccountItem("Gas", "$40.88"));
        spendingAllowanceArrayList.add(new AccountItem("Groceries", "$30.88"));
        spendingAllowanceArrayList.add(new AccountItem("Entertainment", "$30.88"));

        ArrayList<AccountItem> incidentalArrayList = new ArrayList<>();
        incidentalArrayList.add(new AccountItem("Car repairs", "10/08/18", "$40.88"));
        incidentalArrayList.add(new AccountItem("Home repairs", "10/08/18", "$40.88"));
        incidentalArrayList.add(new AccountItem("Room visit", "10/08/18", "$40.88"));

        showFeedSpendingAllowance(spendingAllowanceArrayList);
        showFeedIncidental(incidentalArrayList);

        return rootView;
    }

    private void showFeedBills(ArrayList<AccountItem> accountItemArrayList) {

        bills.setLayoutManager(new LinearLayoutManager(getContext()));
        billListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, "null");
        bills.setAdapter(billListAdapter);

    }

    private void showFeedSpendingAllowance(ArrayList<AccountItem> accountItemArrayList) {

        spendingAllowance.setLayoutManager(new LinearLayoutManager(getContext()));
        spendingAllowanceListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, Config.TAG_LIST_SPENDING_ALLOWANCE);
        spendingAllowance.setAdapter(spendingAllowanceListAdapter);

    }

    private void showFeedIncidental(ArrayList<AccountItem> accountItemArrayList) {

        incidental.setLayoutManager(new LinearLayoutManager(getContext()));
        incidentalListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, "null");
        incidental.setAdapter(incidentalListAdapter);

    }

    private void getBillFromServer() {

        BudgetCatcher.apiManager.getBill("193", new QueryCallback<ArrayList<Bill>>() {
            @Override
            public void onSuccess(ArrayList<Bill> billList) {

                Log.d(TAG, "onSuccess: " + billList.size());

                ArrayList<AccountItem> billsArrayList = new ArrayList<>();

                for (int i = 0; i < billList.size(); i++) {

                    Bill bill = billList.get(i);
                    billsArrayList.add(new AccountItem(bill.getCategoryId(), bill.getDueDate(), bill.getAmount()));

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

}
