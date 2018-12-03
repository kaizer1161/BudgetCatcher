package com.pushertest.www.budgetcatcher.Adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pushertest.www.budgetcatcher.BudgetCatcher;
import com.pushertest.www.budgetcatcher.Config;
import com.pushertest.www.budgetcatcher.Model.AccountItem;
import com.pushertest.www.budgetcatcher.Model.Bill;
import com.pushertest.www.budgetcatcher.Network.QueryCallback;
import com.pushertest.www.budgetcatcher.R;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by User on 11-Dec-17.
 */

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountListAdapterHolder> {

    private LayoutInflater inflater;
    private Activity activity;
    private ArrayList<AccountItem> accountItemArrayList;
    private ArrayList<Bill> bills;
    private String fragmentTag;

    public AccountListAdapter(Activity activity, ArrayList<AccountItem> accountItemArrayList, String fragmentTag) {

        inflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.accountItemArrayList = accountItemArrayList;
        this.fragmentTag = fragmentTag;

    }

    public AccountListAdapter(Activity activity, ArrayList<AccountItem> accountItemArrayList, String fragmentTag, ArrayList<Bill> bills) {

        inflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.accountItemArrayList = accountItemArrayList;
        this.fragmentTag = fragmentTag;
        this.bills = bills;

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
        @BindView(R.id.item_body)
        RelativeLayout itemBody;

        AccountListAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemBody.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                    builder1.setCancelable(false);

                    builder1.setTitle("Please select a action");
                    builder1.setMessage(col1.getText().toString() + " " + col3.getText().toString());

                    builder1.setPositiveButton(
                            activity.getResources().getString(R.string.edit),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                }
                            }).setNegativeButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dialog.cancel();
                        }
                    }).setNeutralButton(activity.getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    final AlertDialog alert11 = builder1.create();

                    alert11.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            alert11.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.RED);
                            alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(activity.getResources().getColor(R.color.deep_sea_dive));
                            alert11.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(activity.getResources().getColor(R.color.hinoki));
                            alert11.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if (fragmentTag.equals(Config.TAG_LIST_BILL))
                                        Toast.makeText(activity, "" + bills.get(getAdapterPosition()).getBillId(), Toast.LENGTH_SHORT).show();

                                }
                            });

                            alert11.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    String userID = activity.getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

                                    if (fragmentTag.equals(Config.TAG_LIST_BILL)) {

                                        BudgetCatcher.apiManager.deleteBill(userID, bills.get(getAdapterPosition()).getBillId(), new QueryCallback<String>() {
                                            @Override
                                            public void onSuccess(String data) {

                                                Toast.makeText(activity, "Successfully deleted", Toast.LENGTH_SHORT).show();
                                                accountItemArrayList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());
                                                alert11.dismiss();

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
                            });

                            alert11.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    alert11.dismiss();

                                }
                            });

                        }
                    });

                    alert11.show();

                    return false;
                }
            });

        }

    }

}

