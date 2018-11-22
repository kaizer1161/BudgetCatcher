package com.pushertest.www.budgetcatcher.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pushertest.www.budgetcatcher.Adapter.AccountListAdapter;
import com.pushertest.www.budgetcatcher.Config;
import com.pushertest.www.budgetcatcher.Model.AccountItem;
import com.pushertest.www.budgetcatcher.R;
import com.pushertest.www.budgetcatcher.View.Activity.MainActivity;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Catcher extends Fragment {

    @BindView(R.id.bill_recycler_view)
    RecyclerView bills;
    @BindView(R.id.sending_allowance_recycler_view)
    RecyclerView sendingAllowance;
    @BindView(R.id.incidental_recycler_view)
    RecyclerView incidental;

    private AccountListAdapter accountListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cather, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null)
            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Catcher");

        ArrayList<AccountItem> billsArrayList = new ArrayList<>();
        billsArrayList.add(new AccountItem("Electricity", "Due in 1 day", "$40.88"));
        billsArrayList.add(new AccountItem("Car payment", "Due in Oct 15", "$80.88"));
        billsArrayList.add(new AccountItem("Rent", "Due in Oct 15", "$80.88"));

        ArrayList<AccountItem> spendingAllowanceArrayList = new ArrayList<>();
        spendingAllowanceArrayList.add(new AccountItem("Gas", "$40.88"));
        spendingAllowanceArrayList.add(new AccountItem("Groceries", "$30.88"));
        spendingAllowanceArrayList.add(new AccountItem("Entertainment", "$30.88"));

        ArrayList<AccountItem> incidentalArrayList = new ArrayList<>();
        incidentalArrayList.add(new AccountItem("Car repairs", "10/08/18", "$40.88"));
        incidentalArrayList.add(new AccountItem("Home repairs", "10/08/18", "$40.88"));
        incidentalArrayList.add(new AccountItem("Room visit", "10/08/18", "$40.88"));

        showFeedBills(billsArrayList);
        showFeedSpendingAllowance(spendingAllowanceArrayList);
        showFeedIncidental(incidentalArrayList);

        return rootView;
    }

    private void showFeedBills(ArrayList<AccountItem> accountItemArrayList) {

        bills.setLayoutManager(new LinearLayoutManager(getContext()));
        accountListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, "null");
        bills.setAdapter(accountListAdapter);

    }

    private void showFeedSpendingAllowance(ArrayList<AccountItem> accountItemArrayList) {

        sendingAllowance.setLayoutManager(new LinearLayoutManager(getContext()));
        accountListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, Config.TAG_LIST_SPENDING_ALLOWANCE);
        sendingAllowance.setAdapter(accountListAdapter);

    }

    private void showFeedIncidental(ArrayList<AccountItem> accountItemArrayList) {

        incidental.setLayoutManager(new LinearLayoutManager(getContext()));
        accountListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, "null");
        incidental.setAdapter(accountListAdapter);

    }

}
