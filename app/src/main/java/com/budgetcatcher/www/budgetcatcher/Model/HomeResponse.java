package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Home home;

    /**
     * No args constructor for use in serialization
     */
    public HomeResponse() {
    }

    /**
     * @param status
     * @param home
     */
    public HomeResponse(String status, Home home) {
        super();
        this.status = status;
        this.home = home;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

}