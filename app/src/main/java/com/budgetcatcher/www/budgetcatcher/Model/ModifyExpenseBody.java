package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModifyExpenseBody {

    @SerializedName("expenseName")
    @Expose
    private String expenseName;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("expenseDescription")
    @Expose
    private String expenseDescription;
    @SerializedName("dateTime")
    @Expose
    private String dateTime;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;

    /**
     * No args constructor for use in serialization
     */
    public ModifyExpenseBody() {
    }

    /**
     * @param amount
     * @param dateTime
     * @param expenseName
     * @param categoryId
     * @param month
     * @param year
     * @param expenseDescription
     */
    public ModifyExpenseBody(String expenseName, String categoryId, String amount, String expenseDescription, String dateTime, String month, String year) {
        super();
        this.expenseName = expenseName;
        this.categoryId = categoryId;
        this.amount = amount;
        this.expenseDescription = expenseDescription;
        this.dateTime = dateTime;
        this.month = month;
        this.year = year;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}