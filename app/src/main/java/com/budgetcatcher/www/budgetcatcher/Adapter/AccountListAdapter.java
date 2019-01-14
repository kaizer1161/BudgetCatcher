package com.budgetcatcher.www.budgetcatcher.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.AccountItem;
import com.budgetcatcher.www.budgetcatcher.Model.Allowance;
import com.budgetcatcher.www.budgetcatcher.Model.Bill;
import com.budgetcatcher.www.budgetcatcher.Model.Expenses;
import com.budgetcatcher.www.budgetcatcher.Model.Income;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.R;
import com.budgetcatcher.www.budgetcatcher.View.Activity.MainActivity;
import com.budgetcatcher.www.budgetcatcher.View.Fragment.EditAllowance;
import com.budgetcatcher.www.budgetcatcher.View.Fragment.EditBill;
import com.budgetcatcher.www.budgetcatcher.View.Fragment.EditIncidental;
import com.budgetcatcher.www.budgetcatcher.View.Fragment.EditIncome;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
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
    private ArrayList<Allowance> allowances;
    private ArrayList<Expenses> expenses;
    private ArrayList<Income> incomes;
    private String fragmentTag;
    private Boolean hasClickListener;

    public AccountListAdapter(Activity activity, ArrayList<AccountItem> accountItemArrayList, String fragmentTag) {

        inflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.accountItemArrayList = accountItemArrayList;
        this.fragmentTag = fragmentTag;
        hasClickListener = false;

    }

    public AccountListAdapter(Activity activity, ArrayList<AccountItem> accountItemArrayList, String fragmentTag, ArrayList<Bill> bills, ArrayList<Allowance> allowances, ArrayList<Expenses> expenses, ArrayList<Income> incomes) {

        inflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.accountItemArrayList = accountItemArrayList;
        this.fragmentTag = fragmentTag;
        hasClickListener = true;


        if (fragmentTag.equals(Config.TAG_LIST_BILL)) {

            this.bills = bills;

        } else if (fragmentTag.equals(Config.TAG_LIST_SPENDING_ALLOWANCE)) {

            this.allowances = allowances;

        } else if (fragmentTag.equals(Config.TAG_LIST_INCIDENTAL)) {

            this.expenses = expenses;

        } else if (fragmentTag.equals(Config.TAG_LIST_INCOME)) {

            this.incomes = incomes;

        }

    }

    @Override
    public AccountListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new AccountListAdapter.AccountListAdapterHolder(inflater.inflate(R.layout.account_item_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(AccountListAdapterHolder holder, int position) {

        AccountItem accountItem = accountItemArrayList.get(position);

        String temp = accountItem.getCol3();
        /*Log.i("temp", temp);*/
        temp = temp.replace("$", "");
        Float val = Float.parseFloat(temp);
        String valStr = String.format("%.2f", val);
        /*Log.d("valStr", valStr);*/
        /*int dec = temp.indexOf(".");
        if (dec != -1) {
            if (temp.length() > dec + 3)
                temp = temp.substring(0, dec + 3);

        }*/
        //Log.d("TEMP", "saveDataToServer: " + dec);

        if (fragmentTag.equals(Config.TAG_LIST_SPENDING_ALLOWANCE)) {

            holder.col1.setText(accountItem.getCol1());
            holder.col3.setText("$" + valStr);

        } else {

            holder.col1.setText(accountItem.getCol1());
            holder.col2.setText(accountItem.getCol2());
            holder.col3.setText("$" + valStr);

        }

    }

    @Override
    public int getItemCount() {
        return accountItemArrayList.size();
    }

    public void clear() {

        if (accountItemArrayList != null)
            accountItemArrayList.clear();
        if (bills != null)
            bills.clear();
        if (allowances != null)
            allowances.clear();
        if (incomes != null)
            incomes.clear();
        if (expenses != null)
            expenses.clear();
        notifyDataSetChanged();

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

            if (hasClickListener) {

                itemBody.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        final ProgressDialog dialog = ProgressDialog.show(activity, "",
                                activity.getResources().getString(R.string.loading), true);
                        dialog.dismiss();

                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setCancelable(false);

                        builder1.setTitle("Please select a action");
                        builder1.setMessage(col1.getText().toString() + " " + col3.getText().toString());

                        builder1.setPositiveButton(
                                activity.getString(R.string.edit),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                    }
                                }).setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.cancel();
                            }
                        }).setNeutralButton(activity.getString(R.string.delete), new DialogInterface.OnClickListener() {
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

                                        Bundle bundle = new Bundle();
                                        Gson gson = new Gson();

                                        if (fragmentTag.equals(Config.TAG_LIST_INCOME)) {

                                            alert11.dismiss();

                                            bundle.putString(Config.KEY_SERIALIZABLE, gson.toJson(incomes.get(getAdapterPosition())));

                                            EditIncome editIncome = new EditIncome();
                                            editIncome.setArguments(bundle);

                                            ((MainActivity) activity).getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.content, editIncome, Config.TAG_EDIT_INCOME_FRAGMENT)
                                                    .addToBackStack(null)
                                                    .commit();

                                        } else if (fragmentTag.equals(Config.TAG_LIST_BILL)) {

                                            alert11.dismiss();

                                            bundle.putString(Config.KEY_SERIALIZABLE, gson.toJson(bills.get(getAdapterPosition())));

                                            EditBill editBill = new EditBill();
                                            editBill.setArguments(bundle);

                                            ((MainActivity) activity).getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.content, editBill, Config.TAG_EDIT_BILL_FRAGMENT)
                                                    .addToBackStack(null)
                                                    .commit();

                                        } else if (fragmentTag.equals(Config.TAG_LIST_SPENDING_ALLOWANCE)) {

                                            alert11.dismiss();

                                            bundle.putString(Config.KEY_SERIALIZABLE, gson.toJson(allowances.get(getAdapterPosition())));

                                            EditAllowance editAllowance = new EditAllowance();
                                            editAllowance.setArguments(bundle);

                                            ((MainActivity) activity).getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.content, editAllowance, Config.TAG_EDIT_ALLOWANCE_FRAGMENT)
                                                    .addToBackStack(null)
                                                    .commit();

                                        } else if (fragmentTag.equals(Config.TAG_LIST_INCIDENTAL)) {

                                            alert11.dismiss();

                                            bundle.putString(Config.KEY_SERIALIZABLE, gson.toJson(expenses.get(getAdapterPosition())));

                                            EditIncidental editIncidental = new EditIncidental();
                                            editIncidental.setArguments(bundle);

                                            ((MainActivity) activity).getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.content, editIncidental, Config.TAG_EDIT_BILL_FRAGMENT)
                                                    .addToBackStack(null)
                                                    .commit();

                                        }

                                    }
                                });

                                alert11.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        String userID = activity.getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

                                        if (fragmentTag.equals(Config.TAG_LIST_INCOME)) {

                                            dialog.show();

                                            BudgetCatcher.apiManager.deleteIncome(userID, incomes.get(getAdapterPosition()).getIncomeId(), new QueryCallback<String>() {
                                                @Override
                                                public void onSuccess(String data) {

                                                    dialog.dismiss();
                                                    Toast.makeText(activity, activity.getString(R.string.successfully_deleted), Toast.LENGTH_SHORT).show();
                                                    accountItemArrayList.remove(getAdapterPosition());
                                                    notifyDataSetChanged();
                                                    alert11.dismiss();

                                                }

                                                @Override
                                                public void onFail() {
                                                    dialog.dismiss();
                                                    Toast.makeText(activity, activity.getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onError(Throwable th) {

                                                    dialog.dismiss();
                                                    if (activity != null) {
                                                        Log.e("SerVerErrAddBill", th.toString());
                                                        if (th instanceof SocketTimeoutException) {
                                                            Toast.makeText(activity, activity.getResources().getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(activity, th.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                }
                                            });

                                        } else if (fragmentTag.equals(Config.TAG_LIST_BILL)) {

                                            dialog.show();

                                            BudgetCatcher.apiManager.deleteBill(userID, bills.get(getAdapterPosition()).getBillId(), new QueryCallback<String>() {
                                                @Override
                                                public void onSuccess(String data) {

                                                    dialog.dismiss();
                                                    Toast.makeText(activity, activity.getString(R.string.successfully_deleted), Toast.LENGTH_SHORT).show();
                                                    accountItemArrayList.remove(getAdapterPosition());
                                                    notifyDataSetChanged();
                                                    alert11.dismiss();

                                                }

                                                @Override
                                                public void onFail() {
                                                    dialog.dismiss();
                                                    Toast.makeText(activity, activity.getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onError(Throwable th) {

                                                    dialog.dismiss();
                                                    if (activity != null) {
                                                        Log.e("SerVerErrAddBill", th.toString());
                                                        if (th instanceof SocketTimeoutException) {
                                                            Toast.makeText(activity, activity.getResources().getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(activity, th.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                }
                                            });

                                        } else if (fragmentTag.equals(Config.TAG_LIST_SPENDING_ALLOWANCE)) {

                                            dialog.show();

                                            BudgetCatcher.apiManager.deleteAllowance(userID, allowances.get(getAdapterPosition()).getAllowanceId(), new QueryCallback<String>() {
                                                @Override
                                                public void onSuccess(String data) {

                                                    dialog.dismiss();
                                                    Toast.makeText(activity, activity.getString(R.string.successfully_deleted), Toast.LENGTH_SHORT).show();
                                                    accountItemArrayList.remove(getAdapterPosition());
                                                    notifyDataSetChanged();
                                                    alert11.dismiss();

                                                }

                                                @Override
                                                public void onFail() {
                                                    dialog.dismiss();
                                                    Toast.makeText(activity, activity.getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onError(Throwable th) {

                                                    dialog.dismiss();
                                                    if (activity != null) {
                                                        Log.e("SerVerErrAddBill", th.toString());
                                                        if (th instanceof SocketTimeoutException) {
                                                            Toast.makeText(activity, activity.getResources().getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(activity, th.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                }
                                            });

                                        } else if (fragmentTag.equals(Config.TAG_LIST_INCIDENTAL)) {

                                            dialog.show();

                                            BudgetCatcher.apiManager.deleteExpense(userID, expenses.get(getAdapterPosition()).getExpenseId(), new QueryCallback<String>() {
                                                @Override
                                                public void onSuccess(String data) {

                                                    dialog.dismiss();
                                                    Toast.makeText(activity, activity.getString(R.string.successfully_deleted), Toast.LENGTH_SHORT).show();
                                                    accountItemArrayList.remove(getAdapterPosition());
                                                    notifyDataSetChanged();
                                                    alert11.dismiss();

                                                }

                                                @Override
                                                public void onFail() {
                                                    dialog.dismiss();
                                                    Toast.makeText(activity, activity.getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onError(Throwable th) {

                                                    dialog.dismiss();
                                                    if (activity != null) {
                                                        Log.e("SerVerErrAddBill", th.toString());
                                                        if (th instanceof SocketTimeoutException) {
                                                            Toast.makeText(activity, activity.getResources().getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(activity, th.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

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

}

