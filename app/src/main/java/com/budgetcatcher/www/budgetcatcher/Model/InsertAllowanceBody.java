package com.budgetcatcher.www.budgetcatcher.Model;

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
    @SerializedName("allowanceName")
    @Expose
    private String allowanceName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tag")
    @Expose
    private String tag;

    /**
     *
     * @param status
     * @param description
     * @param categoryId
     * @param userId
     * @param allowanceAmount
     * @param allowanceName
     * @param tag
     */
    public InsertAllowanceBody(String userId, String categoryId, String allowanceAmount, String description, String allowanceName, String status, String tag) {
        super();
        this.userId = userId;
        this.categoryId = categoryId;
        this.allowanceAmount = allowanceAmount;
        this.description = description;
        this.allowanceName = allowanceName;
        this.status = status;
        this.tag = tag;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}