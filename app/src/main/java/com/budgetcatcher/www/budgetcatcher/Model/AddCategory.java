package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCategory {

    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("tagId")
    @Expose
    private String tagId;
    @SerializedName("userId")
    @Expose
    private String userId;

    /**
     * No args constructor for use in serialization
     */
    public AddCategory() {
    }

    /**
     * @param categoryName
     * @param userId
     * @param tagId
     */
    public AddCategory(String categoryName, String tagId, String userId) {
        super();
        this.categoryName = categoryName;
        this.tagId = tagId;
        this.userId = userId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}