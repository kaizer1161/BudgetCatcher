package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModifyBillBody {

    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("billName")
    @Expose
    private String billName;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("dueDate")
    @Expose
    private String dueDate;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * No args constructor for use in serialization
     */
    public ModifyBillBody() {
    }

    /**
     * @param amount
     * @param billName
     * @param status
     * @param description
     * @param categoryId
     * @param dueDate
     */
    public ModifyBillBody(String categoryId, String billName, String amount, String description, String dueDate, String status) {
        super();
        this.categoryId = categoryId;
        this.billName = billName;
        this.amount = amount;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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