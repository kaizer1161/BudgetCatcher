package com.budgetcatcher.www.budgetcatcher.Network;

/**
 * API manager class, it initializes retrofit with baseUrl and timeout at application onCreate.
 * API manager also manages all APIs like createAccount, login etc.
 *
 * @author Mir Rayan
 * @since Nov - 2018.
 */


import android.util.Log;

import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.AddCategory;
import com.budgetcatcher.www.budgetcatcher.Model.AddOutstandingCheckBody;
import com.budgetcatcher.www.budgetcatcher.Model.Allowance;
import com.budgetcatcher.www.budgetcatcher.Model.AllowanceResponse;
import com.budgetcatcher.www.budgetcatcher.Model.Bill;
import com.budgetcatcher.www.budgetcatcher.Model.BillResponse;
import com.budgetcatcher.www.budgetcatcher.Model.CatcherResponse;
import com.budgetcatcher.www.budgetcatcher.Model.Category;
import com.budgetcatcher.www.budgetcatcher.Model.CategoryResponse;
import com.budgetcatcher.www.budgetcatcher.Model.Cobrand;
import com.budgetcatcher.www.budgetcatcher.Model.CobrandLoginBody;
import com.budgetcatcher.www.budgetcatcher.Model.Expenses;
import com.budgetcatcher.www.budgetcatcher.Model.ExpensesResponse;
import com.budgetcatcher.www.budgetcatcher.Model.GetUserInfo;
import com.budgetcatcher.www.budgetcatcher.Model.Home;
import com.budgetcatcher.www.budgetcatcher.Model.HomeResponse;
import com.budgetcatcher.www.budgetcatcher.Model.Income;
import com.budgetcatcher.www.budgetcatcher.Model.IncomeResponse;
import com.budgetcatcher.www.budgetcatcher.Model.InsertAllowanceBody;
import com.budgetcatcher.www.budgetcatcher.Model.InsertBillBody;
import com.budgetcatcher.www.budgetcatcher.Model.InsertExpensesBody;
import com.budgetcatcher.www.budgetcatcher.Model.InsertIncomeBody;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyAllowanceBody;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyBillBody;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyCategory;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyExpenseBody;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyHomeBody;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyIncomeBody;
import com.budgetcatcher.www.budgetcatcher.Model.PieChartData;
import com.budgetcatcher.www.budgetcatcher.Model.PieChartResponse;
import com.budgetcatcher.www.budgetcatcher.Model.ProfileSetupBody;
import com.budgetcatcher.www.budgetcatcher.Model.SignUpBody;
import com.budgetcatcher.www.budgetcatcher.Model.User;
import com.budgetcatcher.www.budgetcatcher.Model.YodleeCreateManualAccountBody;
import com.budgetcatcher.www.budgetcatcher.Model.YodleeUserLoginBody;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiManager {

    private static final String TAG = "Api Manager";
    private static ApiManager apiManager;
    private static ApiInterface apiInterface;

    private Map<String, String> headers;

    private ApiManager() {

        /*HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!*/

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Config.connectTimeout, Config.timeOutUnit)
                .writeTimeout(Config.writeTimeout, Config.timeOutUnit)
                .readTimeout(Config.readTimeout, Config.timeOutUnit)
                .build();

        apiInterface = new Retrofit.Builder()
                .baseUrl(URL.base)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiInterface.class);

        headers = new HashMap<>();

        headers.put(URL.key_content_Type, URL.value_Content_Type);

    }

    public static ApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public void userSignUp(SignUpBody body, final QueryCallback<Response<String>> callback) {

        Call<String> networkCall = apiInterface.signUp(headers, body);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_CREATED || response.code() == URL.STATUS_BAD_REQUEST) {

                    callback.onSuccess(response);

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void userSignIn(String email, String password, final QueryCallback<String> callback) {

        String uri = URL.base + URL.signIn + email + "/" + password;

        Call<String> networkCall = apiInterface.signIn(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFail();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void getWeekBreakDown(String weekId, final QueryCallback<String> callback) {

        String uri = URL.base + URL.getWeeksOrMonths + weekId;

        Call<String> networkCall = apiInterface.getWeeksOrMonths(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {
                    callback.onFail();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void getMonthBreakDown(String monthId, final QueryCallback<String> callback) {

        String uri = URL.base + URL.getWeeksOrMonths + monthId;

        Call<String> networkCall = apiInterface.getCurrentWeeksOrMonths(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {
                    callback.onFail();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void getCurrentWeek(String weekId, final QueryCallback<String> callback) {

        String uri = URL.base + URL.getCurrentWeekOrMonth + weekId;

        Call<String> networkCall = apiInterface.getWeeksOrMonths(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {
                    callback.onFail();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void getCurrentMonth(String monthId, final QueryCallback<String> callback) {

        String uri = URL.base + URL.getCurrentWeekOrMonth + monthId;

        Call<String> networkCall = apiInterface.getWeeksOrMonths(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {
                    callback.onFail();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void userProfileSetup(String userID, ProfileSetupBody profileSetupBody, final QueryCallback<String> callback) {

        String uri = URL.base + URL.profileSetup + userID;

        Gson gson = new Gson();

        Call<String> networkCall = apiInterface.profileSetup(uri, headers, gson.toJson(profileSetupBody));
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void userProfileUpdate(String userID, User body, final QueryCallback<String> callback) {

        String uri = URL.base + URL.profileSetup + userID;
        Call<String> networkCall = apiInterface.profileUpdate(uri, headers, body);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void getBill(String userId, final QueryCallback<ArrayList<Bill>> callback) {

        String uri = URL.base + URL.getAllBill + userId;

        Call<String> networkCall = apiInterface.getBill(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    Gson gson = new Gson();
                    BillResponse billResponse = gson.fromJson(response.body(), BillResponse.class);

                    callback.onSuccess((ArrayList<Bill>) billResponse.getBills());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void getPieChart(String userId, final QueryCallback<ArrayList<PieChartData>> callback) {

        String uri = URL.base + URL.pieChart + userId;

        Call<String> networkCall = apiInterface.pieChart(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    Gson gson = new Gson();
                    PieChartResponse pieChartResponse = gson.fromJson(response.body(), PieChartResponse.class);

                    callback.onSuccess((ArrayList<PieChartData>) pieChartResponse.getData());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void getCatcher(String userId, String startDate, String endDate, final QueryCallback<CatcherResponse> callback) {

        String uri = URL.base + URL.getCatcher + userId + "/" + startDate + "/" + endDate;

        Call<String> networkCall = apiInterface.getCatcher(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    Gson gson = new Gson();
                    CatcherResponse catcherResponse = gson.fromJson(response.body(), CatcherResponse.class);

                    callback.onSuccess(catcherResponse);

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void getHome(String userId, String startDate, String endDate, final QueryCallback<Home> callback) {

        String uri = URL.base + URL.getHome + userId + "/" + startDate + "/" + endDate;

        Call<String> networkCall = apiInterface.getHome(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    Gson gson = new Gson();
                    HomeResponse homeResponse = gson.fromJson(response.body(), HomeResponse.class);

                    callback.onSuccess(homeResponse.getHome());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void getIncome(String userId, final QueryCallback<ArrayList<Income>> callback) {

        String uri = URL.base + URL.getAllIncome + userId;

        Call<String> networkCall = apiInterface.getIncome(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    Gson gson = new Gson();
                    IncomeResponse incomeResponse = gson.fromJson(response.body(), IncomeResponse.class);

                    callback.onSuccess((ArrayList<Income>) incomeResponse.getIncomes());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void getUserInfo(String userId, final QueryCallback<ArrayList<User>> callback) {

        String uri = URL.base + URL.getUserInfo + userId;

        Call<String> networkCall = apiInterface.getUserInfo(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    Gson gson = new Gson();
                    GetUserInfo getUserInfo = gson.fromJson(response.body(), GetUserInfo.class);

                    callback.onSuccess((ArrayList<User>) getUserInfo.getUser());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void getAllowance(String userId, final QueryCallback<ArrayList<Allowance>> callback) {

        String uri = URL.base + URL.getAllAllowance + userId;

        Call<String> networkCall = apiInterface.getAllowance(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    Gson gson = new Gson();
                    AllowanceResponse allowanceResponse = gson.fromJson(response.body(), AllowanceResponse.class);

                    callback.onSuccess((ArrayList<Allowance>) allowanceResponse.getAllowance());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void getCategory(String tagId, String userId, final QueryCallback<ArrayList<Category>> callback) {

        String uri = URL.base + URL.getAllCategory + tagId + "/" + userId;

        Call<String> networkCall = apiInterface.getCategory(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    Gson gson = new Gson();
                    CategoryResponse categoryResponse = gson.fromJson(response.body(), CategoryResponse.class);

                    callback.onSuccess((ArrayList<Category>) categoryResponse.getCategory());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void addCategory(AddCategory body, final QueryCallback<String> callback) {

        Call<String> networkCall = apiInterface.addCategory(headers, body);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_CREATED) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void modifyCategory(String userId, String categoryId, ModifyCategory body, final QueryCallback<String> callback) {

        String uri = URL.base + URL.modifyCategory + userId + "/" + categoryId;

        Call<String> networkCall = apiInterface.modifyCategory(uri, headers, body);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void modifyIncome(String userId, String incomeId, ModifyIncomeBody body, final QueryCallback<String> callback) {

        String uri = URL.base + URL.modifyIncome + userId + "/" + incomeId;

        Call<String> networkCall = apiInterface.modifyIncome(uri, headers, body);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void insertBill(InsertBillBody body, final QueryCallback<String> callback) {

        Call<String> networkCall = apiInterface.insertBill(headers, body);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_CREATED) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void insertIncome(InsertIncomeBody body, final QueryCallback<String> callback) {

        Call<String> networkCall = apiInterface.insertIncome(headers, body);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_CREATED) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void insertExpenses(InsertExpensesBody body, final QueryCallback<String> callback) {

        Call<String> networkCall = apiInterface.insertExpense(headers, body);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_CREATED) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void insertAllowance(InsertAllowanceBody body, final QueryCallback<String> callback) {

        Call<String> networkCall = apiInterface.insertAllowances(headers, body);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_CREATED) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void addOC(AddOutstandingCheckBody body, final QueryCallback<String> callback) {

        Call<String> networkCall = apiInterface.addOC(headers, body);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_CREATED) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void deleteBill(String userId, String billId, final QueryCallback<String> callback) {

        String uri = URL.base + URL.deleteBills + userId + "/" + billId;

        Call<String> networkCall = apiInterface.deleteBill(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void deleteIncome(String userId, String incomeId, final QueryCallback<String> callback) {

        String uri = URL.base + URL.deleteIncome + userId + "/" + incomeId;

        Call<String> networkCall = apiInterface.deleteIncome(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void deleteAllowance(String userId, String allowanceId, final QueryCallback<String> callback) {

        String uri = URL.base + URL.deleteAllowance + userId + "/" + allowanceId;

        Call<String> networkCall = apiInterface.deleteAllowance(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void deleteExpense(String userId, String expenseId, final QueryCallback<String> callback) {

        String uri = URL.base + URL.deleteExpense + userId + "/" + expenseId;

        Call<String> networkCall = apiInterface.deleteExpense(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void deleteCategory(String categoryId, final QueryCallback<String> callback) {

        String uri = URL.base + URL.deleteCategory + categoryId;

        Call<String> networkCall = apiInterface.deleteCategory(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void modifyBill(String userId, String billId, ModifyBillBody body, final QueryCallback<String> callback) {

        String uri = URL.base + URL.modifyBill + userId + "/" + billId;

        Call<String> networkCall = apiInterface.modifyBill(uri, headers, body);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void modifyAllowance(String userId, String allowanceID, ModifyAllowanceBody body, final QueryCallback<String> callback) {

        String uri = URL.base + URL.modifyAllowance + userId + "/" + allowanceID;

        Call<String> networkCall = apiInterface.modifyAllowance(uri, headers, body);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void modifyHome(String userId, String startDate, String endDate, ModifyHomeBody body, final QueryCallback<String> callback) {

        String uri = URL.base + URL.modifyHome + userId + "/" + startDate + "/" + endDate;

        Log.d(TAG, "modifyHome: " + uri);

        Call<String> networkCall = apiInterface.modifyHome(uri, headers, body);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d(TAG, "onResponse: " + response.code());
                Log.d(TAG, "onResponse: " + response.body());

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void modifyExpense(String userId, String expenseId, ModifyExpenseBody body, final QueryCallback<String> callback) {

        String uri = URL.base + URL.modifyExpense + userId + "/" + expenseId;

        Call<String> networkCall = apiInterface.modifyExpense(uri, headers, body);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void getExpenses(String userId, String month, String year, final QueryCallback<ArrayList<Expenses>> callback) {

        String uri = URL.base + URL.getAllExpenses + userId + "/" + month + "/" + year;

        Call<String> networkCall = apiInterface.getExpenses(uri);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    Gson gson = new Gson();
                    ExpensesResponse expensesResponse = gson.fromJson(response.body(), ExpensesResponse.class);

                    callback.onSuccess((ArrayList<Expenses>) expensesResponse.getExpenses());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    /*Yodlee APIs*/
    public void cobrandLogin(final QueryCallback<String> callback) {

        String uri = URL.yodleeBase + URL.cobrandLogin;

        Map<String, String> cobrandHeaders = new HashMap<>();
        cobrandHeaders.put(URL.key_content_Type, URL.value_Content_Type);
        cobrandHeaders.put(URL.key_Api_Version, URL.value_Api_Version);
        cobrandHeaders.put(URL.key_Cobrand_Name, URL.value_Cobrand_Name);

        Cobrand cobrand = new Cobrand(URL.value_Cobrand_login, URL.value_Cobrand_password, URL.value_Local_language);
        CobrandLoginBody cobrandLoginBody = new CobrandLoginBody(cobrand);

        Call<String> networkCall = apiInterface.cobrandLogin(uri, cobrandHeaders, cobrandLoginBody);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void yodleeUserLogin(String session, final String loginName, final String loginPassword, final QueryCallback<String> callback) {

        String uri = URL.yodleeBase + URL.yodleeUserLogin;

        Map<String, String> yodleeHeaders = new HashMap<>();
        yodleeHeaders.put(URL.key_content_Type, URL.value_Content_Type);
        yodleeHeaders.put(URL.key_Api_Version, URL.value_Api_Version);
        yodleeHeaders.put(URL.key_Cobrand_Name, URL.value_Cobrand_Name);
        yodleeHeaders.put(URL.key_Yodlee_Authorization, URL.key_Yodlee_cobSession + session);

        YodleeUserLoginBody yodleeUserLoginBody = new YodleeUserLoginBody(new YodleeUserLoginBody.User(loginName, loginPassword, URL.value_Local_language));

        Call<String> networkCall = apiInterface.yodleeUserLogin(uri, yodleeHeaders, yodleeUserLoginBody);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void yodleeGetAccount(String session, final QueryCallback<String> callback) {

        String uri = URL.yodleeBase + URL.yodleeGetAccount;

        Map<String, String> yodleeHeaders = new HashMap<>();
        yodleeHeaders.put(URL.key_Api_Version, URL.value_Api_Version);
        yodleeHeaders.put(URL.key_Cobrand_Name, URL.value_Cobrand_Name);
        yodleeHeaders.put(URL.key_Yodlee_Authorization, session);

        Call<String> networkCall = apiInterface.yodleeGetAccount(uri, yodleeHeaders);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d(TAG, "onResponse: " + response.body());

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void yodleeFastLink(String session, final QueryCallback<String> callback) {

        String uri = URL.yodleeBase + URL.yodleeFastLink;

        Map<String, String> yodleeHeaders = new HashMap<>();
        yodleeHeaders.put(URL.key_Api_Version, URL.value_Api_Version);
        yodleeHeaders.put(URL.key_Cobrand_Name, URL.value_Cobrand_Name);
        yodleeHeaders.put(URL.key_Yodlee_Authorization, session);

        Call<String> networkCall = apiInterface.yodleeFastLink(uri, yodleeHeaders);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_RESPONSE_OK) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

    public void yodleeCrateAccountManually(String session, YodleeCreateManualAccountBody yodleeCreateManualAccountBody, final QueryCallback<String> callback) {

        String uri = URL.yodleeBase + URL.yodleeAddAccoutManually;

        Map<String, String> yodleeHeaders = new HashMap<>();
        yodleeHeaders.put(URL.key_content_Type, URL.value_Content_Type);
        yodleeHeaders.put(URL.key_Api_Version, URL.value_Api_Version);
        yodleeHeaders.put(URL.key_Cobrand_Name, URL.value_Cobrand_Name);
        yodleeHeaders.put(URL.key_Yodlee_Authorization, session);

        Call<String> networkCall = apiInterface.yodleeCreateAccountManually(uri, yodleeHeaders, yodleeCreateManualAccountBody);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == URL.STATUS_SERVER_CREATED) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onError(t);

            }
        });

    }

}
