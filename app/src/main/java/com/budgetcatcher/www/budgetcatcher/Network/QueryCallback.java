package com.budgetcatcher.www.budgetcatcher.Network;

/**
 * Created by Arif Khan on 12/24/2017.
 */

public interface QueryCallback<T> {
    // Callback, Success
    void onSuccess(T data);

    // Callback failed
    void onFail();

    // Callback, Error
    void onError(Throwable th);
}
