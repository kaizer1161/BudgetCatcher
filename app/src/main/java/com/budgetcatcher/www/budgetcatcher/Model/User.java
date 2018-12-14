package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("userEmailId")
    @Expose
    private String userEmailId;
    @SerializedName("userPhoneNo")
    @Expose
    private String userPhoneNo;
    @SerializedName("userPassword")
    @Expose
    private String userPassword;
    @SerializedName("profilePicUrl")
    @Expose
    private String profilePicUrl;
    @SerializedName("loginType")
    @Expose
    private String loginType;
    @SerializedName("securityQuestion")
    @Expose
    private String securityQuestion;
    @SerializedName("securityAnswer")
    @Expose
    private String securityAnswer;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("financialGoal")
    @Expose
    private String financialGoal;
    @SerializedName("riskLevel")
    @Expose
    private String riskLevel;
    @SerializedName("loginId")
    @Expose
    private String loginId;
    @SerializedName("skillLevel")
    @Expose
    private String skillLevel;

    /**
     * No args constructor for use in serialization
     */
    public User() {
    }

    /**
     * @param loginId
     * @param username
     * @param riskLevel
     * @param userPhoneNo
     * @param userId
     * @param financialGoal
     * @param userPassword
     * @param profilePicUrl
     * @param userEmailId
     * @param securityQuestion
     * @param securityAnswer
     * @param loginType
     * @param skillLevel
     */
    public User(String userId, String userEmailId, String userPhoneNo, String userPassword, String profilePicUrl, String loginType, String securityQuestion, String securityAnswer, String username, String financialGoal, String riskLevel, String loginId, String skillLevel) {
        super();
        this.userId = userId;
        this.userEmailId = userEmailId;
        this.userPhoneNo = userPhoneNo;
        this.userPassword = userPassword;
        this.profilePicUrl = profilePicUrl;
        this.loginType = loginType;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.username = username;
        this.financialGoal = financialGoal;
        this.riskLevel = riskLevel;
        this.loginId = loginId;
        this.skillLevel = skillLevel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFinancialGoal() {
        return financialGoal;
    }

    public void setFinancialGoal(String financialGoal) {
        this.financialGoal = financialGoal;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

}