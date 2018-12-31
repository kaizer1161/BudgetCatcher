package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PieChartData {

    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;
    @SerializedName("percentage")
    @Expose
    private String percentage;

    /**
     * No args constructor for use in serialization
     */
    public PieChartData() {
    }

    /**
     * @param category
     * @param percentage
     * @param totalAmount
     */
    public PieChartData(String category, String totalAmount, String percentage) {
        super();
        this.category = category;
        this.totalAmount = totalAmount;
        this.percentage = percentage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

}
