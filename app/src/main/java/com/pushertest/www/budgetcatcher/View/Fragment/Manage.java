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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        billsArrayList.add(new AccountItem("Electricity", "due is 1 day", "$40.88"));
        billsArrayList.add(new AccountItem("Car payment", "due is Jan 15", "$80.88"));
        billsArrayList.add(new AccountItem("Rent", "due is Jan 20", "$50.88"));
        billsArrayList.add(new AccountItem("Credit card", "due is Jan 20", "$50.88"));

        ArrayList<AccountItem> spendingAllowanceArrayList = new ArrayList<>();
        spendingAllowanceArrayList.add(new AccountItem("Gas", "$40.88"));
        spendingAllowanceArrayList.add(new AccountItem("Groceries", "$80.88"));
        spendingAllowanceArrayList.add(new AccountItem("Entertainment", "$50.88"));

        showFeedBills(billsArrayList);
        showFeedSpendingAllowance(spendingAllowanceArrayList);

        return rootView;
    }

    private void showFeedBills(ArrayList<AccountItem> accountItemArrayList) {

        bills.setLayoutManager(new LinearLayoutManager(getContext()));
        accountListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, "null");
        bills.setAdapter(accountListAdapter);

    }

    private void showFeedSpendingAllowance(ArrayList<AccountItem> accountItemArrayList) {

        allowance.setLayoutManager(new LinearLayoutManager(getContext()));
        accountListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, Config.TAG_LIST_SPENDING_ALLOWANCE);
        allowance.setAdapter(accountListAdapter);
    }

    @OnClick({R.id.add_bill, R.id.add_allowance})
    public void onClick(View view) {

        switch (view.getId()) {

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
        }

    }

}
