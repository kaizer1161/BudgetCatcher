package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertIncomeBody {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("frequency")
    @Expose
    private String frequency;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("nextPayDay")
    @Expose
    private String nextPayDay;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("provider")
    @Expose
    private String provider;

    /**
     * No args constructor for use in serialization
     */
    public InsertIncomeBody() {
    }

    /**
     * @param amount
     * @param source
     * @param userId
     * @param provider
     * @param frequency
     * @param nextPayDay
     */
    public InsertIncomeBody(String userId, String frequency, String amount, String nextPayDay, String source, String provider) {
        super();
        this.userId = userId;
        this.frequency = frequency;
        this.amount = amount;
        this.nextPayDay = nextPayDay;
        this.source = source;
        this.provider = provider;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNextPayDay() {
        return nextPayDay;
    }

    public void setNextPayDay(String nextPayDay) {
        this.nextPayDay = nextPayDay;
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

}