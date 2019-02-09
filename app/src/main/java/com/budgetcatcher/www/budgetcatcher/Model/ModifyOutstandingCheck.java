package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModifyOutstandingCheck {

    @SerializedName("checkNo")
    @Expose
    private String checkNo;
    @SerializedName("outBalance")
    @Expose
    private String outBalance;

    /**
     * No args constructor for use in serialization
     */
    public ModifyOutstandingCheck() {
    }

    /**
     * @param checkNo
     * @param outBalance
     */
    public ModifyOutstandingCheck(String checkNo, String outBalance) {
        super();
        this.checkNo = checkNo;
        this.outBalance = outBalance;
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