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
import com.pushertest.www.budgetcatcher.Model.AccountItem;
import com.pushertest.www.budgetcatcher.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Manage extends Fragment {

    @BindView(R.id.bills)
    RecyclerView bills;
    @BindView(R.id.allowance)
    RecyclerView allowance;

    private AccountListAdapter accountListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manage, container, false);
        ButterKnife.bind(this, rootView);

        ArrayList<AccountItem> billsArrayList = new ArrayList<>();
        billsArrayList.add(new AccountItem("due is 1 day", "40.88", "social security tax"));
        billsArrayList.add(new AccountItem("due is Jan 15", "80.88", "Accountant"));
        billsArrayList.add(new AccountItem("due is Jan 20", "50.88", "Gym"));

        showFeedBills(billsArrayList);
        showFeedSpendingAllowance(billsArrayList);

        return rootView;
    }

    private void showFeedBills(ArrayList<AccountItem> accountItemArrayList) {

        bills.setLayoutManager(new LinearLayoutManager(getContext()));
        accountListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, "null");
        bills.setAdapter(accountListAdapter);

    }

    private void showFeedSpendingAllowance(ArrayList<AccountItem> accountItemArrayList) {

        allowance.setLayoutManager(new LinearLayoutManager(getContext()));
        accountListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, "null");
        allowance.setAdapter(accountListAdapter);
    }

}
