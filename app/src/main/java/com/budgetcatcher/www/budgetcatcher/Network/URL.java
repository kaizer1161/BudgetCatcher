package com.budgetcatcher.www.budgetcatcher.Network;

public class URL {

    /*Api base url*/
    //public static final String base = "http://www.budget-catcher.com/api/";
    public static final String base = "http://bc.biniyogbondhu.com/api/";
    /*Yodlee base URL*/
    public static final String yodleeBase = "https://developer.api.yodlee.com/ysl/";

    /*URL status code*/
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_SERVER_CREATED = 201;
    public static final int STATUS_SERVER_NOT_FOUND = 404;
    public static final int STATUS_SERVER_RESPONSE_OK = 200;
    public static final int STATUS_SERVER_INTERNAL_ERROR = 500;

    /*URL Suffix*/
    public static final String addOC = "addOC";
    public static final String getHome = "getHome/";
    public static final String signIn = "checkUser/";
    public static final String signUp = "insertUser";
    public static final String modifyOC = "modifyOC/";
    public static final String deleteOC = "deleteOC/";
    public static final String pieChart = "pieChart/";
    public static final String insertBill = "insertBill";
    public static final String modifyBill = "modifyBill/";
    public static final String getCatcher = "getCatcher/";
    public static final String modifyHome = "modifyHome/";
    public static final String getAllBill = "getAllBills/";
    public static final String addCategory = "addCategory";
    public static final String profileSetup = "modifyUser/";
    public static final String getUserInfo = "getUserInfo/";
    public static final String deleteBills = "deleteBills/";
    public static final String insertIncome = "insertIncome";
    public static final String deleteIncome = "deleteIncome/";
    public static final String getAllIncome = "getAllIncome/";
    public static final String cobrandLogin = "cobrand/login";
    public static final String modifyIncome = "modifyIncome/";
    public static final String yodleeUserLogin = "user/login";
    public static final String yodleeGetAccount = "accounts?";
    public static final String insertExpense = "insertExpense";
    public static final String modifyExpense = "modifyExpense/";
    public static final String deleteExpense = "deleteExpense/";
    public static final String modifyCategory = "modifyCategory/";
    public static final String getAllExpenses = "getAllExpenses/";
    public static final String deleteCategory = "deleteCategory/";
    public static final String getAllCategory = "getAllCategory/";
    public static final String getFastLinkView = "getFastLinkView/";
    public static final String yodleeAddAccoutManually = "accounts";
    public static final String deleteAllowance = "deleteAllowance/";
    public static final String modifyAllowance = "modifyAllowance/";
    public static final String getBudgetStatus = "getBudgetStatus/";
    public static final String getAllAllowance = "getAllAllowances/";
    public static final String insertAllowances = "insertAllowances";
    public static final String updateDataTables = "updateDataTables/";
    public static final String getWeeksOrMonths = "getWeeksOrMonths/";
    public static final String getOutstandingChecks = "getOutstandingChecks/";
    public static final String getCurrentWeekOrMonth = "getCurrentWeekOrMonth/";
    public static final String yodleeFastLink = "/user/accessTokens?appIds=10003600";

    /*URL key value*/
    public static final String value_Api_Version = "1.1";
    public static final String value_Local_language = "en_US";
    public static final String value_Cobrand_Name = "restserver";
    public static final String value_Content_Type = "application/json";
    public static final String value_Cobrand_login = "sbCobdd1dcc211512e9a5485650d4299722930a";
    public static final String value_Cobrand_password = "7fcf6aec-4c2f-41a3-959d-531353ffb64d";

    /*API key*/
    public static final String API_KEY_DATA = "data";
    public static final String API_KEY_USER_ID = "userId";

    /*URL key */
    public static final String key_Api_Version = "Api-Version";
    public static final String key_content_Type = "Content_Type";
    public static final String key_Cobrand_Name = "Cobrand-Name";
    public static final String key_Yodlee_Authorization = "Authorization";
    public static final String key_Yodlee_cobSession = "cobSession=";
    public static final String key_Yodlee_userSession = "userSession=";

}
