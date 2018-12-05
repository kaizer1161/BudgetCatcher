package com.pushertest.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpensesResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Expenses> expenses = null;

    /**
     * No args constructor for use in serialization
     */
    public ExpensesResponse() {
    }

    /**
     * @param status
     * @param expenses
     */
    public ExpensesResponse(String status, List<Expenses> expenses) {
        super();
        this.status = status;
        this.expenses = expenses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Expenses> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expenses> expenses) {
        this.expenses = expenses;
    }

}