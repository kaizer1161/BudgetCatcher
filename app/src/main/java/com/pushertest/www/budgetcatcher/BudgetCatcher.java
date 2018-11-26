package com.pushertest.www.budgetcatcher;

import android.app.Application;

import com.pushertest.www.budgetcatcher.Network.ApiManager;

public class BudgetCatcher extends Application {

    public static ApiManager apiManager;

    @Override
    public void onCreate() {
        super.onCreate();

        apiManager = ApiManager.getInstance();

    }
}
