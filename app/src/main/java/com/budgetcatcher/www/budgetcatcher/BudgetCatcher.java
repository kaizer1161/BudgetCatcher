package com.budgetcatcher.www.budgetcatcher;

import android.app.Application;

import com.budgetcatcher.www.budgetcatcher.Network.ApiManager;

public class BudgetCatcher extends Application {

    public static ApiManager apiManager;

    public static Boolean connectedToInternet;

    // Gloabl declaration of variable to use in whole app

    public static boolean activityVisible; // Variable that will check the
    // current activity state

    public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }

    public static void activityResumed() {
        activityVisible = true;// this will set true when activity resumed

    }

    public static void activityPaused() {
        activityVisible = false;// this will set false when activity paused

    }

    public static Boolean getConnectedToInternet() {

        if (connectedToInternet != null) {

            return connectedToInternet;

        } else {

            return false;

        }

    }

    public static void setConnectedToInternet(Boolean status) {

        connectedToInternet = status;

    }

    @Override
    public void onCreate() {
        super.onCreate();

        apiManager = ApiManager.getInstance();

    }
}
