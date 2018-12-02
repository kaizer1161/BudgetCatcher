package com.pushertest.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertAllowanceBody {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("allowanceAmount")
    @Expose
    private String allowanceAmount;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * @param description
     * @param categoryId
     * @param userId
     * @param allowanceAmount
     */
    public InsertAllowanceBody(String userId, String categoryId, String allowanceAmount, String description) {
        super();
        this.userId = userId;
        this.categoryId = categoryId;
        this.allowanceAmount = allowanceAmount;
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getAllowanceAmount() {
        return allowanceAmount;
    }

    public void setAllowanceAmount(String allowanceAmount) {
        this.allowanceAmount = allowanceAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}