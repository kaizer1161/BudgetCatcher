package com.budgetcatcher.www.budgetcatcher;

import java.util.concurrent.TimeUnit;

public class Config {

    /*Shared preference */
    public static final String SP_USER_ID = "USER_ID";
    public static final String SP_LOGGED_IN = "loggedIn";
    public static final String SP_USER_INFO = "SP_USER_INFO";
    public static final String SP_WEEK_INFO = "SP_WEEK_INFO";
    public static final String SP_APP_NAME = "BUDGET_CATCHER";
    public static final String SP_MONTH_INFO = "SP_MONTH_INFO";
    public static final String SP_USER_CREATED_LEVEL = "USER_CREATED";
    public static final String SP_CURRENT_WEEK_INFO = "SP_CURRENT_WEEK_INFO";
    public static final String SP_CURRENT_MONTH_INFO = "SP_CURRENT_MONTH_INFO";
    public static final int SP_USER_CREATED_LEVEL_SIGN_UP = 100;
    public static final int SP_USER_CREATED_LEVEL_NONE = 99;

    /*Fragment Tag*/
    public static final String TAG_LIST_BILL = "TAG_LIST_BILL";
    public static final String TAG_LIST_CATEGORY = "TAG_LIST_CATEGORY";
    public static final String TAG_HOME_FRAGMENT = "TAG_HOME_FRAGMENT";
    public static final String TAG_REPORT_FRAGMENT = "TAG_REPORT_FRAGMENT";
    public static final String TAG_MANAGE_FRAGMENT = "TAG_MANAGE_FRAGMENT";
    public static final String TAG_LIST_INCIDENTAL = "TAG_LIST_INCIDENTAL";
    public static final String TAG_ADVICE_FRAGMENT = "TAG_ADVICE_FRAGMENT";
    public static final String TAG_CATCHER_FRAGMENT = "TAG_CATCHER_FRAGMENT";
    public static final String TAG_SETTINGS_FRAGMENT = "TAG_SETTINGS_FRAGMENT";
    public static final String TAG_ADD_BILL_FRAGMENT = "TAG_ADD_BILL_FRAGMENT";
    public static final String TAG_EDIT_BILL_FRAGMENT = "TAG_EDIT_BILL_FRAGMENT";
    public static final String TAG_BASIC_INFO_FRAGMENT = "TAG_BASIC_INFO_FRAGMENT";
    public static final String TAG_PROFILE_INFO_FRAGMENT = "TAG_PROFILE_INFO_FRAGMENT";
    public static final String TAG_ADD_ALLOWANCE_FRAGMENT = "TAG_ADD_ALLOWANCE_FRAGMENT";
    public static final String TAG_EDIT_ALLOWANCE_FRAGMENT = "TAG_EDIT_ALLOWANCE_FRAGMENT";
    public static final String TAG_ADD_INCIDENTAL_FRAGMENT = "TAG_ADD_INCIDENTAL_FRAGMENT";
    public static final String TAG_LIST_SPENDING_ALLOWANCE = "TAG_LIST_SPENDING_ALLOWANCE";

    public static final String KEY_SERIALIZABLE = "KEY_SERIALIZABLE";

    public static final String CATEGORY_ALLOWANCE_TAG_ID = "1";
    public static final String CATEGORY_BILL_TAG_ID = "2";
    public static final String WEEK_TAG_ID = "1";
    public static final String MONTH_TAG_ID = "2";

    /*retrofit okHttp timeout values*/
    public static final int readTimeout = 60;
    public static final int writeTimeout = 60;
    public static final int connectTimeout = 60;

    /*retrofit okHttp timeout unit*/
    public static final TimeUnit timeOutUnit = TimeUnit.SECONDS;
}
