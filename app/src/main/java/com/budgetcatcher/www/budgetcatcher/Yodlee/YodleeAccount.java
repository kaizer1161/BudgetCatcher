package com.budgetcatcher.www.budgetcatcher.Yodlee;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import com.budgetcatcher.www.budgetcatcher.Model.YodleeGetAccountResponse;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.Network.URL;
import com.budgetcatcher.www.budgetcatcher.R;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class YodleeAccount extends Fragment {

    private static final String TAG = "Yodlee Account";

    @BindView(R.id.yodlee_account)
    RecyclerView yodleeAccount;
    @BindView(R.id.yodlee_account_swipe_down)
    SwipeRefreshLayout yodleeAccountSwipeDown;

    private SharedPreferences sharedPreferences;
    private ProgressDialog dialog;

    private AccountListAdapter yodleeAccountListAdapter;
    private String userID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.yodlee_account, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            dialog = ProgressDialog.show(getActivity(), "",
                    getString(R.string.loading), true);
            dialog.dismiss();

            Objects.requireNonNull(((YodleeActivity) getActivity()).getSupportActionBar()).setTitle("Pay your bills");

            userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

            fetchAllList();

            yodleeAccountSwipeDown.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    yodleeAccountSwipeDown.setRefreshing(false);
                    fetchAllList();

                }
            });

        }


        return rootView;
    }

    private void fetchAllList() {

        if (BudgetCatcher.getConnectedToInternet()) {

            yodleeGetAccountList();

        } else {

            Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

        }

    }

    private void yodleeGetAccountList() {

        if (getActivity() != null) {

            String session = URL.key_Yodlee_cobSession + ((YodleeActivity) getActivity()).cobrandLoginResponse.getSession().getCobSession() + "," + URL.key_Yodlee_userSession + ((YodleeActivity) getActivity()).yodleeUserLoginResponse.getUser().getSession().getUserSession();

            yodleeAccountSwipeDown.setRefreshing(true);
            BudgetCatcher.apiManager.yodleeGetAccount(session, new QueryCallback<String>() {
                @Override
                public void onSuccess(String data) {

                    yodleeAccountSwipeDown.setRefreshing(false);
                    Gson gson = new Gson();

                    YodleeGetAccountResponse yodleeGetAccountResponse = gson.fromJson(data, YodleeGetAccountResponse.class);

                    ArrayList<AccountItem> accountItemArrayList = new ArrayList<>();

                    for (int i = 0; i < yodleeGetAccountResponse.getAccount().size(); i++) {

                        accountItemArrayList.add(new AccountItem(yodleeGetAccountResponse.getAccount().get(i).getAccountName(), String.valueOf(yodleeGetAccountResponse.getAccount().get(i).getBalance().getAmount()), yodleeGetAccountResponse.getAccount().get(i).getAccountNumber()));

                    }

                    showFeedYodleeAccount(accountItemArrayList);


                }

                @Override
                public void onFail() {

                    yodleeAccountSwipeDown.setRefreshing(false);

                }

                @Override
                public void onError(Throwable th) {

                    yodleeAccountSwipeDown.setRefreshing(false);
                    if (getActivity() != null) {
                        Log.e("SerVerErrManage", th.toString());
                        if (th instanceof SocketTimeoutException) {
                            Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });

        }


    }

    private void showFeedYodleeAccount(ArrayList<AccountItem> accountItemArrayList/*, ArrayList<Income> incomeArrayList*/) {

        yodleeAccount.setLayoutManager(new LinearLayoutManager(getContext()));
        yodleeAccountListAdapter = new AccountListAdapter(getActivity(), accountItemArrayList, Config.TAG_LIST_YODLEE_ACCOUNT);
        yodleeAccount.setAdapter(yodleeAccountListAdapter);

    }

    @OnClick({R.id.add_account})
    public void onClick(View view) {

        switch (view.getId()) {

            case (R.id.add_account): {

                if (getActivity() != null)
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.yodlee_content, new AddYodleeAccount())
                            .addToBackStack(null)
                            .commit();

                break;
            }

        }

    }

}
