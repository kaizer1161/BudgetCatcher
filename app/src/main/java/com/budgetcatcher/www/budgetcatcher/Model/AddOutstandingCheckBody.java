package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddOutstandingCheckBody {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("checkNo")
    @Expose
    private String checkNo;
    @SerializedName("outBalance")
    @Expose
    private String outBalance;

    /**
     * No args constructor for use in serialization
     */
    public AddOutstandingCheckBody() {
    }

    /**
     * @param checkNo
     * @param userId
     * @param outBalance
     */
    public AddOutstandingCheckBody(String userId, String checkNo, String outBalance) {
        super();
        this.userId = userId;
        this.checkNo = checkNo;
        this.outBalance = outBalance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public String getOutBalance() {
        return outBalance;
    }

    public void setOutBalance(String outBalance) {
        this.outBalance = outBalance;
    }

}