package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncomeResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Income> incomes = null;

    /**
     * No args constructor for use in serialization
     */
    public IncomeResponse() {
    }

    /**
     * @param status
     * @param incomes
     */
    public IncomeResponse(String status, List<Income> incomes) {
        super();
        this.status = status;
        this.incomes = incomes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

}