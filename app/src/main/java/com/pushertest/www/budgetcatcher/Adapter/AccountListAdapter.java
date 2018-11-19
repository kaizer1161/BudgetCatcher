package com.pushertest.www.budgetcatcher.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pushertest.www.budgetcatcher.Config;
import com.pushertest.www.budgetcatcher.Model.AccountItem;
import com.pushertest.www.budgetcatcher.R;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 11-Dec-17.
 */

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountListAdapterHolder> {

    private LayoutInflater inflater;
    private Activity activity;
    private ArrayList<AccountItem> accountItemArrayList;
    private String fragmentTag;

    public AccountListAdapter(Activity activity, ArrayList<AccountItem> accountItemArrayList, String fragmentTag) {

        inflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.accountItemArrayList = accountItemArrayList;
        this.fragmentTag = fragmentTag;

    }

    @Override
    public AccountListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new AccountListAdapter.AccountListAdapterHolder(inflater.inflate(R.layout.account_item_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(AccountListAdapterHolder holder, int position) {

        AccountItem accountItem = accountItemArrayList.get(position);

        if (fragmentTag.equals(Config.TAG_LIST_SPENDING_ALLOWANCE)) {

            holder.col1.setText(accountItem.getCol1());
            holder.col3.setText(accountItem.getCol3());

        } else {

            holder.col1.setText(accountItem.getCol1());
            holder.col2.setText(accountItem.getCol2());
            holder.col3.setText(accountItem.getCol3());

        }

    }

    @Override
    public int getItemCount() {
        return accountItemArrayList.size();
    }

    public void listUpdated(AccountItem[] accountItems) {

        accountItemArrayList.clear();
        Collections.addAll(accountItemArrayList, accountItems);
        notifyDataSetChanged();

    }

    class AccountListAdapterHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.col1)
        TextView col1;
        @BindView(R.id.col2)
        TextView col2;
        @BindView(R.id.col3)
        TextView col3;

        AccountListAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
