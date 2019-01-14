package com.budgetcatcher.www.budgetcatcher.Yodlee;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Model.CobrandLoginResponse;
import com.budgetcatcher.www.budgetcatcher.Model.YodleeFastLinkResponse;
import com.budgetcatcher.www.budgetcatcher.Model.YodleeUserLoginResponse;
import com.budgetcatcher.www.budgetcatcher.Network.NetworkChangeReceiver;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.Network.URL;
import com.budgetcatcher.www.budgetcatcher.R;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YodleeActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private BroadcastReceiver mNetworkReceiver;
    private ProgressDialog dialog;

    public YodleeUserLoginResponse yodleeUserLoginResponse;
    public CobrandLoginResponse cobrandLoginResponse;

    @BindView(R.id.web_view)
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yodlee);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dialog = ProgressDialog.show(YodleeActivity.this, "",
                getString(R.string.loading), true);
        dialog.dismiss();

        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();
//        sharedPreferences = getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE);

        loginToCobrand();

    }

    private void loginToCobrand() {

        if (BudgetCatcher.connectedToInternet) {

            dialog.show();
            BudgetCatcher.apiManager.cobrandLogin(new QueryCallback<String>() {
                @Override
                public void onSuccess(String data) {

                    Gson gson = new Gson();
                    cobrandLoginResponse = gson.fromJson(data, CobrandLoginResponse.class);
                    yodleeLogin(cobrandLoginResponse);

                }

                @Override
                public void onFail() {

                    dialog.dismiss();
                    Toast.makeText(YodleeActivity.this, "Something went wrong: server error", Toast.LENGTH_SHORT).show();
                    onBackPressed();

                }

                @Override
                public void onError(Throwable th) {

                    dialog.dismiss();
                    Log.e("SerVerErr", th.toString());
                    finish();
                    if (th instanceof SocketTimeoutException) {
                        Toast.makeText(YodleeActivity.this, getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(YodleeActivity.this, th.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    onBackPressed();

                }
            });

        } else {

            Toast.makeText(YodleeActivity.this, getString(R.string.connect_to_internet), Toast.LENGTH_SHORT).show();

        }

    }

    private void yodleeLogin(final CobrandLoginResponse cobrandLoginResponse) {

        if (BudgetCatcher.connectedToInternet) {

            BudgetCatcher.apiManager.yodleeUserLogin(cobrandLoginResponse.getSession().getCobSession(), "sbMemdd1dcc211512e9a5485650d4299722930a1", "sbMemdd1dcc211512e9a5485650d4299722930a1#123", new QueryCallback<String>() {
                @Override
                public void onSuccess(String data) {

                    Gson gson = new Gson();
                    yodleeUserLoginResponse = gson.fromJson(data, YodleeUserLoginResponse.class);

                    /*getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.yodlee_content, new YodleeAccount())
                            .commit();*/

                    getFastLink();

                    dialog.dismiss();

                }

                @Override
                public void onFail() {

                    dialog.dismiss();
                    Toast.makeText(YodleeActivity.this, "Something went wrong: server error", Toast.LENGTH_SHORT).show();
                    onBackPressed();

                }

                @Override
                public void onError(Throwable th) {

                    dialog.dismiss();
                    Log.e("SerVerErr", th.toString());
                    finish();
                    if (th instanceof SocketTimeoutException) {
                        Toast.makeText(YodleeActivity.this, getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(YodleeActivity.this, th.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    onBackPressed();

                }
            });

        } else {

            dialog.dismiss();
            Toast.makeText(YodleeActivity.this, getString(R.string.connect_to_internet), Toast.LENGTH_SHORT).show();

        }

    }

    private void getFastLink() {

        final String session = URL.key_Yodlee_cobSession + cobrandLoginResponse.getSession().getCobSession() + "," + URL.key_Yodlee_userSession + yodleeUserLoginResponse.getUser().getSession().getUserSession();

        dialog.show();
        BudgetCatcher.apiManager.yodleeFastLink(session, new QueryCallback<String>() {
            @Override
            public void onSuccess(String data) {

                dialog.dismiss();

                Gson gson = new Gson();
                YodleeFastLinkResponse yodleeFastLinkResponse = gson.fromJson(data, YodleeFastLinkResponse.class);

                String appId = yodleeFastLinkResponse.getUser().getAccessTokens().get(0).getAppId();
                String token = yodleeFastLinkResponse.getUser().getAccessTokens().get(0).getValue();

                String uri = URL.base + URL.getFastLinkView + appId + "/" + yodleeUserLoginResponse.getUser().getSession().getUserSession() + "/" + token + "/" + "true" + "/" + "callback=www.google.com&dataset=%5B%20%20%0A%20%20%20%7B%20%20%0A%20%20%20%20%20%20%22name%22%3A%22ACCT_PROFILE%22%2C%0A%20%20%20%20%20%20%22attribute%22%3A%5B%0A%20%20%20%20%20%20%20%20%20%7B%20%20%0A%20%20%20%20%20%20%20%20%20%20%20%20%22name%22%3A%22BANK_TRANSFER_CODE%22%2C%0A%20%20%20%20%20%20%20%20%20%20%20%20%22container%22%3A%5B%20%20%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%22bank%22%0A%20%20%20%20%20%20%20%20%20%20%20%20%5D%0A%20%20%20%20%20%20%20%20%20%7D%2C%0A%20%20%20%20%20%20%20%20%20%7B%20%20%0A%20%20%20%20%20%20%20%20%20%20%20%20%22name%22%3A%22HOLDER_NAME%22%2C%0A%20%20%20%20%20%20%20%20%20%20%20%20%22container%22%3A%5B%20%20%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%22bank%22%0A%20%20%20%20%20%20%20%20%20%20%20%20%5D%0A%20%20%20%20%20%20%20%20%20%7D%0A%20%20%20%20%20%20%5D%0A%20%20%20%7D%0A%5D";

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

    private void registerNetworkBroadcastForNougat() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
        } catch (Exception e) {
            Log.v("Internet Reg : ", " " + e);
        }

    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }

    @Override
    protected void onPause() {

        super.onPause();
        BudgetCatcher.activityPaused();// On Pause notify the Application
    }

    @Override
    protected void onResume() {

        super.onResume();
        BudgetCatcher.activityResumed();// On Resume notify the Application
    }

}
