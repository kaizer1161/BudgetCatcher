package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("categoryId")
    @Expose
    private String categoryId;
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
     *
     */
    public Category() {
    }

    /**
     *
     * @param categoryName
     * @param userId
     * @param categoryId
     * @param tagId
     */
    public Category(String categoryId, String categoryName, String tagId, String userId) {
        super();
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.tagId = tagId;
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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