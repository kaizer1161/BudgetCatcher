package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Income {

    @SerializedName("incomeId")
    @Expose
    private String incomeId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("nextPayDay")
    @Expose
    private String nextPayDay;
    @SerializedName("frequency")
    @Expose
    private String frequency;

    /**
     * No args constructor for use in serialization
     */
    public Income() {
    }

    /**
     * @param updatedAt
     * @param amount
     * @param incomeId
     * @param source
     * @param createdAt
     * @param userId
     * @param provider
     * @param frequency
     * @param nextPayDay
     */
    public Income(String incomeId, String userId, String amount, String source, String provider, String updatedAt, String createdAt, String nextPayDay, String frequency) {
        super();
        this.incomeId = incomeId;
        this.userId = userId;
        this.amount = amount;
        this.source = source;
        this.provider = provider;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.nextPayDay = nextPayDay;
        this.frequency = frequency;
    }

    public String getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(String incomeId) {
        this.incomeId = incomeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getNextPayDay() {
        return nextPayDay;
    }

    public void setNextPayDay(String nextPayDay) {
        this.nextPayDay = nextPayDay;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

}
