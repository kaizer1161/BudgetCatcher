package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Session {

    @SerializedName("cobSession")
    @Expose
    private String cobSession;

    /**
     * No args constructor for use in serialization
     */
    public Session() {
    }

    /**
     * @param cobSession
     */
    public Session(String cobSession) {
        super();
        this.cobSession = cobSession;
    }

    public String getCobSession() {
        return cobSession;
    }

    public void setCobSession(String cobSession) {
        this.cobSession = cobSession;
    }

}
