package com.pushertest.www.budgetcatcher.Network;

public class URL {

    /*Api base url*/
    public static final String base = "http://www.budget-catcher.com/api/";

    /*URL status code*/
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_SERVER_NOT_FOUND = 404;
    public static final int STATUS_SERVER_INTERNAL_ERROR = 500;
    public static final int STATUS_SERVER_RESPONSE_OK = 200;
    public static final int STATUS_SERVER_CREATED = 201;

    /*URL Suffix*/
    public static final String signUp = "insertUser";
    public static final String profileSetup = "modifyUser/";
    public static final String signIn = "checkUser/";
    public static final String getAllBill = "getAllBills/";
    public static final String getAllAllowance = "getAllAllowances/";
    public static final String getAllCategory = "getAllCategory/";
    public static final String insertBill = "insertBill";
    public static final String insertAllowances = "insertAllowances";

    /*URL key value*/
    public static final String value_Content_Type = "application/json";

    /*API key*/
    public static final String API_KEY_DATA = "data";
    public static final String API_KEY_USER_ID = "userId";

    /*URL key */
    public static final String key_content_Type = "Content_Type";


}
