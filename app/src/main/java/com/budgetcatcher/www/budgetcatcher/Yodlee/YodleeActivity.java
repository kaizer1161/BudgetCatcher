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
import android.util.Log;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Model.CobrandLoginResponse;
import com.budgetcatcher.www.budgetcatcher.Model.YodleeUserLoginResponse;
import com.budgetcatcher.www.budgetcatcher.Network.NetworkChangeReceiver;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.R;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;

import butterknife.ButterKnife;

public class YodleeActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private BroadcastReceiver mNetworkReceiver;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_screen);
        ButterKnife.bind(this);

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
                    CobrandLoginResponse cobrandLoginResponse = gson.fromJson(data, CobrandLoginResponse.class);
                    yodleeLogin(cobrandLoginResponse);

                }

                @Override
                public void onFail() {

                    dialog.dismiss();
                    Toast.makeText(YodleeActivity.this, "Something went wrong: server error", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(Throwable th) {

                    dialog.dismiss();
                    Log.e("SerVerErr", th.toString());
                    finish();
                    if (th instanceof SocketTimeoutException) {
                        Toast.makeText(YodleeActivity.this, getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(YodleeActivity.this, th.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } else {

            Toast.makeText(YodleeActivity.this, getString(R.string.connect_to_internet), Toast.LENGTH_SHORT).show();

        }

    }

    private void yodleeLogin(CobrandLoginResponse cobrandLoginResponse) {

        if (BudgetCatcher.connectedToInternet) {

            BudgetCatcher.apiManager.yodleeUserLogin(cobrandLoginResponse.getSession().getCobSession(), "sbMemdd1dcc211512e9a5485650d4299722930a1", "sbMemdd1dcc211512e9a5485650d4299722930a1#123", new QueryCallback<String>() {
                @Override
                public void onSuccess(String data) {

                    Gson gson = new Gson();
                    YodleeUserLoginResponse yodleeUserLoginResponse = gson.fromJson(data, YodleeUserLoginResponse.class);

                    dialog.dismiss();

                }

                @Override
                public void onFail() {

                    dialog.dismiss();
                    Toast.makeText(YodleeActivity.this, "Something went wrong: server error", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(Throwable th) {

                    dialog.dismiss();
                    Log.e("SerVerErr", th.toString());
                    finish();
                    if (th instanceof SocketTimeoutException) {
                        Toast.makeText(YodleeActivity.this, getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(YodleeActivity.this, th.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } else {

            dialog.dismiss();
            Toast.makeText(YodleeActivity.this, getString(R.string.connect_to_internet), Toast.LENGTH_SHORT).show();

        }

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
