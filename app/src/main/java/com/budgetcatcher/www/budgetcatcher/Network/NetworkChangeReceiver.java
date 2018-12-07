package com.budgetcatcher.www.budgetcatcher.Network;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;


/**
 * Created by User on 28-Nov-17.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        try {

            boolean isVisible = BudgetCatcher.isActivityVisible();// Check if
            // activity
            // is
            // visible
            // or not
            /*Log.i("Activity is Visible ", "Is activity visible : " + isVisible);*/

            // If it is visible then trigger the task else do nothing
            if (isVisible) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = null;
                if (connectivityManager != null) {
                    networkInfo = connectivityManager
                            .getActiveNetworkInfo();
                }

                // Check internet connection and accrding to state change the
                // text of activity by calling method
                BudgetCatcher.setConnectedToInternet(networkInfo != null && networkInfo.isConnected());

                if (networkInfo != null && networkInfo.isConnected()) {
                    //Toast.makeText(context, "Connected to internet", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show();

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
