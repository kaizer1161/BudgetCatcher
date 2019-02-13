package com.budgetcatcher.www.budgetcatcher.Model;

public class BarChartBudget {

    private Integer years;
    private Integer months;
    private Float incomeBudget;
    private Float allowanceBudget;
    private Float billsBudget;
    private Float cashDeficit;

    /**
     * No args constructor for use in serialization
     */
    public BarChartBudget() {
    }

    /**
     * @param incomeBudget
     * @param months
     * @param years
     * @param billsBudget
     * @param allowanceBudget
     */
    public BarChartBudget(Integer years, Integer months, Float incomeBudget, Float allowanceBudget, Float billsBudget) {
        super();
        this.years = years;
        this.months = months;
        this.incomeBudget = incomeBudget;
        this.allowanceBudget = allowanceBudget;
        this.billsBudget = billsBudget;
        cashDeficit = incomeBudget - billsBudget - allowanceBudget;

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

    public Float getIncomeBudget() {
        return incomeBudget;
    }

    public void setIncomeBudget(Float incomeBudget) {
        this.incomeBudget = incomeBudget;
    }

    public Float getAllowanceBudget() {
        return allowanceBudget;
    }

    public void setAllowanceBudget(Float allowanceBudget) {
        this.allowanceBudget = allowanceBudget;
    }

    public Float getBillsBudget() {
        return billsBudget;
    }

    public void setBillsBudget(Float billsBudget) {
        this.billsBudget = billsBudget;
    }

    public Float getCashDeficit() {
        return cashDeficit;
    }

    public void setCashDeficit(Float cashDeficit) {
        this.cashDeficit = cashDeficit;
    }
}
