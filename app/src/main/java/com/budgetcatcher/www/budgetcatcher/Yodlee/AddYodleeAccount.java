package com.budgetcatcher.www.budgetcatcher.Yodlee;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.YodleeFastLinkResponse;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.Network.URL;
import com.budgetcatcher.www.budgetcatcher.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class AddYodleeAccount extends Fragment {

    private static final int SPINNER_INITIAL_POSITION = 0;

    /*@BindView(R.id.name)
    EditText name;
    @BindView(R.id.account_type)
    Spinner accountType;
    @BindView(R.id.number)
    EditText number;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.amount)
    EditText amount;*/

    @BindView(R.id.web_view)
    WebView webView;

    private ProgressDialog dialog;
    private boolean typeSelected = false;
    private String userID;
    private ArrayList<String> accountTypeList;
    private ArrayAdapter<String> accountTypeListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_yodlee_account, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

            dialog = ProgressDialog.show(getActivity(), "",
                    getString(R.string.loading), true);
            dialog.dismiss();

            Objects.requireNonNull(((YodleeActivity) getActivity()).getSupportActionBar()).setTitle("Add Account");

            setAccountTypeList();

            if (BudgetCatcher.connectedToInternet) {

                getFastLink();

            } else {

                Toast.makeText(getActivity(), getString(R.string.connect_to_internet), Toast.LENGTH_SHORT).show();

            }

            /*accountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    *//*
                     * If position == SPINNER_INITIAL_POSITION,
                     * then financialGoalSpinnerSelected = false
                     * else financialGoalSpinnerSelected = true.
             *//*
                    typeSelected = position != SPINNER_INITIAL_POSITION;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/

        }


        /*name.requestFocus();*/

        return rootView;
    }

    private void getFastLink() {

        if (getActivity() != null) {

            final String session = URL.key_Yodlee_cobSession + ((YodleeActivity) getActivity()).cobrandLoginResponse.getSession().getCobSession() + "," + URL.key_Yodlee_userSession + ((YodleeActivity) getActivity()).yodleeUserLoginResponse.getUser().getSession().getUserSession();

            dialog.show();
            BudgetCatcher.apiManager.yodleeFastLink(session, new QueryCallback<String>() {
                @Override
                public void onSuccess(String data) {

                    dialog.dismiss();

                    Gson gson = new Gson();
                    YodleeFastLinkResponse yodleeFastLinkResponse = gson.fromJson(data, YodleeFastLinkResponse.class);

                    String appId = yodleeFastLinkResponse.getUser().getAccessTokens().get(0).getAppId();
                    String token = yodleeFastLinkResponse.getUser().getAccessTokens().get(0).getValue();

                    String uri = URL.base + URL.getFastLinkView + appId + "/" + ((YodleeActivity) getActivity()).yodleeUserLoginResponse.getUser().getSession().getUserSession() + "/" + token + "/" + "true" + "/" + "callback=www.google.com&dataset=%5B%20%20%0A%20%20%20%7B%20%20%0A%20%20%20%20%20%20%22name%22%3A%22ACCT_PROFILE%22%2C%0A%20%20%20%20%20%20%22attribute%22%3A%5B%0A%20%20%20%20%20%20%20%20%20%7B%20%20%0A%20%20%20%20%20%20%20%20%20%20%20%20%22name%22%3A%22BANK_TRANSFER_CODE%22%2C%0A%20%20%20%20%20%20%20%20%20%20%20%20%22container%22%3A%5B%20%20%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%22bank%22%0A%20%20%20%20%20%20%20%20%20%20%20%20%5D%0A%20%20%20%20%20%20%20%20%20%7D%2C%0A%20%20%20%20%20%20%20%20%20%7B%20%20%0A%20%20%20%20%20%20%20%20%20%20%20%20%22name%22%3A%22HOLDER_NAME%22%2C%0A%20%20%20%20%20%20%20%20%20%20%20%20%22container%22%3A%5B%20%20%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%22bank%22%0A%20%20%20%20%20%20%20%20%20%20%20%20%5D%0A%20%20%20%20%20%20%20%20%20%7D%0A%20%20%20%20%20%20%5D%0A%20%20%20%7D%0A%5D";

                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl(uri);

                }

                @Override
                public void onFail() {

                    dialog.dismiss();

                }

                @Override
                public void onError(Throwable th) {

                    dialog.dismiss();

                }
            });

        }

    }

    private void setAccountTypeList() {

        accountTypeList = new ArrayList<>();
        accountTypeList.add("Select account type");
        accountTypeList.add("CHECKING");
        accountTypeList.add("SAVINGS");
        accountTypeList.add("CD");
        accountTypeList.add("PPF");
        accountTypeList.add("RECURRING_DEPOSIT");
        accountTypeList.add("PREPAID");
        accountTypeList.add("OTHER");
        /*accountTypeList.add("403b");
        accountTypeList.add("trust");
        accountTypeList.add("annuity");
        accountTypeList.add("simple");
        accountTypeList.add("custodial");
        accountTypeList.add("brokerageCashOption");
        accountTypeList.add("brokerageMarginOption");
        accountTypeList.add("jttic");
        accountTypeList.add("jtwros");
        accountTypeList.add("communityProperty");
        accountTypeList.add("jointByEntirety");
        accountTypeList.add("conservatorShip");
        accountTypeList.add("roth");
        accountTypeList.add("rothConversion");
        accountTypeList.add("rollover");
        accountTypeList.add("educational");
        accountTypeList.add("529Plan");
        accountTypeList.add("457DeferredCompensation");
        accountTypeList.add("401a");
        accountTypeList.add("psp");
        accountTypeList.add("mpp");
        accountTypeList.add("stockBasket");
        accountTypeList.add("livingTrust");
        accountTypeList.add("revocableTrust");
        accountTypeList.add("irrevocableTrust");
        accountTypeList.add("charitableRemainder");
        accountTypeList.add("charitableLead");
        accountTypeList.add("charitableGiftAccount");
        accountTypeList.add("sep");
        accountTypeList.add("utma");
        accountTypeList.add("ugma");
        accountTypeList.add("esopp");
        accountTypeList.add("administrator");
        accountTypeList.add("executor");
        accountTypeList.add("partnership");
        accountTypeList.add("soleProprietorShip");
        accountTypeList.add("church");
        accountTypeList.add("investmentClub");
        accountTypeList.add("restrictedStockAward");
        accountTypeList.add("employeeStockPurchasePlan");
        accountTypeList.add("CMA");
        accountTypeList.add("performancePlan");
        accountTypeList.add("brokerageLinkAccount");*/

        accountTypeListAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, R.id.spinner_item_text, accountTypeList);
        /*accountType.setAdapter(accountTypeListAdapter);*/

    }

    /*@OnClick({R.id.save})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.save: {

                boolean hasError = false;

                if (name.getText().toString().equals("")) {
                    name.setError("Empty");
                    hasError = true;
                }
                if (!typeSelected) {
                    Toast.makeText(getActivity(), "Select account type", Toast.LENGTH_SHORT).show();
                    hasError = true;
                }
                if (number.getText().toString().equals("")) {
                    number.setError("Empty");
                    hasError = true;
                }
                if (amount.getText().toString().equals("")) {
                    amount.setError("Empty");
                    hasError = true;
                }
                if (!BudgetCatcher.getConnectedToInternet()) {

                    hasError = true;
                    Toast.makeText(getActivity(), getString(R.string.connect_to_internet), Toast.LENGTH_SHORT).show();

                }
                if (!hasError) {

                    saveDataToServer();

                }

                break;
            }

        }

    }

    private void saveDataToServer() {

        if (getActivity() != null) {

            dialog.show();

            YodleeCreateManualAccountBody yodleeCreateManualAccountBody = new YodleeCreateManualAccountBody(new YodleeCreateManualAccountBody.Account(accountTypeList.get(accountType.getSelectedItemPosition()), name.getText().toString(), number.getText().toString(), new YodleeCreateManualAccountBody.Balance(amount.getText().toString(), "USD"), description.getText().toString()));

            String session = URL.key_Yodlee_cobSession + ((YodleeActivity) getActivity()).cobrandLoginResponse.getSession().getCobSession() + "," + URL.key_Yodlee_userSession + ((YodleeActivity) getActivity()).yodleeUserLoginResponse.getUser().getSession().getUserSession();

            BudgetCatcher.apiManager.yodleeCrateAccountManually(session, yodleeCreateManualAccountBody, new QueryCallback<String>() {
                @Override
                public void onSuccess(String data) {

                    dialog.dismiss();
                    Toast.makeText(getActivity(), getString(R.string.successfully_added), Toast.LENGTH_SHORT).show();
                    if (getActivity() != null) {

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.yodlee_content, new YodleeAccount())
                                .commit();

                    }

                }

                @Override
                public void onFail() {

                    dialog.dismiss();
                    Toast.makeText(getActivity(), getString(R.string.failed_to_added), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(Throwable th) {

                    dialog.dismiss();
                    if (getActivity() != null) {
                        Log.e("SerVerErrAddBill", th.toString());
                        if (th instanceof SocketTimeoutException) {
                            Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), th.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });

        }

    }*/

}
