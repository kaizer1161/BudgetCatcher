package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModifyHomeBody {

    @SerializedName("endBalance")
    @Expose
    private String endBalance;
    @SerializedName("savings")
    @Expose
    private String savings;
    @SerializedName("debts")
    @Expose
    private String debts;

    /**
     * No args constructor for use in serialization
     */
    public ModifyHomeBody() {
    }

    /**
     * @param debts
     * @param endBalance
     * @param savings
     */
    public ModifyHomeBody(String endBalance, String savings, String debts) {
        super();
        this.endBalance = endBalance;
        this.savings = savings;
        this.debts = debts;
    }

    public String getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(String endBalance) {
        this.endBalance = endBalance;
    }

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }

    public String getDebts() {
        return debts;
    }

    public void setDebts(String debts) {
        this.debts = debts;
    }

}