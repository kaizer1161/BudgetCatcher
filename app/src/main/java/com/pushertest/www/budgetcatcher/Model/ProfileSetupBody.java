package com.pushertest.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileSetupBody {

    @SerializedName("base64String")
    @Expose
    private String base64String;
    @SerializedName("riskLevel")
    @Expose
    private String riskLevel;
    @SerializedName("skillLevel")
    @Expose
    private String skillLevel;
    @SerializedName("financialGoal")
    @Expose
    private String financialGoal;

    /**
     * No args constructor for use in serialization
     */
    public ProfileSetupBody() {
    }

    /**
     * @param skillLevel
     * @param riskLevel
     * @param financialGoal
     * @param base64String
     */
    public ProfileSetupBody(String base64String, String riskLevel, String skillLevel, String financialGoal) {
        super();
        this.base64String = base64String;
        this.riskLevel = riskLevel;
        this.skillLevel = skillLevel;
        this.financialGoal = financialGoal;
    }

    public String getBase64String() {
        return base64String;
    }

    public void setBase64String(String base64String) {
        this.base64String = base64String;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getFinancialGoal() {
        return financialGoal;
    }

    public void setFinancialGoal(String financialGoal) {
        this.financialGoal = financialGoal;
    }

}