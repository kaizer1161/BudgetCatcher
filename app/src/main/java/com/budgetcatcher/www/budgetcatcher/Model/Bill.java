package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bill {

    @SerializedName("billId")
    @Expose
    private String billId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("category")
    @Expose
    private String categoryName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("dueDate")
    @Expose
    private String dueDate;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * @param amount
     * @param status
     * @param description
     * @param categoryName
     * @param userId
     * @param billId
     * @param dueDate
     */
    public Bill(String billId, String userId, String categoryName, String description, String amount, String dueDate, String status) {
        super();
        this.billId = billId;
        this.userId = userId;
        this.categoryName = categoryName;
        this.description = description;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}