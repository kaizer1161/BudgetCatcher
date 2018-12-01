package com.pushertest.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Allowance {

    @SerializedName("allowanceId")
    @Expose
    private String allowanceId;
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
    @SerializedName("allowanceName")
    @Expose
    private String allowanceName;

    /**
     * @param description
     * @param categoryId
     * @param userId
     * @param allowanceId
     * @param allowanceAmount
     * @param allowanceName
     */
    public Allowance(String allowanceId, String userId, String categoryId, String allowanceAmount, String description, String allowanceName) {
        super();
        this.allowanceId = allowanceId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.allowanceAmount = allowanceAmount;
        this.description = description;
        this.allowanceName = allowanceName;
    }

    public String getAllowanceId() {
        return allowanceId;
    }

    public void setAllowanceId(String allowanceId) {
        this.allowanceId = allowanceId;
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

    public String getAllowanceName() {
        return allowanceName;
    }

    public void setAllowanceName(String allowanceName) {
        this.allowanceName = allowanceName;
    }

}
