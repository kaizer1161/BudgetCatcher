package com.pushertest.www.budgetcatcher.Network;

/**
 * API interface for price check.
 *
 * @author Mir Rayan
 * @since june-2018
 */

import com.pushertest.www.budgetcatcher.Model.ProfileSetupBody;
import com.pushertest.www.budgetcatcher.Model.SignUpBody;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiInterface {

    @POST(URL.signUp)
    Call<String> signUp(@HeaderMap Map<String, String> headers, @Body SignUpBody signUpBody);

    @POST
    Call<String> profileSetup(@Url String url, @HeaderMap Map<String, String> headers, @Body ProfileSetupBody profileSetupBody);

}
