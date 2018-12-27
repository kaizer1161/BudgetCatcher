package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpenseData {

    @SerializedName("expenseTotal")
    @Expose
    private String expenseTotal;

    /**
     * No args constructor for use in serialization
     */
    public ExpenseData() {
    }

    /**
     * @param expenseTotal
     */
    public ExpenseData(String expenseTotal) {
        super();
        this.expenseTotal = expenseTotal;
    }

    public String getExpenseTotal() {
        return expenseTotal;
    }

    public void setExpenseTotal(String expenseTotal) {
        this.expenseTotal = expenseTotal;
    }

}
