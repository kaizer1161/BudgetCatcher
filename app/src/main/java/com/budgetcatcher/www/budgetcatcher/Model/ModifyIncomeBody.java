package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModifyIncomeBody {

    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("frequency")
    @Expose
    private String frequency;
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
    public ModifyIncomeBody() {
    }

    /**
     * @param amount
     * @param source
     * @param provider
     * @param frequency
     * @param nextPayDay
     */
    public ModifyIncomeBody(String amount, String frequency, String nextPayDay, String source, String provider) {
        super();
        this.amount = amount;
        this.frequency = frequency;
        this.nextPayDay = nextPayDay;
        this.source = source;
        this.provider = provider;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
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