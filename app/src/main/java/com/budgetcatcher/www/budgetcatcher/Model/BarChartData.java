package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BarChartData {

    @SerializedName("years")
    @Expose
    private Integer years;
    @SerializedName("months")
    @Expose
    private Integer months;
    @SerializedName("m_userId")
    @Expose
    private Integer mUserId;
    @SerializedName("incomeBudget")
    @Expose
    private Integer incomeBudget;
    @SerializedName("incomeActual")
    @Expose
    private Integer incomeActual;
    @SerializedName("allowanceBudget")
    @Expose
    private Integer allowanceBudget;
    @SerializedName("allowanceActual")
    @Expose
    private Integer allowanceActual;
    @SerializedName("billsBudget")
    @Expose
    private Integer billsBudget;
    @SerializedName("billsActual")
    @Expose
    private Integer billsActual;
    @SerializedName("incidentalTotal")
    @Expose
    private Integer incidentalTotal;

    /**
     * No args constructor for use in serialization
     */
    public BarChartData() {
    }

    /**
     * @param incomeBudget
     * @param months
     * @param allowanceActual
     * @param incomeActual
     * @param incidentalTotal
     * @param billsActual
     * @param years
     * @param billsBudget
     * @param allowanceBudget
     * @param mUserId
     */
    public BarChartData(Integer years, Integer months, Integer mUserId, Integer incomeBudget, Integer incomeActual, Integer allowanceBudget, Integer allowanceActual, Integer billsBudget, Integer billsActual, Integer incidentalTotal) {
        super();
        this.years = years;
        this.months = months;
        this.mUserId = mUserId;
        this.incomeBudget = incomeBudget;
        this.incomeActual = incomeActual;
        this.allowanceBudget = allowanceBudget;
        this.allowanceActual = allowanceActual;
        this.billsBudget = billsBudget;
        this.billsActual = billsActual;
        this.incidentalTotal = incidentalTotal;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public Integer getMUserId() {
        return mUserId;
    }

    public void setMUserId(Integer mUserId) {
        this.mUserId = mUserId;
    }

    public Integer getIncomeBudget() {
        return incomeBudget;
    }

    public void setIncomeBudget(Integer incomeBudget) {
        this.incomeBudget = incomeBudget;
    }

    public Integer getIncomeActual() {
        return incomeActual;
    }

    public void setIncomeActual(Integer incomeActual) {
        this.incomeActual = incomeActual;
    }

    public Integer getAllowanceBudget() {
        return allowanceBudget;
    }

    public void setAllowanceBudget(Integer allowanceBudget) {
        this.allowanceBudget = allowanceBudget;
    }

    public Integer getAllowanceActual() {
        return allowanceActual;
    }

    public void setAllowanceActual(Integer allowanceActual) {
        this.allowanceActual = allowanceActual;
    }

    public Integer getBillsBudget() {
        return billsBudget;
    }

    public void setBillsBudget(Integer billsBudget) {
        this.billsBudget = billsBudget;
    }

    public Integer getBillsActual() {
        return billsActual;
    }

    public void setBillsActual(Integer billsActual) {
        this.billsActual = billsActual;
    }

    public Integer getIncidentalTotal() {
        return incidentalTotal;
    }

    public void setIncidentalTotal(Integer incidentalTotal) {
        this.incidentalTotal = incidentalTotal;
    }

}
