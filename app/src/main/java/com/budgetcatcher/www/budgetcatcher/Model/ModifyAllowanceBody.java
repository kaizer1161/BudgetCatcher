package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModifyAllowanceBody {

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
     * No args constructor for use in serialization
     */
    public ModifyAllowanceBody() {
    }

    /**
     * @param description
     * @param categoryId
     * @param allowanceAmount
     * @param allowanceName
     */
    public ModifyAllowanceBody(String categoryId, String allowanceAmount, String description, String allowanceName) {
        super();
        this.categoryId = categoryId;
        this.allowanceAmount = allowanceAmount;
        this.description = description;
        this.allowanceName = allowanceName;
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