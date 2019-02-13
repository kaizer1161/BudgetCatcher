package com.budgetcatcher.www.budgetcatcher.Model;

public class BarChartActual {

    private Integer years;
    private Integer months;
    private Float incomeActual;
    private Float allowanceActual;
    private Float billsActual;
    private Float incidentalTotal;
    private Float cashDeficit;

    /**
     * No args constructor for use in serialization
     */
    public BarChartActual() {
    }

    /**
     * @param months
     * @param allowanceActual
     * @param incomeActual
     * @param incidentalTotal
     * @param billsActual
     * @param years
     */
    public BarChartActual(Integer years, Integer months, Float incomeActual, Float allowanceActual, Float billsActual, Float incidentalTotal) {
        super();
        this.years = years;
        this.months = months;
        this.incomeActual = incomeActual;
        this.allowanceActual = allowanceActual;
        this.billsActual = billsActual;
        this.incidentalTotal = incidentalTotal;
        cashDeficit = incomeActual - billsActual - allowanceActual - incidentalTotal;
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

    public Float getIncomeActual() {
        return incomeActual;
    }

    public void setIncomeActual(Float incomeActual) {
        this.incomeActual = incomeActual;
    }

    public Float getAllowanceActual() {
        return allowanceActual;
    }

    public void setAllowanceActual(Float allowanceActual) {
        this.allowanceActual = allowanceActual;
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

    public Float getCashDeficit() {
        return cashDeficit;
    }

    public void setCashDeficit(Float cashDeficit) {
        this.cashDeficit = cashDeficit;
    }
}
