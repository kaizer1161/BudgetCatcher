package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BudgetStatusResponseBody {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<StatusOfBudget> data = null;

    /**
     * No args constructor for use in serialization
     */
    public BudgetStatusResponseBody() {
    }

    /**
     * @param status
     * @param data
     */
    public BudgetStatusResponseBody(String status, List<StatusOfBudget> data) {
        super();
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<StatusOfBudget> getData() {
        return data;
    }

    public void setData(List<StatusOfBudget> data) {
        this.data = data;
    }

}