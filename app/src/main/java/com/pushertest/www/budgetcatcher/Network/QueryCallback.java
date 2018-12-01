package com.pushertest.www.budgetcatcher.Network;

/**
 * Created by Arif Khan on 12/24/2017.
 */

public interface QueryCallback<T> {
    // Callback, Success
    void onSuccess(T data);

    void onFail(T data);

    // Callback, Error
    void onError(Throwable th);
}
