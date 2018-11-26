package com.pushertest.www.budgetcatcher.Network;

/**
 * API interface for price check.
 *
 * @author Mir Rayan
 * @since june-2018
 */

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST(URL.checkPrice)
    Call<String> checkPrice(@HeaderMap Map<String, String> headers);

}
