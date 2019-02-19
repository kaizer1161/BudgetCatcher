package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Home {

    @SerializedName("startingBalance")
    @Expose
    private String startingBalance;
    @SerializedName("income")
    @Expose
    private String income;
    @SerializedName("expense")
    @Expose
    private String expense;
    @SerializedName("endingBalance")
    @Expose
    private String endingBalance;
    @SerializedName("savings")
    @Expose
    private String savings;
    @SerializedName("debts")
    @Expose
    private String debts;

    /**
     * No args constructor for use in serialization
     */
    public Home() {
    }

    /**
     *
     * @param expense
     * @param income
     * @param startingBalance
     * @param endingBalance
     */
    public Home(String startingBalance, String endingBalance, String income, String expense, String savings, String debts) {
        super();
        this.startingBalance = startingBalance;
        this.endingBalance = endingBalance;
        this.income = income;
        this.expense = expense;
        this.savings = savings;
        this.debts = debts;
    }

    public String getStartingBalance() {
        return startingBalance;
    }

    public void setStartingBalance(String startingBalance) {
        this.startingBalance = startingBalance;
    }

    public String getEndingBalance() {
        return endingBalance;
    }

    public void setEndingBalance(String endingBalance) {
        this.endingBalance = endingBalance;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
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
