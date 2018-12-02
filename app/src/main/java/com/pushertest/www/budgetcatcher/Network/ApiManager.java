package com.pushertest.www.budgetcatcher.Network;

/**
 * API manager class, it initializes retrofit with baseUrl and timeout at application onCreate.
 * API manager also manages all APIs like createAccount, login etc.
 *
 * @author Mir Rayan
 * @since Nov - 2018.
 */


import android.util.Log;

import com.google.gson.Gson;
import com.pushertest.www.budgetcatcher.Config;
import com.pushertest.www.budgetcatcher.Model.Allowance;
import com.pushertest.www.budgetcatcher.Model.AllowanceResponse;
import com.pushertest.www.budgetcatcher.Model.Bill;
import com.pushertest.www.budgetcatcher.Model.BillResponse;
import com.pushertest.www.budgetcatcher.Model.Category;
import com.pushertest.www.budgetcatcher.Model.CategoryResponse;
import com.pushertest.www.budgetcatcher.Model.InsertAllowanceBody;
import com.pushertest.www.budgetcatcher.Model.InsertBillBody;
import com.pushertest.www.budgetcatcher.Model.ProfileSetupBody;
import com.pushertest.www.budgetcatcher.Model.SignUpBody;

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

    public void userSignUp(SignUpBody body, final QueryCallback<String> callback) {

        Call<String> networkCall = apiInterface.signUp(headers, body);
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

    public void userProfileSetup(String userID, ProfileSetupBody profileSetupBody, final QueryCallback<String> callback) {

        String uri = URL.base + URL.profileSetup + userID;
        Call<String> networkCall = apiInterface.profileSetup(uri, headers, profileSetupBody);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                Log.d(TAG, "onResponse user profile: " + response.code());

                /*if (response.code() == URL.STATUS_SERVER_CREATED) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onFail(response.body());

                }*/

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

    public void getCategory(final QueryCallback<ArrayList<Category>> callback) {

        Call<String> networkCall = apiInterface.getCategory();
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

}
