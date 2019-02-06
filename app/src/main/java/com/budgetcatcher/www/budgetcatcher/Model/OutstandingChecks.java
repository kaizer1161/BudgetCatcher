package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutstandingChecks {

    @SerializedName("ocId")
    @Expose
    private String ocId;
    @SerializedName("checkNo")
    @Expose
    private String checkNo;
    @SerializedName("outBalance")
    @Expose
    private String outBalance;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     */
    public OutstandingChecks() {
    }

    /**
     * @param updatedAt
     * @param checkNo
     * @param ocId
     * @param createdAt
     * @param userId
     * @param outBalance
     */
    public OutstandingChecks(String ocId, String checkNo, String outBalance, String userId, String createdAt, String updatedAt) {
        super();
        this.ocId = ocId;
        this.checkNo = checkNo;
        this.outBalance = outBalance;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getOcId() {
        return ocId;
    }

    public void setOcId(String ocId) {
        this.ocId = ocId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
