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
    private Float incomeBudget;
    @SerializedName("incomeActual")
    @Expose
    private Float incomeActual;
    @SerializedName("allowanceBudget")
    @Expose
    private Float allowanceBudget;
    @SerializedName("allowanceActual")
    @Expose
    private Float allowanceActual;
    @SerializedName("billsBudget")
    @Expose
    private Float billsBudget;
    @SerializedName("billsActual")
    @Expose
    private Float billsActual;
    @SerializedName("incidentalTotal")
    @Expose
    private Float incidentalTotal;

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
    public BarChartData(Integer years, Integer months, Integer mUserId, Float incomeBudget, Float incomeActual, Float allowanceBudget, Float allowanceActual, Float billsBudget, Float billsActual, Float incidentalTotal) {
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

    public Float getIncomeBudget() {
        return incomeBudget;
    }

    public void setIncomeBudget(Float incomeBudget) {
        this.incomeBudget = incomeBudget;
    }

    public Float getIncomeActual() {
        return incomeActual;
    }

    public void setIncomeActual(Float incomeActual) {
        this.incomeActual = incomeActual;
    }

    public Float getAllowanceBudget() {
        return allowanceBudget;
    }

    public void setAllowanceBudget(Float allowanceBudget) {
        this.allowanceBudget = allowanceBudget;
    }

    public Float getAllowanceActual() {
        return allowanceActual;
    }

    public void setAllowanceActual(Float allowanceActual) {
        this.allowanceActual = allowanceActual;
    }

    public Float getBillsBudget() {
        return billsBudget;
    }

    public void setBillsBudget(Float billsBudget) {
        this.billsBudget = billsBudget;
    }

    public Float getBillsActual() {
        return billsActual;
    }

    public void setBillsActual(Float billsActual) {
        this.billsActual = billsActual;
    }

    public Float getIncidentalTotal() {
        return incidentalTotal;
    }

    public void setIncidentalTotal(Float incidentalTotal) {
        this.incidentalTotal = incidentalTotal;
    }

}
