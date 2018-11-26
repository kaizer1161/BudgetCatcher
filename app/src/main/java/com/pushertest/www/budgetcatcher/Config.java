package com.pushertest.www.budgetcatcher;

import java.util.concurrent.TimeUnit;

public class Config {

    public static final String TAG_HOME_FRAGMENT = "TAG_HOME_FRAGMENT";
    public static final String TAG_CATCHER_FRAGMENT = "TAG_CATCHER_FRAGMENT";
    public static final String TAG_MANAGE_FRAGMENT = "TAG_MANAGE_FRAGMENT";
    public static final String TAG_ADVICE_FRAGMENT = "TAG_ADVICE_FRAGMENT";
    public static final String TAG_ADD_BILL_FRAGMENT = "TAG_ADD_BILL_FRAGMENT";
    public static final String TAG_ADD_ALLOWANCE_FRAGMENT = "TAG_ADD_ALLOWANCE_FRAGMENT";
    public static final String TAG_REPORT_FRAGMENT = "TAG_REPORT_FRAGMENT";

    public static final String TAG_LIST_SPENDING_ALLOWANCE = "TAG_LIST_SPENDING_ALLOWANCE";

    /*retrofit okHttp timeout values*/
    public static final int connectTimeout = 60;
    public static final int writeTimeout = 60;
    public static final int readTimeout = 60;

    /*retrofit okHttp timeout unit*/
    public static final TimeUnit timeOutUnit = TimeUnit.SECONDS;
}
