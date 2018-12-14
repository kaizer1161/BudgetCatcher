package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModifyCategory {

    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("tagId")
    @Expose
    private String tagId;

    /**
     * No args constructor for use in serialization
     */
    public ModifyCategory() {
    }

    /**
     * @param categoryName
     * @param tagId
     */
    public ModifyCategory(String categoryName, String tagId) {
        super();
        this.categoryName = categoryName;
        this.tagId = tagId;
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

}