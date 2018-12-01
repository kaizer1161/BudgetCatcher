package com.pushertest.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUp {

    @SerializedName("username")
    @Expose
    private String userName;
    @SerializedName("userEmailId")
    @Expose
    private String userEmailId;
    @SerializedName("userPassword")
    @Expose
    private String userPassword;
    @SerializedName("userPhoneNo")
    @Expose
    private String userPhoneNo;
    @SerializedName("loginType")
    @Expose
    private String loginType;
    @SerializedName("securityQuestion")
    @Expose
    private String securityQuestion;
    @SerializedName("securityAnswer")
    @Expose
    private String securityAnswer;

    /**
     * @param username
     * @param userPhoneNo
     * @param userPassword
     * @param userEmailId
     * @param securityQuestion
     * @param securityAnswer
     * @param loginType
     */
    public SignUp(String username, String userEmailId, String userPassword, String userPhoneNo, String loginType, String securityQuestion, String securityAnswer) {
        super();
        this.userName = username;
        this.userEmailId = userEmailId;
        this.userPassword = userPassword;
        this.userPhoneNo = userPhoneNo;
        this.loginType = loginType;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
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

}