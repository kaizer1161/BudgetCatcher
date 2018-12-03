package com.pushertest.www.budgetcatcher;

import java.util.concurrent.TimeUnit;

public class Config {

    /*Shared preference */
    public static final String SP_APP_NAME = "BUDGET_CATCHER";
    public static final String SP_LOGGED_IN = "loggedIn";
    public static final String SP_USER_ID = "USER_ID";
    public static final String SP_USER_CREATED_LEVEL = "USER_CREATED";
    public static final int SP_USER_CREATED_LEVEL_SIGN_UP = 100;
    public static final int SP_USER_CREATED_LEVEL_NONE = 99;

    public static final String TAG_HOME_FRAGMENT = "TAG_HOME_FRAGMENT";
    public static final String TAG_CATCHER_FRAGMENT = "TAG_CATCHER_FRAGMENT";
    public static final String TAG_MANAGE_FRAGMENT = "TAG_MANAGE_FRAGMENT";
    public static final String TAG_ADVICE_FRAGMENT = "TAG_ADVICE_FRAGMENT";
    public static final String TAG_ADD_BILL_FRAGMENT = "TAG_ADD_BILL_FRAGMENT";
    public static final String TAG_ADD_ALLOWANCE_FRAGMENT = "TAG_ADD_ALLOWANCE_FRAGMENT";
    public static final String TAG_REPORT_FRAGMENT = "TAG_REPORT_FRAGMENT";
    public static final String TAG_EDIT_BILL_FRAGMENT = "TAG_EDIT_BILL_FRAGMENT";

    public static final String TAG_LIST_SPENDING_ALLOWANCE = "TAG_LIST_SPENDING_ALLOWANCE";
    public static final String TAG_LIST_BILL = "TAG_LIST_BILL";
    public static final String TAG_LIST_INCIDENTAL = "TAG_LIST_INCIDENTAL";

    public static final String KEY_SERIALIZABLE = "KEY_SERIALIZABLE";

    /*retrofit okHttp timeout values*/
    public static final int connectTimeout = 60;
    public static final int writeTimeout = 60;
    public static final int readTimeout = 60;

    /*retrofit okHttp timeout unit*/
    public static final TimeUnit timeOutUnit = TimeUnit.SECONDS;
}
