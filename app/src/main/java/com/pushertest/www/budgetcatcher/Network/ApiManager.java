package com.pushertest.www.budgetcatcher.Network;

/**
 * API manager class, it initializes retrofit with baseUrl and timeout at application onCreate.
 * API manager also manages all APIs like createAccount, login etc.
 *
 * @author Mir Rayan
 * @since may - 2018.
 */

import com.pushertest.www.budgetcatcher.Config;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiManager {

    private static final String TAG = "Api Manager";
    private static ApiManager apiManager;
    private static ApiInterface apiInterface;

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
                .build().create(ApiInterface.class);

    }

    public static ApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public void checkPrice(String weight, String distance, final QueryCallback<String> callback) {

        Map<String, String> headers = new HashMap<String, String>();

        headers.put(URL.API_KEY_WEIGHT, weight);
        headers.put(URL.API_KEY_DISTANCE, distance);

        Call<String> networkCall = apiInterface.checkPrice(headers);
        networkCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                callback.onSuccess(response.body());

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

}
