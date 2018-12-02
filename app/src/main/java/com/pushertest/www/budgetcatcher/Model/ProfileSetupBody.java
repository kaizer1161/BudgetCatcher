package com.pushertest.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileSetupBody implements Serializable {

    @SerializedName("profilePicUrl")
    @Expose
    private String profilePicUrl;
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
     *
     * @param skillLevel
     * @param riskLevel
     * @param financialGoal
     * @param profilePicUrl
     */
    public ProfileSetupBody(String profilePicUrl, String riskLevel, String skillLevel, String financialGoal) {
        super();
        this.profilePicUrl = profilePicUrl;
        this.riskLevel = riskLevel;
        this.skillLevel = skillLevel;
        this.financialGoal = financialGoal;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
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