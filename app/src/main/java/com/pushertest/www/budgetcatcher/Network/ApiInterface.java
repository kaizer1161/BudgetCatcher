package com.pushertest.www.budgetcatcher.Network;

/**
 * API interface for price check.
 *
 * @author Mir Rayan
 * @since june-2018
 */

import com.pushertest.www.budgetcatcher.Model.InsertAllowanceBody;
import com.pushertest.www.budgetcatcher.Model.InsertBillBody;
import com.pushertest.www.budgetcatcher.Model.ProfileSetupBody;
import com.pushertest.www.budgetcatcher.Model.SignUpBody;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiInterface {

    @POST(URL.signUp)
    Call<String> signUp(@HeaderMap Map<String, String> headers, @Body SignUpBody signUpBody);

    @POST
    Call<String> profileSetup(@Url String url, @HeaderMap Map<String, String> headers, @Body ProfileSetupBody profileSetupBody);

    @GET
    Call<String> signIn(@Url String url);

    @GET
    Call<String> getBill(@Url String url);

    @GET
    Call<String> getAllowance(@Url String url);

    @GET(URL.getAllCategory)
    Call<String> getCategory();

    @POST(URL.insertBill)
    Call<String> insertBill(@HeaderMap Map<String, String> headers, @Body InsertBillBody insertBillBody);

    @POST(URL.insertAllowances)
    Call<String> insertAllowances(@HeaderMap Map<String, String> headers, @Body InsertAllowanceBody insertAllowanceBody);


}
