package com.pushertest.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Expenses {

    @SerializedName("expenseId")
    @Expose
    private String expenseId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("expenseName")
    @Expose
    private String expenseName;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("timeStamp")
    @Expose
    private String timeStamp;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("expenseDescription")
    @Expose
    private String expenseDescription;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("dateTime")
    @Expose
    private String dateTime;

    /**
     * No args constructor for use in serialization
     */
    public Expenses() {
    }

    /**
     * @param amount
     * @param timeStamp
     * @param dateTime
     * @param expenseName
     * @param categoryId
     * @param userId
     * @param month
     * @param year
     * @param expenseDescription
     * @param expenseId
     */
    public Expenses(String expenseId, String userId, String expenseName, String categoryId, String timeStamp, String amount, String expenseDescription, String month, String year, String dateTime) {
        super();
        this.expenseId = expenseId;
        this.userId = userId;
        this.expenseName = expenseName;
        this.categoryId = categoryId;
        this.timeStamp = timeStamp;
        this.amount = amount;
        this.expenseDescription = expenseDescription;
        this.month = month;
        this.year = year;
        this.dateTime = dateTime;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
