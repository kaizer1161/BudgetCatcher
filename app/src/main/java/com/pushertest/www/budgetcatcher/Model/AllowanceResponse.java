package com.pushertest.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllowanceResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Allowance> allowance = null;

    /**
     * No args constructor for use in serialization
     */
    public AllowanceResponse() {
    }

    /**
     * @param status
     * @param allowance
     */
    public AllowanceResponse(String status, List<Allowance> allowance) {
        super();
        this.status = status;
        this.allowance = allowance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Allowance> getAllowance() {
        return allowance;
    }

    public void setAllowance(List<Allowance> allowance) {
        this.allowance = allowance;
    }

}
