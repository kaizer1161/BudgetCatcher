package com.budgetcatcher.www.budgetcatcher.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CatcherResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("incidentalsData")
    @Expose
    private List<Expenses> incidentalsData = null;
    @SerializedName("allowancesData")
    @Expose
    private List<Allowance> allowancesData = null;
    @SerializedName("billsData")
    @Expose
    private List<Bill> billsData = null;
    @SerializedName("incomesData")
    @Expose
    private List<Income> incomesData = null;
    @SerializedName("expenseData")
    @Expose
    private ExpenseData expenseData;

    /**
     * No args constructor for use in serialization
     */
    public CatcherResponse() {
    }

    /**
     * @param incidentalsData
     * @param incomesData
     * @param status
     * @param billsData
     * @param allowancesData
     * @param expenseData
     */
    public CatcherResponse(String status, List<Expenses> incidentalsData, List<Allowance> allowancesData, List<Bill> billsData, List<Income> incomesData, ExpenseData expenseData) {
        super();
        this.status = status;
        this.incidentalsData = incidentalsData;
        this.allowancesData = allowancesData;
        this.billsData = billsData;
        this.incomesData = incomesData;
        this.expenseData = expenseData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Expenses> getIncidentalsData() {
        return incidentalsData;
    }

    public void setIncidentalsData(List<Expenses> incidentalsData) {
        this.incidentalsData = incidentalsData;
    }

    public List<Allowance> getAllowancesData() {
        return allowancesData;
    }

    public void setAllowancesData(List<Allowance> allowancesData) {
        this.allowancesData = allowancesData;
    }

    public List<Bill> getBillsData() {
        return billsData;
    }

    public void setBillsData(List<Bill> billsData) {
        this.billsData = billsData;
    }

    public List<Income> getIncomesData() {
        return incomesData;
    }

    public void setIncomesData(List<Income> incomesData) {
        this.incomesData = incomesData;
    }

    public ExpenseData getExpenseData() {
        return expenseData;
    }

    public void setExpenseData(ExpenseData expenseData) {
        this.expenseData = expenseData;
    }

}


